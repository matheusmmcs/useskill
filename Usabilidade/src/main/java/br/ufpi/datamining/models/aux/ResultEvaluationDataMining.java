package br.ufpi.datamining.models.aux;

import java.util.List;

import br.ufpi.datamining.models.EvaluationTaskDataMining;
import br.ufpi.datamining.models.vo.FrequentSequentialPatternResultVO;
import br.ufpi.datamining.models.vo.TaskDataMiningVO;

public class ResultEvaluationDataMining {

	private List<FrequentSequentialPatternResultVO> frequentPatterns;
	private ResultDataMining result;
	private EvaluationTaskDataMining evalTask;
	private TaskDataMiningVO task;
	
	public ResultEvaluationDataMining(
			List<FrequentSequentialPatternResultVO> frequentPatterns,
			ResultDataMining result, EvaluationTaskDataMining evalTask,
			TaskDataMiningVO task) {
		super();
		this.frequentPatterns = frequentPatterns;
		this.result = result;
		this.evalTask = evalTask;
		this.task = task;
	}
	
	public ResultEvaluationDataMining() {
	}

	public List<FrequentSequentialPatternResultVO> getFrequentPatterns() {
		return frequentPatterns;
	}
	public void setFrequentPatterns(
			List<FrequentSequentialPatternResultVO> frequentPatterns) {
		this.frequentPatterns = frequentPatterns;
	}
	public ResultDataMining getResult() {
		return result;
	}
	public void setResult(ResultDataMining result) {
		this.result = result;
	}
	public EvaluationTaskDataMining getEvalTask() {
		return evalTask;
	}
	public void setEvalTask(EvaluationTaskDataMining evalTask) {
		this.evalTask = evalTask;
	}
	public TaskDataMiningVO getTask() {
		return task;
	}
	public void setTask(TaskDataMiningVO task) {
		this.task = task;
	}
	
	
	
}
