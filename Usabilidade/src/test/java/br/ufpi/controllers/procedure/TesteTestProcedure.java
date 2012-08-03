package br.ufpi.controllers.procedure;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.ufpi.commons.HttpRequestTest;
import br.ufpi.componets.TesteView;
import br.ufpi.componets.UsuarioLogado;
import br.ufpi.componets.ValidateComponente;
import br.ufpi.controllers.TesteController;
import br.ufpi.models.Questionario;
import br.ufpi.models.Teste;
import br.ufpi.models.Usuario;
import br.ufpi.repositories.TesteRepository;
import br.ufpi.repositories.UsuarioRepository;
import br.ufpi.repositories.Implement.TesteRepositoryImpl;

public class TesteTestProcedure {
	public static TesteController newInstanceTesteController(
			EntityManager entityManager, MockResult result) {
		HttpServletRequest request = new HttpRequestTest();
		TesteView testeView = new TesteView();
		MockValidator validator = new MockValidator();
		UsuarioRepository usuarioRepositoryImpl = UsuarioTestProcedure
				.newInstanceUsuarioRepository(entityManager);
		UsuarioLogado usuarioLogado = new UsuarioLogado(usuarioRepositoryImpl);
		ValidateComponente validateComponente = new ValidateComponente(
				validator);
		TesteController controller = new TesteController(
				result,
				validator,
				testeView,
				usuarioLogado,
				validateComponente,
				newInstanceTesteRepository(entityManager),
				UsuarioTestProcedure.newInstanceConvidadoRepository(entityManager),
				request);
		return controller;
	}
	
	public static TesteRepository newInstanceTesteRepository(
			EntityManager entityManager) {
		return new TesteRepositoryImpl(entityManager);
	}

	public static Teste newInstanceTeste(String titulo, Usuario usuarioCriador) {
		Teste teste = new Teste();
		teste.setTitulo(titulo);
		teste.setUsuarioCriador(usuarioCriador);
		teste.setTituloPublico("Titulo publico");
		teste.setTextoIndroducao("Texto introdução");
		Questionario questionario = new Questionario();
		teste.setSatisfacao(questionario);

		return teste;

	}
	public static Teste newInstanceTeste(String titulo, Usuario usuarioCriador, boolean liberado) {
		Teste teste = new Teste();
		teste.setTitulo(titulo);
		teste.setUsuarioCriador(usuarioCriador);
		teste.setLiberado(liberado);
		teste.setSatisfacao(new Questionario());
		return teste;

	}
}
