package br.ufpi.repositories;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.caelum.vraptor.util.test.MockResult;
import br.ufpi.controllers.LoginController;
import br.ufpi.controllers.UsuarioController;
import br.ufpi.controllers.procedure.LoginTestProcedure;
import br.ufpi.controllers.procedure.PerguntaTestProcedure;
import br.ufpi.controllers.procedure.TarefaTestProcedure;
import br.ufpi.controllers.procedure.TesteTestProcedure;
import br.ufpi.controllers.procedure.UsuarioTestProcedure;
import br.ufpi.models.Convidado;
import br.ufpi.models.Fluxo;
import br.ufpi.models.FluxoIdeal;
import br.ufpi.models.Pergunta;
import br.ufpi.models.Tarefa;
import br.ufpi.models.Teste;
import br.ufpi.models.Usuario;
import br.ufpi.repositories.Implement.ConvidadoRepositoryImpl;
import br.ufpi.repositories.Implement.FluxoRepositoryImpl;
import br.ufpi.repositories.Implement.TarefaRepositoryImpl;

public class Populator {

	public static void userPopulator(EntityManager entityManager) {
		UsuarioController usuarioController = UsuarioTestProcedure
				.newInstanceUsuarioController(entityManager);
		usuarioController.create(UsuarioTestProcedure.newInstaceUsuario(
				entityManager, "cleiton", "cleitonmouraSilveste@gmail.com",
				"senha1"));

		usuarioController.create(UsuarioTestProcedure.newInstaceUsuario(
				entityManager, "cleiton moura", "cleitonmoura18@hotmail.com",
				"senha2"));
		usuarioController.create(UsuarioTestProcedure.newInstaceUsuario(
				entityManager, "clebert", "clebertmoura@gmail.com", "senha1"));
		usuarioController
				.create(UsuarioTestProcedure.newInstaceUsuario(entityManager,
						"claudia", "claudiamoura18@gmail.com", "senha1"));
		usuarioController.create(UsuarioTestProcedure.newInstaceUsuario(
				entityManager, "Maria", "maria@gmail.com", "senha1"));
		List<String> telefones = new ArrayList<String>();
		telefones.add("(86)3227-0468");
		telefones.add("(86)9462-0776");
		telefones.add("(86)3222-2222");
		usuarioController.create(UsuarioTestProcedure.newInstaceUsuario(
				entityManager, "Deletar", "deletar@gmail.com", "senha1",
				telefones));
	}

	public static void validarIncricao(EntityManager entityManager,
			String validarInscricao) {
		LoginController controller = LoginTestProcedure
				.newInstanceUsuarioController(entityManager, new MockResult());
		controller.validarInscricao(validarInscricao);
	}

	public static void testPopulator(EntityManager entityManager) {
		UsuarioRepository usuarioRepository = UsuarioTestProcedure
				.newInstanceUsuarioRepository(entityManager);
		Usuario usuario1 = usuarioRepository.find(1l);
		Usuario usuario2 = usuarioRepository.find(2l);
		Teste teste1 = TesteTestProcedure.newInstanceTeste(
				"Teste 1 de usuario 1", usuario1);
		TesteRepository impl = TesteTestProcedure
				.newInstanceTesteRepository(entityManager);
		Teste teste2 = TesteTestProcedure.newInstanceTeste(
				"Teste 2 de usuario 1", usuario1);
		Teste teste3 = TesteTestProcedure.newInstanceTeste(
				"Teste 3 de usuario 1", usuario2);
		Teste teste4 = TesteTestProcedure.newInstanceTeste(
				"Teste 4 de usuario 1 esta Liberado", usuario1, true);
		;
		Teste teste5 = TesteTestProcedure.newInstanceTeste(
				"Teste 5 de usuario 2", usuario2);
		List<Usuario> uParticipantes = new ArrayList<Usuario>();
		uParticipantes.add(usuario1);
		uParticipantes.add(usuarioRepository.find(5l));
		teste5.setUsuariosParticipantes(uParticipantes);
		Teste teste6 = TesteTestProcedure.newInstanceTeste(
				"Teste 6 de usuario 2", usuario2, true);
		List<Usuario> participantes = new ArrayList<Usuario>();
		participantes.add(usuario1);
		participantes.add(usuarioRepository.find(4l));
		teste4.setUsuariosParticipantes(participantes);
		teste6.setUsuariosParticipantes(participantes);

		impl.create(teste1);
		impl.create(teste2);
		impl.create(teste3);
		impl.create(teste4);

		impl.create(teste5);

		impl.create(teste6);
		testUser7(entityManager);
		testUser8(entityManager);
		testUser9(entityManager);
		testUser10(entityManager);
		testUser11(entityManager);
		testUser12(entityManager);
		testUser13(entityManager);
		testUser14(entityManager);
		testUser15(entityManager);
		testUser16(entityManager);
		Populator.criarPergunta(entityManager, 5l);
	}

