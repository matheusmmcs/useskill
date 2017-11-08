package br.ufpi.datamining.controllers;

import java.util.List;

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
import br.ufpi.datamining.models.TestDataMining;
import br.ufpi.datamining.models.vo.TestDataMiningVO;

@Path(value = "datamining")
@Resource
public class DataMiningSmellsController extends BaseController {

	public DataMiningSmellsController(Result result, Validator validator,
			TesteView testeView, UsuarioLogado usuarioLogado,
			ValidateComponente validateComponente) {
		super(result, validator, testeView, usuarioLogado, validateComponente);
	}

	
	@Post("/testes/smells/statistics")
	@Consumes("application/json")
	@Logado
	public void view(TestDataMining test, Long initDate, Long endDate, Integer[] metrics) {
		Gson gson = new GsonBuilder()
			.setExclusionStrategies(TestDataMiningVO.exclusionStrategy)
	        .serializeNulls()
	        .create();

		System.out.println("Funfou!!");
		for (int i = 0; i < metrics.length; i++) {
			System.out.println(metrics[i]);
		}
//		for (Object i : metrics) {
//			System.out.println((Integer) i);
//		}
		
		String json = gson.toJson(new TestDataMiningVO(test));
		
		result.use(Results.json()).from(json).serialize();
	}
	
	@Post("/testes/smells/detection")
	@Consumes("application/json")
	@Logado
	public void view(TestDataMining test, Long initDate, Long endDate, Integer[] metrics, Integer[] tasks) {
		Gson gson = new GsonBuilder()
			.setExclusionStrategies(TestDataMiningVO.exclusionStrategy)
	        .serializeNulls()
	        .create();

		System.out.println("Tarefas:");
		for (int i = 0; i < tasks.length; i++) {
			System.out.println(tasks[i]);
		}
		System.out.println("Métricas:");
		for (int i = 0; i < metrics.length; i++) {
			System.out.println(metrics[i]);
		}
//		for (Object i : metrics) {
//			System.out.println((Integer) i);
//		}
		
		String json = gson.toJson(new TestDataMiningVO(test));
		
		result.use(Results.json()).from(json).serialize();
	}
	
}
