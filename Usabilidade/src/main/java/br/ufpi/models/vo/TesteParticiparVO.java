package br.ufpi.models.vo;

import br.ufpi.models.TipoConvidado;

public class TesteParticiparVO {
	private TipoConvidado tipoConvidado;
	private String elemntosTeste;

	

	public TesteParticiparVO(TipoConvidado tipoConvidado, String elemntosTeste) {
		super();
		this.tipoConvidado = tipoConvidado;
		this.elemntosTeste = elemntosTeste;
	}

	/**
	 * @return the elemntosTeste
	 */
	public String getElemntosTeste() {
		return elemntosTeste;
	}

	/**
	 * @param elemntosTeste the elemntosTeste to set
	 */
	public void setElemntosTeste(String elemntosTeste) {
		this.elemntosTeste = elemntosTeste;
	}


	/**
	 * @return the tipoConvidado
	 */
	public TipoConvidado getTipoConvidado() {
		return tipoConvidado;
	}

	/**
	 * @param tipoConvidado
	 *            the tipoConvidado to set
	 */
	public void setTipoConvidado(TipoConvidado tipoConvidado) {
		this.tipoConvidado = tipoConvidado;
	}

}
