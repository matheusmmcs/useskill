/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpi.controllers;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.validator.Message;
import br.com.caelum.vraptor.validator.ValidationException;
import br.ufpi.controllers.procedure.TarefaTestProcedure;
import br.ufpi.models.Action;
import br.ufpi.models.Tarefa;
import br.ufpi.models.TipoConvidado;
import br.ufpi.repositories.AbstractDaoTest;
import br.ufpi.repositories.TarefaRepository;

import com.google.gson.Gson;

/**
 * Caso de Uso criar Tarefa</br> <li>Caso em que passa tarefa null</li> <li>Caso
 * em que passa tarefa preenchida</li> <li>Caso em que passa tarefa e id de
 * teste NÃ£o pertencente ao usuario</li> <li>Caso em que passa tarefa e id de
 * teste jÃ¡ liberado</li> Caso para Salvar tarefa</br> <li>Caso de Sucesso</li>
 * <li>Caso id de teste nÃ£o pertence ao usuario</li> <li>Caso id de teste null</li>
 * <li>Caso id de teste Liberado</li> <li>Caso de Tarefa sem os campos
 * preenchidos</li> Faltando Caso para Editar tarefa</br> <li>Caso de Sucesso</li>
 * <li>Caso id de teste nÃ£o pertence ao usuario</li> <li>Caso id de teste null</li>
 * <li>Caso id de teste Liberado</li> <li>Caso de Tarefa com id diferente do
 * Teste passado</li> Caso para Remover tarefa</br> <li>Caso de Sucesso</li> <li>
 * Caso id de teste nÃ£o pertence ao usuario</li> <li>Caso id de teste null</li>
 * <li>Caso id de teste Liberado</li> <li>Caso de Tarefa com id diferente do
 * Teste passado</li> <li>Caso de Teste com Fluxo ideal gravado</li> Caso para
 * Update tarefa</br> <li>Caso de Sucesso sem fluxo ideal gravado</li> <li>Caso
 * id de teste nÃ£o pertence ao usuario</li> <li>Caso id de teste null</li> <li>
 * Caso id de teste Liberado</li> <li>Caso de Tarefa com id diferente do Teste
 * passado</li> <li>Caso de Teste com Fluxo ideal gravado</li>
 * 
 * @author Cleiton
 */
