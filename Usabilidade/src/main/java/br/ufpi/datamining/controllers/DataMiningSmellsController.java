package br.ufpi.datamining.controllers;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.apache.commons.lang.ArrayUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.caelum.vraptor.Consumes;
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
import br.ufpi.datamining.models.TestDataMining;
import br.ufpi.datamining.models.aux.ResultDataMining;
import br.ufpi.datamining.models.aux.StackedAreaChart;
import br.ufpi.datamining.models.aux.TaskSmellAnalysis;
import br.ufpi.datamining.models.enums.ReturnStatusEnum;
import br.ufpi.datamining.models.enums.SessionClassificationDataMiningFilterEnum;
import br.ufpi.datamining.models.vo.ChartsVO;
import br.ufpi.datamining.models.vo.ReturnVO;
import br.ufpi.datamining.models.vo.TestDataMiningVO;
import br.ufpi.datamining.repositories.ActionDataMiningRepository;
import br.ufpi.datamining.repositories.TaskDataMiningRepository;
import br.ufpi.datamining.utils.EntityDefaultManagerUtil;
import br.ufpi.datamining.utils.EntityManagerUtil;
import br.ufpi.util.UsabilitySmellDetector;

@Path(value = "datamining")
@Resource
public class DataMiningSmellsController extends BaseController {
	
	private static final int ACTION_COUNT = 1;
	private static final int TIME = 2;
	private static final int CYCLE_RATE = 3;
	private static final int LAYER_COUNT = 4;
	
	public DataMiningSmellsController(Result result, Validator validator,
			TesteView testeView, UsuarioLogado usuarioLogado,
			ValidateComponente validateComponente) {
		super(result, validator, testeView, usuarioLogado, validateComponente);
	}

	
	@Post("/testes/smells/statistics")
	@Consumes("application/json")
	@Logado
	public void view(TestDataMining test, Long initDate, Long endDate, Integer[] metrics) throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException, IOException {
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
		
		List<StackedAreaChart> areaCharts = new ArrayList<StackedAreaChart>();
		
		for (int i = 0; i < test.getTasks().size(); i++) {
			System.out.println("Mining sessions for task " + i + "...");
			ResultDataMining resultDataMining = WebUsageMining.analyze((long)(i+1), initDate, endDate,
					SessionClassificationDataMiningFilterEnum.SUCCESS_ERROR_REPEAT, taskDataMiningRepository, actionDataMiningRepository);
			if (resultDataMining.getSessions().size() > 0)
				tasks.add(new TaskSmellAnalysis(test.getTasks().get(i).getTitle(), resultDataMining.getSessions()));				
		}
		
		if (ArrayUtils.contains(metrics, ACTION_COUNT)) {
			System.out.println("Generating action count chart...");
			areaCharts.add(usm.generateTaskActionCountChart(tasks));
		}
		
		if (ArrayUtils.contains(metrics, TIME)) {
			System.out.println("Generating time chart...");
			areaCharts.add(usm.generateTaskTimeChart(tasks));
		}
		
		if (ArrayUtils.contains(metrics, CYCLE_RATE)) {
			System.out.println("Generating cycle rate chart...");
			areaCharts.add(usm.generateTaskCycleRateChart(tasks));
		}
		if (ArrayUtils.contains(metrics, LAYER_COUNT)) {
			System.out.println("Generating layer count chart...");
			areaCharts.add(usm.generateTaskLayerCountChart(tasks));
		}
		
		if (areaCharts.size() > 0) {
			String json = gson.toJson(new ChartsVO(areaCharts));
			result.use(Results.json()).from(json).serialize();
		} else {
			ReturnVO returnvo = new ReturnVO(ReturnStatusEnum.ERRO, "datamining.smells.error.default");
			result.use(Results.json()).from(gson.toJson(returnvo)).serialize();
		}
		
	}
	
	@Post("/testes/smells/detection")
	@Consumes("application/json")
	@Logado
	public void view(TestDataMining test, Long initDate, Long endDate, Integer[] smells, Integer[] tasks) {
		Gson gson = new GsonBuilder()
			.setExclusionStrategies(TestDataMiningVO.exclusionStrategy)
	        .serializeNulls()
	        .create();
		
		System.out.println("Smells:");
		for (int i = 0; i < smells.length; i++) {
			System.out.println(smells[i]);
		}
		System.out.println("Tarefas:");
		for (int i = 0; i < tasks.length; i++) {
			System.out.println(tasks[i]);
		}
//		for (Object i : metrics) {
//			System.out.println((Integer) i);
//		}
		
		String json = gson.toJson(new TestDataMiningVO(test));
		
		result.use(Results.json()).from(json).serialize();
	}
	
}
