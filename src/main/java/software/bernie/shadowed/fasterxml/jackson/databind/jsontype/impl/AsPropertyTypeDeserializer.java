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
 * Type deserializer used with {@link As#PROPERTY}
 * inclusion mechanism.
 * Uses regular form (additional key/value entry before actual data)
 * when typed object is expressed as JSON Object; otherwise behaves similar to how
 * {@link As#WRAPPER_ARRAY} works.
 * Latter is used if JSON representation is polymorphic
 */
public class AsPropertyTypeDeserializer extends AsArrayTypeDeserializer
{
    private static final long serialVersionUID = 1L;

    protected final As _inclusion;

    public AsPropertyTypeDeserializer(JavaType bt, TypeIdResolver idRes,
            String typePropertyName, boolean typeIdVisible, Class<?> defaultImpl)
    {
        this(bt, idRes, typePropertyName, typeIdVisible, defaultImpl, As.PROPERTY);
    }
    
    public AsPropertyTypeDeserializer(JavaType bt, TypeIdResolver idRes,
            String typePropertyName, boolean typeIdVisible, Class<?> defaultImpl,
            As inclusion)
    {
        super(bt, idRes, typePropertyName, typeIdVisible, defaultImpl);
        _inclusion = inclusion;
    }

    public AsPropertyTypeDeserializer(AsPropertyTypeDeserializer src, BeanProperty property) {
        super(src, property);
        _inclusion = src._inclusion;
    }
    
    @Override
    public TypeDeserializer forProperty(BeanProperty prop) {
        return (prop == _property) ? this : new AsPropertyTypeDeserializer(this, prop);
    }
    
    @Override
    public As getTypeInclusion() { return _inclusion; }

    /**
     * This is the trickiest thing to handle, since property we are looking
     * for may be anywhere...
     */
    @Override
    @SuppressWarnings("resource")
    public Object deserializeTypedFromObject(JsonParser p, DeserializationContext ctxt) throws IOException
    {
        // 02-Aug-2013, tatu: May need to use native type ids
        if (p.canReadTypeId()) {
            Object typeId = p.getTypeId();
            if (typeId != null) {
                return _deserializeWithNativeTypeId(p, ctxt, typeId);
            }
        }
        
        // but first, sanity check to ensure we have START_OBJECT or FIELD_NAME
        JsonToken t = p.getCurrentToken();
        if (t == JsonToken.START_OBJECT) {
            t = p.nextToken();
        } else if (/*t == JsonToken.START_ARRAY ||*/ t != JsonToken.FIELD_NAME) {
            /* This is most likely due to the fact that not all Java types are
             * serialized as JSON Objects; so if "as-property" inclusion is requested,
             * serialization of things like Lists must be instead handled as if
             * "as-wrapper-array" was requested.
             * But this can also be due to some custom handling: so, if "defaultImpl"
             * is defined, it will be asked to handle this case.
             */
            return _deserializeTypedUsingDefaultImpl(p, ctxt, null);
        }
        // Ok, let's try to find the property. But first, need token buffer...
        TokenBuffer tb = null;

        for (; t == JsonToken.FIELD_NAME; t = p.nextToken()) {
            String name = p.getCurrentName();
            p.nextToken(); // to point to the value
            if (name.equals(_typePropertyName)) { // gotcha!
                return _deserializeTypedForId(p, ctxt, tb);
            }
            if (tb == null) {
                tb = new TokenBuffer(p, ctxt);
            }
            tb.writeFieldName(name);
            tb.copyCurrentStructure(p);
        }
        return _deserializeTypedUsingDefaultImpl(p, ctxt, tb);
    }

    @SuppressWarnings("resource")
    protected Object _deserializeTypedForId(JsonParser p, DeserializationContext ctxt, TokenBuffer tb) throws IOException
    {
        String typeId = p.getText();
        JsonDeserializer<Object> deser = _findDeserializer(ctxt, typeId);
        if (_typeIdVisible) { // need to merge id back in JSON input?
            if (tb == null) {
                tb = new TokenBuffer(p, ctxt);
            }
            tb.writeFieldName(p.getCurrentName());
            tb.writeString(typeId);
        }
        if (tb != null) { // need to put back skipped properties?
            p = JsonParserSequence.createFlattened(tb.asParser(p), p);
        }
        // Must point to the next value; tb had no current, jp pointed to VALUE_STRING:
        p.nextToken(); // to skip past String value
        // deserializer should take care of closing END_OBJECT as well
        return deser.deserialize(p, ctxt);
    }
    
    // off-lined to keep main method lean and mean...
    @SuppressWarnings("resource")
    protected Object _deserializeTypedUsingDefaultImpl(JsonParser p, DeserializationContext ctxt, TokenBuffer tb) throws IOException
    {
        // As per [JACKSON-614], may have default implementation to use
        JsonDeserializer<Object> deser = _findDefaultImplDeserializer(ctxt);
        if (deser != null) {
            if (tb != null) {
                tb.writeEndObject();
                p = tb.asParser(p);
                // must move to point to the first token:
                p.nextToken();
            }
            return deser.deserialize(p, ctxt);
        }
        // or, perhaps we just bumped into a "natural" value (boolean/int/double/String)?
        Object result = TypeDeserializer.deserializeIfNatural(p, ctxt, _baseType);
        if (result != null) {
            return result;
        }
        // or, something for which "as-property" won't work, changed into "wrapper-array" type:
        if (p.getCurrentToken() == JsonToken.START_ARRAY) {
            return super.deserializeTypedFromAny(p, ctxt);
        }
        throw ctxt.wrongTokenException(p, JsonToken.FIELD_NAME,
                "missing property '"+_typePropertyName+"' that is to contain type id  (for class "+baseTypeName()+")");
    }

    /* Also need to re-route "unknown" version. Need to think
     * this through bit more in future, but for now this does address issue and has
     * no negative side effects (at least within existing unit test suite).
     */
    @Override
    public Object deserializeTypedFromAny(JsonParser p, DeserializationContext ctxt) throws IOException {
        /* Sometimes, however, we get an array wrapper; specifically
         * when an array or list has been serialized with type information.
         */
        if (p.getCurrentToken() == JsonToken.START_ARRAY) {
            return super.deserializeTypedFromArray(p, ctxt);
        }
        return deserializeTypedFromObject(p, ctxt);
    }    
    
    // These are fine from base class:
    //public Object deserializeTypedFromArray(JsonParser jp, DeserializationContext ctxt)
    //public Object deserializeTypedFromScalar(JsonParser jp, DeserializationContext ctxt)    
}
