package br.ufpi.datamining.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

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
import br.ufpi.datamining.analisys.FrequentSequentialPatternMining;
import br.ufpi.datamining.analisys.WebUsageMining;
import br.ufpi.datamining.models.ActionSingleDataMining;
import br.ufpi.datamining.models.EvaluationTaskDataMining;
import br.ufpi.datamining.models.EvaluationTestDataMining;
import br.ufpi.datamining.models.FieldSearchTupleDataMining;
import br.ufpi.datamining.models.TaskDataMining;
import br.ufpi.datamining.models.TestDataMining;
import br.ufpi.datamining.models.aux.ResultDataMining;
import br.ufpi.datamining.models.enums.ReturnStatusEnum;
import br.ufpi.datamining.models.vo.EvaluationTaskDataMiningVO;
import br.ufpi.datamining.models.vo.FrequentSequentialPatternResultVO;
import br.ufpi.datamining.models.vo.ReturnVO;
import br.ufpi.datamining.models.vo.TaskDataMiningVO;
import br.ufpi.datamining.repositories.ActionDataMiningRepository;
import br.ufpi.datamining.repositories.EvaluationTaskDataMiningRepository;
import br.ufpi.datamining.repositories.EvaluationTestDataMiningRepository;
import br.ufpi.datamining.repositories.TaskDataMiningRepository;
import br.ufpi.datamining.repositories.TestDataMiningRepository;
import br.ufpi.datamining.utils.GsonExclusionStrategy;
import br.ufpi.models.Usuario;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Path(value = "datamining")
@Resource
public class DataMiningTaskController extends BaseController {
	
	private final TestDataMiningRepository testeDataMiningRepository;
	private final TaskDataMiningRepository taskDataMiningRepository;
	private final ActionDataMiningRepository actionDataMiningRepository;
	private final EvaluationTaskDataMiningRepository evaluationTaskDataMiningRepository;
	private final EvaluationTestDataMiningRepository evaluationTestDataMiningRepository;
	
	private final Localization localization;
	
	public DataMiningTaskController(Result result, Validator validator,
			TesteView testeView, UsuarioLogado usuarioLogado,
			ValidateComponente validateComponente,
			Localization localization,
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
		this.localization = localization;
	}
	
	@Get("/testes/{idTeste}/tarefas/{idTarefa}")
	@Logado
	public void view(Long idTeste, Long idTarefa) {
		Gson gson = new GsonBuilder()
	        .setExclusionStrategies(TaskDataMiningVO.exclusionStrategy)
	        .serializeNulls()
	        .create();
		TaskDataMining task = taskDataMiningRepository.find(idTarefa);
		
		validateComponente.validarNotNull(task, localization.getMessage("datamining.notfound", "datamining.task"));
		validateComponente.validarEquals(idTeste, task.getTestDataMining().getId(), "datamining.tasks.error.dontbelongs");
		
		TaskDataMiningVO taskDataMiningVO = new TaskDataMiningVO(task);
		result.use(Results.json()).from(gson.toJson(taskDataMiningVO)).serialize();
	}
	
	@Post("/testes/tarefas/salvar")
	@Consumes("application/json")
	@Logado
	public void salvar(TaskDataMining task) {
		Gson gson = new Gson();
		validateComponente.validarString(task.getTitle(), "datamining.tasks.title");
		
		ReturnVO returnvo; 
		
		if(!validator.hasErrors()){
			if(task.getId() != null){
				TaskDataMining taskUpdate = taskDataMiningRepository.find(task.getId());
				taskUpdate.setTitle(task.getTitle());
				taskUpdate.setThreshold(task.getThreshold());
				taskUpdate.setDisregardActions(task.getDisregardActions());
				taskUpdate.setActionsRequiredOrder(task.getActionsRequiredOrder());
				
				taskDataMiningRepository.update(taskUpdate);
				returnvo = new ReturnVO(ReturnStatusEnum.SUCESSO, "datamining.tasks.edit.success");
			}else{
				
				TestDataMining testDataMining = testeDataMiningRepository.find(task.getTestDataMining().getId());
				task.setTestDataMining(testDataMining);
				testDataMining.getTasks().add(task);
				
				taskDataMiningRepository.create(task);
				testeDataMiningRepository.update(testDataMining);
				returnvo = new ReturnVO(ReturnStatusEnum.SUCESSO, "datamining.tasks.new.success");
			}
			result.use(Results.json()).from(gson.toJson(returnvo)).serialize();
		}else{
			returnvo = new ReturnVO(ReturnStatusEnum.ERRO, "erro");
			returnvo.setErrorsMessage(validator.getErrors());
			
			validator.onErrorUse(Results.json()).from(gson.toJson(returnvo)).serialize();
		}
	}
	
