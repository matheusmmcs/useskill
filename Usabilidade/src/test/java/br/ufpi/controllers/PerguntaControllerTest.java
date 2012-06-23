/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpi.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.validator.ValidationException;
import br.ufpi.controllers.procedure.PerguntaTestProcedure;
import br.ufpi.models.Pergunta;
import br.ufpi.repositories.AbstractDaoTest;
import br.ufpi.repositories.PerguntaRepository;

/**
 * 
 * @author Cleiton
 */
public class PerguntaControllerTest extends AbstractDaoTest {
	private PerguntaRepository repository;
	private PerguntaController instance;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		repository = PerguntaTestProcedure
				.newInstancePerguntaRepository(entityManager);
		instance = PerguntaTestProcedure.newInstanceTarefaController(
				entityManager, result);
	}

	/**
	 * Test of EditarPergunt method, of class TesteController. Usuario é o dono
	 * do teste buscado
	 */
	@Test
	public void testEditarPergunta() {
		System.out.println("testCriarPergunta");
		Long testeId = 2l;
		Pergunta pergunta = new Pergunta();
		pergunta.setId(2l);
		instance.editarPergunta(testeId, pergunta);
		Assert.assertEquals(
				"Usuario é o criador do teste entao deveria retorna o mesmo valor do id passado",
				testeId, instance.testeView.getTeste().getId());

	}

	/**
	 * Test of CriarPergunta method, of class TesteController. Usuario não é o
	 * dono do teste buscado
	 */
	@Test
	public void tesEditarPerguntaUsuarioNaoEDonoDoTeste() {
		System.out.println("CriarPerguntaUsuarioNaoEDonoDoTeste");
		Long testeId = 3l;
		Pergunta pergunta = new Pergunta();
		pergunta.setId(2l);
		try {
			instance.editarPergunta(testeId, pergunta);
		} catch (ValidationException validationException) {
			Assert.assertEquals(
					"Não pode deixar salvar pois usuario nao é dono do Teste",
					"campo.form.alterado",
					validationException.getErrors().get(0).getCategory());
		}

	}

	/**
	 * Test of CriarPergunta method, of class TesteController. Usuario não passa
	 * o idTeste
	 */
	@Test
	public void testEditarPerguntaUsuarioNaoPassaIDTeste() {
		System.out.println("CriarPerguntaUsuarioNaoPassaIDTeste");
		Long id = null;
		Pergunta pergunta = new Pergunta();
		pergunta.setId(2l);
		try {
			instance.editarPergunta(id, pergunta);
		} catch (ValidationException validationException) {
			Assert.assertEquals(
					"Não pode deixar salvar pois usuario nao é dono do Teste",
					"campo.form.alterado",
					validationException.getErrors().get(0).getCategory());
		}

	}

	/**
	 * Test of CriarPergunta method, of class TesteController. Usuario é o dono
	 * do teste buscado, mas ele esta liberado
	 */
	@Test
	public void testEditarPerguntaUsuarioEDonoDoTesteLiberado() {
		System.out.println("passo1");
		Long id = 4l;
		Pergunta pergunta = new Pergunta();
		pergunta.setId(2l);
		try {
			instance.editarPergunta(id, pergunta);
		} catch (ValidationException validationException) {
			Assert.assertEquals(
					"Não pode deixar salvar pois usuario nao é dono do Teste",
					"campo.form.alterado",
					validationException.getErrors().get(0).getCategory());
		}

	}

	/**
	 * Test of EditarPergunta method, of class TesteController. Usuario é o dono
	 * do teste buscado,mas o id da tarefa esta errado
	 */
	@Test
	public void testEditarPerguntaUsuarioEDonoDoTesteMasIDDAPerguntaEstaErrado() {
		System.out.println("passo1");
		Long id = 2l;
		Pergunta pergunta = new Pergunta();
		pergunta.setId(20l);
		Pergunta perguntaRetorno = instance.editarPergunta(id, pergunta);
		Assert.assertNull(
				"Era para ter retornado null, pois o teste que o cara quer nao pertence a ele",
				perguntaRetorno);

	}

	/**
	 * Test of CriarPergunt method, of class TesteController. Usuario é o dono
	 * do teste buscado
	 */
	@Test
	public void testCriarPergunta() {
		System.out.println("testCriarPergunta");
		Long id = 2l;
		instance.criarPergunta(id);
		Assert.assertEquals(
				"Usuario é o criador do teste entao deveria retorna o mesmo valor do id passado",
				id, instance.testeView.getTeste().getId());

	}

	/**
	 * Test of CriarPergunta method, of class TesteController. Usuario não é o
	 * dono do teste buscado
	 */
	@Test
	public void testCriarPerguntaUsuarioNaoEDonoDoTeste() {
		System.out.println("CriarPerguntaUsuarioNaoEDonoDoTeste");
		Long id = 3l;
		try {
			instance.criarPergunta(id);
		} catch (ValidationException validationException) {
			Assert.assertEquals(
					"Não pode deixar salvar pois usuario nao é dono do Teste",
					"campo.form.alterado",
					validationException.getErrors().get(0).getCategory());
		}

	}

	/**
	 * Test of CriarPergunta method, of class TesteController. Usuario não passa
	 * o idTeste
	 */
	@Test
	public void testCriarPerguntaUsuarioNaoPassaIDTeste() {
		System.out.println("CriarPerguntaUsuarioNaoPassaIDTeste");
		Long id = null;
		try {
			instance.criarPergunta(id);
		} catch (ValidationException validationException) {
			Assert.assertEquals(
					"Não pode deixar salvar pois usuario nao é dono do Teste",
					"campo.form.alterado",
					validationException.getErrors().get(0).getCategory());
		}

	}

	/**
	 * Test of CriarPergunta method, of class TesteController. Usuario é o dono
	 * do teste buscado, mas ele esta liberado
	 */
	@Test
	public void testCriarPerguntaUsuarioEDonoDoTesteLiberado() {
		System.out.println("passo1");
		Long id = 4l;
		try {
			instance.criarPergunta(id);
		} catch (ValidationException validationException) {
			Assert.assertEquals(
					"Não pode deixar salvar pois usuario nao é dono do Teste",
					"campo.form.alterado",
					validationException.getErrors().get(0).getCategory());
		}

	}

	/**
	 * Test of salvarPergunta method, of class PerguntaController.
	 */
	@Test
	public void testSalvarPergunta() {
		System.out.println("salvarPergunta");
		Long testeId = null;
		Pergunta pergunta = null;
		PerguntaController instance = null;
		instance.salvarPergunta(testeId, pergunta);
		// TODO review the generated test code and remove the default call to
		// fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of atualizarPergunta method, of class PerguntaController.
	 */
	@Test
	public void testAtualizarPergunta() {
		System.out.println("atualizarPergunta");
		Long testeId = null;
		Pergunta pergunta = null;
		PerguntaController instance = null;
		instance.atualizarPergunta(testeId, pergunta);
		// TODO review the generated test code and remove the default call to
		// fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of deletarPergunta method, of class PerguntaController.
	 */
	@Test
	public void testDeletarPergunta() {
		System.out.println("deletarPergunta");
		Long testeId = null;
		Long perguntaId = null;
		PerguntaController instance = null;
		instance.deletarPergunta(testeId, perguntaId);
		// TODO review the generated test code and remove the default call to
		// fail.
		fail("The test case is a prototype.");
	}
}
