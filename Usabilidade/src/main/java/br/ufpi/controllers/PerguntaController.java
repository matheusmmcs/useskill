package br.ufpi.controllers;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.view.Results;
import br.ufpi.annotation.Logado;
import br.ufpi.componets.TesteSessionPlugin;
import br.ufpi.componets.TesteView;
import br.ufpi.componets.UsuarioLogado;
import br.ufpi.componets.ValidateComponente;
import br.ufpi.models.Alternativa;
import br.ufpi.models.Pergunta;
import br.ufpi.models.Questionario;
import br.ufpi.models.Teste;
import br.ufpi.models.vo.PerguntaVO;
import br.ufpi.repositories.PerguntaRepository;
import br.ufpi.repositories.TesteRepository;
import br.ufpi.util.GsonElements;

@Resource
public class PerguntaController extends BaseController {

	private final PerguntaRepository perguntaRepository;
	private final TesteRepository testeRepository;
	private final TesteSessionPlugin sessionPlugin;

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

	/**
	 * Usado para salvar as perguntas que são de um determinado teste
	 * 
	 * @param idTeste
	 * @param pergunta
	 */
	@Logado
	@Post("teste/{idTeste}/editar/passo2/salvar/pergunta")
	public void salvarPergunta(Long idTeste, Pergunta pergunta) {
		testeNaoLiberadoPertenceUsuarioLogado(idTeste);
		this.salvar(pergunta, idTeste, null);
		result.redirectTo(TesteController.class).passo2(idTeste);
	}

	/**
	 * 
	 * @param pergunta
	 * @param testeId
	 * @param questionario
	 *            qunado o questionario for Null significa que ele esta salvando
	 *            uma pergunta de um teste
	 */
	private void salvar(Pergunta pergunta, Long testeId,
			Questionario questionario) {
		validator.validate(pergunta);
		if (pergunta.getId() != null) {
			validateComponente.gerarErroCampoAlterado();
			validator.onErrorRedirectTo(this).criarPergunta(pergunta.getId());
		} else {
			validator.onErrorRedirectTo(TesteController.class).passo2(testeId);
		}
		if (questionario == null) {
			questionario = testeView.getTeste().getSatisfacao();
		}
		pergunta.setQuestionario(questionario);
		boolean tipo = pergunta.getTipoRespostaAlternativa() == null ? false
				: true;
		if (tipo && pergunta.getAlternativas() != null) {
			for (Alternativa alternativa : pergunta.getAlternativas()) {
				alternativa.setPergunta(pergunta);
			}
		} else {
			pergunta.setTipoRespostaAlternativa(false);
			pergunta.setAlternativas(null);
		}
		perguntaRepository.create(pergunta);
		Teste teste = GsonElements.addPergunta(pergunta.getId(),
				testeView.getTeste());
		testeRepository.update(teste);

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
		validateComponente.validarId(testeId);
		validator.validate(pergunta);
		validateComponente.validarObjeto(perguntaPertenceUsuario(
				pergunta.getId(), testeId));
		pergunta.setQuestionario(perguntaRepository.findQuestionario(pergunta
				.getId()));
		boolean objetiva = pergunta.getTipoRespostaAlternativa() == null ? true
				: pergunta.getTipoRespostaAlternativa();

		System.out.println(pergunta.getTipoRespostaAlternativa());
		if (!objetiva) {
			pergunta.setTipoRespostaAlternativa(false);
			pergunta.setAlternativas(null);
		} else {
			if (pergunta.getAlternativas() != null) {
				for (Alternativa alternativa : pergunta.getAlternativas()) {
					alternativa.setPergunta(pergunta);
				}
			} else {
				pergunta.setTipoRespostaAlternativa(false);
				pergunta.setAlternativas(null);
			}
		}
		perguntaRepository.deleteAlternativas(pergunta.getId());
		perguntaRepository.update(pergunta);

		result.redirectTo(TesteController.class).passo2(testeId);

	}

	@Logado
	@Get("teste/{testeId}/pergunta/{perguntaId}/apagar")
	public void deletarPergunta(Long testeId, Long perguntaId) {
		validateComponente.validarObjeto(testeId);
		validateComponente.validarObjeto(perguntaId);
		Pergunta perguntaPertenceUsuario = perguntaPertenceUsuario(perguntaId,
				testeId);
		validateComponente.validarObjeto(perguntaPertenceUsuario);
		Teste teste = testeView.getTeste();
		teste = GsonElements.removerPergunta(perguntaPertenceUsuario.getId(),
				teste);
		perguntaRepository.destroy(perguntaPertenceUsuario);
		testeRepository.update(teste);
		result.redirectTo(TesteController.class).passo2(testeId);

	}

	/**
	 * Analisa se teste.id e pergunta.id pertencem ao usuarioLogado.
	 * 
	 * @param perguntaId
	 * @param testeId
	 * @return
	 */
	private Pergunta perguntaPertenceUsuario(Long perguntaId, Long testeId) {
		testeNaoLiberadoPertenceUsuarioLogado(testeId);
		return perguntaRepository.perguntaPertenceUsuario(usuarioLogado
				.getUsuario().getId(), testeId, perguntaId, false);

	}

	/**
	 * * Analisa se o id do teste buscado pertence ao usuario logado e se o
	 * teste ainda não foi liberado, se não pertencer ao usuario buscado sera
	 * redirecionado para pagina Home
	 * 
	 * @param idTeste
	 *            buscado e analisado para ver se pertence ao usuario
	 * 
	 */
	private void testeNaoLiberadoPertenceUsuarioLogado(Long idTeste) {
		validateComponente.validarId(idTeste);
		Teste teste = testeRepository.getTestCriadoNaoLiberado(usuarioLogado
				.getUsuario().getId(), idTeste);
		validateComponente.validarObjeto(teste);
		testeView.setTeste(teste);

	}

	@Get
	@Logado
	public void getPergunta(long idPergunta) {
		validateComponente.validarId(idPergunta);
		validateComponente.validarId(sessionPlugin.getIdTeste());
		PerguntaVO perguntaPertenceTeste = perguntaRepository
				.perguntaPertenceTeste(sessionPlugin.getIdTeste(), idPergunta);
		validateComponente.validarObjeto(perguntaPertenceTeste);
		System.out.println(perguntaPertenceTeste);
		result.use(Results.json()).from(perguntaPertenceTeste).serialize();
	}

	public PerguntaController(Result result, Validator validator,
			TesteView testeView, UsuarioLogado usuarioLogado,
			ValidateComponente validateComponente,
			PerguntaRepository perguntaRepository,
			TesteRepository testeRepository, TesteSessionPlugin sessionPlugin) {
		super(result, validator, testeView, usuarioLogado, validateComponente);
		this.perguntaRepository = perguntaRepository;
		this.testeRepository = testeRepository;
		this.sessionPlugin = sessionPlugin;
	}

}
