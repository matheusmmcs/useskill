package br.ufpi.controllers;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.ufpi.annotation.Logado;
import br.ufpi.componets.FluxoComponente;
import br.ufpi.componets.SessionActions;
import br.ufpi.componets.TarefaDetalhe;
import br.ufpi.componets.TesteSession;
import br.ufpi.componets.TesteView;
import br.ufpi.componets.UsuarioLogado;
import br.ufpi.componets.ValidateComponente;
import br.ufpi.models.Acao;
import br.ufpi.models.Convidado;
import br.ufpi.models.Fluxo;
import br.ufpi.models.FluxoIdeal;
import br.ufpi.models.FluxoUsuario;
import br.ufpi.models.Tarefa;
import br.ufpi.models.Teste;
import br.ufpi.models.TipoConvidado;
import br.ufpi.models.UsuarioTestePK;
import br.ufpi.repositories.ConvidadoRepository;
import br.ufpi.repositories.FluxoIdealRepository;
import br.ufpi.repositories.FluxoUsuarioRepository;
import br.ufpi.repositories.TarefaRepository;
import br.ufpi.repositories.TesteRepository;
import br.ufpi.util.BaseUrl;
import br.ufpi.util.CookieManager;
import br.ufpi.util.WebClientTester;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Resource
public class TarefaController extends BaseController {

	private final TarefaRepository tarefaRepository;
	private final TesteRepository testeRepository;
	private final FluxoIdealRepository fluxoIdealRepository;
	private final FluxoUsuarioRepository fluxoUsuarioRepository;
	private final ConvidadoRepository convidadoRepository;
	private SessionActions actions;
	private final FluxoComponente fluxoComponente;
	private final HttpServletRequest request;
	private final TesteSession testeSession;
	private final TarefaDetalhe tarefaDetalhe;
	private final CookieManager cookieManager;

	public TarefaController(Result result, Validator validator,
			TesteView testeView, UsuarioLogado usuarioLogado,
			ValidateComponente validateComponente,
			TarefaRepository tarefaRepository, TesteRepository testeRepository,
			FluxoIdealRepository fluxoIdealRepository,
			FluxoUsuarioRepository fluxoUsuarioRepository,
			ConvidadoRepository convidadoRepository, SessionActions actions,
			FluxoComponente fluxoComponente, HttpServletRequest request,
			TesteSession testeSession, TarefaDetalhe tarefaDetalhe,
			CookieManager cookieManager) {
		super(result, validator, testeView, usuarioLogado, validateComponente);
		this.tarefaRepository = tarefaRepository;
		this.testeRepository = testeRepository;
		this.fluxoIdealRepository = fluxoIdealRepository;
		this.fluxoUsuarioRepository = fluxoUsuarioRepository;
		this.convidadoRepository = convidadoRepository;
		this.actions = actions;
		this.fluxoComponente = fluxoComponente;
		this.request = request;
		this.testeSession = testeSession;
		this.tarefaDetalhe = tarefaDetalhe;
		this.cookieManager = cookieManager;
	}

	/**
	 * Usado para criar Tarefas analisa se o teste id passado não é igual a
	 * null, caso seja redireciona para a pagina de login
	 * 
	 * @param testeId
	 * @param tarefa
	 * @return
	 */
	@Logado
	@Get("teste/{testeId}/editar/passo2/criar/tarefa")
	public Tarefa criarTarefa(Long testeId, Tarefa tarefa) {
		this.testeNaoRealizadoPertenceUsuarioLogado(testeId);
		if (tarefa == null) {
			return new Tarefa();
		}
		return tarefa;
	}

