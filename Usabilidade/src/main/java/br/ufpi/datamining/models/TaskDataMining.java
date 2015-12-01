package br.ufpi.datamining.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CollectionOfElements;

import br.ufpi.datamining.models.enums.ActionTypeDataMiningEnum;

@Entity  
@Table(name="datamining_task")
public class TaskDataMining implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String title;
	private Integer threshold;
	private Set<ActionTypeDataMiningEnum> disregardActions;
	
	private String actionsRequiredOrder;
	private List<ActionSingleDataMining> actionsInitial;
	private List<ActionSingleDataMining> actionsEnd;	
	private List<ActionSingleDataMining> actionsRequired;
	
	private List<EvaluationTaskDataMining> evaluations;
	
	private TestDataMining testDataMining;
	
	
	@Id  
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(nullable = false)
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
	
	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinTable(name="datamining_task_actionsInitial")
	public List<ActionSingleDataMining> getActionsInitial() {
		return actionsInitial;
	}
	
	public void setActionsInitial(List<ActionSingleDataMining> actionsInitial) {
		this.actionsInitial = actionsInitial;
	}
	
	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinTable(name="datamining_task_actionsEnd")
	public List<ActionSingleDataMining> getActionsEnd() {
		return actionsEnd;
	}
	
	public void setActionsEnd(List<ActionSingleDataMining> actionsEnd) {
		this.actionsEnd = actionsEnd;
	}
	
	@ManyToOne
    @JoinColumn(name="test_id")
	public TestDataMining getTestDataMining() {
		return testDataMining;
	}
	
	public void setTestDataMining(TestDataMining testDataMining) {
		this.testDataMining = testDataMining;
	}
	
	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinTable(name="datamining_task_actionsRequired")
	public List<ActionSingleDataMining> getActionsRequired() {
		return actionsRequired;
	}
	public void setActionsRequired(List<ActionSingleDataMining> actionsRequired) {
		this.actionsRequired = actionsRequired;
	}
	
	public String getActionsRequiredOrder() {
		return actionsRequiredOrder;
	}
	public void setActionsRequiredOrder(String actionsRequiredOrder) {
		this.actionsRequiredOrder = actionsRequiredOrder;
	}
	
	@CollectionOfElements(fetch = FetchType.LAZY, targetElement = ActionTypeDataMiningEnum.class)
	@JoinTable(name = "datamining_task_disregardActions", joinColumns = @JoinColumn(name = "disregardactionsid"))
	@Column(name = "disregardActions")
	@Enumerated(EnumType.STRING)
	public Set<ActionTypeDataMiningEnum> getDisregardActions() {
		return disregardActions;
	}
	public void setDisregardActions(Set<ActionTypeDataMiningEnum> disregardActions) {
		this.disregardActions = disregardActions;
	}
	
	
	@OneToMany(mappedBy="taskDataMining", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	public List<EvaluationTaskDataMining> getEvaluations() {
		return evaluations;
	}
	public void setEvaluations(List<EvaluationTaskDataMining> evaluations) {
		this.evaluations = evaluations;
	}
	
	public EvaluationTaskDataMining getEvaluationFromEvalTest(Long idEvaluationTest) {
		for (EvaluationTaskDataMining evTask : evaluations) {
			if (evTask.getEvaluationTest().getId().equals(idEvaluationTest)) {
				return evTask;
			}
		}
		return null;
	}
	
	public EvaluationTaskDataMining getEvaluationBetweenDates(Date init, Date end) {
		for (EvaluationTaskDataMining evTask : evaluations) {
			EvaluationTestDataMining e = evTask.getEvaluationTest();
			if (e.getInitDate().compareTo(init) == 0 && e.getLastDate().compareTo(end) == 0 ) {
				return evTask;
			}
		}
		return null;
	}
	
		
}
