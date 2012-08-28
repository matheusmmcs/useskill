package br.ufpi.models.vo;

/**
 * @author Cleiton
 *
 */
public class TarefaVO {
	private String roteiro;
	private String url;
	
	public TarefaVO(String roteiro, String url) {
		super();
		this.roteiro = roteiro;
		this.url = url;
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
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TarefaVO [roteiro=" + roteiro + ", url=" + url + "]";
	}
	

}
