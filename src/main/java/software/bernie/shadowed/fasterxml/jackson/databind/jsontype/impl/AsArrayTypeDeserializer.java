package software.bernie.shadowed.fasterxml.jackson.databind.jsontype.impl;

import java.io.IOException;

import software.bernie.shadowed.fasterxml.jackson.annotation.JsonTypeInfo.As;
import software.bernie.shadowed.fasterxml.jackson.core.*;
import software.bernie.shadowed.fasterxml.jackson.core.util.JsonParserSequence;
import software.bernie.shadowed.fasterxml.jackson.databind.*;
import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import software.bernie.shadowed.fasterxml.jackson.databind.util.TokenBuffer;

/**
 * Type deserializer used with {@link As#WRAPPER_ARRAY}
 * inclusion mechanism. Simple since JSON structure used is always
 * the same, regardless of structure used for actual value: wrapping
 * is done using a 2-element JSON Array where type id is the first
 * element, and actual object data as second element.
 */
public class AsArrayTypeDeserializer
    extends TypeDeserializerBase
    implements java.io.Serializable
{
    private static final long serialVersionUID = 1L;

    public AsArrayTypeDeserializer(JavaType bt, TypeIdResolver idRes,
            String typePropertyName, boolean typeIdVisible, Class<?> defaultImpl)
    {
        super(bt, idRes, typePropertyName, typeIdVisible, defaultImpl);
    }

    public AsArrayTypeDeserializer(AsArrayTypeDeserializer src, BeanProperty property) {
        super(src, property);
    }
    
    @Override
    public TypeDeserializer forProperty(BeanProperty prop) {
        // usually if it's null:
        return (prop == _property) ? this : new AsArrayTypeDeserializer(this, prop);
    }
    
    @Override
    public As getTypeInclusion() { return As.WRAPPER_ARRAY; }

    /**
     * Method called when actual object is serialized as JSON Array.
     */
    @Override
    public Object deserializeTypedFromArray(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return _deserialize(jp, ctxt);
    }

    /**
     * Method called when actual object is serialized as JSON Object
     */
    @Override
    public Object deserializeTypedFromObject(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return _deserialize(jp, ctxt);
    }
    
    @Override
    public Object deserializeTypedFromScalar(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return _deserialize(jp, ctxt);
    }    

    @Override
    public Object deserializeTypedFromAny(JsonParser jp, DeserializationContext ctxt) throws IOException {
        return _deserialize(jp, ctxt);
    }    
    
    /*
    /***************************************************************
    /* Internal methods
    /***************************************************************
     */

    /**
     * Method that handles type information wrapper, locates actual
     * subtype deserializer to use, and calls it to do actual
     * deserialization.
     */
    @SuppressWarnings("resource")
    protected Object _deserialize(JsonParser p, DeserializationContext ctxt) throws IOException
    {
        // 02-Aug-2013, tatu: May need to use native type ids
        if (p.canReadTypeId()) {
            Object typeId = p.getTypeId();
            if (typeId != null) {
                return _deserializeWithNativeTypeId(p, ctxt, typeId);
            }
        }
        boolean hadStartArray = p.isExpectedStartArrayToken();
        String typeId = _locateTypeId(p, ctxt);
        JsonDeserializer<Object> deser = _findDeserializer(ctxt, typeId);
        // Minor complication: we may need to merge type id in?
        if (_typeIdVisible
                // 06-Oct-2014, tatu: To fix [databind#408], must distinguish between
                //   internal and external properties
                //  TODO: but does it need to be injected in external case? Why not?
                && !_usesExternalId()
                && p.getCurrentToken() == JsonToken.START_OBJECT) {
            // but what if there's nowhere to add it in? Error? Or skip? For now, skip.
            TokenBuffer tb = new TokenBuffer(null, false);
            tb.writeStartObject(); // recreate START_OBJECT
            tb.writeFieldName(_typePropertyName);
            tb.writeString(typeId);
            p = JsonParserSequence.createFlattened(tb.asParser(p), p);
            p.nextToken();
        }
        Object value = deser.deserialize(p, ctxt);
        // And then need the closing END_ARRAY
        if (hadStartArray && p.nextToken() != JsonToken.END_ARRAY) {
            throw ctxt.wrongTokenException(p, JsonToken.END_ARRAY,
                    "expected closing END_ARRAY after type information and deserialized value");
        }
        return value;
    }    
    
    protected String _locateTypeId(JsonParser jp, DeserializationContext ctxt) throws IOException
    {
        if (!jp.isExpectedStartArrayToken()) {
            // Need to allow even more customized handling, if something unexpected seen...
            // but should there be a way to limit this to likely success cases?
            if (_defaultImpl != null) {
                return _idResolver.idFromBaseType();
            }
            throw ctxt.wrongTokenException(jp, JsonToken.START_ARRAY, "need JSON Array to contain As.WRAPPER_ARRAY type information for class "+baseTypeName());
        }
        // And then type id as a String
        JsonToken t = jp.nextToken();
        if (t == JsonToken.VALUE_STRING) {
            String result = jp.getText();
            jp.nextToken();
            return result;
        }
        if (_defaultImpl != null) {
            return _idResolver.idFromBaseType();
        }
        throw ctxt.wrongTokenException(jp, JsonToken.VALUE_STRING, "need JSON String that contains type id (for subtype of "+baseTypeName()+")");
    }

    /**
     * @since 2.5
     */
    protected boolean _usesExternalId() {
        return false;
    }
}
