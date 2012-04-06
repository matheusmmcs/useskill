package br.ufpi.controllers;

import java.util.List;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.ufpi.annotation.Logado;
import br.ufpi.componets.SessionActions;
import br.ufpi.componets.SessionFluxoTarefa;
import br.ufpi.componets.UsuarioLogado;
import br.ufpi.models.Acao;
import br.ufpi.models.FluxoIdeal;
import br.ufpi.models.FluxoUsuario;
import br.ufpi.models.Tarefa;
import br.ufpi.models.Teste;
import br.ufpi.repositories.FluxoUsuarioRepository;
import br.ufpi.repositories.TarefaRepository;
import br.ufpi.repositories.TesteRepository;
import br.ufpi.util.TarefaDetalhe;
import br.ufpi.util.WebClientTester;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;

import java.util.Enumeration;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.bcel.generic.ACONST_NULL;
import org.omg.PortableServer.REQUEST_PROCESSING_POLICY_ID;

@Resource
public class TarefaController {

	private final Result result;
	private final TarefaRepository tarefaRepository;
	private UsuarioLogado usuarioLogado;
	private final Validator validator;
	private final TesteRepository testeRepository;
	private final FluxoUsuarioRepository fluxoUsuarioRepository;
	private SessionActions actions;
	private final SessionFluxoTarefa fluxoTarefa;
	private final HttpServletRequest request;

	public TarefaController(Result result, TarefaRepository tarefaRepository,
			UsuarioLogado usuarioLogado, Validator validator,
			TesteRepository testeRepository,
			FluxoUsuarioRepository fluxoUsuarioRepository,
			SessionActions actions, SessionFluxoTarefa fluxoTarefa,
			HttpServletRequest request) {
		super();
		this.result = result;
		this.tarefaRepository = tarefaRepository;
		this.usuarioLogado = usuarioLogado;
		this.validator = validator;
		this.testeRepository = testeRepository;
		this.fluxoUsuarioRepository = fluxoUsuarioRepository;
		this.actions = actions;
		this.fluxoTarefa = fluxoTarefa;
		this.request = request;
	}

	/**
	 * Usado para criar Tarefas analisa se o teste id passado não é igual a
	 * null, caso seja redireciona para uma pagina 404
	 */
	@Logado
	@Get("teste/{testeId}/editar/passo2/criar/tarefa")
	public Tarefa criarTarefa(Long testeId, Tarefa tarefa) {
		if (testeId != null) {
			this.testeNaoRealizadoPertenceUsuarioLogado(testeId);
			if (tarefa == null) {
				return new Tarefa();
			}
			return tarefa;
		} else {
			result.notFound();
			return null;
		}
	}

	@Logado
	@Post("teste/salvar/tarefa")
	public void salvarTarefa(Tarefa tarefa) {
		validator.validate(tarefa);
		validator.onErrorRedirectTo(this).criarTarefa(
				usuarioLogado.getTeste().getId(), tarefa);
		Teste teste = usuarioLogado.getTeste();
		tarefa.setTeste(teste);
		tarefaRepository.create(tarefa);
		result.redirectTo(TesteController.class).passo2(
				usuarioLogado.getTeste().getId());
	}

	/**
	 * Pagina para editar Tarefa, se idTeste e TarefaId foor igual a null
	 * redireciona para página 404
	 * 
	 * @param idTeste
	 *            Identificador ao qual a tarefa pertence
	 * @param tarefaId
	 *            Identificador da tarefa a ser editada
	 * @return
	 */
	@Logado
	@Get()
	@Path(value = "teste/{idTeste}/editar/passo2/editar/{tarefa.id}/tarefa")
	public Tarefa editarTarefa(Long idTeste, Tarefa tarefa, boolean isErro) {
		if (idTeste != null && tarefa.getId() != null) {
			if (isErro) {
				return tarefa;
			}
			return this.tarefaPertenceTeste(idTeste, tarefa.getId());
		} else {
			result.notFound();
			return null;
		}
	}