	public static void convidarUsuarios(EntityManager entityManager) {
		ConvidadoRepository convidadoRepository = new ConvidadoRepositoryImpl(
				entityManager);
		List<Long> idUsuarios = new ArrayList<Long>();
		idUsuarios.add(1l);
		idUsuarios.add(4l);
		convidadoRepository.convidarUsuarios(idUsuarios, 6l);
		convidadoRepository.convidarUsuarios(idUsuarios, 5l);

	}

	public static void criarTarefa(EntityManager entityManager) {
		TarefaRepository repository = new TarefaRepositoryImpl(entityManager);
		Teste teste = TesteTestProcedure.newInstanceTesteRepository(
				entityManager).find(2l);
		Tarefa tarefa = TarefaTestProcedure.newInstanceTarefa(
				"http://www.globo.com", "Ir no site do Verdão",
				"Procurar Campeão", teste);
		Tarefa tarefa2 = TarefaTestProcedure.newInstanceTarefa(
				"http://www.meionorte.com",
				"visualizar blog do Efrem rsrsrsrs", "Procurar Campeão", teste);
		Tarefa tarefa3 = TarefaTestProcedure.newInstanceTarefa(
				"http://www.cidadeverde.com", "visualizar blog de união",
				"Procurar Campeão", teste);
		repository.create(tarefa);
		repository.create(tarefa2);
		repository.create(tarefa3);
		teste = TesteTestProcedure.newInstanceTesteRepository(entityManager)
				.find(4l);
		Tarefa tarefa4 = TarefaTestProcedure.newInstanceTarefa(
				"http://www.globo.com", "Ir no site do Verdão",
				"Procurar Campeão", teste);
		Tarefa tarefa5 = TarefaTestProcedure.newInstanceTarefa(
				"http://www.meionorte.com",
				"visualizar blog do Efrem rsrsrsrs", "Procurar Campeão", teste);
		Tarefa tarefa6 = TarefaTestProcedure.newInstanceTarefa(
				"http://www.cidadeverde.com", "visualizar blog de união",
				"Procurar Campeão", teste);

		repository.create(tarefa4);
		repository.create(tarefa5);
		repository.create(tarefa6);
	}

