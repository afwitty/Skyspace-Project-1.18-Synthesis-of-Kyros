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

package software.bernie.shadowed.fasterxml.jackson.datatype.jsr310;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.MonthDay;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.Period;
import java.time.Year;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

import software.bernie.shadowed.fasterxml.jackson.databind.BeanDescription;
import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationConfig;
import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
import software.bernie.shadowed.fasterxml.jackson.databind.deser.ValueInstantiator;
import software.bernie.shadowed.fasterxml.jackson.databind.deser.ValueInstantiators;
import software.bernie.shadowed.fasterxml.jackson.databind.deser.std.StdValueInstantiator;
import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedClass;
import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMethod;
import software.bernie.shadowed.fasterxml.jackson.databind.module.SimpleModule;
import software.bernie.shadowed.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.DurationDeserializer;
import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.JSR310StringParsableDeserializer;
import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.MonthDayDeserializer;
import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.OffsetTimeDeserializer;
import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.YearDeserializer;
import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.YearMonthDeserializer;
import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.key.DurationKeyDeserializer;
import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.key.InstantKeyDeserializer;
import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.key.LocalDateKeyDeserializer;
import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.key.LocalDateTimeKeyDeserializer;
import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.key.LocalTimeKeyDeserializer;
import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.key.MonthDayKeyDeserializer;
import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.key.OffsetDateTimeKeyDeserializer;
import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.key.OffsetTimeKeyDeserializer;
import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.key.PeriodKeyDeserializer;
import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.key.YearKeyDeserializer;
import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.key.YearMothKeyDeserializer;
import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.key.ZoneIdKeyDeserializer;
import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.key.ZoneOffsetKeyDeserializer;
import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.deser.key.ZonedDateTimeKeyDeserializer;
import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.ser.DurationSerializer;
import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer;
import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.ser.MonthDaySerializer;
import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.ser.OffsetDateTimeSerializer;
import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.ser.OffsetTimeSerializer;
import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.ser.YearMonthSerializer;
import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.ser.YearSerializer;
import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeWithZoneIdSerializer;
import software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.ser.key.ZonedDateTimeKeySerializer;

/**
 * Older version of {@link JavaTimeModule} which was the default choice up to
 * Jackson 2.5, but was obsoleted in 2.6 by {@link JavaTimeModule}.
 * Functionality does not differ between the two modules (at least in 2.6),
 * so Javadocs for {@link JavaTimeModule} may be consulted for functionality
 * available.
 * The default settings do, however, such that
 *<ul>
 * <li>New {@link JavaTimeModule} uses same standard settings to default to
 *    serialization that does NOT use Timezone Ids, and instead only uses ISO-8601
 *    compliant Timezone offsets. Behavior may be changed using
 *    {@link software.bernie.shadowed.fasterxml.jackson.databind.SerializationFeature#WRITE_DATES_WITH_ZONE_ID}
 *  </li>
 *  <li>Old {@link JSR310Module} defaults to serialization WITH Timezone Ids (to support
 *    round-trippability of values when using JSR-310 types and Jackson)
 *   </li>
 * </ul>
 * Note that it is, then, possible to upgrade to {@link JavaTimeModule} by simply
 * reconfiguring it by enabling
 * {@link software.bernie.shadowed.fasterxml.jackson.databind.SerializationFeature#WRITE_DATES_WITH_ZONE_ID}.
 * This class is only retained to keep strict source and binary compatibility.
 *<p>
 * @author Nick Williams
 * @since 2.2.0
 * @see software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.ser.key.Jsr310NullKeySerializer
 * @see software.bernie.shadowed.fasterxml.jackson.datatype.jsr310.JavaTimeModule
 *
 * @deprecated Replaced by {@link JavaTimeModule} since Jackson 2.7, see above for
 *   details on differences in the default configuration.
 */
@Deprecated // since 2.5
public final class JSR310Module extends SimpleModule
{
    private static final long serialVersionUID = 1L;

