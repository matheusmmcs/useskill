package br.ufpi.componets;

import javax.annotation.PreDestroy;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;

/*
 * Classe utilizada para passar parametros sobre a tarefa para a view responsável por mostrar a página modificada
 */
@SessionScoped
@Component
public class TarefaDetalhe {

	private String url, roteiro, urlJSON;
	

	public String getUrlJSON() {
		return urlJSON;
	}

	public void setUrlJSON(String urlJSON) {
		this.urlJSON = urlJSON;
	}

	public TarefaDetalhe() {

	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRoteiro() {
		return roteiro;
	}

	public void setRoteiro(String roteiro) {
		this.roteiro = roteiro;
	}

	@PreDestroy
	public void destroy() {
		url = null;
		roteiro = null;
	}
}
