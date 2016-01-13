package br.ufpi.datamining.models.vo;

import java.util.Date;

import br.ufpi.datamining.models.EvaluationTaskDataMining;
import br.ufpi.datamining.models.EvaluationTestDataMining;
import br.ufpi.datamining.models.TaskDataMining;

public class EvaluationTaskDataMiningVO {
	
	private Long id;
	private EvaluationTestDataMining evaluationTest;
	private TaskDataMining taskDataMining;
	private Long idTaskDataMining;
	
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
	
	public EvaluationTaskDataMiningVO(EvaluationTaskDataMining eval) {
		this.id = eval.getId();
		this.evaluationTest = eval.getEvaluationTest();
		this.taskDataMining = eval.getTaskDataMining();
		
		this.evalLastDate = eval.getEvalLastDate();
		this.evalMeanActions = eval.getEvalMeanActions();
		this.evalMeanTimes = eval.getEvalMeanTimes();
		this.evalMeanCompletion = eval.getEvalMeanCompletion();
		this.evalMeanCorrectness = eval.getEvalMeanCorrectness();
		this.evalZScoreActions = eval.getEvalZScoreActions();
		this.evalZScoreTime = eval.getEvalZScoreTime();
		this.evalEfficiency = eval.getEvalEfficiency();
		this.evalEffectiveness = eval.getEvalEffectiveness();
		this.evalEfficiencyNormalized = eval.getEvalEfficiencyNormalized();
		this.evalEffectivenessNormalized = eval.getEvalEffectivenessNormalized();
		this.evalFuzzyPriority = eval.getEvalFuzzyPriority();
		this.evalCountSessions = eval.getEvalCountSessions();
		this.evalCountSessionsNormalized = eval.getEvalCountSessionsNormalized();
		this.idTaskDataMining = this.taskDataMining.getId();
		
		this.meanTimeLoading = eval.getMeanTimeLoading();
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public EvaluationTestDataMining getEvaluationTest() {
		return evaluationTest;
	}
	public void setEvaluationTest(EvaluationTestDataMining evaluationTest) {
		this.evaluationTest = evaluationTest;
	}
	public TaskDataMining getTaskDataMining() {
		return taskDataMining;
	}
	public void setTaskDataMining(TaskDataMining taskDataMining) {
		this.taskDataMining = taskDataMining;
	}
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
	public Double getEvalEfficiencyNormalized() {
		return evalEfficiencyNormalized;
	}
	public void setEvalEfficiencyNormalized(Double evalEfficiencyNormalized) {
		this.evalEfficiencyNormalized = evalEfficiencyNormalized;
	}
	public Double getEvalEffectivenessNormalized() {
		return evalEffectivenessNormalized;
	}
	public void setEvalEffectivenessNormalized(Double evalEffectivenessNormalized) {
		this.evalEffectivenessNormalized = evalEffectivenessNormalized;
	}
	public Double getEvalFuzzyPriority() {
		return evalFuzzyPriority;
	}
	public void setEvalFuzzyPriority(Double evalFuzzyPriority) {
		this.evalFuzzyPriority = evalFuzzyPriority;
	}
	public Integer getEvalCountSessions() {
		return evalCountSessions;
	}
	public void setEvalCountSessions(Integer evalCountSessions) {
		this.evalCountSessions = evalCountSessions;
	}
	public Double getEvalCountSessionsNormalized() {
		return evalCountSessionsNormalized;
	}
	public void setEvalCountSessionsNormalized(Double evalCountSessionsNormalized) {
		this.evalCountSessionsNormalized = evalCountSessionsNormalized;
	}

	public Long getIdTaskDataMining() {
		return idTaskDataMining;
	}

	public void setIdTaskDataMining(Long idTaskDataMining) {
		this.idTaskDataMining = idTaskDataMining;
	}
	
	public static EvaluationTaskDataMining zeroEvaluation(EvaluationTaskDataMining eval) {
		eval.setEvalMeanActions(0d);
		eval.setEvalMeanTimes(0d);
		eval.setEvalMeanCompletion(0d);
		eval.setEvalMeanCorrectness(0d);
		eval.setEvalZScoreActions(0d);
		eval.setEvalZScoreTime(0d);
		eval.setEvalEfficiency(0d);
		eval.setEvalEffectiveness(0d);
		eval.setEvalEfficiencyNormalized(0d);
		eval.setEvalEffectivenessNormalized(0d);
		eval.setEvalFuzzyPriority(0d);
		eval.setEvalCountSessionsNormalized(0d);
		eval.setMeanTimeLoading(0l);
		return eval;
	}

	public Long getMeanTimeLoading() {
		return meanTimeLoading;
	}

	public void setMeanTimeLoading(Long meanTimeLoading) {
		this.meanTimeLoading = meanTimeLoading;
	}
	
}