	/**
	 * Utilizado para alterar Tarefa
	 * 
	 * @param idTeste
	 *            Procupar por teste com identificador
	 * @param tarefa
	 *            Tarefa a ser alterada
	 * @param isErro
	 *            True para verificar se esta vindo de uma tentativa de alterar
	 *            e ocorreu erro, neste caso não necessita pegar do banco e
	 *            tambem ele tem que retorna a pergunta do modo que foi
	 *            alterada.
	 * @return
	 */
	@Logado
	@Post("teste/tarefa/atualizar")
	public void updateTarefa(Tarefa tarefa) {
		validator.validate(tarefa);

		validator.onErrorRedirectTo(this).editarTarefa(
				usuarioLogado.getTeste().getId(), tarefa, true);
		Tarefa tarefaUpdate = tarefaRepository.find(tarefa.getId());
		tarefaUpdate.setRoteiro(tarefa.getRoteiro());
		tarefaUpdate.setNome(tarefa.getNome());
		tarefaUpdate.setUrlInicial(tarefa.getUrlInicial());
		tarefaRepository.update(tarefaUpdate);
		result.redirectTo(TesteController.class).passo2(
				usuarioLogado.getTeste().getId());

	}

	/**
	 * Remove Tarefa caso tarefa não possa ser deletada vai pra 404.
	 * 
	 * @param idTarefa
	 *            identificador da Tarefa a ser deletada
	 */
	@Logado
	@Post("teste/removed/tarefa")
	public void removed(Long idTarefa) {
		if (idTarefa != null) {
			Tarefa tarefa = this.tarefaPertenceTesteNaoRealizado(idTarefa);
			tarefaRepository.destroy(tarefa);
			result.redirectTo(TesteController.class).passo2(
					usuarioLogado.getTeste().getId());
		} else {
			result.notFound();
		}
	}

	@Logado
	@Post("tarefa/save/fluxo/ideal")
	/**
	 * Recebe uma String de dados. 
	 * @param dados Estes dados serão passados em forma de String e convertido em uma lista de Ações
	 * @param completo Realata se terminou a captura de informações de um teste
	 */
	public void saveFluxoIdeal(String dados, Boolean completo, Long tarefaId) {
		System.out.println("Action: saveFluxoIdeal");
		System.out.println(dados + " - " + completo + " - " + tarefaId);
		saveFluxo(dados, completo, tarefaId, true);
	}

	@Logado
	@Post("tarefa/save/fluxo/usuario")
	public void saveFluxoUsuario(String dados, Boolean completo, Long tarefaId) {
		System.out.println("Action: saveFluxoUsuario");
		System.out.println(dados + " - " + completo + " - " + tarefaId);
		saveFluxo(dados, completo, tarefaId, false);

	}

	/**
	 * Salva os Fluxos e altera a url para a pxoxima pagina aberta.
	 * 
	 * @param dados
	 *            Ações em formato Json
	 * @param completo
	 *            Determina true se teste estiverCompleto
	 * @param tarefaId
	 *            Identificador da Tarefa que o fluxo pertence
	 * @param fluxoIdeal
	 *            True se for fluxo ideal
	 */
	private void saveFluxo(String dados, Boolean completo, Long tarefaId,
			boolean fluxoIdeal) {
		Gson gson = new Gson();
		Type collectionType = new TypeToken<Collection<Acao>>() {
		}.getType();
		Collection<Acao> ints2 = gson.fromJson(dados, collectionType);
		List<Acao> acoes = new ArrayList<Acao>(ints2);
		this.actions.addAction(acoes);
		if (completo) {
			if (fluxoIdeal) {
				gravaFluxoIdeal(tarefaId);
			} else {
				System.out.println("Salva Fluxo Usuario");
				gravaFluxoUSuario(this.fluxoTarefa.getVez());

			}
			actions.destroy();
		}
		// actions.setUrlProxima();
	}

