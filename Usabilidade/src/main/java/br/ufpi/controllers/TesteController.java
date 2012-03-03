package br.ufpi.controllers;

import br.com.caelum.vraptor.Delete;
import java.util.ArrayList;
import java.util.List;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.ufpi.annotation.Logado;
import br.ufpi.componets.UsuarioLogado;
import br.ufpi.models.Pergunta;
import br.ufpi.models.Questionario;
import br.ufpi.models.Tarefa;
import br.ufpi.models.Teste;
import br.ufpi.repositories.TesteRepository;
import br.ufpi.util.Criptografa;

@Resource
public class TesteController {

    private final Result result;
    private final TesteRepository testeRepository;
    private UsuarioLogado usuarioLogado;
    private final Validator validator;

    public TesteController(Result result, TesteRepository testeRepository,
            UsuarioLogado usuarioLogado, Validator validator) {
        super();
        this.result = result;
        this.testeRepository = testeRepository;
        this.usuarioLogado = usuarioLogado;
        this.validator = validator;
    }

    /**
     * Contera todo o procedimento para criar um teste. Na view contera os dados
     * do teste por meio da seção UsuarioLogado.teste
     */
    @Get("teste/criar")
    @Logado
    public void criarTeste() {
        if (usuarioLogado.getTeste() == null) {
            this.usuarioLogado.setTeste(new Teste());
            this.usuarioLogado.getTeste().setUsuarioCriador(
                    usuarioLogado.getUsuario());
        }
    }

    @Logado
    @Post()
    public void salvar(String titulo) {
        Teste teste = usuarioLogado.getTeste();
        teste.setTitulo(titulo);
        teste.setSatisfacao(new Questionario());
        testeRepository.saveUpdate(teste);
        result.redirectTo(this).passo1(teste.getId());

    }

    @Logado
    @Get("teste/{id}/editar/passo1")
    public void passo1(Long id) {
        this.isTestePertenceUsuarioLogado(id);

    }

    @Logado
    @Post("teste/{id}/editar/passo2")
    public void passo2(String titulo, String tituloPublico,
            String textoIndroducao) {
        Teste teste = usuarioLogado.getTeste();
        teste.setTitulo(titulo);
        teste.setTituloPublico(tituloPublico);
        teste.setTextoIndroducao(textoIndroducao);
        usuarioLogado.getUsuario().getTestesCriados();
        testeRepository.saveUpdate(teste);
        result.include("tarefas", usuarioLogado.getTeste().getTarefas());
        result.include("perguntas", usuarioLogado.getTeste().getSatisfacao().getPerguntas());
    }

    @Logado
    @Get("teste/{idTeste}/editar/passo2")
    public void passo2(Long idTeste) {
        this.isTestePertenceUsuarioLogado(idTeste);
        result.include("tarefas", usuarioLogado.getTeste().getTarefas());
        result.include("perguntas", usuarioLogado.getTeste().getSatisfacao().getPerguntas());

    }

    /**
     * Usado para criar Tarefas
     */
    @Logado
    @Get("teste/{testeId}/editar/passo2/criar/tarefa")
    public void criarTarefa(Long testeId) {
    }

    @Logado
    @Post
    public void salvarTarefa(Tarefa tarefa) {
        validator.validate(tarefa);
        Teste teste = usuarioLogado.getTeste();
        List<Tarefa> tarefas = teste.getTarefas();
        if (tarefas == null) {
            teste.setTarefas(new ArrayList<Tarefa>());
        }
        teste.getTarefas().add(tarefa);
        testeRepository.update(teste);
    }

    @Logado
    @Get({"teste/{testeId}/editar/passo2/criar/pergunta", "teste/{testeId}/editar/passo2/editar/{idPergunta}/pergunta"})
    public Pergunta criarPergunta(Long testeId) {
        //TODO se o idPergunta for diferente de null eu irei lançar ela
        return new Pergunta();
    }

    @Logado
    @Post("teste/{testeId}/editar/passo2/salvar/pergunta")
    public void salvarPergunta(Long testeId, Pergunta pergunta) {
        System.out.println("perguntaId="+pergunta.getTipoRespostaAlternativa());
        validator.validate(pergunta);
        Questionario satisfacao = usuarioLogado.getTeste().getSatisfacao();
        if (satisfacao.getPerguntas() == null) {
            satisfacao.setPerguntas(new ArrayList<Pergunta>());
        }
        pergunta.setQuestionario(satisfacao);
        satisfacao.getPerguntas().add(pergunta);
        usuarioLogado.getTeste().setSatisfacao(satisfacao);
        testeRepository.saveUpdate(usuarioLogado.getTeste());
        result.redirectTo(this).passo2(testeId);
    }

    /**
     * *
     * Analisa se o id do teste buscado pertence ao usuario logado, se não
     * pertencer ao usuario buscado sera redirecionado para pagina 404.
     *
     * @param idTeste buscado e analisado para ver se pertence ao usuario
     *
     */
    private void isTestePertenceUsuarioLogado(Long idTeste) {
        if (usuarioLogado.getTeste() != null
                && usuarioLogado.getTeste().getId() != null
                && usuarioLogado.getTeste().getId().equals(idTeste)) {
            return;
        } else {
            Teste teste = testeRepository.testCriado(usuarioLogado.getUsuario().getId(), idTeste);
            if (teste != null) {
                usuarioLogado.setTeste(teste);
            } else {
                result.notFound();
            }
        }
    }

    @Get("teste/{idTeste}/remover")
    @Logado
    public void remove(Long idTeste) {
        this.isTestePertenceUsuarioLogado(idTeste);
    }

    @Delete()
    @Logado
    public void removed(String senha) {
        senha = Criptografa.criptografar(senha);
        if (senha.equals(usuarioLogado.getUsuario().getSenha())) {
            testeRepository.destroy(usuarioLogado.getTeste());
            usuarioLogado.setTeste(null);
            result.redirectTo(LoginController.class).logado();
        } else {
            result.redirectTo(this).remove(usuarioLogado.getTeste().getId());
        }
    }

    @Get()
    public void realizarTeste() {
    }
}
