package software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.ser;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
import software.bernie.shadowed.fasterxml.jackson.databind.SerializationFeature;
import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;

public class ZonedDateTimeSerializer extends InstantSerializerBase<ZonedDateTime> {
    private static final long serialVersionUID = 1L;

    public static final ZonedDateTimeSerializer INSTANCE = new ZonedDateTimeSerializer();

    protected ZonedDateTimeSerializer() {
        // ISO_ZONED_DATE_TIME is not the ISO format, it is an extension of it
        this(DateTimeFormatter.ISO_OFFSET_DATE_TIME);
    }

    public ZonedDateTimeSerializer(DateTimeFormatter formatter) {
        super(ZonedDateTime.class, dt -> dt.toInstant().toEpochMilli(),
              ZonedDateTime::toEpochSecond, ZonedDateTime::getNano,
              formatter);
    }

    private ZonedDateTimeSerializer(ZonedDateTimeSerializer base,
            Boolean useTimestamp, DateTimeFormatter formatter) {
        super(base, useTimestamp, formatter);
    }

    @Override
    protected JSR310FormattedSerializerBase<?> withFormat(Boolean useTimestamp, DateTimeFormatter formatter) {
        return new ZonedDateTimeSerializer(this, useTimestamp, formatter);
    }

    @Override
    public void serialize(ZonedDateTime value, JsonGenerator generator, SerializerProvider provider) throws IOException {
        if (!useTimestamp(provider) &&
                provider.isEnabled(SerializationFeature.WRITE_DATES_WITH_ZONE_ID)) {
            // write with zone
            generator.writeString(DateTimeFormatter.ISO_ZONED_DATE_TIME.format(value));
            return;
        }
        // else
        super.serialize(value, generator, provider);
    }

}
