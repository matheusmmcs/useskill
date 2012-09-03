/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpi.controllers;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.caelum.vraptor.util.test.MockSerializationResult;
import br.com.caelum.vraptor.validator.Message;
import br.com.caelum.vraptor.validator.ValidationException;
import br.ufpi.componets.TesteSessionPlugin;
import br.ufpi.controllers.procedure.PerguntaTestProcedure;
import br.ufpi.controllers.procedure.TesteTestProcedure;
import br.ufpi.models.Pergunta;
import br.ufpi.models.Teste;
import br.ufpi.repositories.AbstractDaoTest;
import br.ufpi.repositories.AlternativaRepository;
import br.ufpi.repositories.PerguntaRepository;
import br.ufpi.repositories.Implement.AlternativaRepositoryImpl;

/**
 * 
 * @author Cleiton Casos de Teste Para salvar uma pergunta</br> <li>Caso de
 *         Sucesso salvar pergunta Objetiva</li> <li>Caso de Sucesso salvar
 *         pergunta Subjetiva</li> <li>Usuario não é dono do teste</li> <li>
 *         Teste esta Liberado então não pode salvar</li> <li>Id do teste null</li>
 *         <li>Tentar Salvar pergunta que já possua um id</li>
 * 
 *         Caso de Teste Remover Pergunta</br> <li>Caso de Sucesso remover
 *         pergunta Objetiva</li> <li>Caso de Sucesso remover pergunta Subjetiva
 *         </li> <li>Usuario não é dono do teste</li> <li>Teste esta Liberado
 *         então não pode remover</li> <li>Id do teste null</li><li >remover
 *         pergunta que nao pertence ao id do teste passado</li>
 * 
 *         Caso de Teste Atualizar Pergunta</br> <li>Atualizar Pergunta com id
 *         vazio</li> <li>Caso de Sucesso atualizar pergunta Objetiva</li> <li>
 *         Usuario não é dono do teste</li> <li>Teste esta Liberado então não
 *         pode remover</li> <li>Id do teste null</li> FALTANDO <li >remover
 *         pergunta que nao pertence ao id do teste passado</li>
 * 
 */
public class PerguntaControllerTest extends AbstractDaoTest {
	private PerguntaRepository repository;
	private PerguntaController instance;
	private Long testePertenceUsuarioNaoLiberado = 10l;
	private Long testeNaoPertenceUsuario = 5l;
	private Long testeLiberado = 11l;

