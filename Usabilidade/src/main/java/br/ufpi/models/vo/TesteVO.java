package br.ufpi.models.vo;

import br.ufpi.models.TipoConvidado;

public class TesteVO {
	private Long id;
	private String nome;
	private String usuarioCriado;
	private TipoConvidado tipoConvidado;

	public TesteVO(Long id, String nome, String usuarioCriado,
			TipoConvidado tipoConvidado) {
		super();
		this.id = id;
		this.nome = nome;
		this.usuarioCriado = usuarioCriado;
		this.setTipoConvidado(tipoConvidado);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUsuarioCriado() {
		return usuarioCriado;
	}

	public void setUsuarioCriado(String usuarioCriado) {
		this.usuarioCriado = usuarioCriado;
	}

	public TipoConvidado getTipoConvidado() {
		return tipoConvidado;
	}

	public void setTipoConvidado(TipoConvidado tipoConvidado) {
		this.tipoConvidado = tipoConvidado;
	}

}
