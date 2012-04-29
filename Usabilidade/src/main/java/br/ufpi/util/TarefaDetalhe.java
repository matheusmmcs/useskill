package br.ufpi.util;

/*
 * Classe utilizada para passar parametros sobre a tarefa para a view responsável por mostrar a página modificada
 */
public class TarefaDetalhe {

	private String url, roteiro;
	
	public TarefaDetalhe(){
		
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
	
}
