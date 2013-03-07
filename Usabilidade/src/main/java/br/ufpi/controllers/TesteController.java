package br.ufpi.controllers;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;

import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.Validations;
import br.com.caelum.vraptor.view.Results;
import br.ufpi.annotation.Logado;
import br.ufpi.componets.TesteView;
import br.ufpi.componets.UsuarioLogado;
import br.ufpi.componets.ValidateComponente;
import br.ufpi.models.ElementosTeste;
import br.ufpi.models.Pergunta;
import br.ufpi.models.Questionario;
import br.ufpi.models.Tarefa;
import br.ufpi.models.Teste;
import br.ufpi.models.TipoConvidado;
import br.ufpi.models.Usuario;
import br.ufpi.models.vo.ConvidadoVO;
import br.ufpi.repositories.ConvidadoRepository;
import br.ufpi.repositories.TesteRepository;
import br.ufpi.util.BaseUrl;
import br.ufpi.util.Criptografa;
import br.ufpi.util.EmailUtils;
import br.ufpi.util.Paginacao;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Resource
public class TesteController extends BaseController {

	private final TesteRepository testeRepository;
	private final ConvidadoRepository convidadoRepository;
	private final HttpServletRequest request;

	public TesteController(Result result, Validator validator,
			TesteView testeView, UsuarioLogado usuarioLogado,
			ValidateComponente validateComponente,
			TesteRepository testeRepository,
			ConvidadoRepository convidadoRepository, HttpServletRequest request) {
		super(result, validator, testeView, usuarioLogado, validateComponente);
		this.testeRepository = testeRepository;
		this.convidadoRepository = convidadoRepository;
		this.request = request;
	}

	/**
	 * Contera todo o procedimento para criar um teste. Na view contera os dados
	 * do teste por meio da seção UsuarioLogado.teste
	 */
	@Get("teste/criar")
	@Logado
	public void criarTeste() {
	}

	@Logado
	@Post()
	public void salvar(String titulo) {
		validateComponente.validarString(titulo, "testes.titulo");
		validator.onErrorRedirectTo(this).criarTeste();
		Teste teste = new Teste();
		teste.setUsuarioCriador(usuarioLogado.getUsuario());
		teste.setTitulo(titulo);
		teste.setTituloPublico(titulo);
		teste.setSatisfacao(new Questionario());
		testeRepository.create(teste);
		result.redirectTo(this).passo1(teste.getId());
	}

	@Logado
	@Get("teste/{id}/editar/passo1")
	public void passo1(Long id) {
		this.testeNaoLiberadoPertenceUsuarioLogado(id);
	}

	/**
	 * Usado para inserir os dados passados no passo2 que estavam com erros
	 * 
	 * @param idTeste
	 *            Identificador do Teste que o usuario esta tentando alterar
	 * @param titulo
	 *            Titulo que o usuario colocou no teste
	 * @param tituloPublico
	 *            Titulo publico para ser mostrado para os usuarios que
	 *            realizaram o teste
	 * @param textoIndroducao
	 *            Um breve texto que o usuario vai visualizar ao iniciar o teste
	 */
	private void passo1(Long idTeste, String titulo, String tituloPublico,
			String textoIndroducao) {
		Teste teste = testeView.getTeste();
		teste.setTitulo(titulo);
		teste.setTituloPublico(tituloPublico);
		teste.setTextoIndroducao(textoIndroducao);
	}

	@Logado
	@Post("teste/{idTeste}/editar/passo2")
	public void passo2(Long idTeste, String titulo, String tituloPublico,
			String textoIndroducao) {
		validateComponente.validarId(idTeste);
		this.testeNaoLiberadoPertenceUsuarioLogado(idTeste);
		validateComponente.validarString(titulo, "testes.passo1.titulo");
		validateComponente.validarString(tituloPublico,
				"testes.passo1.titulopublico");
		validateComponente.validarString(textoIndroducao,
				"testes.passo1.introducao");
		if (validator.hasErrors()) {
			this.passo1(idTeste, titulo, tituloPublico, textoIndroducao);
		}
		validator.onErrorUsePageOf(this).passo1(idTeste);

		Teste teste = testeView.getTeste();
		teste.setTitulo(titulo);
		teste.setTituloPublico(tituloPublico);
		teste.setTextoIndroducao(textoIndroducao);
		usuarioLogado.getUsuario().getTestesCriados();
		testeRepository.update(teste);
		result.include("elementosTeste", organizarElementos());
	}

