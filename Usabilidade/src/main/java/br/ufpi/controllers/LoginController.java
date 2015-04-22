package br.ufpi.controllers;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.Validations;
import br.ufpi.annotation.Logado;
import br.ufpi.componets.TesteView;
import br.ufpi.componets.UsuarioLogado;
import br.ufpi.componets.ValidateComponente;
import br.ufpi.models.Teste;
import br.ufpi.models.Usuario;
import br.ufpi.repositories.UsuarioRepository;
import br.ufpi.util.BaseUrl;
import br.ufpi.util.Criptografa;
import br.ufpi.util.EmailUtils;
import br.ufpi.util.Paginacao;

@Resource
public class LoginController extends BaseController {

	private final UsuarioRepository usuarioRepository;
	private final HttpServletRequest request;

	public LoginController(Result result, Validator validator,
			TesteView testeView, UsuarioLogado usuarioLogado,
			ValidateComponente validateComponente,
			UsuarioRepository usuarioRepository, HttpServletRequest request) {
		super(result, validator, testeView, usuarioLogado, validateComponente);
		this.usuarioRepository = usuarioRepository;
		this.request = request;
	}

	@Get("/login")
	public void login(String email) {
		result.include("email", email);
	}

	/**
	 * Usada para a pessoa acessar a conta apos o Login.
	 * 
	 * @param email
	 * @param senha
	 */
	@Post("/conta")
	public void conta(final String email, final String senha) {
		validator.checking(new Validations() {

			{
				that(!email.isEmpty(), "campo.email.obrigatorio",
						"campo.obrigatorio", i18n("email"));
				that(!senha.isEmpty(), "campo.senha.obrigatorio",
						"campo.obrigatorio", i18n("senha"));
			}
		});
		validator.onErrorRedirectTo(this).login(email);
		String senhaCriptografada = Criptografa.criptografar(senha);
		Usuario usuario = usuarioRepository.logar(email, senhaCriptografada);
		if (usuario != null) {
			if (usuario.isEmailConfirmado()) {
				usuarioLogado.setUsuario(usuario);
				result.redirectTo(this).logado();
			} else {
				result.forwardTo(this).reenviaEmailConfirmacao(
						usuario.getEmail());
			}
		} else {
			validator.checking(new Validations() {

				{
					that(false, "email.senha.invalido", "email.senha.invalido");
				}
			});
			validator.onErrorRedirectTo(this).login(email);
		}
	}
	
	@Get({ "/usuario" })
	@Logado
	public void logado() {
		result.forwardTo(this).logado(1);
	}
	
	/**
	 * ao logar mostra todos os teste que o usuario foi convidado e os testes
	 * que ainda não foram liberados
	 */
	@Get({ "/usuario/pag/{numeroPagina}" })
	@Logado
	public void logado(int numeroPagina) {
		if (numeroPagina == 0) {
			numeroPagina = 1;
		}
		Paginacao<Teste> paginacao = usuarioRepository
				.findTesteNaoLiberadosOrdenadorData(usuarioLogado.getUsuario()
						.getId(), numeroPagina, Paginacao.OBJETOS_POR_PAGINA);

		result.include("testesCriados", paginacao.getListObjects());
		Long qttObjetosNoBanco = paginacao.getCount();
		paginacao.geraPaginacao(numeroPagina, Paginacao.OBJETOS_POR_PAGINA,
				qttObjetosNoBanco, result);
	}

	/**
	 * Referencia para o usuario desloga da aplicação. Acabando com a seção do
	 * usuario.
	 */
	@Logado
	@Get("/logout")
	public void logout() {
		usuarioLogado.logout();
		result.redirectTo(this).login(null);
	}

