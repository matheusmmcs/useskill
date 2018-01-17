package br.ufpi.datamining.controllers;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.apache.commons.lang.ArrayUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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
import br.ufpi.datamining.analisys.WebUsageMining;
import br.ufpi.datamining.models.ActionDataMining;
import br.ufpi.datamining.models.ParameterSmellDataMining;
import br.ufpi.datamining.models.ParameterSmellTestDataMining;
import br.ufpi.datamining.models.TaskDataMining;
import br.ufpi.datamining.models.TestDataMining;
import br.ufpi.datamining.models.aux.BarChart;
import br.ufpi.datamining.models.aux.ResultDataMining;
import br.ufpi.datamining.models.aux.SessionGraph;
import br.ufpi.datamining.models.aux.StackedAreaChart;
import br.ufpi.datamining.models.aux.TaskSmellAnalysis;
import br.ufpi.datamining.models.enums.ReturnStatusEnum;
import br.ufpi.datamining.models.enums.SessionClassificationDataMiningFilterEnum;
import br.ufpi.datamining.models.vo.ChartsVO;
import br.ufpi.datamining.models.vo.ReturnVO;
import br.ufpi.datamining.models.vo.TaskDataMiningVO;
import br.ufpi.datamining.models.vo.TestDataMiningVO;
import br.ufpi.datamining.repositories.ActionDataMiningRepository;
import br.ufpi.datamining.repositories.ParameterSmellDataMiningRepository;
import br.ufpi.datamining.repositories.ParameterSmellTestDataMiningRepository;
import br.ufpi.datamining.repositories.TaskDataMiningRepository;
import br.ufpi.datamining.utils.EntityDefaultManagerUtil;
import br.ufpi.datamining.utils.EntityManagerUtil;
import br.ufpi.datamining.utils.ParameterSmellUtils;
import br.ufpi.util.UsabilitySmellDetector;

@Path(value = "datamining")
@Resource
public class DataMiningSmellsController extends BaseController {
	
	//metricas
	private static final int TASK_ACTION_COUNT = 1;
	private static final int TASK_TIME = 2;
	private static final int TASK_CYCLE_RATE = 3;
	private static final int ACTION_OCCURRENCE_RATE = 4;
	private static final int TASK_LAYER_COUNT = 5;
	private static final int ACTION_REPETITION_COUNT = 6;
	
	//parametros
	private static final int MAX_ACTION_COUNT = 1;
	private static final int MAX_TIME = 2;
	private static final int MAX_CYCLE_RATE = 3;
	private static final int MAX_OCCURRENCE_RATE = 4;
	private static final int MIN_OCCURRENCE_COUNT_LA = 5;
	private static final int MAX_LAYER_COUNT = 6;
	private static final int MAX_ATTEMPT_COUNT = 7;
	private static final int MAX_REPETITION_COUNT = 8;
	private static final int MIN_OCCURRENCE_COUNT_MF = 9;
	
	//smells
	private static final int LABORIOUS_TASK = 1;
	private static final int CYCLIC_TASK = 2;
	private static final int LONELY_ACTION = 3;
	private static final int TOO_MANY_LAYERS = 4;
	private static final int UNDESCRIPTIVE_ELEMENT = 5;
	private static final int MISSING_FEEDBACK = 6;
	
	private final ParameterSmellDataMiningRepository parameterSmellDataMiningRepository;
	private final ParameterSmellTestDataMiningRepository parameterSmellTestDataMiningRepository;
	
