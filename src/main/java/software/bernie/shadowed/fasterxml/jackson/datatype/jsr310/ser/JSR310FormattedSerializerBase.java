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

import java.lang.reflect.Type;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import software.bernie.shadowed.fasterxml.jackson.annotation.JsonFormat;
import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
import software.bernie.shadowed.fasterxml.jackson.databind.JsonNode;
import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
import software.bernie.shadowed.fasterxml.jackson.databind.SerializationFeature;
import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
import software.bernie.shadowed.fasterxml.jackson.databind.introspect.Annotated;
import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonArrayFormatVisitor;
import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonStringFormatVisitor;
import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonValueFormat;
import software.bernie.shadowed.fasterxml.jackson.databind.ser.ContextualSerializer;

/**
 * Base class that provides an array schema instead of scalar schema if
 * {@link SerializationFeature#WRITE_DATES_AS_TIMESTAMPS} is enabled.
 *
 * @author Nick Williams
 * @since 2.2
 */
abstract class JSR310FormattedSerializerBase<T>
    extends JSR310SerializerBase<T>
    implements ContextualSerializer
{
    private static final long serialVersionUID = 1L;

    /**
     * Flag that indicates that serialization must be done as the
     * Java timestamp, regardless of other settings.
     */
    protected final Boolean _useTimestamp;
    
    /**
     * Specific format to use, if not default format: non null value
     * also indicates that serialization is to be done as JSON String,
     * not numeric timestamp, unless {@link #_useTimestamp} is true.
     */
    protected final DateTimeFormatter _formatter;

    protected JSR310FormattedSerializerBase(Class<T> supportedType) {
        this(supportedType, null);
    }

    protected JSR310FormattedSerializerBase(Class<T> supportedType,
            DateTimeFormatter formatter) {
        super(supportedType);
        _useTimestamp = null;
        _formatter = formatter;
    }
    
    protected JSR310FormattedSerializerBase(JSR310FormattedSerializerBase<?> base,
        Boolean useTimestamp, DateTimeFormatter dtf)
    {            
        super(base.handledType());
        _useTimestamp = useTimestamp;
        _formatter = dtf;
    }

    protected abstract JSR310FormattedSerializerBase<?> withFormat(Boolean useTimestamp,
            DateTimeFormatter dtf);

    @Override
    public JsonSerializer<?> createContextual(SerializerProvider prov,
            BeanProperty property) throws JsonMappingException
    {
        if (property != null) {
            JsonFormat.Value format = prov.getAnnotationIntrospector().findFormat((Annotated)property.getMember());
            if (format != null) {
                Boolean useTimestamp = null;

               // Simple case first: serialize as numeric timestamp?
                JsonFormat.Shape shape = format.getShape();
                if (shape == JsonFormat.Shape.ARRAY || shape.isNumeric() ) {
                    useTimestamp = Boolean.TRUE;
                } else {
                    useTimestamp = (shape == JsonFormat.Shape.STRING) ? Boolean.FALSE : null;
                }
                DateTimeFormatter dtf = _formatter;

                // If not, do we have a pattern?
                if (format.hasPattern()) {
                    final String pattern = format.getPattern();
                    final Locale locale = format.hasLocale() ? format.getLocale() : prov.getLocale();
                    if (locale == null) {
                        dtf = DateTimeFormatter.ofPattern(pattern);
                    } else {
                        dtf = DateTimeFormatter.ofPattern(pattern, locale);
                    }
                    //Issue #69: For instant serializers/deserializers we need to configure the formatter with
                    //a time zone picked up from JsonFormat annotation, otherwise serialization might not work
                    if (format.hasTimeZone()) {
                        dtf = dtf.withZone(format.getTimeZone().toZoneId());
                    }
                }
                if (useTimestamp != _useTimestamp || dtf != _formatter) {
                    return withFormat(useTimestamp, dtf);
                }
            }
        }
        return this;
    }

    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint)
    {
        return createSchemaNode(
                provider.isEnabled(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS) ? "array" : "string", true
        );
    }

    @Override
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException
    {
        SerializerProvider provider = visitor.getProvider();
        boolean useTimestamp = (provider != null) && useTimestamp(provider);
        if (useTimestamp) {
            _acceptTimestampVisitor(visitor, typeHint);
        } else {
            JsonStringFormatVisitor v2 = visitor.expectStringFormat(typeHint);
            if (v2 != null) {
                v2.format(JsonValueFormat.DATE_TIME);
            }
        }
    }

    protected void _acceptTimestampVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint) throws JsonMappingException
    {
        // By default, most sub-types use JSON Array, so do this:
        JsonArrayFormatVisitor v2 = visitor.expectArrayFormat(typeHint);
        if (v2 != null) {
            v2.itemsFormat(JsonFormatTypes.INTEGER);
        }
    }

    protected boolean useTimestamp(SerializerProvider provider) {
        if (_useTimestamp != null) {
            return _useTimestamp.booleanValue();
        }
        // assume that explicit formatter definition implies use of textual format
        if (_formatter != null) { 
            return false;
        }
        return provider.isEnabled(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    protected boolean _useTimestampExplicitOnly(SerializerProvider provider) {
        if (_useTimestamp != null) {
            return _useTimestamp.booleanValue();
        }
        return false;
    }
}