	@Logado
	@Post("teste/salvar/tarefa")
	public void salvarTarefa(Tarefa tarefa, Long idTeste) {
		validateComponente.validarString(tarefa.getNome(), "tarefa.titulo");
		validateComponente.validarString(tarefa.getRoteiro(), "tarefa.roteito");
		validateComponente.validarString(tarefa.getUrlInicial(),
				"tarefa.urlInicial");
		validator.onErrorRedirectTo(this).criarTarefa(idTeste, tarefa);
		this.testeNaoRealizadoPertenceUsuarioLogado(idTeste);
		Teste teste = testeView.getTeste();
		tarefa.setTeste(teste);
		tarefaRepository.create(tarefa);
		result.redirectTo(TesteController.class).passo2(
				testeView.getTeste().getId());
	}

	/**
	 * Utilizado para alterar Tarefa
	 * 
	 * @param idTeste
	 *            Procupar por teste com identificador
	 * @param tarefa
	 *            Tarefa a ser alterada
	 * 
	 * @param isErro
	 *            True para verificar se esta vindo de uma tentativa de alterar
	 *            e ocorreu erro, neste caso não necessita pegar do banco e
	 *            tambem ele tem que retorna a pergunta do modo que foi
	 *            alterada.
	 * @return
	 */
	@Logado
	@Get()
	@Path(value = "teste/{idTeste}/editar/passo2/editar/{tarefa.id}/tarefa")
	public Tarefa editarTarefa(Long idTeste, Tarefa tarefa, boolean isErro) {
		Teste teste = new Teste();
		teste.setId(idTeste);
		testeView.setTeste(teste);
		if (isErro) {
			return tarefa;
		}
		return this.tarefaPertenceTeste(idTeste, tarefa.getId());
	}

	/**
	 * Pagina para editar Tarefa, se idTeste e TarefaId foor igual a null
	 * redireciona para página 404
	 * 
	 * @param tarefa
	 * @param idTeste
	 *            Identificador ao qual a tarefa pertence
	 */
	@Logado
	@Post("teste/tarefa/atualizar")
	public void updateTarefa(Tarefa tarefa, Long idTeste) {
		validateComponente.validarString(tarefa.getNome(), "tarefa.titulo");
		validateComponente.validarString(tarefa.getRoteiro(), "tarefa.roteito");
		validateComponente.validarString(tarefa.getUrlInicial(),
				"tarefa.urlInicial");
		validator.onErrorRedirectTo(this).editarTarefa(idTeste, tarefa, true);
		this.tarefaPertenceTesteNaoRealizado(tarefa.getId(), idTeste);
		Tarefa tarefaUpdate = tarefaRepository.find(tarefa.getId());
		tarefaUpdate.setRoteiro(tarefa.getRoteiro());
		tarefaUpdate.setNome(tarefa.getNome());

		if (!tarefaUpdate.getUrlInicial().equals(tarefa.getUrlInicial().trim())) {
			tarefaUpdate.setFluxoIdealPreenchido(false);
			tarefaUpdate.setUrlInicial(tarefa.getUrlInicial());
		}
		// TODO Apaagar o fluxo que ja tinha sido gravado caso
		tarefaRepository.update(tarefaUpdate);
		result.redirectTo(TesteController.class).passo2(idTeste);

	}

	/**
	 * Remove Tarefa caso tarefa não possa ser deletada vai pra 404.
	 * 
	 * @param idTarefa
	 *            identificador da Tarefa a ser deletada
	 * @param idTeste
	 */
	@Logado
	@Post("teste/removed/tarefa")
	public void removed(Long idTarefa, Long idTeste) {
		Tarefa tarefa = this.tarefaPertenceTesteNaoRealizado(idTarefa, idTeste);
		tarefaRepository.destroy(tarefa);
		result.redirectTo(TesteController.class).passo2(idTeste);
	}

