package software.bernie.shadowed.fasterxml.jackson.databind.ser.std;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicReference;

import software.bernie.shadowed.fasterxml.jackson.core.*;
import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
import software.bernie.shadowed.fasterxml.jackson.databind.JsonNode;
import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializable;
import software.bernie.shadowed.fasterxml.jackson.databind.ObjectMapper;
import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
import software.bernie.shadowed.fasterxml.jackson.databind.annotation.JacksonStdImpl;
import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import software.bernie.shadowed.fasterxml.jackson.databind.jsonschema.JsonSerializableSchema;
import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
import software.bernie.shadowed.fasterxml.jackson.databind.node.ObjectNode;
import software.bernie.shadowed.fasterxml.jackson.databind.type.TypeFactory;

/**
 * Generic handler for types that implement {@link JsonSerializable}.
 *<p>
 * Note: given that this is used for anything that implements
 * interface, can not be checked for direct class equivalence.
 */
@JacksonStdImpl
@SuppressWarnings("serial")
public class SerializableSerializer
    extends StdSerializer<JsonSerializable>
{
    public final static SerializableSerializer instance = new SerializableSerializer();

    // Ugh. Should NOT need this...
    private final static AtomicReference<ObjectMapper> _mapperReference = new AtomicReference<ObjectMapper>();
    
    protected SerializableSerializer() { super(JsonSerializable.class); }

    @Override
    public boolean isEmpty(SerializerProvider serializers, JsonSerializable value) {
        if (value instanceof JsonSerializable.Base) {
            return ((JsonSerializable.Base) value).isEmpty(serializers);
        }
        return false;
    }

    @Override
    public void serialize(JsonSerializable value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        value.serialize(gen, serializers);
    }

    @Override
    public final void serializeWithType(JsonSerializable value, JsonGenerator gen, SerializerProvider serializers,
            TypeSerializer typeSer) throws IOException {
        value.serializeWithType(gen, serializers, typeSer);
    }
    
    @Override
    @SuppressWarnings("deprecation")
    public JsonNode getSchema(SerializerProvider provider, Type typeHint)
        throws JsonMappingException
    {
        ObjectNode objectNode = createObjectNode();
        String schemaType = "any";
        String objectProperties = null;
        String itemDefinition = null;
        if (typeHint != null) {
            Class<?> rawClass = TypeFactory.rawClass(typeHint);
            if (rawClass.isAnnotationPresent(JsonSerializableSchema.class)) {
                JsonSerializableSchema schemaInfo = rawClass.getAnnotation(JsonSerializableSchema.class);
                schemaType = schemaInfo.schemaType();
                if (!JsonSerializableSchema.NO_VALUE.equals(schemaInfo.schemaObjectPropertiesDefinition())) {
                    objectProperties = schemaInfo.schemaObjectPropertiesDefinition();
                }
                if (!JsonSerializableSchema.NO_VALUE.equals(schemaInfo.schemaItemDefinition())) {
                    itemDefinition = schemaInfo.schemaItemDefinition();
                }
            }
        }
        /* 19-Mar-2012, tatu: geez, this is butt-ugly abonimation of code...
         *    really, really should not require back ref to an ObjectMapper.
         */
        objectNode.put("type", schemaType);
        if (objectProperties != null) {
            try {
                objectNode.set("properties", _getObjectMapper().readTree(objectProperties));
            } catch (IOException e) {
                throw JsonMappingException.from(provider,
                        "Failed to parse @JsonSerializableSchema.schemaObjectPropertiesDefinition value");
            }
        }
        if (itemDefinition != null) {
            try {
                objectNode.set("items", _getObjectMapper().readTree(itemDefinition));
            } catch (IOException e) {
                throw JsonMappingException.from(provider,
                        "Failed to parse @JsonSerializableSchema.schemaItemDefinition value");
            }
        }
        // always optional, no need to specify:
        //objectNode.put("required", false);
        return objectNode;
    }
    
    private final static synchronized ObjectMapper _getObjectMapper()
    {
        ObjectMapper mapper = _mapperReference.get();
        if (mapper == null) {
            mapper = new ObjectMapper();
            _mapperReference.set(mapper);
        }
        return mapper;
    }

    @Override
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint)
        throws JsonMappingException
    {
        visitor.expectAnyFormat(typeHint);
    }
}
