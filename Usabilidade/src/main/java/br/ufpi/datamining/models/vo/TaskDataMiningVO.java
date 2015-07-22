package br.ufpi.datamining.models.vo;

import java.util.List;
import java.util.Set;

import br.ufpi.datamining.models.ActionSingleDataMining;
import br.ufpi.datamining.models.TaskDataMining;
import br.ufpi.datamining.models.TestDataMining;
import br.ufpi.datamining.models.enums.ActionTypeDataMiningEnum;

public class TaskDataMiningVO {

	private Long id;
	private String title;
	private Integer threshold;
	private Set<ActionTypeDataMiningEnum> disregardActions;
	
	private List<ActionSingleDataMining> actionsInitial;
	private List<ActionSingleDataMining> actionsEnd;
	private List<ActionSingleDataMining> actionsRequired;
	
	private TestDataMining testDataMining;
	
	public TaskDataMiningVO(TaskDataMining taskDataMining) {
		super();
		this.id = taskDataMining.getId();
		this.title = taskDataMining.getTitle();
		this.threshold = taskDataMining.getThreshold();
		this.actionsInitial = taskDataMining.getActionsInitial();
		this.actionsRequired = taskDataMining.getActionsRequired();
		this.actionsEnd = taskDataMining.getActionsEnd();
		this.testDataMining = taskDataMining.getTestDataMining();
		this.disregardActions = taskDataMining.getDisregardActions();
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

	public List<ActionSingleDataMining> getActionsRequired() {
		return actionsRequired;
	}

	public void setActionsRequired(List<ActionSingleDataMining> actionsRequired) {
		this.actionsRequired = actionsRequired;
	}

	public Set<ActionTypeDataMiningEnum> getDisregardActions() {
		return disregardActions;
	}

	public void setDisregardActions(Set<ActionTypeDataMiningEnum> disregardActions) {
		this.disregardActions = disregardActions;
	}
	
	
}
