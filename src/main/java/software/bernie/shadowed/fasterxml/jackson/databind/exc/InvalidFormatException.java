package software.bernie.shadowed.fasterxml.jackson.databind.exc;

import software.bernie.shadowed.fasterxml.jackson.core.JsonLocation;
import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;

/**
 * Specialized sub-class of {@link JsonMappingException}
 * that is used when the underlying problem appears to be that
 * of bad formatting of a value to deserialize.
 * 
 * @since 2.1
 */
public class InvalidFormatException extends JsonMappingException
{
    private static final long serialVersionUID = 1L; // silly Eclipse, warnings

    /**
     * Underlying value that could not be deserialized into
     * target type, if available.
     */
    protected final Object _value;

    /**
     * Intended target type (type-erased class) that value could not
     * be deserialized into, if known.
     */
    protected final Class<?> _targetType;
    
    /*
    /**********************************************************
    /* Life-cycle
    /**********************************************************
     */

    /**
     * @deprecated Since 2.7 Use variant that takes {@link JsonParser}
     */
    @Deprecated // since 2.7
    public InvalidFormatException(String msg,
            Object value, Class<?> targetType)
    {
        super(null, msg);
        _value = value;
        _targetType = targetType;
    }

    /**
     * @deprecated Since 2.7 Use variant that takes {@link JsonParser}
     */
    @Deprecated // since 2.7
    public InvalidFormatException(String msg, JsonLocation loc,
            Object value, Class<?> targetType)
    {
        super(null, msg, loc);
        _value = value;
        _targetType = targetType;
    }

    /**
     * @since 2.7
     */
    public InvalidFormatException(JsonParser p,
            String msg, Object value, Class<?> targetType)
    {
        super(p, msg);
        _value = value;
        _targetType = targetType;
    }

    public static InvalidFormatException from(JsonParser p, String msg,
            Object value, Class<?> targetType)
    {
        return new InvalidFormatException(p, msg, value, targetType);
    }

    /*
    /**********************************************************
    /* Additional accessors
    /**********************************************************
     */

    /**
     * Accessor for checking source value (String, Number usually) that could not
     * be deserialized into target type ({@link #getTargetType}).
     * Note that value may not be available, depending on who throws the exception
     * and when.
     */
    public Object getValue() {
        return _value;
    }

    /**
     * Accessor for checking target type of value ({@link #getValue} that failed
     * to deserialize.
     * Note that type may not be available, depending on who throws the exception
     * and when.
     */
    public Class<?> getTargetType() {
        return _targetType;
    }
}
