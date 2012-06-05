package br.ufpi.models;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Embeddable
public class UsuarioTestePK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ManyToOne(fetch = FetchType.LAZY)
	private Usuario participante;
	@ManyToOne(fetch = FetchType.LAZY)
	private Teste teste;

	public UsuarioTestePK(Usuario usuario, Teste teste) {
		participante = usuario;
		this.teste = teste;
	}

	public UsuarioTestePK() {
	}

	public Usuario getUsuario() {
		return participante;
	}

	public void setUsuario(Usuario usuario) {
		this.participante = usuario;
	}

	public Teste getTeste() {
		return teste;
	}

	public void setTeste(Teste teste) {
		this.teste = teste;
	}

	@Override
	public String toString() {
		return "UsuarioTestePK [participante=" + participante.getId() + ", teste="
				+ teste.getId() + "]";
	}

}
