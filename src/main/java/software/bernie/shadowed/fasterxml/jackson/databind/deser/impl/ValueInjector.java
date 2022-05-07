package software.bernie.shadowed.fasterxml.jackson.databind.deser.impl;

import java.io.IOException;

import software.bernie.shadowed.fasterxml.jackson.databind.BeanProperty;
import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
import software.bernie.shadowed.fasterxml.jackson.databind.JavaType;
import software.bernie.shadowed.fasterxml.jackson.databind.PropertyMetadata;
import software.bernie.shadowed.fasterxml.jackson.databind.PropertyName;
import software.bernie.shadowed.fasterxml.jackson.databind.introspect.AnnotatedMember;
import software.bernie.shadowed.fasterxml.jackson.databind.util.Annotations;

/**
 * Class that encapsulates details of value injection that occurs before
 * deserialization of a POJO. Details include information needed to find
 * injectable value (logical id) as well as method used for assigning
 * value (setter or field)
 */
public class ValueInjector
    extends BeanProperty.Std
{
    /**
     * Identifier used for looking up value to inject
     */
    protected final Object _valueId;

    public ValueInjector(PropertyName propName, JavaType type,
            Annotations contextAnnotations, AnnotatedMember mutator,
            Object valueId)
    {
        super(propName, type, null, contextAnnotations, mutator,
                PropertyMetadata.STD_OPTIONAL);
        _valueId = valueId;
    }

    @Deprecated // since 2.3
    public ValueInjector(String propName, JavaType type,
            Annotations contextAnnotations, AnnotatedMember mutator,
            Object valueId)
    {
        this(new PropertyName(propName), type, contextAnnotations, mutator, valueId);
    }

    public Object findValue(DeserializationContext context, Object beanInstance)
    {
        return context.findInjectableValue(_valueId, this, beanInstance);
    }
    
    public void inject(DeserializationContext context, Object beanInstance)
        throws IOException
    {
        _member.setValue(beanInstance, findValue(context, beanInstance));
    }
}