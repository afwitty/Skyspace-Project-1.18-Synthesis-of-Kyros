package software.bernie.shadowed.fasterxml.jackson.databind.ser.std;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import software.bernie.shadowed.fasterxml.jackson.annotation.JsonInclude;
import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
import software.bernie.shadowed.fasterxml.jackson.databind.*;
import software.bernie.shadowed.fasterxml.jackson.databind.annotation.JsonSerialize;
import software.bernie.shadowed.fasterxml.jackson.databind.introspect.Annotated;
import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
import software.bernie.shadowed.fasterxml.jackson.databind.ser.ContextualSerializer;
import software.bernie.shadowed.fasterxml.jackson.databind.ser.impl.PropertySerializerMap;
import software.bernie.shadowed.fasterxml.jackson.databind.type.ReferenceType;
import software.bernie.shadowed.fasterxml.jackson.databind.util.NameTransformer;

// Since 2.6 in its own class
public class AtomicReferenceSerializer
    extends StdSerializer<AtomicReference<?>>
    implements ContextualSerializer
{
    private static final long serialVersionUID = 1L;

    /**
     * Declared type parameter for Optional.
     */
    protected final JavaType _referredType;

    protected final BeanProperty _property;

    /**
     * Type serializer used for values, if any.
     *
     * @since 2.7
     */
    protected final TypeSerializer _valueTypeSerializer;

    /**
     * @since 2.7
     */
    protected final JsonSerializer<Object> _valueSerializer;

    /**
     * To support unwrapped values of dynamic types, will need this:
     *
     * @since 2.7
     */
    protected final NameTransformer _unwrapper;

    /**
     * Further guidance on serialization-inclusion (or not), regarding
     * contained value (if any).
     *
     * @since 2.7
     */
    protected final JsonInclude.Include _contentInclusion;
    
    /**
     * If element type can not be statically determined, mapping from
     * runtime type to serializer is handled using this object
     *
     * @since 2.7
     */
    protected transient PropertySerializerMap _dynamicSerializers;

    /*
    /**********************************************************
    /* Constructors, factory methods
    /**********************************************************
     */

    public AtomicReferenceSerializer(ReferenceType fullType, boolean staticTyping, TypeSerializer vts,
            JsonSerializer<Object> ser)
    {
        super(fullType);
        _referredType = fullType.getReferencedType();
        _property = null;
        _valueTypeSerializer = vts;
        _valueSerializer = ser;
        _unwrapper = null;
        _contentInclusion = null;
        _dynamicSerializers = PropertySerializerMap.emptyForProperties();
    }

    @SuppressWarnings("unchecked")
    protected AtomicReferenceSerializer(AtomicReferenceSerializer base, BeanProperty property,
            TypeSerializer vts, JsonSerializer<?> valueSer,
            NameTransformer unwrapper,
            JsonInclude.Include contentIncl)
    {
        super(base);
        _referredType = base._referredType;
        _dynamicSerializers = base._dynamicSerializers;
        _property = property;
        _valueTypeSerializer = vts;
        _valueSerializer = (JsonSerializer<Object>) valueSer;
        _unwrapper = unwrapper;
        if ((contentIncl == JsonInclude.Include.USE_DEFAULTS)
                || (contentIncl == JsonInclude.Include.ALWAYS)) {
            _contentInclusion = null;
        } else {
            _contentInclusion = contentIncl;
        }
    }

    @Override
    public JsonSerializer<AtomicReference<?>> unwrappingSerializer(NameTransformer transformer) {
        JsonSerializer<Object> ser = _valueSerializer;
        if (ser != null) {
            ser = ser.unwrappingSerializer(transformer);
        }
        NameTransformer unwrapper = (_unwrapper == null) ? transformer
                : NameTransformer.chainedTransformer(transformer, _unwrapper);
        return withResolved(_property, _valueTypeSerializer, ser, unwrapper, _contentInclusion);
    }
    
    protected AtomicReferenceSerializer withResolved(BeanProperty prop,
            TypeSerializer vts, JsonSerializer<?> valueSer,
            NameTransformer unwrapper,
            JsonInclude.Include contentIncl)
    {
        if ((_property == prop) && (contentIncl == _contentInclusion)
                && (_valueTypeSerializer == vts) && (_valueSerializer == valueSer)
                && (_unwrapper == unwrapper)) {
            return this;
        }
        return new AtomicReferenceSerializer(this, prop, vts, valueSer, unwrapper, contentIncl);
    }

    /*
    /**********************************************************
    /* Contextualization (support for property annotations)
    /**********************************************************
     */

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider provider,
            BeanProperty property) throws JsonMappingException
    {
        TypeSerializer typeSer = _valueTypeSerializer;
        if (typeSer != null) {
            typeSer = typeSer.forProperty(property);
        }
        // First: do we have an annotation override from property?
        JsonSerializer<?> ser = findAnnotatedContentSerializer(provider, property);;
        if (ser == null) {
            // If not, use whatever was configured by type
            ser = _valueSerializer;
            if (ser == null) {
                // A few conditions needed to be able to fetch serializer here:
                if (_useStatic(provider, property, _referredType)) {
                    ser = _findSerializer(provider, _referredType, property);
                }
            } else {
                ser = provider.handlePrimaryContextualization(ser, property);
            }
        }
        // Also: may want to have more refined exclusion based on referenced value
        JsonInclude.Include contentIncl = _contentInclusion;
        if (property != null) {
            JsonInclude.Value incl = property.findPropertyInclusion(provider.getConfig(),
                    AtomicReference.class);
            JsonInclude.Include newIncl = incl.getContentInclusion();
            if ((newIncl != contentIncl) && (newIncl != JsonInclude.Include.USE_DEFAULTS)) {
                contentIncl = newIncl;
            }
        }
        return withResolved(property, typeSer, ser, _unwrapper, contentIncl);
    }

    protected boolean _useStatic(SerializerProvider provider, BeanProperty property,
            JavaType referredType)
    {
        // First: no serializer for `Object.class`, must be dynamic
        if (referredType.isJavaLangObject()) {
            return false;
        }
        // but if type is final, might as well fetch
        if (referredType.isFinal()) { // or should we allow annotation override? (only if requested...)
            return true;
        }
        // also: if indicated by typing, should be considered static
        if (referredType.useStaticType()) {
            return true;
        }
        // if neither, maybe explicit annotation?
        AnnotationIntrospector intr = provider.getAnnotationIntrospector();
        if ((intr != null) && (property != null)) {
            Annotated ann = property.getMember();
            if (ann != null) {
                JsonSerialize.Typing t = intr.findSerializationTyping(property.getMember());
                if (t == JsonSerialize.Typing.STATIC) {
                    return true;
                }
                if (t == JsonSerialize.Typing.DYNAMIC) {
                    return false;
                }
            }
        }
        // and finally, may be forced by global static typing (unlikely...)
        return provider.isEnabled(MapperFeature.USE_STATIC_TYPING);
    }

    /*
    /**********************************************************
    /* Accessors
    /**********************************************************
     */

    @Override
    public boolean isEmpty(SerializerProvider provider, AtomicReference<?> value)
    {
        if (value == null) {
            return true;
        }
        Object contents = value.get();
        if (contents == null) {
            return true;
        }
        if (_contentInclusion == null) {
            return false;
        }
        JsonSerializer<Object> ser = _valueSerializer;
        if (ser == null) {
            try {
                ser = _findCachedSerializer(provider, contents.getClass());
            } catch (JsonMappingException e) { // nasty but necessary
                throw new RuntimeJsonMappingException(e);
            }
        }
        return ser.isEmpty(provider, contents);

    }

    @Override
    public boolean isUnwrappingSerializer() {
        return (_unwrapper != null);
    }

    /*
    /**********************************************************
    /* Serialization methods
    /**********************************************************
     */

    @Override
    public void serialize(AtomicReference<?> ref, JsonGenerator g, SerializerProvider provider)
        throws IOException
    {
        Object value = ref.get();
        if (value == null) {
            if (_unwrapper == null) {
                provider.defaultSerializeNull(g);
            }
            return;
        }
        JsonSerializer<Object> ser = _valueSerializer;
        if (ser == null) {
            ser = _findCachedSerializer(provider, value.getClass());
        }
        if (_valueTypeSerializer != null) {
            ser.serializeWithType(value, g, provider, _valueTypeSerializer);
        } else {
            ser.serialize(value, g, provider);
        }
    }

    @Override
    public void serializeWithType(AtomicReference<?> ref,
            JsonGenerator g, SerializerProvider provider,
            TypeSerializer typeSer) throws IOException
    {
        Object value = ref.get();
        if (value == null) {
            if (_unwrapper == null) {
                provider.defaultSerializeNull(g);
            }
            return;
        }

        // 19-Apr-2016, tatu: In order to basically "skip" the whole wrapper level
        //    (which is what non-polymorphic serialization does too), we will need
        //    to simply delegate call, I think, and NOT try to use it here.
        
        // Otherwise apply type-prefix/suffix, then std serialize:
        /*
        typeSer.writeTypePrefixForScalar(ref, g);
        serialize(ref, g, provider);
        typeSer.writeTypeSuffixForScalar(ref, g);
        */

        JsonSerializer<Object> ser = _valueSerializer;
        if (ser == null) {
            ser = _findCachedSerializer(provider, value.getClass());
        }
        ser.serializeWithType(value, g, provider, typeSer);
    }

    /*
    /**********************************************************
    /* Introspection support
    /**********************************************************
     */

    @Override
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint)
        throws JsonMappingException
    {
        JsonSerializer<?> ser = _valueSerializer;
        if (ser == null) {
            ser = _findSerializer(visitor.getProvider(), _referredType, _property);
            if (_unwrapper != null) {
                ser = ser.unwrappingSerializer(_unwrapper);
            }
        }
        ser.acceptJsonFormatVisitor(visitor, _referredType);
    }

    /*
    /**********************************************************
    /* Helper methods
    /**********************************************************
     */
    
    /**
     * Helper method that encapsulates logic of retrieving and caching required
     * serializer.
     */
    private final JsonSerializer<Object> _findCachedSerializer(SerializerProvider provider,
            Class<?> type) throws JsonMappingException
    {
        JsonSerializer<Object> ser = _dynamicSerializers.serializerFor(type);
        if (ser == null) {
            ser = _findSerializer(provider, type, _property);
            if (_unwrapper != null) {
                ser = ser.unwrappingSerializer(_unwrapper);
            }
            _dynamicSerializers = _dynamicSerializers.newWith(type, ser);
        }
        return ser;
    }

    private final JsonSerializer<Object> _findSerializer(SerializerProvider provider,
            Class<?> type, BeanProperty prop) throws JsonMappingException
    {
        return provider.findTypedValueSerializer(type, true, prop);
    }

    private final JsonSerializer<Object> _findSerializer(SerializerProvider provider,
        JavaType type, BeanProperty prop) throws JsonMappingException
    {
        return provider.findTypedValueSerializer(type, true, prop);
    }

}
