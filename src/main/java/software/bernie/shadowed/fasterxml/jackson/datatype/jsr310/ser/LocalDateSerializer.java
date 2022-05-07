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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;

/**
 * Serializer for Java 8 temporal {@link LocalDate}s.
 *
 * @author Nick Williams
 * @since 2.2
 */
public class LocalDateSerializer extends JSR310FormattedSerializerBase<LocalDate>
{
    private static final long serialVersionUID = 1L;

    public static final LocalDateSerializer INSTANCE = new LocalDateSerializer();

    protected LocalDateSerializer() {
        super(LocalDate.class);
    }

    protected LocalDateSerializer(LocalDateSerializer base,
            Boolean useTimestamp, DateTimeFormatter dtf) {
        super(base, useTimestamp, dtf);
    }

    public LocalDateSerializer(DateTimeFormatter formatter) {
        super(LocalDate.class, formatter);
    }

    @Override
    protected LocalDateSerializer withFormat(Boolean useTimestamp, DateTimeFormatter dtf) {
        return new LocalDateSerializer(this, useTimestamp, dtf);
    }

    @Override
    public void serialize(LocalDate date, JsonGenerator generator, SerializerProvider provider) throws IOException
    {
        if (useTimestamp(provider)) {
            generator.writeStartArray();
            generator.writeNumber(date.getYear());
            generator.writeNumber(date.getMonthValue());
            generator.writeNumber(date.getDayOfMonth());
            generator.writeEndArray();
        } else {
            String str = (_formatter == null) ? date.toString() : date.format(_formatter);
            generator.writeString(str);
        }
    }
}
