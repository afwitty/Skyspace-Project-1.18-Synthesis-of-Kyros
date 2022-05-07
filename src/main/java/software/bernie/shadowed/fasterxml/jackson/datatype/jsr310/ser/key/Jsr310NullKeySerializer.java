package software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.ser.key;

import java.io.IOException;
import java.util.Map;

import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;

/**
 * This class is to be used in case {@code null} keys are needed to be serialized in a {@link Map} with Java 8 temporal keys. By default the
 * {@code null} key is not supported by jackson, the serializer needs to be registered manually.
 *
 * @author Zoltan Kiss
 * @since 2.6
 */
public class Jsr310NullKeySerializer extends JsonSerializer<Object> {

    public static final String NULL_KEY = "";

    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers) throws IOException
    {
        if (value != null) {
            throw JsonMappingException.from(gen,
                    "Jsr310NullKeySerializer is only for serializing null values.");
        }
        gen.writeFieldName(NULL_KEY);
    }

}
