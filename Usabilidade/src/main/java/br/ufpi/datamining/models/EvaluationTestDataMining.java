package br.ufpi.datamining.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity  
@Table(name="datamining_evaluation_test")
@NamedQueries({
	@NamedQuery(name = "Eval.Test.ContainsDates", query = "SELECT e FROM EvaluationTestDataMining e WHERE :test = e.test AND :initDate = e.initDate AND :lastDate = e.lastDate")
})
public class EvaluationTestDataMining implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private TestDataMining test;
	private Date initDate;
	private Date lastDate;
	private List<EvaluationTaskDataMining> evaluationsTask;
	private Boolean isHidden = false;
	
	@Id  
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@ManyToOne
    @JoinColumn(name="test_id")
	public TestDataMining getTest() {
		return test;
	}
	public void setTest(TestDataMining testDataMining) {
		this.test = testDataMining;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date getInitDate() {
		return initDate;
	}
	public void setInitDate(Date initDate) {
		this.initDate = initDate;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	public Date getLastDate() {
		return lastDate;
	}
	public void setLastDate(Date lastDate) {
		this.lastDate = lastDate;
	}
	
	@OneToMany(mappedBy="evaluationTest", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	public List<EvaluationTaskDataMining> getEvaluationsTask() {
		return evaluationsTask;
	}
	public void setEvaluationsTask(List<EvaluationTaskDataMining> evaluationsTask) {
		this.evaluationsTask = evaluationsTask;
	}
	
	@Column(nullable = false)
	public Boolean getIsHidden() {
		return isHidden;
	}
	public void setIsHidden(Boolean isHidden) {
		this.isHidden = isHidden;
	}

}
