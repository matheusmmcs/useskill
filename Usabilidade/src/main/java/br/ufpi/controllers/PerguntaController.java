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
    @Get({"teste/{testeId}/editar/passo2/criar/pergunta",
        "teste/{testeId}/editar/passo2/editar/{idPergunta}/pergunta"})
    public Pergunta criarPergunta(Long testeId) {
        // TODO se o idPergunta for diferente de null eu irei lançar ela
        return new Pergunta();
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
        if (testeId != null) {
            validator.validate(pergunta);
            if (pergunta.getId() != null) {
                validator.onErrorRedirectTo(this).criarPergunta(pergunta.getId());
            } else {
                validator.onErrorRedirectTo(TesteController.class).passo2(testeId);
            }
            Questionario satisfacao = usuarioLogado.getTeste().getSatisfacao();
            pergunta.setQuestionario(satisfacao);
            perguntaRepository.create(pergunta);
            result.redirectTo(TesteController.class).passo2(testeId);
        } else {
            result.redirectTo(LoginController.class).logado();
        }
    }

    public void atualizarPergunta(Long testeId, Pergunta pergunta) {
        if (testeId != null) {
            validator.validate(pergunta);
            if (pergunta.getId() != null) {
                validator.onErrorRedirectTo(this).criarPergunta(pergunta.getId());
            } else {
                validator.onErrorRedirectTo(TesteController.class).passo2(testeId);
            }
            perguntaRepository.update(pergunta);
            result.redirectTo(TesteController.class).passo2(testeId);
        } else {
            result.redirectTo(LoginController.class).logado();
        }
    }
}
