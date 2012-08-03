package br.ufpi.controllers;

//TODO Validar se as perguntas estão sendo respondida e mostra uma mensagem
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.Validations;
import br.ufpi.annotation.Logado;
import br.ufpi.componets.FluxoComponente;
import br.ufpi.componets.TesteView;
import br.ufpi.componets.UsuarioLogado;
import br.ufpi.componets.ValidateComponente;
import br.ufpi.models.Alternativa;
import br.ufpi.models.Pergunta;
import br.ufpi.models.RespostaAlternativa;
import br.ufpi.models.RespostaEscrita;
import br.ufpi.repositories.PerguntaRepository;
import br.ufpi.repositories.RespostaAlternativaRepository;
import br.ufpi.repositories.RespostaEscritaRepository;
import br.ufpi.repositories.TesteRepository;

@Resource
public class RespostaController extends BaseController {
	private final RespostaEscritaRepository escritaRepository;
	private final RespostaAlternativaRepository alternativaRepository;
	private final PerguntaRepository perguntaRepository;
	private final FluxoComponente fluxoComponente;
	private final TesteRepository testeRepository;

	public RespostaController(Result result, Validator validator,
			TesteView testeView, UsuarioLogado usuarioLogado,
			ValidateComponente validateComponente,
			RespostaEscritaRepository escritaRepository,
			RespostaAlternativaRepository alternativaRepository,
			PerguntaRepository perguntaRepository,
			FluxoComponente fluxoComponente, TesteRepository testeRepository) {
		super(result, validator, testeView, usuarioLogado, validateComponente);
		this.escritaRepository = escritaRepository;
		this.alternativaRepository = alternativaRepository;
		this.perguntaRepository = perguntaRepository;
		this.fluxoComponente = fluxoComponente;
		this.testeRepository = testeRepository;
	}


	@Logado
	@Post("/teste/salvar/resposta/escrita")
	public void salvarRespostaEscrita(final String resposta) {
		validateComponente.validarString(resposta, "resposta");
		validator.onErrorRedirectTo(this).exibir();
		RespostaEscrita respostaEscrita = new RespostaEscrita();
		respostaEscrita.setResposta(resposta);
		respostaEscrita.setUsuario(usuarioLogado.getUsuario());
		Pergunta pergunta = instanciarPergunta(false);
		respostaEscrita.setPergunta(pergunta);
		escritaRepository.create(respostaEscrita);
		proximaPergunta();
		result.redirectTo(this).exibir();
	}


	@Logado
	@Post("/teste/salvar/resposta/alternativa")
	public void salvarRespostaAlternativa(Alternativa alternativa) {
		RespostaAlternativa respostaAlternativa = new RespostaAlternativa();
		respostaAlternativa.setAlternativa(alternativa);
		Pergunta pergunta = instanciarPergunta(true);
		alternaviaPertencePergunta(alternativa, pergunta);
		respostaAlternativa.setPergunta(pergunta);
		respostaAlternativa.setUsuario(usuarioLogado.getUsuario());
		alternativaRepository.create(respostaAlternativa);
		proximaPergunta();
		result.redirectTo(this).exibir();
	}

	/**
	 * Analisa se a alternativa passada pertence a pergunta respondida
	 * 
	 * @param alternativa
	 * @param pergunta
	 */
	private void alternaviaPertencePergunta(Alternativa alternativa,
			Pergunta pergunta) {
		boolean alternativaPertencePergunta = false;
		for (Alternativa alternativa2 : pergunta.getAlternativas()) {
			if (alternativa2.getId().equals(alternativa.getId())) {
				alternativaPertencePergunta = true;
			}
		}
		if (!alternativaPertencePergunta) {
			validator.checking(new Validations() {
				{
					that(false, "pergunta.alternativa.sem.resposta",
							"pergunta.alternativa.sem.resposta");
				}
			});
			validator.onErrorRedirectTo(this).exibir();
		}
	}

