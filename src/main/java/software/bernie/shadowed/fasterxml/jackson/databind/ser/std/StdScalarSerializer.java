package software.bernie.shadowed.fasterxml.jackson.databind.ser.std;

import java.io.IOException;
import java.lang.reflect.Type;

import software.bernie.shadowed.fasterxml.jackson.core.*;
import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
import software.bernie.shadowed.fasterxml.jackson.databind.JsonNode;
import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
import software.bernie.shadowed.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatVisitorWrapper;
import software.bernie.shadowed.fasterxml.jackson.databind.jsontype.TypeSerializer;

@SuppressWarnings("serial")
public abstract class StdScalarSerializer<T>
    extends StdSerializer<T>
{
    protected StdScalarSerializer(Class<T> t) {
        super(t);
    }

    /**
     * Alternate constructor that is (alas!) needed to work
     * around kinks of generic type handling
     */
    @SuppressWarnings("unchecked")
    protected StdScalarSerializer(Class<?> t, boolean dummy) {
        super((Class<T>) t);
    }
    
    /**
     * Default implementation will write type prefix, call regular serialization
     * method (since assumption is that value itself does not need JSON
     * Array or Object start/end markers), and then write type suffix.
     * This should work for most cases; some sub-classes may want to
     * change this behavior.
     */
    @Override
    public void serializeWithType(T value, JsonGenerator g, SerializerProvider provider,
            TypeSerializer typeSer) throws IOException
    {
        typeSer.writeTypePrefixForScalar(value, g);
        serialize(value, g, provider);
        typeSer.writeTypeSuffixForScalar(value, g);
    }

    @Override
    public JsonNode getSchema(SerializerProvider provider, Type typeHint)
        throws JsonMappingException
    {
        return createSchemaNode("string", true);
    }
    
    @Override
    public void acceptJsonFormatVisitor(JsonFormatVisitorWrapper visitor, JavaType typeHint)
        throws JsonMappingException
    {
        // 13-Sep-2013, tatu: Let's assume it's usually a String, right?
        visitStringFormat(visitor, typeHint);
    }
}
