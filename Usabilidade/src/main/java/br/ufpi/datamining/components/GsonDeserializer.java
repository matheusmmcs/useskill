package br.ufpi.datamining.components;

import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import br.com.caelum.vraptor.deserialization.Deserializer;
import br.com.caelum.vraptor.deserialization.Deserializes;
import br.com.caelum.vraptor.resource.ResourceMethod;
import br.ufpi.datamining.models.ActionSingleDataMining;
import br.ufpi.datamining.models.TestDataMining;

@Deserializes("application/json")
public class GsonDeserializer implements Deserializer {

	@Override
	public Object[] deserialize(InputStream inputStream, ResourceMethod method) {
		// TODO Auto-generated method stub
		Class<?>[] parameterTypes = method.getMethod().getParameterTypes();
		
		System.out.println("Parametros: "+parameterTypes);
		
		if(parameterTypes.length > 0){
			String streamString = convertStreamToString(inputStream);
			Gson gson = new Gson();
			
			Object[] result = new Object[parameterTypes.length];
			int count = 0;
			
			JsonObject jsonObject = new JsonParser().parse(streamString).getAsJsonObject();
			Set<Map.Entry<String,JsonElement>> entrySet = jsonObject.entrySet();
			for (Map.Entry<String,JsonElement> entry : entrySet) {
				JsonElement jsonElement = jsonObject.get(entry.getKey());
				result[count] = gson.fromJson(jsonElement, parameterTypes[count]);
				count++;
			}
			return result;
		}
		
		return null;
	}
	
	static String convertStreamToString(java.io.InputStream is) {
	    java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
	    return s.hasNext() ? s.next() : "";
	}
	
	//{"test":{"title":"qwe","clientAbbreviation":"asd","urlSystem":"zxc"}}
	
	public static void main(String[] args) { 
		Gson gson = new Gson();
		String streamString = "{\"id\":123, \"obj\": {\"title\":\"qwe\",\"clientAbbreviation\":\"asd\",\"urlSystem\":\"zxc\"}}";
		TestDataMining fromJson = gson.fromJson(streamString, TestDataMining.class);
		
		
		Class<?>[] parameterTypes = new Class<?>[]{Long.class, TestDataMining.class};
		Object[] result = new Object[parameterTypes.length];
		int count = 0;
		
		JsonObject jsonObject = new JsonParser().parse(streamString).getAsJsonObject();
		Set<Map.Entry<String,JsonElement>> entrySet = jsonObject.entrySet();
		for (Map.Entry<String,JsonElement> entry : entrySet) {
			JsonElement jsonElement = jsonObject.get(entry.getKey());
			result[count] = gson.fromJson(jsonElement, parameterTypes[count]);
			count++;
		}
		
		
		
//		Type typeMap = new TypeToken<HashMap<String, Object>>(){}.getType();
//		HashMap<String, Object> map = gson.fromJson(streamString, typeMap);
//		Set<String> keySet = map.keySet();
//		
//		for (String key : keySet) {
//			System.out.println(key);
//			System.out.println(map.get(key));
//		}
		
//		streamString = "{\"actionType\":\"form_submit\"}";
//		ActionSingleDataMining action = gson.fromJson(streamString, ActionSingleDataMining.class);
//		System.out.println(action.getActionType());
		
	}
	
	
	
	
	
	
	

}
