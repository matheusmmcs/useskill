package br.ufpi.controllers;

import java.util.List;

import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.validator.Message;
import br.com.caelum.vraptor.validator.Validations;
import br.com.caelum.vraptor.view.Results;
import br.ufpi.componets.UsuarioLogado;
import br.ufpi.models.Usuario;
import br.ufpi.repositories.UsuarioRepository;
import br.ufpi.util.Criptografa;
@Component
public class ServicesController {
	private final Result result;
	private final UsuarioRepository usuarioRepository;
	private final Validator validator;
	private final UsuarioLogado usuarioLogado;

	public ServicesController(Result result,
			UsuarioRepository usuarioRepository, Validator validator,
			UsuarioLogado usuarioLogado) {
		super();
		this.result = result;
		this.usuarioRepository = usuarioRepository;
		this.validator = validator;
		this.usuarioLogado = usuarioLogado;
	}

	@Post
	public void login(final String email, final String senha) {
		validator.checking(new Validations() {

			{
				that(!email.isEmpty(), "campo.email.obrigatorio",
						"campo.obrigatorio", i18n("email"));
				that(!senha.isEmpty(), "campo.senha.obrigatorio",
						"campo.obrigatorio", i18n("senha"));
			}
		});
		List<Message> errors = validator.getErrors();

		validator.onErrorUse(Results.json()).from(this.toStringMessage(errors))
				.serialize();
		String senhaCriptografada = Criptografa.criptografar(senha);
		Usuario usuario = usuarioRepository.logar(email, senhaCriptografada);
		if (usuario != null) {
			if (usuario.isEmailConfirmado()) {
				usuarioLogado.setUsuario(usuario);
				result.use(Results.json()).from(usuario.getNome()).serialize();
			} else {
				result.use(Results.json())
						.from("Entre no site para confirmar senha.")
						.serialize();
			}
		} else {
			result.use(Results.json()).from("senha invalida").serialize();
		}
	}

	private String toStringMessage(List<Message> messages) {
		String mensagem = "";
		for (Message message : messages) {
			mensagem += message.getMessage();

		}
		return mensagem;
	}
}
