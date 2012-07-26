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
import br.ufpi.componets.FluxoComponente;
import br.ufpi.componets.TesteSession;
import br.ufpi.controllers.procedure.RespostaTestProcedure;
import br.ufpi.controllers.procedure.TesteParticiparTestProcedure;
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
	private static final Long alternativaPergunta26 = 54l;
	private static final Long alternativaPergunta28 = 63l;
	private RespostaAlternativaRepository respostaAlternativaRepository;

	private RespostaEscritaRepository escritaRepositoryImpl;
	private RespostaController instance;
	private TesteParticiparController participarController;
	private FluxoComponente fluxo;
	private TesteSession testeSession;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		respostaAlternativaRepository = RespostaTestProcedure
				.newInstanceRespostaAlternativaRepository(entityManager);
		escritaRepositoryImpl = RespostaTestProcedure
				.newInstanceRespostaEscritaRepositoryImpl(entityManager);
		fluxo = new FluxoComponente();
		testeSession = new TesteSession();
		participarController = TesteParticiparTestProcedure
				.newInstanceTesteController(entityManager, result,
						testeSession, fluxo);
		instance = RespostaTestProcedure.newInstanceRespostaController(
				entityManager, result, fluxo);

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
			instance.salvarRespostaEscrita(resposta);
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
		participarController.aceitar(testeConvidadoLiberado);
		String resposta = "Esta é minha Resposta";
		int qAntes = escritaRepositoryImpl.findAll().size();
		int perAntes = fluxo.getPerguntasInicio().size();
		instance.salvarRespostaEscrita(resposta);
		int qDepois = escritaRepositoryImpl.findAll().size();
		int perDepois = fluxo.getPerguntasInicio().size();
		Assert.assertEquals(perAntes, perDepois + 1);
		Assert.assertEquals("Era para ter salvo mais uma resposta", qAntes + 1,
				qDepois);
	}
	/**
	 * Test of salvarRespostaEscrita method, of class RespostaController.
	 */
	@Test
	public void testSalvarRespostaEscritaCorretaUltimaPerguntaFim() {
		System.out.println("salvarRespostaEscrita");
		participarController.aceitar(testeConvidadoLiberado);
		String resposta = "Esta é minha Resposta";
		int qAntes = escritaRepositoryImpl.findAll().size();
		int perAntes = fluxo.getPerguntasInicio().size();
		fluxo.getProximaPergunta(fluxo.getPerguntasInicio());
		fluxo.getProximaPergunta(fluxo.getPerguntasInicio());
		fluxo.getProximaPergunta(fluxo.getPerguntasInicio());
		System.out.println(fluxo.getPerguntaVez(fluxo.getPerguntasInicio()));
		instance.salvarRespostaEscrita(resposta);
		int qDepois = escritaRepositoryImpl.findAll().size();
		int perDepois = fluxo.getPerguntasInicio().size();
		Assert.assertEquals(perAntes, perDepois + 4);
		Assert.assertEquals("Era para ter salvo mais uma resposta", qAntes+1,
				qDepois);
	}

	/**
	 * Test of salvarRespostaEscrita method, of class RespostaController.
	 */
	@Test
	public void testSalvarRespostaEscritaCorretaFim() {
		System.out.println("salvarRespostaEscrita");
		participarController.aceitar(testeConvidadoLiberado);
		fluxo.setRespondendoInicio(false);
		String resposta = "Esta é minha Resposta";
		int qAntes = escritaRepositoryImpl.findAll().size();
		int perAntes = fluxo.getPerguntasFim().size();
		instance.salvarRespostaEscrita(resposta);
		int qDepois = escritaRepositoryImpl.findAll().size();
		int perDepois = fluxo.getPerguntasFim().size();
		Assert.assertEquals(perAntes, perDepois + 1);
		Assert.assertEquals("Era para ter salvo mais uma resposta", qAntes + 1,
				qDepois);
	}

	/**
	 * Test of salvarRespostaAlternativa method, of class RespostaController.
	 */
	@Test
	public void testSalvarRespostaAlternativaCorreta() {
		System.out.println("salvarRespostaAlternativa");
		participarController.aceitar(testeConvidadoLiberado);
		int perAntes = fluxo.getPerguntasInicio().size();
		int resAntes = respostaAlternativaRepository.findAll().size();
		Alternativa alternativa = new Alternativa();
		alternativa.setId(alternativaPergunta26);
		instance.salvarRespostaAlternativa(alternativa);
		int perDepois = fluxo.getPerguntasInicio().size();
		int resDepois = respostaAlternativaRepository.findAll().size();
		Assert.assertEquals(perAntes, perDepois + 1);
		Assert.assertEquals(resAntes, resDepois - 1);
	}
	@Test
	public void testSalvarRespostaAlternativaCorretaDoFim() {
		System.out.println("salvarRespostaAlternativa");
		participarController.aceitar(testeConvidadoLiberado);
		fluxo.setRespondendoInicio(false);
		fluxo.getProximaPergunta(fluxo.getPerguntasFim());
		int perAntes = fluxo.getPerguntasFim().size();
		int resAntes = respostaAlternativaRepository.findAll().size();
		Alternativa alternativa = new Alternativa();
		alternativa.setId(alternativaPergunta28);
		instance.salvarRespostaAlternativa(alternativa);
		int perDepois = fluxo.getPerguntasFim().size();
		int resDepois = respostaAlternativaRepository.findAll().size();
		Assert.assertEquals(perAntes, perDepois + 1);
		Assert.assertEquals(resAntes, resDepois - 1);
	}

	/**
	 * Test of salvarRespostaAlternativa method, of class RespostaController.
	 */
	@Test
	public void testSalvarRespostaAlternativaPassandoIdInvalido() {
		System.out.println("salvarRespostaAlternativa");
		participarController.aceitar(testeConvidadoLiberado);
		int perAntes = fluxo.getPerguntasInicio().size();
		int resAntes = respostaAlternativaRepository.findAll().size();
		Alternativa alternativa = new Alternativa();
		alternativa.setId(1l);
		List<Message> errors=null;
		try {
			instance.salvarRespostaAlternativa(alternativa);
		} catch (ValidationException validationException) {
			errors = validationException.getErrors();
		}
		Assert.assertEquals("Alternativa não pertence ao teste","pergunta.alternativa.sem.resposta", errors.get(0).getCategory());
		int perDepois = fluxo.getPerguntasInicio().size();
		int resDepois = respostaAlternativaRepository.findAll().size();
		Assert.assertEquals(perAntes, perDepois);
		Assert.assertEquals(resAntes, resDepois);
	}

	/**
	 * Test of exibir method, of class RespostaController.
	 */
	@Test
	public void testExibir() {
		System.out.println("exibir");
		instance.exibir();
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