	public static void criarPergunta(EntityManager entityManager, Long idTeste) {
		PerguntaRepository perguntaRepository = PerguntaTestProcedure
				.newInstancePerguntaRepository(entityManager);

		Teste teste = TesteTestProcedure.newInstanceTesteRepository(
				entityManager).find(idTeste);
		Pergunta perguntaAlternativa = PerguntaTestProcedure
				.newInstancePerguntaAlternativa(teste, "texto", 5, true,
						"Resposta FIM, Alternativa, TESTE " + idTeste);
		perguntaRepository.create(perguntaAlternativa);
		Pergunta pergunta2 = PerguntaTestProcedure
				.newInstancePerguntaAlternativa(teste, "texto", 2, false,
						"Resposta INICIO, Alternativa, TESTE " + idTeste);
		perguntaRepository.create(pergunta2);
		Pergunta pergunta3 = PerguntaTestProcedure
				.newInstancePerguntaAlternativa(teste, "texto", 4, false,
						"Resposta INICIO, Alternativa, TESTE " + idTeste);
		perguntaRepository.create(pergunta3);
		Pergunta pergunta4 = PerguntaTestProcedure
				.newInstancePerguntaAlternativa(teste, "texto", 5, true,
						"Resposta FIM, Alternativa, TESTE " + idTeste);
		perguntaRepository.create(pergunta4);
		Pergunta pEscritaTeste2 = PerguntaTestProcedure
				.newInstancePerguntaEscrita(teste, "texto", true,
						"Resposta FIM, Escrita, TESTE " + idTeste);
		perguntaRepository.create(pEscritaTeste2);
		Pergunta pEscrita1Teste2 = PerguntaTestProcedure
				.newInstancePerguntaEscrita(teste, "texto", true,
						"Resposta FIM, Escrita, TESTE " + idTeste);
		perguntaRepository.create(pEscrita1Teste2);
		Pergunta pEscrita2Teste2 = PerguntaTestProcedure
				.newInstancePerguntaEscrita(teste, "texto", false,
						"Resposta INICIO, Escrita, TESTE " + idTeste);
		perguntaRepository.create(pEscrita2Teste2);
		Pergunta pEscrita3Teste2 = PerguntaTestProcedure
				.newInstancePerguntaEscrita(teste, "texto", false,
						"Resposta INICIO, Escrita, TESTE " + idTeste);
		perguntaRepository.create(pEscrita3Teste2);
	}

	/**
	 * Criar Teste Do usuario de id 1 com convidados Não liberado Sem tarefas
	 */
	public static void testUser7(EntityManager entityManager) {
		UsuarioRepository usuarioRepository = UsuarioTestProcedure
				.newInstanceUsuarioRepository(entityManager);
		TesteRepository impl = TesteTestProcedure
				.newInstanceTesteRepository(entityManager);
		Usuario usuario1 = usuarioRepository.find(1l);
		Teste teste1 = TesteTestProcedure.newInstanceTeste(
				"Teste 7 de usuario 1 com convidados Não liberado Sem tarefas",
				usuario1);
		impl.create(teste1);
		ConvidadoRepository convidadoRepository = new ConvidadoRepositoryImpl(
				entityManager);
		List<Long> idUsuarios = new ArrayList<Long>();
		idUsuarios.add(5l);
		idUsuarios.add(2l);
		convidadoRepository.convidarUsuarios(idUsuarios, 7l);
	}

	/**
	 * Criar Teste Do usuario de id 1 com convidados Não liberado Com Tarefas e
	 * sem fluxo ideal respondido
	 */
	public static void testUser8(EntityManager entityManager) {
		UsuarioRepository usuarioRepository = UsuarioTestProcedure
				.newInstanceUsuarioRepository(entityManager);
		TesteRepository impl = TesteTestProcedure
				.newInstanceTesteRepository(entityManager);
		Usuario usuario1 = usuarioRepository.find(1l);
		Teste teste = TesteTestProcedure
				.newInstanceTeste(
						"Teste 8 de usuario 1 com convidados Não liberado Com tarefas e sem fluxo Ideal",
						usuario1);
		impl.create(teste);
		ConvidadoRepository convidadoRepository = new ConvidadoRepositoryImpl(
				entityManager);
		List<Long> idUsuarios = new ArrayList<Long>();
		idUsuarios.add(5l);
		idUsuarios.add(2l);
		convidadoRepository.convidarUsuarios(idUsuarios, 8l);
		TarefaRepository repository = new TarefaRepositoryImpl(entityManager);
		Tarefa tarefa = TarefaTestProcedure.newInstanceTarefa(
				"http://www.globo.com", "Ir no site do Verdão",
				"Procurar Campeão", teste);
		Tarefa tarefa2 = TarefaTestProcedure.newInstanceTarefa(
				"http://www.meionorte.com",
				"visualizar blog do Efrem rsrsrsrs", "Procurar Campeão", teste);
		Tarefa tarefa3 = TarefaTestProcedure.newInstanceTarefa(
				"http://www.cidadeverde.com", "visualizar blog de união",
				"Procurar Campeão", teste);
		repository.create(tarefa);
		repository.create(tarefa2);
		repository.create(tarefa3);
	}

