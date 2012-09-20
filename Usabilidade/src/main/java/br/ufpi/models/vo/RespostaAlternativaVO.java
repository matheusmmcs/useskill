/**
 * 
 */
package br.ufpi.models.vo;

import br.ufpi.models.Alternativa;

/**
 * @author Cleiton
 * 
 */
public class RespostaAlternativaVO {
	private Alternativa alternativa;
	private Long quantidadeRespostas;
	private double porcentos;

	/**
	 * @return the alternativa
	 */
	public Alternativa getAlternativa() {
		return alternativa;
	}

	/**
	 * @param alternativa
	 * @param quantidadeRespostas
	 */
	public RespostaAlternativaVO(Alternativa alternativa,
			Long quantidadeRespostas) {
		super();
		this.alternativa = alternativa;
		if (quantidadeRespostas == null)
			this.quantidadeRespostas = 0l;
		else
			this.quantidadeRespostas = quantidadeRespostas;
	}

	/**
	 * @param alternativa
	 *            the alternativa to set
	 */
	public void setAlternativa(Alternativa alternativa) {
		this.alternativa = alternativa;
	}

	/**
	 * @return the quantidadeRespostas
	 */
	public Long getQuantidadeRespostas() {
		return quantidadeRespostas;
	}

	/**
	 * @param quantidadeRespostas
	 *            the quantidadeRespostas to set
	 */
	public void setQuantidadeRespostas(Long quantidadeRespostas) {
		this.quantidadeRespostas = quantidadeRespostas;
	}

	/**
	 * @param quantidadeRespostas
	 */
	public RespostaAlternativaVO(Long quantidadeRespostas) {
		super();
		this.quantidadeRespostas = quantidadeRespostas;
	}

	/**
	 * @return the porcentos
	 */
	public double getPorcentos() {
		return porcentos;
	}

	/**
	 * @param porcentos
	 *            the porcentos to set
	 */
	public void setPorcentos(double porcentos) {
		this.porcentos = porcentos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RespostaAlternativaVO [alternativa=" + alternativa
				+ ", quantidadeRespostas=" + quantidadeRespostas
				+ ", porcentos=" + porcentos + "]";
	}

	/**
	 * @param alternativa
	 */
	public RespostaAlternativaVO(Alternativa alternativa) {
		super();
		this.alternativa = alternativa;
	}

}