	@Logado
	@Post("tarefa/save/fluxo/ideal")
	/**
	 * Recebe uma String de dados.
	 *
	 * @param dados Estes dados serão passados em forma de String e convertido
	 * em uma lista de Ações
	 * @param completo Realata se terminou a captura de informações de um teste
	 */
	public void saveFluxoIdeal(String dados, Boolean completo) {
		Long tarefaId = this.testeSession.getTarefa().getId();
		System.out.println("Action: saveFluxoIdeal");
		if (completo) {
			System.out.println("Completo");
		}
		System.out.println(dados + " - " + completo + " - " + tarefaId);
		saveFluxo(dados, completo, tarefaId, TipoConvidado.TESTER);
	}

	@Logado
	@Post("tarefa/save/fluxo/usuario")
	public String saveFluxoUsuario(String dados, Boolean completo, Long tarefaId) {
		System.out.println("Action: saveFluxoUsuario");
		// System.out.println(dados + " - " + completo + " - " + tarefaId);
		if (completo) {
			System.out.println("Completo");
		}
		saveFluxo(dados, completo, tarefaId,fluxoComponente.getTipoConvidado());
		return "Teste";
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
			TipoConvidado tipoConvidado) {
		Gson gson = new Gson();
		Type collectionType = new TypeToken<Collection<Acao>>() {
		}.getType();
		Collection<Acao> ints2 = gson.fromJson(dados, collectionType);
		List<Acao> acoes = new ArrayList<Acao>(ints2);
		this.actions.addAction(acoes);
		if (completo) {
			switch (tipoConvidado) {
			case TESTER:
				gravaFluxo(tarefaId, tipoConvidado);
				break;
			case USER:

				gravaFluxo(this.fluxoComponente.getTarefaVez(), tipoConvidado);
				break;

			default:
				break;

			}
			actions.destroy();
		}
		// actions.setUrlProxima();
	}

	/**
	 * Grava o fluxo de usuario de uma determinada Tarefa. Destroy o
	 * FluxoComponente de ações.
	 * 
	 * @param tarefaId
	 *            O identificador da tarefa que tera o fluxo gravado
	 */
	private void gravaFluxo(Long tarefaId, TipoConvidado tipoConvidado) {
		// System.out.println("GRAVA FLUXO DE USUARIO");
		Fluxo fluxo = new Fluxo();
		fluxo.setUsuario(usuarioLogado.getUsuario());
		List<Acao> acoes = actions.getAcoes();
		for (Acao acao : acoes) {
			acao.setFluxo(fluxo);
		}
		fluxo.setAcoes(actions.getAcoes());
		// fluxoRepository.create(fluxo);
		saveTipoFluxo(tipoConvidado, fluxo);
		fluxoComponente.getProximaTarefa();
		// System.out.println("AGORA vez esta" +
		// fluxoComponente.getTarefaVez());
		if (fluxoComponente.getTarefaVez() == 0) {
			// System.out
			// .println("Tarefa = 0 -> redirecionar para responder as ultimas perguntas");
			result.redirectTo(RespostaController.class).exibir();
		}
	}

	/**
	 * @param tipoConvidado
	 * @param fluxo
	 */
	private void saveTipoFluxo(TipoConvidado tipoConvidado, Fluxo fluxo) {
		switch (tipoConvidado) {
		case TESTER:
			FluxoIdeal fluxoIdeal = new FluxoIdeal();
			fluxoIdeal.setFluxo(fluxo);
			fluxoIdealRepository.create(fluxoIdeal);
			break;
		case USER:
			FluxoUsuario fluxoUsuario = new FluxoUsuario();
			fluxoUsuario.setFluxo(fluxo);
			fluxoUsuarioRepository.create(fluxoUsuario);
			break;
		default:
			break;
		}
	}

	/**
	 * Criado para iniciar os objetos na sessão antes da gravação dos testes
	 * 
	 * @param idTarefa
	 *            Identificador da Tarefa a ser gravada
	 * @param idTeste
	 *            Identificador do teste ao qual a tarefa pertence
	 */
	@Logado
	@Post()
	@Path(value = "tarefa/gravar")
	public void iniciarGravacaoTester(Long idTarefa, Long idTeste) {
		this.tarefaPertenceTesteNaoRealizado(idTarefa, idTeste);
		this.testeSession.setTeste(testeRepository.find(idTeste));
		this.testeSession.setTarefa(tarefaRepository.find(idTarefa));
		this.result.redirectTo(TarefaController.class).loadtasktester();
	}