	/**
	 * Criar teste Do usuario de id 1 com convidados, Não liberado, Com Tarefas
	 * e com flag fluxo ideal respondido
	 * 
	 */
	public static void testUser9(EntityManager entityManager) {
		UsuarioRepository usuarioRepository = UsuarioTestProcedure
				.newInstanceUsuarioRepository(entityManager);
		TesteRepository impl = TesteTestProcedure
				.newInstanceTesteRepository(entityManager);
		Usuario usuario1 = usuarioRepository.find(1l);
		Teste teste = TesteTestProcedure
				.newInstanceTeste(
						"Teste 9 de usuario 1 com convidados Não liberado Com tarefas e sem fluxo Ideal",
						usuario1);
		impl.create(teste);
		ConvidadoRepository convidadoRepository = new ConvidadoRepositoryImpl(
				entityManager);
		List<Long> idUsuarios = new ArrayList<Long>();
		idUsuarios.add(5l);
		idUsuarios.add(2l);
		convidadoRepository.convidarUsuarios(idUsuarios, 9l);
		TarefaRepository repository = new TarefaRepositoryImpl(entityManager);
		Tarefa tarefa = TarefaTestProcedure.newInstanceTarefa(
				"http://www.globo.com", "Ir no site do Verdão",
				"Procurar Campeão", teste, true);
		Tarefa tarefa2 = TarefaTestProcedure.newInstanceTarefa(
				"http://www.meionorte.com",
				"visualizar blog do Efrem rsrsrsrs", "Procurar Campeão", teste,
				true);
		Tarefa tarefa3 = TarefaTestProcedure.newInstanceTarefa(
				"http://www.cidadeverde.com", "visualizar blog de união",
				"Procurar Campeão", teste, true);
		repository.create(tarefa);
		repository.create(tarefa2);
		repository.create(tarefa3);
	}

	/**
	 * Criar teste Do usuario de id 1 com convidados, Não liberado, Com Tarefas
	 * e com flag fluxo ideal respondido
	 * 
	 */
	public static void testUser10(EntityManager entityManager) {
		Long idTeste = 10l;
		UsuarioRepository usuarioRepository = UsuarioTestProcedure
				.newInstanceUsuarioRepository(entityManager);
		TesteRepository impl = TesteTestProcedure
				.newInstanceTesteRepository(entityManager);
		Usuario usuario1 = usuarioRepository.find(1l);
		Teste teste = TesteTestProcedure
				.newInstanceTeste(
						"Teste 8 de usuario 1 com convidados Não liberado Com tarefas e sem fluxo Ideal",
						usuario1);
		impl.create(teste);
		ConvidadoRepository convidadoRepository = new ConvidadoRepositoryImpl(
				entityManager);
		List<Long> idUsuarios = new ArrayList<Long>();
		idUsuarios.add(5l);
		idUsuarios.add(2l);
		convidadoRepository.convidarUsuarios(idUsuarios, idTeste);

		TarefaRepository repository = new TarefaRepositoryImpl(entityManager);
		Tarefa tarefa = TarefaTestProcedure.newInstanceTarefa(
				"http://www.globo.com", "Ir no site do Verdão",
				"Procurar Campeão", teste, true);
		Tarefa tarefa2 = TarefaTestProcedure.newInstanceTarefa(
				"http://www.meionorte.com",
				"visualizar blog do Efrem rsrsrsrs", "Procurar Campeão", teste,
				true);
		Tarefa tarefa3 = TarefaTestProcedure.newInstanceTarefa(
				"http://www.cidadeverde.com", "visualizar blog de união",
				"Procurar Campeão", teste, true);
		FluxoRepository fluxoRepository= new FluxoRepositoryImpl(entityManager);
		Fluxo fluxo = TarefaTestProcedure.newInstanceFluxo(usuario1);
		fluxoRepository.create(fluxo);
		FluxoIdeal fluxoIdeal = TarefaTestProcedure
				.newInstanceFluxoIdeal(usuario1);
		fluxoIdeal.setFluxo(fluxo);
		tarefa.setFluxoIdeal(fluxoIdeal);
		repository.create(tarefa);
		repository.create(tarefa2);
		repository.create(tarefa3);
		Populator.criarPergunta(entityManager, idTeste);

	}