public class TarefaControllerTest extends AbstractDaoTest {
	private static Long testePertenceUsuarioNaoLiberado = 10l;
	private static Long testeNaoPertenceUsuario = 5l;
	private static Long testeLiberado = 11l;
	private TarefaRepository repository;
	private TarefaController instance;
	private Long testeNaoPertenceUsuario2 = 14l;

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
	 * tarefa e id de teste NÃ£o pertencente ao usuario
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
				"NÃ£o pode deixar salvar pois usuario nao Ã© dono do Teste",
				"campo.form.alterado", errors.get(0).getCategory());

		Assert.assertNull(result);
	}

	/**
	 * Test of criarTarefa method, of class TarefaController. Caso em que passa
	 * tarefa e id de teste jÃ¡ liberado
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
				"NÃ£o pode deixar salvar pois usuario nao Ã© dono do Teste",
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
	 * Test of salvarTarefa method, of class TarefaController. Caso id de teste
	 * nÃ£o pertence ao usuario
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
				"NÃ£o pode deixar salvar pois usuario nao Ã© dono do Teste",
				"campo.form.alterado", errors.get(0).getCategory());
		int qDepois = repository.findAll().size();
		Assert.assertEquals(qAntes, qDepois);
	}

	/**
	 * Test of salvarTarefa method, of class TarefaController. Caso id de teste
	 * nÃ£o pertence ao usuario
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
				"NÃ£o pode deixar salvar pois usuario nao Ã© dono do Teste",
				"campo.form.alterado", errors.get(0).getCategory());
		int qDepois = repository.findAll().size();
		Assert.assertEquals(qAntes, qDepois);
	}

	/**
	 * Test of salvarTarefa method, of class TarefaController. Caso id de teste
	 * nÃ£o pertence ao usuario
	 */
	@Test
	public void testSalvarTarefaCamposNaoPreenchido() {
		System.out.println("salvarTarefa");
		int qAntes = repository.findAll().size();
		Tarefa tarefa = TarefaTestProcedure.newInstanceTarefa("", "", "");
		List<Message> errors = null;
		try {
			instance.salvarTarefa(tarefa, testePertenceUsuarioNaoLiberado);
		} catch (ValidationException validationException) {
			errors = validationException.getErrors();
			System.out.println(errors);
		}
		Assert.assertEquals("Pra ter gerado 3 erros", 3, errors.size());
		int qDepois = repository.findAll().size();
		Assert.assertEquals(qAntes, qDepois);
	}

	/**
	 * Test of salvarTarefa method, of class TarefaController. Caso id de teste
	 * nul
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
				"NÃ£o pode deixar salvar pois usuario nao Ã© dono do Teste",
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
		Long idTarefa = 7l;
		new TarefaTestProcedure();
		Tarefa tarefa = TarefaTestProcedure.newInstanceTarefa(idTarefa,
				"urlInicial", "roteiero", "nome");
		boolean isErro = false;
		Tarefa result = instance.editarTarefa(testePertenceUsuarioNaoLiberado,
				tarefa, isErro);
		Assert.assertNotNull(result);
	}

	/**
	 * Test of editarTarefa method, of class TarefaController. Caso id de teste
	 * nÃ£o pertence ao usuario
	 */
	@Test
	public void testEditarTarefaTesteNaoPertenceUsuario() {
		System.out.println("editarTarefa");
		Long idTarefa = 1l;
		new TarefaTestProcedure();
		Tarefa tarefa = TarefaTestProcedure.newInstanceTarefa(idTarefa,
				"urlInicial", "roteiero", "nome");
		boolean isErro = false;
		Tarefa result = null;
		List<Message> errors = null;

		try {

			result = instance.editarTarefa(testePertenceUsuarioNaoLiberado,
					tarefa, isErro);
		} catch (ValidationException validationException) {
			errors = validationException.getErrors();
		}
		Assert.assertEquals(
				"NÃ£o pode deixar salvar pois usuario nao Ã© dono do Teste",
				"campo.form.alterado", errors.get(0).getCategory());

		Assert.assertNull(result);
	}

	/**
	 * Test of editarTarefa method, of class TarefaController. Caso id de teste
	 * null
	 */
	@Test
	public void testEditarTarefaTesteIdNulo() {
		System.out.println("editarTarefa");
		Long idTarefa = 7l;
		new TarefaTestProcedure();
		Tarefa tarefa = TarefaTestProcedure.newInstanceTarefa(idTarefa,
				"urlInicial", "roteiero", "nome");
		boolean isErro = false;
		Tarefa result = null;
		List<Message> errors = null;

		try {

			result = instance.editarTarefa(null, tarefa, isErro);
		} catch (ValidationException validationException) {
			errors = validationException.getErrors();
		}
		Assert.assertEquals(
				"NÃ£o pode deixar salvar pois usuario nao Ã© dono do Teste",
				"campo.form.alterado", errors.get(0).getCategory());

		Assert.assertNull(result);
	}

	/**
	 * Test of editarTarefa method, of class TarefaController. Caso id de teste
	 * null
	 */
	@Test
	public void testEditarTarefaTesteIdDiferenteDaTarefa() {
		System.out.println("editarTarefa");
		Long idTarefa = 4l;
		new TarefaTestProcedure();
		Tarefa tarefa = TarefaTestProcedure.newInstanceTarefa(idTarefa,
				"urlInicial", "roteiero", "nome");
		boolean isErro = false;
		Tarefa result = null;
		List<Message> errors = null;

		try {

			result = instance.editarTarefa(testePertenceUsuarioNaoLiberado,
					tarefa, isErro);
		} catch (ValidationException validationException) {
			errors = validationException.getErrors();
		}
		Assert.assertEquals(
				"NÃ£o pode deixar salvar pois usuario nao Ã© dono do Teste",
				"campo.form.alterado", errors.get(0).getCategory());

		Assert.assertNull(result);
	}

	/**
	 * Test of editarTarefa method, of class TarefaController. Caso id de teste
	 * null
	 */
	@Test
	public void testEditarTarefaIdNulo() {
		System.out.println("editarTarefa");
		new TarefaTestProcedure();
		Tarefa tarefa = TarefaTestProcedure.newInstanceTarefa("urlInicial",
				"roteiero", "nome");
		boolean isErro = false;
		Tarefa result = null;
		List<Message> errors = null;

		try {

			result = instance.editarTarefa(testePertenceUsuarioNaoLiberado,
					tarefa, isErro);
		} catch (ValidationException validationException) {
			errors = validationException.getErrors();
		}
		Assert.assertEquals(
				"NÃ£o pode deixar salvar pois usuario nao Ã© dono do Teste",
				"campo.form.alterado", errors.get(0).getCategory());

		Assert.assertNull(result);
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
		Assert.assertEquals(qAntes, qDepois + 1);
	}

	/**
	 * Test of removed method, of class TarefaController. Caso id de teste nÃ£o
	 * pertence ao usuario
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
				"NÃ£o pode deixar salvar pois usuario nao Ã© dono do Teste",
				"campo.form.alterado", errors.get(0).getCategory());

		int qDepois = repository.findAll().size();
		Assert.assertEquals(qAntes, qDepois);
	}

	/**
	 * Test of removed method, of class TarefaController. Caso id de teste
	 * Liberado
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
				"NÃ£o pode deixar salvar pois usuario nao Ã© dono do Teste",
				"campo.form.alterado", errors.get(0).getCategory());

		int qDepois = repository.findAll().size();
		Assert.assertEquals(qAntes, qDepois);
	}

	/**
	 * Test of removed method, of class TarefaController. Caso de Tarefa com id
	 * diferente do Teste passado
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
				"NÃ£o pode deixar salvar pois usuario nao Ã© dono do Teste",
				"campo.form.alterado", errors.get(0).getCategory());

		int qDepois = repository.findAll().size();
		Assert.assertEquals(qAntes, qDepois);
	}

	/**
	 * Test of removed method, of class TarefaController. Caso id de teste null
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
				"NÃ£o pode deixar salvar pois usuario nao Ã© dono do Teste",
				"campo.form.alterado", errors.get(0).getCategory());

		int qDepois = repository.findAll().size();
		Assert.assertEquals(qAntes, qDepois);
	}

	@Test
	public void exibirActions() {
		Long usarioId = 1l;
		Long tarefaId = 1l;
		Long testeId = 8l;
		instance.exibirActions(testeId, tarefaId, usarioId);
	}

	public String getGSonDados() {
		List<Action> acaos = new ArrayList<Action>();
		for (int i = 0; i < 10; i++) {

			Action acao = new Action();
			acao.setsContent("conteudo");
			acao.setsPosX(i);
			acao.setsPosY(i);
			acao.setsTag("tag");
			acao.setsTag("sTagName");
			acao.setsUrl("www.globo.com");
			acaos.add(acao);
		}
		Gson gson = new Gson();
		return gson.toJson(acaos);
	}

	@Test
	public void saveFluxo() {
		
		instance= TarefaTestProcedure.newInstanceTarefaController(entityManager, result, 8l,TipoConvidado.TESTER);
		instance.saveFluxo(this.getGSonDados(), 8l);
	}
}
