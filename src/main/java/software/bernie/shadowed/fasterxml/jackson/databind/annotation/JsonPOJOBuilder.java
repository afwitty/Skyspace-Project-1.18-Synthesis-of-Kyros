package software.bernie.shadowed.fasterxml.jackson.databind.annotation;

import java.lang.annotation.*;

/**
 * Annotation used to configure details of a Builder class:
 * instances of which are used as Builders for deserialized
 * POJO values, instead of POJOs being instantiated using
 * constructors or factory methods.
 * Note that this annotation is NOT used to define what is
 * the Builder class for a POJO: rather, this is determined
 * by {@link JsonDeserialize#builder} property of {@link JsonDeserialize}.
 *<p>
 * Annotation is typically used if the naming convention
 * of a Builder class is different from defaults:
 *<ul>
 * </ul>
 * 
 * @since 2.0
 */
@Target({ElementType.ANNOTATION_TYPE, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@software.bernie.shadowed.fasterxml.jackson.annotation.JacksonAnnotation
public @interface JsonPOJOBuilder
{
	/**
	 * Property to use for re-defining which zero-argument method
	 * is considered the actual "build-method": method called after
	 * all data has been bound, and the actual instance needs to
	 * be instantiated.
	 *<p>
	 * Default value is "build".
	 */
	public String buildMethodName() default "build";

	/**
	 * Property used for (re)defining name prefix to use for
	 * auto-detecting "with-methods": methods that are similar to
	 * "set-methods" (in that they take an argument), but that
	 * may also return the new builder instance to use
	 * (which may be 'this', or a new modified builder instance).
	 * Note that in addition to this prefix, it is also possible
	 * to use {@link software.bernie.shadowed.fasterxml.jackson.annotation.JsonProperty}
	 * annotation to indicate "with-methods" (as well as
	 * {@link software.bernie.shadowed.fasterxml.jackson.annotation.JsonSetter}).
	 *<p>
	 * Default value is "with", so that method named "withValue()"
	 * would be used for binding JSON property "value" (using type
	 * indicated by the argument; or one defined with annotations.
	 */
	public String withPrefix() default "with";

    /*
    /**********************************************************
    /* Helper classes
    /**********************************************************
     */
	
	/**
	 * Simple value container for containing values read from
	 * {@link JsonPOJOBuilder} annotation instance.
	 */
	public class Value
	{
        public final String buildMethodName;
	    public final String withPrefix;

	    public Value(JsonPOJOBuilder ann)
	    {
	        buildMethodName = ann.buildMethodName();
	        withPrefix = ann.withPrefix();
	    }
	}
}