    public JSR310Module()
    {
        super(PackageVersion.VERSION);

        // First deserializers

        // // Instant variants:
        addDeserializer(Instant.class, InstantDeserializer.INSTANT);
        addDeserializer(OffsetDateTime.class, InstantDeserializer.OFFSET_DATE_TIME);
        addDeserializer(ZonedDateTime.class, InstantDeserializer.ZONED_DATE_TIME);
        
        // // Other deserializers
        addDeserializer(Duration.class, DurationDeserializer.INSTANCE);
        addDeserializer(LocalDateTime.class, LocalDateTimeDeserializer.INSTANCE);
        addDeserializer(LocalDate.class, LocalDateDeserializer.INSTANCE);
        addDeserializer(LocalTime.class, LocalTimeDeserializer.INSTANCE);
        addDeserializer(MonthDay.class, MonthDayDeserializer.INSTANCE);
        addDeserializer(OffsetTime.class, OffsetTimeDeserializer.INSTANCE);
        addDeserializer(Period.class, JSR310StringParsableDeserializer.PERIOD);
        addDeserializer(Year.class, YearDeserializer.INSTANCE);
        addDeserializer(YearMonth.class, YearMonthDeserializer.INSTANCE);
        addDeserializer(ZoneId.class, JSR310StringParsableDeserializer.ZONE_ID);
        addDeserializer(ZoneOffset.class, JSR310StringParsableDeserializer.ZONE_OFFSET);

        // then serializers:
        addSerializer(Duration.class, DurationSerializer.INSTANCE);
        addSerializer(Instant.class, InstantSerializer.INSTANCE);
        addSerializer(LocalDateTime.class, LocalDateTimeSerializer.INSTANCE);
        addSerializer(LocalDate.class, LocalDateSerializer.INSTANCE);
        addSerializer(LocalTime.class, LocalTimeSerializer.INSTANCE);
        addSerializer(MonthDay.class, MonthDaySerializer.INSTANCE);
        addSerializer(OffsetDateTime.class, OffsetDateTimeSerializer.INSTANCE);
        addSerializer(OffsetTime.class, OffsetTimeSerializer.INSTANCE);
        addSerializer(Period.class, new ToStringSerializer(Period.class));
        addSerializer(Year.class, YearSerializer.INSTANCE);
        addSerializer(YearMonth.class, YearMonthSerializer.INSTANCE);

        /* 27-Jun-2015, tatu: This is the real difference to the new
         *  {@link JavaTimeModule}: default is to include timezone id, not just offset
         */
        addSerializer(ZonedDateTime.class, ZonedDateTimeWithZoneIdSerializer.INSTANCE);

        // note: actual concrete type is `ZoneRegion`, but that's not visible:
        addSerializer(ZoneId.class, new ToStringSerializer(ZoneId.class));

        addSerializer(ZoneOffset.class, new ToStringSerializer(ZoneOffset.class));

        // key serializers
        addKeySerializer(ZonedDateTime.class, ZonedDateTimeKeySerializer.INSTANCE);

        // key deserializers
        addKeyDeserializer(Duration.class, DurationKeyDeserializer.INSTANCE);
        addKeyDeserializer(Instant.class, InstantKeyDeserializer.INSTANCE);
        addKeyDeserializer(LocalDateTime.class, LocalDateTimeKeyDeserializer.INSTANCE);
        addKeyDeserializer(LocalDate.class, LocalDateKeyDeserializer.INSTANCE);
        addKeyDeserializer(LocalTime.class, LocalTimeKeyDeserializer.INSTANCE);
        addKeyDeserializer(MonthDay.class, MonthDayKeyDeserializer.INSTANCE);
        addKeyDeserializer(OffsetDateTime.class, OffsetDateTimeKeyDeserializer.INSTANCE);
        addKeyDeserializer(OffsetTime.class, OffsetTimeKeyDeserializer.INSTANCE);
        addKeyDeserializer(Period.class, PeriodKeyDeserializer.INSTANCE);
        addKeyDeserializer(Year.class, YearKeyDeserializer.INSTANCE);
        addKeyDeserializer(YearMonth.class, YearMothKeyDeserializer.INSTANCE);
        addKeyDeserializer(ZonedDateTime.class, ZonedDateTimeKeyDeserializer.INSTANCE);
        addKeyDeserializer(ZoneId.class, ZoneIdKeyDeserializer.INSTANCE);
        addKeyDeserializer(ZoneOffset.class, ZoneOffsetKeyDeserializer.INSTANCE);
    }

    @Override
    public void setupModule(SetupContext context) {
        super.setupModule(context);
        context.addValueInstantiators(new ValueInstantiators.Base() {
            @Override
            public ValueInstantiator findValueInstantiator(DeserializationConfig config,
                    BeanDescription beanDesc, ValueInstantiator defaultInstantiator)
            {
                JavaType type = beanDesc.getType();
                Class<?> raw = type.getRawClass();
                // 15-May-2015, tatu: In theory not safe, but in practice we do need to do "fuzzy" matching
                //    because we will (for now) be getting a subtype, but in future may want to downgrade
                //    to the common base type. Even more, serializer may purposefully force use of base type.
                //    So... in practice it really should always work, in the end. :)
                if (ZoneId.class.isAssignableFrom(raw)) {
                    // let's assume we should be getting "empty" StdValueInstantiator here:
                    if (defaultInstantiator instanceof StdValueInstantiator) {
                        StdValueInstantiator inst = (StdValueInstantiator) defaultInstantiator;
                        // one further complication: we need ZoneId info, not sub-class
                        AnnotatedClass ac;
                        if (raw == ZoneId.class) {
                            ac = beanDesc.getClassInfo();
                        } else {
                            // we don't need Annotations, so constructing directly is fine here
                            // even if it's not generally recommended
                            ac = AnnotatedClass.construct(config.constructType(ZoneId.class), config);
                        }
                        if (!inst.canCreateFromString()) {
                            AnnotatedMethod factory = _findFactory(ac, "of", String.class);
                            if (factory != null) {
                                inst.configureFromStringCreator(factory);
                            }
                            // otherwise... should we indicate an error?
                        }
                        //return ZoneIdInstantiator.construct(config, beanDesc, defaultInstantiator);
                    }
                }
                return defaultInstantiator;
            }
        });
    }

    // For 
    protected AnnotatedMethod _findFactory(AnnotatedClass cls, String name, Class<?>... argTypes)
    {
        final int argCount = argTypes.length;
        for (AnnotatedMethod method : cls.getStaticMethods()) {
            if (!name.equals(method.getName())
                || (method.getParameterCount() != argCount)) {
                continue;
            }
            for (int i = 0; i < argCount; ++i) {
                Class<?> argType = method.getParameter(i).getRawType();
                if (!argType.isAssignableFrom(argTypes[i])) {
                    continue;
                }
            }
            return method;
        }
        return null;
    }
}
