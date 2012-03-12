package br.ufpi.controllers;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.ufpi.annotation.Logado;
import br.ufpi.componets.UsuarioLogado;
import br.ufpi.models.Pergunta;
import br.ufpi.models.Questionario;
import br.ufpi.models.Teste;
import br.ufpi.repositories.PerguntaRepository;

@Resource
public class PerguntaController {

	private final Result result;
	private final PerguntaRepository perguntaRepository;
	private UsuarioLogado usuarioLogado;
	private final Validator validator;

	public PerguntaController(Result result,
			PerguntaRepository perguntaRepository, UsuarioLogado usuarioLogado,
			Validator validator) {
		super();
		this.result = result;
		this.perguntaRepository = perguntaRepository;
		this.usuarioLogado = usuarioLogado;
		this.validator = validator;
	}

	@Logado
	@Get({ "teste/{testeId}/editar/passo2/criar/pergunta" })
	public Pergunta criarPergunta(Long testeId) {
		return new Pergunta();
	}

	/**
	 * Método utilizado para editar pergunta.
	 * 
	 * @param testeId
	 * @param idPergunta
	 * @return
	 */
	@Logado
	@Get("teste/{testeId}/editar/passo2/editar/{pergunta.id}/pergunta")
	public Pergunta criarPergunta(Long testeId, Pergunta pergunta) {
		return this.perguntaPertenceUsuario(pergunta.getId(), testeId);
	}
//TODO Fazer um metodo para percorrer todos os objetos e ir clonando alterando apenas o id
	@Logado
	@Post("teste/duplicar/pergunta")
	public void duplicar(Long testeId, Long perguntaId) {
		Pergunta pergunta = perguntaPertenceUsuario(perguntaId, testeId);
		Pergunta perguntaCopia = new Pergunta();
		perguntaCopia.setId(null);
		perguntaRepository.create(perguntaCopia);
		result.redirectTo(TesteController.class).passo2(testeId);
	}

	/**
	 * 
	 * @param testeId
	 * @param pergunta
	 */
	@Logado
	@Post("teste/{testeId}/editar/passo2/salvar/pergunta")
	public void salvarPergunta(Long testeId, Pergunta pergunta) {
		if (testeId != null) {
			validator.validate(pergunta);
			if (pergunta.getId() != null) {
				validator.onErrorRedirectTo(this).criarPergunta(
						pergunta.getId());
			} else {
				validator.onErrorRedirectTo(TesteController.class).passo2(
						testeId);
			}
			Questionario satisfacao = usuarioLogado.getTeste().getSatisfacao();
			pergunta.setQuestionario(satisfacao);
			perguntaRepository.create(pergunta);
			result.redirectTo(TesteController.class).passo2(testeId);
		} else {
			result.redirectTo(LoginController.class).logado();
		}
	}

	/**
	 * Atualiza pergunta passado, mas seo teste id passado for igual a null
	 * redireciona home do usuario. Se o Caso o id da pergunta for invalido ele
	 * é redirecionado para criar a pergunta.
	 * 
	 * @param testeId
	 * @param pergunta
	 */
	@Logado
	@Put("teste/{testeId}/editar/passo2/salvar/pergunta")
	public void atualizarPergunta(Long testeId, Pergunta pergunta) {
		if (testeId != null) {
			validator.validate(pergunta);
			if (pergunta.getId() != null) {
				validator.onErrorRedirectTo(this).criarPergunta(
						pergunta.getId());
			} else {
				validator.onErrorRedirectTo(TesteController.class).passo2(
						testeId);
			}
			pergunta.setQuestionario(perguntaRepository
					.findQuestionario(pergunta.getId()));
			perguntaRepository.update(pergunta);
			result.redirectTo(TesteController.class).passo2(testeId);
		} else {
			result.redirectTo(LoginController.class).logado();
		}
	}

	/**
	 * Analisa se teste.id e pergunta.id pertencem ao usuarioLogado.
	 * 
	 * @param idTeste
	 * @param idPergunta
	 * @return
	 */
	private Pergunta perguntaPertenceUsuario(Long perguntaId, Long testeId) {
		Pergunta perguntaUsuario = perguntaRepository.perguntaPertenceUsuario(
				usuarioLogado.getUsuario().getId(), testeId, perguntaId);
		if (perguntaUsuario == null)
			result.notFound();
		return perguntaUsuario;

	}

	/**
	 * Analisa se PErgunta e teste pertencem ao usuarioLogado.
	 * 
	 * @param pergunta
	 * @param teste
	 * @return
	 */
	private Pergunta perguntaPertenceUsuario(Pergunta pergunta, Teste teste) {
		return perguntaPertenceUsuario(pergunta.getId(), teste.getId());
	}
}
