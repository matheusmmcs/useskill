package br.ufpi.controllers;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.util.hibernate.extra.Load;
import br.ufpi.annotation.Logado;
import br.ufpi.componets.UsuarioLogado;
import br.ufpi.models.Pergunta;
import br.ufpi.models.Questionario;
import br.ufpi.models.Tarefa;
import br.ufpi.models.Teste;
import br.ufpi.repositories.TesteRepository;

@Resource
public class TesteController {
	private final Result result;
	private final TesteRepository testeRepository;
	private UsuarioLogado usuarioLogado;
	private final Validator validator;

	public TesteController(Result result, TesteRepository testeRepository,
			UsuarioLogado usuarioLogado, Validator validator) {
		super();
		this.result = result;
		this.testeRepository = testeRepository;
		this.usuarioLogado = usuarioLogado;
		this.validator = validator;
	}

	/**
	 * Contera todo o procedimento para criar um teste. Na view contera os dados
	 * do teste por meio da seção UsuarioLogado.teste
	 */
	@Get("teste/criar")
	// @Logado
	public void criarTeste() {
		if (usuarioLogado.getTeste() == null) {
			this.usuarioLogado.setTeste(new Teste());
			this.usuarioLogado.getTeste().setUsuarioCriador(
					usuarioLogado.getPessoa());

		}

	}

	@Post("teste/editar")
	public void passo1(String titulo, String tituloPublico,
			String textoIndroducao) {
		Teste teste = usuarioLogado.getTeste();
		teste.setTitulo(titulo);
		teste.setTituloPublico(tituloPublico);
		teste.setTextoIndroducao(textoIndroducao);
		testeRepository.saveUpdate(teste);
	}

	/**
	 * Usado para criar Tarefas
	 */
	@Post("teste/editar/tarefas")
	public void criarTarefa(Tarefa tarefa) {
		Teste teste = usuarioLogado.getTeste();
		List<Tarefa> tarefas = teste.getTarefas();
		if (tarefas == null)
			teste.setTarefas(new ArrayList<Tarefa>());
		teste.getTarefas().add(tarefa);
		testeRepository.update(teste);
	}

	// TODO ACho que deveria ter uma forma onde se o fluxo não fosse necessario
	// o kra relatar apenas a url final e inicial
	@Post("teste/editar/pergunta")
	public void tipoPergunta() {

	}

	// TODO Decidir se vai ser usado o id ou a seção
	public void criarPergunta() {
	}
}
