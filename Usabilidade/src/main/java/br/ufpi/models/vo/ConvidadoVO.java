package br.ufpi.models.vo;

import br.ufpi.models.Teste;
import br.ufpi.models.TipoConvidado;
import br.ufpi.models.Usuario;

public class ConvidadoVO {
	private Teste teste;
	private TipoConvidado tipoConvidado;
	private Usuario usuario;

	public ConvidadoVO(Teste teste, TipoConvidado convidado) {
		this.teste = teste;
		this.setTipoConvidado(convidado);
	}

	public ConvidadoVO(Usuario usuario, TipoConvidado convidado) {
		super();
		this.setTipoConvidado(convidado);
		this.setUsuario(usuario);
	}

	public ConvidadoVO(Teste teste) {
		this.teste = teste;
	}

	public Teste getTeste() {
		return teste;
	}

	public void setTeste(Teste teste) {
		this.teste = teste;
	}

	

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public TipoConvidado getTipoConvidado() {
		return tipoConvidado;
	}

	public void setTipoConvidado(TipoConvidado tipoConvidado) {
		this.tipoConvidado = tipoConvidado;
	}

}
