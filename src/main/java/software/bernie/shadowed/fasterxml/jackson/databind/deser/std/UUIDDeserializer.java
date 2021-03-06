package software.bernie.shadowed.fasterxml.jackson.databind.deser.std;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

import software.bernie.shadowed.fasterxml.jackson.core.Base64Variants;
import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
import software.bernie.shadowed.fasterxml.jackson.databind.JsonMappingException;
import software.bernie.shadowed.fasterxml.jackson.databind.exc.InvalidFormatException;

public class UUIDDeserializer extends FromStringDeserializer<UUID>
{
    private static final long serialVersionUID = 1L;

    final static int[] HEX_DIGITS = new int[127];
    static {
        Arrays.fill(HEX_DIGITS, -1);
        for (int i = 0; i < 10; ++i) { HEX_DIGITS['0' + i] = i; }
        for (int i = 0; i < 6; ++i) {
            HEX_DIGITS['a' + i] = 10 + i;
            HEX_DIGITS['A' + i] = 10 + i;
        }
    }

    public UUIDDeserializer() { super(UUID.class); }

    @Override
    protected UUID _deserialize(String id, DeserializationContext ctxt) throws IOException
    {
        // Adapted from java-uuid-generator (https://github.com/cowtowncoder/java-uuid-generator)
        // which is 5x faster than UUID.fromString(value), as oper "ManualReadPerfWithUUID"
        if (id.length() != 36) {
            /* 14-Sep-2013, tatu: One trick we do allow, Base64-encoding, since we know
             *   length it must have...
             */
            if (id.length() == 24) {
                byte[] stuff = Base64Variants.getDefaultVariant().decode(id);
                return _fromBytes(stuff, ctxt);
            }
            _badFormat(id, ctxt);
        }

        // verify hyphens first:
        if ((id.charAt(8) != '-') || (id.charAt(13) != '-')
                || (id.charAt(18) != '-') || (id.charAt(23) != '-')) {
            _badFormat(id, ctxt);
        }
        long l1 = intFromChars(id, 0, ctxt);
        l1 <<= 32;
        long l2 = ((long) shortFromChars(id, 9, ctxt)) << 16;
        l2 |= shortFromChars(id, 14, ctxt);
        long hi = l1 + l2;

        int i1 = (shortFromChars(id, 19, ctxt) << 16) | shortFromChars(id, 24, ctxt);
        l1 = i1;
        l1 <<= 32;
        l2 = intFromChars(id, 28, ctxt);
        l2 = (l2 << 32) >>> 32; // sign removal, Java-style. Ugh.
        long lo = l1 | l2;

        return new UUID(hi, lo);
    }
    
    @Override
    protected UUID _deserializeEmbedded(Object ob, DeserializationContext ctxt) throws IOException
    {
        if (ob instanceof byte[]) {
            return _fromBytes((byte[]) ob, ctxt);
        }
        super._deserializeEmbedded(ob, ctxt);
        return null; // never gets here
    }

    private void _badFormat(String uuidStr, DeserializationContext ctxt)
        throws JsonMappingException
    {
        throw InvalidFormatException.from(ctxt.getParser(),
                String.format("UUID has to be represented by standard 36-char representation: input String '%s'",
                        uuidStr),
                uuidStr, handledType());
    }
    
    static int intFromChars(String str, int index, DeserializationContext ctxt) throws JsonMappingException {
        return (byteFromChars(str, index, ctxt) << 24)
                + (byteFromChars(str, index+2, ctxt) << 16)
                + (byteFromChars(str, index+4, ctxt) << 8)
                + byteFromChars(str, index+6, ctxt);
    }
    
    static int shortFromChars(String str, int index, DeserializationContext ctxt) throws JsonMappingException {
        return (byteFromChars(str, index, ctxt) << 8) + byteFromChars(str, index+2, ctxt);
    }
    
    static int byteFromChars(String str, int index, DeserializationContext ctxt) throws JsonMappingException
    {
        final char c1 = str.charAt(index);
        final char c2 = str.charAt(index+1);

        if (c1 <= 127 && c2 <= 127) {
            int hex = (HEX_DIGITS[c1] << 4) | HEX_DIGITS[c2];
            if (hex >= 0) {
                return hex;
            }
        }
        if (c1 > 127 || HEX_DIGITS[c1] < 0) {
            return _badChar(str, index, ctxt, c1);
        }
        return _badChar(str, index+1, ctxt, c2);
    }

    static int _badChar(String uuidStr, int index, DeserializationContext ctxt, char c) throws JsonMappingException {
        String msg = String.format(
"Non-hex character '%c' (value 0x%s), not valid for UUID String: input String '%s'",
        c, Integer.toHexString(c), uuidStr);
        throw InvalidFormatException.from(ctxt.getParser(), msg, uuidStr, UUID.class);
    }

    private UUID _fromBytes(byte[] bytes, DeserializationContext ctxt) throws JsonMappingException {
        if (bytes.length != 16) {
            throw InvalidFormatException.from(ctxt.getParser(),
                    "Can only construct UUIDs from byte[16]; got "+bytes.length+" bytes",
                    bytes, handledType());
        }
        return new UUID(_long(bytes, 0), _long(bytes, 8));
    }

    private static long _long(byte[] b, int offset) {
        long l1 = ((long) _int(b, offset)) << 32;
        long l2 = _int(b, offset+4);
        // faster to just do it than check if it has sign
        l2 = (l2 << 32) >>> 32; // to get rid of sign
        return l1 | l2;
    }

    private static int _int(byte[] b, int offset) {
        return (b[offset] << 24) | ((b[offset+1] & 0xFF) << 16) | ((b[offset+2] & 0xFF) << 8) | (b[offset+3] & 0xFF);
    }
}