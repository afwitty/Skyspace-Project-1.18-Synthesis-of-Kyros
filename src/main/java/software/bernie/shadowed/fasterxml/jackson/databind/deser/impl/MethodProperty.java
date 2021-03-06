package software.bernie.shadowed.fasterxml.jackson.databind.deser.impl;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
import software.bernie.shadowed.fasterxml.jackson.databind.*;
import software.bernie.shadowed.fasterxml.jackson.databind.deser.SettableBeanProperty;
import software.bernie.shadowed.fasterxml.jackson.databind.introspect.*;
import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import software.bernie.shadowed.fasterxml.jackson.databind.util.Annotations;

/**
 * This concrete sub-class implements property that is set
 * using regular "setter" method.
 */
public final class MethodProperty
    extends SettableBeanProperty
{
    private static final long serialVersionUID = 1;

    protected final AnnotatedMethod _annotated;
    
    /**
     * Setter method for modifying property value; used for
     * "regular" method-accessible properties.
     */
    protected final transient Method _setter;
    
    public MethodProperty(BeanPropertyDefinition propDef,
            JavaType type, TypeDeserializer typeDeser,
            Annotations contextAnnotations, AnnotatedMethod method)
    {
        super(propDef, type, typeDeser, contextAnnotations);
        _annotated = method;
        _setter = method.getAnnotated();
    }

    protected MethodProperty(MethodProperty src, JsonDeserializer<?> deser) {
        super(src, deser);
        _annotated = src._annotated;
        _setter = src._setter;
    }

    protected MethodProperty(MethodProperty src, PropertyName newName) {
        super(src, newName);
        _annotated = src._annotated;
        _setter = src._setter;
    }

    /**
     * Constructor used for JDK Serialization when reading persisted object
     */
    protected MethodProperty(MethodProperty src, Method m) {
        super(src);
        _annotated = src._annotated;
        _setter = m;
    }
    
    @Override
    public MethodProperty withName(PropertyName newName) {
        return new MethodProperty(this, newName);
    }
    
    @Override
    public MethodProperty withValueDeserializer(JsonDeserializer<?> deser) {
        return new MethodProperty(this, deser);
    }
    
    /*
    /**********************************************************
    /* BeanProperty impl
    /**********************************************************
     */
    
    @Override
    public <A extends Annotation> A getAnnotation(Class<A> acls) {
        return (_annotated == null) ? null : _annotated.getAnnotation(acls);
    }

    @Override public AnnotatedMember getMember() {  return _annotated; }

    /*
    /**********************************************************
    /* Overridden methods
    /**********************************************************
     */

    @Override
    public void deserializeAndSet(JsonParser p, DeserializationContext ctxt,
            Object instance) throws IOException
    {
        Object value = deserialize(p, ctxt);
        try {
            _setter.invoke(instance, value);
        } catch (Exception e) {
            _throwAsIOE(p, e, value);
        }
    }

    @Override
    public Object deserializeSetAndReturn(JsonParser p,
    		DeserializationContext ctxt, Object instance) throws IOException
    {
        Object value = deserialize(p, ctxt);
        try {
            Object result = _setter.invoke(instance, value);
            return (result == null) ? instance : result;
        } catch (Exception e) {
            _throwAsIOE(p, e, value);
            return null;
        }
    }
    
    @Override
    public final void set(Object instance, Object value) throws IOException
    {
        try {
            _setter.invoke(instance, value);
        } catch (Exception e) {
            // 15-Sep-2015, tatu: How coud we get a ref to JsonParser?
            _throwAsIOE(e, value);
        }
    }

    @Override
    public Object setAndReturn(Object instance, Object value) throws IOException
    {
        try {
            Object result = _setter.invoke(instance, value);
            return (result == null) ? instance : result;
        } catch (Exception e) {
            // 15-Sep-2015, tatu: How coud we get a ref to JsonParser?
            _throwAsIOE(e, value);
            return null;
        }
    }

    /*
    /**********************************************************
    /* JDK serialization handling
    /**********************************************************
     */

    Object readResolve() {
        return new MethodProperty(this, _annotated.getAnnotated());
    }
}
