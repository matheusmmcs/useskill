package br.ufpi.controllers;

//TODO Validar se as perguntas estão sendo respondida e mostra uma mensagem
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.Validations;
import br.ufpi.annotation.Logado;
import br.ufpi.componets.TesteSessionPlugin;
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
	private final TesteRepository testeRepository;
	private final TesteSessionPlugin  testeSessionPlugin;

	public RespostaController(Result result, Validator validator,
			TesteView testeView, UsuarioLogado usuarioLogado,
			ValidateComponente validateComponente,
			RespostaEscritaRepository escritaRepository,
			RespostaAlternativaRepository alternativaRepository,
			PerguntaRepository perguntaRepository,
			TesteRepository testeRepository,
			TesteSessionPlugin testeSessionPlugin) {
		super(result, validator, testeView, usuarioLogado, validateComponente);
		this.escritaRepository = escritaRepository;
		this.alternativaRepository = alternativaRepository;
		this.perguntaRepository = perguntaRepository;
		this.testeRepository = testeRepository;
		this.testeSessionPlugin = testeSessionPlugin;
	}




	@Logado
	@Post("/teste/salvar/resposta/escrita")
	public void salvarRespostaEscrita(final String resposta,Long perguntaId) {
		validateComponente.validarString(resposta, "resposta");
		//TODO validar com o json
		RespostaEscrita respostaEscrita = new RespostaEscrita();
		respostaEscrita.setResposta(resposta);
		respostaEscrita.setUsuario(usuarioLogado.getUsuario());
		Pergunta pergunta = perguntaPertenceTesteLiberado(perguntaId,testeSessionPlugin.getIdTeste());
		respostaEscrita.setPergunta(pergunta);
		escritaRepository.create(respostaEscrita);
	}


	

	private Pergunta perguntaPertenceTesteLiberado(Long perguntaId, Long idTeste) {
		// TODO Auto-generated method stub
		return null;
	}




	@Logado
	@Post("/teste/salvar/resposta/alternativa")
	public void salvarRespostaAlternativa(Alternativa alternativa,Long perguntaId) {
		//TODO validar com o json
		RespostaAlternativa respostaAlternativa = new RespostaAlternativa();
		respostaAlternativa.setAlternativa(alternativa);
		Pergunta pergunta = perguntaPertenceTesteLiberado(perguntaId,testeSessionPlugin.getIdTeste());
		alternaviaPertencePergunta(alternativa, pergunta);
		respostaAlternativa.setPergunta(pergunta);
		respostaAlternativa.setUsuario(usuarioLogado.getUsuario());
		alternativaRepository.create(respostaAlternativa);
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
			//TODO manda mensagem que alternavia não possui a pergunta
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
