package software.bernie.shadowed.fasterxml.jackson.databind.ser.std;

import java.io.IOException;
import java.text.DateFormat;
import java.util.*;

import software.bernie.shadowed.fasterxml.jackson.core.*;
import software.bernie.shadowed.fasterxml.jackson.databind.*;
import software.bernie.shadowed.fasterxml.jackson.databind.annotation.JacksonStdImpl;

/**
 * For efficiency, we will serialize Dates as longs, instead of
 * potentially more readable Strings.
 */
@JacksonStdImpl
@SuppressWarnings("serial")
public class DateSerializer
    extends DateTimeSerializerBase<Date>
{
    /**
     * Default instance that is used when no contextual configuration
     * is needed.
     */
    public static final DateSerializer instance = new DateSerializer();
    
    public DateSerializer() {
        this(null, null);
    }
        
    public DateSerializer(Boolean useTimestamp, DateFormat customFormat) {
        super(Date.class, useTimestamp, customFormat);
    }

    @Override
    public DateSerializer withFormat(Boolean timestamp, DateFormat customFormat) {
        return new DateSerializer(timestamp, customFormat);
    }

    @Override
    protected long _timestamp(Date value) {
        return (value == null) ? 0L : value.getTime();
    }

    @Override
    public void serialize(Date value, JsonGenerator gen, SerializerProvider provider) throws IOException
    {
        if (_asTimestamp(provider)) {
            gen.writeNumber(_timestamp(value));
        } else if (_customFormat != null) {
            // 21-Feb-2011, tatu: not optimal, but better than alternatives:
            synchronized (_customFormat) {
                gen.writeString(_customFormat.format(value));
            }
        } else {
            provider.defaultSerializeDateValue(value, gen);
        }
    }
}
