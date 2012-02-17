package br.ufpi.controllers;

import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.ufpi.componets.UsuarioLogado;
import br.ufpi.repositories.TesteRepository;

@Resource
public class TesteController {
	private final Result result;
	private final TesteRepository repository;
	private UsuarioLogado usuarioLogado;
	private final Validator validator;

	public TesteController(Result result, TesteRepository repository,
			UsuarioLogado usuarioLogado, Validator validator) {
		super();
		this.result = result;
		this.repository = repository;
		this.usuarioLogado = usuarioLogado;
		this.validator = validator;
	}
	@Post("teste/criar")
	public void criarTeste(){
	result.include("teste", usuarioLogado.getTeste());	
	}
}
