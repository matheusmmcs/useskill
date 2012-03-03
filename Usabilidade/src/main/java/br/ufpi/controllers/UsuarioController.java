package br.ufpi.controllers;

import java.util.List;

import org.apache.commons.mail.EmailException;

import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Put;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.Validations;
import br.ufpi.models.Usuario;
import br.ufpi.repositories.UsuarioRepository;
import br.ufpi.util.EmailUtils;
import br.ufpi.util.Mensagem;
import javax.servlet.http.HttpServletRequest;

@Resource
public class UsuarioController {

    private final Result result;
    private final UsuarioRepository usuarioRepository;
    private final Validator validator;
     private final HttpServletRequest request; 

    public UsuarioController(Result result, UsuarioRepository repository,
            Validator validator,HttpServletRequest request) {
        this.result = result;
        this.usuarioRepository = repository;
        this.validator = validator;
        this.request=request;
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
        this.enviarEmail(usuario);
        result.redirectTo(this).index();
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

    private void enviarEmail(Usuario pessoa) {
        String header = request.getHeader("Host");
        header=header+request.getContextPath();
        System.out.println("header"+header);
        Mensagem mensagem = new Mensagem();
        mensagem.setDestino(pessoa.getEmail());
        mensagem.setTitulo("Teste");
        mensagem.setMensagem(header+"/confirmar/"+pessoa.getConfirmacaoEmail());
        EmailUtils emailUtils = new EmailUtils();
        try {
            emailUtils.enviaEmail(mensagem);
        } catch (EmailException e) {
            e.printStackTrace();
        }
    }
}