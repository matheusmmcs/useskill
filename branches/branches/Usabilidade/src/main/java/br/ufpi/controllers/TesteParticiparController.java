package br.ufpi.controllers;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.ufpi.annotation.Logado;
import br.ufpi.componets.FluxoComponente;
import br.ufpi.componets.TesteSession;
import br.ufpi.componets.TesteView;
import br.ufpi.componets.UsuarioLogado;
import br.ufpi.componets.ValidateComponente;
import br.ufpi.models.Convidado;
import br.ufpi.models.vo.ConvidadoVO;
import br.ufpi.repositories.ConvidadoRepository;

/**
 * @author Cleiton
 * 
 */
@Path(value = "teste/participar")
@Resource
public class TesteParticiparController extends BaseController {
	private final ConvidadoRepository convidadoRepository;
	private final FluxoComponente fluxoComponente;
	private final TesteSession testeSession;

	public TesteParticiparController(Result result, Validator validator,
			TesteView testeView, UsuarioLogado usuarioLogado,
			ValidateComponente validateComponente,
			ConvidadoRepository convidadoRepository, FluxoComponente fluxoComponente,
			TesteSession testeSession) {
		super(result, validator, testeView, usuarioLogado, validateComponente);
		this.convidadoRepository = convidadoRepository;
		this.fluxoComponente = fluxoComponente;
		this.testeSession = testeSession;
	}

	@Logado
	@Post
	public void negar(Long testeId) {
		verificaSeUsuarioConvidado(testeId);
		Convidado convidado = new Convidado(usuarioLogado.getUsuario().getId(),
				testeId);
		convidado.setRealizou(false);
		convidadoRepository.update(convidado);
		result.redirectTo(LoginController.class).logado();
	}

	/**
	 * O Usuario aceita o Teste
	 * 
	 * @param testeId
	 */
	@Post
	@Logado
	public void aceitar(Long testeId) {
		 ConvidadoVO convidado = verificaSeUsuarioConvidado(testeId);
		 
		testeSession.setTeste(convidado.getTeste());
		fluxoComponente.criarLista(convidado.getTeste(),convidado.getConvidado());
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
	private ConvidadoVO verificaSeUsuarioConvidado(Long idTeste) {
		validateComponente.validarId(idTeste);
	 ConvidadoVO convidadoVO = convidadoRepository.getTesteConvidado(idTeste,
				usuarioLogado.getUsuario().getId());
		validateComponente.validarObjeto(convidadoVO);
		return convidadoVO;
	}

	@Logado
	public void responder() {

		// incluir proximo
		// incluir tbm o que é pra responder

	}

}
