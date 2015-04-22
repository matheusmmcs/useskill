package br.ufpi.datamining.controllers;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.core.Localization;
import br.com.caelum.vraptor.view.Results;
import br.ufpi.annotation.Logado;
import br.ufpi.componets.TesteView;
import br.ufpi.componets.UsuarioLogado;
import br.ufpi.componets.ValidateComponente;
import br.ufpi.controllers.BaseController;
import br.ufpi.datamining.models.TestDataMining;
import br.ufpi.datamining.models.vo.TestDataMiningVO;
import br.ufpi.datamining.repositories.TestDataMiningRepository;
import br.ufpi.models.Usuario;

@Path(value = "datamining")
@Resource
public class DataMiningTestController extends BaseController {

	private final TestDataMiningRepository testeDataMiningRepository;
	private final Localization localization;
	
	public DataMiningTestController(Result result, Validator validator,
			TesteView testeView, UsuarioLogado usuarioLogado,
			ValidateComponente validateComponente,
			Localization localization,
			TestDataMiningRepository testeDataMiningRepository) {
		super(result, validator, testeView, usuarioLogado, validateComponente);
		this.testeDataMiningRepository = testeDataMiningRepository;
		this.localization = localization; 
	}
	
	@Get("/")
	@Logado
	public void index() {}
	
	@Get("/testes/")
	@Logado
	public void list() {
		Gson gson = new Gson();
		List<TestDataMining> tests = testeDataMiningRepository.getTests(usuarioLogado.getUsuario().getId());
		List<TestDataMiningVO> testsVO = new ArrayList<TestDataMiningVO>();
		for(TestDataMining test : tests){
			testsVO.add(new TestDataMiningVO(test));
		}
		String json = gson.toJson(testsVO);
		
		//result.include("testesList", testeDataMiningRepository.getTests(usuarioLogado.getUsuario().getId()));
		
		result.use(Results.json()).from(json).serialize();
	}
	
	@Get("/testes/{idTeste}")
	@Logado
	public void view(Long idTeste) {
		Gson gson = new Gson();
		TestDataMining testPertencente = testeDataMiningRepository.getTestPertencente(usuarioLogado.getUsuario().getId(), idTeste);
		validateComponente.validarNotNull(testPertencente, "datamining.accessdenied");
		validator.onErrorRedirectTo(this).list();
		
		//result.include("test", testPertencente);
		
		result.use(Results.json()).from(gson.toJson(new TestDataMiningVO(testPertencente))).serialize();
	}
	
	@Get("/testes/criar")
	@Logado
	public void create() {
		result.include("title", localization.getMessage("datamining.testes.new"));
	}
	
	@Get("/testes/editar/{idTeste}")
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
	
	@Post("/testes/salvar")
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
	
	@Get("/testes/remover/{idTeste}")
	@Logado
	public void exclude(Long idTeste) {
		//TODO
	}
	
	private boolean testePertenceUsuarioLogado(Long idTeste) {
		validateComponente.validarId(idTeste);
		TestDataMining teste = testeDataMiningRepository.getTestPertencente(usuarioLogado.getUsuario().getId(), idTeste);
		return teste != null;
	}

}
