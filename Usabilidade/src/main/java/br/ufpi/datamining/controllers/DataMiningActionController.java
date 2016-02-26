package br.ufpi.datamining.controllers;

import br.com.caelum.vraptor.Consumes;
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
import br.ufpi.datamining.models.ActionSingleDataMining;
import br.ufpi.datamining.models.FieldSearchTupleDataMining;
import br.ufpi.datamining.models.TaskDataMining;
import br.ufpi.datamining.models.enums.MomentTypeActionDataMiningEnum;
import br.ufpi.datamining.models.enums.ReturnStatusEnum;
import br.ufpi.datamining.models.vo.ActionSingleDataMiningVO;
import br.ufpi.datamining.models.vo.ActionSingleJHeatDataMiningVO;
import br.ufpi.datamining.models.vo.ReturnVO;
import br.ufpi.datamining.repositories.ActionSingleDataMiningRepository;
import br.ufpi.datamining.repositories.FieldSearchTupleDataMiningRepository;
import br.ufpi.datamining.repositories.TaskDataMiningRepository;
import br.ufpi.datamining.repositories.TestDataMiningRepository;
import br.ufpi.datamining.utils.GsonExclusionStrategy;
import br.ufpi.models.Usuario;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Path(value = "datamining")
@Resource
public class DataMiningActionController extends BaseController {
	
	private final TestDataMiningRepository testeDataMiningRepository;
	private final TaskDataMiningRepository taskDataMiningRepository;
	private final ActionSingleDataMiningRepository actionSingleDataMiningRepository;
	private final FieldSearchTupleDataMiningRepository fieldSearchTupleDataMiningRepository;
	private final Localization localization;
	
	public DataMiningActionController(Result result, Validator validator,
			TesteView testeView, UsuarioLogado usuarioLogado,
			ValidateComponente validateComponente,
			Localization localization,
			TestDataMiningRepository testeDataMiningRepository,
			TaskDataMiningRepository taskDataMiningRepository,
			ActionSingleDataMiningRepository actionSingleDataMiningRepository,
			FieldSearchTupleDataMiningRepository fieldSearchTupleDataMiningRepository) {
		super(result, validator, testeView, usuarioLogado, validateComponente);
		this.testeDataMiningRepository = testeDataMiningRepository;
		this.taskDataMiningRepository = taskDataMiningRepository;
		this.localization = localization;
		this.actionSingleDataMiningRepository = actionSingleDataMiningRepository;
		this.fieldSearchTupleDataMiningRepository = fieldSearchTupleDataMiningRepository;
	}
	
	@Get("/testes/{idTeste}/tarefas/{idTarefa}/acoes/{idAcao}")
	@Logado
	public void view(Long idTeste, Long idTarefa, Long idAcao) {
		Gson gson = new GsonBuilder()
	        .setExclusionStrategies(ActionSingleDataMiningVO.exclusionStrategy)
	        .serializeNulls()
	        .create();
		ActionSingleDataMining action = actionSingleDataMiningRepository.find(idAcao);
		
		validateComponente.validarNotNull(action, localization.getMessage("datamining.notfound", "datamining.tasks.action"));
		validateComponente.validarEquals(idTeste, action.getTask().getId(), "datamining.action.task.error.dontbelongs");
		validateComponente.validarEquals(idTeste, action.getTask().getTestDataMining().getId(), "datamining.action.test.error.dontbelongs");
		
		result.use(Results.json()).from(gson.toJson(new ActionSingleDataMiningVO(action))).serialize();
	}
	
	@Post("/testes/tarefas/acoes/salvar")
	@Consumes("application/json")
	@Logado
	public void salvar(ActionSingleJHeatDataMiningVO actionJHeatVO) {
		Gson gson = new Gson();
		ActionSingleDataMining action = actionJHeatVO.toActionSingleDataMining();
		ReturnVO returnvo;
		
		TaskDataMining taskDataMining = taskDataMiningRepository.find(action.getTask().getId());
		action.setTask(taskDataMining);
		
		//setar actions no set correto
		if(action.getMomentType().equals(MomentTypeActionDataMiningEnum.START)){
			taskDataMining.getActionsInitial().add(action);
		}else if(action.getMomentType().equals(MomentTypeActionDataMiningEnum.END)){
			taskDataMining.getActionsEnd().add(action);
		}else if(action.getMomentType().equals(MomentTypeActionDataMiningEnum.REQUIRED)){
			taskDataMining.getActionsRequired().add(action);
		}
		
		//persistir fieldsearch
		for(FieldSearchTupleDataMining f : action.getElementFiedlSearch()){
			fieldSearchTupleDataMiningRepository.create(f);
		}
		for(FieldSearchTupleDataMining f : action.getUrlFieldSearch()){
			fieldSearchTupleDataMiningRepository.create(f);
		}
		
		actionSingleDataMiningRepository.create(action);
		taskDataMiningRepository.update(taskDataMining);
		returnvo = new ReturnVO(ReturnStatusEnum.SUCESSO, "datamining.tasks.actions.new.success");
			
		result.use(Results.json()).from(gson.toJson(returnvo)).serialize();
		
	}
	
	@Post("/testes/tarefas/acoes/deletar")
	@Consumes("application/json")
	@Logado
	public void delete(Long actionId) {
		Gson gson = new Gson();
		ActionSingleDataMining action = actionSingleDataMiningRepository.find(actionId);
		ReturnVO returnvo;
		
		TaskDataMining taskDataMining = taskDataMiningRepository.find(action.getTask().getId());
		if(action.getMomentType().equals(MomentTypeActionDataMiningEnum.START)){
			taskDataMining.getActionsInitial().remove(action);
		}else if(action.getMomentType().equals(MomentTypeActionDataMiningEnum.END)){
			taskDataMining.getActionsEnd().remove(action);
		}else if(action.getMomentType().equals(MomentTypeActionDataMiningEnum.REQUIRED)){
			taskDataMining.getActionsRequired().remove(action);
		}
		
		taskDataMiningRepository.update(taskDataMining);
		actionSingleDataMiningRepository.destroy(action);
		
		returnvo = new ReturnVO(ReturnStatusEnum.SUCESSO, "datamining.tasks.actions.delete.ok");
			
		result.use(Results.json()).from(gson.toJson(returnvo)).serialize();
	}
	
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
