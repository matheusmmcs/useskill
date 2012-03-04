package br.ufpi.controllers;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.ufpi.annotation.Logado;
import br.ufpi.componets.UsuarioLogado;
import br.ufpi.models.Tarefa;
import br.ufpi.models.Teste;
import br.ufpi.repositories.TarefaRepository;

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
		validator.onErrorRedirectTo(tarefa);
		Teste teste = usuarioLogado.getTeste();
		tarefa.setTeste(teste);
		tarefaRepository.create(tarefa);
	}

	@Logado
	public void editarTarefa(Tarefa tarefa) {
		validator.validate(tarefa);
		validator.onErrorRedirectTo(tarefa);
		Teste teste = usuarioLogado.getTeste();
		tarefa.setTeste(teste);
		tarefaRepository.update(tarefa);
	}

	@Logado
	public void duplicarTarefa(Tarefa tarefa) {
		tarefa.setId(null);
		tarefaRepository.create(tarefa);
	}
}
