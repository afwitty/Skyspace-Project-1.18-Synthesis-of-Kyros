package software.bernie.geckolib3.geo.raw.pojo;

import java.io.IOException;

import software.bernie.shadowed.fasterxml.jackson.core.JsonGenerator;
import software.bernie.shadowed.fasterxml.jackson.core.JsonParser;
import software.bernie.shadowed.fasterxml.jackson.core.JsonProcessingException;
import software.bernie.shadowed.fasterxml.jackson.databind.DeserializationContext;
import software.bernie.shadowed.fasterxml.jackson.databind.JsonDeserializer;
import software.bernie.shadowed.fasterxml.jackson.databind.JsonSerializer;
import software.bernie.shadowed.fasterxml.jackson.databind.SerializerProvider;
import software.bernie.shadowed.fasterxml.jackson.databind.annotation.JsonDeserialize;
import software.bernie.shadowed.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonDeserialize(using = PolysUnion.Deserializer.class)
@JsonSerialize(using = PolysUnion.Serializer.class)
public class PolysUnion {
	public double[][][] doubleArrayArrayArrayValue;
	public PolysEnum enumValue;

	static class Deserializer extends JsonDeserializer<PolysUnion> {
		@Override
		public PolysUnion deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)
				throws IOException, JsonProcessingException {
			PolysUnion value = new PolysUnion();
			switch (jsonParser.getCurrentToken()) {
			case VALUE_STRING:
				String string = jsonParser.readValueAs(String.class);
				try {
					value.enumValue = PolysEnum.forValue(string);
				} catch (Exception ex) {
					// Ignored
				}
				break;
			case START_ARRAY:
				value.doubleArrayArrayArrayValue = jsonParser.readValueAs(double[][][].class);
				break;
			default:
				throw new IOException("Cannot deserialize PolysUnion");
			}
			return value;
		}
	}

	static class Serializer extends JsonSerializer<PolysUnion> {
		@Override
		public void serialize(PolysUnion obj, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
				throws IOException {
			if (obj.doubleArrayArrayArrayValue != null) {
				jsonGenerator.writeObject(obj.doubleArrayArrayArrayValue);
				return;
			}
			if (obj.enumValue != null) {
				jsonGenerator.writeObject(obj.enumValue);
				return;
			}
			throw new IOException("PolysUnion must not be null");
		}
	}
}
