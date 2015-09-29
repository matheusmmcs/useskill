package br.ufpi.datamining.controllers;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.vraptor.Consumes;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.view.Results;
import br.ufpi.annotation.Logado;
import br.ufpi.componets.TesteView;
import br.ufpi.componets.UsuarioLogado;
import br.ufpi.componets.ValidateComponente;
import br.ufpi.controllers.BaseController;
import br.ufpi.datamining.analisys.Fuzzy;
import br.ufpi.datamining.models.ActionSingleDataMining;
import br.ufpi.datamining.models.TaskDataMining;
import br.ufpi.datamining.models.TestDataMining;
import br.ufpi.datamining.models.enums.ReturnStatusEnum;
import br.ufpi.datamining.models.vo.ReturnVO;
import br.ufpi.datamining.models.vo.TestDataMiningVO;
import br.ufpi.datamining.repositories.TaskDataMiningRepository;
import br.ufpi.datamining.repositories.TestDataMiningRepository;
import br.ufpi.datamining.utils.GsonExclusionStrategy;
import br.ufpi.datamining.utils.UsabilityUtils;
import br.ufpi.models.Usuario;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Path(value = "datamining")
@Resource
public class DataMiningTestController extends BaseController {

	private final TestDataMiningRepository testeDataMiningRepository;
	private final TaskDataMiningRepository taskDataMiningRepository;
	
	public DataMiningTestController(Result result, Validator validator,
			TesteView testeView, UsuarioLogado usuarioLogado,
			ValidateComponente validateComponente,
			TestDataMiningRepository testeDataMiningRepository,
			TaskDataMiningRepository taskDataMiningRepository) {
		super(result, validator, testeView, usuarioLogado, validateComponente);
		this.testeDataMiningRepository = testeDataMiningRepository;
		this.taskDataMiningRepository = taskDataMiningRepository;
	}
	
	@Get("/")
	@Logado
	public void index() {}
	
	@Get("/testes/")
	@Logado
	public void list() {
		Gson gson = new GsonBuilder()
	        .setExclusionStrategies(new GsonExclusionStrategy(TestDataMining.class, TaskDataMining.class))
	        .serializeNulls()
	        .create();
		
		List<TestDataMining> tests = testeDataMiningRepository.getTests(usuarioLogado.getUsuario().getId());
		List<TestDataMiningVO> testsVO = new ArrayList<TestDataMiningVO>();
		for(TestDataMining test : tests){
			testsVO.add(new TestDataMiningVO(test));
		}
		String json = gson.toJson(testsVO);
		
		result.use(Results.json()).from(json).serialize();
	}
	
