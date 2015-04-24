package br.ufpi.datamining.models.vo;

import java.util.List;

import br.ufpi.datamining.models.TaskDataMining;
import br.ufpi.datamining.models.TestDataMining;

public class TestDataMiningVO {

	private Long id;
	private String title;
	private String clientAbbreviation;
	private String urlSystem;
	private List<TaskDataMining> tasks;
	
	public TestDataMiningVO(TestDataMining test) {
		super();
		this.id = test.getId();
		this.title = test.getTitle();
		this.clientAbbreviation = test.getClientAbbreviation();
		this.urlSystem = test.getUrlSystem();
		this.tasks = test.getTasks();
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
	public List<TaskDataMining> getTasks() {
		return tasks;
	}
	public void setTasks(List<TaskDataMining> tasks) {
		this.tasks = tasks;
	}
	
}
