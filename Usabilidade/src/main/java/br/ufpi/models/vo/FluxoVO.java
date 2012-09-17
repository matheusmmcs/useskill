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
	private Long tempoRealicao;
	private TipoMedia media;
	private TipoConvidado tipoConvidado;
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
		this.tempoRealicao = tempoRealicao;
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
	 * @return the tempoRealicao
	 */
	public Long getTempoRealicao() {
		return tempoRealicao;
	}
	/**
	 * @param tempoRealicao the tempoRealicao to set
	 */
	public void setTempoRealicao(Long tempoRealicao) {
		this.tempoRealicao = tempoRealicao;
	}
	/**
	 * @return the media
	 */
	public TipoMedia getMedia() {
		return media;
	}
	/**
	 * @param media the media to set
	 */
	public void setMedia(TipoMedia media) {
		this.media = media;
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
	
	
}
