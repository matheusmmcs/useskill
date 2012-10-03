/**
 * 
 */
package br.ufpi.models.vo;

import java.util.Date;

import br.ufpi.analise.enums.TipoMedia;
import br.ufpi.models.TipoConvidado;

/**
 * @author Cleiton
 * 
 */
public class FluxoVO {
	private String nomeUsuario;
	private Long idUsuario;
	private Date dataRealizacao;
	private Long tempoRealizacao;
	private TipoMedia mediaTempo;
	private TipoMedia mediaAcoes;
	private TipoConvidado tipoConvidado;
	private Long quantidadeAcoes;
	private Long fluxoId;
	/**
	 * @param nomeUsuario
	 * @param idUsuario
	 * @param dataRealizacao
	 * @param tempoRealicao
	 */
	public FluxoVO(String nomeUsuario, Long idUsuario, Date dataRealizacao,
			Long tempoRealicao,TipoConvidado tipoConvidado) {
		super();
		this.nomeUsuario = nomeUsuario;
		this.idUsuario = idUsuario;
		this.dataRealizacao = dataRealizacao;
		this.tempoRealizacao = tempoRealicao;
		this.tipoConvidado=tipoConvidado;
	}
	
	/**
	 * @param dataRealizacao
	 * @param tempoRealicao
	 * @param mediaTempo
	 * @param tipoConvidado
	 * @param quantidadeAcoes
	 */
	public FluxoVO(Long fluXoId,Date dataRealizacao, Long tempoRealicao,TipoConvidado tipoConvidado, Long quantidadeAcoes) {
		super();
		this.dataRealizacao = dataRealizacao;
		this.tempoRealizacao = tempoRealicao;
		this.quantidadeAcoes = quantidadeAcoes;
		this.fluxoId=fluXoId;
		this.tipoConvidado=tipoConvidado;
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
	/**
	 * @return the idUsuario
	 */
	public Long getIdUsuario() {
		return idUsuario;
	}
	/**
	 * @param idUsuario the idUsuario to set
	 */
	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}
	/**
	 * @return the dataRealizacao
	 */
	public Date getDataRealizacao() {
		return dataRealizacao;
	}
	/**
	 * @param dataRealizacao the dataRealizacao to set
	 */
	public void setDataRealizacao(Date dataRealizacao) {
		this.dataRealizacao = dataRealizacao;
	}

	/**
	 * @return the tempoRealizacao
	 */
	public Long getTempoRealizacao() {
		return tempoRealizacao;
	}

	/**
	 * @param tempoRealizacao the tempoRealizacao to set
	 */
	public void setTempoRealizacao(Long tempoRealizacao) {
		this.tempoRealizacao = tempoRealizacao;
	}

	
	/**
	 * @return the mediaTempo
	 */
	public TipoMedia getMediaTempo() {
		return mediaTempo;
	}

	/**
	 * @param mediaTempo the mediaTempo to set
	 */
	public void setMediaTempo(TipoMedia mediaTempo) {
		this.mediaTempo = mediaTempo;
	}

	/**
	 * @return the mediaAcoes
	 */
	public TipoMedia getMediaAcoes() {
		return mediaAcoes;
	}

	/**
	 * @param mediaAcoes the mediaAcoes to set
	 */
	public void setMediaAcoes(TipoMedia mediaAcoes) {
		this.mediaAcoes = mediaAcoes;
	}

	/**
	 * @return the tipoConvidado
	 */
	public TipoConvidado getTipoConvidado() {
		return tipoConvidado;
	}
	/**
	 * @param tipoConvidado the tipoConvidado to set
	 */
	public void setTipoConvidado(TipoConvidado tipoConvidado) {
		this.tipoConvidado = tipoConvidado;
	}

	/**
	 * @return the quantidadeAcoes
	 */
	public Long getQuantidadeAcoes() {
		return quantidadeAcoes;
	}

	/**
	 * @param quantidadeAcoes the quantidadeAcoes to set
	 */
	public void setQuantidadeAcoes(Long quantidadeAcoes) {
		this.quantidadeAcoes = quantidadeAcoes;
	}

	/**
	 * @return the fluxoId
	 */
	public Long getFluxoId() {
		return fluxoId;
	}

	/**
	 * @param fluxoId the fluxoId to set
	 */
	public void setFluxoId(Long fluxoId) {
		this.fluxoId = fluxoId;
	}
	
	
}
