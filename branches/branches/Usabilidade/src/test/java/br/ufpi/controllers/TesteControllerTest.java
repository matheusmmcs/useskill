/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpi.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.validator.Message;
import br.com.caelum.vraptor.validator.ValidationException;
import br.ufpi.controllers.procedure.TesteTestProcedure;
import br.ufpi.models.Convidado;
import br.ufpi.models.Pergunta;
import br.ufpi.models.Tarefa;
import br.ufpi.models.Teste;
import br.ufpi.repositories.AbstractDaoTest;
import br.ufpi.repositories.TesteRepository;
import br.ufpi.repositories.Implement.ConvidadoRepositoryImpl;

/**
 * 
 * @author Cleiton
 */
public class TesteControllerTest extends AbstractDaoTest {
	private TesteRepository repository;
	private TesteController instance;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		repository = TesteTestProcedure
				.newInstanceTesteRepository(entityManager);
		instance = TesteTestProcedure.newInstanceTesteController(entityManager,
				result);
	}

	/**
	 * Test of criarTeste method, of class TesteController.
	 */
	@Test
	public void testCriarTeste() {
		System.out.println("criarTeste");
		instance.criarTeste();
	}

	/**
	 * Test of salvar method, of class TesteController.
	 */
	@Test
	public void testSalvar() {
		System.out.println("salvar");
		String titulo = "Novo Teste";
		int qAntes = repository.findAll().size();
		instance.salvar(titulo);
		Assert.assertEquals(qAntes + 1, repository.findAll().size());
	}

	/**
	 * Test of salvar method, of class TesteController. Tenta Salvar sem passar
	 * um titulo
	 */
	@Test
	public void testSalvarSemTitulo() {
		System.out.println("salvar");
		String titulo = "";
		int qAntes = repository.findAll().size();
		try {
			instance.salvar(titulo);
		} catch (ValidationException validationException) {
			Assert.assertEquals("Não pode deixar salvar pois campo esta vazio",
					"campo.titulo.obrigatorio", validationException.getErrors()
							.get(0).getCategory());
		}
		Assert.assertEquals(qAntes, repository.findAll().size());
	}

	/**
	 * Test of salvar method, of class TesteController. Tenta Salvar sem passar
	 * um titulo
	 */
	@Test
	public void testSalvarTituloNulo() {
		System.out.println("salvar");
		String titulo = null;
		int qAntes = repository.findAll().size();
		try {
			instance.salvar(titulo);
		} catch (ValidationException validationException) {
			Assert.assertEquals("Não pode deixar salvar pois campo esta vazio",
					"campo.titulo.obrigatorio", validationException.getErrors()
							.get(0).getCategory());
		}
		Assert.assertEquals(qAntes, repository.findAll().size());
	}

	/**
	 * Test of passo4 method, of class TesteController. Usuario é o dono do
	 * teste buscado
	 */
	@Test
	public void testPasso4() {
		System.out.println("passo1");
		Long id = 2l;
		instance.passo4(id);
		Assert.assertEquals(
				"Usuario é o criador do teste entao deveria retorna o mesmo valor do id passado",
				id, instance.testeView.getTeste().getId());

	}

	/**
	 * Test of passo4 method, of class TesteController. Usuario não é o dono do
	 * teste buscado
	 */
	@Test
	public void testPasso4UsuarioNaoEDonoDoTeste() {
		System.out.println("Passo1UsuarioNaoEDonoDoTeste");
		Long id = 3l;
		try {
			instance.passo4(id);
		} catch (ValidationException validationException) {
			Assert.assertEquals(
					"Não pode deixar salvar pois usuario nao é dono do Teste",
					"campo.form.alterado",
					validationException.getErrors().get(0).getCategory());
		}

	}

	/**
	 * Test of passo4 method, of class TesteController. Usuario não passa o
	 * idTeste
	 */
	@Test
	public void testPasso4UsuarioNaoPassaIDTeste() {
		System.out.println("Passo1UsuarioNaoPassaIDTeste");
		Long id = null;
		try {
			instance.passo4(id);
		} catch (ValidationException validationException) {
			Assert.assertEquals(
					"Não pode deixar salvar pois usuario nao é dono do Teste",
					"campo.form.alterado",
					validationException.getErrors().get(0).getCategory());
		}

	}

	/**
	 * Test of passo1 method, of class TesteController. Usuario é o dono do
	 * teste buscado, mas ele esta liberado
	 */
	@Test
	public void testPasso4UsuarioEDonoDoTesteLiberado() {
		System.out.println("passo1");
		Long id = 4l;
		try {
			instance.passo4(id);
		} catch (ValidationException validationException) {
			Assert.assertEquals(
					"Não pode deixar salvar pois usuario nao é dono do Teste",
					"campo.form.alterado",
					validationException.getErrors().get(0).getCategory());
		}

	}

	/**
	 * Test of passo1 method, of class TesteController. Usuario passou id do
	 * teste não existente
	 */
	@Test
	public void testPasso1UsuarioIDtesteNaoExiste() {
		System.out.println("Passo1UsuarioIDtesteNaoExiste");
		Long id = 5500l;
		try {
			instance.passo1(id);
		} catch (ValidationException validationException) {
			Assert.assertEquals(
					"Não pode deixar salvar pois usuario nao é dono do Teste",
					"campo.form.alterado",
					validationException.getErrors().get(0).getCategory());
		}

	}

	/**
	 * Test of passo1 method, of class TesteController. Usuario é o dono do
	 * teste buscado
	 */
	@Test
	public void testPasso1() {
		System.out.println("passo1");
		Long id = 2l;
		instance.passo1(id);
		Assert.assertEquals(
				"Usuario é o criador do teste entao deveria retorna o mesmo valor do id passado",
				id, instance.testeView.getTeste().getId());

	}

	/**
	 * Test of passo1 method, of class TesteController. Usuario não é o dono do
	 * teste buscado
	 */
	@Test
	public void testPasso1UsuarioNaoEDonoDoTeste() {
		System.out.println("Passo1UsuarioNaoEDonoDoTeste");
		Long id = 3l;
		try {
			instance.passo1(id);
		} catch (ValidationException validationException) {
			Assert.assertEquals(
					"Não pode deixar salvar pois usuario nao é dono do Teste",
					"campo.form.alterado",
					validationException.getErrors().get(0).getCategory());
		}

	}

	/**
	 * Test of passo1 method, of class TesteController. Usuario não passa o
	 * idTeste
	 */
	@Test
	public void testPasso1UsuarioNaoPassaIDTeste() {
		System.out.println("Passo1UsuarioNaoPassaIDTeste");
		Long id = null;
		try {
			instance.passo1(id);
		} catch (ValidationException validationException) {
			Assert.assertEquals(
					"Não pode deixar salvar pois usuario nao é dono do Teste",
					"campo.form.alterado",
					validationException.getErrors().get(0).getCategory());
		}

	}

	/**
	 * Test of passo1 method, of class TesteController. Usuario é o dono do
	 * teste buscado, mas ele esta liberado
	 */
	@Test
	public void testPasso1UsuarioEDonoDoTesteLiberado() {
		System.out.println("passo1");
		Long id = 4l;
		try {
			instance.passo1(id);
		} catch (ValidationException validationException) {
			Assert.assertEquals(
					"Não pode deixar salvar pois usuario nao é dono do Teste",
					"campo.form.alterado",
					validationException.getErrors().get(0).getCategory());
		}

	}

	/**
	 * Test of passo1 method, of class TesteController. Usuario passou id do
	 * teste não existente
	 */
	@Test
	public void testPasso4UsuarioIDtesteNaoExiste() {
		System.out.println("Passo1UsuarioIDtesteNaoExiste");
		Long id = 5500l;
		try {
			instance.passo4(id);
		} catch (ValidationException validationException) {
			Assert.assertEquals(
					"Não pode deixar salvar pois usuario nao é dono do Teste",
					"campo.form.alterado",
					validationException.getErrors().get(0).getCategory());
		}

	}

	/**
	 * Test of passo2 method, of class TesteController.
	 */
	@Test
	public void testPasso2_4args() {
		System.out.println("passo2");
		Long idTeste = 1l;
		String titulo = "Novo Teste";
		String tituloPublico = " titulo publico";
		String textoIndroducao = "texto introdução";
		Teste testeAntes = repository.find(idTeste);
		instance.passo2(idTeste, titulo, tituloPublico, textoIndroducao);
		Teste testeDepois = repository.find(idTeste);
		Assert.assertEquals(
				"Texto deveria ter mudado, pois o teste foi alterado",
				textoIndroducao, testeDepois.getTextoIndroducao());
		Assert.assertFalse("Texto deveria ter mudado", !testeAntes.getTitulo()
				.equals(testeDepois.getTitulo()));
	}

	/**
	 * Test of passo1 method, of class TesteController. Usuario passou id do
	 * teste não existente
	 */
	@Test
	public void testPasso2_4argsUsuarioIDtesteNaoExiste() {
		System.out.println("Passo2_4argsUsuarioIDtesteNaoExiste");
		Long id = 5500l;
		String titulo = "Novo Teste";
		String tituloPublico = " titulo publico";
		String textoIndroducao = "texto introdução";
		try {
			instance.passo2(id, titulo, tituloPublico, textoIndroducao);
		} catch (ValidationException validationException) {
			Assert.assertEquals(
					"Não pode deixar salvar pois usuario nao é dono do Teste",
					"campo.form.alterado",
					validationException.getErrors().get(0).getCategory());
		}

	}

	/**
	 * Test of passo2_4args method, of class TesteController. Usuario não é o
	 * dono do teste buscado
	 */
	@Test
	public void testPasso2_4argsUsuarioNaoEDonoDoTeste() {
		System.out.println("Passo1UsuarioNaoEDonoDoTeste");
		Long id = 3l;
		String titulo = "Novo Teste";
		String tituloPublico = " titulo publico";
		String textoIndroducao = "texto introdução";
		try {
			instance.passo2(id, titulo, tituloPublico, textoIndroducao);
		} catch (ValidationException validationException) {
			Assert.assertEquals(
					"Não pode deixar salvar pois usuario nao é dono do Teste",
					"campo.form.alterado",
					validationException.getErrors().get(0).getCategory());
		}

	}

	/**
	 * Test of passo1 method, of class TesteController. Usuario não passa o
	 * idTeste
	 */
	@Test
	public void testPasso2_4argsUsuarioNaoPassaIDTeste() {
		System.out.println("Passo1UsuarioNaoPassaIDTeste");
		Long id = null;
		String titulo = "Novo Teste";
		String tituloPublico = " titulo publico";
		String textoIndroducao = "texto introdução";
		try {
			instance.passo2(id, titulo, tituloPublico, textoIndroducao);
		} catch (ValidationException validationException) {
			Assert.assertEquals(
					"Não pode deixar salvar pois usuario nao é dono do Teste",
					"campo.form.alterado",
					validationException.getErrors().get(0).getCategory());
		}

	}

	/**
	 * Test of passo2_4args method, of class TesteController. Usuario é o dono
	 * do teste buscado, mas ele esta liberado
	 */
	@Test
	public void testPasso2_4argsUsuarioEDonoDoTesteLiberado() {
		System.out.println("passo1");
		Long id = 4l;
		String titulo = "Novo Teste";
		String tituloPublico = " titulo publico";
		String textoIndroducao = "texto introdução";
		try {
			instance.passo2(id, titulo, tituloPublico, textoIndroducao);
		} catch (ValidationException validationException) {
			Assert.assertEquals(
					"Não pode deixar salvar pois usuario nao é dono do Teste",
					"campo.form.alterado",
					validationException.getErrors().get(0).getCategory());
		}

	}

	/**
	 * Test of passo2_4args method, of class TesteController. Usuario é o dono
	 * do teste buscado, mas passa titulo vazio
	 */
	@Test
	public void testPasso2_4argsUsuarioPassaCampoTituloVazio() {
		System.out.println("passo1");
		Long id = 1l;
		String titulo = "";
		String tituloPublico = " titulo publico";
		String textoIndroducao = "texto introdução";
		Teste testeAntes = repository.find(1l);
		try {
			instance.passo2(id, titulo, tituloPublico, textoIndroducao);
		} catch (ValidationException validationException) {
			Assert.assertEquals(
					"Não pode deixar salvar pois usuario passou campo titulo vazio",
					"campo.titulo.obrigatorio", validationException.getErrors()
							.get(0).getCategory());
		}
		Teste testeDepois = repository.find(1l);
		Assert.assertFalse(
				"Texto antes tem que ser igual ao texto de depois pois não foi alterado o teste",
				testeAntes.getTitulo().equals(testeDepois.getTextoIndroducao()));
	}

	/**
	 * Test of passo2_4args method, of class TesteController. Usuario é o dono
	 * do teste buscado, mas passa titulo introdução vazio
	 */
	@Test
	public void testPasso2_4argsUsuarioPassaCampoTituloIntroducaoVazio() {
		System.out.println("passo1");
		Long id = 1l;
		String titulo = "Testo";
		String tituloPublico = " titulo titulo";
		String textoIndroducao = "";
		Teste testeAntes = repository.find(1l);

		try {
			instance.passo2(id, titulo, tituloPublico, textoIndroducao);
		} catch (ValidationException validationException) {
			Assert.assertEquals(
					"Não pode deixar salvar pois usuario passou campo titulo vazio",
					"campo.teste.textoIndroducao.obrigatorio",
					validationException.getErrors().get(0).getCategory());
		}
		Teste testeDepois = repository.find(1l);
		Assert.assertFalse(
				"Texto antes tem que ser igual ao texto de depois pois não foi alterado o teste",
				testeAntes.getTitulo().equals(testeDepois.getTextoIndroducao()));

	}

	/**
	 * Test of passo2_4args method, of class TesteController. Usuario é o dono
	 * do teste buscado, mas passa titulo publico vazio
	 */
	@Test
	public void testPasso2_4argsUsuarioPassaCampoTituloPublicoVazio() {
		System.out.println("passo1");
		Long id = 1l;
		String titulo = "Preenchido";
		String tituloPublico = "";
		String textoIndroducao = "texto introdução";
		Teste testeAntes = repository.find(1l);
		try {
			instance.passo2(id, titulo, tituloPublico, textoIndroducao);
		} catch (ValidationException validationException) {
			Assert.assertEquals(
					"Não pode deixar salvar pois usuario passou campo titulo publico vazio",
					"campo.teste.publico.obrigatorio", validationException
							.getErrors().get(0).getCategory());
		}
		Teste testeDepois = repository.find(1l);
		Assert.assertFalse(
				"Texto antes tem que ser igual ao texto de depois pois não foi alterado o teste",
				testeAntes.getTitulo().equals(testeDepois.getTextoIndroducao()));
	}

	/**
	 * Test of passo2_4args method, of class TesteController. Usuario é o dono
	 * do teste buscado, mas passa todos os campos vazio
	 */
	@Test
	public void testPasso2_4argsUsuarioPassaTodosCamposVazio() {
		System.out.println("passo1");
		Long id = 1l;
		String titulo = "";
		String tituloPublico = "";
		String textoIndroducao = "";
		Teste testeAntes = repository.find(1l);
		try {
			instance.passo2(id, titulo, tituloPublico, textoIndroducao);
		} catch (ValidationException validationException) {
			Assert.assertEquals(
					"Não pode deixar salvar pois usuario passou campo titulo publico vazio",
					"campo.teste.publico.obrigatorio", validationException
							.getErrors().get(1).getCategory());
			Assert.assertEquals(
					"Não pode deixar salvar pois usuario passou campo titulo vazio",
					"campo.teste.textoIndroducao.obrigatorio",
					validationException.getErrors().get(2).getCategory());
			Assert.assertEquals(
					"Não pode deixar salvar pois usuario passou campo titulo vazio",
					"campo.titulo.obrigatorio", validationException.getErrors()
							.get(0).getCategory());
		}
		Teste testeDepois = repository.find(1l);

		Assert.assertTrue(
				"Texto antes tem que ser igual ao texto de depois pois não foi alterado o teste",
				testeAntes.getTitulo().equals(testeDepois.getTitulo()));
	}

	/**
	 * Test of passo2 method, of class TesteController. Usuario passou id do
	 * teste não existente
	 */
	@Test
	public void testPasso2UsuarioIDtesteNaoExiste() {
		System.out.println("Passo1UsuarioIDtesteNaoExiste");
		Long id = 5500l;
		try {
			instance.passo1(id);
		} catch (ValidationException validationException) {
			Assert.assertEquals(
					"Não pode deixar salvar pois usuario nao é dono do Teste",
					"campo.form.alterado",
					validationException.getErrors().get(0).getCategory());
		}

	}

	/**
	 * Test of passo2 method, of class TesteController. Usuario é o dono do
	 * teste buscado
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testPasso2_Long() {
		System.out.println("passo1");
		Long id = 2l;
		instance.passo2(id);
		Assert.assertEquals(
				"Usuario é o criador do teste entao deveria retorna o mesmo valor do id passado",
				id, instance.testeView.getTeste().getId());
		List<Tarefa> tarefas = (List<Tarefa>) result.included().get("tarefas");
		List<Pergunta> perguntas = (List<Pergunta>) result.included().get(
				"perguntas");
		Assert.assertEquals("O teste so é para possuir 3 tarefas", 3,
				tarefas.size());
		Assert.assertEquals("O teste  é para possuir 8 pergunta", 8,
				perguntas.size());
	}

	/**
	 * Test of passo2 method, of class TesteController. Usuario não é o dono do
	 * teste buscado
	 */
	@Test
	public void testPasso2UsuarioNaoEDonoDoTeste() {
		System.out.println("Passo1UsuarioNaoEDonoDoTeste");
		Long id = 3l;
		try {
			instance.passo1(id);
		} catch (ValidationException validationException) {
			Assert.assertEquals(
					"Não pode deixar salvar pois usuario nao é dono do Teste",
					"campo.form.alterado",
					validationException.getErrors().get(0).getCategory());
		}

	}

	/**
	 * Test of passo2 method, of class TesteController. Usuario não passa o
	 * idTeste
	 */
	@Test
	public void testPasso2UsuarioNaoPassaIDTeste() {
		System.out.println("Passo1UsuarioNaoPassaIDTeste");
		Long id = null;
		try {
			instance.passo1(id);
		} catch (ValidationException validationException) {
			Assert.assertEquals(
					"Não pode deixar salvar pois usuario nao é dono do Teste",
					"campo.form.alterado",
					validationException.getErrors().get(0).getCategory());
		}

	}

	/**
	 * Test of passo1 method, of class TesteController. Usuario é o dono do
	 * teste buscado, mas ele esta liberado
	 */
	@Test
	public void testPasso2UsuarioEDonoDoTesteLiberado() {
		System.out.println("passo1");
		Long id = 4l;
		try {
			instance.passo1(id);
		} catch (ValidationException validationException) {
			Assert.assertEquals(
					"Não pode deixar salvar pois usuario nao é dono do Teste",
					"campo.form.alterado",
					validationException.getErrors().get(0).getCategory());
		}

	}

	/**
	 * Test of liberarTeste method, of class TesteController. Não possui nenhum
	 * usuario Convidado para o teste
	 */
	@Test
	public void testLiberarTesteSemUsuariosConvidados() {
		System.out.println("liberarTeste");
		Long idTeste = 2l;
		instance.liberarTeste(idTeste);
		Assert.assertTrue("Teste deveria ser liberado!",
				repository.find(idTeste).isLiberado());

	}

	/**
	 * Test of liberarTeste method, of class TesteController. Não possui tarefas
	 * cadastradas para o teste
	 */
	@Test
	public void testLiberarTesteSemTarefas() {
		System.out.println("liberarTeste");
		Long idTeste = 7l;
		Message message = null;
		try {
			instance.liberarTeste(idTeste);

		} catch (ValidationException validationException) {
			message = validationException.getErrors().get(0);

		}
		Assert.assertEquals("Nenhuma tarefa foi cadastrada",
				"sem.tarefa.cadastrada", message.getCategory());

	}

	/**
	 * Test of liberarTeste method, of class TesteController. Não possui Fluxo
	 * Ideal cadastrado para as tarefas
	 */
	@Test
	public void testLiberarTesteSemFluxoIdealGravado() {
		System.out.println("liberarTeste");
		Long idTeste = 8l;
		instance.liberarTeste(idTeste);
		Assert.assertTrue(
				"Teste deveria esta liberado, pois nao precisa ter fluxo ideal gravado",
				repository.find(idTeste).isLiberado());

	}

	/**
	 * Test of liberarTeste method, of class TesteController. possui Fluxo Ideal
	 * cadastrado para as tarefas
	 */
	@Test
	public void testLiberarTesteComFluxoIdealGravado() {
		System.out.println("liberarTeste");
		Long idTeste = 9l;
		boolean liberado = repository.find(idTeste).isLiberado();
		Assert.assertFalse("Teste deveria estar nao liberado", liberado);
		instance.liberarTeste(idTeste);

		Assert.assertTrue("Teste deveria estar nao liberado",
				repository.find(idTeste).isLiberado());

	}

	/**
	 * Test of convidarUsuario method, of class TesteController.
	 * 
	 */
	@Test
	public void testConvidarUsuario() {
		System.out.println("convidarUsuario");
		ConvidadoRepositoryImpl convidadoRepositoryImpl = new ConvidadoRepositoryImpl(
				entityManager);
		List<Long> idUsuarios = new ArrayList<Long>();
		idUsuarios.add(4l);
		idUsuarios.add(5l);
		Long idTeste = 2l;
		int qAntes = convidadoRepositoryImpl.findAll().size();
		instance.convidarUsuario(idUsuarios, idTeste,true);
		int qDepois = convidadoRepositoryImpl.findAll().size();
		Assert.assertEquals(qAntes + 2, qDepois);
	}

	/**
	 * Test of convidarUsuario já convidados.
	 * 
	 */
	@Test
	public void testConvidarUsuarioJaConvidados() {
		System.out.println("convidarUsuario");
		ConvidadoRepositoryImpl convidadoRepositoryImpl = new ConvidadoRepositoryImpl(
				entityManager);
		List<Long> idUsuarios = new ArrayList<Long>();
		idUsuarios.add(2l);
		idUsuarios.add(4l);
		idUsuarios.add(5l);
		idUsuarios.add(6l);
		Long idTeste = 8l;
		int qAntes = convidadoRepositoryImpl.findAll().size();
		Message message = null;
		try {
			instance.convidarUsuario(idUsuarios, idTeste, false);
		} catch (ValidationException validationException) {
			message = validationException.getErrors().get(0);

		}
		Assert.assertEquals(
				"Não pode deixar salvar pois Esta reconvidando usuario",
				"campo.form.alterado", message.getCategory());
		int qDepois = convidadoRepositoryImpl.findAll().size();
		Assert.assertEquals(qAntes, qDepois);
	}

	/**
	 * Test of convidarUsuario method, of class TesteController.
	 */
	@Test
	public void testConvidarUsuarioNaoEDonoTeste() {
		System.out.println("ConvidarUsuarioNaoEDonoTeste");
		List<Long> idUsuarios = new ArrayList<Long>();
		idUsuarios.add(4l);
		idUsuarios.add(5l);
		idUsuarios.add(6l);
		Long idTeste = 3l;
		Message message = null;
		try {

			instance.convidarUsuario(idUsuarios, idTeste, false);
		} catch (ValidationException validationException) {
			message = validationException.getErrors().get(0);

		}
		Assert.assertEquals(
				"Não pode deixar salvar pois usuario nao é dono do Teste",
				"campo.form.alterado", message.getCategory());
	}

	/**
	 * Test of desconvidarUsuario method, of class TesteController.
	 */
	@Test
	public void testDesconvidarUsuario() {
		System.out.println("desconvidarUsuario");
		ConvidadoRepositoryImpl convidadoRepositoryImpl = new ConvidadoRepositoryImpl(
				entityManager);
		int qAntes = convidadoRepositoryImpl.findAll().size();
		List<Long> idUsuarios = new ArrayList<Long>();
		idUsuarios.add(2l);
		idUsuarios.add(5l);
		Long idTeste = 7l;
		instance.desconvidarUsuario(idUsuarios, idTeste);
		int qDepois = convidadoRepositoryImpl.findAll().size();
		Assert.assertEquals("Era pra possuir dois testes a menos", qAntes,
				qDepois + 2);
	}

	/**
	 * Test of desconvidarUsuario method, of class TesteController. Desconvidar
	 * usuarios que não estao convidados
	 */
	@Test
	public void testDesconvidarUsuarioQueNaoEstaoConvidados() {
		System.out.println("desconvidarUsuario");
		ConvidadoRepositoryImpl convidadoRepositoryImpl = new ConvidadoRepositoryImpl(
				entityManager);
		int qAntes = convidadoRepositoryImpl.findAll().size();
		List<Long> idUsuarios = new ArrayList<Long>();
		idUsuarios.add(4l);
		idUsuarios.add(3l);
		Long idTeste = 7l;
		instance.desconvidarUsuario(idUsuarios, idTeste);
		int qDepois = convidadoRepositoryImpl.findAll().size();
		Assert.assertEquals("é pra possuir o mesmo numero de teste", qAntes,
				qDepois);
	}

	/**
	 * Test of desconvidarUsuario method, of class TesteController. Desconvidar
	 * usuarios que não estao convidados com usuarios convidados
	 */
	@Test
	public void testDesconvidarUsuarioComUsuarioNaoConvidado() {
		System.out.println("desconvidarUsuario");
		ConvidadoRepositoryImpl convidadoRepositoryImpl = new ConvidadoRepositoryImpl(
				entityManager);
		int qAntes = convidadoRepositoryImpl.findAll().size();
		List<Long> idUsuarios = new ArrayList<Long>();
		idUsuarios.add(2l);
		idUsuarios.add(3l);
		idUsuarios.add(4l);
		Long idTeste = 7l;
		instance.desconvidarUsuario(idUsuarios, idTeste);
		int qDepois = convidadoRepositoryImpl.findAll().size();
		Assert.assertEquals("é pra possuir o mesmo numero de teste", qAntes,
				qDepois + 1);
	}

	/**
	 * Test of desconvidarUsuario method, of class TesteController. Desconvidar
	 * usuarios de teste Liberado
	 */
	@Test
	public void testDesconvidarUsuarioDeTesteLiberado() {
		System.out.println("desconvidarUsuario");
		ConvidadoRepositoryImpl convidadoRepositoryImpl = new ConvidadoRepositoryImpl(
				entityManager);
		int qAntes = convidadoRepositoryImpl.findAll().size();
		List<Long> idUsuarios = new ArrayList<Long>();
		idUsuarios.add(2l);
		Long idTeste = 11l;
		instance.desconvidarUsuario(idUsuarios, idTeste);
		int qDepois = convidadoRepositoryImpl.findAll().size();
		Assert.assertEquals("é pra possuir o mesmo numero de teste", qAntes,
				qDepois + 1);
	}

	/**
	 * Test of desconvidarUsuario method, of class TesteController. Desconvidar
	 * usuarios com lista de usuarios vazia
	 */
	@Test
	public void testDesconvidarUsuarioComListaDeUsuariosVazia() {
		System.out.println("desconvidarUsuario");
		ConvidadoRepositoryImpl convidadoRepositoryImpl = new ConvidadoRepositoryImpl(
				entityManager);
		int qAntes = convidadoRepositoryImpl.findAll().size();
		List<Long> idUsuarios = new ArrayList<Long>();

		Long idTeste = 6l;
		instance.desconvidarUsuario(idUsuarios, idTeste);
		int qDepois = convidadoRepositoryImpl.findAll().size();
		Assert.assertEquals("é pra possuir o mesmo numero de teste", qAntes,
				qDepois);
	}

	/**
	 * Test of remove method, of class TesteController.
	 * 
	 */
	@Test
	public void testRemove() {
		System.out.println("passo1");
		Long idTeste = 2l;
		instance.remove(idTeste);
		Assert.assertEquals(
				"Usuario é o criador do teste entao deveria retorna o mesmo valor do id passado",
				idTeste, instance.testeView.getTeste().getId());

	}

	/**
	 * Test of remove method, of class TesteController.Usuario não é o dono do
	 * teste buscado
	 */
	@Test
	public void testRemoveUsuarioNaoEDonoDoTeste() {
		System.out.println("Passo1UsuarioNaoEDonoDoTeste");
		Long id = 3l;
		try {
			instance.passo1(id);
		} catch (ValidationException validationException) {
			Assert.assertEquals(
					"Não pode deixar salvar pois usuario nao é dono do Teste",
					"campo.form.alterado",
					validationException.getErrors().get(0).getCategory());
		}

	}

	/**
	 * Test of remove method, of class TesteController. Usuario não passa o
	 * idTeste
	 */
	@Test
	public void testRemoveUsuarioNaoPassaIDTeste() {
		System.out.println("Passo1UsuarioNaoPassaIDTeste");
		Long id = null;
		try {
			instance.remove(id);
		} catch (ValidationException validationException) {
			Assert.assertEquals(
					"Não pode deixar salvar pois usuario nao é dono do Teste",
					"campo.form.alterado",
					validationException.getErrors().get(0).getCategory());
		}

	}

	/**
	 * Test of remove method, of class TesteController. Usuario passou id do
	 * teste não existente
	 */
	@Test
	public void testRemoveUsuarioIDtesteNaoExiste() {
		System.out.println("Passo1UsuarioIDtesteNaoExiste");
		Long id = 5500l;
		try {
			instance.remove(id);
		} catch (ValidationException validationException) {
			Assert.assertEquals(
					"Não pode deixar salvar pois usuario nao é dono do Teste",
					"campo.form.alterado",
					validationException.getErrors().get(0).getCategory());
		}

	}

	/**
	 * Test of removed method, of class TesteController.
	 */
	@Test
	public void testRemoved() {
		System.out.println("removed");
		String senha = "senha1";
		Long idTeste = 1l;
		int qAntes = repository.findAll().size();
		instance.removed(senha, idTeste);
		int qDepois = repository.findAll().size();
		Assert.assertEquals("Deveria possui um teste a menos", qAntes - 1,
				qDepois);
	}

	/**
	 * Test of testRemoved method, of class TesteController. Usuario não é o
	 * dono do teste buscado
	 */
	@Test
	public void testRemovedUsuarioNaoEDonoDoTeste() {
		System.out.println("testRemoved1UsuarioNaoEDonoDoTeste");
		Long id = 3l;
		String senha = "senha1";
		try {
			instance.removed(senha, id);
		} catch (ValidationException validationException) {
			Assert.assertEquals(
					"Não pode deixar salvar pois usuario nao é dono do Teste",
					"campo.form.alterado",
					validationException.getErrors().get(0).getCategory());
		}

	}

	/**
	 * Test of Removed method, of class TesteController. Usuario não passa o
	 * idTeste
	 */
	@Test
	public void testRemovedUsuarioNaoPassaIDTeste() {
		System.out.println("testRemovedUsuarioNaoPassaIDTeste");
		Long id = null;

		String senha = "senha1";
		try {
			instance.removed(senha, id);
		} catch (ValidationException validationException) {
			Assert.assertEquals(
					"Não pode deixar salvar pois usuario nao é dono do Teste",
					"campo.form.alterado",
					validationException.getErrors().get(0).getCategory());
		}

	}

	/**
	 * Test of Removed method, of class TesteController. Usuario é o dono do
	 * teste buscado, mas ele esta liberado
	 */
	@Test
	public void testRemovedUsuarioEDonoDoTesteLiberado() {
		System.out.println("passo1");
		Long id = 4l;
		String senha = "senha1";
		int qAntes = repository.findAll().size();
		instance.removed(senha, id);
		int qDepois = repository.findAll().size();
		Assert.assertEquals("Deveria possui um teste a menos", qAntes - 1,
				qDepois);

	}

	/**
	 * Test of Removed method, of class TesteController. Usuario é o dono do
	 * teste buscado, mas ele esta liberado
	 */
	@Test
	public void testRemovedUsuarioEDonoDoTesteLiberadoSenhaErrada() {
		System.out.println("passo1");
		Long id = 4l;
		String senha = "senha";
		int qAntes = repository.findAll().size();
		try {
			instance.removed(senha, id);
		} catch (ValidationException validationException) {
			Assert.assertEquals(
					"Não pode deixar deletar pois senha esta incorreta",
					"senha.incorreta", validationException.getErrors().get(0)
							.getCategory());
		}
		int qDepois = repository.findAll().size();
		Assert.assertEquals("Deveria ter o mesmo numero de teste", qAntes,
				qDepois);
	}

	/**
	 * Test of Removed method, of class TesteController. Usuario é o dono do
	 * teste buscado, mas ele esta liberado
	 */
	@Test
	public void testRemovedUsuarioEDonoDoTesteLiberadoSenhaVazia() {
		System.out.println("passo1");
		Long id = 4l;
		String senha = "";
		int qAntes = repository.findAll().size();
		try {
			instance.removed(senha, id);
		} catch (ValidationException validationException) {
			Assert.assertEquals(
					"Não pode deixar deletar pois senha esta vazia",
					"campo.senha.obrigatorio", validationException.getErrors()
							.get(0).getCategory());
		}
		int qDepois = repository.findAll().size();
		Assert.assertEquals("Deveria ter o mesmo numero de teste", qAntes,
				qDepois);
	}

	/**
	 * Test of realizarTeste method, of class TesteController.
	 */
	@Test
	public void testRealizarTeste() {
		System.out.println("realizarTeste");
		instance.realizarTeste();
	}

	/**
	 * Test of meusProjetos method, of class TesteController.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testMeusProjetos() {
		System.out.println("meusProjetos");
		instance.meusProjetos(1);
		List<Teste> object = (List<Teste>) result.included().get(
				"testesParticipados");
		Assert.assertTrue("", object.isEmpty());
		Long numeroTeste = (Long) result.included().get(
				"testesParticipadosCount");
		Long numeroTestesParticipados = 1l;
		Assert.assertEquals("Este usuario nunca participou de um teste",
				numeroTestesParticipados, numeroTeste);
	}

	/**
	 * Test of passo1 method, of class TesteController. Usuario passou id do
	 * teste não existente
	 */
	@Test
	public void testPasso3UsuarioIDtesteNaoExiste() {
		System.out.println("Passo1UsuarioIDtesteNaoExiste");
		Long id = 5500l;
		try {
			instance.passo3(id);
		} catch (ValidationException validationException) {
			Assert.assertEquals(
					"Não pode deixar salvar pois usuario nao é dono do Teste",
					"campo.form.alterado",
					validationException.getErrors().get(0).getCategory());
		}

	}

	/**
	 * Test of passo1 method, of class TesteController. Usuario é o dono do
	 * teste buscado
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testPasso3() {
		System.out.println("passo1");
		Long id = 2l;
		instance.passo3(id);
		List<Convidado> convidados = (List<Convidado>) result.included().get(
				"usuariosEscolhidos");
		Assert.assertTrue("Não possui usuarios Convidados",
				convidados.isEmpty());

	}

	/**
	 * Test of passo1 method, of class TesteController. Usuario não é o dono do
	 * teste buscado
	 */
	@Test
	public void testPasso3UsuarioNaoEDonoDoTeste() {
		System.out.println("Passo1UsuarioNaoEDonoDoTeste");
		Long id = 3l;
		try {
			instance.passo3(id);
		} catch (ValidationException validationException) {
			Assert.assertEquals(
					"Não pode deixar salvar pois usuario nao é dono do Teste",
					"campo.form.alterado",
					validationException.getErrors().get(0).getCategory());
		}

	}

	/**
	 * Test of passo1 method, of class TesteController. Usuario não passa o
	 * idTeste
	 */
	@Test
	public void testPasso3UsuarioNaoPassaIDTeste() {
		System.out.println("Passo1UsuarioNaoPassaIDTeste");
		Long id = null;
		try {
			instance.passo3(id);
		} catch (ValidationException validationException) {
			Assert.assertEquals(
					"Não pode deixar salvar pois usuario nao é dono do Teste",
					"campo.form.alterado",
					validationException.getErrors().get(0).getCategory());
		}

	}

	/**
	 * Test of passo3method, of class TesteController. Usuario é o dono do teste
	 * buscado, mas ele esta liberado
	 */
	@Test
	public void testPasso3UsuarioEDonoDoTesteLiberado() {
		System.out.println("passo1");
		Long id = 4l;
		try {
			instance.passo3(id);
		} catch (ValidationException validationException) {
			Assert.assertEquals(
					"Não pode deixar salvar pois usuario nao é dono do Teste",
					"campo.form.alterado",
					validationException.getErrors().get(0).getCategory());
		}

	}

	/**
	 * Test of passo1 method, of class TesteController. Usuario passou id do
	 * teste não existente
	 */
	@Test
	public void testConvidarUsuarioUsuarioIDtesteNaoExiste() {
		System.out.println("Passo3UsuarioIDtesteNaoExiste");
		Long id = 5500l;
		try {
			instance.convidar(id);
		} catch (ValidationException validationException) {
			Assert.assertEquals(
					"Não pode deixar salvar pois usuario nao é dono do Teste",
					"campo.form.alterado",
					validationException.getErrors().get(0).getCategory());
		}

	}

	/**
	 * Test of ConvidarUsuario method, of class TesteController. Usuario é o
	 * dono do teste buscado e o teste esta liberado
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testConvidarUsuarioTesteLiberado() {
		System.out.println("passo1");
		Long id = 4l;
		instance.convidar(id);
		List<Convidado> convidados = (List<Convidado>) result.included().get(
				"usuariosEscolhidos");
		List<Convidado> usuariosLivres = (List<Convidado>) result.included()
				.get("usuariosLivres");
		Assert.assertNull(
				"Tem que ser Igual a nulo, pois este teste jah esta liberado",
				convidados);
		Assert.assertFalse("Era pra possuir mais usuarios livres",
				usuariosLivres.isEmpty());

	}

	/**
	 * Test of ConvidarUsuario method, of class TesteController. Usuario não é o
	 * dono do teste buscado
	 */
	@Test
	public void testConvidarUsuarioUsuarioNaoEDonoDoTeste() {
		System.out.println("Passo1UsuarioNaoEDonoDoTeste");
		Long id = 3l;
		try {
			instance.convidar(id);
		} catch (ValidationException validationException) {
			Assert.assertEquals(
					"Não pode deixar salvar pois usuario nao é dono do Teste",
					"campo.form.alterado",
					validationException.getErrors().get(0).getCategory());
		}

	}

	/**
	 * Test of passo1 method, of class TesteController. Usuario não passa o
	 * idTeste
	 */
	@Test
	public void testConvidarUsuarioUsuarioNaoPassaIDTeste() {
		System.out.println("Passo1UsuarioNaoPassaIDTeste");
		Long id = null;
		try {
			instance.convidar(id);
		} catch (ValidationException validationException) {
			Assert.assertEquals(
					"Não pode deixar salvar pois usuario nao é dono do Teste",
					"campo.form.alterado",
					validationException.getErrors().get(0).getCategory());
		}
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testlistarTestesLiberados() {
		instance.listarTestesLiberados(1);
		Map<String, Object> included = result.included();
		Assert.assertEquals(5l, included.get("testesLiberadosCount"));
		List<Teste> testes = (List<Teste>) included.get("testesLiberados");
		List<Teste> all = repository.findAll();
		Long usuarioId = instance.usuarioLogado.getUsuario().getId();
		Integer numeroTestesLiberados = 0;
		for (Teste teste : all) {
			if (teste.isLiberado()
					&& teste.getUsuarioCriador().getId().equals(usuarioId)) {
				numeroTestesLiberados++;
			}
		}
		Integer size = testes.size();
		System.out.println(size);
		Assert.assertEquals(numeroTestesLiberados, size);
	}
}