	public DataMiningSmellsController(Result result, Validator validator,
			TesteView testeView, UsuarioLogado usuarioLogado,
			ValidateComponente validateComponente,
			ParameterSmellDataMiningRepository parameterSmellDataMiningRepository,
			ParameterSmellTestDataMiningRepository parameterSmellTestDataMiningRepository
			) {
		super(result, validator, testeView, usuarioLogado, validateComponente);
		this.parameterSmellDataMiningRepository = parameterSmellDataMiningRepository;
		this.parameterSmellTestDataMiningRepository = parameterSmellTestDataMiningRepository;
	}

	
	@Post("/testes/smells/statistics")
	@Consumes("application/json")
	@Logado
	public void view(TestDataMining test, Long initDate, Long endDate, Integer[] selectedMetrics) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, IOException {
		Gson gson = new GsonBuilder()
			.setExclusionStrategies(TestDataMiningVO.exclusionStrategy)
	        .serializeNulls()
	        .create();
		
		EntityManager entityManager = EntityManagerUtil.getEntityManager();
		EntityManager entityDafaultManager = EntityDefaultManagerUtil.getEntityManager();
		
		TaskDataMiningRepository taskDataMiningRepository = new TaskDataMiningRepository(entityDafaultManager);
		ActionDataMiningRepository actionDataMiningRepository = new ActionDataMiningRepository(entityManager);
		
		UsabilitySmellDetector usm = new UsabilitySmellDetector();
		List<TaskSmellAnalysis> tasks = new ArrayList<TaskSmellAnalysis>();
		List<ActionDataMining> actions = new ArrayList<ActionDataMining>();
		
		List<StackedAreaChart> areaCharts = new ArrayList<StackedAreaChart>();
		List<BarChart> barCharts = new ArrayList<BarChart>();
		
		boolean mineSessions =
				ArrayUtils.contains(selectedMetrics, TASK_ACTION_COUNT) || ArrayUtils.contains(selectedMetrics, TASK_TIME) ||
				ArrayUtils.contains(selectedMetrics, TASK_CYCLE_RATE) || ArrayUtils.contains(selectedMetrics, TASK_LAYER_COUNT);
		boolean mineActions =
				ArrayUtils.contains(selectedMetrics, ACTION_OCCURRENCE_RATE) || ArrayUtils.contains(selectedMetrics, ACTION_REPETITION_COUNT);
		
		for (TaskDataMining task : test.getTasks()) {
			if (mineSessions) {
				System.out.println("Mining sessions for task " + task.getId() + "...");
				ResultDataMining resultDataMining = WebUsageMining.analyze(task.getId(), initDate, endDate,
						SessionClassificationDataMiningFilterEnum.SUCCESS_ERROR_REPEAT, taskDataMiningRepository, actionDataMiningRepository);
				if (resultDataMining.getSessions().size() > 0)
					tasks.add(new TaskSmellAnalysis(task.getTitle(), resultDataMining.getSessions()));				
			}
		}
		
		if (mineActions) {
			System.out.println("Mining actions...");
			actions = WebUsageMining.listActionsBetweenDates(
					test.getTasks().get(0).getId(), taskDataMiningRepository, actionDataMiningRepository,
					new Date(initDate), new Date(endDate), null);
		}
		
		if (ArrayUtils.contains(selectedMetrics, TASK_ACTION_COUNT)) {
			System.out.println("Generating action count chart...");
			areaCharts.add(usm.generateTaskActionCountChart(tasks));
		}
		if (ArrayUtils.contains(selectedMetrics, TASK_TIME)) {
			System.out.println("Generating time chart...");
			areaCharts.add(usm.generateTaskTimeChart(tasks));
		}
		if (ArrayUtils.contains(selectedMetrics, TASK_CYCLE_RATE)) {
			System.out.println("Generating cycle rate chart...");
			areaCharts.add(usm.generateTaskCycleRateChart(tasks));
		}
		if (ArrayUtils.contains(selectedMetrics, ACTION_OCCURRENCE_RATE)) {
			System.out.println("Generating action occurrence rate chart...");
			barCharts.add(usm.generateActionOccurrenceRateChart(actions, 10));
		}
		if (ArrayUtils.contains(selectedMetrics, TASK_LAYER_COUNT)) {
			System.out.println("Generating layer count chart...");
			areaCharts.add(usm.generateTaskLayerCountChart(tasks));
		}
		if (ArrayUtils.contains(selectedMetrics, ACTION_REPETITION_COUNT)) {
			System.out.println("Generating action repetition count chart...");
			barCharts.add(usm.generateActionRepetitionCountChart2(actions, 10));
		}
		
		if (areaCharts.size() > 0 || barCharts.size() > 0) {
			String json = gson.toJson(new ChartsVO(areaCharts, barCharts));
			result.use(Results.json()).from(json).serialize();
		} else {
			ReturnVO returnvo = new ReturnVO(ReturnStatusEnum.ERRO, "datamining.smells.error.default");
			result.use(Results.json()).from(gson.toJson(returnvo)).serialize();
		}
		
	}
	
