package br.ufpi.datamining.models.vo;

import java.util.ArrayList;
import java.util.List;

import br.ufpi.datamining.models.EvaluationTestDataMining;
import br.ufpi.datamining.models.TaskDataMining;
import br.ufpi.datamining.models.TestDataMining;

public class TestDataMiningVO {

	private Long id;
	private String title;
	private String clientAbbreviation;
	private String urlSystem;
	private List<TaskDataMiningVO> tasks;
	private List<EvaluationTestDataMining> evaluations;
	
	public TestDataMiningVO(TestDataMining test) {
		super();
		this.id = test.getId();
		this.title = test.getTitle();
		this.clientAbbreviation = test.getClientAbbreviation();
		this.urlSystem = test.getUrlSystem();
		this.setEvaluations(test.getEvaluations());
		
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
	
}
