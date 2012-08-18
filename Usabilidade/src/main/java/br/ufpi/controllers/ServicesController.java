package br.ufpi.controllers;

import java.util.HashMap;
import java.util.List;

import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.Message;
import br.com.caelum.vraptor.validator.Validations;
import br.com.caelum.vraptor.view.Results;
import br.ufpi.componets.UsuarioLogado;
import br.ufpi.models.Usuario;
import br.ufpi.repositories.UsuarioRepository;
import br.ufpi.util.Criptografa;

import com.google.gson.Gson;

@Resource
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
	
	public String login(final String email, final String senha) {
		Gson gson = new Gson();
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		validator.checking(new Validations() {

			{
				that(email != null && !email.isEmpty(),
						"campo.email.obrigatorio", "campo.obrigatorio",
						i18n("email"));
				that(senha != null && !senha.isEmpty(),
						"campo.senha.obrigatorio", "campo.obrigatorio",
						i18n("senha"));
			}
		});
		if (validator.hasErrors()) {
			
			validator.onErrorUse(Results.json()).from(toStringMessage(validator.getErrors())).serialize();
		}
		String senhaCriptografada = Criptografa.criptografar(senha);
		Usuario usuario = usuarioRepository.logar(email, senhaCriptografada);
		if (usuario != null) {
			if (usuario.isEmailConfirmado()) {
				usuarioLogado.setUsuario(usuario);
				hashMap.put("return", true);
				hashMap.put("name", usuario.getNome());
				System.out.println("Matheus vidao");
				System.out.println("Matheus vidao");
				return gson.toJson(hashMap);
			} else {
				hashMap.put("return", false);
				hashMap.put("usuarioNaoConfirmado", "Usuario não confirmado");
				return gson.toJson(hashMap).toString();
			}
		} else {
			validator.checking(new Validations() {

				{
					that(false, "email.senha.invalido", "email.senha.invalido");
				}
			});
			validator.onErrorUse(Results.json()).from(toStringMessage(validator.getErrors())).serialize();
		}
		return "";
	}

	private String toStringMessage(List<Message> messages) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		String mensagem = "";
		for (Message message : messages) {
			hashMap.put("ERROR" + message.getCategory(), message.getMessage());
			hashMap.put("retorno", false);
		}
		result.use(Results.json()).from(hashMap).serialize();
		return mensagem;
	}

}
