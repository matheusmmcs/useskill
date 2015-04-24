package br.ufpi.datamining.components;

import java.io.InputStream;

import com.google.gson.Gson;

import br.com.caelum.vraptor.deserialization.Deserializer;
import br.com.caelum.vraptor.deserialization.Deserializes;
import br.com.caelum.vraptor.resource.ResourceMethod;
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
			Object fromJson = gson.fromJson(streamString, parameterTypes[0]);
			
			System.out.println("streamString: "+streamString);
			System.out.println("fromJson: "+fromJson);
			
			return new Object[]{fromJson};
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
		String streamString = "{\"title\":\"qwe\",\"clientAbbreviation\":\"asd\",\"urlSystem\":\"zxc\"}";
		TestDataMining fromJson = gson.fromJson(streamString, TestDataMining.class);
		System.out.println(fromJson.getTitle());
	}
	
	
	
	
	
	
	

}
