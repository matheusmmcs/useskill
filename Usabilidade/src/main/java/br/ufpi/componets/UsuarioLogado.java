package br.ufpi.componets;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;
import br.ufpi.models.Teste;
import br.ufpi.models.Usuario;

@Component
@SessionScoped
public class UsuarioLogado {
	private Usuario usuario;
	private Teste teste;

	public UsuarioLogado( ) {
	}

	public Usuario getPessoa() {
		return usuario;
	}

	public void setPessoa(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean islogado() {
		return usuario != null;
	}

	public void logout() {
		usuario = null;
		setTeste(null);
	}

	public Teste getTeste() {
		return teste;
	}

	public void setTeste(Teste teste) {
		this.teste = teste;
	}

}
