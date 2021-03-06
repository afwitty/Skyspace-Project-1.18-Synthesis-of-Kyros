package software.bernie.shadowed.fasterxml.jackson.databind.ser.impl;

import java.io.IOException;

import software.bernie.shadowed.fasterxml.jackson.annotation.ObjectIdGenerator;
import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
import software.bernie.shadowed.fasterxml.jackson.core.SerializableString;
import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;

/**
 * Simple value container used to keep track of Object Ids during
 * serialization.
 */
public final class WritableObjectId
{
    public final ObjectIdGenerator<?> generator;

    public Object id;

    /**
     * Marker to denote whether Object Id value has been written as part of an Object,
     * to be referencible. Remains false when forward-reference is written.
     */
    protected boolean idWritten = false;

    public WritableObjectId(ObjectIdGenerator<?> generator) {
        this.generator = generator;
    }

    public boolean writeAsId(JsonGenerator gen, SerializerProvider provider, ObjectIdWriter w) throws IOException
    {
        if ((id != null) && (idWritten || w.alwaysAsId)) {
            // 03-Aug-2013, tatu: Prefer Native Object Ids if available
            if (gen.canWriteObjectId()) {
                gen.writeObjectRef(String.valueOf(id));
            } else {
                w.serializer.serialize(id, gen, provider);
            }
            return true;
        }
        return false;
    }
    
    public Object generateId(Object forPojo) {
        // 04-Jun-2016, tatu: As per [databind#1255], need to consider possibility of
        //    id being generated for "alwaysAsId", but not being written as POJO; regardless,
        //    need to use existing id if there is one:
        if (id == null) {
            id = generator.generateId(forPojo);
        }
        return id;
    }

    /**
     * Method called to output Object Id as specified.
     */
    public void writeAsField(JsonGenerator gen, SerializerProvider provider,
            ObjectIdWriter w) throws IOException
    {
        idWritten = true;

        // 03-Aug-2013, tatu: Prefer Native Object Ids if available
        if (gen.canWriteObjectId()) {
            // Need to assume String(ified) ids, for now... could add 'long' variant?
            gen.writeObjectId(String.valueOf(id));
            return;
        }
        
        SerializableString name = w.propertyName;
        if (name != null) {
            gen.writeFieldName(name);
            w.serializer.serialize(id, gen, provider);
        }
    }
}
