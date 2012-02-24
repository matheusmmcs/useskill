package br.ufpi.componets;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;
import br.ufpi.models.Teste;
import br.ufpi.models.Usuario;
import javax.annotation.PreDestroy;

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

       @PreDestroy
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
