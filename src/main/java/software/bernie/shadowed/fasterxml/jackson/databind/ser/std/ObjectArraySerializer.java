package software.bernie.shadowed.fasterxml.jackson.databind.ser.std;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

import software.bernie.shadowed.fasterxml.jackson.annotation.JsonFormat;
import software.bernie.shadowed.fasterxml.jackson.core.*;
import software.bernie.shadowed.fasterxml.jackson.databind.*;
import software.bernie.shadowed.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMember;
import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import software.bernie.shadowed.fasterxml.jackson.databind.jsonschema.SchemaAware;
import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
import software.bernie.shadowed.fasterxml.jackson.databind.node.ObjectNode;
import software.bernie.shadowed.fasterxml.jackson.databind.ser.ContainerSerializer;
import software.bernie.shadowed.fasterxml.jackson.databind.ser.ContextualSerializer;
import software.bernie.shadowed.fasterxml.jackson.databind.ser.impl.PropertySerializerMap;
import software.bernie.shadowed.fasterxml.jackson.databind.type.ArrayType;
import software.bernie.shadowed.fasterxml.jackson.databind.type.TypeFactory;

/**
 * Generic serializer for Object arrays (<code>Object[]</code>).
 */
@JacksonStdImpl
@SuppressWarnings("serial")
public class ObjectArraySerializer
    extends ArraySerializerBase<Object[]>
    implements ContextualSerializer
{
    /**
     * Whether we are using static typing (using declared types, ignoring
     * runtime type) or not for elements.
     */
    protected final boolean _staticTyping;

    /**
     * Declared type of element entries
     */
    protected final JavaType _elementType;

    /**
     * Type serializer to use for values, if any.
     */
    protected final TypeSerializer _valueTypeSerializer;
    
    /**
     * Value serializer to use, if it can be statically determined.
     */
    protected JsonSerializer<Object> _elementSerializer;

    /**
     * If element type can not be statically determined, mapping from
     * runtime type to serializer is handled using this object
     */
    protected PropertySerializerMap _dynamicSerializers;

    /*
    /**********************************************************
    /* Life-cycle
    /**********************************************************
     */
    
    public ObjectArraySerializer(JavaType elemType, boolean staticTyping,
            TypeSerializer vts, JsonSerializer<Object> elementSerializer)
    {
        super(Object[].class);
        _elementType = elemType;
        _staticTyping = staticTyping;
        _valueTypeSerializer = vts;
        _dynamicSerializers = PropertySerializerMap.emptyForProperties();
        _elementSerializer = elementSerializer;
    }

    public ObjectArraySerializer(ObjectArraySerializer src, TypeSerializer vts)
    {
        super(src);
        _elementType = src._elementType;
        _valueTypeSerializer = vts;
        _staticTyping = src._staticTyping;
        _dynamicSerializers = src._dynamicSerializers;
        _elementSerializer = src._elementSerializer;
    }
    
    @SuppressWarnings("unchecked")
    public ObjectArraySerializer(ObjectArraySerializer src,
            BeanProperty property, TypeSerializer vts, JsonSerializer<?> elementSerializer,
            Boolean unwrapSingle)
    {
        super(src,  property, unwrapSingle);
        _elementType = src._elementType;
        _valueTypeSerializer = vts;
        _staticTyping = src._staticTyping;
        _dynamicSerializers = src._dynamicSerializers;
        _elementSerializer = (JsonSerializer<Object>) elementSerializer;
    }

    @Override
    public JsonSerializer<?> _withResolved(BeanProperty prop, Boolean unwrapSingle) {
        return new ObjectArraySerializer(this, prop,
                _valueTypeSerializer, _elementSerializer, unwrapSingle);
    }
    
    @Override
    public ContainerSerializer<?> _withValueTypeSerializer(TypeSerializer vts) {
        return new ObjectArraySerializer(_elementType, _staticTyping, vts, _elementSerializer);
    }

    public ObjectArraySerializer withResolved(BeanProperty prop,
            TypeSerializer vts, JsonSerializer<?> ser, Boolean unwrapSingle) {
        if ((_property == prop) && (ser == _elementSerializer)
                && (_valueTypeSerializer == vts) && (_unwrapSingle == unwrapSingle)) {
            return this;
        }
        return new ObjectArraySerializer(this, prop, vts, ser, unwrapSingle);
    }

    /*
    /**********************************************************
    /* Post-processing
    /**********************************************************
     */

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider provider,
            BeanProperty property)
        throws JsonMappingException
    {
        TypeSerializer vts = _valueTypeSerializer;
        if (vts != null) {
            vts = vts.forProperty(property);
        }
        JsonSerializer<?> ser = null;
        Boolean unwrapSingle = null;

        // First: if we have a property, may have property-annotation overrides
        if (property != null) {
            AnnotatedMember m = property.getMember();
            final AnnotationIntrospector intr = provider.getAnnotationIntrospector();
            if (m != null) {
                Object serDef = intr.findContentSerializer(m);
                if (serDef != null) {
                    ser = provider.serializerInstance(m, serDef);
                }
            }
            JsonFormat.Value format = property.findPropertyFormat(provider.getConfig(), _handledType);
            if (format != null) {
                unwrapSingle = format.getFeature(JsonFormat.Feature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED);
            }
        }
        if (ser == null) {
            ser = _elementSerializer;
        }
        // #124: May have a content converter
        ser = findConvertingContentSerializer(provider, property, ser);
        if (ser == null) {
            // 30-Sep-2012, tatu: One more thing -- if explicit content type is annotated,
            //   we can consider it a static case as well.
            if (_elementType != null) {
                if (_staticTyping && !_elementType.isJavaLangObject()) {
                    ser = provider.findValueSerializer(_elementType, property);
                }
            }
        } else {
            ser = provider.handleSecondaryContextualization(ser, property);
        }
        return withResolved(property, vts, ser, unwrapSingle);
    }

    /*
    /**********************************************************
    /* Accessors
    /**********************************************************
     */

    @Override
    public JavaType getContentType() {
        return _elementType;
    }

    @Override
    public JsonSerializer<?> getContentSerializer() {
        return _elementSerializer;
    }

    @Override
    public boolean isEmpty(SerializerProvider prov, Object[] value) {
        return (value == null) || (value.length == 0);
    }

    @Override
    public boolean hasSingleElement(Object[] value) {
        return (value.length == 1);
    }

    /*
    /**********************************************************
    /* Actual serialization
    /**********************************************************
     */

    @Override
    public final void serialize(Object[] value, JsonGenerator gen, SerializerProvider provider) throws IOException
    {
        final int len = value.length;
        if (len == 1) {
            if (((_unwrapSingle == null) &&
                    provider.isEnabled(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED))
                    || (_unwrapSingle == Boolean.TRUE)) {
                serializeContents(value, gen, provider);
                return;
            }
        }
        gen.writeStartArray(len);
        serializeContents(value, gen, provider);
        gen.writeEndArray();
    }

    @Override
    public void serializeContents(Object[] value, JsonGenerator gen, SerializerProvider provider) throws IOException
    {
        final int len = value.length;
        if (len == 0) {
            return;
        }
        if (_elementSerializer != null) {
            serializeContentsUsing(value, gen, provider, _elementSerializer);
            return;
        }
        if (_valueTypeSerializer != null) {
            serializeTypedContents(value, gen, provider);
            return;
        }
        int i = 0;
        Object elem = null;
        try {
            PropertySerializerMap serializers = _dynamicSerializers;
            for (; i < len; ++i) {
                elem = value[i];
                if (elem == null) {
                    provider.defaultSerializeNull(gen);
                    continue;
                }
                Class<?> cc = elem.getClass();
                JsonSerializer<Object> serializer = serializers.serializerFor(cc);
                if (serializer == null) {
                    // To fix [JACKSON-508]
                    if (_elementType.hasGenericTypes()) {
                        serializer = _findAndAddDynamic(serializers,
                                provider.constructSpecializedType(_elementType, cc), provider);
                    } else {
                        serializer = _findAndAddDynamic(serializers, cc, provider);
                    }
                }
                serializer.serialize(elem, gen, provider);
            }
        } catch (IOException ioe) {
            throw ioe;
        } catch (Exception e) {
            // [JACKSON-55] Need to add reference information
            /* 05-Mar-2009, tatu: But one nasty edge is when we get
             *   StackOverflow: usually due to infinite loop. But that gets
             *   hidden within an InvocationTargetException...
             */
            Throwable t = e;
            while (t instanceof InvocationTargetException && t.getCause() != null) {
                t = t.getCause();
            }
            if (t instanceof Error) {
                throw (Error) t;
            }
            throw JsonMappingException.wrapWithPath(t, elem, i);
        }
    }

    public void serializeContentsUsing(Object[] value, JsonGenerator jgen, SerializerProvider provider,
            JsonSerializer<Object> ser) throws IOException
    {
        final int len = value.length;
        final TypeSerializer typeSer = _valueTypeSerializer;

        int i = 0;
        Object elem = null;
        try {
            for (; i < len; ++i) {
                elem = value[i];
                if (elem == null) {
                    provider.defaultSerializeNull(jgen);
                    continue;
                }
                if (typeSer == null) {
                    ser.serialize(elem, jgen, provider);
                } else {
                    ser.serializeWithType(elem, jgen, provider, typeSer);
                }
            }
        } catch (IOException ioe) {
            throw ioe;
        } catch (Exception e) {
            Throwable t = e;
            while (t instanceof InvocationTargetException && t.getCause() != null) {
                t = t.getCause();
            }
            if (t instanceof Error) {
                throw (Error) t;
            }
            throw JsonMappingException.wrapWithPath(t, elem, i);
        }
    }

    public void serializeTypedContents(Object[] value, JsonGenerator jgen, SerializerProvider provider) throws IOException
    {
        final int len = value.length;
        final TypeSerializer typeSer = _valueTypeSerializer;
        int i = 0;
        Object elem = null;
        try {
            PropertySerializerMap serializers = _dynamicSerializers;
            for (; i < len; ++i) {
                elem = value[i];
                if (elem == null) {
                    provider.defaultSerializeNull(jgen);
                    continue;
                }
                Class<?> cc = elem.getClass();
                JsonSerializer<Object> serializer = serializers.serializerFor(cc);
                if (serializer == null) {
                    serializer = _findAndAddDynamic(serializers, cc, provider);
                }
                serializer.serializeWithType(elem, jgen, provider, typeSer);
            }
        } catch (IOException ioe) {
            throw ioe;
        } catch (Exception e) {
            Throwable t = e;
            while (t instanceof InvocationTargetException && t.getCause() != null) {
                t = t.getCause();
            }
            if (t instanceof Error) {
                throw (Error) t;
            }
            throw JsonMappingException.wrapWithPath(t, elem, i);
        }
    }
    
    @SuppressWarnings("deprecation")
    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint)
        throws JsonMappingException
    {
        ObjectNode o = createSchemaNode("array", true);
        if (typeHint != null) {
            JavaType javaType = provider.constructType(typeHint);
            if (javaType.isArrayType()) {
                Class<?> componentType = ((ArrayType) javaType).getContentType().getRawClass();
                // 15-Oct-2010, tatu: We can't serialize plain Object.class; but what should it produce here? Untyped?
                if (componentType == Object.class) {
                    o.set("items", software.bernie.shadowed.fasterxml.jackson.databind.jsonschema.JsonSchema.getDefaultSchemaNode());
                } else {
                    JsonSerializer<Object> ser = provider.findValueSerializer(componentType, _property);
                    JsonNode schemaNode = (ser instanceof SchemaAware) ?
                            ((SchemaAware) ser).getSchema(provider, null) :
                            	software.bernie.shadowed.fasterxml.jackson.databind.jsonschema.JsonSchema.getDefaultSchemaNode();
                    o.set("items", schemaNode);
                }
            }
        }
        return o;
    }

    @Override
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint)
        throws JsonMappingException
    {
        JsonArrayFormatVisitor arrayVisitor = visitor.expectArrayFormat(typeHint);
        if (arrayVisitor != null) {
            TypeFactory tf = visitor.getProvider().getTypeFactory();
            JavaType contentType = tf.moreSpecificType(_elementType, typeHint.getContentType());
            if (contentType == null) {
                throw JsonMappingException.from(visitor.getProvider(), "Could not resolve type");
            }
            JsonSerializer<?> valueSer = _elementSerializer;
            if (valueSer == null) {
                valueSer = visitor.getProvider().findValueSerializer(contentType, _property);
            }
            arrayVisitor.itemsFormat(valueSer, contentType);
        }
    }

    protected final JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap map,
            Class<?> type, SerializerProvider provider) throws JsonMappingException
    {
        PropertySerializerMap.SerializerAndMapResult result = map.findAndAddSecondarySerializer(type, provider, _property);
        // did we get a new map of serializers? If so, start using it
        if (map != result.map) {
            _dynamicSerializers = result.map;
        }
        return result.serializer;
    }

    protected final JsonSerializer<Object> _findAndAddDynamic(PropertySerializerMap map,
            JavaType type, SerializerProvider provider) throws JsonMappingException
    {
        PropertySerializerMap.SerializerAndMapResult result = map.findAndAddSecondarySerializer(type, provider, _property);
        // did we get a new map of serializers? If so, start using it
        if (map != result.map) {
            _dynamicSerializers = result.map;
        }
        return result.serializer;
    }
}