	/**
	 * Criar teste Do usuario de id 1 com convidados, liberado, Com Tarefas e
	 * com flag fluxo ideal respondido
	 * 
	 */
	public static void testUser11(EntityManager entityManager) {
		Long idTeste = 11l;
		UsuarioRepository usuarioRepository = UsuarioTestProcedure
				.newInstanceUsuarioRepository(entityManager);
		TesteRepository impl = TesteTestProcedure
				.newInstanceTesteRepository(entityManager);
		Usuario usuario1 = usuarioRepository.find(1l);
		Teste teste = TesteTestProcedure
				.newInstanceTeste(
						"Teste 11 de usuario 1 com convidados liberado Com tarefas e sem fluxo Ideal",
						usuario1, true);
		impl.create(teste);
		ConvidadoRepository convidadoRepository = new ConvidadoRepositoryImpl(
				entityManager);
		List<Long> idUsuarios = new ArrayList<Long>();
		idUsuarios.add(5l);
		idUsuarios.add(2l);
		convidadoRepository.convidarUsuarios(idUsuarios, idTeste);

		TarefaRepository repository = new TarefaRepositoryImpl(entityManager);
		Tarefa tarefa = TarefaTestProcedure.newInstanceTarefa(
				"http://www.globo.com", "Ir no site do Verdão",
				"Procurar Campeão", teste, true);
		Tarefa tarefa2 = TarefaTestProcedure.newInstanceTarefa(
				"http://www.meionorte.com",
				"visualizar blog do Efrem rsrsrsrs", "Procurar Campeão", teste,
				true);
		Tarefa tarefa3 = TarefaTestProcedure.newInstanceTarefa(
				"http://www.cidadeverde.com", "visualizar blog de união",
				"Procurar Campeão", teste, true);
		repository.create(tarefa);
		repository.create(tarefa2);
		repository.create(tarefa3);
		Populator.criarPergunta(entityManager, idTeste);
	}

	/**
	 * Criar teste Do usuario de id 1 com convidados, liberado, Com Tarefas e
	 * com flag fluxo ideal respondido
	 * 
	 */
	public static void testUser12(EntityManager entityManager) {
		Long idTeste = 12l;
		UsuarioRepository usuarioRepository = UsuarioTestProcedure
				.newInstanceUsuarioRepository(entityManager);
		TesteRepository impl = TesteTestProcedure
				.newInstanceTesteRepository(entityManager);
		Usuario usuario1 = usuarioRepository.find(1l);
		Teste teste = TesteTestProcedure
				.newInstanceTeste(
						"Teste 11 de usuario 1 com convidados liberado Com tarefas e sem fluxo Ideal",
						usuario1, true);
		impl.create(teste);
		ConvidadoRepository convidadoRepository = new ConvidadoRepositoryImpl(
				entityManager);
		List<Long> idUsuarios = new ArrayList<Long>();
		idUsuarios.add(1l);
		idUsuarios.add(2l);
		convidadoRepository.convidarUsuarios(idUsuarios, idTeste);
		Convidado find = convidadoRepository.find(idTeste, 1l);
		find.setRealizou(true);
		convidadoRepository.update(find);
		TarefaRepository repository = new TarefaRepositoryImpl(entityManager);
		Tarefa tarefa = TarefaTestProcedure.newInstanceTarefa(
				"http://www.globo.com", "Ir no site do Verdão",
				"Procurar Campeão", teste, true);
		Tarefa tarefa2 = TarefaTestProcedure.newInstanceTarefa(
				"http://www.meionorte.com",
				"visualizar blog do Efrem rsrsrsrs", "Procurar Campeão", teste,
				true);
		Tarefa tarefa3 = TarefaTestProcedure.newInstanceTarefa(
				"http://www.cidadeverde.com", "visualizar blog de união",
				"Procurar Campeão", teste, true);
		repository.create(tarefa);
		repository.create(tarefa2);
		repository.create(tarefa3);
		Populator.criarPergunta(entityManager, idTeste);
	}