	@Post("/testes/smells/detection")
	@Consumes("application/json")
	@Logado
	public void view(TestDataMining test, Long initDate, Long endDate, Integer[] selectedSmells, Long[] selectedTasks) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, IOException {
		Gson gson = new GsonBuilder()
			.setExclusionStrategies(TestDataMiningVO.exclusionStrategy)
	        .serializeNulls()
	        .create();
		
		ParameterSmellUtils.findParametersSmellValues(test.getId(), parameterSmellDataMiningRepository, parameterSmellTestDataMiningRepository);
		List<ParameterSmellTestDataMining> smellsParametersValues = ParameterSmellUtils.findParametersSmellValues(test.getId(),
				parameterSmellDataMiningRepository,
				parameterSmellTestDataMiningRepository);
		
		EntityManager entityManager = EntityManagerUtil.getEntityManager();
		EntityManager entityDafaultManager = EntityDefaultManagerUtil.getEntityManager();
		
		TaskDataMiningRepository taskDataMiningRepository = new TaskDataMiningRepository(entityDafaultManager);
		ActionDataMiningRepository actionDataMiningRepository = new ActionDataMiningRepository(entityManager);
		
		UsabilitySmellDetector usm = new UsabilitySmellDetector();
		List<TaskSmellAnalysis> tasks = new ArrayList<TaskSmellAnalysis>();
		List<ActionDataMining> actions = new ArrayList<ActionDataMining>();
		
		Map<String, Map<String, List<SessionGraph>>> smellsDetections = new LinkedHashMap<String, Map<String,List<SessionGraph>>>();
		
		boolean mineSessions =
				ArrayUtils.contains(selectedSmells, LABORIOUS_TASK) || ArrayUtils.contains(selectedSmells, CYCLIC_TASK) ||
				ArrayUtils.contains(selectedSmells, TOO_MANY_LAYERS) || ArrayUtils.contains(selectedSmells, UNDESCRIPTIVE_ELEMENT);
		boolean mineActions =
				ArrayUtils.contains(selectedSmells, LONELY_ACTION) || ArrayUtils.contains(selectedSmells, MISSING_FEEDBACK);
		
		if (mineSessions) {
			for (TaskDataMining task : test.getTasks()) {
				if (ArrayUtils.contains(selectedTasks, task.getId())) {
					System.out.println("Mining sessions for task " + task.getId() + "...");
					ResultDataMining resultDataMining = WebUsageMining.analyze(task.getId(), initDate, endDate,
							SessionClassificationDataMiningFilterEnum.SUCCESS_ERROR_REPEAT, taskDataMiningRepository, actionDataMiningRepository);
					if (resultDataMining.getSessions().size() > 0)
						tasks.add(new TaskSmellAnalysis(task.getTitle(), resultDataMining.getSessions()));				
				}
				//break;
			}
		}
		
		if (mineActions) {
			System.out.println("Mining actions...");
			actions = WebUsageMining.listActionsBetweenDates(
					test.getTasks().get(0).getId(), taskDataMiningRepository, actionDataMiningRepository,
					new Date(initDate), new Date(endDate), null);
		}
		
		if (ArrayUtils.contains(selectedSmells, LABORIOUS_TASK)) {
			int maxActionCount = 0, minSessionCount = 0;
			long maxTime = 0;
			for (ParameterSmellTestDataMining parameter : smellsParametersValues) {
				if (parameter.getParameterSmell().getId() == MAX_ACTION_COUNT)
					maxActionCount =  (int)(double)parameter.getValue();
				if (parameter.getParameterSmell().getId() == MAX_TIME)
					maxTime =  (long)(double)parameter.getValue();
			}
			
			System.out.println("Detecting Laborious Task...");
			Map<String, List<SessionGraph>> sessionGraphs = usm.detectLaboriousTasks2(tasks,
					maxActionCount, maxTime, UsabilitySmellDetector.DEFAULT_VALUE);
			smellsDetections.put("Laborious Task", sessionGraphs);
			//areaCharts.add(usm.generateTaskActionCountChart(tasks));
		}
		if (ArrayUtils.contains(selectedSmells, CYCLIC_TASK)) {
			double maxCycleRate = 0;
			for (ParameterSmellTestDataMining parameter : smellsParametersValues) {
				if (parameter.getParameterSmell().getId() == MAX_CYCLE_RATE)
					maxCycleRate =  (double)parameter.getValue();
			}
			System.out.println("Detecting Cyclic Task...");
			Map<String, List<SessionGraph>> sessionGraphs = usm.detectCyclicSessions2(tasks,
					maxCycleRate, UsabilitySmellDetector.DEFAULT_VALUE);
			smellsDetections.put("Cyclic Task", sessionGraphs);
		}
		
//		String json = gson.toJson(new TestDataMiningVO(test));
		String json = gson.toJson(smellsDetections);
		
		result.use(Results.json()).from(json).serialize();
	}
	