	@Get("/testes/{idTeste}/priorizar/")
	@Logado
	public void priorizar(Long idTeste) {
		Gson gson = new GsonBuilder()
	        .setExclusionStrategies(new GsonExclusionStrategy(TestDataMining.class, ActionSingleDataMining.class))
	        .serializeNulls()
	        .create();
		
		TestDataMining testPertencente = testeDataMiningRepository.getTestPertencente(usuarioLogado.getUsuario().getId(), idTeste);
		validateComponente.validarNotNull(testPertencente, "datamining.accessdenied");
		validator.onErrorRedirectTo(this).list();
		
		//calcula eficácia e eficiência das tarefas
		List<TaskDataMining> tasks = testPertencente.getTasks();
		Double maxEffectiveness = 0d, maxEfficiency = 0d, maxSessions = 0d;
		for(TaskDataMining t : tasks){
			if(t.getEvalMeanCompletion() != null && t.getEvalMeanCorrectness() != null && t.getEvalMeanTimes() != null && t.getEvalMeanActions() != null ){
				t.setEvalEffectiveness(UsabilityUtils.calcEffectiveness(t.getEvalMeanCompletion(), t.getEvalMeanCorrectness()));
				t.setEvalEfficiency(UsabilityUtils.calcEfficiency(t.getEvalEffectiveness(), t.getEvalZScoreActions(), t.getEvalZScoreTime()));
				
				maxEffectiveness = maxEffectiveness < t.getEvalEffectiveness() ? t.getEvalEffectiveness() : maxEffectiveness;
				maxEfficiency = maxEfficiency < t.getEvalEfficiency() ? t.getEvalEfficiency() : maxEfficiency;
				maxSessions = maxSessions < t.getEvalCountSessions() ? t.getEvalCountSessions() : maxSessions;
			}
		}
		//normaliza
		for(TaskDataMining t : tasks){
			if(t.getEvalMeanCompletion() != null && t.getEvalMeanCorrectness() != null && t.getEvalMeanTimes() != null && t.getEvalMeanActions() != null ){
				t.setEvalEffectivenessNormalized(t.getEvalEffectiveness()/maxEffectiveness);
				t.setEvalEfficiencyNormalized(t.getEvalEfficiency()/maxEfficiency);
				t.setEvalCountSessionsNormalized(t.getEvalCountSessions()/maxSessions);
			}
		}
		//Fuzzy para calcular priorização
		Fuzzy.executeFuzzySystemWithFCMTasks(tasks, true);
		//salvar novos indices
		for(TaskDataMining t : tasks){
			if(t.getEvalMeanCompletion() != null && t.getEvalMeanCorrectness() != null && t.getEvalMeanTimes() != null && t.getEvalMeanActions() != null ){
				taskDataMiningRepository.update(t);
			}
		}
		
		result.use(Results.json()).from(gson.toJson(new TestDataMiningVO(testPertencente))).serialize();
	}
	
	@Get("/testes/{idTeste}")
	@Logado
	public void view(Long idTeste) {
		Gson gson = new GsonBuilder()
	        .setExclusionStrategies(new GsonExclusionStrategy(TestDataMining.class, ActionSingleDataMining.class))
	        .serializeNulls()
	        .create();
		
		TestDataMining testPertencente = testeDataMiningRepository.getTestPertencente(usuarioLogado.getUsuario().getId(), idTeste);
		validateComponente.validarNotNull(testPertencente, "datamining.accessdenied");
		validator.onErrorRedirectTo(this).list();
		
		result.use(Results.json()).from(gson.toJson(new TestDataMiningVO(testPertencente))).serialize();
	}
	
	@Post("/testes/salvar")
	@Consumes("application/json")
	@Logado
	public void salvar(TestDataMining test) {
		Gson gson = new Gson();
		//TestDataMining test = gson.fromJson(json, TestDataMining.class);
		validateComponente.validarString(test.getTitle(), "datamining.testes.title");
		validateComponente.validarString(test.getClientAbbreviation(), "datamining.testes.abbrev");
		validateComponente.validarURL(test.getUrlSystem());
		
		ReturnVO returnvo; 
		
		if(!validator.hasErrors()){
			if(test.getId() != null){
				TestDataMining testUpdate = testeDataMiningRepository.getTestPertencente(usuarioLogado.getUsuario().getId(), test.getId());
				testUpdate.setTitle(test.getTitle());
				testUpdate.setClientAbbreviation(test.getClientAbbreviation());
				testUpdate.setUrlSystem(test.getUrlSystem());
				
				testeDataMiningRepository.update(testUpdate);
				returnvo = new ReturnVO(ReturnStatusEnum.SUCESSO, "datamining.testes.edit.success");
			}else{
				ArrayList<Usuario> users = new ArrayList<Usuario>();
				users.add(usuarioLogado.getUsuario());
				test.setUsers(users);
				test.setUserCreated(usuarioLogado.getUsuario());
				
				testeDataMiningRepository.create(test);
				returnvo = new ReturnVO(ReturnStatusEnum.SUCESSO, "datamining.testes.new.success");
			}
			
			result.use(Results.json()).from(gson.toJson(returnvo)).serialize();
		}else{
			returnvo = new ReturnVO(ReturnStatusEnum.ERRO, "erro");
			returnvo.setErrorsMessage(validator.getErrors());
			
			validator.onErrorUse(Results.json()).from(gson.toJson(returnvo)).serialize();
		}
		
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
