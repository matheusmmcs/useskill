/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpi.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.validator.Message;
import br.com.caelum.vraptor.validator.ValidationException;
import br.ufpi.controllers.procedure.TarefaTestProcedure;
import br.ufpi.models.Fluxo;
import br.ufpi.models.Tarefa;
import br.ufpi.repositories.AbstractDaoTest;
import br.ufpi.repositories.FluxoRepository;
import br.ufpi.repositories.TarefaRepository;
import br.ufpi.repositories.Implement.FluxoRepositoryImpl;
import br.ufpi.util.TarefaDetalhe;

/**
 * Caso de Uso criar Tarefa</br> <li>Caso em que passa tarefa null</li> <li>Caso
 * em que passa tarefa preenchida</li> <li>Caso em que passa tarefa e id de
 * teste Não pertencente ao usuario</li> <li>Caso em que passa tarefa e id de
 * teste já liberado</li>
 * Caso para Salvar tarefa</br>
 * <li>Caso de Sucesso</li>
 * <li>Caso id de teste não pertence ao usuario</li>
 * <li>Caso id de teste null</li>
 * <li>Caso id de teste Liberado</li>
 * <li>Caso de Tarefa sem os campos preenchidos</li>
 * Faltando
 * Caso para Editar tarefa</br>
 * <li>Caso de Sucesso</li>
 * <li>Caso id de teste não pertence ao usuario</li>
 * <li>Caso id de teste null</li>
 * <li>Caso id de teste Liberado</li>
 * <li>Caso de Tarefa com id diferente do Teste passado</li>
 * Caso para Remover tarefa</br>
 * <li>Caso de Sucesso</li>
 * <li>Caso id de teste não pertence ao usuario</li>
 * <li>Caso id de teste null</li>
 * <li>Caso id de teste Liberado</li>
 * <li>Caso de Tarefa com id diferente do Teste passado</li>
 * <li>Caso de Teste com Fluxo ideal gravado</li>
 * 
 * @author Cleiton
 */
