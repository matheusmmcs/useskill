/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpi.controllers;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import br.com.caelum.vraptor.validator.ValidationException;
import br.ufpi.controllers.procedure.LoginTestProcedure;
import br.ufpi.controllers.procedure.UsuarioTestProcedure;
import br.ufpi.models.Teste;
import br.ufpi.repositories.AbstractDaoTest;
import br.ufpi.repositories.UsuarioRepository;

/**
 * 
 * @author Cleiton
 */
public class LoginControllerTest extends AbstractDaoTest {
	/**
	 * Test of login method, of class LoginController.
	 */
	@Test
	public void testLogin() {
		System.out.println("login");
		String email = "cleitonmouraSilveste@gmail.com";
		LoginController instance = LoginTestProcedure
				.newInstanceUsuarioController(entityManager, result);
		instance.login(email);
		Assert.assertEquals("O email deveria ser igual" + email, email, result
				.included().get("email"));

	}

	/**
	 * Test of conta method, of class LoginController. Usuario não possui senha
	 * confirmada se usuario possuir senha confirmada o objeto usuarioLogado
	 * mudaria
	 */
	@Test
	public void testConta() {
		System.out.println("conta");
		String email = "cleitonmoura18@hotmail.com";
		String senha = "senha2";
		LoginController instance = LoginTestProcedure
				.newInstanceUsuarioController(entityManager, result);
		instance.conta(email, senha);
		Assert.assertEquals("cleiton", instance.usuarioLogado.getUsuario()
				.getNome());

	}

	/**
	 * Usuario possui senha confirmada se usuario possuir senha confirmada o
	 * objeto usuarioLogado muda.
	 */
	@Test
	public void testContaEmailConfirmado() {
		System.out.println("conta");
		String email = "claudiamoura18@gmail.com";
		String senha = "senha1";
		LoginController instance = LoginTestProcedure
				.newInstanceUsuarioController(entityManager, result);
		instance.conta(email, senha);
		Assert.assertEquals("claudia", instance.usuarioLogado.getUsuario()
				.getNome());

	}

	/**
	 * Testar fazer login sem entrar com uma conta de email
	 */
	@Test
	public void testContaSemPassarEmail() {
		System.out.println("conta");
		String email = "";
		String senha = "senha1";
		LoginController instance = LoginTestProcedure
				.newInstanceUsuarioController(entityManager, result);
		try {
			instance.conta(email, senha);
		} catch (ValidationException validationException) {
			Assert.assertEquals("campo.email.obrigatorio", validationException
					.getErrors().get(0).getCategory());
		}
	}

	/**
	 * Testar fazer com senha incorreta
	 */
	@Test
	public void testContaComEmailSenhaErrada() {
		System.out.println("conta");
		String email = "claudiamoura18@gmail.com";
		String senha = "sen";
		LoginController instance = LoginTestProcedure
				.newInstanceUsuarioController(entityManager, result);
		try {
			instance.conta(email, senha);
		} catch (ValidationException validationException) {
			Assert.assertEquals("email.senha.invalido", validationException
					.getErrors().get(0).getCategory());
		}
	}

	/**
	 * Testar fazer login sem entrar com uma conta de email
	 */
	@Test
	public void testContaSemPassarEmailNemSenha() {
		System.out.println("conta");
		String email = "";
		String senha = "";
		LoginController instance = LoginTestProcedure
				.newInstanceUsuarioController(entityManager, result);
		try {
			instance.conta(email, senha);
		} catch (ValidationException validationException) {
			Assert.assertEquals("campo.email.obrigatorio", validationException
					.getErrors().get(0).getCategory());
			Assert.assertEquals("campo.senha.obrigatorio", validationException
					.getErrors().get(1).getCategory());
		}
	}

	/**
	 * Testar fazer login sem entrar com uma senha
	 */
	@Test
	public void testContaSemPassarSenha() {
		System.out.println("conta");
		String email = "claudiamoura18@gmail.com";
		String senha = "";
		LoginController instance = LoginTestProcedure
				.newInstanceUsuarioController(entityManager, result);
		try {
			instance.conta(email, senha);
		} catch (ValidationException validationException) {
			Assert.assertEquals("campo.senha.obrigatorio", validationException
					.getErrors().get(0).getCategory());
		}
	}

