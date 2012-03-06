package br.ufpi.controllers;

import br.com.caelum.vraptor.Delete;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.Validations;
import br.ufpi.annotation.Logado;
import br.ufpi.componets.UsuarioLogado;
import br.ufpi.models.Pergunta;
import br.ufpi.models.Questionario;
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
        for (Pergunta object : usuarioLogado.getTeste().getSatisfacao().getPerguntas()) {
            System.out.println("Titulo############"+object.getTitulo());
        }
        result.include("perguntas", usuarioLogado.getTeste().getSatisfacao().getPerguntas());

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
        Teste teste = testeRepository.testCriado(usuarioLogado.getUsuario().getId(), idTeste);
        if (teste != null) {
            usuarioLogado.setTeste(teste);
        } else {
            result.notFound();
        }
    }

    @Get("teste/{idTeste}/remover")
    @Logado
    public void remove(Long idTeste) {
        this.isTestePertenceUsuarioLogado(idTeste);
    }

    @Delete()
    @Logado
    public void removed(final String senha) {
        validator.checking(new Validations() {

            {
                that(!senha.isEmpty(), "campo.obrigatorio", "campo.obrigatorio", "senha");
            }
        });
        validator.onErrorRedirectTo(this).remove(usuarioLogado.getTeste().getId());
        String senhaCriptografada = Criptografa.criptografar(senha);
        if (senhaCriptografada.equals(usuarioLogado.getUsuario().getSenha())) {
            testeRepository.destroy(usuarioLogado.getTeste());
            usuarioLogado.setTeste(null);
            result.redirectTo(LoginController.class).logado();
        } else {
            validator.checking(new Validations() {
                {
                    that(!senha.isEmpty(), "senha.incorreta", "senha.incorreta");
                }
            });
            validator.onErrorRedirectTo(this).remove(usuarioLogado.getTeste().getId());
        }
    }

    @Get()
    public void realizarTeste() {
    }
}