	@Logado
	@Get("teste/{idTeste}/editar/passo2")
	public void passo2(Long idTeste) {
		this.testeNaoLiberadoPertenceUsuarioLogado(idTeste);
		result.include("elementosTeste", organizarElementos());
	}

	/**
	 * Passo para a escolha dos usuarios. Pode ser por questionario ou por
	 * escolha de caracterização. A Escolha vai poder ser por uma lista de
	 * Usuarios
	 * 
	 * @param idTeste
	 */
	@Logado
	@Get("teste/{idTeste}/editar/passo3")
	public void passo3(Long idTeste) {
		int quantidade = 50;
		int numeroPagina = 1;
		this.testeNaoLiberadoPertenceUsuarioLogado(idTeste);
		Paginacao<ConvidadoVO> usuariosConvidados = testeRepository
				.getUsuariosConvidados(idTeste, 1, 50);
		result.include("convidados", usuariosConvidados.getListObjects());
		result.include("totalUsuariosEscolhidos", usuariosConvidados.getCount());
		Paginacao<Usuario> paginacaoUsuariosLivres = testeRepository
				.usuariosLivresParaPartciparTeste(idTeste, numeroPagina,
						quantidade);
		result.include("usuariosLivres",
				paginacaoUsuariosLivres.getListObjects());
		result.include("totalUsuarios", paginacaoUsuariosLivres.getCount());
	}

	/**
	 * Passo para a liberação do Teste
	 * 
	 * @param idTeste
	 */
	@Logado
	@Get("teste/{idTeste}/editar/passo4")
	public void passo4(Long idTeste) {
		this.testeNaoLiberadoPertenceUsuarioLogado(idTeste);

	}

	/**
	 * Liber um Teste para que os usuarios possa Liberar para os usuarios
	 * selecionados o teste.
	 * 
	 * @param idTeste
	 *            Identificador do Teste a ser Liberado
	 */
	@Logado
	@Post("teste/liberar")
	public void liberarTeste(Long idTeste) {
		this.testeNaoLiberadoPertenceUsuarioLogado(idTeste);
		Teste teste = testeView.getTeste();
		List<Tarefa> tarefas = teste.getTarefas();
		if (tarefas.isEmpty() || tarefas == null) {
			validator.checking(new Validations() {
				{
					that(false, "sem.tarefa.cadastrada",
							"sem.tarefa.cadastrada");
				}
			});
		}

		validator.onErrorRedirectTo(this).passo4(idTeste);
		BaseUrl.getInstance(request);
		EmailUtils email = new EmailUtils();
		List<ConvidadoVO> convidadoVOs = testeRepository
				.getUsuariosConvidadosAll(idTeste);
		for (ConvidadoVO usuario : convidadoVOs) {
			email.enviarConviteTeste(usuario.getUsuario(), teste);
		}
		teste.setLiberado(true);
		teste.setDataLiberacao(new Date(System.currentTimeMillis()));
		testeRepository.update(teste);
		this.result.redirectTo(LoginController.class).logado(1);
	}

