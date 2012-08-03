package br.ufpi.controllers.procedure;

import javax.persistence.EntityManager;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.ufpi.componets.FluxoComponente;
import br.ufpi.componets.TesteView;
import br.ufpi.componets.UsuarioLogado;
import br.ufpi.componets.ValidateComponente;
import br.ufpi.controllers.RespostaController;
import br.ufpi.repositories.PerguntaRepository;
import br.ufpi.repositories.RespostaAlternativaRepository;
import br.ufpi.repositories.RespostaEscritaRepository;
import br.ufpi.repositories.TesteRepository;
import br.ufpi.repositories.UsuarioRepository;
import br.ufpi.repositories.Implement.RespostaAlternativaRepositoryImpl;
import br.ufpi.repositories.Implement.RespostaEscritaRepositoryImpl;
import br.ufpi.repositories.Implement.TarefaRepositoryImpl;
import br.ufpi.repositories.Implement.TesteRepositoryImpl;

public class RespostaTestProcedure {
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
		PerguntaRepository perguntaRepository = PerguntaTestProcedure
		.newInstancePerguntaRepository(entityManager);
		RespostaEscritaRepository escritaRepository = newInstanceRespostaEscritaRepositoryImpl(entityManager);
		RespostaAlternativaRepository alternativaRepository = newInstanceRespostaAlternativaRepository(entityManager);
		
		TesteRepository testeRepository=new TesteRepositoryImpl(entityManager);
		return new RespostaController(result, validator, testeView, usuarioLogado, validateComponente, escritaRepository, alternativaRepository, perguntaRepository, fluxoComponente, testeRepository);
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
