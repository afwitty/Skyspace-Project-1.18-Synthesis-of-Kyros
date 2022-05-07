package software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.ser.key;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
import software.bernie.shadowed.fasterxml.jackson.core.JsonProcessingException;
import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;

public class ZonedDateTimeKeySerializer extends JsonSerializer<ZonedDateTime> {

    public static final ZonedDateTimeKeySerializer INSTANCE = new ZonedDateTimeKeySerializer();

    private ZonedDateTimeKeySerializer() {
        // singleton
    }

    @Override
    public void serialize(ZonedDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException,
            JsonProcessingException {
        /*
         * Serialization of timezone data is unwanted (not ISO). Offset is kept, timezone info is thrown away here.
         *
         * Keeping timezone info is a new feature which needs to be implemented.
         */
        gen.writeFieldName(DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(value));
    }

}
