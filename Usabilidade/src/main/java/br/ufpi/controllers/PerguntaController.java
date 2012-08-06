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
import br.ufpi.models.Tarefa;
import br.ufpi.models.Teste;
import br.ufpi.repositories.PerguntaRepository;
import br.ufpi.repositories.TarefaRepository;
import br.ufpi.repositories.TesteRepository;

@Resource
public class PerguntaController extends BaseController {

	private final PerguntaRepository perguntaRepository;
	private final TesteRepository testeRepository;
	private final TarefaRepository tarefaRepository;

	public PerguntaController(Result result, Validator validator,
			TesteView testeView, UsuarioLogado usuarioLogado,
			ValidateComponente validateComponente,
			PerguntaRepository perguntaRepository,
			TesteRepository testeRepository, TarefaRepository tarefaRepository) {
		super(result, validator, testeView, usuarioLogado, validateComponente);
		this.perguntaRepository = perguntaRepository;
		this.testeRepository = testeRepository;
		this.tarefaRepository = tarefaRepository;
	}

	@Logado
	@Get({ "teste/{testeId}/editar/passo2/criar/pergunta" })
	public Pergunta criarPergunta(Long testeId) {
		this.testeNaoLiberadoPertenceUsuarioLogado(testeId);
		return new Pergunta();
	}

	@Logado
	@Get(value = "teste/{idTeste}/editar/passo2/editar/{idTarefa}/tarefa/questionario/criar/pergunta")
	public Pergunta criarPerguntaTarefa(Long idTeste, Long idTarefa) {
		Tarefa tarefa = this.tarefaPertenceTesteNaoRealizado(idTarefa, idTeste);
		result.include(tarefa);
		super.instanceIdTesteView(idTeste);
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
	 * Usado para salvar perguntas de uma determinada tarefa
	 * 
	 * @param idTeste
	 *            identificador do teste que a pergunta pertence
	 * @param pergunta
	 *            pergunta a ser salva
	 * @param idTarefa
	 *            identificador da tarefa que a pergunta pertence
	 */
	@Logado
	@Post("teste/{idTeste}/editar/passo2/editar/{idTarefa}/tarefa/questionario/salvar/pergunta")
	public void salvarPergunta(Long idTeste, Pergunta pergunta, Long idTarefa) {
		Tarefa tarefa = this.tarefaPertenceTesteNaoRealizado(idTarefa, idTeste);
		salvar(pergunta, idTeste, tarefa.getQuestionario());
		this.result.redirectTo(TarefaController.class).questionario(idTeste,
				idTarefa);
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
		this.atualizar(testeId, pergunta);
		result.redirectTo(TesteController.class).passo2(testeId);

	}

	/**
	 * Usado para salvar apenas perguntas de tarefas. Atualiza pergunta passada,
	 * mas se o teste id passado for igual a null redireciona home do usuario.
	 * Se o Caso o id da pergunta for invalido ele é redirecionado para criar a
	 * pergunta.
	 * 
	 * @param idTeste
	 * @param pergunta
	 * @param idTarefa
	 */
	@Logado
	@Put("teste/{idTeste}/editar/passo2/atualizar/{idTarefa}/tarefa/questionario/salvar/pergunta")
	public void atualizarPergunta(Long idTeste, Pergunta pergunta, Long idTarefa) {
		this.atualizar(idTeste, pergunta);
		result.redirectTo(TarefaController.class).questionario(idTeste,
				idTarefa);

	}

	@Logado
	@Post("teste/apagar/pergunta")
	public void deletarPergunta(Long testeId, Long perguntaId) {
		delete(testeId, perguntaId, null);
		result.redirectTo(TesteController.class).passo2(testeId);

	}

	@Logado
	@Get("teste/{testeId}/apagar/tarefa/{tarefaId}/pergunta/{perguntaId}")
	public void deletarPergunta(Long testeId, Long perguntaId, Long tarefaId) {
		validateComponente.validarId(tarefaId);
		System.out.println(testeId);
		System.out.println(tarefaId);
		System.out.println(perguntaId);
		delete(testeId, perguntaId, tarefaId);
		result.redirectTo(TarefaController.class).questionario(testeId,
				tarefaId);
	}

	/**
	 * @param testeId
	 * @param perguntaId
	 */
	private void delete(Long testeId, Long perguntaId, Long tarefaId) {
		validateComponente.validarObjeto(testeId);
		validateComponente.validarObjeto(perguntaId);
		Pergunta perguntaPertenceUsuario = null;
		if (tarefaId==null){
			perguntaPertenceUsuario = perguntaPertenceUsuario(perguntaId,
					testeId);
		System.out.println("Nao era para ele entratr");	
		}
		else{
			perguntaPertenceUsuario = perguntaPertenceTarefa(perguntaId,
					testeId,tarefaId);
			System.out.println("sql errada");
		}validateComponente.validarObjeto(perguntaPertenceUsuario);
		perguntaRepository.destroy(perguntaPertenceUsuario);
	}

	private Pergunta perguntaPertenceTarefa(Long perguntaId, Long testeId,Long tarefaId) {
		testeNaoLiberadoPertenceUsuarioLogado(testeId);
		return perguntaRepository.perguntaPertenceUsuarioETarefa(usuarioLogado
				.getUsuario().getId(), testeId, perguntaId, tarefaId);
	}

	private void atualizar(Long testeId, Pergunta pergunta) {
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
}
