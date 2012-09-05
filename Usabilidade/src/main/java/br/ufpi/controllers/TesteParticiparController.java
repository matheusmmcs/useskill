package br.ufpi.controllers;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.view.Results;
import br.ufpi.annotation.Logado;
import br.ufpi.componets.TesteSessionPlugin;
import br.ufpi.componets.TesteView;
import br.ufpi.componets.UsuarioLogado;
import br.ufpi.componets.ValidateComponente;
import br.ufpi.models.Convidado;
import br.ufpi.models.vo.TesteParticiparVO;
import br.ufpi.repositories.ConvidadoRepository;

/**
 * @author Cleiton
 * 
 */
@Path(value = "teste/participar")
@Resource
public class TesteParticiparController extends BaseController {
	private final ConvidadoRepository convidadoRepository;
	private final TesteSessionPlugin testeSessionPlugin;

	public TesteParticiparController(Result result, Validator validator,
			TesteView testeView, UsuarioLogado usuarioLogado,
			ValidateComponente validateComponente,
			ConvidadoRepository convidadoRepository,
			TesteSessionPlugin testeSessionPlugin) {
		super(result, validator, testeView, usuarioLogado, validateComponente);
		this.convidadoRepository = convidadoRepository;
		this.testeSessionPlugin = testeSessionPlugin;
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
		verificaSeUsuarioConvidado(testeId);
		Convidado convidado = new Convidado(usuarioLogado.getUsuario().getId(),
				testeId);
		convidado.setRealizou(false);
		convidadoRepository.update(convidado);
		result.redirectTo(TesteController.class).listarTestesConvidados(0);
	}

	@Logado
	@Get
	public void termino() {

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

	@Logado
	public void responder() {

		// incluir proximo
		// incluir tbm o que é pra responder

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
		System.out.println(testeParticiparVO.getElemntosTeste());
		testeSessionPlugin.setIdTeste(testeId);
		testeSessionPlugin.setTipoConvidado(testeParticiparVO.getTipoConvidado());
		result.use(Results.json()).from(testeParticiparVO.getElemntosTeste(),
				"listaElementos").serialize();

	}

}
