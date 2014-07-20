package br.ufpi.models.vo;

import org.apache.commons.lang.builder.EqualsBuilder;

import br.ufpi.models.Usuario;

public class ActionVO {

	private String sActionType;
	private String sUrl;
	private String sXPath;
	
	private boolean melhorCaminho;
	private boolean obrigatorio;
	
	/**
	 * Usuario definido 
	 */
	private Usuario usuario;
	
	public ActionVO(String actionType, String url, String xPath){
		this.sActionType = actionType;
		this.sUrl = url;
		this.sXPath = xPath;
		this.melhorCaminho = false;
		this.obrigatorio = false;
	}
	
	public String getsActionType() {
		return sActionType;
	}
	public void setsActionType(String sActionType) {
		this.sActionType = sActionType;
	}
	public String getsUrl() {
		return sUrl;
	}
	public void setsUrl(String sUrl) {
		this.sUrl = sUrl;
	}
	public String getsXPath() {
		return sXPath;
	}
	public void setsXPath(String sXPath) {
		this.sXPath = sXPath;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
	@Override
	public boolean equals(Object obj){
		if (obj == null){
            return false;
		}
        if (obj == this){
            return true;
        }
        if (!(obj instanceof ActionVO)){
            return false;
        }

        ActionVO rhs = (ActionVO) obj;
        return new EqualsBuilder().
            append(sActionType, rhs.getsActionType()).
            append(sUrl, rhs.getsUrl()).
            append(sXPath, rhs.getsXPath()).
            isEquals();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		return new String(sActionType + sUrl + sXPath).hashCode() * prime;
	}
	
	@Override
	public String toString() {
		return "Action [actionType=" + sActionType + ", url=" + sUrl + ", xPath=" + sXPath + "]";
	}

	/**
	 * @return the obrigatorio
	 */
	public boolean isObrigatorio() {
		return obrigatorio;
	}

	/**
	 * @param obrigatorio the obrigatorio to set
	 */
	public void setObrigatorio(boolean obrigatorio) {
		this.obrigatorio = obrigatorio;
	}

	/**
	 * @return the melhorCaminho
	 */
	public boolean isMelhorCaminho() {
		return melhorCaminho;
	}

	/**
	 * @param melhorCaminho the melhorCaminho to set
	 */
	public void setMelhorCaminho(boolean melhorCaminho) {
		this.melhorCaminho = melhorCaminho;
	}

}
