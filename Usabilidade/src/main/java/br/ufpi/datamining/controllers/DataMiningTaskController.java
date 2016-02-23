package br.ufpi.datamining.controllers;

import java.io.StringReader;
import java.util.ArrayList;
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
import br.ufpi.datamining.models.aux.ActionSituationAux;
import br.ufpi.datamining.models.aux.ResultDataMining;
import br.ufpi.datamining.models.enums.MomentTypeActionDataMiningEnum;
import br.ufpi.datamining.models.enums.ReturnStatusEnum;
import br.ufpi.datamining.models.vo.EvaluationTaskDataMiningVO;
import br.ufpi.datamining.models.vo.FrequentSequentialPatternResultVO;
import br.ufpi.datamining.models.vo.ReturnVO;
import br.ufpi.datamining.models.vo.TaskDataMiningVO;
import br.ufpi.datamining.repositories.ActionDataMiningRepository;
import br.ufpi.datamining.repositories.ActionSingleDataMiningRepository;
import br.ufpi.datamining.repositories.EvaluationTaskDataMiningRepository;
import br.ufpi.datamining.repositories.EvaluationTestDataMiningRepository;
import br.ufpi.datamining.repositories.FieldSearchTupleDataMiningRepository;
import br.ufpi.datamining.repositories.TaskDataMiningRepository;
import br.ufpi.datamining.repositories.TestDataMiningRepository;
import br.ufpi.datamining.utils.GsonExclusionStrategy;
import br.ufpi.models.Usuario;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

@Path(value = "datamining")
@Resource
public class DataMiningTaskController extends BaseController {
	
	private final TestDataMiningRepository testeDataMiningRepository;
	private final TaskDataMiningRepository taskDataMiningRepository;
	private final ActionDataMiningRepository actionDataMiningRepository;
	private final ActionSingleDataMiningRepository actionSingleDataMiningRepository;
	private final EvaluationTaskDataMiningRepository evaluationTaskDataMiningRepository;
	private final EvaluationTestDataMiningRepository evaluationTestDataMiningRepository;
	private final FieldSearchTupleDataMiningRepository fieldSearchTupleDataMiningRepository;
	
	private final Localization localization;
	
