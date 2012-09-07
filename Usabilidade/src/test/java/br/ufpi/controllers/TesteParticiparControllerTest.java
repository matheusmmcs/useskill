/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpi.controllers;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.validator.Message;
import br.com.caelum.vraptor.validator.ValidationException;
import br.ufpi.componets.TesteSessionPlugin;
import br.ufpi.controllers.procedure.TesteParticiparTestProcedure;
import br.ufpi.models.Convidado;
import br.ufpi.repositories.AbstractDaoTest;
import br.ufpi.repositories.ConvidadoRepository;
import br.ufpi.repositories.Implement.ConvidadoRepositoryImpl;

/**
 * Caso de Teste Negar Participação</br>
 * <li>Caso em que o usuario é convidado para o teste e ele esta liberado</li>
 * <li>Caso em que o usuario é convidado para o teste e ele não esta liberado</li>
 * <li>Caso em que o usuario passa id do Teste Null</li>
 * <li>Caso em que o usuario já realizou o teste e depois tenta negar sua participação</li>
 * Caso de Teste Aceitar Participação</br>
 * <li>Caso em que o usuario é convidado para o teste e ele esta liberado</li>
 * <li>Caso em que o usuario é convidado para o teste e ele não esta liberado</li>
 * <li>Caso em que o usuario passa id do Teste Null</li>
 * <li>Caso em que o usuario já realizou o teste e depois tenta negar sua participação</li>
 * @author Cleiton
 */
public class TesteParticiparControllerTest extends AbstractDaoTest {
	private TesteParticiparController instance;
	private Long testeConvidadoNaoLiberado = 5l;
	private Long testeConvidadoLiberado = 6l;
	private Long testeConvidadoEJaParticipou = 12l;
	private ConvidadoRepository convidadoRepository;
	private Long usuarioId = 1l;
	TesteSessionPlugin testeSession= new TesteSessionPlugin(entityManager);

	@Before
	public void setUp() throws Exception {
		super.setUp();
		convidadoRepository = new ConvidadoRepositoryImpl(entityManager);
		instance = TesteParticiparTestProcedure.newInstanceTesteController(
				entityManager, result);
	}

	/**
	 * Test of negar method, of class TesteParticiparController.
	 Caso em que o usuario é convidado para o teste e ele esta liberado
	 */
	@Test
	public void testNegar() {
		System.out.println("negar");
		Convidado convidado = convidadoRepository.find(testeConvidadoLiberado,
				usuarioId);
		Assert.assertNull(convidado.isRealizou());
		instance.negar(testeConvidadoLiberado);
		Convidado convidadoDepois = convidadoRepository.find(testeConvidadoLiberado,
				usuarioId);
		Assert.assertFalse(convidadoDepois.isRealizou());
	}
	
	/**
	 * Test of negar method, of class TesteParticiparController.
Caso em que o usuario é convidado para o teste e ele não esta liberado
	 */
	@Test
	public void testNegarTesteNaoLiberado() {
		System.out.println("negar");
		Convidado convidado = convidadoRepository.find(testeConvidadoNaoLiberado,
				usuarioId);
		Assert.assertNull(convidado.isRealizou());
		List<Message> errors=null;
		try{
		instance.negar(testeConvidadoNaoLiberado);
		}catch (ValidationException validationException) {
		errors = validationException.getErrors();
		}
		Assert.assertEquals(
				"Não pode deixar salvar pois usuario nao é dono do Teste",
				"campo.form.alterado",
				errors.get(0).getCategory());
		Convidado convidadoDepois = convidadoRepository.find(testeConvidadoNaoLiberado,
				usuarioId);
		Assert.assertNull(convidadoDepois.isRealizou());
	}
	/**
	 * Test of negar method, of class TesteParticiparController.
Caso em que o usuario é convidado para o teste e ele não esta liberado
	 */
	@Test
	public void testNegarTesteNaoPassaID() {
		System.out.println("negar");
		List<Message> errors=null;
		try{
			instance.negar(null);
		}catch (ValidationException validationException) {
			errors = validationException.getErrors();
		}
		Assert.assertEquals(
				"Não pode deixar salvar pois usuario nao é dono do Teste",
				"campo.form.alterado",
				errors.get(0).getCategory());
	}
	
