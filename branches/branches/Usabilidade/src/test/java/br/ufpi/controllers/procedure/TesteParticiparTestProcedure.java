package br.ufpi.controllers.procedure;

import javax.persistence.EntityManager;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.ufpi.componets.FluxoComponente;
import br.ufpi.componets.TesteSession;
import br.ufpi.componets.TesteView;
import br.ufpi.componets.UsuarioLogado;
import br.ufpi.componets.ValidateComponente;
import br.ufpi.controllers.TesteParticiparController;
import br.ufpi.repositories.ConvidadoRepository;
import br.ufpi.repositories.UsuarioRepository;

public class TesteParticiparTestProcedure {
	public static TesteParticiparController newInstanceTesteController(
			EntityManager entityManager, MockResult result) {
		TesteView testeView = new TesteView();
		MockValidator validator = new MockValidator();
		UsuarioRepository usuarioRepositoryImpl = UsuarioTestProcedure
				.newInstanceUsuarioRepository(entityManager);
		UsuarioLogado usuarioLogado = new UsuarioLogado(usuarioRepositoryImpl);
		ValidateComponente validateComponente = new ValidateComponente(
				validator);
		ConvidadoRepository convidadoRepository= UsuarioTestProcedure.newInstanceConvidadoRepository(entityManager);
		TesteParticiparController controller= new TesteParticiparController(result, validator, testeView, usuarioLogado, validateComponente, convidadoRepository,null,null);
				
		return controller;
	}
	public static TesteParticiparController newInstanceTesteController(
			EntityManager entityManager, MockResult result, TesteSession testeSession, FluxoComponente fluxo) {
		TesteView testeView = new TesteView();
		MockValidator validator = new MockValidator();
		UsuarioRepository usuarioRepositoryImpl = UsuarioTestProcedure
				.newInstanceUsuarioRepository(entityManager);
		UsuarioLogado usuarioLogado = new UsuarioLogado(usuarioRepositoryImpl);
		ValidateComponente validateComponente = new ValidateComponente(
				validator);
		ConvidadoRepository convidadoRepository= UsuarioTestProcedure.newInstanceConvidadoRepository(entityManager);
		TesteParticiparController controller= new TesteParticiparController(result, validator, testeView, usuarioLogado, validateComponente, convidadoRepository,fluxo,testeSession);
		
		return controller;
	}
	
}