	/**
	 * Test of logado method, of class LoginController.
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testLogado() {
		System.out.println("logado");
		LoginController instance = LoginTestProcedure
				.newInstanceUsuarioController(entityManager, result);
		instance.logado(1);
		List<Teste> testesCriados = (List<Teste>) result.included().get(
				"testesCriados");
		List<Teste> testesConvidados = (List<Teste>) result.included().get(
				"testesConvidados");
		for (Teste teste : testesConvidados) {
			if(teste.getId()==5){
				Assert.assertTrue("Teste 5 ainda não foi liberado entao não e para o usuario ver este teste",false);
			}
		}
		Assert.assertEquals("Usuario so foi convidado para 4 teste",4,
				testesConvidados.size());
		Assert.assertEquals(
				"Usuario so possui 7 teste criados e não liberados ", 7,
				testesCriados.size());
	}

	/**
	 * Test of logout method, of class LoginController.
	 */
	@Test
	public void testLogout() {
		System.out.println("logout");
		LoginController instance = LoginTestProcedure
				.newInstanceUsuarioController(entityManager, result);
		instance.logout();
		Assert.assertNull("Era pra ser nulo",
				instance.usuarioLogado.getUsuario());
	}

	/**
	 * Test of validarInscricao method, of class LoginController.
	 */
	@Test
	public void testValidarInscricao() {
		System.out.println("validarInscricao");
		UsuarioRepository usuarioRepository = UsuarioTestProcedure
				.newInstanceUsuarioRepository(entityManager);
		String confirmacaoEmail = usuarioRepository.find(1l)
				.getConfirmacaoEmail();
		LoginController instance = LoginTestProcedure
				.newInstanceUsuarioController(entityManager, result);
		instance.validarInscricao(confirmacaoEmail);
		Assert.assertNull("Apos confirmar a confirmação de email fica null",
				usuarioRepository.find(1l).getConfirmacaoEmail());
	}

	/**
	 * 
	 * Valida a incrição de email passando confirmação de email inesistente Deve
	 * retorna um result vazio pois ele nao adiciona usuario apenas redireciona
	 * para outra view
	 */
	@Test
	public void testValidarInscricaoPassandoConfirmacaoInexistente() {
		System.out.println("validarInscricaoPassandoConfirmacaoInexistente");
		String confirmacaoEmail = "naoExiste";
		LoginController instance = LoginTestProcedure
				.newInstanceUsuarioController(entityManager, result);
		instance.validarInscricao(confirmacaoEmail);
		Assert.assertTrue(
				"Deveria estar vazio o include pois ele não encoutrou o usuario",
				result.included().isEmpty());
	}

	/**
	 * Test of recuperaSenha method, of class LoginController.
	 */
	@Test
	public void testRecuperaSenha() {
		System.out.println("recuperaSenha");
		String email = "cleitonmoura8@gmail.com";
		LoginController instance = LoginTestProcedure
				.newInstanceUsuarioController(entityManager, result);
		instance.recuperaSenha(email);
		assertEquals(email, result.included().get("email"));
	}

	/**
	 * Test of recuperaSenhaCompleta method, of class LoginController. Sem
	 * passar o campo email
	 */
	@Test
	public void testRecuperaSenhaCompletaSemPassarEmail() {
		System.out.println("RecuperaSenhaCompletaSemPassarEmail");
		String email = "";
		LoginController instance = LoginTestProcedure
				.newInstanceUsuarioController(entityManager, result);
		try {

			instance.recuperaSenhaCompleta(email);
		} catch (ValidationException validationException) {
			Assert.assertEquals("campo.email.obrigatorio", validationException
					.getErrors().get(0).getCategory());
		}

	}

	/**
	 * Test of recuperaSenhaCompleta method, of class LoginController.Passando
	 * um email invalido
	 */
	@Test
	public void testRecuperaSenhaCompletaEmailInvalido() {
		System.out.println("RecuperaSenhaCompletaEmailInvalido");
		String email = "cleiton@gtalk.com";
		LoginController instance = LoginTestProcedure
				.newInstanceUsuarioController(entityManager, result);
		try {
			instance.recuperaSenhaCompleta(email);
		} catch (ValidationException validationException) {
			Assert.assertEquals("email.nao.cadastrado", validationException
					.getErrors().get(0).getCategory());
		}

	}

