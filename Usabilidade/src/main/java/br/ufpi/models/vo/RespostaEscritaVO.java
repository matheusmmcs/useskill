/**
 * 
 */
package br.ufpi.models.vo;

/**
 * @author Cleiton
 *
 */
public class RespostaEscritaVO {
private String resposta;
private String nomeUsuario;
/**
 * @param resposta
 * @param nomeUsuario
 */
public RespostaEscritaVO(String resposta, String nomeUsuario) {
	super();
	this.resposta = resposta;
	this.nomeUsuario = nomeUsuario;
}
/**
 * @return the resposta
 */
public String getResposta() {
	return resposta;
}
/**
 * @param resposta the resposta to set
 */
public void setResposta(String resposta) {
	this.resposta = resposta;
}
/**
 * @return the nomeUsuario
 */
public String getNomeUsuario() {
	return nomeUsuario;
}
/**
 * @param nomeUsuario the nomeUsuario to set
 */
public void setNomeUsuario(String nomeUsuario) {
	this.nomeUsuario = nomeUsuario;
}

}
