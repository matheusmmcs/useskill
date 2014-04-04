package br.ufpi.controllers.procedure;

import javax.persistence.EntityManager;

import br.com.caelum.vraptor.util.test.MockResult;
import br.com.caelum.vraptor.util.test.MockValidator;
import br.ufpi.componets.TesteSessionPlugin;
import br.ufpi.componets.TesteView;
import br.ufpi.componets.UsuarioLogado;
import br.ufpi.componets.ValidateComponente;
import br.ufpi.controllers.TesteParticiparController;
import br.ufpi.repositories.ConvidadoRepository;
import br.ufpi.repositories.FluxoRepository;
import br.ufpi.repositories.RespostaAlternativaRepository;
import br.ufpi.repositories.RespostaEscritaRepository;
import br.ufpi.repositories.UsuarioRepository;
import br.ufpi.repositories.ValorRoteiroRepository;
import br.ufpi.repositories.Implement.FluxoRepositoryImpl;
import br.ufpi.repositories.Implement.RespostaAlternativaRepositoryImpl;
import br.ufpi.repositories.Implement.RespostaEscritaRepositoryImpl;
import br.ufpi.repositories.Implement.ValorRoteiroRepositoryImpl;

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
		TesteSessionPlugin testeSessionPlugin = null;
		FluxoRepository fluxoRepository=new FluxoRepositoryImpl(entityManager);
		RespostaEscritaRepository escritaRepository= new RespostaEscritaRepositoryImpl(entityManager);
		RespostaAlternativaRepository alternativaRepository =new  RespostaAlternativaRepositoryImpl(entityManager);
		ValorRoteiroRepository valorRoteiroRepository = new ValorRoteiroRepositoryImpl(entityManager);
		TesteParticiparController controller= new TesteParticiparController(result, validator, testeView, usuarioLogado, validateComponente, convidadoRepository, testeSessionPlugin, alternativaRepository, escritaRepository, fluxoRepository, valorRoteiroRepository);
				
		return controller;
	}
	public static TesteParticiparController newInstanceTesteController(
			EntityManager entityManager, MockResult result, TesteSessionPlugin testeSessionPlugin) {
		TesteView testeView = new TesteView();
		MockValidator validator = new MockValidator();
		UsuarioRepository usuarioRepositoryImpl = UsuarioTestProcedure
				.newInstanceUsuarioRepository(entityManager);
		UsuarioLogado usuarioLogado = new UsuarioLogado(usuarioRepositoryImpl);
		ValidateComponente validateComponente = new ValidateComponente(
				validator);
		ConvidadoRepository convidadoRepository= UsuarioTestProcedure.newInstanceConvidadoRepository(entityManager);
		FluxoRepository fluxoRepository=new FluxoRepositoryImpl(entityManager);
		RespostaEscritaRepository escritaRepository= new RespostaEscritaRepositoryImpl(entityManager);
		RespostaAlternativaRepository alternativaRepository =new  RespostaAlternativaRepositoryImpl(entityManager);
		ValorRoteiroRepository valorRoteiroRepository = new ValorRoteiroRepositoryImpl(entityManager);
		TesteParticiparController controller= new TesteParticiparController(result, validator, testeView, usuarioLogado, validateComponente, convidadoRepository, testeSessionPlugin, alternativaRepository, escritaRepository, fluxoRepository, valorRoteiroRepository);
		
		return controller;
	}
	
}
