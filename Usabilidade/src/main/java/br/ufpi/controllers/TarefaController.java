package br.ufpi.controllers;

import java.util.List;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.ufpi.annotation.Logado;
import br.ufpi.componets.UsuarioLogado;
import br.ufpi.models.Acao;
import br.ufpi.models.Fluxo;
import br.ufpi.models.FluxoIdeal;
import br.ufpi.models.Tarefa;
import br.ufpi.models.Teste;
import br.ufpi.repositories.TarefaRepository;

//TODO refazer estes passos para tarefa
@Resource
public class TarefaController {
	private final Result result;
	private final TarefaRepository tarefaRepository;
	private UsuarioLogado usuarioLogado;
	private final Validator validator;

	public TarefaController(Result result, TarefaRepository tarefaRepository,
			UsuarioLogado usuarioLogado, Validator validator) {
		super();
		this.result = result;
		this.tarefaRepository = tarefaRepository;
		this.usuarioLogado = usuarioLogado;
		this.validator = validator;
	}

	/**
	 * Usado para criar Tarefas
	 */
	@Logado
	@Get("teste/{testeId}/editar/passo2/criar/tarefa")
	public Tarefa criarTarefa(Long testeId, Tarefa tarefa) {
		return tarefa;
	}

	@Logado
	@Post
	public void salvarTarefa(Tarefa tarefa) {
		validator.validate(tarefa);
		validator.onErrorRedirectTo(this).criarTarefa(
				usuarioLogado.getTeste().getId(), tarefa);
		Teste teste = usuarioLogado.getTeste();
		tarefa.setTeste(teste);
		tarefaRepository.create(tarefa);
	}

	@Logado
	public void editarTarefa(Tarefa tarefa) {
		validator.validate(tarefa);
		validator.onErrorRedirectTo(this);
		Teste teste = usuarioLogado.getTeste();
		tarefa.setTeste(teste);
		tarefaRepository.update(tarefa);
	}

	@Logado
	public void duplicarTarefa(Tarefa tarefa) {
		tarefa.setId(null);
		tarefaRepository.create(tarefa);
	}

	@Logado
	@Post("tarefa/sace/fluxo/ideal")
	public void saveFluxoIdeal(List<Acao> acoes, Long idTarefa) {
		System.out.println("Tarefa"+ idTarefa);
		System.out.println("Acoes"+acoes);
//		Tarefa tarefa = tarefaPertenceTeste(usuarioLogado.getTeste().getId(),
//				idTarefa);
//		FluxoIdeal fluxoIdeal = tarefa.getFluxoIdeal();
//		if(fluxoIdeal==null)
//			fluxoIdeal= new FluxoIdeal();
//		fluxoIdeal.setAcoes(acoes);
//		tarefa.setFluxoIdeal(fluxoIdeal);
//		tarefaRepository.update(tarefa);

	}

	/** Analisa se Tarefa pertence ao id do Teste passado e se ela pertence ao usuario Logado
	 * Caso não Obedessa as requisições redireciona para a página 404
	 * @param idTeste
	 * @param idTarefa
	 * @return Tarefa requisitada pelo id. Caso não encontre não retorna nada, pois pagina é redirecionada para a página 404
	 */
	private Tarefa tarefaPertenceTeste(Long idTeste, Long idTarefa) {
		Tarefa tarefa = tarefaRepository.pertenceTeste(idTarefa, idTeste,
				usuarioLogado.getUsuario().getId());
		if (tarefa == null)
			result.notFound();
		return tarefa;
	}
}
