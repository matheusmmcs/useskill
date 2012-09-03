package br.ufpi.controllers.procedure;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.caelum.vraptor.util.test.JSR303MockValidator;
import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.ufpi.componets.TesteSessionPlugin;
import br.ufpi.componets.TesteView;
import br.ufpi.componets.UsuarioLogado;
import br.ufpi.componets.ValidateComponente;
import br.ufpi.controllers.TarefaController;
import br.ufpi.models.Action;
import br.ufpi.models.Fluxo;
import br.ufpi.models.FluxoIdeal;
import br.ufpi.models.Tarefa;
import br.ufpi.models.Teste;
import br.ufpi.models.Usuario;
import br.ufpi.repositories.FluxoIdealRepository;
import br.ufpi.repositories.FluxoRepository;
import br.ufpi.repositories.FluxoUsuarioRepository;
import br.ufpi.repositories.TarefaRepository;
import br.ufpi.repositories.TesteRepository;
import br.ufpi.repositories.UsuarioRepository;
import br.ufpi.repositories.Implement.FluxoIdealRepositoryImpl;
import br.ufpi.repositories.Implement.FluxoRepositoryImpl;
import br.ufpi.repositories.Implement.FluxoUsuarioRepositoryImpl;
import br.ufpi.repositories.Implement.TarefaRepositoryImpl;
import br.ufpi.repositories.Implement.TesteRepositoryImpl;

public class TarefaTestProcedure {
	public static TarefaController newInstanceTarefaController(
			EntityManager entityManager, MockResult result) {
		TesteView testeView = new TesteView();
		JSR303MockValidator validator = new JSR303MockValidator();
		UsuarioRepository usuarioRepositoryImpl = UsuarioTestProcedure
				.newInstanceUsuarioRepository(entityManager);
		UsuarioLogado usuarioLogado = new UsuarioLogado(usuarioRepositoryImpl);
		ValidateComponente validateComponente = new ValidateComponente(
				validator);
		TarefaRepository tarefaRepository = newInstanceTarefaRepository(entityManager);
		TesteRepository testeRepository = TesteTestProcedure
				.newInstanceTesteRepository(entityManager);
		FluxoUsuarioRepository fluxoUsuarioRepository = new FluxoUsuarioRepositoryImpl(
				entityManager);
		FluxoIdealRepository fluxoIdealRepository = new FluxoIdealRepositoryImpl(
				entityManager);
		TesteSessionPlugin testeSessionPlugin= new TesteSessionPlugin();
		TarefaController controller = new TarefaController(result, validator, testeView, usuarioLogado, validateComponente, tarefaRepository, testeRepository, fluxoIdealRepository, fluxoUsuarioRepository, testeSessionPlugin);
		return controller;
	}

	public static TarefaController newInstanceTarefaController(
			EntityManager entityManager, MockResult result, Long idTarefa,
			Long idTeste) {
		TesteView testeView = new TesteView();
		MockValidator validator = new MockValidator();
		UsuarioRepository usuarioRepositoryImpl = UsuarioTestProcedure
				.newInstanceUsuarioRepository(entityManager);
		UsuarioLogado usuarioLogado = new UsuarioLogado(usuarioRepositoryImpl);
		ValidateComponente validateComponente = new ValidateComponente(
				validator);
		TarefaRepository tarefaRepository = new TarefaRepositoryImpl(
				entityManager);
		TesteRepository testeRepository = new TesteRepositoryImpl(entityManager);
		TesteSessionPlugin testeSessionPlugin= new TesteSessionPlugin();
		testeSessionPlugin.setIdTeste(idTeste);
		FluxoUsuarioRepository fluxoUsuarioRepository = new FluxoUsuarioRepositoryImpl(
				entityManager);
		FluxoIdealRepository fluxoIdealRepository = new FluxoIdealRepositoryImpl(
				entityManager);
		TarefaController controller = new TarefaController(result, validator, testeView, usuarioLogado, validateComponente, tarefaRepository, testeRepository, fluxoIdealRepository, fluxoUsuarioRepository, testeSessionPlugin);
		return controller;
	}

	public static TarefaRepository newInstanceTarefaRepository(
			EntityManager entityManager) {
		return new TarefaRepositoryImpl(entityManager);
	}

	public static FluxoRepository newInstanceFluxoRepository(
			EntityManager entityManager) {
		return new FluxoRepositoryImpl(entityManager);
	}

	public static Tarefa newInstanceTarefa(String urlInicial, String roteiro,
			String nome, Teste teste) {
		Tarefa tarefa = new Tarefa();
		tarefa.setNome(nome);
		tarefa.setRoteiro(roteiro);
		tarefa.setUrlInicial(urlInicial);
		tarefa.setTeste(teste);
		return tarefa;
	}

	public static Tarefa newInstanceTarefa(String urlInicial, String roteiro,
			String nome) {
		Tarefa tarefa = new Tarefa();
		tarefa.setNome(nome);
		tarefa.setRoteiro(roteiro);
		tarefa.setUrlInicial(urlInicial);
		return tarefa;
	}

	public static Tarefa newInstanceTarefa(Long id, String urlInicial,
			String roteiro, String nome) {

		Tarefa tarefa = new Tarefa();
		tarefa.setNome(nome);
		tarefa.setId(id);
		tarefa.setRoteiro(roteiro);
		tarefa.setUrlInicial(urlInicial);
		return tarefa;
	}

	public static Tarefa newInstanceTarefa(String urlInicial, String roteiro,
			String nome, Teste teste, boolean fluxoIdealPreenchido) {
		Tarefa tarefa = new Tarefa();
		tarefa.setNome(nome);
		tarefa.setRoteiro(roteiro);
		tarefa.setUrlInicial(urlInicial);
		tarefa.setTeste(teste);
		return tarefa;
	}

	public static FluxoIdeal newInstanceFluxoIdeal(Usuario usuario) {
		FluxoIdeal fluxoIdeal = new FluxoIdeal();
		// fluxoIdeal.setFluxo(newInstanceFluxo(usuario));
		return fluxoIdeal;
	}

	public static Fluxo newInstanceFluxo(Usuario usuario) {

		Fluxo fluxo = new Fluxo();
		fluxo.setDataInicio(new Date(System.currentTimeMillis()));
		fluxo.setDataFim(new Date(System.currentTimeMillis()));
		fluxo.setAcoes(newInstanceAcoes(fluxo));
		fluxo.setUsuario(usuario);

		return fluxo;
	}

	public static List<Action> newInstanceAcoes(Fluxo fluxo) {
		ArrayList<Action> acoes = new ArrayList<Action>();
		for (int i = 0; i < 10; i++) {
			Action acao = new Action();
			acao.setsContent("conteudo");
			acao.setFluxo(fluxo);
			acao.setsPosX(10 * i);
			acao.setsPosY(10 * i);
			acao.setsTagName("tag");
			acao.setsUrl("www.globo.com");
			acoes.add(acao);
		}
		return acoes;
	}
}