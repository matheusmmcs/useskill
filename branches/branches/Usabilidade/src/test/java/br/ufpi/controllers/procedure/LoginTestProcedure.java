package br.ufpi.controllers.procedure;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.ufpi.commons.HttpRequestTest;
import br.ufpi.componets.TesteView;
import br.ufpi.componets.UsuarioLogado;
import br.ufpi.componets.ValidateComponente;
import br.ufpi.controllers.LoginController;
import br.ufpi.repositories.Implement.UsuarioRepositoryImpl;

public class LoginTestProcedure {
	public static LoginController newInstanceUsuarioController(
			EntityManager entityManager,MockResult result) {
		HttpServletRequest request = new HttpRequestTest();
		TesteView testeView = new TesteView();
		MockValidator validator = new MockValidator();
		UsuarioRepositoryImpl usuarioRepositoryImpl = new UsuarioRepositoryImpl(
				entityManager);
		UsuarioLogado usuarioLogado = new UsuarioLogado(usuarioRepositoryImpl);
		ValidateComponente validateComponente = new ValidateComponente(
				validator);
		LoginController loginController = new LoginController(result,
				validator, testeView, usuarioLogado, validateComponente,
				usuarioRepositoryImpl, request);
		return loginController;
	}

}
