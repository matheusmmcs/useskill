/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpi.controllers;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.vraptor.validator.Message;
import br.com.caelum.vraptor.validator.ValidationException;
import br.ufpi.controllers.procedure.UsuarioTestProcedure;
import br.ufpi.models.Usuario;
import br.ufpi.repositories.AbstractDaoTest;
import br.ufpi.repositories.UsuarioRepository;

/**
 * 
 * @author Cleiton
 */
public class UsuarioControllerTest extends AbstractDaoTest {

	/**
	 * Test of index method, of class UsuarioController.
	 */
	@Test
	public void testIndex() {
		System.out.println("index");
		UsuarioController instance = UsuarioTestProcedure
				.newInstanceUsuarioController(entityManager);
		UsuarioRepository usuarioRepository= UsuarioTestProcedure.newInstanceUsuarioRepository(entityManager);
		assertEquals(usuarioRepository.findAll().size(), instance.index().size());
	}

	/**
	 * Test of create method, of class UsuarioController.
	 */
	@Test()
	public void testCorrectCreate() {
		System.out.println("create");

		Usuario usuario = UsuarioTestProcedure.newInstaceUsuario(entityManager,
				"cleiton", "cleitonmoura19@hotmail.com", "cleiton");
		UsuarioController instance = UsuarioTestProcedure
				.newInstanceUsuarioController(entityManager);
		int qAntes = instance.index().size();
		instance.create(usuario);
		assertEquals("Deveria ter um usuario a Mais", qAntes + 1, instance
				.index().size());
	}

	@Test
	public void testEmailEqual() {
		System.out.println("create email equal");
		Usuario usuario = UsuarioTestProcedure.newInstaceUsuario(entityManager,
				"cleiton", "cleitonmoura18@hotmail.com", "cleiton");
		UsuarioController instance = UsuarioTestProcedure
				.newInstanceUsuarioController(entityManager);
		try {
			instance.create(usuario);
		} catch (ValidationException validationExceptione) {

			Assert.assertEquals("Email já esta sendo usado",
					"email.cadastrado", validationExceptione.getErrors().get(0)
							.getCategory());

		}
	}

	/**
	 * Test of newUsuario method, of class UsuarioController.
	 */
	@Test
	public void testNewUsuario() {
		System.out.println("newUsuario");
		UsuarioController instance = UsuarioTestProcedure
				.newInstanceUsuarioController(entityManager);
		Usuario result = instance.newUsuario();
		Assert.assertNotNull(result);
	}

	/**
	 * Test of update method, of class UsuarioController.
	 */
	@Test
	public void testUpdate() {
		System.out.println("update");
		Usuario usuario = UsuarioTestProcedure.newInstaceUsuario(entityManager,
				"Cleiton Santos ", "cleitonmouraSilveste@gmail.com", "moura");
		usuario.setId(1l);
		UsuarioController instance = UsuarioTestProcedure
				.newInstanceUsuarioController(entityManager);
		try {
			instance.update(usuario);
		} catch (ValidationException validationException) {
			Assert.assertTrue("Não era para gerar EXception", false);
		}
		Usuario show = instance.show(usuario);
		assertEquals(usuario.getNome(), show.getNome());
	}

	/**
	 * Tentar atualizar usuario com email que jah esta cadastrado no banco
	 */
	@Test
	public void testUpdateErro() {
		System.out.println("update");
		Usuario usuario = UsuarioTestProcedure.newInstaceUsuario(entityManager,
				"Cleiton Santos ", "cleitonmoura18@hotmail.com", "moura");
		usuario.setId(1l);
		UsuarioController instance = UsuarioTestProcedure
				.newInstanceUsuarioController(entityManager);
		try {
			instance.update(usuario);
		} catch (ValidationException validationException) {
			Assert.assertEquals("Ja possui usuario cadastrado com este nome",
					"email.cadastrado", validationException.getErrors().get(0)
							.getCategory());
		}
		Usuario show = instance.show(usuario);
		assertEquals("cleiton", show.getNome());
	}

	/**
	 * Test of edit method, of class UsuarioController.
	 */
	@Test
	public void testEdit() {
		System.out.println("edit");
		Usuario usuario = new Usuario();
		usuario.setId(1l);
		UsuarioController instance = UsuarioTestProcedure
				.newInstanceUsuarioController(entityManager);
		Usuario result = instance.edit(usuario);
		assertEquals("cleiton", result.getNome());
	}

	/*
	 * Tentar ediatar usuario que nao é o mesmo que esta na sessão
	 */
	@Test
	public void testEditErro() {
		System.out.println("edit");
		Usuario usuario = new Usuario();
		usuario.setId(2l);
		UsuarioController instance = UsuarioTestProcedure
				.newInstanceUsuarioController(entityManager);
		try {
			instance.edit(usuario);
		} catch (ValidationException validationException) {
			Message errors = validationException.getErrors().get(0);
			Assert.assertEquals("Era para ter gerado mensagem de que o usuario não é dono da secao", "nao.proprietario",
					errors.getCategory());
			
			return;
		}
		Assert.assertFalse(
				"Era para ter entrado na exception pois usuario não é o mesmo da section",
				true);
		;
	}

	/**
	 * Test of show method, of class UsuarioController.
	 */
	@Test
	public void testShow() {
		System.out.println("show");
		Usuario usuario = new Usuario();
		usuario.setId(1l);
		UsuarioController instance = UsuarioTestProcedure.newInstanceUsuarioController(entityManager);
		Usuario result = instance.show(usuario);
		assertEquals("cleiton", result.getNome());
	}

	/**
	 * Test of destroy method, of class UsuarioController.
	 */
	@Test
	public void testDestroy() {
		System.out.println("destroy");
		Usuario usuario = new Usuario();
		usuario.setId(6l);
		UsuarioController instance =  UsuarioTestProcedure.newInstanceUsuarioController(entityManager);
		instance.usuarioLogado.setUsuario(UsuarioTestProcedure.newInstanceUsuarioRepository(entityManager).find(6l));
		int qAntes = instance.index().size();
		instance.destroy(usuario);
		Assert.assertEquals("Deveria ter um usuario a menos", qAntes-1,instance.index().size());
	}
}
