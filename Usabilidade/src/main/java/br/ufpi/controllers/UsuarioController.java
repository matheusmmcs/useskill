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

@Resource
public class UsuarioController {

	private final Result result;
	private final UsuarioRepository usuarioRepository;
	private final Validator validator;

	public UsuarioController(Result result, UsuarioRepository repository,
			Validator validator) {
		this.result = result;
		this.usuarioRepository = repository;
		this.validator = validator;
	}

	@Get("/usuarios")
	public List<Usuario> index() {
		return usuarioRepository.findAll();
	}

	@Post("/usuarios")
	public void create(Usuario usuario) {
		validator.validate(usuario);
		if(usuarioRepository.isContainsEmail(usuario.getEmail()))
			validator.checking(new Validations(){
			{
				that(false, "email.cadastrado", "email.cadastrado");
			}
			});
		validator.onErrorUsePageOf(this).newUsuario();
		usuario.criptografarSenhaGerarConfimacaoEmail();
		/*usuario.setSenha( Criptografa.criptografar(usuario.getSenha()));
		do{
			usuario.setConfirmacaoEmail(Criptografa.criptografar(new Date().toString()));
		}while(usuarioRepository.isContainConfirmacaoEmail(usuario.getConfirmacaoEmail()));*/
		usuarioRepository.create(usuario);
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
		return usuarioRepository.find(usuario.getId());
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
		Mensagem mensagem = new Mensagem();
		mensagem.setDestino(pessoa.getEmail());
		mensagem.setTitulo("Teste");
		mensagem.setMensagem("<h1>Cleiton<h1>");
		EmailUtils emailUtils = new EmailUtils();
		try {
			emailUtils.enviaEmail(mensagem);
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}

	
}