package br.ufpi.controllers;

import java.util.List;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.ufpi.annotation.Logado;
import br.ufpi.componets.SessionFluxoTarefa;
import br.ufpi.componets.UsuarioLogado;
import br.ufpi.models.Convidado;
import br.ufpi.models.Tarefa;
import br.ufpi.models.Teste;
import br.ufpi.repositories.ConvidadoRepository;
import br.ufpi.repositories.TesteRepository;

/**
 * @author Cleiton
 * 
 */
@Path(value = "teste/participar")
@Resource
public class TesteParticiparController {
	private final Result result;
	private UsuarioLogado usuarioLogado;
	private final Validator validator;
	private final ConvidadoRepository convidadoRepository;
	private final SessionFluxoTarefa fluxoTarefa;

	public TesteParticiparController(Result result,
			UsuarioLogado usuarioLogado, Validator validator,
			ConvidadoRepository convidadoRepository,
			SessionFluxoTarefa fluxoTarefa) {
		super();
		this.result = result;
		this.usuarioLogado = usuarioLogado;
		this.validator = validator;
		this.convidadoRepository = convidadoRepository;
		this.fluxoTarefa = fluxoTarefa;
	}

	@Logado
	@Post
	public void negar(Long testeId) {
		Convidado convidado = new Convidado(usuarioLogado.getUsuario().getId(),
				testeId);
		convidado.setRealizou(false);
		convidadoRepository.update(convidado);
		result.redirectTo(LoginController.class).logado();
	}

	/**
	 * @param testeId
	 */
	@Post
	public void aceitar(Long testeId) {
		usuarioConvidado(testeId);
		fluxoTarefa.criarLista(usuarioLogado.getTeste());

	}

	@Logado
	@Get
	public void termino() {
	
	}

	private void usuarioConvidado(Long testeId) {
		if (testeId != null) {
			this.usuarioLogado.getUsuario();
			Teste testeConvidado = convidadoRepository.getTesteConvidado(
					testeId, usuarioLogado.getUsuario().getId());
			if (testeConvidado != null) {
				usuarioLogado.setTeste(testeConvidado);
			} else {
				result.notFound();
			}
		}
	}

}
