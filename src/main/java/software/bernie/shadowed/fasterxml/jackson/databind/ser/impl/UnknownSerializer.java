package software.bernie.shadowed.fasterxml.jackson.databind.ser.impl;

import java.io.IOException;
import java.lang.reflect.Type;

import software.bernie.shadowed.fasterxml.jackson.core.*;
import software.bernie.shadowed.fasterxml.jackson.databind.*;
import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;
import software.bernie.shadowed.fasterxml.jackson.databind.ser.std.StdSerializer;

@SuppressWarnings("serial")
public class UnknownSerializer
    extends StdSerializer<Object>
{
    public UnknownSerializer() {
        super(Object.class);
    }

    /**
     * @since 2.6
     */
    public UnknownSerializer(Class<?> cls) {
        super(cls, false);
    }
    
    @Override
    public void serialize(Object value, JsonGenerator gen, SerializerProvider provider) throws IOException
    {
        // 27-Nov-2009, tatu: As per [JACKSON-201] may or may not fail...
        if (provider.isEnabled(SerializationFeature.FAIL_ON_EMPTY_BEANS)) {
            failForEmpty(gen, value);
        }
        // But if it's fine, we'll just output empty JSON Object:
        gen.writeStartObject();
        gen.writeEndObject();
    }

    @Override
    public final void serializeWithType(Object value, JsonGenerator gen, SerializerProvider provider,
            TypeSerializer typeSer) throws IOException
    {
        if (provider.isEnabled(SerializationFeature.FAIL_ON_EMPTY_BEANS)) {
            failForEmpty(gen, value);
        }
        typeSer.writeTypePrefixForObject(value, gen);
        typeSer.writeTypeSuffixForObject(value, gen);
    }

    @Override
    public boolean isEmpty(SerializerProvider provider, Object value) {
        return true;
    }

    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint) throws JsonMappingException {
        return null;
    }
    
    @Override
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint)
        throws JsonMappingException
    { 
        visitor.expectAnyFormat(typeHint);
    }

    protected void failForEmpty(JsonGenerator gen, Object value) throws JsonMappingException {
        throw JsonMappingException.from(gen,
                "No serializer found for class "+value.getClass().getName()+" and no properties discovered to create BeanSerializer (to avoid exception, disable SerializationFeature.FAIL_ON_EMPTY_BEANS) )");
    }
}
