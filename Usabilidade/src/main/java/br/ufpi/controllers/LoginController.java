package br.ufpi.controllers;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.Validations;
import br.ufpi.annotation.Logado;
import br.ufpi.componets.UsuarioLogado;
import br.ufpi.models.Teste;
import br.ufpi.models.Usuario;
import br.ufpi.repositories.UsuarioRepository;
import br.ufpi.util.Criptografa;
import br.ufpi.util.EmailUtils;
import java.util.Date;
import java.util.List;

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
                that(!email.isEmpty(), "email", "campo.obrigatorio",
                        i18n("email"));
                that(!senha.isEmpty(), "senha", "campo.obrigatorio",
                        i18n("senha"));
            }
        });
        validator.onErrorRedirectTo(this).login(senha);
        String senhaCriptografada = Criptografa.criptografar(senha);
        Usuario usuario = usuarioRepository.logar(email, senhaCriptografada);
        if (usuario != null) {
            if (usuario.isEmailConfirmado()) {
                usuarioLogado.setUsuario(usuario);
                usuarioLogado.setTeste(null);
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

    @Get("/usuario")
    @Logado
    public void logado() {
        List<Teste> findTestesConvidados = usuarioRepository.findTestesConvidados(usuarioLogado.getUsuario().getId());
        System.out.println("¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨¨666699999999999999999");
        for (Teste teste : findTestesConvidados) {
            System.out.println(teste.getTitulo());
            
        }
       result.include("testesConvidados", usuarioRepository.findTestesConvidados(usuarioLogado.getUsuario().getId()));
        result.include("testesCriados", usuarioRepository.findTesteCriadosRealizadoOrdenadorData(usuarioLogado.getUsuario()));
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
     * gerado o resultado de página não encontrada.
     *
     * @param confirmacaoEmail
     */
    @Get("/confirmar/{confirmacaoEmail}")
    public void validarInscricao(String confirmacaoEmail) {
        Usuario usuario = usuarioRepository.findConfirmacaoEmail(confirmacaoEmail);
        if (usuario != null) {
            usuario.setEmailConfirmado(true);
            usuario.setConfirmacaoEmail(null);
            usuarioRepository.update(usuario);
            result.include(usuario);
        } else {
            result.notFound();
        }
    }

    /**
     * Pagina para o usuario recuperar a senha , onde vai conter apenas o campo
     * de email do usuario;
     *
     */
    @Get("/usuario/recupera-senha")
    public void recuperaSenha(String email) {
        result.include("email", email);
    }

    /**
     *
     *
     * @param email passou o email em que esta cadastrado e o aplicativo envia
     * outra senha para o mesmo
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
            validator.onErrorUsePageOf(this).recuperaSenha(email);
        } else {
            String novaSenha = geradorSenha(usuario);
            String senha = Criptografa.criptografar(novaSenha);
            usuario.setSenha(senha);
            EmailUtils emailUtils = new EmailUtils();
            emailUtils.enviarNovaSenha(usuario, novaSenha);
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
        return Criptografa.criptografar(
                usuario.getNome() + usuario.getEmail() + new Date()).substring(
                0, 8);
    }

    @Get({"usuario/reenviar/email"})
    public void reenviaEmailConfirmacao(String email) {
        result.include("email", email);
    }

    @Post("usuario/reenviar/email")
    public void reenviaEmailConfirmacaoCompleto(String email) {
        if (email != null) {
            Usuario usuario = usuarioRepository.findEmail(email);
            if (usuario != null) {
                if (usuario.getConfirmacaoEmail() != null) {
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
                        that(false, "email.nao.cadastrado",
                                "email.nao.cadastrado");
                    }
                });
                validator.onErrorForwardTo(this).reenviaEmailConfirmacao(email);
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
}
