package br.ufpi.controllers;

import java.util.List;

import br.com.caelum.vraptor.Delete;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.Validations;
import br.ufpi.annotation.Logado;
import br.ufpi.componets.UsuarioLogado;
import br.ufpi.models.Questionario;
import br.ufpi.models.Teste;
import br.ufpi.models.Usuario;
import br.ufpi.repositories.ConvidadoRepository;
import br.ufpi.repositories.TesteRepository;
import br.ufpi.util.Criptografa;
import br.ufpi.util.Paginacao;

@Resource
public class TesteController {

	private final Result result;
	private final TesteRepository testeRepository;
	private UsuarioLogado usuarioLogado;
	private final Validator validator;
	private final ConvidadoRepository convidadoRepository;

	public TesteController(Result result, TesteRepository testeRepository,
			UsuarioLogado usuarioLogado, Validator validator,
			ConvidadoRepository convidadoRepository) {
		super();
		this.result = result;
		this.testeRepository = testeRepository;
		this.usuarioLogado = usuarioLogado;
		this.validator = validator;
		this.convidadoRepository = convidadoRepository;
	}

	/**
	 * Contera todo o procedimento para criar um teste. Na view contera os dados
	 * do teste por meio da seção UsuarioLogado.teste
	 */
	@Get("teste/criar")
	@Logado
	public void criarTeste() {
		this.usuarioLogado.setTeste(new Teste());
		this.usuarioLogado.getTeste().setUsuarioCriador(
				usuarioLogado.getUsuario());
	}

	@Logado
	@Post()
	public void salvar(String titulo) {
		if (titulo != null) {
			Teste teste = usuarioLogado.getTeste();
			teste.setTitulo(titulo);
			Questionario questionario = new Questionario();
			questionario.setTeste(teste);
			teste.setSatisfacao(questionario);
			if (teste.getId() == null) {
				testeRepository.create(teste);
			} else {
				testeRepository.update(teste);
			}
			result.redirectTo(this).passo1(teste.getId());
		} else {
			criarTeste();
		}
	}

	@Logado
	@Get("teste/{id}/editar/passo1")
	public void passo1(Long id) {
		this.testeNaoRealizadoPertenceUsuarioLogado(id);

	}

	@Logado
	@Post("teste/{idTeste}/editar/passo2")
	public void passo2(Long idTeste, String titulo, String tituloPublico,
			String textoIndroducao) {
		this.testeNaoRealizadoPertenceUsuarioLogado(idTeste);
		Teste teste = usuarioLogado.getTeste();
		teste.setTitulo(titulo);
		teste.setTituloPublico(tituloPublico);
		teste.setTextoIndroducao(textoIndroducao);
		usuarioLogado.getUsuario().getTestesCriados();
		testeRepository.update(teste);
		result.include("tarefas", usuarioLogado.getTeste().getTarefas());
		result.include("perguntas", usuarioLogado.getTeste().getSatisfacao()
				.getPerguntas());
	}

	@Logado
	@Get("teste/{idTeste}/editar/passo2")
	public void passo2(Long idTeste) {
		this.testeNaoRealizadoPertenceUsuarioLogado(idTeste);
		result.include("tarefas", usuarioLogado.getTeste().getTarefas());
		result.include("perguntas", usuarioLogado.getTeste().getSatisfacao()
				.getPerguntas());

	}

	/**
	 * Passo para a escolha dos usuarios. Pode ser por questionario ou por
	 * escolha de caracterização. A Escolha vai poder ser por uma lista de
	 * Usuarios
	 */
	@Logado
	@Get("teste/{idTeste}/editar/passo3")
	public void passo3(Long idTeste) {
		this.testeNaoRealizadoPertenceUsuarioLogado(idTeste);
		Paginacao<Usuario> usuariosConvidados = testeRepository
				.getUsuariosConvidados(idTeste, 1, 50);
		result.include("usuariosEscolhidos",
				usuariosConvidados.getListObjects());
		result.include("totalUsuariosEscolhidos", usuariosConvidados.getCount());
		Paginacao<Usuario> paginacaoUsuariosLivres = testeRepository
				.usuariosLivresParaPartciparTeste(idTeste, 1, 50);
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
		this.testeNaoRealizadoPertenceUsuarioLogado(idTeste);

	}

	/**
	 * Liber um Teste para que os usuarios possa Liberar para os usuarios
	 * selecionados o teste.
	 * 
	 * @param idteste
	 *            Identificador do Teste a ser Liberado
	 */
	@Logado
	@Post("teste/liberar")
	public void liberarTeste(Long idteste) {
		this.testeNaoRealizadoPertenceUsuarioLogado(idteste);
		Teste teste = usuarioLogado.getTeste();
		teste.setLiberado(true);
		testeRepository.update(teste);
	}

	/**
	 * Usado para selecionar uma lista de usuarios
	 * 
	 * @param idUsuarios
	 *            Lista de Usuarios que foram marcados para participar do teste
	 */
	@Logado
	@Post("teste/convidar/usuario")
	public void convidarUsuario(List<Long> idUsuarios) {
		if (idUsuarios != null) {
			convidadoRepository.convidarUsuarios(idUsuarios, usuarioLogado
					.getTeste().getId());
		}
		result.redirectTo(this).passo3(usuarioLogado.getTeste().getId());

	}

	@Logado
	@Post("teste/desconvidar/usuario")
	public void desconvidarUsuario(List<Long> idUsuarios) {
		if (idUsuarios != null) {
			convidadoRepository.desconvidarUsuarios(idUsuarios, usuarioLogado
					.getTeste().getId());
		}
		result.redirectTo(this).passo3(usuarioLogado.getTeste().getId());

	}

	/**
	 * * Analisa se o id do teste buscado pertence ao usuario logado, se não
	 * pertencer ao usuario buscado sera redirecionado para pagina 404.
	 * 
	 * @param idTeste
	 *            buscado e analisado para ver se pertence ao usuario
	 * 
	 */
	private void testeNaoRealizadoPertenceUsuarioLogado(Long idTeste) {
		if (idTeste != null) {
			Teste teste = testeRepository.getTestCriadoNaoRealizado(
					usuarioLogado.getUsuario().getId(), idTeste);
			if (teste != null) {
				usuarioLogado.setTeste(teste);
			} else {
				result.notFound();
			}
		} else {
			result.notFound();
		}
	}

	@Get("teste/{idTeste}/remover")
	@Logado
	public void remove(Long idTeste) {
		this.testeNaoRealizadoPertenceUsuarioLogado(idTeste);
	}

	@Delete()
	@Logado
	public void removed(final String senha) {
		validator.checking(new Validations() {

			{
				that(!senha.isEmpty(), "campo.obrigatorio",
						"campo.obrigatorio", "senha");
			}
		});
		validator.onErrorRedirectTo(this).remove(
				usuarioLogado.getTeste().getId());
		String senhaCriptografada = Criptografa.criptografar(senha);
		if (senhaCriptografada.equals(usuarioLogado.getUsuario().getSenha())) {
			System.out.println("Id de Teste Removido"
					+ usuarioLogado.getTeste().getId());
			testeRepository.destroy(usuarioLogado.getTeste());
			usuarioLogado.setTeste(null);
			result.redirectTo(LoginController.class).logado();
		} else {
			validator.checking(new Validations() {

				{
					that(!senha.isEmpty(), "senha.incorreta", "senha.incorreta");
				}
			});
			validator.onErrorRedirectTo(this).remove(
					usuarioLogado.getTeste().getId());
		}
	}

	@Get()
	public void realizarTeste() {
	}
}