	public static void testUser13(EntityManager entityManager) {
		Long idTeste = 13l;
		UsuarioRepository usuarioRepository = UsuarioTestProcedure
				.newInstanceUsuarioRepository(entityManager);
		TesteRepository impl = TesteTestProcedure
				.newInstanceTesteRepository(entityManager);
		Usuario usuario1 = usuarioRepository.find(1l);
		Teste teste = TesteTestProcedure
				.newInstanceTeste(
						"Teste 13 de usuario 1 com convidados liberado Com tarefas e sem fluxo Ideal",
						usuario1, true);
		impl.create(teste);
		ConvidadoRepository convidadoRepository = new ConvidadoRepositoryImpl(
				entityManager);
		List<Long> idUsuarios = new ArrayList<Long>();
		idUsuarios.add(1l);
		idUsuarios.add(2l);
		convidadoRepository.convidarUsuarios(idUsuarios, idTeste);
		TarefaRepository repository = new TarefaRepositoryImpl(entityManager);
		Tarefa tarefa = TarefaTestProcedure.newInstanceTarefa(
				"http://www.globo.com", "Ir no site do Verdão",
				"Procurar Campeão", teste, true);
		Tarefa tarefa2 = TarefaTestProcedure.newInstanceTarefa(
				"http://www.meionorte.com",
				"visualizar blog do Efrem rsrsrsrs", "Procurar Campeão", teste,
				true);
		Tarefa tarefa3 = TarefaTestProcedure.newInstanceTarefa(
				"http://www.cidadeverde.com", "visualizar blog de união",
				"Procurar Campeão", teste, true);
		repository.create(tarefa);
		repository.create(tarefa2);
		repository.create(tarefa3);
		Populator.criarPergunta(entityManager, idTeste);
	}

