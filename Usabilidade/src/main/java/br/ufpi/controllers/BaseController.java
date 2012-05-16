package br.ufpi.controllers;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.ufpi.componets.TesteView;
import br.ufpi.componets.UsuarioLogado;

public class BaseController {
	protected final Result result;
	protected final Validator validator;
	protected final TesteView testeView;
	protected UsuarioLogado usuarioLogado;
	
	public BaseController(Result result, Validator validator,
			TesteView testeView, UsuarioLogado usuarioLogado) {
		super();
		this.result = result;
		this.validator = validator;
		this.testeView = testeView;
		this.usuarioLogado = usuarioLogado;
	}
	public final Result getResult() {
		return result;
	}
	public final Validator getValidator() {
		return validator;
	}
	public final TesteView getTesteView() {
		return testeView;
	}

}
