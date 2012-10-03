/**
 * 
 */
package br.ufpi.models.vo;

import br.ufpi.models.TipoConvidado;

/**
 * @author Cleiton
 * 
 */
public class ConvidadoCount {
	private Long numeroConvidados;
	private TipoConvidado tipoConvidado;

	/**
	 * @param numeroConvidados
	 * @param tipoConvidado
	 */
	public ConvidadoCount(Long numeroConvidados, TipoConvidado tipoConvidado) {
		super();
		this.numeroConvidados = numeroConvidados;
		this.tipoConvidado = tipoConvidado;
	}

	/**
	 * @return the numeroConvidados
	 */
	public Long getNumeroConvidados() {
		return numeroConvidados;
	}

	/**
	 * @param numeroConvidados
	 *            the numeroConvidados to set
	 */
	public void setNumeroConvidados(Long numeroConvidados) {
		this.numeroConvidados = numeroConvidados;
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ConvidadoCount [numeroConvidados=" + numeroConvidados
				+ ", tipoConvidado=" + tipoConvidado + "]";
	}

}
