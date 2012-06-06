package br.ufpi.controllers;

//TODO Validar se as perguntas est√£o sendo respondida e mostra uma mensagem
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.ufpi.annotation.Logado;
import br.ufpi.componets.FluxoComponente;
import br.ufpi.componets.UsuarioLogado;
import br.ufpi.models.Alternativa;
import br.ufpi.models.Pergunta;
import br.ufpi.models.RespostaAlternativa;
import br.ufpi.models.RespostaEscrita;
import br.ufpi.repositories.PerguntaRepository;
import br.ufpi.repositories.RespostaAlternativaRepository;
import br.ufpi.repositories.RespostaEscritaRepository;

@Resource
public class RespostaController {
	private final RespostaEscritaRepository escritaRepository;
	private final RespostaAlternativaRepository alternativaRepository;
	private final Result result;
	private final Validator validator;
	private final PerguntaRepository perguntaRepository;
	private final FluxoComponente fluxoComponente;
	private final UsuarioLogado usuarioLogado;

	public RespostaController(RespostaEscritaRepository escritaRepository,
			RespostaAlternativaRepository alternativaRepository, Result result,
			Validator validator, PerguntaRepository perguntaRepository,
			FluxoComponente fluxoComponente, UsuarioLogado usuarioLogado) {
		super();
		this.escritaRepository = escritaRepository;
		this.alternativaRepository = alternativaRepository;
		this.result = result;
		this.validator = validator;
		this.perguntaRepository = perguntaRepository;
		this.fluxoComponente = fluxoComponente;
		this.usuarioLogado = usuarioLogado;
	}

	@Logado
	@Post("/teste/salvar/resposta/escrita")
	public void salvarRespostaEscrita(String resposta) {
		RespostaEscrita respostaEscrita = new RespostaEscrita();
		respostaEscrita.setResposta(resposta);
		respostaEscrita.setUsuario(usuarioLogado.getUsuario());
		Pergunta pergunta = instanciarPergunta();
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
		Pergunta pergunta = instanciarPergunta();
		respostaAlternativa.setPergunta(pergunta);
		respostaAlternativa.setUsuario(usuarioLogado.getUsuario());
		alternativaRepository.create(respostaAlternativa);
		proximaPergunta();
	}

	@Logado
	@Get("/teste/responder/pergunta")
	public void exibir() {
		Pergunta pergunta = getPerguntaExibir();
		if (pergunta == null) {
			if (fluxoComponente.isRespondendoInicio())
				result.redirectTo(TarefaController.class).loadtaskuser();
			else
				result.redirectTo(TesteParticiparController.class).termino();
                }
	result.include("pergunta", pergunta);
	}

	/**
	 * Obtem a pergunta que vai ser exibida para ser respondida no momento
	 * 
	 * @return Null se n„o tiver pergunta para ser respondida no momento
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
	private Pergunta instanciarPergunta() {
		Pergunta pergunta = new Pergunta();
		if (fluxoComponente.isRespondendoInicio())
			pergunta.setId(fluxoComponente.getPerguntaVez(fluxoComponente
					.getPerguntasInicio()));
		else
			pergunta.setId(fluxoComponente.getPerguntaVez(fluxoComponente
					.getPerguntasFim()));
		return pergunta;
	}

}
