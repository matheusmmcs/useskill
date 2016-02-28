package br.ufpi.datamining.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity  
@Table(name="datamining_evaluation_task")
public class EvaluationTaskDataMining implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private EvaluationTestDataMining evaluationTest;
	private TaskDataMining taskDataMining;
	
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
	
	private Long meanTimeLoading;
	
	@Id  
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	@ManyToOne
    @JoinColumn(name="evaluation_test_id")
	public EvaluationTestDataMining getEvaluationTest() {
		return evaluationTest;
	}
	public void setEvaluationTest(EvaluationTestDataMining evaluationTest) {
		this.evaluationTest = evaluationTest;
	}
	@ManyToOne
    @JoinColumn(name="task_id")
	public TaskDataMining getTaskDataMining() {
		return taskDataMining;
	}
	public void setTaskDataMining(TaskDataMining taskDataMining) {
		this.taskDataMining = taskDataMining;
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
	public Long getMeanTimeLoading() {
		return meanTimeLoading;
	}
	public void setMeanTimeLoading(Long meanTimeLoading) {
		this.meanTimeLoading = meanTimeLoading;
	}
	
	public String toString() {
		/*
		private Long id;
		private EvaluationTestDataMining evaluationTest;
		private TaskDataMining taskDataMining;
		
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
		
		private Long meanTimeLoading;
		 */
		
		String s = "--------- EvaluationTaskDataMining --------\n";
		s += "evalLastDate: " + evalLastDate +";\n";
		s += "evalMeanActions: " + evalMeanActions + ";\n";
		s += "evalMeanTimes: " + evalMeanTimes + ";\n";
		s += "evalMeanCompletion: " + evalMeanCompletion + ";\n";
		s += "evalMeanCorrectness: " + evalMeanCorrectness + ";\n";
		s += "evalZScoreActions: " + evalZScoreActions + ";\n";
		s += "evalZScoreTime: " + evalZScoreTime + ";\n";
		s += "evalEfficiency: " + evalEfficiency + ";\n";
		s += "evalEffectiveness: " + evalEffectiveness + ";\n";
		s += "evalEfficiencyNormalized: " + evalEfficiencyNormalized + ";\n";
		s += "evalEffectivenessNormalized: " + evalEffectivenessNormalized + ";\n";
		s += "evalFuzzyPriority: " + evalFuzzyPriority + ";\n";
		s += "evalCountSessions: " + evalCountSessions + ";\n";
		s += "evalCountSessionsNormalized: " + evalCountSessionsNormalized + ";\n";
		s += "meanTimeLoading: " + meanTimeLoading + ";\n";
		s += "\n";
		
		return s;
		
	}
}
