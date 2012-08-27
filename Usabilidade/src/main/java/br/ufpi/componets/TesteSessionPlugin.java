package br.ufpi.componets;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;
import br.ufpi.models.TipoConvidado;

@SessionScoped
@Component
public class TesteSessionPlugin {
private Long idTeste;
private TipoConvidado tipoConvidado;
/**
 * @return the idTeste
 */
public Long getIdTeste() {
	return idTeste;
}

/**
 * @param idTeste the idTeste to set
 */
public void setIdTeste(Long idTeste) {
	this.idTeste = idTeste;
}

public TipoConvidado getTipoConvidado() {
	return tipoConvidado;
}

public void setTipoConvidado(TipoConvidado tipoConvidado) {
	this.tipoConvidado = tipoConvidado;
}



}
