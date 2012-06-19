package br.ufpi.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.Validations;
import br.ufpi.componets.TesteView;
import br.ufpi.componets.UsuarioLogado;
import br.ufpi.componets.ValidateComponente;
import br.ufpi.models.Usuario;
import br.ufpi.repositories.UsuarioRepository;
import br.ufpi.util.BaseUrl;
import br.ufpi.util.EmailUtils;

@Resource
public class UsuarioController extends BaseController{

	private final UsuarioRepository usuarioRepository;
	private final HttpServletRequest request;

	public UsuarioController(Result result, Validator validator,
			TesteView testeView, UsuarioLogado usuarioLogado,
			ValidateComponente validateComponente,
			UsuarioRepository usuarioRepository, HttpServletRequest request) {
		super(result, validator, testeView, usuarioLogado, validateComponente);
		this.usuarioRepository = usuarioRepository;
		this.request = request;
	}

	@Get("/usuarios")
	public List<Usuario> index() {
		return usuarioRepository.findAll();
	}

	@Post("/usuarios")
	public void create(Usuario usuario) {
		validator.validate(usuario);
		if (usuarioRepository.isContainsEmail(usuario.getEmail())) {
			validator.checking(new Validations() {

				{
					that(false, "email.cadastrado", "email.cadastrado");
				}
			});
		}
		validator.onErrorUsePageOf(this).newUsuario();
		usuario.criptografarSenhaGerarConfimacaoEmail();
		usuarioRepository.create(usuario);
		BaseUrl.getInstance(request);
		EmailUtils emailUtils = new EmailUtils();
		emailUtils.enviarEmailConfirmacao(usuario);
		result.redirectTo(LoginController.class).login(usuario.getEmail());
	}

	@Get("/usuarios/new")
	public Usuario newUsuario() {
		return new Usuario();
	}

	@Put("/usuarios")
	public void update(Usuario pessoa) {
		validator.validate(pessoa);
		validator.onErrorUsePageOf(this).edit(pessoa);
		usuarioRepository.update(pessoa);
		result.redirectTo(this).index();
	}

	@Get("/usuarios/{usuario.id}/edit")
	public Usuario edit(Usuario usuario) {
		Usuario usuario1 = usuarioRepository.find(usuario.getId());
		result.include("contador", usuario1.getTelefones().size());
		return usuario1;
	}

	@Get("/usuarios/{usuario.id}")
	public Usuario show(Usuario usuario) {
		return usuarioRepository.find(usuario.getId());
	}

	@Delete("/usuarios/{usuario.id}")
	public void destroy(Usuario usuario) {
		usuarioRepository.destroy(usuarioRepository.find(usuario.getId()));
		result.redirectTo(this).index();
	}

}