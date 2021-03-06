/**
 * 
 */
package br.ufpi.models.vo;


/**
 * @author Cleiton
 * 
 */
public class FluxoCountVO {
	private Long quantidadeAcoes;
	private Long tempoRealizacao;
	private boolean isFinished;

	/**
	 * @param quantidadeAcoes
	 * @param tempoRealizacao
	 */
	public FluxoCountVO( Long tempoRealizacao,Long quantidadeAcoes) {
		super();
		this.quantidadeAcoes = quantidadeAcoes;
		this.tempoRealizacao = tempoRealizacao;
	}

	/**
	 * @return the quantidadeAcoes
	 */
	public Long getQuantidadeAcoes() {
		return quantidadeAcoes;
	}

	/**
	 * @param quantidadeAcoes
	 *            the quantidadeAcoes to set
	 */
	public void setQuantidadeAcoes(Long quantidadeAcoes) {
		this.quantidadeAcoes = quantidadeAcoes;
	}

	/**
	 * @return the tempoRealizacao
	 */
	public Long getTempoRealizacao() {
		return tempoRealizacao;
	}

	/**
	 * @param tempoRealizacao
	 *            the tempoRealizacao to set
	 */
	public void setTempoRealizacao(Long tempoRealizacao) {
		this.tempoRealizacao = tempoRealizacao;
	}

	@Override
	public String toString() {
		return "FluxoCountVO [quantidadeAcoes=" + quantidadeAcoes
				+ ", tempoRealizacao=" + tempoRealizacao + ", isFinished="
				+ isFinished + "]";
	}

	public boolean isFinished() {
		return isFinished;
	}

	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}

	
}
