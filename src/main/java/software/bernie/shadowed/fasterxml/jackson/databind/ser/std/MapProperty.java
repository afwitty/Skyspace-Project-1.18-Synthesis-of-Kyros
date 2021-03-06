package software.bernie.shadowed.fasterxml.jackson.databind.ser.std;

import java.io.IOException;
import java.lang.annotation.Annotation;

import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
import software.bernie.shadowed.fasterxml.jackson.databind.PropertyMetadata;
import software.bernie.shadowed.fasterxml.jackson.databind.PropertyName;
import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMember;
import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
import software.bernie.shadowed.fasterxml.jackson.databind.node.ObjectNode;
import software.bernie.shadowed.fasterxml.jackson.databind.ser.PropertyWriter;
import software.bernie.shadowed.fasterxml.jackson.databind.type.TypeFactory;

/**
 * Helper class needed to support flexible filtering of Map properties
 * with generic JSON Filter functionality. Since {@link java.util.Map}s
 * are not handled as a collection of properties by Jackson (unlike POJOs),
 * bit more wrapping is required.
 */
public class MapProperty extends PropertyWriter
{
    private static final long serialVersionUID = 1L;

    protected final TypeSerializer _typeSerializer;

    protected final BeanProperty _property;

    protected Object _key;

    protected JsonSerializer<Object> _keySerializer, _valueSerializer;

    public MapProperty(TypeSerializer typeSer, BeanProperty prop)
    {
        super((prop == null) ? PropertyMetadata.STD_REQUIRED_OR_OPTIONAL : prop.getMetadata());
        _typeSerializer = typeSer;
        _property = prop;
    }

    /**
     * Initialization method that needs to be called before passing
     * property to filter.
     */
    public void reset(Object key,
            JsonSerializer<Object> keySer, JsonSerializer<Object> valueSer)
    {
        _key = key;
        _keySerializer = keySer;
        _valueSerializer = valueSer;
    }
    
    @Override
    public String getName() {
        if (_key instanceof String) {
            return (String) _key;
        }
        return String.valueOf(_key);
    }

    @Override
    public PropertyName getFullName() {
        return new PropertyName(getName());
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> acls) {
        return (_property == null) ? null : _property.getAnnotation(acls);
    }

    @Override
    public <A extends Annotation> A getContextAnnotation(Class<A> acls) {
        return (_property == null) ? null : _property.getContextAnnotation(acls);
    }
    
    @Override
    public void serializeAsField(Object value, JsonGenerator gen,
            SerializerProvider provider) throws IOException
    {
        _keySerializer.serialize(_key, gen, provider);
        if (_typeSerializer == null) {
            _valueSerializer.serialize(value, gen, provider);
        } else {
            _valueSerializer.serializeWithType(value, gen, provider, _typeSerializer);
        }
    }

    @Override
    public void serializeAsOmittedField(Object value, JsonGenerator gen,
            SerializerProvider provider) throws Exception
    {
        if (!gen.canOmitFields()) {
            gen.writeOmittedField(getName());
        }
    }

    @Override
    public void serializeAsElement(Object value, JsonGenerator gen,
            SerializerProvider provider) throws Exception
    {
        if (_typeSerializer == null) {
            _valueSerializer.serialize(value, gen, provider);
        } else {
            _valueSerializer.serializeWithType(value, gen, provider, _typeSerializer);
        }
    }
    
    @Override
    public void serializeAsPlaceholder(Object value, JsonGenerator gen,
            SerializerProvider provider) throws Exception
    {
        gen.writeNull();
    }

    /*
    /**********************************************************
    /* Rest of BeanProperty, nop
    /**********************************************************
     */
    
    @Override
    public void depositSchemaProperty(JsonObjectFormatVisitor objectVisitor,
            SerializerProvider provider)
        throws JsonMappingException
    {
        if (_property != null) {
            _property.depositSchemaProperty(objectVisitor, provider);
        }
    }

    @Override
    @Deprecated
    public void depositSchemaProperty(ObjectNode propertiesNode,
            SerializerProvider provider) throws JsonMappingException {
        // nothing to do here
   }

    @Override
    public JavaType getType() {
        return (_property == null) ? TypeFactory.unknownType() : _property.getType();
    }

    @Override
    public PropertyName getWrapperName() {
        return (_property == null) ? null : _property.getWrapperName();
    }

    @Override
    public AnnotatedMember getMember() {
        return (_property == null) ? null : _property.getMember();
    }
}