	public static void testUser14(EntityManager entityManager) {
		Long idTeste = 14l;
		UsuarioRepository usuarioRepository = UsuarioTestProcedure
				.newInstanceUsuarioRepository(entityManager);
		TesteRepository impl = TesteTestProcedure
				.newInstanceTesteRepository(entityManager);
		Usuario usuario1 = usuarioRepository.find(2l);
		Teste teste = TesteTestProcedure
				.newInstanceTeste(
						"Teste 14 de usuario 2 com convidados liberado Com tarefas e sem fluxo Ideal",
						usuario1, true);
		impl.create(teste);
		ConvidadoRepository convidadoRepository = new ConvidadoRepositoryImpl(
				entityManager);
		List<Long> idUsuarios = new ArrayList<Long>();
		idUsuarios.add(1l);
		idUsuarios.add(2l);
		convidadoRepository.convidarUsuarios(idUsuarios, idTeste);
		TarefaRepository repository = new TarefaRepositoryImpl(entityManager);
		Tarefa tarefa = TarefaTestProcedure.newInstanceTarefa(
				"http://www.globo.com", "Ir no site do Verdão",
				"Procurar Campeão", teste, true);
		Tarefa tarefa2 = TarefaTestProcedure.newInstanceTarefa(
				"http://www.meionorte.com",
				"visualizar blog do Efrem rsrsrsrs", "Procurar Campeão", teste,
				true);
		Tarefa tarefa3 = TarefaTestProcedure.newInstanceTarefa(
				"http://www.cidadeverde.com", "visualizar blog de união",
				"Procurar Campeão", teste, true);
		FluxoRepository fluxoRepository= new FluxoRepositoryImpl(entityManager);
		Fluxo fluxo = TarefaTestProcedure.newInstanceFluxo(usuario1);
		fluxoRepository.create(fluxo);
		FluxoIdeal fluxoIdeal = TarefaTestProcedure
		.newInstanceFluxoIdeal(usuario1);
		fluxoIdeal.setFluxo(fluxo);
		tarefa.setFluxoIdeal(fluxoIdeal);
		repository.create(tarefa);
		repository.create(tarefa2);
		repository.create(tarefa3);
		Populator.criarPergunta(entityManager, idTeste);
	}
	public static void testUser15(EntityManager entityManager) {
		Long idTeste = 15l;
		UsuarioRepository usuarioRepository = UsuarioTestProcedure
				.newInstanceUsuarioRepository(entityManager);
		TesteRepository impl = TesteTestProcedure
				.newInstanceTesteRepository(entityManager);
		Usuario usuario1 = usuarioRepository.find(1l);
		Teste teste = TesteTestProcedure
				.newInstanceTeste(
						"Teste 14 de usuario 2 com convidados liberado Com tarefas e sem fluxo Ideal",
						usuario1, true);
		impl.create(teste);
		ConvidadoRepository convidadoRepository = new ConvidadoRepositoryImpl(
				entityManager);
		List<Long> idUsuarios = new ArrayList<Long>();
		idUsuarios.add(1l);
		idUsuarios.add(2l);
		convidadoRepository.convidarUsuarios(idUsuarios, idTeste);
		TarefaRepository repository = new TarefaRepositoryImpl(entityManager);
		Tarefa tarefa = TarefaTestProcedure.newInstanceTarefa(
				"http://www.globo.com", "Ir no site do Verdão",
				"Procurar Campeão", teste, true);
		Tarefa tarefa2 = TarefaTestProcedure.newInstanceTarefa(
				"http://www.meionorte.com",
				"visualizar blog do Efrem rsrsrsrs", "Procurar Campeão", teste,
				true);
		Tarefa tarefa3 = TarefaTestProcedure.newInstanceTarefa(
				"http://www.cidadeverde.com", "visualizar blog de união",
				"Procurar Campeão", teste, true);
		FluxoRepository fluxoRepository= new FluxoRepositoryImpl(entityManager);
		Fluxo fluxo = TarefaTestProcedure.newInstanceFluxo(usuario1);
		fluxoRepository.create(fluxo);
		FluxoIdeal fluxoIdeal = TarefaTestProcedure
				.newInstanceFluxoIdeal(usuario1);
		fluxoIdeal.setFluxo(fluxo);
		tarefa.setFluxoIdeal(fluxoIdeal);
		repository.create(tarefa);
		repository.create(tarefa2);
		repository.create(tarefa3);
		Populator.criarPergunta(entityManager, idTeste);
	}
	public static void testUser16(EntityManager entityManager) {
		Long idTeste = 16l;
		UsuarioRepository usuarioRepository = UsuarioTestProcedure
				.newInstanceUsuarioRepository(entityManager);
		TesteRepository impl = TesteTestProcedure
				.newInstanceTesteRepository(entityManager);
		Usuario usuario1 = usuarioRepository.find(1l);
		Teste teste = TesteTestProcedure
				.newInstanceTeste(
						"Teste 16 de usuario 2 com convidados liberado Com tarefas e sem fluxo Ideal",
						usuario1, false);
		impl.create(teste);
		ConvidadoRepository convidadoRepository = new ConvidadoRepositoryImpl(
				entityManager);
		List<Long> idUsuarios = new ArrayList<Long>();
		idUsuarios.add(1l);
		idUsuarios.add(2l);
		convidadoRepository.convidarUsuarios(idUsuarios, idTeste);
		TarefaRepository repository = new TarefaRepositoryImpl(entityManager);
		Tarefa tarefa = TarefaTestProcedure.newInstanceTarefa(
				"http://www.globo.com", "Ir no site do Verdão",
				"Procurar Campeão", teste, true);
		Tarefa tarefa2 = TarefaTestProcedure.newInstanceTarefa(
				"http://www.meionorte.com",
				"visualizar blog do Efrem rsrsrsrs", "Procurar Campeão", teste,
				true);
		Tarefa tarefa3 = TarefaTestProcedure.newInstanceTarefa(
				"http://www.cidadeverde.com", "visualizar blog de união",
				"Procurar Campeão", teste, true);
		FluxoRepository fluxoRepository= new FluxoRepositoryImpl(entityManager);
		Fluxo fluxo = TarefaTestProcedure.newInstanceFluxo(usuario1);
		fluxoRepository.create(fluxo);
		FluxoIdeal fluxoIdeal = TarefaTestProcedure
				.newInstanceFluxoIdeal(usuario1);
		fluxoIdeal.setFluxo(fluxo);
		tarefa.setFluxoIdeal(fluxoIdeal);
		repository.create(tarefa);
		repository.create(tarefa2);
		repository.create(tarefa3);
		Populator.criarPergunta(entityManager, idTeste);
	}
}