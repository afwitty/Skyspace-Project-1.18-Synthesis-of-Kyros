package software.bernie.shadowed.fasterxml.jackson.databind.exc;

import java.util.*;

import software.bernie.shadowed.fasterxml.jackson.core.JsonLocation;
import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;

/**
 * Specialized {@link JsonMappingException} sub-class specifically used
 * to indicate problems due to encountering a JSON property that could
 * not be mapped to an Object property (via getter, constructor argument
 * or field).
 */
public class UnrecognizedPropertyException
    extends PropertyBindingException
{
    private static final long serialVersionUID = 1L;

    public UnrecognizedPropertyException(JsonParser p, String msg, JsonLocation loc,
            Class<?> referringClass, String propName,
            Collection<Object> propertyIds)
    {
        super(p, msg, loc, referringClass, propName, propertyIds);
    }

    /**
     * @deprecated Since 2.7
     */
    @Deprecated // since 2.7
    public UnrecognizedPropertyException(String msg, JsonLocation loc,
            Class<?> referringClass, String propName,
            Collection<Object> propertyIds)
    {
        super(msg, loc, referringClass, propName, propertyIds);
    }

    /**
     * Factory method used for constructing instances of this exception type.
     * 
     * @param p Underlying parser used for reading input being used for data-binding
     * @param fromObjectOrClass Reference to either instance of problematic type (
     *    if available), or if not, type itself
     * @param propertyName Name of unrecognized property
     * @param propertyIds (optional, null if not available) Set of properties that
     *    type would recognize, if completely known: null if set can not be determined.
     */
    public static UnrecognizedPropertyException from(JsonParser p,
            Object fromObjectOrClass, String propertyName,
            Collection<Object> propertyIds)
    {
        if (fromObjectOrClass == null) {
            throw new IllegalArgumentException();
        }
        Class<?> ref;
        if (fromObjectOrClass instanceof Class<?>) {
            ref = (Class<?>) fromObjectOrClass;
        } else {
            ref = fromObjectOrClass.getClass();
        }
        String msg = "Unrecognized field \""+propertyName+"\" (class "+ref.getName()+"), not marked as ignorable";
        UnrecognizedPropertyException e = new UnrecognizedPropertyException(p, msg,
                p.getCurrentLocation(), ref, propertyName, propertyIds);
        // but let's also ensure path includes this last (missing) segment
        e.prependPath(fromObjectOrClass, propertyName);
        return e;
    }
}