	/**
	 * Método que carrega uma página para realizar a tarefa. Nesta página,
	 * possui um iframe onde são testadas as ações do TESTADOR.
	 * 
	 * @return
	 */
	@Logado
	@Get()
	@Post()
	public void loadtasktester() {
		Long idTarefa = testeSession.getTarefa().getId();
		System.out.println("Action: loadTaskTester");
		Tarefa tarefa = tarefaPertenceTeste(testeSession.getTeste().getId(),
				idTarefa);
		String url = BaseUrl.getInstance(request);
		System.out.println("${String} =" + url
				+ "/tarefa/loadactiontester?url=" + tarefa.getUrlInicial()
				+ "&idTarefa=" + idTarefa);
		tarefaDetalhe.setRoteiro(tarefa.getRoteiro());
		tarefaDetalhe.setUrl(url + "/tarefa/loadactiontester?url="
				+ tarefa.getUrlInicial() + "&idTarefa=" + idTarefa);
	}

	/**
	 * Método que carrega uma página e adaptada para a Usabilitool. Nesta página
	 * serão armazeandas as ações do TESTADOR.
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unused", "unchecked", "rawtypes" })
	@Logado
	@Get()
	@Post()
	public String loadactiontester() {
		Long idTarefa = testeSession.getTarefa().getId();
		System.out.println("loadActionTester");
		Tarefa tarefa = tarefaPertenceTeste(testeSession.getTeste().getId(),
				idTarefa);

		String url = request.getParameter("url");
		Map<String, String[]> parametrosRecebidos = request.getParameterMap();
		String metodo = request.getMethod();

		Enumeration headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = (String) headerNames.nextElement();
		}
		if (url != null) {
			return WebClientTester.loadPage(BaseUrl.getInstance(request)
					+ "/tarefa/loadactiontester", url,
					Integer.parseInt(idTarefa.toString()), parametrosRecebidos,
					metodo, cookieManager);
		} else {
			return "erro";
		}
	}

	/**
	 * Metodo que carrega uma página para realizar a tarefa. Nest pagina, possui
	 * um iframe onde são testadas as ações do USUARIO. O id da tarefa é
	 * recebido a partir da sessão FluxoComponente
	 * 
	 * @return
	 */
	@Logado
	@Get()
	@Post()
	public TarefaDetalhe loadtaskuser() {
		System.out.println("Action: loadTaskUser");
		Long idTarefa = fluxoComponente.getTarefaVez();
		System.out.println(idTarefa + "Tarefa na vez");
		if (idTarefa == 0) {
			System.out.println("Tarefa igual a zero");
			Convidado convidado = new Convidado(new UsuarioTestePK(
					usuarioLogado.getUsuario(), testeSession.getTeste()));
			convidado.setRealizou(true);
			convidadoRepository.update(convidado);
			fluxoComponente.setRespondendoInicio(false);
			result.redirectTo(RespostaController.class).exibir();
			return null;
		}

		Tarefa tarefa = getTarefa(idTarefa);
		// return BaseUrl.getInstance(request)+"/tarefa/loadactionuser?url="+
		// tarefa.getUrlInicial() + "&idTarefa=" + idTarefa;
		// System.out.println("TESTAR TAREFA" + tarefa);
		TarefaDetalhe tarefadetalhe = new TarefaDetalhe();
		tarefadetalhe.setRoteiro(tarefa.getRoteiro());
		tarefadetalhe.setUrl(BaseUrl.getInstance(request)
				+ "/tarefa/loadactionuser?url=" + tarefa.getUrlInicial()
				+ "&idTarefa=" + idTarefa);
		return tarefadetalhe;
	}

