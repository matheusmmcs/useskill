package br.ufpi.models.vo;

import br.ufpi.models.Teste;
import br.ufpi.models.TipoConvidado;

public class ConvidadoVO {
	private Teste teste;
	private TipoConvidado convidado;

	public ConvidadoVO(Teste teste, TipoConvidado convidado) {
		this.teste = teste;
		this.convidado = convidado;
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

	public TipoConvidado getConvidado() {
		return convidado;
	}

	public void setConvidado(TipoConvidado convidado) {
		this.convidado = convidado;
	}

}
