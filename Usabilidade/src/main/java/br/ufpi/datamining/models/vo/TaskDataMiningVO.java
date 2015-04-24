package br.ufpi.datamining.models.vo;

import java.util.List;

import br.ufpi.datamining.models.ActionSingleDataMining;
import br.ufpi.datamining.models.TaskDataMining;
import br.ufpi.datamining.models.TestDataMining;

public class TaskDataMiningVO {

	private Long id;
	private String title;
	private Integer threshold;
	private List<ActionSingleDataMining> actionsInitial;
	private List<ActionSingleDataMining> actionsEnd;	
	private TestDataMining testDataMining;
	
	public TaskDataMiningVO(TaskDataMining taskDataMining) {
		super();
		this.id = taskDataMining.getId();
		this.title = taskDataMining.getTitle();
		this.threshold = taskDataMining.getThreshold();
		this.actionsInitial = taskDataMining.getActionsInitial();
		this.actionsEnd = taskDataMining.getActionsEnd();
		this.testDataMining = taskDataMining.getTestDataMining();
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
	public Integer getThreshold() {
		return threshold;
	}
	public void setThreshold(Integer threshold) {
		this.threshold = threshold;
	}
	public List<ActionSingleDataMining> getActionsInitial() {
		return actionsInitial;
	}
	public void setActionsInitial(List<ActionSingleDataMining> actionsInitial) {
		this.actionsInitial = actionsInitial;
	}
	public List<ActionSingleDataMining> getActionsEnd() {
		return actionsEnd;
	}
	public void setActionsEnd(List<ActionSingleDataMining> actionsEnd) {
		this.actionsEnd = actionsEnd;
	}
	public TestDataMining getTestDataMining() {
		return testDataMining;
	}
	public void setTestDataMining(TestDataMining testDataMining) {
		this.testDataMining = testDataMining;
	}
	
	
}
