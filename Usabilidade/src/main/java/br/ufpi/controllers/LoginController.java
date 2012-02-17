package br.ufpi.controllers;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.Validations;
import br.ufpi.annotation.Logado;
import br.ufpi.componets.UsuarioLogado;
import br.ufpi.models.Usuario;
import br.ufpi.repositories.UsuarioRepository;
import br.ufpi.util.Criptografa;

@Resource
public class LoginController {

	private final Result result;
	private final UsuarioRepository usuarioRepository;
	private UsuarioLogado usuarioLogado;
	private final Validator validator;

	LoginController(Result result, UsuarioRepository repository,
			Validator validator, UsuarioLogado usuarioLogado) {
		this.result = result;
		this.usuarioRepository = repository;
		this.validator = validator;
		this.usuarioLogado = usuarioLogado;
	}

	@Get("/login")
	public void login() {
	}

	/**
	 * 
	 * @param email
	 * @param senha
	 */
	@Post("/conta")
	public void conta(final String email, final String senha) {
		// TODO Se a validação não for feita por jqueryValidate
		/*
		 * validator.checking(new Validations() { { that(!email.isEmpty(),
		 * "email", "campo.obrigatorio", i18n("email")); that(!senha.isEmpty(),
		 * "senha", "campo.obrigatorio", i18n("senha")); } });
		 * validator.onErrorUsePageOf(this).login();
		 */
		Usuario pessoa = usuarioRepository.logar(email, senha);
		if (pessoa != null) {
			usuarioLogado.setPessoa(pessoa);
			result.include("pessoa", pessoa);
		} else {
			validator.checking(new Validations() {
				{
					that(false, "email.senha.invalido", "email.senha.invalido");
				}
			});
			validator.onErrorRedirectTo(this).login();
		}
	}

	@Logado
	@Get("/logout")
	public void logout() {
		usuarioLogado.logout();
		result.redirectTo(this).login();
	}

	/**
	* Usuario ao receber o email acessara a pagina passada e
	 * esta action valida a inscrição do usuario.
	 * @param confirmacaoEmail
	 */
	@Get("/confirmar/{confirmacaoEmail}")
	public void validarInscricao(String confirmacaoEmail) {
		Usuario usuario = usuarioRepository
				.findConfirmacaoEmail(confirmacaoEmail);
		usuario.setEmailConfirmado(true);
		usuario.setConfirmacaoEmail(null);
		usuarioRepository.update(usuario);
		result.include(usuario);
	}

	/**
	 * Pagina para o usuario recuperar a senha , onde vai conter apenas o email
	 * do usuario;
	 * 
	 */
	@Get("/usuario/recupera-senha")
	public void recuperaSenha() {
		System.out.println("Recupera senha");
	}

	/**
	 * Se usuario perde a senha.
	 * 
	 * @param email
	 *            passou o email em que esta cadastrado e o aplicativo envia
	 *            outra senha para o mesmo
	 */
	@Post("usuario/recupera-senha-completa")
	public String recuperaSenhaCompleta(String email) {
		Usuario usuario = usuarioRepository.findEmail(email);
		if (usuario == null) {
			validator.checking(new Validations() {
				{
					that(false, "email.nao.cadastrado", "email.nao.cadastrado");
				}
			});
			validator.onErrorUsePageOf(this).recuperaSenha();
		} else {
			usuario.setSenha(Criptografa.criptografar(geradorSenha(usuario)));
			// TODO Enviar email para o usuario com senha nova Criar uma action
			// do tipo get que vai receber a url enviada ao email do cliente
			usuarioRepository.update(usuario);
			return email;
		}
		return null;
	}

	/**
	 * 
	 * @param string
	 * @return
	 */
	private String geradorSenha(Usuario usuario) {
		return Criptografa.criptografar(usuario.getNome() + usuario.getEmail());
	}
}