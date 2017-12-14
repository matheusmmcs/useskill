package br.ufpi.datamining.controllers;

import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
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
import br.ufpi.datamining.repositories.TestDataMiningRepository;
import br.ufpi.datamining.utils.EntityDefaultManagerUtil;
import br.ufpi.datamining.utils.EntityManagerUtil;
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
		boolean mineActions = ArrayUtils.contains(selectedMetrics, ACTION_OCCURRENCE_RATE);
		
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
			barCharts.add(usm.generateActionOccurrenceRateChart(actions));
		}
		if (ArrayUtils.contains(selectedMetrics, TASK_LAYER_COUNT)) {
			System.out.println("Generating layer count chart...");
			areaCharts.add(usm.generateTaskLayerCountChart(tasks));
		}
		if (ArrayUtils.contains(selectedMetrics, ACTION_REPETITION_COUNT)) {
			System.out.println("Generating action repetition count chart...");
			barCharts.add(usm.generateActionRepetitionCountChart2(actions, 10));
			
//			Instant start1 = Instant.now();
//			usm.generateActionRepetitionCountChart(tasks);
//			Instant end1 = Instant.now();
//			
//			System.out.println("O método 1 durou " + Duration.between(start1, end1));
//			
//			Instant start2 = Instant.now();
//			usm.generateActionRepetitionCountChart2(actions, 10);
//			Instant end2 = Instant.now();
//			
//			System.out.println("O método 2 durou " + Duration.between(start2, end2));
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
	public void view(TestDataMining test, Long initDate, Long endDate, Integer[] selectedSmells, Integer[] selectedTasks) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, IOException {
		Gson gson = new GsonBuilder()
			.setExclusionStrategies(TestDataMiningVO.exclusionStrategy)
	        .serializeNulls()
	        .create();
		
		System.out.println("Smells:");
		for (int i = 0; i < selectedSmells.length; i++) {
			System.out.println(selectedSmells[i]);
		}
		System.out.println("Tarefas:");
		for (int i = 0; i < selectedTasks.length; i++) {
			System.out.println(selectedTasks[i]);
		}
		
		EntityManager entityManager = EntityManagerUtil.getEntityManager();
		EntityManager entityDafaultManager = EntityDefaultManagerUtil.getEntityManager();
		
		TaskDataMiningRepository taskDataMiningRepository = new TaskDataMiningRepository(entityDafaultManager);
		ActionDataMiningRepository actionDataMiningRepository = new ActionDataMiningRepository(entityManager);
		
		UsabilitySmellDetector usm = new UsabilitySmellDetector();
		List<TaskSmellAnalysis> tasks = new ArrayList<TaskSmellAnalysis>();
		
		for (int i = 0; i < test.getTasks().size(); i++) {
			System.out.println("Mining sessions for task " + i + "...");
			ResultDataMining resultDataMining = WebUsageMining.analyze((long)(i+1), initDate, endDate,
					SessionClassificationDataMiningFilterEnum.SUCCESS_ERROR_REPEAT, taskDataMiningRepository, actionDataMiningRepository);
			if (resultDataMining.getSessions().size() > 0)
				tasks.add(new TaskSmellAnalysis(test.getTasks().get(i).getTitle(), resultDataMining.getSessions()));				
		}
		
		if (ArrayUtils.contains(selectedSmells, LABORIOUS_TASK)) {
			System.out.println("Detecting Laborious Task...");
			usm.detectLaboriousTasks(tasks, UsabilitySmellDetector.NUMBER_DEFAULT, UsabilitySmellDetector.TIME_DEFAULT);
			//areaCharts.add(usm.generateTaskActionCountChart(tasks));
		}
		
		String json = gson.toJson(new TestDataMiningVO(test));
		
		result.use(Results.json()).from(json).serialize();
	}
	
	@Get("/testes/{idTeste}/smells/{idSmell}/value")
	@Logado
	public void view(Long idTeste, Long idSmell) {
		Gson gson = new GsonBuilder()
	        .setExclusionStrategies(TaskDataMiningVO.exclusionStrategy)
	        .serializeNulls()
	        .create();
		
		List<ParameterSmellDataMining> parametersFromSmell = parameterSmellDataMiningRepository.getParametersFromSmell(idSmell);
		
		for (ParameterSmellDataMining p : parametersFromSmell) {
			ParameterSmellTestDataMining valueParameter = parameterSmellTestDataMiningRepository.getParametersFromSmell(idTeste, p.getId());
		}
		
		result.use(Results.json()).from(gson.toJson(parametersFromSmell)).serialize();
	}
	
}
