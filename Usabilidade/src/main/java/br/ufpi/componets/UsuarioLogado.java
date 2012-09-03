package br.ufpi.componets;

import javax.annotation.PreDestroy;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;
import br.ufpi.models.Usuario;
import br.ufpi.repositories.UsuarioRepository;

/**
 * Usado para Colocar o usuario na sessão apos Logar na Aplicação
 * @author Cleiton
 */
@Component
@SessionScoped
public class UsuarioLogado {
	//TODO depois apagar UsuarioRepository aqui é apenas para não precisar fazer login
//	UsuarioRepository usuarioRepository;
	private Usuario usuario;

	public UsuarioLogado( UsuarioRepository repository) {
		//this.usuarioRepository=repository;
		//usuario=usuarioRepository.find(9l);
		//System.out.println(usuario);
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

        /**
         * Analisa se possui usuario na sessão
         * @return
         */
        public boolean islogado() {
		return usuario != null;
	}

        /**
         * Quando terminar o tempo da sessão o usuario vai ser igual a null
         */
        @PreDestroy
	public void logout() {
		usuario = null;
	}

	

}