	/**
	 * Usado para convidar um lista de usuarios a participarem de um teste Caso
	 * o teste jah tenha sido liberado ele redireciona para a pagina convidar
	 * caso contrario para pagina passo3
	 * 
	 * @param idUsuarios
	 *            Lista de Usuarios que foram marcados para participar do teste
	 * @param idTeste
	 * @param tipoConvidado
	 *            True para USER e False para TESTER
	 */
	@Logado
	@Post("teste/convidar/usuario")
	public void convidarUsuario(List<Long> idUsuarios, Long idTeste,
			boolean tipoConvidado) {
		System.out.println("Tipo Convidado " + tipoConvidado);
		System.out.println("Teste id " + idTeste);
		System.out.println("Identificador dos usuario " + idUsuarios);
		validateComponente.validarId(idTeste);
		if (idUsuarios != null && !idUsuarios.isEmpty()) {
			this.testePertenceUsuarioLogado(idTeste);
			try {
				convidadoRepository.convidarUsuarios(idUsuarios, idTeste,
						TipoConvidado.parseTipoConvidado(tipoConvidado));
			} catch (PersistenceException e) {
				validateComponente.gerarErroCampoAlterado();
				validator.onErrorForwardTo(this).listarUsuriosNaoConvidados(
						idTeste, 1);
			}
			if (!testeView.getTeste().isLiberado()) {
				result.redirectTo(this).passo3(testeView.getTeste().getId());
			} else {
				result.redirectTo(this).listarUsuriosNaoConvidados(
						testeView.getTeste().getId(), 1);
			}
		} else {
			result.redirectTo(this).passo3(idTeste);
		}

	}

	/**
	 * 
	 * Usado para desconvidar um lista de usuarios a participarem de um teste
	 * Caso o teste jah tenha sido liberado ele redireciona para a pagina
	 * convidar caso contrario para pagina passo3
	 * 
	 * @param idUsuarios
	 *            Lista de identificadores de usuarios que não participaram de
	 *            um teste
	 * @param idTeste
	 */
	@Logado
	@Post("teste/desconvidar/usuario")
	public void desconvidarUsuario(List<Long> idUsuarios, Long idTeste) {

		validateComponente.validarId(idTeste);
		if (idUsuarios != null && !idUsuarios.isEmpty()) {
			testePertenceUsuarioLogado(idTeste);
			convidadoRepository.desconvidarUsuarios(idUsuarios, testeView
					.getTeste().getId());
			if (!testeView.getTeste().isLiberado()) {
				result.redirectTo(this).passo3(testeView.getTeste().getId());
			} else {
				result.redirectTo(this).listarUsuriosNaoConvidados(
						testeView.getTeste().getId(), 1);
			}
		} else {
			result.redirectTo(this).passo3(idTeste);
		}

	}

	/**
	 * * Analisa se o id do teste buscado pertence ao usuario logado e se o
	 * teste ainda não foi liberado, se não pertencer ao usuario buscado sera
	 * redirecionado para pagina Home
	 * 
	 * @param idTeste
	 *            buscado e analisado para ver se pertence ao usuario
	 * 
	 */
	private void testeNaoLiberadoPertenceUsuarioLogado(Long idTeste) {
		validateComponente.validarId(idTeste);
		Teste teste = testeRepository.getTestCriadoNaoLiberado(usuarioLogado
				.getUsuario().getId(), idTeste);
		validateComponente.validarObjeto(teste);
		testeView.setTeste(teste);

	}

	/**
	 * * Analisa se o id do teste buscado pertence ao usuario logado, se não
	 * pertencer ao usuario buscado sera redirecionado para pagina 404.
	 * 
	 * @param idTeste
	 *            buscado e analisado para ver se pertence ao usuario
	 * 
	 */
	private void testePertenceUsuarioLogado(Long idTeste) {
		validateComponente.validarId(idTeste);
		Teste teste = testeRepository.getTestCriado(usuarioLogado.getUsuario()
				.getId(), idTeste);
		validateComponente.validarObjeto(teste);
		testeView.setTeste(teste);

	}

	@Get("teste/{idTeste}/remover")
	@Logado
	public void remove(Long idTeste) {
		this.testePertenceUsuarioLogado(idTeste);
	}