	@Logado
	@Get("/teste/responder/pergunta")
	public void exibir() {
		Pergunta pergunta = getPerguntaExibir();
		if (pergunta == null) {
			if (fluxoComponente.isRespondendoInicio()) {
				System.out.println("è para redirecionar aqui");
				result.redirectTo(TarefaController.class).loadtaskuser();
				return;
			} else {
				result.redirectTo(TesteParticiparController.class).termino();
				return;
			}
		}
		System.out.println("pergunta alternativa"
				+ pergunta.getTipoRespostaAlternativa());
		result.include("pergunta", pergunta);
	}

	/**
	 * Obtem a pergunta que vai ser exibida para ser respondida no momento
	 * 
	 * @return Null se não tiver pergunta para ser respondida no momento
	 */
	private Pergunta getPerguntaExibir() {
		Long vez;
		if (fluxoComponente.isRespondendoInicio())
			vez = fluxoComponente.getPerguntaVez(fluxoComponente
					.getPerguntasInicio());
		else {
			vez = fluxoComponente.getPerguntaVez(fluxoComponente
					.getPerguntasFim());

		}
		System.out.println("Vou exibir pergunta " + vez);
		if (vez != 0)
			return perguntaRepository.find(vez);
		else {
			return null;
		}
	}

	/**
	 * Chama a proxima pergunta para ser respondida
	 */
	private void proximaPergunta() {
		if (fluxoComponente.isRespondendoInicio())
			fluxoComponente.getProximaPergunta(fluxoComponente
					.getPerguntasInicio());
		else
			fluxoComponente.getProximaPergunta(fluxoComponente
					.getPerguntasFim());
	}

	/**
	 * Inicializa a pergunta ao qual a resposta esta ligado Pegando o
	 * identificador do fluxoComponte que e a pergunta que esta na vez
	 * 
	 * @return
	 */
	private Pergunta instanciarPergunta(boolean respostaAlternativa) {
		if (!respostaAlternativa) {
			Pergunta pergunta = new Pergunta();
			if (fluxoComponente.isRespondendoInicio())
				pergunta.setId(fluxoComponente.getPerguntaVez(fluxoComponente
						.getPerguntasInicio()));
			else
				pergunta.setId(fluxoComponente.getPerguntaVez(fluxoComponente
						.getPerguntasFim()));
			return pergunta;
		} else {
			if (fluxoComponente.isRespondendoInicio())
				return perguntaRepository.find(fluxoComponente
						.getPerguntaVez(fluxoComponente.getPerguntasInicio()));
			else
				return perguntaRepository.find(fluxoComponente
						.getPerguntaVez(fluxoComponente.getPerguntasFim()));
		}

	}

	@Logado
	@Get("analise/teste/{testeId}/pergunta/{perguntaId}")
	public void exibirRespostas(Long testeId, Long perguntaId) {
		if (testeId != null && perguntaId != null) {
			Pergunta pergunta = perguntaPertenceUsuarioTesteLiberado(
					perguntaId, testeId);
			result.include("teste", testeRepository.find(testeId));
			result.include("pergunta", pergunta);

		} else {
			result.redirectTo(LoginController.class).logado(1);
		}
	}

	/**
	 * Analisa se pergunta pertence a usuario e se o teste que a pergunta
	 * pertence jah esta liberado
	 * 
	 * @param perguntaId
	 * @param testeId
	 * @return
	 */
	private Pergunta perguntaPertenceUsuarioTesteLiberado(Long perguntaId,
			Long testeId) {
		Pergunta pergunta;
		if (perguntaId != null && testeId != null) {
			pergunta = perguntaRepository.perguntaPertenceUsuario(usuarioLogado
					.getUsuario().getId(), testeId, perguntaId, true);
			if (pergunta == null) {
				validateComponente
						.redirecionarHome("pergunta.nao.liberada.ou.nao.pertence.ao.usuario");
			} else {
				testeView.setTeste(testeRepository.find(testeId));
			}
			return pergunta;
		}
		validateComponente.redirecionarHome("pergunta.nao.pertence.usuario");
		return null;
	}

}
