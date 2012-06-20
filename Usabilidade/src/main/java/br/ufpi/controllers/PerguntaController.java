package br.ufpi.controllers;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.ufpi.annotation.Logado;
import br.ufpi.componets.TesteView;
import br.ufpi.componets.UsuarioLogado;
import br.ufpi.componets.ValidateComponente;
import br.ufpi.models.Alternativa;
import br.ufpi.models.Pergunta;
import br.ufpi.models.Questionario;
import br.ufpi.models.Teste;
import br.ufpi.models.exceptions.PerguntaException;
import br.ufpi.repositories.PerguntaRepository;
import br.ufpi.repositories.TesteRepository;

@Resource
public class PerguntaController extends BaseController {

	private final PerguntaRepository perguntaRepository;
	private final TesteRepository testeRepository;

	public PerguntaController(Result result, Validator validator,
			TesteView testeView, UsuarioLogado usuarioLogado,
			ValidateComponente validateComponente,
			PerguntaRepository perguntaRepository,
			TesteRepository testeRepository) {
		super(result, validator, testeView, usuarioLogado, validateComponente);
		this.perguntaRepository = perguntaRepository;
		this.testeRepository = testeRepository;
	}

	@Logado
	@Get({ "teste/{testeId}/editar/passo2/criar/pergunta" })
	public Pergunta criarPergunta(Long testeId) {
		this.testeNaoLiberadoPertenceUsuarioLogado(testeId);
		return new Pergunta();
	}

	/**
	 * Método utilizado para editar pergunta.
	 * 
	 * @param testeId
         * @param pergunta 
	 * @return
	 */
	@Logado
	@Get("teste/{testeId}/editar/passo2/editar/{pergunta.id}/pergunta")
	public Pergunta editarPergunta(Long testeId, Pergunta pergunta) {
		return this.perguntaPertenceUsuario(pergunta.getId(), testeId);
	}

	// @Logado
	// @Post("teste/duplicar/pergunta")
	// public void duplicar(Long testeId, Long perguntaId) {
	// Pergunta pergunta = perguntaPertenceUsuario(perguntaId, testeId);
	// Pergunta perguntaCopia = new Pergunta();
	// perguntaCopia.setId(null);
	// perguntaRepository.create(perguntaCopia);
	// result.redirectTo(TesteController.class).passo2(testeId);
	// }

	/**
	 * 
	 * @param testeId
	 * @param pergunta
	 */
	@Logado
	@Post("teste/{testeId}/editar/passo2/salvar/pergunta")
	public void salvarPergunta(Long testeId, Pergunta pergunta) {
		testeNaoLiberadoPertenceUsuarioLogado(testeId);
		if (testeId != null) {
			validator.validate(pergunta);
			if (pergunta.getId() != null) {
				validator.onErrorRedirectTo(this).criarPergunta(
						pergunta.getId());
			} else {
				validator.onErrorRedirectTo(TesteController.class).passo2(
						testeId);
			}
			Questionario satisfacao = testeView.getTeste().getSatisfacao();
			pergunta.setQuestionario(satisfacao);
			boolean tipo = pergunta.getTipoRespostaAlternativa() == null ? false
					: true;
			if (tipo && pergunta.getAlternativas() != null) {
				System.out.println("Objetiva");
				System.out.println("Responder em qual momento"
						+ pergunta.isResponderFim());
				for (Alternativa alternativa : pergunta.getAlternativas()) {
					alternativa.setPergunta(pergunta);
				}
			} else {
				System.out.println("Responder em qual momento"
						+ pergunta.isResponderFim());
				System.out.println("SUBJETIVA");
				pergunta.setTipoRespostaAlternativa(false);
				pergunta.setAlternativas(null);
			}
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
		// TODO Resolver problema de Alternativas tem que deleta elas e criar de
		// novo
		if (testeId != null) {
			validator.validate(pergunta);
			testeNaoLiberadoPertenceUsuarioLogado(testeId);
			if (pergunta.getId() != null) {
				validator.onErrorRedirectTo(this).criarPergunta(
						pergunta.getId());
			} else {
				validator.onErrorRedirectTo(TesteController.class).passo2(
						testeId);
			}
			perguntaPertenceUsuario(pergunta.getId(), testeId);
			pergunta.setQuestionario(perguntaRepository
					.findQuestionario(pergunta.getId()));
			if (pergunta.getTipoRespostaAlternativa() == null) {

			}
			boolean objetiva = pergunta.getTipoRespostaAlternativa() == null ? true
					: pergunta.getTipoRespostaAlternativa();
			System.out
					.println(objetiva == true ? "Objetiva 12" : "Subjetiva 2");
			if (!objetiva) {
				System.out.println("Subjetiva 1");
				pergunta.setTipoRespostaAlternativa(false);
				pergunta.setAlternativas(null);
			} else {
				if (pergunta.getAlternativas() != null) {
					System.out.println("OBjetiva");
					for (Alternativa alternativa : pergunta.getAlternativas()) {
						alternativa.setPergunta(pergunta);
					}
				} else {
					System.out.println("Subjetiva");
					pergunta.setTipoRespostaAlternativa(false);
					pergunta.setAlternativas(null);
				}

			}
			perguntaRepository.deleteAlternativas(pergunta.getId());
			perguntaRepository.update(pergunta);
			result.redirectTo(TesteController.class).passo2(testeId);
		} else {
			result.redirectTo(LoginController.class).logado();
		}
	}

	@Logado
	@Post("teste/apagar/pergunta")
	public void deletarPergunta(Long testeId, Long perguntaId) {
		if (testeId != null && perguntaId != null) {
			Pergunta perguntaPertenceUsuario = null;
			perguntaPertenceUsuario = perguntaPertenceUsuario(perguntaId,
					testeId);
			perguntaRepository.destroy(perguntaPertenceUsuario);
			result.redirectTo(TesteController.class).passo2(testeId);
		} else {
			result.notFound();
		}
	}

	/**
	 * Analisa se teste.id e pergunta.id pertencem ao usuarioLogado.
	 * 
	 * @param idTeste
	 * @param idPergunta
	 * @param testeLiberado
	 *            se null ele vai mostrar perguntas de teste liberado e não
	 *            liberado
	 * @return
	 * @throws PerguntaException
	 *             Gerado quando não enconta a pergunta
	 */
	private Pergunta perguntaPertenceUsuario(Long perguntaId, Long testeId) {
		testeNaoLiberadoPertenceUsuarioLogado(testeId);
		Pergunta perguntaUsuario = perguntaRepository.perguntaPertenceUsuario(
				usuarioLogado.getUsuario().getId(), testeId, perguntaId, false);
		return perguntaUsuario;

	}

	/**
	 * * Analisa se o id do teste buscado pertence ao usuario logado e se o
	 * teste ainda não foi liberado, se não pertencer ao usuario buscado sera
	 * redirecionado para pagina 404.
	 * 
	 * @param idTeste
	 *            buscado e analisado para ver se pertence ao usuario
	 * 
	 */
	private void testeNaoLiberadoPertenceUsuarioLogado(Long idTeste) {
		if (idTeste != null) {
			Teste teste = testeRepository.getTestCriadoNaoLiberado(
					usuarioLogado.getUsuario().getId(), idTeste);
			if (teste != null) {
				testeView.setTeste(teste);
				return;
			}
		}
		validateComponente.redirecionarHome("teste.nao.pertence.usuario");

	}

}
