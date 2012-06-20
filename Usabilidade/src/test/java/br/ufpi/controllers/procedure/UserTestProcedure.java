package br.ufpi.controllers.procedure;

import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.ufpi.commons.HttpRequestTest;
import br.ufpi.componets.TesteView;
import br.ufpi.componets.UsuarioLogado;
import br.ufpi.componets.ValidateComponente;
import br.ufpi.controllers.UsuarioController;
import br.ufpi.models.Usuario;
import br.ufpi.repositories.Implement.UsuarioRepositoryImpl;

public class UserTestProcedure {

	public static UsuarioController newInstanceUsuarioController(
			EntityManager entityManager) {

		HttpServletRequest request = new HttpRequestTest();

		MockResult result = new MockResult();
		TesteView testeView = new TesteView();
		MockValidator validator = new MockValidator();
		UsuarioRepositoryImpl usuarioRepositoryImpl = new UsuarioRepositoryImpl(
				entityManager);
		UsuarioLogado usuarioLogado = new UsuarioLogado(usuarioRepositoryImpl);
		ValidateComponente validateComponente = new ValidateComponente(
				validator);
		UsuarioController usuarioController = new UsuarioController(result,
				validator, testeView, usuarioLogado, validateComponente,
				usuarioRepositoryImpl, (HttpServletRequest) request);
		return usuarioController;
	}

	public static Usuario newInstaceUsuario(EntityManager entityManager,
			String nome, String email, String senha, List<String> telefones,
			String confirmacaoEmail) {
		UsuarioRepositoryImpl repositoryImpl = new UsuarioRepositoryImpl(
				entityManager);
		Usuario usuario = new Usuario(repositoryImpl);
		usuario.setEmail(email);
		usuario.setNome(nome);
		usuario.setSenha(senha);
		usuario.setTelefones(telefones);
		usuario.setConfirmacaoEmail(confirmacaoEmail);
		return usuario;
	}

	public static Usuario newInstaceUsuario(EntityManager entityManager,
			String nome, String email, String senha, List<String> telefones) {
		UsuarioRepositoryImpl repositoryImpl = new UsuarioRepositoryImpl(
				entityManager);
		Usuario usuario = new Usuario(repositoryImpl);
		usuario.setEmail(email);
		usuario.setNome(nome);
		usuario.setSenha(senha);
		usuario.setTelefones(telefones);
		return usuario;
	}

	public static Usuario newInstaceUsuario(EntityManager entityManager,
			String nome, String email, String senha) {
		UsuarioRepositoryImpl repositoryImpl = new UsuarioRepositoryImpl(
				entityManager);
		Usuario usuario = new Usuario(repositoryImpl);
		usuario.setEmail(email);
		usuario.setNome(nome);
		usuario.setSenha(senha);
		return usuario;
	}
}
