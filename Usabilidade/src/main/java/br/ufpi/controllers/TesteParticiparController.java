package br.ufpi.controllers;

import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.ufpi.componets.UsuarioLogado;
import br.ufpi.repositories.ConvidadoRepository;
import br.ufpi.repositories.TesteRepository;

@Resource
public class TesteParticiparController {
	private final Result result;
	private final TesteRepository testeRepository;
	private UsuarioLogado usuarioLogado;
	private final Validator validator;
	private final ConvidadoRepository convidadoRepository;

	public TesteParticiparController(Result result,
			TesteRepository testeRepository, UsuarioLogado usuarioLogado,
			Validator validator, ConvidadoRepository convidadoRepository) {
		super();
		this.result = result;
		this.testeRepository = testeRepository;
		this.usuarioLogado = usuarioLogado;
		this.validator = validator;
		this.convidadoRepository = convidadoRepository;
	}

	public void negar() {
	}

	public void aceitar() {
	}
}
