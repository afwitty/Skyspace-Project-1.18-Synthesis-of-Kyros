package software.bernie.shadowed.fasterxml.jackson.databind.jsontype.impl;

import java.io.IOException;

import software.bernie.shadowed.fasterxml.jackson.annotation.JsonTypeInfo.As;
import software.bernie.shadowed.fasterxml.jackson.core.*;
import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeIdResolver;

/**
 * Type serializer that preferably embeds type information as an "external"
 * type property; embedded in enclosing JSON object.
 * Note that this serializer should only be used when value is being output
 * at JSON Object context; otherwise it can not work reliably, and will have
 * to revert operation similar to {@link AsPropertyTypeSerializer}.
 *<p>
 * Note that implementation of serialization is bit cumbersome as we must
 * serialized external type id AFTER object; this because callback only
 * occurs after field name has been written.
 *<p>
 * Also note that this type of type id inclusion will NOT try to make use
 * of native Type Ids, even if those exist.
 */
public class AsExternalTypeSerializer extends TypeSerializerBase
{
    protected final String _typePropertyName;

    public AsExternalTypeSerializer(TypeIdResolver idRes, BeanProperty property, String propName) {
        super(idRes, property);
        _typePropertyName = propName;
    }

    @Override
    public AsExternalTypeSerializer forProperty(BeanProperty prop) {
        return (_property == prop) ? this : new AsExternalTypeSerializer(_idResolver, prop, _typePropertyName);
    }

    @Override
    public String getPropertyName() { return _typePropertyName; }

    @Override
    public As getTypeInclusion() { return As.EXTERNAL_PROPERTY; }

    /*
    /**********************************************************
    /* Writing prefixes
    /**********************************************************
     */
   
    @Override
    public void writeTypePrefixForObject(Object value, JsonGenerator gen) throws IOException {
        _writeObjectPrefix(value, gen);
    }

    @Override
    public void writeTypePrefixForObject(Object value, JsonGenerator gen, Class<?> type) throws IOException {
        _writeObjectPrefix(value, gen);
    }

    @Override
    public void writeTypePrefixForArray(Object value, JsonGenerator gen) throws IOException {
        _writeArrayPrefix(value, gen);
    }

    @Override
    public void writeTypePrefixForArray(Object value, JsonGenerator gen, Class<?> type) throws IOException {
        _writeArrayPrefix(value, gen);
    }

    @Override
    public void writeTypePrefixForScalar(Object value, JsonGenerator gen) throws IOException {
        _writeScalarPrefix(value, gen);
    }

    @Override
    public void writeTypePrefixForScalar(Object value, JsonGenerator gen, Class<?> type) throws IOException {
        _writeScalarPrefix(value, gen);
    }

    /*
    /**********************************************************
    /* Writing suffixes
    /**********************************************************
     */
   
   @Override
   public void writeTypeSuffixForObject(Object value, JsonGenerator gen) throws IOException {
       _writeObjectSuffix(value, gen, idFromValue(value));
   }

   @Override
   public void writeTypeSuffixForArray(Object value, JsonGenerator gen) throws IOException {
       _writeArraySuffix(value, gen, idFromValue(value));
   }
   
   @Override
   public void writeTypeSuffixForScalar(Object value, JsonGenerator gen) throws IOException {
       _writeScalarSuffix(value, gen, idFromValue(value));
   }

   /*
   /**********************************************************
   /* Writing with custom type id
   /**********************************************************
    */

   @Override
   public void writeCustomTypePrefixForScalar(Object value, JsonGenerator gen, String typeId) throws IOException {
       _writeScalarPrefix(value, gen);
   }
   
   @Override
   public void writeCustomTypePrefixForObject(Object value, JsonGenerator gen, String typeId) throws IOException {
       _writeObjectPrefix(value, gen);
   }
   
   @Override
   public void writeCustomTypePrefixForArray(Object value, JsonGenerator gen, String typeId) throws IOException {
       _writeArrayPrefix(value, gen);
   }

   @Override
   public void writeCustomTypeSuffixForScalar(Object value, JsonGenerator gen, String typeId) throws IOException {
       _writeScalarSuffix(value, gen, typeId);
   }

   @Override
   public void writeCustomTypeSuffixForObject(Object value, JsonGenerator gen, String typeId) throws IOException {
       _writeObjectSuffix(value, gen, typeId);
   }

   @Override
   public void writeCustomTypeSuffixForArray(Object value, JsonGenerator gen, String typeId) throws IOException {
       _writeArraySuffix(value, gen, typeId);
   }

   /*
   /**********************************************************
   /* Helper methods
   /**********************************************************
    */

   // nothing to wrap it with:
   protected final void _writeScalarPrefix(Object value, JsonGenerator gen) throws IOException { }

   protected final void _writeObjectPrefix(Object value, JsonGenerator gen) throws IOException {
       gen.writeStartObject();
   }

   protected final void _writeArrayPrefix(Object value, JsonGenerator gen) throws IOException {
       gen.writeStartArray();
   }
   
   protected final void _writeScalarSuffix(Object value, JsonGenerator gen, String typeId) throws IOException {
       if (typeId != null) {
           gen.writeStringField(_typePropertyName, typeId);
       }
   }
   
   protected final void _writeObjectSuffix(Object value, JsonGenerator gen, String typeId) throws IOException {
       gen.writeEndObject();
       if (typeId != null) {
           gen.writeStringField(_typePropertyName, typeId);
       }
   }

   protected final void _writeArraySuffix(Object value, JsonGenerator gen, String typeId) throws IOException {
       gen.writeEndArray();
       if (typeId != null) {
           gen.writeStringField(_typePropertyName, typeId);
       }
   }
}
