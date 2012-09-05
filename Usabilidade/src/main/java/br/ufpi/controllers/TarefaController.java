package br.ufpi.controllers;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.view.Results;
import br.ufpi.annotation.Logado;
import br.ufpi.componets.TesteSessionPlugin;
import br.ufpi.componets.TesteView;
import br.ufpi.componets.UsuarioLogado;
import br.ufpi.componets.ValidateComponente;
import br.ufpi.models.Action;
import br.ufpi.models.Fluxo;
import br.ufpi.models.FluxoIdeal;
import br.ufpi.models.FluxoUsuario;
import br.ufpi.models.Tarefa;
import br.ufpi.models.Teste;
import br.ufpi.models.TipoConvidado;
import br.ufpi.models.vo.TarefaVO;
import br.ufpi.repositories.FluxoIdealRepository;
import br.ufpi.repositories.FluxoUsuarioRepository;
import br.ufpi.repositories.TarefaRepository;
import br.ufpi.repositories.TesteRepository;
import br.ufpi.util.GsonElements;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Resource
public class TarefaController extends BaseController {

	private final TarefaRepository tarefaRepository;
	private final TesteRepository testeRepository;
	private final FluxoIdealRepository fluxoIdealRepository;
	private final FluxoUsuarioRepository fluxoUsuarioRepository;
	private final TesteSessionPlugin testeSessionPlugin;

	public TarefaController(Result result, Validator validator,
			TesteView testeView, UsuarioLogado usuarioLogado,
			ValidateComponente validateComponente,
			TarefaRepository tarefaRepository, TesteRepository testeRepository,
			FluxoIdealRepository fluxoIdealRepository,
			FluxoUsuarioRepository fluxoUsuarioRepository,
			TesteSessionPlugin testeSessionPlugin) {
		super(result, validator, testeView, usuarioLogado, validateComponente);
		this.tarefaRepository = tarefaRepository;
		this.testeRepository = testeRepository;
		this.fluxoIdealRepository = fluxoIdealRepository;
		this.fluxoUsuarioRepository = fluxoUsuarioRepository;
		this.testeSessionPlugin = testeSessionPlugin;
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
		Teste testeUpdate = GsonElements.addTarefa(tarefa.getId(),
				testeView.getTeste());
		testeRepository.update(testeUpdate);
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
		this.instanceIdTesteView(idTeste);
		if (isErro) {
			return tarefa;
		}
		Tarefa tarefaPertenceTeste = this.tarefaPertenceTeste(idTeste,
				tarefa.getId());
		testeView.setTeste(testeRepository.find(idTeste));
		return tarefaPertenceTeste;
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
			tarefaUpdate.setUrlInicial(tarefa.getUrlInicial());
		}
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
	@Get("teste/{idTeste}/tarefa/{idTarefa}/apagar")
	public void removed(Long idTarefa, Long idTeste) {
		Tarefa tarefa = this.tarefaPertenceTesteNaoRealizado(idTarefa, idTeste);
		testeView.setTeste(testeRepository.find(idTeste));
		Teste teste = GsonElements.removerTarefa(tarefa.getId(),
				testeView.getTeste());
		tarefaRepository.destroy(tarefa);
		testeRepository.update(teste);
		result.redirectTo(TesteController.class).passo2(idTeste);
	}

	@Logado
	@Post("tarefa/save/fluxo")
	public void saveFluxo(String dados, Long tarefaId) {
		gravaFluxo(dados, tarefaId, testeSessionPlugin.getTipoConvidado());
	}

	/**
	 * Grava o fluxo de usuario de uma determinada Tarefa. Destroy o
	 * FluxoComponente de ações.
	 * 
	 * @param tarefaId
	 *            O identificador da tarefa que tera o fluxo gravado
	 */
	private void gravaFluxo(String dados, Long tarefaId,
			TipoConvidado tipoConvidado) {
		Fluxo fluxo = new Fluxo();
		fluxo.setUsuario(usuarioLogado.getUsuario());
		Gson gson = new Gson();
		Type collectionType = new TypeToken<Collection<Action>>() {
		}.getType();
		Collection<Action> ints2 = gson.fromJson(dados, collectionType);
		List<Action> acoes = new ArrayList<Action>(ints2);
		fluxo.setAcoes(acoes);
		saveTipoFluxo(tipoConvidado, fluxo);
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
		}
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

	@Post("/tarefa/roteiro")
	@Logado
	public void getRoteiro(Long idTarefa) {
		validateComponente.validarId(idTarefa);
		String roteiro = tarefaRepository.getRoteiro(idTarefa,
				testeSessionPlugin.getIdTeste());
		validateComponente.validarObjeto(roteiro);
		result.use(Results.json()).from(roteiro).serialize();
	}

	@Logado
	@Get("/tarefa/{idTarefa}/json")
	public void getTarefa(Long idTarefa) {
		validateComponente.validarId(idTarefa);
		validateComponente.validarId(testeSessionPlugin.getIdTeste());
		TarefaVO tarefaVO = tarefaRepository.getTarefaVO(idTarefa,
				testeSessionPlugin.getIdTeste());
		System.out.println(tarefaVO);
		validateComponente.validarObjeto(tarefaVO);
		result.use(Results.json()).from(tarefaVO).serialize();
	}

	/**
	 * Passos do método
	 * 
	 * 1º passar idTarefa,idTeste,idPessoa(Validar itens passados) e tipo de
	 * fluxo que ele realizou
	 * 
	 * 2º verificar se o usario é criador do teste
	 * 
	 * 
	 * 
	 * 
	 * */
	@Logado
	@Get
	public void exibirActions(Long testeId, Long tarefaId, Long usarioId) {
		validateComponente.validarId(testeId);
		validateComponente.validarId(tarefaId);
		validateComponente.validarId(usarioId);
		Long usuarioCriadorId = usuarioLogado.getUsuario().getId();
		Fluxo fluxo = tarefaRepository.getFluxo(testeId, tarefaId, usarioId,
				usuarioCriadorId);
		System.out.println(fluxo.getId());
		validateComponente.validarObjeto(fluxo);
		result.include("fluxo", fluxo);
	}

	public void listUsers(Long tarefaId) {
		validateComponente.validarId(tarefaId);
		
	
	}
}
