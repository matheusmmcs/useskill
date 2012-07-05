package br.ufpi.controllers.procedure;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

import br.com.caelum.vraptor.util.test.JSR303MockValidator;
import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.ufpi.commons.HttpRequestTest;
import br.ufpi.componets.FluxoComponente;
import br.ufpi.componets.SessionActions;
import br.ufpi.componets.TesteSession;
import br.ufpi.componets.TesteView;
import br.ufpi.componets.UsuarioLogado;
import br.ufpi.componets.ValidateComponente;
import br.ufpi.controllers.TarefaController;
import br.ufpi.models.Acao;
import br.ufpi.models.Fluxo;
import br.ufpi.models.FluxoIdeal;
import br.ufpi.models.Tarefa;
import br.ufpi.models.Teste;
import br.ufpi.models.Usuario;
import br.ufpi.repositories.FluxoUsuarioRepository;
import br.ufpi.repositories.TarefaRepository;
import br.ufpi.repositories.UsuarioRepository;
import br.ufpi.repositories.Implement.FluxoUsuarioRepositoryImpl;
import br.ufpi.repositories.Implement.TarefaRepositoryImpl;

public class TarefaTestProcedure {
	public static TarefaController newInstanceTarefaController(
			EntityManager entityManager, MockResult result) {
		HttpServletRequest request = new HttpRequestTest();
		TesteView testeView = new TesteView();
		JSR303MockValidator validator = new JSR303MockValidator();
		UsuarioRepository usuarioRepositoryImpl = UsuarioTestProcedure
				.newInstanceUsuarioRepository(entityManager);
		UsuarioLogado usuarioLogado = new UsuarioLogado(usuarioRepositoryImpl);
		ValidateComponente validateComponente = new ValidateComponente(
				validator);
		TesteSession testeSession = null;
		FluxoComponente fluxo = null;
		SessionActions actions = null;
		TarefaController controller = new TarefaController(result, validator,
				testeView, usuarioLogado, validateComponente,
				newInstanceTarefaRepository(entityManager),
				TesteTestProcedure.newInstanceTesteRepository(entityManager),
				newInstanceFluxoUsuarioRepository(entityManager),
				UsuarioTestProcedure
						.newInstanceConvidadoRepository(entityManager),
				actions, fluxo, request, testeSession);
		return controller;
	}

	public static TarefaController newInstanceTarefaController(
			EntityManager entityManager, MockResult result,
			TesteSession testeSession) {
		HttpServletRequest request = new HttpRequestTest();
		TesteView testeView = new TesteView();
		MockValidator validator = new MockValidator();
		UsuarioRepository usuarioRepositoryImpl = UsuarioTestProcedure
				.newInstanceUsuarioRepository(entityManager);
		UsuarioLogado usuarioLogado = new UsuarioLogado(usuarioRepositoryImpl);
		ValidateComponente validateComponente = new ValidateComponente(
				validator);
		FluxoComponente fluxo = null;
		SessionActions actions = null;
		TarefaController controller = new TarefaController(result, validator,
				testeView, usuarioLogado, validateComponente,
				newInstanceTarefaRepository(entityManager),
				TesteTestProcedure.newInstanceTesteRepository(entityManager),
				newInstanceFluxoUsuarioRepository(entityManager),
				UsuarioTestProcedure
						.newInstanceConvidadoRepository(entityManager),
				actions, fluxo, request, testeSession);
		return controller;
	}

	public static TarefaRepository newInstanceTarefaRepository(
			EntityManager entityManager) {
		return new TarefaRepositoryImpl(entityManager);
	}

	public static FluxoUsuarioRepository newInstanceFluxoUsuarioRepository(
			EntityManager entityManager) {
		return new FluxoUsuarioRepositoryImpl(entityManager);
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
		tarefa.setFluxoIdealPreenchido(fluxoIdealPreenchido);
		return tarefa;
	}

	public static FluxoIdeal newInstanceFluxoIdeal(Usuario usuario) {
		FluxoIdeal fluxoIdeal = new FluxoIdeal();
//		fluxoIdeal.setFluxo(newInstanceFluxo(usuario));
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

	public static List<Acao> newInstanceAcoes(Fluxo fluxo) {
		ArrayList<Acao> acoes = new ArrayList<Acao>();
		for (int i = 0; i < 10; i++) {
			Acao acao = new Acao();
			acao.setConteudo("conteudo");
			acao.setFluxo(fluxo);
			acao.setPosicaoPaginaX(10 * i);
			acao.setPosicaoPaginaY(10 * i);
			acao.setTagName("tag");
			acao.setUrl("www.globo.com");
			acoes.add(acao);
		}
		return acoes;
	}
}