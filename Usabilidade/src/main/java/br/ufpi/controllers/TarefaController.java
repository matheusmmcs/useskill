package br.ufpi.controllers;

import java.util.List;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.ufpi.annotation.Logado;
import br.ufpi.componets.UsuarioLogado;
import br.ufpi.models.Acao;
import br.ufpi.models.FluxoIdeal;
import br.ufpi.models.Tarefa;
import br.ufpi.models.Teste;
import br.ufpi.repositories.TarefaRepository;
import br.ufpi.repositories.TesteRepository;

//TODO refazer estes passos para tarefa
@Resource
public class TarefaController {
	private final Result result;
	private final TarefaRepository tarefaRepository;
	private UsuarioLogado usuarioLogado;
	private final Validator validator;
	private final TesteRepository testeRepository;

	public TarefaController(Result result, TarefaRepository tarefaRepository,
			UsuarioLogado usuarioLogado, Validator validator,
			TesteRepository testeRepository) {
		super();
		this.result = result;
		this.tarefaRepository = tarefaRepository;
		this.usuarioLogado = usuarioLogado;
		this.validator = validator;
		this.testeRepository = testeRepository;
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
			if (tarefa == null)
				return new Tarefa();
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
		tarefa.setFluxoIdeal(new FluxoIdeal());
		tarefaRepository.create(tarefa);
		// TODO REdirecionamento esta Faltando
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
	@Get()
	@Path(value = "teste/{idTeste}/editar/passo2/editar/{tarefa.id}/tarefa")
	public Tarefa editarTarefa(Long idTeste, Tarefa tarefa, boolean isErro) {
		if (idTeste != null && tarefa.getId() != null) {
			if (isErro)
				return tarefa;
			return this.tarefaPertenceTeste(idTeste, tarefa.getId());
		} else {
			result.notFound();
			return null;
		}
	}

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
		// TODO REdirecionamento esta Faltando

	}

	/**Remove Tarefa caso tarefa não possa ser deletada vai pra 404.
	 * @param idTarefa identificador da Tarefa a ser deletada
	 */
	@Logado
	@Post("teste/removed/tarefa")
	public void removed(Long idTarefa) {
		if (idTarefa != null) {
			Tarefa tarefa = this.tarefaPertenceTesteNaoRealizado(idTarefa);
			tarefaRepository.destroy(tarefa);
			result.redirectTo(TesteController.class).passo2(
					usuarioLogado.getTeste().getId());
		}else{
		result.notFound();
		}
	}

	@Logado
	@Post("tarefa/save/fluxo/ideal")
	public void saveFluxoIdeal(List<Acao> acoes, Long idTarefa) {
		System.out.println("Tarefa" + idTarefa);
		System.out.println("Acoes" + acoes);
		// Tarefa tarefa = tarefaPertenceTeste(usuarioLogado.getTeste().getId(),
		// idTarefa);
		// FluxoIdeal fluxoIdeal = tarefa.getFluxoIdeal();
		// if(fluxoIdeal==null)
		// fluxoIdeal= new FluxoIdeal();
		// fluxoIdeal.setAcoes(acoes);
		// tarefa.setFluxoIdeal(fluxoIdeal);
		// tarefaRepository.update(tarefa);

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
		if (tarefa == null)
			result.notFound();
		return tarefa;
	}

	/**
	 * Analisa se um determinado Teste pertence ao usuario
	 * 
	 * @param idTeste
	 *            Identificador do Teste a ser procurado
	 */
	private void testeNaoRealizadoPertenceUsuarioLogado(Long idTeste) {
		Teste teste = testeRepository.testCriadoNaoRealizado(usuarioLogado
				.getUsuario().getId(), idTeste);
		if (teste != null) {
			usuarioLogado.setTeste(teste);
		} else {
			result.notFound();
		}
	}

	/**Analisa se uma determinada tarefa pertence a um teste ainda não Realizado.
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
}