	/**
	 * Test of recuperaSenhaCompleta method, of class LoginController.Passando
	 * um email valido
	 */
	@Test
	public void testRecuperaSenhaCompletaEmailValido() {
		System.out.println("RecuperaSenhaCompletaEmailValido");
		String email = "cleitonmouraSilveste@gmail.com";
		LoginController instance = LoginTestProcedure
				.newInstanceUsuarioController(entityManager, result);
		UsuarioRepository repository = UsuarioTestProcedure
				.newInstanceUsuarioRepository(entityManager);
		String senha = repository.find(1l).getSenha();
		instance.recuperaSenhaCompleta(email);
		Assert.assertFalse("As senhas deveria ser diferentes",
				senha.equals(repository.find(1l).getSenha()));

	}

	/**
	 * Test of reenviaEmailConfirmacao method, of class LoginController.
	 */
	@Test
	public void testReenviaEmailConfirmacao() {
		System.out.println("reenviaEmailConfirmacao");
		String email = "cleitonmouraSilveste@gmail.com";
		LoginController instance = LoginTestProcedure
				.newInstanceUsuarioController(entityManager, result);
		instance.reenviaEmailConfirmacao(email);
		Assert.assertEquals(email, result.included().get("email"));
	}

	/**
	 * Test of reenviaEmailConfirmacaoCompleto method, of class LoginController.
	 * Passando parametro de email vazio
	 */
	@Test
	public void testReenviaEmailConfirmacaoCompleto() {
		System.out.println("reenviaEmailConfirmacaoCompleto");
		String email = "";
		LoginController instance = LoginTestProcedure
				.newInstanceUsuarioController(entityManager, result);
		try {
			instance.reenviaEmailConfirmacaoCompleto(email);
		} catch (ValidationException validationException) {

			Assert.assertEquals("campo.email.obrigatorio", validationException
					.getErrors().get(0).getCategory());
		}
	}

	/**
	 * Test of reenviaEmailConfirmacaoCompleto method, of class LoginController.
	 * Passando parametro usuario com email ja confirmado
	 */
	@Test
	public void testReenviaEmailConfirmacaoCompletoEmailJaConfirmado() {
		System.out
				.println("testReenviaEmailConfirmacaoCompletoEmailJaConfirmado");
		String email = "claudiamoura18@gmail.com";
		LoginController instance = LoginTestProcedure
				.newInstanceUsuarioController(entityManager, result);
		instance.reenviaEmailConfirmacaoCompleto(email);
		Assert.assertTrue("Email já esta confirmado", (Boolean) result
				.included().get("isConfirmado"));
	}

	/**
	 * Test of reenviaEmailConfirmacaoCompleto method, of class LoginController.
	 * Passando parametro de email não existente no banco
	 */
	@Test
	public void testReenviaEmailConfirmacaoCompletoEmailNaoExiste() {
		System.out.println("reenviaEmailConfirmacaoCompleto");
		String email = "cleiton@gmail.com";
		LoginController instance = LoginTestProcedure
				.newInstanceUsuarioController(entityManager, result);
		try {
			instance.reenviaEmailConfirmacaoCompleto(email);
		} catch (ValidationException validationException) {
			Assert.assertEquals("email.nao.cadastrado", validationException
					.getErrors().get(0).getCategory());
		}
	}

	/**
	 * Test of reenviaEmailConfirmacaoCompleto method, of class LoginController.
	 * Passando parametro de email existente no banco e ainda não confirmado
	 * email(confirmação de email!=null)
	 */
	@Test
	public void testReenviaEmailConfirmacaoCompletoEmailExiste() {
		System.out.println("reenviaEmailConfirmacaoCompleto");
		String email = "cleitonmouraSilveste@gmail.com";
		LoginController instance = LoginTestProcedure
				.newInstanceUsuarioController(entityManager, result);
		instance.reenviaEmailConfirmacaoCompleto(email);
		Assert.assertFalse("Email ainda não esta confirmado", (Boolean) result
				.included().get("isConfirmado"));
	}

}