	@Get("/testes/{idTeste}/smells/{idSmell}/parameters")
	@Logado
	public void parameters(Long idTeste, Long idSmell) {
		Gson gson = new GsonBuilder()
	        .setExclusionStrategies(TaskDataMiningVO.exclusionStrategy)
	        .serializeNulls()
	        .create();
		
		List<ParameterSmellTestDataMining> listParametersSmellValues = ParameterSmellUtils.findParametersSmellValues(idTeste,
				parameterSmellDataMiningRepository,
				parameterSmellTestDataMiningRepository);
		
		List<ParameterSmellTestDataMining> listParametersValuesFromSmell = new ArrayList<ParameterSmellTestDataMining>();
		for (ParameterSmellTestDataMining p : listParametersSmellValues) {
			if (p.getParameterSmell().getIdSmell().equals(idSmell)) {
				listParametersValuesFromSmell.add(p);
			}
		}
		
		result.use(Results.json()).from(gson.toJson(listParametersValuesFromSmell)).serialize();
	}
	
	@Post("/testes/smells/parameters/change")
	@Consumes("application/json")
	@Logado
	//public void changeParameter(Long idTeste, Long idParameterSmell, Double value) {
	public void changeParameter(ParameterSmellTestDataMining[] parameters) {
		Gson gson = new GsonBuilder()
	        .serializeNulls()
	        .create();
		
		ReturnVO returnvo;
		
		if (parameters != null) {
			for (ParameterSmellTestDataMining parameter : parameters) {
				parameterSmellTestDataMiningRepository.update(parameter);
			}
			returnvo = new ReturnVO(ReturnStatusEnum.SUCESSO, "datamining.smells.testes.config.parameters.saveok");
		} else {
			returnvo = new ReturnVO(ReturnStatusEnum.ERRO, "datamining.smells.error.default");
		}
		
		//ParameterSmellTestDataMining[] listParametersValuesFromSmell
		/*
		ParameterSmellTestDataMining valueParameterSmellTest = parameterSmellTestDataMiningRepository.getValueParameterSmellTest(idTeste, idParameterSmell);
		ReturnVO returnvo;
		
		if (valueParameterSmellTest != null) {
			valueParameterSmellTest.setValue(value);
			parameterSmellTestDataMiningRepository.update(valueParameterSmellTest);
			returnvo = new ReturnVO(ReturnStatusEnum.ERRO, "datamining.smells.testes.config.parameters.saveok");
		} else {
			returnvo = new ReturnVO(ReturnStatusEnum.ERRO, "datamining.smells.error.default");
		}
		*/
		
		result.use(Results.json()).from(gson.toJson(returnvo)).serialize();
	}
	
}
