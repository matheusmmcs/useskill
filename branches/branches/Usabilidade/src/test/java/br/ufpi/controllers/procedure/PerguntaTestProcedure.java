package br.ufpi.controllers.procedure;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.ufpi.componets.TesteView;
import br.ufpi.componets.UsuarioLogado;
import br.ufpi.componets.ValidateComponente;
import br.ufpi.controllers.PerguntaController;
import br.ufpi.models.Alternativa;
import br.ufpi.models.Pergunta;
import br.ufpi.models.Teste;
import br.ufpi.repositories.PerguntaRepository;
import br.ufpi.repositories.TarefaRepository;
import br.ufpi.repositories.TesteRepository;
import br.ufpi.repositories.UsuarioRepository;
import br.ufpi.repositories.Implement.PerguntaRepositoryImpl;
import br.ufpi.repositories.Implement.TarefaRepositoryImpl;

public class PerguntaTestProcedure {
	public static PerguntaController newInstanceTarefaController(
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
		TarefaRepository tarefaRepository = new TarefaRepositoryImpl(
				entityManager);
		PerguntaController controller = new PerguntaController(result,
				validator, testeView, usuarioLogado, validateComponente,
				perguntaRepository, testeRepository, tarefaRepository);
		return controller;
	}

	public static PerguntaRepository newInstancePerguntaRepository(
			EntityManager entityManager) {
		return new PerguntaRepositoryImpl(entityManager);
	}

	public static Pergunta newInstancePerguntaAlternativa(Teste teste,
			String texto, int numeroAlternativas, boolean responderFim,
			String titulo) {
		Pergunta pergunta = new Pergunta();
		pergunta.setQuestionario(teste.getSatisfacao());
		pergunta.setResponderFim(responderFim);
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
		pergunta.setResponderFim(responderFim);
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
		pergunta.setResponderFim(responderFim);
		pergunta.setTexto(texto);
		pergunta.setTipoRespostaAlternativa(false);
		pergunta.setTitulo(titulo);
		return pergunta;
	}

	public static Pergunta newInstancePerguntaEscrita(String texto,
			boolean responderFim, String titulo) {
		Pergunta pergunta = new Pergunta();
		pergunta.setResponderFim(responderFim);
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
			alternativa.setPergunta(pergunta);
			alternativas.add(alternativa);
		}
		return alternativas;
	}

}
