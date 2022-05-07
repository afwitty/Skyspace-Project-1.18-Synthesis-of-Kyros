package software.bernie.shadowed.fasterxml.jackson.databind.jsonschema;

import java.lang.reflect.Type;

import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
import software.bernie.shadowed.fasterxml.jackson.databind.JsonNode;
import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;

/**
 * Marker interface for schema-aware serializers.
 */
public interface SchemaAware
{
    /**
     * Get the representation of the schema to which this serializer will conform.
     *
     * @param provider The serializer provider.
     * @param typeHint A hint about the type.
     * @return <a href="http://json-schema.org/">Json-schema</a> for this serializer.
     */
    public JsonNode getSchema(SerializerProvider provider, Type typeHint)
        throws JsonMappingException;
    
    /**
     * Get the representation of the schema to which this serializer will conform.
     *
     * @param provider The serializer provider.
     * @param isOptional Is the type optional
     * @param typeHint A hint about the type.
     * @return <a href="http://json-schema.org/">Json-schema</a> for this serializer.
     */
    public JsonNode getSchema(SerializerProvider provider, Type typeHint, boolean isOptional)
        throws JsonMappingException;
}
