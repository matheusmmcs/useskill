package br.ufpi.controllers.procedure;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.ufpi.componets.TesteSessionPlugin;
import br.ufpi.componets.TesteView;
import br.ufpi.componets.UsuarioLogado;
import br.ufpi.componets.ValidateComponente;
import br.ufpi.controllers.PerguntaController;
import br.ufpi.models.Alternativa;
import br.ufpi.models.Pergunta;
import br.ufpi.models.Teste;
import br.ufpi.repositories.AlternativaRepository;
import br.ufpi.repositories.PerguntaRepository;
import br.ufpi.repositories.PerguntaRepositoryTest;
import br.ufpi.repositories.TesteRepository;
import br.ufpi.repositories.UsuarioRepository;
import br.ufpi.repositories.Implement.AlternativaRepositoryImpl;

public class PerguntaTestProcedure {
	public static PerguntaController newInstancePerguntaController(
			EntityManager entityManager, MockResult result) {
		TesteView testeView = new TesteView();
		MockValidator validator = new MockValidator();
		UsuarioRepository usuarioRepositoryImpl = UsuarioTestProcedure
				.newInstanceUsuarioRepository(entityManager);
		UsuarioLogado usuarioLogado = new UsuarioLogado(usuarioRepositoryImpl);
		ValidateComponente validateComponente = new ValidateComponente(
				validator);
		PerguntaRepository perguntaRepository = newInstancePerguntaRepository(entityManager);
		TesteRepository testeRepository = TesteTestProcedure
				.newInstanceTesteRepository(entityManager);
		TesteSessionPlugin sessionPlugin= new TesteSessionPlugin();
		AlternativaRepository alternativaRepository= new AlternativaRepositoryImpl(entityManager);
		PerguntaController controller = new PerguntaController(result, validator, testeView, usuarioLogado, validateComponente, perguntaRepository, testeRepository, sessionPlugin,alternativaRepository);
		return controller;
	}

	public static PerguntaRepository newInstancePerguntaRepository(
			EntityManager entityManager) {
		return new PerguntaRepositoryTest(entityManager);
	}

	public static Pergunta newInstancePerguntaAlternativa(Teste teste,
			String texto, int numeroAlternativas, boolean responderFim,
			String titulo) {
		Pergunta pergunta = new Pergunta();
		pergunta.setQuestionario(teste.getSatisfacao());
		pergunta.setTexto(texto);
		pergunta.setTipoRespostaAlternativa(true);
		pergunta.setAlternativas(newListAlternativas(numeroAlternativas,
				pergunta));
		pergunta.setTitulo(titulo);
		return pergunta;
	}

	public static Pergunta newInstancePerguntaAlternativa(String texto,
			int numeroAlternativas, boolean responderFim, String titulo) {
		Pergunta pergunta = new Pergunta();
		pergunta.setTexto(texto);
		pergunta.setTipoRespostaAlternativa(true);
		pergunta.setAlternativas(newListAlternativas(numeroAlternativas,
				pergunta));
		pergunta.setTitulo(titulo);
		return pergunta;
	}

	public static Pergunta newInstancePerguntaEscrita(Teste teste,
			String texto, boolean responderFim, String titulo) {
		Pergunta pergunta = new Pergunta();
		pergunta.setQuestionario(teste.getSatisfacao());
		pergunta.setTexto(texto);
		pergunta.setTipoRespostaAlternativa(false);
		pergunta.setTitulo(titulo);
		return pergunta;
	}

	public static Pergunta newInstancePerguntaEscrita(String texto,
			boolean responderFim, String titulo) {
		Pergunta pergunta = new Pergunta();
		pergunta.setTexto(texto);
		pergunta.setTipoRespostaAlternativa(false);
		pergunta.setTitulo(titulo);
		return pergunta;
	}

	public static List<Alternativa> newListAlternativas(int numeroAlternativas,
			Pergunta pergunta) {
		List<Alternativa> alternativas = new ArrayList<Alternativa>();
		for (int i = 0; i < numeroAlternativas; i++) {
			Alternativa alternativa = new Alternativa();
			alternativa.setTextoAlternativa("Alternativa de numero ");
			alternativas.add(alternativa);
		}
		return alternativas;
	}

	public static PerguntaController newInstancePerguntaController(
			EntityManager entityManager, MockResult result,
			TesteSessionPlugin sessionPlugin) {
		TesteView testeView = new TesteView();
		MockValidator validator = new MockValidator();
		UsuarioRepository usuarioRepositoryImpl = UsuarioTestProcedure
				.newInstanceUsuarioRepository(entityManager);
		UsuarioLogado usuarioLogado = new UsuarioLogado(usuarioRepositoryImpl);
		ValidateComponente validateComponente = new ValidateComponente(
				validator);
		PerguntaRepository perguntaRepository = newInstancePerguntaRepository(entityManager);
		TesteRepository testeRepository = TesteTestProcedure
				.newInstanceTesteRepository(entityManager);
		AlternativaRepository alternativaRepository= new AlternativaRepositoryImpl(entityManager);
		PerguntaController controller = new PerguntaController(result, validator, testeView, usuarioLogado, validateComponente, perguntaRepository, testeRepository, sessionPlugin,alternativaRepository);
		return controller;
	}

}