	public DataMiningTaskController(Result result, Validator validator,
			TesteView testeView, UsuarioLogado usuarioLogado,
			ValidateComponente validateComponente,
			Localization localization,
			TestDataMiningRepository testeDataMiningRepository,
			TaskDataMiningRepository taskDataMiningRepository,
			ActionDataMiningRepository actionDataMiningRepository,
			EvaluationTaskDataMiningRepository evaluationTaskDataMiningRepository,
			EvaluationTestDataMiningRepository evaluationTestDataMiningRepository,
			ActionSingleDataMiningRepository actionSingleDataMiningRepository,
			FieldSearchTupleDataMiningRepository fieldSearchTupleDataMiningRepository) {
		super(result, validator, testeView, usuarioLogado, validateComponente);
		this.testeDataMiningRepository = testeDataMiningRepository;
		this.taskDataMiningRepository = taskDataMiningRepository;
		this.actionDataMiningRepository = actionDataMiningRepository;
		this.evaluationTaskDataMiningRepository = evaluationTaskDataMiningRepository;
		this.evaluationTestDataMiningRepository = evaluationTestDataMiningRepository;
		this.actionSingleDataMiningRepository = actionSingleDataMiningRepository;
		this.fieldSearchTupleDataMiningRepository = fieldSearchTupleDataMiningRepository;
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
	
	@Get("/testes/{idTeste}/avaliacao/{idEvaluationTest}/tarefas/{idTarefa}/avaliar/minsup/{minSup}/minitens/{minItens}")
	@Logado
	public void avaliarParams(Long idTeste, Long idEvaluationTest, Long idTarefa, Double minSup, Integer minItens) {
		minSup = minSup > 0 ? minSup / 100 : 0;
		this.avaliarTarefa(idTeste, idEvaluationTest, idTarefa, minSup, minItens);
	}
	
	@Get("/testes/{idTeste}/avaliacao/{idEvaluationTest}/tarefas/{idTarefa}/avaliar")
	@Logado
	public void avaliar(Long idTeste, Long idEvaluationTest, Long idTarefa) {
		this.avaliarTarefa(idTeste, idEvaluationTest, idTarefa, null, null);
	}
		
	private void avaliarTarefa(Long idTeste, Long idEvaluationTest, Long idTarefa, Double minSup, Integer minItens) {
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
			
			double lastMinSup = minSup != null ? minSup : 0d;
			int defaultMinItens = minItens != null ? minItens : 4;
			
			if (evaluation.getEvalCountSessions() > 1) {
				if (minSup != null) {
					frequentPatterns = fspm.analyze(resultDataMining, lastMinSup, null, defaultMinItens);
				} else {
					//automatic patterns: (100/80/60/40/20)
					double[] minSups = new double[]{1.0, 0.8, 0.6, 0.4, 0.2};
					for (int i = 0; i < minSups.length; i++) {
						if (frequentPatterns == null || frequentPatterns.size() == 0) {
							lastMinSup = minSups[i];
							//if (!(resultDataMining.getUsersSequences().size() < 2 && lastMinSup == 1.0)) {
							System.out.println("--INICIAR FSPM: " + lastMinSup);
							frequentPatterns = fspm.analyze(resultDataMining, minSups[i], null, defaultMinItens);
							//}
						}
					}
				}
			}
			resultDataMining.setLastMinSup(lastMinSup);
			resultDataMining.setLastMinItens(defaultMinItens);
			
			
			
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
	
	@Post("/testes/avaliacao/tarefas/saveActionSituation")
	@Consumes("application/json")
	@Logado
	public void saveActionSituation(Long testId, Long evalTestId, Long taskId, String actionsStr) {
		TaskDataMining taskDataMining = taskDataMiningRepository.find(taskId);
		
		Gson gson = new Gson();
		List<ActionSituationAux> actions = gson.fromJson(actionsStr, new TypeToken<ArrayList<ActionSituationAux>>() {}.getType());

		List<ActionSingleDataMining> actionsRequired = taskDataMining.getActionsRequired();
		
		//buscar por outras ações obrigatórias para editar as existentes
		for (ActionSituationAux as : actions) {
			System.out.println("-----------");
			System.out.println(as.getAction());
			System.out.println(as.getElement());
			System.out.println(as.getLocation());
			System.out.println(as.getSituation());
			System.out.println(as.getType());
			
			ActionSingleDataMining asdm = as.toActionSingleDataMining(taskDataMining);
			
			//verificar se há alguma ação obrigatória já cadastrada
			for (ActionSingleDataMining asdm2 : actionsRequired) {
				if (asdm2.getActionType().equals(asdm.getActionType())) {
					int countSame = 0, 
						expected1 = asdm.getElementFiedlSearch().size(),
						expected2 = asdm.getUrlFieldSearch().size();
					
					for (FieldSearchTupleDataMining f : asdm.getElementFiedlSearch()) {
						for (FieldSearchTupleDataMining f2 : asdm2.getElementFiedlSearch()) {
							if (f.getField().equals(f2.getField()) && 
								f.getValue().equals(f2.getValue())) {
								countSame++;
							}
						}
					}
					
					if (countSame == expected1) {
						countSame = 0;
						for (FieldSearchTupleDataMining f : asdm.getUrlFieldSearch()) {
							for (FieldSearchTupleDataMining f2 : asdm2.getUrlFieldSearch()) {
								if (f.getField().equals(f2.getField()) && 
									f.getValue().equals(f2.getValue())) {
									countSame++;
								}
							}
						}
						if (countSame == expected2) {
							//são iguais
							asdm.setId(asdm2.getId());
							
							//remover do grupo anterior
							if(asdm2.getMomentType().equals(MomentTypeActionDataMiningEnum.START)){
								taskDataMining.getActionsInitial().remove(asdm2);
							}else if(asdm2.getMomentType().equals(MomentTypeActionDataMiningEnum.END)){
								taskDataMining.getActionsEnd().remove(asdm2);
							}else if(asdm2.getMomentType().equals(MomentTypeActionDataMiningEnum.REQUIRED)){
								taskDataMining.getActionsRequired().remove(asdm2);
							}
							
							break;
						}
					}
				}
			}
			
			
			asdm.setTask(taskDataMining);
			
			//setar actions no set correto
			boolean newMoment = false;
			if (asdm.getMomentType() != null) {
				if(asdm.getMomentType().equals(MomentTypeActionDataMiningEnum.START)){
					taskDataMining.getActionsInitial().add(asdm);
					newMoment = true;
				}else if(asdm.getMomentType().equals(MomentTypeActionDataMiningEnum.END)){
					taskDataMining.getActionsEnd().add(asdm);
					newMoment = true;
				}else if(asdm.getMomentType().equals(MomentTypeActionDataMiningEnum.REQUIRED)){
					taskDataMining.getActionsRequired().add(asdm);
					newMoment = true;
				}
			}
			
			if (newMoment) {
				//persistir fieldsearch
				for(FieldSearchTupleDataMining f : asdm.getElementFiedlSearch()){
					fieldSearchTupleDataMiningRepository.create(f);
				}
				for(FieldSearchTupleDataMining f : asdm.getUrlFieldSearch()){
					fieldSearchTupleDataMiningRepository.create(f);
				}
				
				//novo
				if (asdm.getId() == null) {
					actionSingleDataMiningRepository.create(asdm);
				} else {
					//editar
					actionSingleDataMiningRepository.update(asdm);
				}
			} else {
				//TODO: remover o antigo fieldsearch para evitar lixo no banco
			}
			
			taskDataMiningRepository.update(taskDataMining);
		}
		
		
		ReturnVO returnvo = new ReturnVO(ReturnStatusEnum.SUCESSO, "datamining.tasks.actions.edits.success");
		
		result.use(Results.json()).from(gson.toJson(returnvo)).serialize();
		
	}
	
	/*
	actions: "{"3":{"location":"confirmarGuiaDeExamePrestador-buscarSegurados","element":"id(\"content\")/div[@class=\"panel\"]/div[@class=\"form-wrapper\"]/form[1]","action":"form_submit","id":"3","situation":"error"},"6":{"location":"confirmarGuiaDeExamePrestador-selecionarSegurado","element":"id(\"content\")/div[@class=\"panel\"]/div[@class=\"form-wrapper\"]/form[1]/span[1]/button[@class=\"actionButton\"]","action":"click","id":"6","type":"default"},"7":{"location":"confirmarGuiaDeExamePrestador-selecionarSegurado","element":"id(\"content\")/div[@class=\"panel\"]/div[@class=\"form-wrapper\"]/form[1]","action":"form_submit","id":"7","type":"required","situation":"ok"},"8":{"location":"confirmarGuiaDeExamePrestador-selecionarGuia","element":"null","action":"onload","id":"8","type":"end"}}"
	evalTestId: 10
	taskId: 14
	testId: 1
	*/
	
	/*
	 
	 $scope.specialActionsEnum = {
	    	INITIAL: {
	    		id: 'initial',
	    		desc: 'Inicial'
	    	},
	    	END: {
	    		id: 'end',
	    		desc: 'Final'
	    	},
	    	REQUIRED: {
	    		id: 'required',
	    		desc: 'Obrigatória'
	    	},
	    	DEFAULT: {
	    		id: 'default',
	    		desc: 'Ação Normal'
	    	}
	    }
	 
	 */
	
	
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
