package software.bernie.shadowed.fasterxml.jackson.databind.deser;

import java.io.IOException;
import java.lang.annotation.Annotation;

import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
import software.bernie.shadowed.fasterxml.jackson.core.JsonProcessingException;
import software.bernie.shadowed.fasterxml.jackson.databind.*;
import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMember;
import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedParameter;
import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import software.bernie.shadowed.fasterxml.jackson.databind.util.Annotations;

/**
 * This concrete sub-class implements property that is passed
 * via Creator (constructor or static factory method).
 * It is not a full-featured implementation in that its set method
 * should never be called -- instead, value must separately passed.
 *<p>
 * Note on injectable values: unlike with other mutators, where
 * deserializer and injecting are separate, here we treat the two as related
 * things. This is necessary to add proper priority, as well as to simplify
 * coordination.
 */
public class CreatorProperty
    extends SettableBeanProperty
{
    private static final long serialVersionUID = 1L;

    /**
     * Placeholder that represents constructor parameter, when it is created
     * from actual constructor.
     * May be null when a synthetic instance is created.
     */
    protected final AnnotatedParameter _annotated;

    /**
     * Id of value to inject, if value injection should be used for this parameter
     * (in addition to, or instead of, regular deserialization).
     */
    protected final Object _injectableValueId;

    /**
     * @since 2.1
     */
    protected final int _creatorIndex;

    /**
     * In special cases, when implementing "updateValue", we can not use
     * constructors or factory methods, but have to fall back on using a
     * setter (or mutable field property). If so, this refers to that fallback
     * accessor.
     *<p>
     * Mutable only to allow setting after construction, but must be strictly
     * set before any use.
     * 
     * @since 2.3
     */
    protected SettableBeanProperty _fallbackSetter;

    /**
     * @param name Name of the logical property
     * @param type Type of the property, used to find deserializer
     * @param typeDeser Type deserializer to use for handling polymorphic type
     *    information, if one is needed
     * @param contextAnnotations Contextual annotations (usually by class that
     *    declares creator [constructor, factory method] that includes
     *    this property)
     * @param param Representation of property, constructor or factory
     *    method parameter; used for accessing annotations of the property
     * @param index Index of this property within creator invocation
     * 
     * @since 2.3
     */
    public CreatorProperty(PropertyName name, JavaType type, PropertyName wrapperName,
            TypeDeserializer typeDeser,
            Annotations contextAnnotations, AnnotatedParameter param,
            int index, Object injectableValueId,
            PropertyMetadata metadata)
    {
        super(name, type, wrapperName, typeDeser, contextAnnotations, metadata);
        _annotated = param;
        _creatorIndex = index;
        _injectableValueId = injectableValueId;
        _fallbackSetter = null;
    }

    /**
     * @since 2.3
     */
    protected CreatorProperty(CreatorProperty src, PropertyName newName) {
        super(src, newName);
        _annotated = src._annotated;
        _creatorIndex = src._creatorIndex;
        _injectableValueId = src._injectableValueId;
        _fallbackSetter = src._fallbackSetter;
    }

    protected CreatorProperty(CreatorProperty src, JsonDeserializer<?> deser) {
        super(src, deser);
        _annotated = src._annotated;
        _creatorIndex = src._creatorIndex;
        _injectableValueId = src._injectableValueId;
        _fallbackSetter = src._fallbackSetter;
    }

    @Override
    public CreatorProperty withName(PropertyName newName) {
        return new CreatorProperty(this, newName);
    }
    
    @Override
    public CreatorProperty withValueDeserializer(JsonDeserializer<?> deser) {
        return new CreatorProperty(this, deser);
    }

    /**
     * NOTE: one exception to immutability, due to problems with CreatorProperty instances
     * being shared between Bean, separate PropertyBasedCreator
     * 
     * @since 2.6.0
     */
    public void setFallbackSetter(SettableBeanProperty fallbackSetter) {
        _fallbackSetter = fallbackSetter;
    }

    /**
     * Method that can be called to locate value to be injected for this
     * property, if it is configured for this.
     */
    public Object findInjectableValue(DeserializationContext context, Object beanInstance)
    {
        if (_injectableValueId == null) {
            throw new IllegalStateException("Property '"+getName()
                    +"' (type "+getClass().getName()+") has no injectable value id configured");
        }
        return context.findInjectableValue(_injectableValueId, this, beanInstance);
    }
    
    /**
     * Method to find value to inject, and inject it to this property.
     */
    public void inject(DeserializationContext context, Object beanInstance)
        throws IOException
    {
        set(beanInstance, findInjectableValue(context, beanInstance));
    }
    
    /*
    /**********************************************************
    /* BeanProperty impl
    /**********************************************************
     */
    
    @Override
    public <A extends Annotation> A getAnnotation(Class<A> acls) {
        if (_annotated == null) {
            return null;
        }
        return _annotated.getAnnotation(acls);
    }

    @Override public AnnotatedMember getMember() {  return _annotated; }

    @Override public int getCreatorIndex() {
        return _creatorIndex;
    }
    
    /*
    /**********************************************************
    /* Overridden methods
    /**********************************************************
     */

    @Override
    public void deserializeAndSet(JsonParser jp, DeserializationContext ctxt,
                                  Object instance)
        throws IOException, JsonProcessingException
    {
        set(instance, deserialize(jp, ctxt));
    }

    @Override
    public Object deserializeSetAndReturn(JsonParser jp,
    		DeserializationContext ctxt, Object instance)
        throws IOException, JsonProcessingException
    {
        return setAndReturn(instance, deserialize(jp, ctxt));
    }
    
    @Override
    public void set(Object instance, Object value) throws IOException
    {
        /* Hmmmh. Should we return quietly (NOP), or error?
         * Perhaps better to throw an exception, since it's generally an error.
         */
        if (_fallbackSetter == null) {
            throw new IllegalStateException("No fallback setter/field defined: can not use creator property for "
                    +getClass().getName());
        }
        _fallbackSetter.set(instance, value);
    }

    @Override
    public Object setAndReturn(Object instance, Object value) throws IOException
    {
        if (_fallbackSetter == null) {
            throw new IllegalStateException("No fallback setter/field defined: can not use creator property for "
                    +getClass().getName());
        }
        return _fallbackSetter.setAndReturn(instance, value);
    }
    
    @Override
    public Object getInjectableValueId() {
        return _injectableValueId;
    }

    @Override
    public String toString() { return "[creator property, name '"+getName()+"'; inject id '"+_injectableValueId+"']"; }
}
