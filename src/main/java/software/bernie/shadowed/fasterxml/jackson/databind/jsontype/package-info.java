/**
 * Package that contains interfaces that define how to implement
 * functionality for dynamically resolving type during deserialization.
 * This is needed for complete handling of polymorphic types, where
 * actual type can not be determined statically (declared type is
 * a supertype of actual polymorphic serialized types).
 */
package software.bernie.shadowed.fasterxml.jackson.databind.jsontype;
