package br.ufpi.controllers;

import java.util.ArrayList;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
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
import br.ufpi.models.Convidado;
import br.ufpi.models.Fluxo;
import br.ufpi.models.RespostaAlternativa;
import br.ufpi.models.RespostaEscrita;
import br.ufpi.models.TipoConvidado;
import br.ufpi.models.vo.TesteParticiparVO;
import br.ufpi.repositories.ConvidadoRepository;
import br.ufpi.repositories.FluxoRepository;
import br.ufpi.repositories.RespostaAlternativaRepository;
import br.ufpi.repositories.RespostaEscritaRepository;

/**
 * @author Cleiton
 * 
 */
@Path(value = "teste/participar")
@Resource
public class TesteParticiparController extends BaseController {
	private final ConvidadoRepository convidadoRepository;
	private final TesteSessionPlugin testeSessionPlugin;
	private final FluxoRepository fluxoRepository;
	private final RespostaAlternativaRepository respostaAlternativaRepository;
	private final RespostaEscritaRepository respostaEscritaRepository;

	public TesteParticiparController(Result result, Validator validator,
			TesteView testeView, UsuarioLogado usuarioLogado,
			ValidateComponente validateComponente,
			ConvidadoRepository convidadoRepository,
			TesteSessionPlugin testeSessionPlugin,
			RespostaAlternativaRepository alternativaRepository,
			RespostaEscritaRepository escritaRepository,
			FluxoRepository fluxoRepository) {
		super(result, validator, testeView, usuarioLogado, validateComponente);
		this.convidadoRepository = convidadoRepository;
		this.testeSessionPlugin = testeSessionPlugin;
		this.respostaAlternativaRepository = alternativaRepository;
		this.respostaEscritaRepository = escritaRepository;
		this.fluxoRepository = fluxoRepository;
	}

	/**
	 * @return the convidadoRepository
	 */
	public ConvidadoRepository getConvidadoRepository() {
		return convidadoRepository;
	}

	/**
	 * @return the testeSessionPlugin
	 */
	public TesteSessionPlugin getTesteSessionPlugin() {
		return testeSessionPlugin;
	}

	@Logado
	@Get("/negar/testeId/{testeId}")
	public void negar(Long testeId) {
		TipoConvidado tipoConvidado = verificaSeUsuarioConvidado(testeId)
				.getTipoConvidado();
		Convidado convidado = new Convidado(usuarioLogado.getUsuario().getId(),
				testeId);
		convidado.setTipoConvidado(tipoConvidado);
		convidadoRepository.update(convidado);
		result.redirectTo(TesteController.class).listarTestesConvidados(0);
	}

	@Logado
	@Get("/termino")
	public void termino() {
		if (testeSessionPlugin != null) {
			verificaSeUsuarioConvidado(testeSessionPlugin.getIdTeste());
			Convidado convidado = new Convidado(usuarioLogado.getUsuario()
					.getId(), testeSessionPlugin.getIdTeste());
			convidado.setRealizou(false);
			convidado.setTipoConvidado(testeSessionPlugin.getTipoConvidado());
			convidadoRepository.update(convidado);
			testeSessionPlugin.termino();
		}
	}

	/**
	 * Verifica se o usuario foi convidado e se o teste já foi liberado
	 * 
	 * @param idTeste
	 * @return
	 */
	private TesteParticiparVO verificaSeUsuarioConvidado(Long idTeste) {
		validateComponente.validarId(idTeste);
		TesteParticiparVO testeParticiparVO = convidadoRepository
				.getTesteConvidado(idTeste, usuarioLogado.getUsuario().getId());
		validateComponente.validarObjeto(testeParticiparVO);
		return testeParticiparVO;
	}

	/**
	 * O Usuario aceita o Teste
	 * 
	 * @param testeId
	 */
	@Logado
	@Get("/{testeId}/aceitar")
	public void aceitar(Long testeId) {
		TesteParticiparVO testeParticiparVO = verificaSeUsuarioConvidado(testeId);
		testeSessionPlugin.setIdTeste(testeId);
		testeSessionPlugin.setTipoConvidado(testeParticiparVO
				.getTipoConvidado());
		result.use(Results.json())
				.from(testeParticiparVO.getElemntosTeste(), "listaElementos")
				.serialize();
	}

	public void deixar() {
		testeSessionPlugin.removerObjetosSalvos();
	}

	/**
	 * 
	 */
	public void removerObjetos() {
		for (ObjetoSalvo objetoSalvo : testeSessionPlugin.getObjetosSalvos()) {
			switch (objetoSalvo.getEnumObjetoSalvo()) {
			case FLUXO:
				Fluxo fluxo = new Fluxo();
				fluxo.setId(objetoSalvo.getId());
				fluxoRepository.destroy(fluxo);
				break;
			case OBJETIVA:
				RespostaAlternativa respostaAlternativa = new RespostaAlternativa();
				respostaAlternativa.setId(objetoSalvo.getId());
				respostaAlternativaRepository.destroy(respostaAlternativa);
				break;
			case SUBJETIVA:

				RespostaEscrita respostaEscrita = new RespostaEscrita();
				respostaEscrita.setId(objetoSalvo.getId());
				respostaEscritaRepository.destroy(respostaEscrita);
				break;
			default:
				break;
			}
		}
		testeSessionPlugin.setObjetosSalvos(new ArrayList<ObjetoSalvo>());

	}
}