	@Before
	public void setUp() throws Exception {
		super.setUp();
		repository = PerguntaTestProcedure
				.newInstancePerguntaRepository(entityManager);
		instance = PerguntaTestProcedure.newInstancePerguntaController(
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
		pergunta.setId(200l);
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
	 * Test of salvarPergunta method, of class PerguntaController. Caso de
	 * Sucesso salvar pergunta Subjetiva
	 */
	@Test
	public void testSalvarPerguntaEscritaSucesso() {
		System.out.println("salvarPergunta");
		int qAntes = repository.findAll().size();
		Pergunta pergunta = PerguntaTestProcedure.newInstancePerguntaEscrita(
				"O que vc achou do site?", true, "Pergunta2");
		instance.salvarPergunta(testePertenceUsuarioNaoLiberado, pergunta);
		int qDepois = repository.findAll().size();
		Assert.assertEquals("Deveria ter uma pergunta a mais", qAntes + 1,
				qDepois);
	}

	/**
	 * Test of salvarPergunta method, of class PerguntaController. Caso de
	 * Sucesso salvar pergunta Subjetiva
	 */
	@Test
	public void testSalvarPerguntaSubjetivaSucesso() {
		AlternativaRepository alternativaRepository = new AlternativaRepositoryImpl(
				entityManager);
		int qAntesAlternativas = alternativaRepository.findAll().size();
		System.out.println("salvarPergunta");
		int qAntes = repository.findAll().size();
		int numeroAlternativas = 3;
		Pergunta pergunta = PerguntaTestProcedure
				.newInstancePerguntaAlternativa("O que vc achou do site?",
						numeroAlternativas, true, "Pergunta2");
		instance.salvarPergunta(testePertenceUsuarioNaoLiberado, pergunta);
		int qDepois = repository.findAll().size();
		int qDepoisAlternativas = alternativaRepository.findAll().size();
		Assert.assertEquals("Deveria ter mais 3 alternativas",
				qAntesAlternativas + numeroAlternativas, qDepoisAlternativas);
		Assert.assertEquals("Deveria ter uma pergunta a mais", qAntes + 1,
				qDepois);
	}

	/**
	 * Test of salvarPergunta method, of class PerguntaController. Caso de
	 * Usuario não é dono do teste
	 */
	@Test
	public void testSalvarPerguntaUsuarioNaoEDono() {
		AlternativaRepository alternativaRepository = new AlternativaRepositoryImpl(
				entityManager);
		int qAntesAlternativas = alternativaRepository.findAll().size();
		System.out.println("salvarPergunta");
		int qAntes = repository.findAll().size();
		int numeroAlternativas = 3;
		Pergunta pergunta = PerguntaTestProcedure
				.newInstancePerguntaAlternativa("O que vc achou do site?",
						numeroAlternativas, true, "Pergunta2");
		List<Message> errors = null;
		try {

			instance.salvarPergunta(testeNaoPertenceUsuario, pergunta);
		} catch (ValidationException validationException) {
			errors = validationException.getErrors();
		}
		Assert.assertEquals("campo.form.alterado", errors.get(0).getCategory());
		int qDepois = repository.findAll().size();
		int qDepoisAlternativas = alternativaRepository.findAll().size();
		Assert.assertEquals("Deveria ter mais 3 alternativas",
				qAntesAlternativas, qDepoisAlternativas);
		Assert.assertEquals("Deveria ter uma pergunta a mais", qAntes, qDepois);
	}

	/**
	 * Test of salvarPergunta method, of class PerguntaController. Teste esta
	 * Liberado então não pode salvar
	 */
	@Test
	public void testSalvarPerguntatesteLiberado() {
		AlternativaRepository alternativaRepository = new AlternativaRepositoryImpl(
				entityManager);
		int qAntesAlternativas = alternativaRepository.findAll().size();
		System.out.println("salvarPergunta");
		int qAntes = repository.findAll().size();
		int numeroAlternativas = 3;
		Pergunta pergunta = PerguntaTestProcedure
				.newInstancePerguntaAlternativa("O que vc achou do site?",
						numeroAlternativas, true, "Pergunta2");
		List<Message> errors = null;
		try {

			instance.salvarPergunta(testeLiberado, pergunta);
		} catch (ValidationException validationException) {
			errors = validationException.getErrors();
		}
		Assert.assertEquals("campo.form.alterado", errors.get(0).getCategory());
		int qDepois = repository.findAll().size();
		int qDepoisAlternativas = alternativaRepository.findAll().size();
		Assert.assertEquals("Deveria ter mais 3 alternativas",
				qAntesAlternativas, qDepoisAlternativas);
		Assert.assertEquals("Deveria ter uma pergunta a mais", qAntes, qDepois);
	}

	/**
	 * Test of salvarPergunta method, of class PerguntaController. Teste esta
	 * Liberado então não pode salvar
	 */
	@Test
	public void testSalvarPerguntatesteIdNulo() {
		AlternativaRepository alternativaRepository = new AlternativaRepositoryImpl(
				entityManager);
		int qAntesAlternativas = alternativaRepository.findAll().size();
		System.out.println("salvarPergunta");
		int qAntes = repository.findAll().size();
		int numeroAlternativas = 3;
		Pergunta pergunta = PerguntaTestProcedure
				.newInstancePerguntaAlternativa("O que vc achou do site?",
						numeroAlternativas, true, "Pergunta2");
		List<Message> errors = null;
		try {

			instance.salvarPergunta(null, pergunta);
		} catch (ValidationException validationException) {
			errors = validationException.getErrors();
		}
		Assert.assertEquals("campo.form.alterado", errors.get(0).getCategory());
		int qDepois = repository.findAll().size();
		int qDepoisAlternativas = alternativaRepository.findAll().size();
		Assert.assertEquals("Deveria ter mais 3 alternativas",
				qAntesAlternativas, qDepoisAlternativas);
		Assert.assertEquals("Deveria ter uma pergunta a mais", qAntes, qDepois);
	}

	/**
	 * Test of salvarPergunta method, of class PerguntaController. Pergunta
	 * passando o id
	 */
	@Test
	public void testSalvarPerguntaComID() {
		AlternativaRepository alternativaRepository = new AlternativaRepositoryImpl(
				entityManager);
		int qAntesAlternativas = alternativaRepository.findAll().size();
		System.out.println("salvarPergunta");
		int qAntes = repository.findAll().size();
		int numeroAlternativas = 3;
		Pergunta pergunta = PerguntaTestProcedure
				.newInstancePerguntaAlternativa("O que vc achou do site?",
						numeroAlternativas, true, "Pergunta2");
		pergunta.setId(2l);

		List<Message> errors = null;
		try {
			instance.salvarPergunta(testePertenceUsuarioNaoLiberado, pergunta);
		} catch (ValidationException validationException) {
			errors = validationException.getErrors();
		}
		Assert.assertEquals("campo.form.alterado", errors.get(0).getCategory());
		int qDepois = repository.findAll().size();
		int qDepoisAlternativas = alternativaRepository.findAll().size();
		Assert.assertEquals("Deveria ter mais 3 alternativas",
				qAntesAlternativas, qDepoisAlternativas);
		Assert.assertEquals("Deveria ter uma pergunta a mais", qAntes, qDepois);
	}

	/**
	 * Test of deletarPergunta method, of class PerguntaController. Sucesso
	 * remover pergunta Objetiva
	 */
	@Test
	public void testDeletarPerguntaObjetiva() {
		System.out.println("deletarPerguntaObjetiva");
		Teste teste = TesteTestProcedure.newInstanceTesteRepository(
				entityManager).find(testePertenceUsuarioNaoLiberado);
		List<Pergunta> perguntas = teste.getSatisfacao().getPerguntas();

		Long perguntaId = null;
		sairDoFor: for (Pergunta pergunta : perguntas) {
			if (pergunta.getTipoRespostaAlternativa()) {
				perguntaId = pergunta.getId();
				break sairDoFor;
			}
		}
		instance.deletarPergunta(testePertenceUsuarioNaoLiberado, perguntaId);
		Assert.assertNull("Pergunta era para ter sido removida",
				repository.find(perguntaId));
	}

	/**
	 * Test of deletarPergunta method, of class PerguntaController. Sucesso
	 * remover pergunta Subjetiva
	 */
	@Test
	public void testDeletarPerguntaSubjetiva() {
		System.out.println("deletarPerguntaSubjetiva");
		Teste teste = TesteTestProcedure.newInstanceTesteRepository(
				entityManager).find(testePertenceUsuarioNaoLiberado);
		List<Pergunta> perguntas = teste.getSatisfacao().getPerguntas();

		Long perguntaId = null;
		sairDoFor: for (Pergunta pergunta : perguntas) {
			System.out.println(pergunta.getId() + "Tipo de resposta"
					+ pergunta.getTipoRespostaAlternativa());
			if (!pergunta.getTipoRespostaAlternativa()) {
				perguntaId = pergunta.getId();
				break sairDoFor;
			}
		}
		instance.deletarPergunta(testePertenceUsuarioNaoLiberado, perguntaId);
		Assert.assertNull("Pergunta era para ter sido removida",
				repository.find(perguntaId));
	}

	/**
	 * Test of deletarPergunta method, of class PerguntaController. Erro
	 * Pergunta não pertence ao usuario
	 */
	@Test
	public void testDeletarPerguntaUsuarioNaoEDono() {
		System.out.println("DeletarPerguntaUsuarioNaoEDono");
		Teste teste = TesteTestProcedure.newInstanceTesteRepository(
				entityManager).find(testeNaoPertenceUsuario);
		List<Pergunta> perguntas = teste.getSatisfacao().getPerguntas();

		Long perguntaId = null;
		sairDoFor: for (Pergunta pergunta : perguntas) {
			System.out.println(pergunta.getId() + "Tipo de resposta"
					+ pergunta.getTipoRespostaAlternativa());
			if (!pergunta.getTipoRespostaAlternativa()) {
				perguntaId = pergunta.getId();
				break sairDoFor;
			}
		}
		List<Message> errors = null;
		try {
			instance.deletarPergunta(testeNaoPertenceUsuario, perguntaId);
		} catch (ValidationException validationException) {
			errors = validationException.getErrors();
		}
		Assert.assertEquals("campo.form.alterado", errors.get(0).getCategory());
		Assert.assertNotNull("Pergunta era para ter sido removida",
				repository.find(perguntaId));
	}

	/**
	 * Test of deletarPergunta method, of class PerguntaController. Remover
	 * pergunta de teste já liberado, não vai da certo remover
	 */
	@Test
	public void testDeletarPerguntaTesteJaLiberado() {
		System.out.println("DeletarPerguntaUsuarioNaoEDono");
		Teste teste = TesteTestProcedure.newInstanceTesteRepository(
				entityManager).find(testeLiberado);
		List<Pergunta> perguntas = teste.getSatisfacao().getPerguntas();

		Long perguntaId = null;
		sairDoFor: for (Pergunta pergunta : perguntas) {
			System.out.println(pergunta.getId() + "Tipo de resposta"
					+ pergunta.getTipoRespostaAlternativa());
			if (!pergunta.getTipoRespostaAlternativa()) {
				perguntaId = pergunta.getId();
				break sairDoFor;
			}
		}
		List<Message> errors = null;
		try {
			instance.deletarPergunta(testeLiberado, perguntaId);
		} catch (ValidationException validationException) {
			errors = validationException.getErrors();
		}
		Assert.assertEquals("campo.form.alterado", errors.get(0).getCategory());
		Assert.assertNotNull("Pergunta era para ter sido removida",
				repository.find(perguntaId));
	}

	/**
	 * Test of deletarPergunta method, of class PerguntaController. Remover
	 * pergunta de teste passando o id do teste igual a nulo
	 */
	@Test
	public void testDeletarPerguntaTesteIDNulo() {
		System.out.println("DeletarPerguntaUsuarioNaoEDono");
		Teste teste = TesteTestProcedure.newInstanceTesteRepository(
				entityManager).find(testeLiberado);
		List<Pergunta> perguntas = teste.getSatisfacao().getPerguntas();

		Long perguntaId = null;
		sairDoFor: for (Pergunta pergunta : perguntas) {
			System.out.println(pergunta.getId() + "Tipo de resposta"
					+ pergunta.getTipoRespostaAlternativa());
			if (!pergunta.getTipoRespostaAlternativa()) {
				perguntaId = pergunta.getId();
				break sairDoFor;
			}
		}
		List<Message> errors = null;
		try {
			instance.deletarPergunta(null, perguntaId);
		} catch (ValidationException validationException) {
			errors = validationException.getErrors();
		}
		Assert.assertEquals("campo.form.alterado", errors.get(0).getCategory());
		Assert.assertNotNull("Pergunta era para ter sido removida",
				repository.find(perguntaId));
	}

	/**
	 * Test of deletarPergunta method, of class PerguntaController. Remover
	 * pergunta de teste passando o id do teste diferente ao id do teste que ela
	 * pertence
	 */
	@Test
	public void testDeletarPerguntaIDNaoPertenceAoIdDoTeste() {
		System.out.println("DeletarPerguntaUsuarioNaoEDono");
		Teste teste = TesteTestProcedure.newInstanceTesteRepository(
				entityManager).find(4l);
		List<Pergunta> perguntas = teste.getSatisfacao().getPerguntas();

		Long perguntaId = null;
		sairDoFor: for (Pergunta pergunta : perguntas) {
			if (!pergunta.getTipoRespostaAlternativa()) {
				perguntaId = pergunta.getId();
				break sairDoFor;
			}
		}
		List<Message> errors = null;
		try {
			instance.deletarPergunta(testePertenceUsuarioNaoLiberado,
					perguntaId);
		} catch (ValidationException validationException) {
			errors = validationException.getErrors();
		}
		Assert.assertEquals("campo.form.alterado", errors.get(0).getCategory());
		Assert.assertNotNull("Pergunta era para ter sido removida",
				repository.find(perguntaId));
	}

	/**
	 * Test of atualizarPergunta method, of class PerguntaController.
	 */
	@Test
	public void testAtualizarPerguntaIdVazio() {
		System.out.println("atualizarPergunta");
		getPerguntaID(testePertenceUsuarioNaoLiberado, true);
		Pergunta pergunta = getPerguntaView(true);
		List<Message> errors = null;
		try {
			instance.atualizarPergunta(testePertenceUsuarioNaoLiberado,
					pergunta);
		} catch (ValidationException validationException) {
			errors = validationException.getErrors();
		}
		Assert.assertEquals("campo.form.alterado", errors.get(0).getCategory());
	}

	/**
	 * Test of atualizarPergunta method, of class PerguntaController.
	 */
	@Test
	public void testAtualizarPeterguntaObjetiva() {
		System.out.println("atualizarPergunta");
		Long perguntaID = getPerguntaID(testePertenceUsuarioNaoLiberado, true);
		Pergunta pergunta = getPerguntaView(true);
		pergunta.setId(perguntaID);
		int qAntes = sizeAlternativas(perguntaID);
		instance.atualizarPergunta(testePertenceUsuarioNaoLiberado, pergunta);
		int qDepois = sizeAlternativas(perguntaID);
		Assert.assertEquals(
				"Antes a pergunta so possui 5 alternativas agora possui 4",
				qAntes, qDepois + 1);
	}

	/**
	 * Test of atualizarPergunta method, of class PerguntaController.
	 */
	@Test
	public void testAtualizarPeterguntaSubjetiva() {
		System.out.println("atualizarPergunta");
		Long perguntaID = getPerguntaID(testePertenceUsuarioNaoLiberado, true);
		Pergunta pergunta = getPerguntaView(false);
		AlternativaRepositoryImpl alternativaRepositoryImpl = new AlternativaRepositoryImpl(
				entityManager);
		int qAntes = alternativaRepositoryImpl.findAll().size();
		pergunta.setId(perguntaID);
		instance.atualizarPergunta(testePertenceUsuarioNaoLiberado, pergunta);
		int qDepois = alternativaRepositoryImpl.findAll().size();
		Assert.assertEquals("Não era para possui mais alternativas",0, repository
				.find(perguntaID).getAlternativas().size());

		Assert.assertEquals("Era para possuir menos 5 alternativas",
				qAntes - 5, qDepois);
	}

	/**
	 * Test of atualizarPergunta method, of class PerguntaController.
	 */
	@Test
	public void testAtualizarPeterguntaUsuarioNaoEDonoTeste() {
		System.out.println("atualizarPergunta");
		Long perguntaID = getPerguntaID(testeNaoPertenceUsuario, true);
		Pergunta pergunta = getPerguntaView(false);
		AlternativaRepositoryImpl alternativaRepositoryImpl = new AlternativaRepositoryImpl(
				entityManager);
		int qAntes = alternativaRepositoryImpl.findAll().size();
		pergunta.setId(perguntaID);
		List<Message> errors = null;
		try {
			instance.atualizarPergunta(testeNaoPertenceUsuario, pergunta);
		} catch (ValidationException validationException) {
			errors = validationException.getErrors();
		}
		Assert.assertEquals("campo.form.alterado", errors.get(0).getCategory());
		int qDepois = alternativaRepositoryImpl.findAll().size();

		Assert.assertEquals(
				"E pra possui o mesmo tanto pois não foi alterado nada",
				qAntes, qDepois);
	}

	/**
	 * Test of atualizarPergunta method, of class PerguntaController.
	 */
	@Test
	public void testAtualizarPerguntaTesteJaLiberado() {
		System.out.println("atualizarPergunta");
		Long perguntaID = getPerguntaID(testeLiberado, true);
		Pergunta pergunta = getPerguntaView(false);
		AlternativaRepositoryImpl alternativaRepositoryImpl = new AlternativaRepositoryImpl(
				entityManager);
		int qAntes = alternativaRepositoryImpl.findAll().size();
		pergunta.setId(perguntaID);
		List<Message> errors = null;
		try {
			instance.atualizarPergunta(testeLiberado, pergunta);
		} catch (ValidationException validationException) {
			errors = validationException.getErrors();
		}
		Assert.assertEquals("campo.form.alterado", errors.get(0).getCategory());
		int qDepois = alternativaRepositoryImpl.findAll().size();

		Assert.assertEquals(
				"E pra possui o mesmo tanto pois não foi alterado nada",
				qAntes, qDepois);
	}

	/**
	 * Test of atualizarPergunta method, of class PerguntaController.
	 */
	@Test
	public void testAtualizarPerguntaTesteComIDNulo() {
		System.out.println("atualizarPergunta");
		Long perguntaID = getPerguntaID(testeLiberado, true);
		Pergunta pergunta = getPerguntaView(false);
		AlternativaRepositoryImpl alternativaRepositoryImpl = new AlternativaRepositoryImpl(
				entityManager);
		int qAntes = alternativaRepositoryImpl.findAll().size();
		pergunta.setId(perguntaID);
		List<Message> errors = null;
		try {
			instance.atualizarPergunta(null, pergunta);
		} catch (ValidationException validationException) {
			errors = validationException.getErrors();
		}
		Assert.assertEquals("campo.form.alterado", errors.get(0).getCategory());
		int qDepois = alternativaRepositoryImpl.findAll().size();

		Assert.assertEquals(
				"E pra possui o mesmo tanto pois não foi alterado nada",
				qAntes, qDepois);
	}

	/**
	 * Test of atualizarPergunta method, of class PerguntaController.
	 */
	@Test
	public void testAtualizarPerguntaTesteComIDNaoPertenceTeste() {
		System.out.println("atualizarPergunta");
		Long perguntaID = getPerguntaID(testeNaoPertenceUsuario, true);
		Pergunta pergunta = getPerguntaView(false);
		AlternativaRepositoryImpl alternativaRepositoryImpl = new AlternativaRepositoryImpl(
				entityManager);
		int qAntes = alternativaRepositoryImpl.findAll().size();
		pergunta.setId(perguntaID);
		List<Message> errors = null;
		try {
			instance.atualizarPergunta(testePertenceUsuarioNaoLiberado,
					pergunta);
		} catch (ValidationException validationException) {
			errors = validationException.getErrors();
		}
		Assert.assertEquals("campo.form.alterado", errors.get(0).getCategory());
		int qDepois = alternativaRepositoryImpl.findAll().size();

		Assert.assertEquals(
				"E pra possui o mesmo tanto pois não foi alterado nada",
				qAntes, qDepois);
	}

	private Long getPerguntaID(Long idTeste, boolean objetiva) {
		Teste teste = TesteTestProcedure.newInstanceTesteRepository(
				entityManager).find(idTeste);
		List<Pergunta> perguntas = teste.getSatisfacao().getPerguntas();
		if (objetiva) {
			for (Pergunta pergunta : perguntas) {
				if (pergunta.getTipoRespostaAlternativa()) {
					return pergunta.getId();
				}
			}
		} else {
			for (Pergunta pergunta : perguntas) {
				if (!pergunta.getTipoRespostaAlternativa()) {
					return pergunta.getId();
				}
			}

		}
		return null;
	}

	@Test
	public void testGetPergunta() throws Exception {
		long idPergunta = 10;
		MockSerializationResult result = new MockSerializationResult();
		instance = PerguntaTestProcedure.newInstancePerguntaController(
				entityManager, result, getTesteSessionPlugin(testeLiberado));
		instance.getPergunta(idPergunta);
		System.out.println(result.serializedResult());
	}

	private Pergunta getPerguntaView(boolean objetiva) {
		Pergunta pergunta;
		if (!objetiva)
			pergunta = PerguntaTestProcedure.newInstancePerguntaEscrita(
					"Pergunta modificada", false, "Pergunta modificada");

		else
			pergunta = PerguntaTestProcedure.newInstancePerguntaAlternativa(
					"Pergunta bla bla bla", 4, false, "titulo");
		return pergunta;
	}

	private int sizeAlternativas(Long idpergunta) {
		return repository.find(idpergunta).getAlternativas().size();
	}

	private static TesteSessionPlugin getTesteSessionPlugin(Long idTeste) {
		TesteSessionPlugin plugin = new TesteSessionPlugin();
		plugin.setIdTeste(idTeste);
		return plugin;
	}

}
