package br.ufpi.datamining.controllers;

import java.util.ArrayList;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.core.Localization;
import br.ufpi.annotation.Logado;
import br.ufpi.componets.TesteView;
import br.ufpi.componets.UsuarioLogado;
import br.ufpi.componets.ValidateComponente;
import br.ufpi.controllers.BaseController;
import br.ufpi.datamining.models.TestDataMining;
import br.ufpi.datamining.repositories.TestDataMiningRepository;
import br.ufpi.models.Usuario;

@Path(value = "datamining")
@Resource
public class DataMiningTaskController extends BaseController {
	
	private final TestDataMiningRepository testeDataMiningRepository;
	private final Localization localization;
	
	public DataMiningTaskController(Result result, Validator validator,
			TesteView testeView, UsuarioLogado usuarioLogado,
			ValidateComponente validateComponente,
			Localization localization,
			TestDataMiningRepository testeDataMiningRepository) {
		super(result, validator, testeView, usuarioLogado, validateComponente);
		this.testeDataMiningRepository = testeDataMiningRepository;
		this.localization = localization; 
	}
	
//	@Get("/testes/{idTeste}/tarefas/{idTarefa}")
//	@Logado
//	public void view(Long idTeste, Long idTarefa) {
//		TestDataMining testPertencente = testeDataMiningRepository.getTestPertencente(usuarioLogado.getUsuario().getId(), idTeste);
//		validateComponente.validarNotNull(testPertencente, "datamining.accessdenied");
//		validator.onErrorRedirectTo(this).list();
//		result.include("test", testPertencente);
//	}
	
	/*
	@Get("/testes/{idTeste}/tarefas/criar")
	@Logado
	public void create() {
		result.include("title", localization.getMessage("datamining.tasks.new"));
	}
	
	@Get("/testes/{idTeste}/tarefas/editar/{idTeste}")
	@Logado
	public void edit(Long idTeste) {
		TestDataMining testPertencente = testeDataMiningRepository.getTestPertencente(usuarioLogado.getUsuario().getId(), idTeste);
		validateComponente.validarNotNull(testPertencente, "datamining.accessdenied");
		validator.onErrorRedirectTo(this).list();
		result.include("test", testPertencente);
		result.include("edit", true);
		
		result.include("title", localization.getMessage("datamining.testes.edit"));
		result.of(this).create();
	}
	
	@Post("/testes/{idTeste}/tarefas/salvar")
	@Logado
	public void salvar(TestDataMining test) {
		validateComponente.validarString(test.getTitle(), "datamining.testes.title");
		validateComponente.validarString(test.getClientAbbreviation(), "datamining.testes.abbrev");
		validateComponente.validarURL(test.getUrlSystem());
		
		if(test.getId() != null){
			validator.onErrorRedirectTo(this).edit(test.getId());
			
			TestDataMining testUpdate = testeDataMiningRepository.getTestPertencente(usuarioLogado.getUsuario().getId(), test.getId());
			testUpdate.setTitle(test.getTitle());
			testUpdate.setClientAbbreviation(test.getClientAbbreviation());
			testUpdate.setUrlSystem(test.getUrlSystem());
			
			testeDataMiningRepository.update(testUpdate);
			result.include("sucesso", localization.getMessage("datamining.testes.edit.success"));
		}else{
			validator.onErrorRedirectTo(this).create();
			
			ArrayList<Usuario> users = new ArrayList<Usuario>();
			users.add(usuarioLogado.getUsuario());
			test.setUsers(users);
			test.setUserCreated(usuarioLogado.getUsuario());
			
			testeDataMiningRepository.create(test);
			result.include("sucesso", localization.getMessage("datamining.testes.new.success"));
		}
		
		result.forwardTo(this).list();
	}
	*/
}