	/**
	 * Test of negar method, of class TesteParticiparController.
Caso em que o usuario é convidado para o teste e ele não esta liberado
	 */
	@Test
	public void testNegarUsuarioJaRealizouUmaVez() {
		System.out.println("negar");
		Convidado convidado = convidadoRepository.find(testeConvidadoEJaParticipou,
				usuarioId);
		Assert.assertTrue(convidado.isRealizou());
		List<Message> errors=null;
		try{
			instance.negar(testeConvidadoEJaParticipou);
		}catch (ValidationException validationException) {
			errors = validationException.getErrors();
		}
		Assert.assertEquals(
				"Não pode deixar salvar pois usuario nao é dono do Teste",
				"campo.form.alterado",
				errors.get(0).getCategory());
		Convidado convidadoDepois = convidadoRepository.find(testeConvidadoEJaParticipou,
				usuarioId);
		Assert.assertTrue(convidadoDepois.isRealizou());
	}
	
	/**
	 * Test of Aceitar method, of class TesteParticiparController.
	 Caso em que o usuario é convidado para o teste e ele esta liberado
	 */
	@Test
	public void testAceitar() {
		System.out.println("aceitar");
		instanciarTesteParticipar();
		Convidado convidado = convidadoRepository.find(testeConvidadoLiberado,
				usuarioId);
		Assert.assertNull(convidado.isRealizou());
		instance.aceitar(testeConvidadoLiberado);
	}
	
	private void instanciarTesteParticipar() {
		
		instance=TesteParticiparTestProcedure.newInstanceTesteController(entityManager, result, testeSession);
		
	}

	/**
	 * Test of negar method, of class TesteParticiparController.
Caso em que o usuario é convidado para o teste e ele não esta liberado
	 */
	@Test
	public void testAceitarTesteNaoLiberado() {
		System.out.println("AceitarTesteNaoLiberador");
		Convidado convidado = convidadoRepository.find(testeConvidadoNaoLiberado,
				usuarioId);
		Assert.assertNull(convidado.isRealizou());
		List<Message> errors=null;
		try{
			instance.aceitar(testeConvidadoNaoLiberado);
		}catch (ValidationException validationException) {
			errors = validationException.getErrors();
		}
		Assert.assertEquals(
				"Não pode deixar salvar pois usuario nao é dono do Teste",
				"campo.form.alterado",
				errors.get(0).getCategory());
		Convidado convidadoDepois = convidadoRepository.find(testeConvidadoNaoLiberado,
				usuarioId);
		Assert.assertNull(convidadoDepois.isRealizou());
	}
	/**
	 * Test of negar method, of class TesteParticiparController.
Caso em que o usuario é convidado para o teste e ele não passa id Teste
	 */
	@Test
	public void testAceitarTesteNaoPassaID() {
		System.out.println("AceitarTesteNaoPassaID");
		List<Message> errors=null;
		try{
			instance.aceitar(null);
		}catch (ValidationException validationException) {
			errors = validationException.getErrors();
		}
		Assert.assertEquals(
				"Não pode deixar salvar pois usuario nao é dono do Teste",
				"campo.form.alterado",
				errors.get(0).getCategory());
	}
	
	/**
	 * Test of negar method, of class TesteParticiparController.
Caso em que o usuario é convidado para o teste e já realizou uma vez
	 */
	@Test
	public void testAceitarUsuarioJaRealizouUmaVez() {
		System.out.println("negar");
		Convidado convidado = convidadoRepository.find(testeConvidadoEJaParticipou,
				usuarioId);
		Assert.assertTrue(convidado.isRealizou());
		List<Message> errors=null;
		try{
			instance.aceitar(testeConvidadoEJaParticipou);
		}catch (ValidationException validationException) {
			errors = validationException.getErrors();
		}
		Assert.assertEquals(
				"Não pode deixar salvar pois usuario nao é dono do Teste",
				"campo.form.alterado",
				errors.get(0).getCategory());
		Convidado convidadoDepois = convidadoRepository.find(testeConvidadoEJaParticipou,
				usuarioId);
		Assert.assertTrue(convidadoDepois.isRealizou());
	}
	

	/**
	 * Test of termino method, of class TesteParticiparController.
	 */
	@Test
	public void testTermino() {
		System.out.println("termino");
		instance.termino();
	}
}