package br.ufpi.datamining.models.vo;

import java.util.List;
import java.util.Set;

import br.ufpi.datamining.models.ActionSingleDataMining;
import br.ufpi.datamining.models.EvaluationTaskDataMining;
import br.ufpi.datamining.models.EvaluationTestDataMining;
import br.ufpi.datamining.models.FieldSearchTupleDataMining;
import br.ufpi.datamining.models.TaskDataMining;
import br.ufpi.datamining.models.TestDataMining;
import br.ufpi.datamining.models.enums.ActionTypeDataMiningEnum;
import br.ufpi.models.Usuario;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;

public class TaskDataMiningVO {
	
	public static ExclusionStrategy exclusionStrategy = new ExclusionStrategy() {
        public boolean shouldSkipClass(Class<?> clazz) {
            return (clazz == TaskDataMining.class || clazz == Usuario.class || clazz == EvaluationTestDataMining.class);
        }
        public boolean shouldSkipField(FieldAttributes f) {
            return (f.getDeclaringClass() == FieldSearchTupleDataMining.class && f.getName().equals("action"));
        }
     };

	private Long id;
	private String title;
	private Integer threshold;
	private Set<ActionTypeDataMiningEnum> disregardActions;
	
	private String actionsRequiredOrder;
	private List<ActionSingleDataMining> actionsInitial;
	private List<ActionSingleDataMining> actionsEnd;
	private List<ActionSingleDataMining> actionsRequired;
	
	private TestDataMining testDataMining;
	private List<EvaluationTaskDataMining> evaluations;
	
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
		this.actionsRequiredOrder = taskDataMining.getActionsRequiredOrder();
		this.setEvaluations(taskDataMining.getEvaluations());
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

	public String getActionsRequiredOrder() {
		return actionsRequiredOrder;
	}

	public void setActionsRequiredOrder(String actionsRequiredOrder) {
		this.actionsRequiredOrder = actionsRequiredOrder;
	}

	public List<EvaluationTaskDataMining> getEvaluations() {
		return evaluations;
	}

	public void setEvaluations(List<EvaluationTaskDataMining> evaluations) {
		this.evaluations = evaluations;
	}
	
	
}
