package br.ufpi.controllers;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.ufpi.annotation.Logado;
import br.ufpi.componets.UsuarioLogado;
import br.ufpi.models.Pergunta;
import br.ufpi.models.Questionario;
import br.ufpi.repositories.PerguntaRepository;

@Resource
public class PerguntaController {
	private final Result result;
	private final PerguntaRepository perguntaRepository;
	private UsuarioLogado usuarioLogado;
	private final Validator validator;

	public PerguntaController(Result result,
			PerguntaRepository perguntaRepository, UsuarioLogado usuarioLogado,
			Validator validator) {
		super();
		this.result = result;
		this.perguntaRepository = perguntaRepository;
		this.usuarioLogado = usuarioLogado;
		this.validator = validator;
	}

	@Logado
	@Get({ "teste/{testeId}/editar/passo2/criar/pergunta",
			"teste/{testeId}/editar/passo2/editar/{idPergunta}/pergunta" })
	public Pergunta criarPergunta(Long testeId) {
		// TODO se o idPergunta for diferente de null eu irei lançar ela
		Pergunta pergunta = new Pergunta();
		pergunta.setTexto("sgsd");
		pergunta.setTipoRespostaAlternativa(true);
		result.include("tipoPergunta",true);
		return pergunta;
	}

	@Logado
	public Pergunta duplicar(Pergunta pergunta) {
		pergunta.setId(null);
		perguntaRepository.create(pergunta);
		return pergunta;

	}

	@Logado
	@Post("teste/{testeId}/editar/passo2/salvar/pergunta")
	public void salvarPergunta(Long testeId, Pergunta pergunta) {
		System.out.println("perguntaId="
				+ pergunta.getTipoRespostaAlternativa());
		validator.validate(pergunta);
		Questionario satisfacao = usuarioLogado.getTeste().getSatisfacao();
		pergunta.setQuestionario(satisfacao);
		pergunta.setQuestionario(satisfacao);
		satisfacao.getPerguntas().add(pergunta);
		usuarioLogado.getTeste().setSatisfacao(satisfacao);
		perguntaRepository.create(pergunta);
		result.redirectTo(TesteController.class).passo2(testeId);
	}

	public void atualizarPergunta(Long testeId, Pergunta pergunta) {
		validator.validate(pergunta);
		perguntaRepository.update(pergunta);
		result.redirectTo(TesteController.class).passo2(testeId);
	}
}