	@Delete()
	@Logado
	public void removed(final String senha, Long idTeste) {
		validateComponente.validarString(senha, "login.senha");
		validator.onErrorRedirectTo(this).remove(idTeste);
		String senhaCriptografada = Criptografa.criptografar(senha);
		if (senhaCriptografada.equals(usuarioLogado.getUsuario().getSenha())) {
			this.testePertenceUsuarioLogado(idTeste);
			testeRepository.destroy(testeView.getTeste());
			result.redirectTo(LoginController.class).logado(1);
		} else {
			validator.checking(new Validations() {

				{
					that(false, "senha.incorreta", "senha.incorreta");
				}
			});
			validator.onErrorRedirectTo(this).remove(idTeste);
		}
	}

	/**
	 * Usado para convidar usuarios de um teste apos este teste ter sido
	 * liberado
	 * 
	 * @param idTeste
	 *            Identificador do teste ao qual se quer adicionar novos
	 *            convidados
	 * @param numeroPagina
	 */
	@Logado
	@Get({ "teste/{idTeste}/convidar/usuarios",
			"teste/{idTeste}/convidar/usuarios/pag/{numeroPagina}" })
	public void listarUsuriosNaoConvidados(Long idTeste, int numeroPagina) {
		if (numeroPagina <= 0 || numeroPagina < 0)
			numeroPagina = 1;
		this.testePertenceUsuarioLogado(idTeste);
		Paginacao<Usuario> paginacao = testeRepository
				.usuariosLivresParaPartciparTeste(idTeste, numeroPagina,
						Paginacao.OBJETOS_POR_PAGINA);
		result.include("usuariosLivres", paginacao.getListObjects());
		Long qttObjetosNoBanco = paginacao.getCount();
		result.include("totalUsuarios", qttObjetosNoBanco);
		paginacao.geraPaginacao(numeroPagina, Paginacao.OBJETOS_POR_PAGINA,
				qttObjetosNoBanco, result);

	}

	/**
	 * Usado para listar os usuarios que foram convidados para participar dos
	 * testes
	 * 
	 * @param idTeste
	 *            Identificador do teste ao qual se quer adicionar novos
	 *            convidados
	 * @param numeroPagina
	 */
	@Logado
	@Get({ "teste/{idTeste}/usuarios/convidados",
			"teste/{idTeste}/usuarios/convidados/pag/{numeroPagina}" })
	public void listarUsuriosConvidados(Long idTeste, int numeroPagina) {
		if (numeroPagina <= 0 || numeroPagina < 0)
			numeroPagina = 1;
		this.testePertenceUsuarioLogado(idTeste);
		Paginacao<ConvidadoVO> paginacao = testeRepository
				.getUsuariosConvidados(idTeste, numeroPagina,
						Paginacao.OBJETOS_POR_PAGINA);
		result.include("convidados", paginacao.getListObjects());
		Long qttObjetosNoBanco = paginacao.getCount();
		result.include("totalUsuariosEscolhidos", qttObjetosNoBanco);
		paginacao.geraPaginacao(numeroPagina, Paginacao.OBJETOS_POR_PAGINA,
				qttObjetosNoBanco, result);
	}

	@Logado
	public void meusProjetos(int numeroPagina) {
		validateComponente.validarId((long) numeroPagina);
		Paginacao<Teste> paginacao = testeRepository.getTestesParticipados(
				usuarioLogado.getUsuario().getId(), numeroPagina, 50);
		result.include("testesParticipados", paginacao.getListObjects());
		Long qttObjetosNoBanco = paginacao.getCount();
		// result.include("testesParticipadosCount", qttObjetosNoBanco);
		paginacao.geraPaginacao(numeroPagina, Paginacao.OBJETOS_POR_PAGINA,
				qttObjetosNoBanco, result);

	}

