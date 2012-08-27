package br.ufpi.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import br.ufpi.models.ElementosTeste;
import br.ufpi.models.Teste;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class GsonElements {
	public static Teste addPergunta(Long id, Teste teste) {
		Gson gson = new Gson();
		ElementosTeste elementosTeste = new ElementosTeste(id, 'P');
		String elementosTeste2 = teste.getElementosTeste();
		List<ElementosTeste> elementosTestes= new ArrayList<ElementosTeste>();
		if (elementosTeste2 != null) {
			Type listType = new TypeToken<List<ElementosTeste>>(){}.getType();
		 elementosTestes = (List<ElementosTeste>) gson.fromJson(elementosTeste2, listType);}
		elementosTestes.add(elementosTeste);
		teste.setElementosTeste(gson.toJson(elementosTestes));
		return teste;
	}

	public static Teste addTarefa(Long id, Teste teste) {
		Gson gson = new Gson();
		ElementosTeste elementosTeste = new ElementosTeste(id, 'T');
		String elementosTeste2 = teste.getElementosTeste();
		List<ElementosTeste> elementosTestes= new ArrayList<ElementosTeste>();
		if (elementosTeste2 != null) {
			Type listType = new TypeToken<List<ElementosTeste>>(){}.getType();
			elementosTestes = (List<ElementosTeste>) gson.fromJson(elementosTeste2, listType);
			}
		elementosTestes.add(elementosTeste);
		teste.setElementosTeste(gson.toJson(elementosTestes));
		return teste;
	}
}
