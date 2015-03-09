/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpi.controllers;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Before;
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
	private UsuarioRepository repository;
	private UsuarioController instance;
	private static Long usuarioLogado = 1l;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		repository = UsuarioTestProcedure
				.newInstanceUsuarioRepository(entityManager);
		instance = UsuarioTestProcedure
				.newInstanceUsuarioController(entityManager);
	}

	/**
	 * Test of index method, of class UsuarioController.
	 */
	@Test
	public void testIndex() {
		System.out.println("index");
		UsuarioRepository usuarioRepository = UsuarioTestProcedure
				.newInstanceUsuarioRepository(entityManager);
		assertEquals(usuarioRepository.findAll().size(), instance.index()
				.size());
	}

	/**
	 * Test of create method, of class UsuarioController.
	 */
	@Test()
	public void testCorrectCreate() {
		System.out.println("create");
		String senha = "cleiton";

		Usuario usuario = UsuarioTestProcedure.newInstaceUsuario(entityManager,
				"cleiton", "cleitonmoura19@hotmail.com", senha);
		int qAntes = instance.index().size();
		instance.create(usuario, senha);
		assertEquals("Deveria ter um usuario a Mais", qAntes + 1, instance
				.index().size());
	}

	@Test
	public void testEmailEqual() {
		System.out.println("create email equal");
		String senha = "cleiton";
		
		Usuario usuario = UsuarioTestProcedure.newInstaceUsuario(entityManager,
				"cleiton", "cleitonmoura18@hotmail.com", senha);
		try {
			instance.create(usuario, senha);
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
				"Cleiton Santos ", "cleitonmouraSilvesteas@gmail.com", "moura");
		usuario.setId(1l);
		try {
			instance.update(usuario);
		} catch (ValidationException validationException) {
			Assert.assertTrue("Não era para gerar EXception", false);
		}
		Usuario show = instance.show(usuario);
		assertEquals(usuario.getEmail(), show.getEmail());
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
		try {
			instance.edit(usuario);
		} catch (ValidationException validationException) {
			Message errors = validationException.getErrors().get(0);
			Assert.assertEquals(
					"Era para ter gerado mensagem de que o usuario não é dono da secao",
					"nao.proprietario", errors.getCategory());

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
		instance.usuarioLogado.setUsuario(UsuarioTestProcedure
				.newInstanceUsuarioRepository(entityManager).find(6l));
		int qAntes = instance.index().size();
		instance.destroy(usuario);
		Assert.assertEquals("Deveria ter um usuario a menos", qAntes - 1,
				instance.index().size());
	}

	/**
	 * Alterar senha sucesso
	 */
	@Test
	public void testalterarSenha() {
		System.out.println("Alterar senha sucesso");
		String antiga = repository.find(usuarioLogado).getSenha();
		instance.alterarSenha("senhaNova", "senhaNova", "senha1");
		String nova = repository.find(usuarioLogado).getSenha();
		Assert.assertFalse(nova.equals(antiga));

	}
}
