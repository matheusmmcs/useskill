package br.ufpi.componets;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;
import br.ufpi.models.Teste;
import br.ufpi.models.Usuario;
import br.ufpi.repositories.UsuarioRepository;

import javax.annotation.PreDestroy;

@Component
@SessionScoped
public class UsuarioLogado {
	//TODO depois apagar UsuarioRepository aqui é apenas para não precisar fazer login
	UsuarioRepository usuarioRepository;
	private Usuario usuario;
	private Teste teste;

	public UsuarioLogado( UsuarioRepository repository) {
		this.usuarioRepository=repository;
		usuario=usuarioRepository.find(1l);
		System.out.println(usuario);
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
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
