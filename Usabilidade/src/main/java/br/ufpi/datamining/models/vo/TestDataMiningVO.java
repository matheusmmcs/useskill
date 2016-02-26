package br.ufpi.datamining.models.vo;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.ExclusionStrategy;

import br.ufpi.datamining.models.ActionSingleDataMining;
import br.ufpi.datamining.models.EvaluationTaskDataMining;
import br.ufpi.datamining.models.EvaluationTestDataMining;
import br.ufpi.datamining.models.TaskDataMining;
import br.ufpi.datamining.models.TestDataMining;
import br.ufpi.datamining.utils.GsonExclusionStrategy;
import br.ufpi.models.Tarefa;
import br.ufpi.models.Teste;

public class TestDataMiningVO {

	public static ExclusionStrategy exclusionStrategy = new GsonExclusionStrategy(
			TestDataMining.class, TaskDataMining.class, ActionSingleDataMining.class, EvaluationTaskDataMining.class, Teste.class, Tarefa.class);
	
	private Long id;
	private String title;
	private String clientAbbreviation;
	private String urlSystem;
	private List<TaskDataMiningVO> tasks;
	private List<EvaluationTestDataMining> evaluations;
	
	private Teste testControl;
	private Boolean isControl = false;
	private Boolean isIgnoreURL = false;
	
	public TestDataMiningVO(TestDataMining test) {
		super();
		this.id = test.getId();
		this.title = test.getTitle();
		this.clientAbbreviation = test.getClientAbbreviation();
		this.urlSystem = test.getUrlSystem();
		this.setEvaluations(test.getEvaluations());
		this.setTestControl(test.getTestControl());
		this.setIsControl(test.getIsControl());
		this.setIsIgnoreURL(test.getIsIgnoreURL());
		
		List<TaskDataMiningVO> ts = new ArrayList<TaskDataMiningVO>();
		for (TaskDataMining t : test.getTasks()) {
			ts.add(new TaskDataMiningVO(t));
		}
		this.tasks = ts;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getClientAbbreviation() {
		return clientAbbreviation;
	}
	public void setClientAbbreviation(String clientAbbreviation) {
		this.clientAbbreviation = clientAbbreviation;
	}
	public String getUrlSystem() {
		return urlSystem;
	}
	public void setUrlSystem(String urlSystem) {
		this.urlSystem = urlSystem;
	}
	public List<TaskDataMiningVO> getTasks() {
		return tasks;
	}
	public void setTasks(List<TaskDataMiningVO> tasks) {
		this.tasks = tasks;
	}
	public List<EvaluationTestDataMining> getEvaluations() {
		return evaluations;
	}
	public void setEvaluations(List<EvaluationTestDataMining> evaluations) {
		this.evaluations = evaluations;
	}
	public Teste getTestControl() {
		return testControl;
	}
	public void setTestControl(Teste testControl) {
		this.testControl = testControl;
	}
	public Boolean getIsControl() {
		return isControl;
	}
	public void setIsControl(Boolean isControl) {
		this.isControl = isControl;
	}
	public Boolean getIsIgnoreURL() {
		return isIgnoreURL;
	}
	public void setIsIgnoreURL(Boolean isIgnoreURL) {
		this.isIgnoreURL = isIgnoreURL;
	}
	
}
