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
import br.ufpi.controllers.procedure.RespostaTestProcedure;
import br.ufpi.models.Alternativa;
import br.ufpi.repositories.AbstractDaoTest;
import br.ufpi.repositories.RespostaAlternativaRepository;
import br.ufpi.repositories.RespostaEscritaRepository;

/**
 * Caso de Teste Salvar Resposta Escrita</br> <li>Salvar resposta sem passar
 * resposta</li> <li>Salvar resposta Caso correto</li>
 * Caso de teste Resposta Alternativa</br>
 * <li>Salvar alternativa de forma coreta</li>
 * <li>Salvar alternativa com id diferente da pergunta passada</li>
 * 
 * @author Cleiton
 */
public class RespostaControllerTest extends AbstractDaoTest {

	private static final Long testeConvidadoLiberado = 13l;
	private static final Long perguntaDoTesteConvidadoLiberadoSubjetiva = 31l;
	private static final Long perguntaDoTesteConvidadoLiberadoObjetiva = 26l;
	private static final Long alternativaPergunta26 = 54l;
	private RespostaAlternativaRepository respostaAlternativaRepository;

	private RespostaEscritaRepository escritaRepositoryImpl;
	private RespostaController instance;
	private TesteSessionPlugin testeSession;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		respostaAlternativaRepository = RespostaTestProcedure
				.newInstanceRespostaAlternativaRepository(entityManager);
		escritaRepositoryImpl = RespostaTestProcedure
				.newInstanceRespostaEscritaRepositoryImpl(entityManager);
		testeSession = new TesteSessionPlugin();
		testeSession.setIdTeste(testeConvidadoLiberado);
		instance = RespostaTestProcedure.newInstanceRespostaController(
				entityManager, result,testeSession);

	}

	/**
	 * Test of salvarRespostaEscrita method, of class RespostaController.
	 */
	@Test
	public void testSalvarRespostaEscritaSemPassarResposta() {
		System.out.println("salvarRespostaEscrita");
		String resposta = "";
		int qAntes = escritaRepositoryImpl.findAll().size();
		List<Message> errors = null;
		try {
			instance.salvarRespostaEscrita(resposta,perguntaDoTesteConvidadoLiberadoSubjetiva);
		} catch (ValidationException validationException) {
			errors = validationException.getErrors();
		}
		Assert.assertEquals("Não pode deixar salvar pois campo esta vazio",
				"campo.resposta.obrigatorio", errors.get(0).getCategory());
		int qDepois = escritaRepositoryImpl.findAll().size();
		Assert.assertEquals("Era para possuir o mesmo tanto de respostas",
				qAntes, qDepois);
	}

	/**
	 * Test of salvarRespostaEscrita method, of class RespostaController.
	 */
	@Test
	public void testSalvarRespostaEscritaCorretaInicio() {
		System.out.println("salvarRespostaEscrita");
		String resposta = "Esta é minha Resposta";
		int qAntes = escritaRepositoryImpl.findAll().size();
		instance.salvarRespostaEscrita(resposta,perguntaDoTesteConvidadoLiberadoSubjetiva);
		int qDepois = escritaRepositoryImpl.findAll().size();
		Assert.assertEquals("Era para ter salvo mais uma resposta", qAntes + 1,
				qDepois);
	}

	/**
	 * Test of salvarRespostaAlternativa method, of class RespostaController.
	 */
	@Test
	public void testSalvarRespostaAlternativaCorreta() {
		System.out.println("salvarRespostaAlternativa");
		int resAntes = respostaAlternativaRepository.findAll().size();
		Alternativa alternativa = new Alternativa();
		alternativa.setId(alternativaPergunta26);
		instance.salvarRespostaAlternativa(alternativa,perguntaDoTesteConvidadoLiberadoObjetiva);
		int resDepois = respostaAlternativaRepository.findAll().size();
		Assert.assertEquals(resAntes, resDepois - 1);
	}
	/**
	 * Test of salvarRespostaAlternativa method, of class RespostaController.
	 */
	@Test
	public void testSalvarRespostaAlternativaPassandoIdInvalido() {
		System.out.println("salvarRespostaAlternativa Id invalido");
		int resAntes = respostaAlternativaRepository.findAll().size();
		Alternativa alternativa = new Alternativa();
		alternativa.setId(1l);
		List<Message> errors=null;
		try {
			System.out.println("Teste session "+testeSession.getIdTeste());
			instance.salvarRespostaAlternativa(alternativa,perguntaDoTesteConvidadoLiberadoObjetiva);
		} catch (ValidationException validationException) {
			errors = validationException.getErrors();
		}
		Assert.assertEquals("Alternativa não pertence ao teste","campo.form.alterado", errors.get(0).getCategory());
		int resDepois = respostaAlternativaRepository.findAll().size();
		Assert.assertEquals(resAntes, resDepois);
	}


	/**
	 * Test of exibirRespostas method, of class RespostaController.
	 */
	@Test
	public void testExibirRespostas() {
		System.out.println("exibirRespostas");
		//TODO Ainda falta Fazer os Teste mas so em uma segunda etapa
	}

}
