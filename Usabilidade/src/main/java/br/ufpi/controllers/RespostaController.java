package br.ufpi.controllers;

import java.util.List;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.view.Results;
import br.ufpi.annotation.Logado;
import br.ufpi.componets.ObjetoSalvo;
import br.ufpi.componets.TesteSessionPlugin;
import br.ufpi.componets.TesteView;
import br.ufpi.componets.UsuarioLogado;
import br.ufpi.componets.ValidateComponente;
import br.ufpi.models.Alternativa;
import br.ufpi.models.Pergunta;
import br.ufpi.models.RespostaAlternativa;
import br.ufpi.models.RespostaEscrita;
import br.ufpi.models.vo.RespostaAlternativaVO;
import br.ufpi.models.vo.RespostaEscritaVO;
import br.ufpi.repositories.PerguntaRepository;
import br.ufpi.repositories.RespostaAlternativaRepository;
import br.ufpi.repositories.RespostaEscritaRepository;
import br.ufpi.repositories.TesteRepository;
import br.ufpi.util.EnumObjetoSalvo;
import br.ufpi.util.Paginacao;

@Resource
public class RespostaController extends BaseController {
	private final RespostaEscritaRepository escritaRepository;
	private final RespostaAlternativaRepository alternativaRepository;
	private final PerguntaRepository perguntaRepository;
	private final TesteRepository testeRepository;
	private final TesteSessionPlugin testeSessionPlugin;

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
	public void salvarRespostaEscrita( String resposta, Long perguntaId) {
		if(resposta==null || resposta.trim().isEmpty()){
			resposta = " ";
		}
		validateComponente.validarString(resposta, "resposta");
		validator.onErrorUse(Results.json())
				.from(validator.getErrors(), "erro").serialize();
		RespostaEscrita respostaEscrita = new RespostaEscrita();
		respostaEscrita.setResposta(resposta);
		respostaEscrita.setUsuario(usuarioLogado.getUsuario());
		Pergunta pergunta = perguntaPertenceTesteLiberado(perguntaId,
				testeSessionPlugin.getIdTeste());
		respostaEscrita.setPergunta(pergunta);
		escritaRepository.create(respostaEscrita);
		testeSessionPlugin.addObjetosSalvos(new ObjetoSalvo(respostaEscrita
				.getId(), EnumObjetoSalvo.SUBJETIVA));
		result.use(Results.nothing());
	}

	private Pergunta perguntaPertenceTesteLiberado(Long perguntaId, Long idTeste) {
		Pergunta pergunta = perguntaRepository.perguntPertenceTeste(idTeste,
				perguntaId);
		validateComponente.validarObjeto(pergunta);
		return pergunta;
	}

	private Pergunta perguntaPertenceTesteLiberadoEAlternativa(
			Long alternativaId, Long perguntaId, Long idTeste) {
		Pergunta pergunta = alternativaRepository
				.perguntaPertenceTesteLiberadoEAlternativa(alternativaId,
						perguntaId, idTeste);
		validateComponente.validarObjeto(pergunta);
		return pergunta;
	}

	@Logado
	@Post("/teste/salvar/resposta/alternativa")
	public void salvarRespostaAlternativa(Long resposta, Long perguntaId) {
		System.out.println("Id da alternativa " + resposta);
		System.out.println("Id da pergunta " + perguntaId);

		RespostaAlternativa respostaAlternativa = new RespostaAlternativa();
		Alternativa alternativa = new Alternativa();
		alternativa.setId(resposta);
		respostaAlternativa.setAlternativa(alternativa);
		Pergunta pergunta = perguntaPertenceTesteLiberadoEAlternativa(
				alternativa.getId(), perguntaId,
				testeSessionPlugin.getIdTeste());
		validateComponente.validarObjetoJson(pergunta);
		respostaAlternativa.setPergunta(pergunta);
		respostaAlternativa.setUsuario(usuarioLogado.getUsuario());
		alternativaRepository.create(respostaAlternativa);
		testeSessionPlugin.addObjetosSalvos(new ObjetoSalvo(respostaAlternativa
				.getId(), EnumObjetoSalvo.SUBJETIVA));
		result.use(Results.nothing());
	}

	@Logado
	@Get({ "teste/{testeId}/pergunta/{perguntaId}/analise",
			"teste/{testeId}/pergunta/{perguntaId}/pag/{pag}/analise" })
	public void analise(Long testeId, Long perguntaId, int pag) {
		validateComponente.validarId(testeId);
		validateComponente.validarId(perguntaId);
		Pergunta pergunta = perguntaPertenceUsuarioTesteLiberado(perguntaId,
				testeId);
		if (pergunta.getTipoRespostaAlternativa()) {
			List<RespostaAlternativaVO> respostasAlternativas = alternativaRepository
					.getRespostasAlternativas(perguntaId);
			result.include("respostas", respostasAlternativas);
			result.include("isAlternativa", true);
		} else {
			if (pag == 0)
				pag = 1;
			Paginacao<RespostaEscritaVO> paginacao = escritaRepository
					.findResposta(perguntaId, pag, Paginacao.OBJETOS_POR_PAGINA);
			paginacao.geraPaginacao("respostasEscrita", pag, result);
			result.include("isAlternativa", false);

		}

		result.include("teste", testeRepository.find(testeId));
		result.include("pergunta", pergunta);

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
