package br.ufpi.datamining.models.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.ufpi.datamining.models.ActionSingleDataMining;
import br.ufpi.datamining.models.EvaluationTaskDataMining;
import br.ufpi.datamining.models.EvaluationTestDataMining;
import br.ufpi.datamining.models.TaskDataMining;
import br.ufpi.datamining.models.TestDataMining;
import br.ufpi.datamining.utils.GsonExclusionStrategy;

import com.google.gson.ExclusionStrategy;

public class EvaluationTestDataMiningVO {

	public static ExclusionStrategy exclusionStrategy = new GsonExclusionStrategy(
			EvaluationTestDataMining.class, TestDataMining.class, ActionSingleDataMining.class, TaskDataMining.class);
	
	private Long id;
	private TestDataMining test;
	private Date initDate;
	private Date lastDate;
	private List<EvaluationTaskDataMiningVO> evaluationsTask;
	
	public EvaluationTestDataMiningVO(EvaluationTestDataMining eval) {
		this.id = eval.getId();
		this.test = eval.getTest();
		this.initDate = eval.getInitDate();
		this.lastDate = eval.getLastDate();
		
		List<EvaluationTaskDataMiningVO> evalTasksVO = new ArrayList<EvaluationTaskDataMiningVO>();
		List<EvaluationTaskDataMining> evalTasks = eval.getEvaluationsTask();
		if (evalTasks != null) {
			for (EvaluationTaskDataMining e : evalTasks) {
				evalTasksVO.add(new EvaluationTaskDataMiningVO(e));
			}
		}
		this.evaluationsTask = evalTasksVO;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public TestDataMining getTest() {
		return test;
	}
	public void setTest(TestDataMining test) {
		this.test = test;
	}
	public Date getInitDate() {
		return initDate;
	}
	public void setInitDate(Date initDate) {
		this.initDate = initDate;
	}
	public Date getLastDate() {
		return lastDate;
	}
	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}
	public List<EvaluationTaskDataMiningVO> getEvaluationsTask() {
		return evaluationsTask;
	}
	public void setEvaluationsTask(List<EvaluationTaskDataMiningVO> evaluationsTask) {
		this.evaluationsTask = evaluationsTask;
	}
	
	
}