	@Get("/testes/{idTeste}/avaliacao/{idEvaluationTest}/tarefas/{idTarefa}/avaliar")
	@Logado
	public void avaliar(Long idTeste, Long idEvaluationTest, Long idTarefa) {
		Long init = new Date().getTime();
		Gson gson = new GsonBuilder()
	        .setExclusionStrategies(TaskDataMiningVO.exclusionStrategy)
	        .serializeNulls()
	        .create();
		
		try {
			//persist results
			TaskDataMining taskDataMining = taskDataMiningRepository.find(idTarefa);
			EvaluationTestDataMining evaluationTest = evaluationTestDataMiningRepository.find(idEvaluationTest);
			
			ResultDataMining resultDataMining = WebUsageMining.analyze(idTarefa, evaluationTest.getInitDate().getTime(), evaluationTest.getLastDate().getTime(), taskDataMiningRepository, actionDataMiningRepository);
			
			EvaluationTaskDataMining evaluation = taskDataMining.getEvaluationFromEvalTest(idEvaluationTest);
			boolean newEvaluation = evaluation == null ? true : false;
			
			if (evaluation == null) {
				evaluation = new EvaluationTaskDataMining();
				evaluation.setEvaluationTest(evaluationTest);
				evaluation.setTaskDataMining(taskDataMining);
			}
			
			evaluation.setEvalLastDate(new Date());
			evaluation.setEvalCountSessions(resultDataMining.getCountSessions());
			
			if (evaluation.getEvalCountSessions() > 0) {
				evaluation.setEvalMeanActions(resultDataMining.getActionsAverageOk());
				evaluation.setEvalMeanTimes(resultDataMining.getTimesAverageOk());
				evaluation.setEvalMeanCompletion(resultDataMining.getRateSuccess());
				evaluation.setEvalMeanCorrectness(resultDataMining.getRateRequired());
				
				evaluation.setEvalZScoreActions((resultDataMining.getMaxActionsOk() - resultDataMining.getMeanActionsOk()) / resultDataMining.getStdDevActionsOk());
				evaluation.setEvalZScoreTime((resultDataMining.getMaxTimesOk() - resultDataMining.getMeanTimesOk()) / resultDataMining.getStdDevTimesOk());
				
				evaluation.setEvalEffectiveness((evaluation.getEvalMeanCompletion() * evaluation.getEvalMeanCorrectness()) / 100);
				evaluation.setEvalEfficiency(evaluation.getEvalEffectiveness() / (evaluation.getEvalZScoreActions() * evaluation.getEvalZScoreTime()));
				
				System.out.println(evaluation.getEvalEffectiveness() / (evaluation.getEvalZScoreTime()));
				System.out.println(evaluation.getEvalEffectiveness() / (evaluation.getEvalZScoreActions() * evaluation.getEvalZScoreTime()));
				System.out.println(evaluation.getEvalEffectiveness() / ((evaluation.getEvalZScoreActions() + evaluation.getEvalZScoreTime()) / 2));
			} else {
				evaluation = EvaluationTaskDataMiningVO.zeroEvaluation(evaluation);
			}
			
			FrequentSequentialPatternMining fspm = new FrequentSequentialPatternMining();
			List<FrequentSequentialPatternResultVO> frequentPatterns = null;
			
			double lastMinSup = 0d;
			if (evaluation.getEvalCountSessions() > 0) {
				//automatic patterns: (100/80/60/40/20)
				int defaultMinItens = 4;
				double[] minSups = new double[]{1.0, 0.8, 0.6, 0.4, 0.2};
				for (int i = 0; i < minSups.length; i++) {
					if (frequentPatterns == null || frequentPatterns.size() == 0) {
						lastMinSup = minSups[i];
						frequentPatterns = fspm.analyze(resultDataMining, minSups[i], null, defaultMinItens);
					}
				}
			}
			resultDataMining.setLastMinSup(lastMinSup);
			
			Long diffTime = new Date().getTime() - init;
			if (evaluation.getMeanTimeLoading() != null) {
				evaluation.setMeanTimeLoading((evaluation.getMeanTimeLoading() + diffTime) / 2);
			} else {
				evaluation.setMeanTimeLoading(diffTime);
			}
			
			
			if (newEvaluation) {
				evaluationTaskDataMiningRepository.create(evaluation);
				
				taskDataMining.getEvaluations().add(evaluation);
				taskDataMiningRepository.update(taskDataMining);
				
				evaluationTest.getEvaluationsTask().add(evaluation);
				evaluationTestDataMiningRepository.update(evaluationTest);
				
				System.out.println("Nova avaliação cadastrada!");
			} else {
				evaluationTaskDataMiningRepository.update(evaluation);
				System.out.println("Avaliação atualizada!");
			}
			
			
			HashMap<String, String> resultMap = new HashMap<String, String>();
			resultMap.put("frequentPatterns", gson.toJson(frequentPatterns));
			resultMap.put("result", gson.toJson(resultDataMining));
			resultMap.put("evalTask", gson.toJson(evaluation));
			resultMap.put("task", gson.toJson(new TaskDataMiningVO(taskDataMining)));
			
			result.use(Results.json()).from(resultMap).serialize();
		} catch (Exception e) {
			e.printStackTrace();
			ReturnVO returnVO = new ReturnVO(ReturnStatusEnum.ERRO, "erro");
			validator.onErrorUse(Results.json()).from(gson.toJson(returnVO)).serialize();
		}
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
