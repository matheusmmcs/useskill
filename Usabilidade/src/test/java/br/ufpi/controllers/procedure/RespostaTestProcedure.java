package br.ufpi.controllers.procedure;

import javax.persistence.EntityManager;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.ufpi.componets.FluxoComponente;
import br.ufpi.componets.TesteView;
import br.ufpi.componets.UsuarioLogado;
import br.ufpi.componets.ValidateComponente;
import br.ufpi.controllers.RespostaController;
import br.ufpi.repositories.RespostaAlternativaRepository;
import br.ufpi.repositories.RespostaEscritaRepository;
import br.ufpi.repositories.UsuarioRepository;
import br.ufpi.repositories.Implement.RespostaAlternativaRepositoryImpl;
import br.ufpi.repositories.Implement.RespostaEscritaRepositoryImpl;

public class RespostaTestProcedure {
	public static RespostaController newInstanceTarefaController(
			EntityManager entityManager, MockResult result) {
		TesteView testeView = new TesteView();
		MockValidator validator = new MockValidator();
		UsuarioRepository usuarioRepositoryImpl = UsuarioTestProcedure
				.newInstanceUsuarioRepository(entityManager);
		UsuarioLogado usuarioLogado = new UsuarioLogado(usuarioRepositoryImpl);
		ValidateComponente validateComponente = new ValidateComponente(
				validator);
		FluxoComponente fluxoComponente = null;
		return new RespostaController(result, validator, testeView,
				usuarioLogado, validateComponente,
				newInstanceRespostaEscritaRepositoryImpl(entityManager),
				newInstanceRespostaAlternativaRepository(entityManager),
				PerguntaTestProcedure
						.newInstancePerguntaRepository(entityManager),
				fluxoComponente);
	}

	public static RespostaController newInstanceRespostaController(
			EntityManager entityManager, MockResult result,
			FluxoComponente fluxoComponente) {
		TesteView testeView = new TesteView();
		MockValidator validator = new MockValidator();
		UsuarioRepository usuarioRepositoryImpl = UsuarioTestProcedure
				.newInstanceUsuarioRepository(entityManager);
		UsuarioLogado usuarioLogado = new UsuarioLogado(usuarioRepositoryImpl);
		ValidateComponente validateComponente = new ValidateComponente(
				validator);
		return new RespostaController(result, validator, testeView,
				usuarioLogado, validateComponente,
				newInstanceRespostaEscritaRepositoryImpl(entityManager),
				newInstanceRespostaAlternativaRepository(entityManager),
				PerguntaTestProcedure
						.newInstancePerguntaRepository(entityManager),
				fluxoComponente);
	}

	public static RespostaAlternativaRepository newInstanceRespostaAlternativaRepository(
			EntityManager entityManager) {
		return new RespostaAlternativaRepositoryImpl(entityManager);

	}

	public static RespostaEscritaRepository newInstanceRespostaEscritaRepositoryImpl(
			EntityManager entityManager) {
		return new RespostaEscritaRepositoryImpl(entityManager);
	}

}