	// @Get("exibir/{idTeste}")
	// public StringBuilder exibir(Long idTeste) {
	// StringBuilder builder = new StringBuilder();
	// Teste teste = this.testeRepository.find(idTeste);
	// for (Tarefa tarefa : teste.getTarefas()) {
	// List<Acao> acoes = tarefa.getFluxoIdeal().getFluxo().getAcoes();
	// builder.append(acoes);
	// for (FluxoUsuario fluxo : tarefa.getFluxoUsuario()) {
	// builder.append(fluxo.getFluxo().getAcoes());
	// }
	// }
	// return builder;
	//
	// }

	/**
	 * Lista todos os testes liberados do usuario logado.
	 * 
	 * @param numeroPagina
	 *            Numero da pagina que o usuario esta visualizando no momento
	 */

	@Logado
	@Get({ "testes/liberados/pag/{numeroPagina}", "testes/liberados" })
	public void listarTestesLiberados(int numeroPagina) {
		if (numeroPagina <= 0) {
			numeroPagina = 1;
		}
		validateComponente.validarId((long) numeroPagina);
		Paginacao<Teste> testesCriadosLiberados = testeRepository
				.getTestesCriadosLiberados(usuarioLogado.getUsuario().getId(),
						Paginacao.OBJETOS_POR_PAGINA, numeroPagina);
		Long qtdTestesTotais = testesCriadosLiberados.getCount();
		result.include("testesLiberados",
				testesCriadosLiberados.getListObjects());
		testesCriadosLiberados.geraPaginacao(numeroPagina,
				Paginacao.OBJETOS_POR_PAGINA, qtdTestesTotais, result);
	}

	@Logado
	@Get({ "/testes/convidados", "/testes/convidados/pag/{numeroPagina}" })
	public void listarTestesConvidados(int numeroPagina) {
		if (numeroPagina <= 0) {
			numeroPagina = 1;
		}
		Paginacao<ConvidadoVO> paginacao = testeRepository
				.findTestesConvidados(usuarioLogado.getUsuario().getId(),
						numeroPagina, Paginacao.OBJETOS_POR_PAGINA);
		result.include("testesConvidados", paginacao.getListObjects());
		Long qttObjetosNoBanco = paginacao.getCount();
		paginacao.geraPaginacao(numeroPagina, Paginacao.OBJETOS_POR_PAGINA,
				qttObjetosNoBanco, result);
	}

	@SuppressWarnings({ "unchecked" })
	private List<ElementosTeste> organizarElementos() {
		Gson gson = new Gson();
		Teste teste = testeView.getTeste();
		String elementosTeste2 = teste.getElementosTeste();
		if(elementosTeste2==null)
			return null;
		
		Type listType = new TypeToken<List<ElementosTeste>>(){}.getType();
		List<ElementosTeste> elementosTestes = (List<ElementosTeste>) gson.fromJson(elementosTeste2, listType);
		for (ElementosTeste elementosTeste : elementosTestes) {
			if (elementosTeste.getTipo() == 'T') {
				for (Tarefa tarefa : teste.getTarefas()) {
					if (tarefa.getId().equals(elementosTeste.getId()))
						elementosTeste.setTitulo(tarefa.getNome());
				}
			} else {
				for (Pergunta pergunta : teste.getSatisfacao().getPerguntas()) {
					if (pergunta.getId().equals(elementosTeste.getId())) {
						elementosTeste.setTitulo(pergunta.getTitulo());
					}
				}
			}
		}
		return elementosTestes;
	}

	@Logado
	@Post("teste/ordenar")
	public void salvaListaElementos(Long idTeste, String listaElementos) {
		validateComponente.validarId(idTeste);
		this.testeNaoLiberadoPertenceUsuarioLogado(idTeste);
		Teste teste = testeView.getTeste();
		teste.setElementosTeste(listaElementos);
		testeRepository.update(teste);
		result.use(Results.json()).from("sucesso").serialize();
	}
	@Logado
	@Get("teste/{idTeste}/analise")
	public void analise(Long idTeste){
		this.testePertenceUsuarioLogado(idTeste);
		result.include("elementosTeste", organizarElementos());
	}
}
