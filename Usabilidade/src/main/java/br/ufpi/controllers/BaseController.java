package br.ufpi.controllers;

import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.ufpi.componets.TesteView;
import br.ufpi.componets.UsuarioLogado;
import br.ufpi.componets.ValidateComponente;

public class BaseController {
	protected final Result result;
	protected final Validator validator;
	protected final TesteView testeView;
	protected UsuarioLogado usuarioLogado;
	protected final ValidateComponente validateComponente;
	
	public BaseController(Result result, Validator validator,
			TesteView testeView, UsuarioLogado usuarioLogado,
			ValidateComponente validateComponente) {
		super();
		this.result = result;
		this.validator = validator;
		this.testeView = testeView;
		this.usuarioLogado = usuarioLogado;
		this.validateComponente = validateComponente;
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
