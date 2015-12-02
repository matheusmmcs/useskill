package br.ufpi.datamining.controllers;

import java.util.ArrayList;
import java.util.Date;
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
import br.ufpi.datamining.analisys.WebUsageMining;
import br.ufpi.datamining.models.ActionDataMining;
import br.ufpi.datamining.models.ActionSingleDataMining;
import br.ufpi.datamining.models.EvaluationTaskDataMining;
import br.ufpi.datamining.models.EvaluationTestDataMining;
import br.ufpi.datamining.models.TaskDataMining;
import br.ufpi.datamining.models.TestDataMining;
import br.ufpi.datamining.models.aux.CountActionsAux;
import br.ufpi.datamining.models.aux.FieldSearch;
import br.ufpi.datamining.models.aux.FieldSearchComparatorEnum;
import br.ufpi.datamining.models.enums.ReturnStatusEnum;
import br.ufpi.datamining.models.vo.EvaluationTestDataMiningVO;
import br.ufpi.datamining.models.vo.ReturnVO;
import br.ufpi.datamining.models.vo.TestDataMiningVO;
import br.ufpi.datamining.repositories.ActionDataMiningRepository;
import br.ufpi.datamining.repositories.EvaluationTaskDataMiningRepository;
import br.ufpi.datamining.repositories.EvaluationTestDataMiningRepository;
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
	private final ActionDataMiningRepository actionDataMiningRepository;
	private final EvaluationTaskDataMiningRepository evaluationTaskDataMiningRepository;
	private final EvaluationTestDataMiningRepository evaluationTestDataMiningRepository;
	
	public DataMiningTestController(Result result, Validator validator,
			TesteView testeView, UsuarioLogado usuarioLogado,
			ValidateComponente validateComponente,
			TestDataMiningRepository testeDataMiningRepository,
			TaskDataMiningRepository taskDataMiningRepository,
			ActionDataMiningRepository actionDataMiningRepository,
			EvaluationTaskDataMiningRepository evaluationTaskDataMiningRepository,
			EvaluationTestDataMiningRepository evaluationTestDataMiningRepository) {
		super(result, validator, testeView, usuarioLogado, validateComponente);
		this.testeDataMiningRepository = testeDataMiningRepository;
		this.taskDataMiningRepository = taskDataMiningRepository;
		this.actionDataMiningRepository = actionDataMiningRepository;
		this.evaluationTaskDataMiningRepository = evaluationTaskDataMiningRepository;
		this.evaluationTestDataMiningRepository = evaluationTestDataMiningRepository;
	}
	
	@Get("/")
	@Logado
	public void index() {}
	
	@Get("/testes/")
	@Logado
	public void list() {
		Gson gson = new GsonBuilder()
	        .setExclusionStrategies(new GsonExclusionStrategy(ActionSingleDataMining.class, TestDataMining.class, TaskDataMining.class))
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
	
	@Get("/testes/{idTeste}/priorizar/init/{init}/end/{end}")
	@Logado
	public void priorizar(Long idTeste, Date init, Date end) {
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
			EvaluationTaskDataMining e = t.getEvaluationBetweenDates(init, end);
			if(e != null && e.getEvalMeanCompletion() != null && e.getEvalMeanCorrectness() != null && e.getEvalMeanTimes() != null && e.getEvalMeanActions() != null ){
				e.setEvalEffectiveness(UsabilityUtils.calcEffectiveness(e.getEvalMeanCompletion(), e.getEvalMeanCorrectness()));
				e.setEvalEfficiency(UsabilityUtils.calcEfficiency(e.getEvalEffectiveness(), e.getEvalZScoreActions(), e.getEvalZScoreTime()));
				
				maxEffectiveness = maxEffectiveness < e.getEvalEffectiveness() ? e.getEvalEffectiveness() : maxEffectiveness;
				maxEfficiency = maxEfficiency < e.getEvalEfficiency() ? e.getEvalEfficiency() : maxEfficiency;
				maxSessions = maxSessions < e.getEvalCountSessions() ? e.getEvalCountSessions() : maxSessions;
			}
		}
		
		//normaliza
		for(TaskDataMining t : tasks){
			EvaluationTaskDataMining e = t.getEvaluationBetweenDates(init, end);
			if(e != null && e.getEvalMeanCompletion() != null && e.getEvalMeanCorrectness() != null && e.getEvalMeanTimes() != null && e.getEvalMeanActions() != null ){
				e.setEvalEffectivenessNormalized(e.getEvalEffectiveness()/maxEffectiveness);
				e.setEvalEfficiencyNormalized(e.getEvalEfficiency()/maxEfficiency);
				e.setEvalCountSessionsNormalized(e.getEvalCountSessions()/maxSessions);
			}
		}
		
		//Fuzzy para calcular priorização
		Fuzzy.executeFuzzySystemWithFCMTasks(tasks, init, end, true);
		
		//salvar novos indices
		for(TaskDataMining t : tasks){
			EvaluationTaskDataMining e = t.getEvaluationBetweenDates(init, end);
			if(e.getEvalMeanCompletion() != null && e.getEvalMeanCorrectness() != null && e.getEvalMeanTimes() != null && e.getEvalMeanActions() != null ){
				evaluationTaskDataMiningRepository.update(e);
			}
		}
		
		result.use(Results.json()).from(gson.toJson(new TestDataMiningVO(testPertencente))).serialize();
	}
	
	@Get("/testes/{idTeste}")
	@Logado
	public void view(Long idTeste) {
		Gson gson = new GsonBuilder()
	        .setExclusionStrategies(new GsonExclusionStrategy(TestDataMining.class, TaskDataMining.class, ActionSingleDataMining.class))
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
	
	@Get("/testes/{idTeste}/maisacessados/{field}/init/{initDate}/end/{endDate}")
	@Logado
	public void mostView(Long idTeste, String field, Long initDate, Long endDate) {
		Gson gson = new Gson();
		
		TestDataMining testPertencente = testeDataMiningRepository.getTestPertencente(usuarioLogado.getUsuario().getId(), idTeste);
		validateComponente.validarNotNull(testPertencente, "datamining.accessdenied");
		validator.onErrorRedirectTo(this).list();
		
		Date initialDate = new Date(initDate);
		Date finalDate = new Date(endDate);
		
		List<CountActionsAux> counts = WebUsageMining.countActionsByRestrictions(idTeste, new FieldSearch(field, field, null, null), testeDataMiningRepository, actionDataMiningRepository, initialDate, finalDate);
		for (CountActionsAux c : counts) {
			System.out.println(c.getDescription() + " -> " + c.getCount());
		}
		
		result.use(Results.json()).from(gson.toJson(counts)).serialize();
	}
	
	@Get("/testes/{idTeste}/sessaoespecifica/{username}/local/{local}/init/{initDate}/end/{endDate}/limit/{limit}")
	@Logado
	public void actionsBetweenDates(Long idTeste, String username, String local, Long initDate, Long endDate, Long limit) {
		Gson gson = new Gson();
		
		TestDataMining testPertencente = testeDataMiningRepository.getTestPertencente(usuarioLogado.getUsuario().getId(), idTeste);
		validateComponente.validarNotNull(testPertencente, "datamining.accessdenied");
		validator.onErrorRedirectTo(this).list();
		
		Date initialDate = new Date(initDate);
		Date finalDate = new Date(endDate);
		
		
		FieldSearch fieldLocation = new FieldSearch("sJhm", "local", local, FieldSearchComparatorEnum.EQUALS);
		fieldLocation = null;
		List<ActionDataMining> listActionsBetweenDates = WebUsageMining.listActionsFromUserBetweenDates(idTeste, username, fieldLocation, taskDataMiningRepository, actionDataMiningRepository, initialDate, finalDate, limit);
		
		result.use(Results.json()).from(gson.toJson(listActionsBetweenDates)).serialize();
	}
	
	@Post("/testes/newevaluationtest")
	@Consumes("application/json")
	@Logado
	public void newevaluationtest(Long idTeste, Long initDate, Long endDate) {
		Gson gson = new GsonBuilder()
	        //.setExclusionStrategies(new GsonExclusionStrategy(EvaluationTestDataMining.class, EvaluationTaskDataMining.class, TestDataMining.class, ActionSingleDataMining.class))
			.setExclusionStrategies(new GsonExclusionStrategy(TestDataMining.class, TaskDataMining.class, ActionSingleDataMining.class))
	        .serializeNulls()
	        .create();
		
		TestDataMining testPertencente = testeDataMiningRepository.getTestPertencente(usuarioLogado.getUsuario().getId(), idTeste);
		
		EvaluationTestDataMining eval = new EvaluationTestDataMining();
		eval.setTest(testPertencente);
		eval.setInitDate(new Date(initDate));
		eval.setLastDate(new Date(endDate));
		
		List<EvaluationTestDataMining> evaluationTests = evaluationTestDataMiningRepository.getEvaluationTests(testPertencente, eval.getInitDate(), eval.getLastDate());
		
		if (evaluationTests.size() == 0) {
			evaluationTestDataMiningRepository.create(eval);
			result.use(Results.json()).from(gson.toJson(new TestDataMiningVO(testPertencente))).serialize();
		} else {
			System.out.println("already exists");
			result.use(Results.json()).from(gson.toJson(new ReturnVO(ReturnStatusEnum.ERRO, "datamining.testes.evaluations.newdates.alreadyexists"))).serialize();
		}
	}
	
	@Get("/testes/{idTeste}/avaliacao/{idEvalTest}")
	@Logado
	public void getTestEvaluation(Long idTeste, Long idEvalTest) {
		Gson gson = new GsonBuilder()
			.setExclusionStrategies(new GsonExclusionStrategy(EvaluationTestDataMining.class, TestDataMining.class, ActionSingleDataMining.class))
	        .serializeNulls()
	        .create();
		
		EvaluationTestDataMining evaluationTestDataMining = evaluationTestDataMiningRepository.find(idEvalTest);
		validateComponente.validarEquals(evaluationTestDataMining.getTest().getId(), idTeste, "datamining.accessdenied");
		
		ReturnVO returnvo;
		
		if(!validator.hasErrors()){
			result.use(Results.json()).from(gson.toJson(new EvaluationTestDataMiningVO(evaluationTestDataMining))).serialize();
		} else {
			returnvo = new ReturnVO(ReturnStatusEnum.ERRO, "erro");
			returnvo.setErrorsMessage(validator.getErrors());
			
			validator.onErrorUse(Results.json()).from(gson.toJson(returnvo)).serialize();
		}
	}
	
	private boolean testePertenceUsuarioLogado(Long idTeste) {
		validateComponente.validarId(idTeste);
		TestDataMining teste = testeDataMiningRepository.getTestPertencente(usuarioLogado.getUsuario().getId(), idTeste);
		return teste != null;
	}

}