	/**
	 * Grava o fluxo de usuario de uma determinada Tarefa. Destroy o Fluxo de
	 * ações.
	 * 
	 * @param tarefaId
	 */
	private void gravaFluxoUSuario(Long tarefaId) {
		FluxoUsuario fluxoUsuario = new FluxoUsuario();
		fluxoUsuario.setUsuario(usuarioLogado.getUsuario());
		List<Acao> acoes = actions.getAcoes();
		for (Acao acao : acoes) {
			acao.setFluxo(fluxoUsuario);
		}
		fluxoUsuario.setAcoes(actions.getAcoes());
		fluxoUsuarioRepository.create(fluxoUsuario);
		fluxoTarefa.getProximo();
		System.out.println("Salvou Fluxo Usuario");
		
	}

	@Logado
	@Get()
	public TarefaDetalhe carregar(Long idTarefa) {
		System.out.println("Action: Carregar");
		Tarefa tarefa = tarefaPertenceTeste(usuarioLogado.getTeste().getId(),
				idTarefa);
		TarefaDetalhe tarefadetalhe = new TarefaDetalhe();

		System.out
				.println("${String} = http://localhost:8080/Usabilidade/tarefa/visualizar?url="
						+ tarefa.getUrlInicial() + "&idTarefa=" + idTarefa);

		tarefadetalhe.setRoteiro(tarefa.getRoteiro());
		tarefadetalhe
				.setUrl("http://localhost:8080/Usabilidade/tarefa/visualizar?url="
						+ tarefa.getUrlInicial() + "&idTarefa=" + idTarefa);
		return tarefadetalhe;
	}

	@Logado
	@Get()
	public TarefaDetalhe testar() {
		System.out.println("Action: Testar");
		Long idTarefa = fluxoTarefa.getVez();
		Tarefa tarefa = getTarefa(idTarefa);
		// return "http://localhost:8080/Usabilidade/tarefa/acoes?url="+
		// tarefa.getUrlInicial() + "&idTarefa=" + idTarefa;

		TarefaDetalhe tarefadetalhe = new TarefaDetalhe();

		tarefadetalhe.setRoteiro(tarefa.getRoteiro());
		tarefadetalhe
				.setUrl("http://localhost:8080/Usabilidade/tarefa/acoes?url="
						+ tarefa.getUrlInicial() + "&idTarefa=" + idTarefa);
		return tarefadetalhe;
	}

	/**
	 * Utilizado para mostrar ao usuario a tarefa definida. Usado apenas para o
	 * Testador
	 * 
	 * @param idTarefa
	 *            identicado da Tarefa a ser realizada
	 * @param url
	 *            url a ser exibida
	 * @return
	 */
	@Logado
	@Get()
	public String visualizar(Long idTarefa) {
		System.out.println("Action: Visualizar: getId:"
				+ usuarioLogado.getTeste().getId() + " - idTarefa:" + idTarefa);
		Tarefa tarefa = tarefaPertenceTeste(usuarioLogado.getTeste().getId(),
				idTarefa);

		String url = request.getParameter("url");
		Map<String, String[]> parametrosRecebidos = request.getParameterMap();
		String metodo = request.getMethod();

		Enumeration headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = (String) headerNames.nextElement();
			// System.out.println(headerName);
			// System.out.println("HN(HN): " + request.getHeader(headerName));
		}

