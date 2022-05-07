/**
 * Base classes used by concrete Parser and Generator implementations;
 * contain functionality that is not specific to JSON or input
 * abstraction (byte vs char).
 * Most formats extend these types, although it is also possible to
 * directly extend {@link software.bernie.shadowed.fasterxml.jackson.core.JsonParser} or
 * {@link software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator}.
 */
package software.bernie.shadowed.fasterxml.jackson.core.base;
