package br.ufpi.repositories;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import br.com.caelum.vraptor.util.test.MockResult;
import br.ufpi.controllers.LoginController;
import br.ufpi.controllers.UsuarioController;
import br.ufpi.controllers.procedure.LoginTestProcedure;
import br.ufpi.controllers.procedure.PerguntaTestProcedure;
import br.ufpi.controllers.procedure.TesteTestProcedure;
import br.ufpi.controllers.procedure.UsuarioTestProcedure;
import br.ufpi.models.Pergunta;
import br.ufpi.models.Teste;
import br.ufpi.models.Usuario;
import br.ufpi.repositories.Implement.ConvidadoRepositoryImpl;

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
		usuarioController.create(UsuarioTestProcedure.newInstaceUsuario(
				entityManager, "Deletar", "deletar@gmail.com", "senha1"));
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

}