		if (url != null) {
			return WebClientTester.loadPage(
					"http://localhost:8080/Usabilidade/tarefa/visualizar", url,
					Integer.parseInt(idTarefa.toString()), parametrosRecebidos,
					metodo);
		} else {
			return "erro";
		}
	}

	/**
	 * Utilizado para mostrar ao usuario a tarefa definida. Usado apenas para os
	 * usuarios Participantes
	 * 
	 * @param idTarefa
	 *            identicado da Tarefa a ser realizada
	 * @return
	 */
	@Logado
	@Get
	public String acoes() {
		Long idTarefa = fluxoTarefa.getVez();
		System.out.println("Action: Visualizar: getId:"
				+ usuarioLogado.getTeste().getId() + " - idTarefa:" + idTarefa);
		Tarefa tarefa = getTarefa(idTarefa);
		String url = request.getParameter("url");
		Map<String, String[]> parametrosRecebidos = request.getParameterMap();
		String metodo = request.getMethod();

		Enumeration headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = (String) headerNames.nextElement();
			// System.out.println(headerName);
			// System.out.println("HN(HN): " + request.getHeader(headerName));
		}

		if (url != null) {
			return WebClientTester.loadPage(
					"http://localhost:8080/Usabilidade/tarefa/acoes", url,
					Integer.parseInt(idTarefa.toString()), parametrosRecebidos,
					metodo);
		} else {
			return WebClientTester.loadPage(
					"http://localhost:8080/Usabilidade/tarefa/acoes",
					tarefa.getUrlInicial(),
					Integer.parseInt(idTarefa.toString()), parametrosRecebidos,
					metodo);

		}

	}

	private void gravaFluxoIdeal(Long tarefaId) {
		Tarefa tarefa = this.tarefaPertenceTeste(usuarioLogado.getTeste()
				.getId(), tarefaId);

		FluxoIdeal fluxoIdeal = new FluxoIdeal();
		fluxoIdeal.setUsuario(usuarioLogado.getUsuario());
		List<Acao> acoes = actions.getAcoes();
		for (Acao acao : acoes) {
			acao.setFluxo(fluxoIdeal);
		}

		fluxoIdeal.setAcoes(acoes);
		tarefa.setFluxoIdeal(fluxoIdeal);
		tarefa.setFluxoIdealPreenchido(true);
		tarefaRepository.update(tarefa);
		result.redirectTo(TesteController.class).passo2(
				usuarioLogado.getTeste().getId());

	}

	/**
	 * Analisa se Tarefa pertence ao id do Teste passado.Caso ela pertence ao
	 * usuario Logado Caso não Obedeça as requisições redireciona para a página
	 * 404
	 * 
	 * @param idTeste
	 * @param idTarefa
	 * @return Tarefa requisitada pelo id. Caso não encontre não retorna nada,
	 *         pois pagina é redirecionada para a página 404
	 */
	private Tarefa tarefaPertenceTeste(Long idTeste, Long idTarefa) {
		Tarefa tarefa = tarefaRepository.pertenceTeste(idTarefa, idTeste,
				usuarioLogado.getUsuario().getId());
		if (tarefa == null) {
			result.notFound();
		}
		return tarefa;
	}

	/**
	 * Analisa se um determinado Teste pertence ao usuario
	 * 
	 * @param idTeste
	 *            Identificador do Teste a ser procurado
	 */
	private void testeNaoRealizadoPertenceUsuarioLogado(Long idTeste) {
		Teste teste = testeRepository.getTestCriadoNaoLiberado(usuarioLogado
				.getUsuario().getId(), idTeste);
		if (teste != null) {
			usuarioLogado.setTeste(teste);
		} else {
			result.notFound();
		}
	}

	/**
	 * Analisa se uma determinada tarefa pertence a um teste ainda não
	 * Realizado.
	 * 
	 * @param tarefaId
	 * @return
	 */
	private Tarefa tarefaPertenceTesteNaoRealizado(Long tarefaId) {
		Tarefa tarefaRetorno = tarefaRepository.perteceTesteNaoRealizado(
				tarefaId, usuarioLogado.getTeste().getId(), usuarioLogado
						.getUsuario().getId());
		if (tarefaRetorno == null) {
			result.notFound();
			return null;
		}
		return tarefaRetorno;
	}

	private Tarefa getTarefa(Long idTarefa) {
		return tarefaRepository.find(idTarefa);

	}
}
