package br.ufpi.util;

import java.util.List;

import br.ufpi.models.ElementosTeste;
import br.ufpi.models.Teste;

import com.google.gson.Gson;

public class GsonElements {
	public static Teste addPergunta(Long id, Teste teste) {
		Gson gson = new Gson();
		ElementosTeste elementosTeste = new ElementosTeste(id, 'P');
		String elementosTeste2 = teste.getElementosTeste();
		if (elementosTeste2 == null) {
			teste.setElementosTeste(gson.toJson(elementosTeste));
			return teste;
		}
		List<ElementosTeste> elementosTestes = (List<ElementosTeste>) gson
				.fromJson(elementosTeste2, ElementosTeste.class);
		elementosTestes.add(elementosTeste);
		teste.setElementosTeste(gson.toJson(elementosTestes));
		return teste;
	}

	public static Teste addTarefa(Long id, Teste teste) {
		Gson gson = new Gson();
		ElementosTeste elementosTeste = new ElementosTeste(id, 'T');
		String elementosTeste2 = teste.getElementosTeste();
		if (elementosTeste2 == null) {
			teste.setElementosTeste(gson.toJson(elementosTeste));
			return teste;
		}
		List<ElementosTeste> elementosTestes = (List<ElementosTeste>) gson
				.fromJson(elementosTeste2, ElementosTeste.class);
		elementosTestes.add(elementosTeste);
		teste.setElementosTeste(gson.toJson(elementosTestes));
		return teste;
	}
}