public class TarefaControllerTest extends AbstractDaoTest {
	private static Long testePertenceUsuarioNaoLiberado = 10l;
	private static Long testeNaoPertenceUsuario = 5l;
	private static Long testeLiberado = 11l;
	private TarefaRepository repository;
	private TarefaController instance;
	private Long testeNaoPertenceUsuario2=14l;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		repository = TarefaTestProcedure
				.newInstanceTarefaRepository(entityManager);
		instance = TarefaTestProcedure.newInstanceTarefaController(
				entityManager, result);
	}

	/**
	 * Test of criarTarefa method, of class TarefaController.
	 */
	@Test
	public void testCriarTarefa() {
		System.out.println("criarTarefa");
		Tarefa tarefa = null;
		Tarefa result = instance.criarTarefa(testePertenceUsuarioNaoLiberado,
				tarefa);
		Assert.assertNotNull(result);
	}

	@Test
	public void testCriarTarefaPreenchida() {
		System.out.println("criarTarefa");
		Tarefa tarefa = new Tarefa();
		tarefa.setNome("tarefa 1");
		Tarefa result = instance.criarTarefa(testePertenceUsuarioNaoLiberado,
				tarefa);
		Assert.assertEquals(tarefa.getNome(), result.getNome());
	}

	/**
	 * Test of criarTarefa method, of class TarefaController. Caso em que passa
	 * tarefa e id de teste Não pertencente ao usuario
	 */
	@Test
	public void testCriarTarefaUsuarioNaoEDono() {
		System.out.println("criarTarefa");
		Tarefa tarefa = null;
		List<Message> errors = null;
		Tarefa result = null;
		try {
			result = instance.criarTarefa(testeNaoPertenceUsuario, tarefa);
		} catch (ValidationException validationException) {
			errors = validationException.getErrors();
		}
		Assert.assertEquals(
				"Não pode deixar salvar pois usuario nao é dono do Teste",
				"campo.form.alterado", errors.get(0).getCategory());

		Assert.assertNull(result);
	}

	/**
	 * Test of criarTarefa method, of class TarefaController. Caso em que passa
	 * tarefa e id de teste já liberado
	 */
	@Test
	public void testCriarTarefaDeTesteJaLiberado() {
		System.out.println("criarTarefa");
		Tarefa tarefa = null;
		List<Message> errors = null;
		Tarefa result = null;
		try {
			result = instance.criarTarefa(testeLiberado, tarefa);
		} catch (ValidationException validationException) {
			errors = validationException.getErrors();
		}
		Assert.assertEquals(
				"Não pode deixar salvar pois usuario nao é dono do Teste",
				"campo.form.alterado", errors.get(0).getCategory());

		Assert.assertNull(result);
	}

	/**
	 * Test of salvarTarefa method, of class TarefaController.
	 */
	@Test
	public void testSalvarTarefa() {
		System.out.println("salvarTarefa");
		int qAntes = repository.findAll().size();
		Tarefa tarefa = TarefaTestProcedure.newInstanceTarefa("urlInicial",
				"roteiro", "nome");
		instance.salvarTarefa(tarefa, testePertenceUsuarioNaoLiberado);
		int qDepois = repository.findAll().size();
		Assert.assertEquals(qAntes, qDepois - 1);
	}
	/**
	 * Test of salvarTarefa method, of class TarefaController.
	 Caso id de teste não pertence ao usuario
	 */
	@Test
	public void testSalvarTarefaIdTesteNaoPertenceUsuario() {
		System.out.println("salvarTarefa");
		int qAntes = repository.findAll().size();
		Tarefa tarefa = TarefaTestProcedure.newInstanceTarefa("urlInicial",
				"roteiro", "nome");
		List<Message> errors = null;
		try {
		instance.salvarTarefa(tarefa, testeNaoPertenceUsuario);
		} catch (ValidationException validationException) {
			errors = validationException.getErrors();
		}
		Assert.assertEquals(
				"Não pode deixar salvar pois usuario nao é dono do Teste",
				"campo.form.alterado", errors.get(0).getCategory());
		int qDepois = repository.findAll().size();
		Assert.assertEquals(qAntes, qDepois);
	}
	/**
	 * Test of salvarTarefa method, of class TarefaController.
	 Caso id de teste não pertence ao usuario
	 */
	@Test
	public void testSalvarTarefaIdTesteJaLiberado() {
		System.out.println("salvarTarefa");
		int qAntes = repository.findAll().size();
		Tarefa tarefa = TarefaTestProcedure.newInstanceTarefa("urlInicial",
				"roteiro", "nome");
		List<Message> errors = null;
		try {
			instance.salvarTarefa(tarefa, testeLiberado);
		} catch (ValidationException validationException) {
			errors = validationException.getErrors();
		}
		Assert.assertEquals(
				"Não pode deixar salvar pois usuario nao é dono do Teste",
				"campo.form.alterado", errors.get(0).getCategory());
		int qDepois = repository.findAll().size();
		Assert.assertEquals(qAntes, qDepois);
	}
	/**
	 * Test of salvarTarefa method, of class TarefaController.
	 Caso id de teste não pertence ao usuario
	 */
	@Test
	public void testSalvarTarefaCamposNaoPreenchido() {
		System.out.println("salvarTarefa");
		int qAntes = repository.findAll().size();
		Tarefa tarefa = TarefaTestProcedure.newInstanceTarefa("",
				"", "");
		List<Message> errors = null;
		try{
			instance.salvarTarefa(tarefa, testePertenceUsuarioNaoLiberado);
		} catch (ValidationException validationException) {
			errors = validationException.getErrors();
			System.out.println(errors);
		}
		Assert.assertEquals(
				"Pra ter gerado 3 erros",
				3, errors.size());
		int qDepois = repository.findAll().size();
		Assert.assertEquals(qAntes, qDepois);
	}
	/**
	 * Test of salvarTarefa method, of class TarefaController.
	Caso id de teste nul
	 */
	@Test
	public void testSalvarTarefaIdTesteNulo() {
		System.out.println("salvarTarefa");
		int qAntes = repository.findAll().size();
		Tarefa tarefa = TarefaTestProcedure.newInstanceTarefa("urlInicial",
				"roteiro", "nome");
		List<Message> errors = null;
		try {
			instance.salvarTarefa(tarefa, null);
		} catch (ValidationException validationException) {
			errors = validationException.getErrors();
		}
		Assert.assertEquals(
				"Não pode deixar salvar pois usuario nao é dono do Teste",
				"campo.form.alterado", errors.get(0).getCategory());
		int qDepois = repository.findAll().size();
		Assert.assertEquals(qAntes, qDepois);
	}

	/**
	 * Test of editarTarefa method, of class TarefaController.
	 */
	@Test
	public void testEditarTarefa() {
		System.out.println("editarTarefa");
		Long idTarefa=7l;
		new TarefaTestProcedure();
		Tarefa tarefa = TarefaTestProcedure.newInstanceTarefa(idTarefa,"urlInicial", "roteiero", "nome");
		boolean isErro = false;
		Tarefa result = instance.editarTarefa(testePertenceUsuarioNaoLiberado, tarefa, isErro);
		Assert.assertNotNull(result);
	}
	/**
	 * Test of editarTarefa method, of class TarefaController.
	 * Caso id de teste não pertence ao usuario
	 */
	@Test
	public void testEditarTarefaTesteNaoPertenceUsuario() {
		System.out.println("editarTarefa");
		Long idTarefa=1l;
		new TarefaTestProcedure();
		Tarefa tarefa = TarefaTestProcedure.newInstanceTarefa(idTarefa,"urlInicial", "roteiero", "nome");
		boolean isErro = false;
		Tarefa result = null;
		List<Message> errors = null;

		try {
			
			 result = instance.editarTarefa(testePertenceUsuarioNaoLiberado, tarefa, isErro);
		} catch (ValidationException validationException) {
			errors = validationException.getErrors();
		}
		Assert.assertEquals(
				"Não pode deixar salvar pois usuario nao é dono do Teste",
				"campo.form.alterado", errors.get(0).getCategory());

		Assert.assertNull(result);
	}
	/**
	 * Test of editarTarefa method, of class TarefaController.
Caso id de teste null
	 */
	@Test
	public void testEditarTarefaTesteIdNulo() {
		System.out.println("editarTarefa");
		Long idTarefa=7l;
		new TarefaTestProcedure();
		Tarefa tarefa = TarefaTestProcedure.newInstanceTarefa(idTarefa,"urlInicial", "roteiero", "nome");
		boolean isErro = false;
		Tarefa result = null;
		List<Message> errors = null;
		
		try {
			
			result = instance.editarTarefa(null, tarefa, isErro);
		} catch (ValidationException validationException) {
			errors = validationException.getErrors();
		}
		Assert.assertEquals(
				"Não pode deixar salvar pois usuario nao é dono do Teste",
				"campo.form.alterado", errors.get(0).getCategory());
		
		Assert.assertNull(result);
	}
	
	/**
	 * Test of editarTarefa method, of class TarefaController.
Caso id de teste null
	 */
	@Test
	public void testEditarTarefaTesteIdDiferenteDaTarefa() {
		System.out.println("editarTarefa");
		Long idTarefa=4l;
		new TarefaTestProcedure();
		Tarefa tarefa = TarefaTestProcedure.newInstanceTarefa(idTarefa,"urlInicial", "roteiero", "nome");
		boolean isErro = false;
		Tarefa result = null;
		List<Message> errors = null;
		
		try {
			
			result = instance.editarTarefa(testePertenceUsuarioNaoLiberado, tarefa, isErro);
		} catch (ValidationException validationException) {
			errors = validationException.getErrors();
		}
		Assert.assertEquals(
				"Não pode deixar salvar pois usuario nao é dono do Teste",
				"campo.form.alterado", errors.get(0).getCategory());
		
		Assert.assertNull(result);
	}
	
	/**
	 * Test of editarTarefa method, of class TarefaController.
Caso id de teste null
	 */
	@Test
	public void testEditarTarefaIdNulo() {
		System.out.println("editarTarefa");
		new TarefaTestProcedure();
		Tarefa tarefa = TarefaTestProcedure.newInstanceTarefa("urlInicial", "roteiero", "nome");
		boolean isErro = false;
		Tarefa result = null;
		List<Message> errors = null;
		
		try {
			
			result = instance.editarTarefa(testePertenceUsuarioNaoLiberado, tarefa, isErro);
		} catch (ValidationException validationException) {
			errors = validationException.getErrors();
		}
		Assert.assertEquals(
				"Não pode deixar salvar pois usuario nao é dono do Teste",
				"campo.form.alterado", errors.get(0).getCategory());
		
		Assert.assertNull(result);
	}
	

	/**
	 * Test of updateTarefa method, of class TarefaController.
	 */
	@Test
	public void testUpdateTarefa() {
		System.out.println("updateTarefa");
		Tarefa tarefa = null;
		Long idTeste = null;
		TarefaController instance = null;
		instance.updateTarefa(tarefa, idTeste);
		// TODO review the generated test code and remove the default call to
		// fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of removed method, of class TarefaController.
	 */
	@Test
	public void testRemoved() {
		System.out.println("removed");
		Long idTarefa = 7l;
		int qAntes = repository.findAll().size();
		instance.removed(idTarefa, testePertenceUsuarioNaoLiberado);
		int qDepois = repository.findAll().size();
		Assert.assertEquals(qAntes, qDepois+1);}
	
	/**
	 * Test of removed method, of class TarefaController.
	 */
	@Test
	public void testRemovedComFluxoIdealGravado() {
		System.out.println("removed");
		Long idTarefa = 25l;
		FluxoRepository fluxoRepository= new FluxoRepositoryImpl(entityManager);
		int qAntesFluxo = fluxoRepository.findAll().size();
		int qAntes = repository.findAll().size();
		instance.removed(idTarefa, 16l);
		int qDepois = repository.findAll().size();
		int qDepoisFluxo = fluxoRepository.findAll().size();
		Assert.assertEquals(qAntes, qDepois+1);
	Assert.assertEquals(qAntesFluxo, qDepoisFluxo+1);}
	
	/**
	 * Test of removed method, of class TarefaController.
	 * Caso id de teste não pertence ao usuario
	 */
	@Test
	public void testRemovedTesteNaopertenceAoUsuario() {
		System.out.println("removed");
		Long idTarefa = 19l;
		int qAntes = repository.findAll().size();
	List<Message> errors = null;
		
		try {
			instance.removed(idTarefa, testeNaoPertenceUsuario2);
			
		} catch (ValidationException validationException) {
			errors = validationException.getErrors();
		}
		Assert.assertEquals(
				"Não pode deixar salvar pois usuario nao é dono do Teste",
				"campo.form.alterado", errors.get(0).getCategory());
		
		int qDepois = repository.findAll().size();
		Assert.assertEquals(qAntes, qDepois);}
	/**
	 * Test of removed method, of class TarefaController.
	 * Caso id de teste Liberado
	 */
	@Test
	public void testRemovedTesteComIDNulo() {
		System.out.println("removed");
		Long idTarefa = 7l;
		int qAntes = repository.findAll().size();
		List<Message> errors = null;
		
		try {
			instance.removed(idTarefa, null);
			
		} catch (ValidationException validationException) {
			errors = validationException.getErrors();
		}
		Assert.assertEquals(
				"Não pode deixar salvar pois usuario nao é dono do Teste",
				"campo.form.alterado", errors.get(0).getCategory());
		
		int qDepois = repository.findAll().size();
		Assert.assertEquals(qAntes, qDepois);}
	/**
	 * Test of removed method, of class TarefaController.
	 * Caso de Tarefa com id diferente do Teste passado
	 */
	@Test
	public void testRemovedTesteComTarefaDeIdDiferenteDoTeste() {
		System.out.println("removed");
		Long idTarefa = 15l;
		int qAntes = repository.findAll().size();
		List<Message> errors = null;
		
		try {
			instance.removed(idTarefa, testeLiberado);
			
		} catch (ValidationException validationException) {
			errors = validationException.getErrors();
		}
		Assert.assertEquals(
				"Não pode deixar salvar pois usuario nao é dono do Teste",
				"campo.form.alterado", errors.get(0).getCategory());
		
		int qDepois = repository.findAll().size();
		Assert.assertEquals(qAntes, qDepois);}
	
	/**
	 * Test of removed method, of class TarefaController.
	 * Caso id de teste null
	 */
	@Test
	public void testRemovedTesteLiberado() {
		System.out.println("removed");
		Long idTarefa = 12l;
		int qAntes = repository.findAll().size();
		List<Message> errors = null;
		
		try {
			instance.removed(idTarefa, testeLiberado);
			
		} catch (ValidationException validationException) {
			errors = validationException.getErrors();
		}
		Assert.assertEquals(
				"Não pode deixar salvar pois usuario nao é dono do Teste",
				"campo.form.alterado", errors.get(0).getCategory());
		
		int qDepois = repository.findAll().size();
		Assert.assertEquals(qAntes, qDepois);}

	/**
	 * Test of saveFluxoIdeal method, of class TarefaController.
	 */
	@Test
	public void testSaveFluxoIdeal() {
		System.out.println("saveFluxoIdeal");
		String dados = "";
		Boolean completo = null;
		TarefaController instance = null;
		instance.saveFluxoIdeal(dados, completo);
		// TODO review the generated test code and remove the default call to
		// fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of saveFluxoUsuario method, of class TarefaController.
	 */
	@Test
	public void testSaveFluxoUsuario() {
		System.out.println("saveFluxoUsuario");
		String dados = "";
		Boolean completo = null;
		Long tarefaId = null;
		TarefaController instance = null;
		String expResult = "";
		String result = instance.saveFluxoUsuario(dados, completo, tarefaId);
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to
		// fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of iniciarGravacao method, of class TarefaController.
	 */
	@Test
	public void testIniciarGravacao() {
		System.out.println("iniciarGravacao");
		Long idTarefa = null;
		Long idTeste = null;
		TarefaController instance = null;
		instance.iniciarGravacao(idTarefa, idTeste);
		// TODO review the generated test code and remove the default call to
		// fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of loadtasktester method, of class TarefaController.
	 */
	@Test
	public void testLoadtasktester() {
		System.out.println("loadtasktester");
		TarefaController instance = null;
		TarefaDetalhe expResult = null;
		TarefaDetalhe result = instance.loadtasktester();
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to
		// fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of loadactiontester method, of class TarefaController.
	 */
	@Test
	public void testLoadactiontester() {
		System.out.println("loadactiontester");
		TarefaController instance = null;
		String expResult = "";
		String result = instance.loadactiontester();
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to
		// fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of loadtaskuser method, of class TarefaController.
	 */
	@Test
	public void testLoadtaskuser() {
		System.out.println("loadtaskuser");
		TarefaController instance = null;
		TarefaDetalhe expResult = null;
		TarefaDetalhe result = instance.loadtaskuser();
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to
		// fail.
		fail("The test case is a prototype.");
	}

	/**
	 * Test of loadactionuser method, of class TarefaController.
	 */
	@Test
	public void testLoadactionuser() {
		System.out.println("loadactionuser");
		TarefaController instance = null;
		String expResult = "";
		String result = instance.loadactionuser();
		assertEquals(expResult, result);
		// TODO review the generated test code and remove the default call to
		// fail.
		fail("The test case is a prototype.");
	}
}
