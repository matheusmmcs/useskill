package br.ufpi.models.vo;

/**
 * @author Cleiton
 *
 */
public class TarefaVO {
	private String titulo;
	private String roteiro;
	private String url;
	
	
	/**
	 * @return the titulo
	 */
	public String getTitulo() {
		return titulo;
	}
	/**
	 * @param titulo the titulo to set
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public TarefaVO(String roteiro, String url, String titulo) {
		super();
		this.roteiro = roteiro;
		this.url = url;
		this.titulo = titulo;
	}
	/**
	 * @return the roteiro
	 */
	public String getRoteiro() {
		return roteiro;
	}
	/**
	 * @param roteiro the roteiro to set
	 */
	public void setRoteiro(String roteiro) {
		this.roteiro = roteiro;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	@Override
	public String toString() {
		return "TarefaVO [titulo=" + titulo + ", roteiro=" + roteiro + ", url="
				+ url + "]";
	}
	
	

}
