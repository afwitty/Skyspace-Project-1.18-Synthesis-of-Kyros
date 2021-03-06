/*
 * Copyright 2013 FasterXML.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the license for the specific language governing permissions and
 * limitations under the license.
 */

package software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.ser;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;

import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
import software.bernie.shadowed.fasterxml.jackson.databind.SerializationFeature;
import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;

/**
 * Serializer for Java 8 temporal {@link LocalDateTime}s.
 *
 * @author Nick Williams
 * @since 2.2
 */
public class LocalDateTimeSerializer extends JSR310FormattedSerializerBase<LocalDateTime>
{
    private static final long serialVersionUID = 1L;

    public static final LocalDateTimeSerializer INSTANCE = new LocalDateTimeSerializer();
    
    protected LocalDateTimeSerializer() {
        this(null);
    }

    public LocalDateTimeSerializer(DateTimeFormatter f) {
        super(LocalDateTime.class, f);
    }

    private LocalDateTimeSerializer(LocalDateTimeSerializer base, Boolean useTimestamp, DateTimeFormatter f) {
        super(base, useTimestamp, f);
    }

    @Override
    protected JSR310FormattedSerializerBase<LocalDateTime> withFormat(Boolean useTimestamp, DateTimeFormatter f) {
        return new LocalDateTimeSerializer(this, useTimestamp, f);
    }

    @Override
    public void serialize(LocalDateTime value, JsonGenerator g, SerializerProvider provider)
        throws IOException
    {
        if (useTimestamp(provider)) {
            g.writeStartArray();
            g.writeNumber(value.getYear());
            g.writeNumber(value.getMonthValue());
            g.writeNumber(value.getDayOfMonth());
            g.writeNumber(value.getHour());
            g.writeNumber(value.getMinute());
            if (value.getSecond() > 0 || value.getNano() > 0) {
                g.writeNumber(value.getSecond());
                if(value.getNano() > 0) {
                    if (provider.isEnabled(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS))
                        g.writeNumber(value.getNano());
                    else
                        g.writeNumber(value.get(ChronoField.MILLI_OF_SECOND));
                }
            }
            g.writeEndArray();
        } else {
            DateTimeFormatter dtf = _formatter;
            if (dtf == null) {
                dtf = _defaultFormatter();
            }
            g.writeString(value.format(dtf));
        }
    }

    // since 2.7: TODO in 2.8; change to use per-type defaulting
    protected DateTimeFormatter _defaultFormatter() {
        return DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    }
}