	/**
	 * Método que carrega uma página e adaptada para a Usabilitool. Nesta página
	 * serão armazeandas as ações do TESTADOR.
	 * 
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	@Logado
	@Get
	public String loadactionuser() {
		System.out.println("loadActionUser ");
		Long idTarefa = fluxoComponente.getTarefaVez();
		if (idTarefa == 0) {
			Convidado convidado = new Convidado(new UsuarioTestePK(
					usuarioLogado.getUsuario(), testeSession.getTeste()));
			convidado.setRealizou(true);
			convidadoRepository.update(convidado);
			return null;
		}

		Tarefa tarefa = getTarefa(idTarefa);
		String url = request.getParameter("url");
		System.out.println("URL" + url);
		Map<String, String[]> parametrosRecebidos = request.getParameterMap();
		String metodo = request.getMethod();

		Enumeration headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = (String) headerNames.nextElement();
			// System.out.println(headerName);
			// System.out.println("HN(HN): " + request.getHeader(headerName));
		}
		if (url == null) {
			url = tarefa.getUrlInicial();
		}
		return WebClientTester.loadPage(BaseUrl.getInstance(request)
				+ "/tarefa/loadactionuser", url,
				Integer.parseInt(idTarefa.toString()), parametrosRecebidos,
				metodo, cookieManager);
	}

	// private void gravaFluxoIdeal(Long tarefaId) {
	// System.out.println("Grava FluxoTarefa IDEAL");
	// Long idTeste = testeSession.getTeste().getId();
	// Tarefa tarefa = this.tarefaPertenceTeste(idTeste, tarefaId);
	// Fluxo fluxoIdeal = new Fluxo();
	// fluxoIdeal.setUsuario(usuarioLogado.getUsuario());
	// List<Acao> acoes = actions.getAcoes();
	// for (Acao acao : acoes) {
	// acao.setFluxo(fluxoIdeal);
	// }
	// fluxoIdeal.setAcoes(acoes);
	// tarefa.setFluxoIdeal(fluxoIdeal);
	// tarefa.setFluxoIdealPreenchido(true);
	// tarefaRepository.update(tarefa);
	// tarefaDetalhe.destroy();
	// System.out.println("Tarefa é pra ter sido salva");
	// }

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
		validateComponente.validarId(idTeste);
		validateComponente.validarId(idTarefa);
		Tarefa tarefa = tarefaRepository.pertenceTeste(idTarefa, idTeste,
				usuarioLogado.getUsuario().getId());
		validateComponente.validarObjeto(tarefa);
		return tarefa;
	}

	/**
	 * Analisa se um determinado Teste não liberado pertence ao usuario
	 * 
	 * @param idTeste
	 *            Identificador do Teste a ser procurado
	 */
	private void testeNaoRealizadoPertenceUsuarioLogado(Long idTeste) {
		validateComponente.validarId(idTeste);
		Teste teste = testeRepository.getTestCriadoNaoLiberado(usuarioLogado
				.getUsuario().getId(), idTeste);
		validateComponente.validarObjeto(teste);
		testeView.setTeste(teste);

	}

	/**
	 * Analisa se uma determinada tarefa pertence a um teste ainda não
	 * Realizado.
	 * 
	 * @param tarefaId
	 * @return
	 */
	private Tarefa tarefaPertenceTesteNaoRealizado(Long tarefaId, Long idTeste) {
		validateComponente.validarId(idTeste);
		validateComponente.validarId(tarefaId);
		Tarefa tarefaRetorno = tarefaRepository.perteceTesteNaoRealizado(
				tarefaId, idTeste, usuarioLogado.getUsuario().getId());
		validateComponente.validarObjeto(tarefaRetorno);
		return tarefaRetorno;
	}

	private Tarefa getTarefa(Long idTarefa) {
		return tarefaRepository.find(idTarefa);

	}

}
