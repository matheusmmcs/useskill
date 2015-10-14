package br.ufpi.util;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class StringDeserializer implements JsonDeserializer<String> {
	@Override
	public String deserialize(JsonElement json, Type typeOfT,
			JsonDeserializationContext context) throws JsonParseException {
		if (json.isJsonPrimitive())
			return json.getAsString();
		return "";
	}
}