	/**
	 * Usuario ao receber o email acessara a pagina passada e esta action valida
	 * a inscrição do usuario. Caso o email de confirmação não for encontrado é
	 * redirecionado para a pagina de Login.
	 * 
	 * @param confirmacaoEmail
	 */
	@Get("/confirmar/{confirmacaoEmail}")
	public void validarInscricao(String confirmacaoEmail) {
		Usuario usuario = usuarioRepository
				.findConfirmacaoEmail(confirmacaoEmail);
		if (usuario != null) {
			usuario.setEmailConfirmado(true);
			usuario.setConfirmacaoEmail(null);
			usuarioRepository.update(usuario);
			result.include(usuario);
		} else {
			result.redirectTo(LoginController.class).login(null);
		}
	}

	/**
	 * Pagina para o usuario recuperar a senha , onde vai conter apenas o campo
	 * de email do usuario;
	 * 
	 * 
	 * @param email
	 */
	@Get("/usuario/recupera-senha")
	public void recuperaSenha(String email) {
		result.include("email", email);
	}

	/**
	 * Realiza o processo de Reenvio de nova senha para o usuario
	 * 
	 * @param email
	 *            passou o email em que esta cadastrado e o aplicativo envia
	 *            outra senha para o mesmo
	 * @return
	 */
	@Post("usuario/recupera-senha-completa")
	public void recuperaSenhaCompleta(final String email) {
		validator.checking(new Validations() {
			{
				that(!email.isEmpty(), "campo.email.obrigatorio",
						"campo.obrigatorio", i18n("email"));
			}
		});
		validator.onErrorForwardTo(this).recuperaSenha(email);
		Usuario usuario = usuarioRepository.findEmail(email);
		if (usuario == null) {
			validator.checking(new Validations() {

				{
					that(false, "email.nao.cadastrado", "email.nao.cadastrado");
				}
			});
			validator.onErrorUsePageOf(this).recuperaSenha(email);
		} else {
			String novaSenha = geradorSenha(usuario);
			String senha = Criptografa.criptografar(novaSenha);
			usuario.setSenha(senha);
			BaseUrl.getInstance(request);
			EmailUtils emailUtils = new EmailUtils();
			emailUtils.enviarNovaSenha(usuario, novaSenha);
			usuarioRepository.update(usuario);
			result.include("sucesso", "usuario.senha.redefinida");
			result.include("novaSenha", novaSenha);
			result.redirectTo(this).recuperaSenha(email);
		}
	}

	/**
	 * Gera a senha para o Usuario
	 * 
	 * @param usuario
	 *            Usuario ao qual vai ser gerado a senha
	 * @return a senha de tamanho de 8 caracteres
	 */
	private String geradorSenha(Usuario usuario) {
		return Criptografa.criptografar(
				usuario.getNome() + usuario.getEmail() + new Date()).substring(
				0, 8);
	}

	@Get({ "usuario/reenviar/email" })
	public void reenviaEmailConfirmacao(String email) {
		result.include("email", email);
	}

	@Post("usuario/reenviar/email")
	public void reenviaEmailConfirmacaoCompleto(final String email) {
		validator.checking(new Validations() {
			{
				that(!email.isEmpty(), "campo.email.obrigatorio",
						"campo.obrigatorio", i18n("email"));
			}
		});
		validator.onErrorForwardTo(this).reenviaEmailConfirmacao(email);
		Usuario usuario = usuarioRepository.findEmail(email);
		if (usuario != null) {
			if (usuario.getConfirmacaoEmail() != null) {
				BaseUrl.getInstance(request);
				EmailUtils emailUtils = new EmailUtils();
				emailUtils.enviarEmailConfirmacao(usuario);
				result.include("email", email);
				result.include("isConfirmado", false);

			} else {
				result.include("email", email);
				result.include("isConfirmado", true);
			}
		} else {
			validator.checking(new Validations() {
				{
					that(false, "email.nao.cadastrado", "email.nao.cadastrado");
				}
			});
			validator.onErrorForwardTo(this).reenviaEmailConfirmacao(email);
		}
	}
	
	public static void main(String[] args) {
		System.out.println(Criptografa.criptografar("java"));
	}
}
