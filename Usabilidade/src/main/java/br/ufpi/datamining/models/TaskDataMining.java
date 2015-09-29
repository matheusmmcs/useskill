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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

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
	
	private TestDataMining testDataMining;
	
	//Evaluations Data
	private Date evalLastDate;
	private Double evalMeanActions;
	private Double evalMeanTimes;
	private Double evalMeanCompletion;
	private Double evalMeanCorrectness;
	private Double evalZScoreActions;
	private Double evalZScoreTime;
	private Double evalEfficiency;
	private Double evalEffectiveness;
	private Double evalEfficiencyNormalized;
	private Double evalEffectivenessNormalized;
	private Double evalFuzzyPriority;
	private Integer evalCountSessions;
	private Double evalCountSessionsNormalized;
	
	
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
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date getEvalLastDate() {
		return evalLastDate;
	}
	public void setEvalLastDate(Date evalLastDate) {
		this.evalLastDate = evalLastDate;
	}
	
	public Double getEvalMeanActions() {
		return evalMeanActions;
	}
	public void setEvalMeanActions(Double evalMeanActions) {
		this.evalMeanActions = evalMeanActions;
	}
	
	public Double getEvalMeanTimes() {
		return evalMeanTimes;
	}
	public void setEvalMeanTimes(Double evalMeanTimes) {
		this.evalMeanTimes = evalMeanTimes;
	}
	
	public Double getEvalMeanCompletion() {
		return evalMeanCompletion;
	}
	public void setEvalMeanCompletion(Double evalMeanCompletion) {
		this.evalMeanCompletion = evalMeanCompletion;
	}
	
	public Double getEvalMeanCorrectness() {
		return evalMeanCorrectness;
	}
	public void setEvalMeanCorrectness(Double evalMeanCorrectness) {
		this.evalMeanCorrectness = evalMeanCorrectness;
	}
	
	public Integer getEvalCountSessions() {
		return evalCountSessions;
	}
	public void setEvalCountSessions(Integer evalCountSessions) {
		this.evalCountSessions = evalCountSessions;
	}
	public Double getEvalZScoreActions() {
		return evalZScoreActions;
	}
	public void setEvalZScoreActions(Double evalZScoreActions) {
		this.evalZScoreActions = evalZScoreActions;
	}
	public Double getEvalZScoreTime() {
		return evalZScoreTime;
	}
	public void setEvalZScoreTime(Double evalZScoreTime) {
		this.evalZScoreTime = evalZScoreTime;
	}
	public Double getEvalEfficiency() {
		return evalEfficiency;
	}
	public void setEvalEfficiency(Double evalEfficiency) {
		this.evalEfficiency = evalEfficiency;
	}
	public Double getEvalEffectiveness() {
		return evalEffectiveness;
	}
	public void setEvalEffectiveness(Double evalEffectiveness) {
		this.evalEffectiveness = evalEffectiveness;
	}
	public Double getEvalFuzzyPriority() {
		return evalFuzzyPriority;
	}
	public void setEvalFuzzyPriority(Double evalFuzzyPriority) {
		this.evalFuzzyPriority = evalFuzzyPriority;
	}
	public Double getEvalEfficiencyNormalized() {
		return evalEfficiencyNormalized;
	}
	public void setEvalEfficiencyNormalized(Double evalEfficiencyNormalized) {
		this.evalEfficiencyNormalized = evalEfficiencyNormalized;
	}
	public Double getEvalEffectivenessNormalized() {
		return evalEffectivenessNormalized;
	}
	public void setEvalEffectivenessNormalized(
			Double evalEffectivenessNormalized) {
		this.evalEffectivenessNormalized = evalEffectivenessNormalized;
	}
	public Double getEvalCountSessionsNormalized() {
		return evalCountSessionsNormalized;
	}
	public void setEvalCountSessionsNormalized(
			Double evalCountSessionsNormalized) {
		this.evalCountSessionsNormalized = evalCountSessionsNormalized;
	}
		
}
