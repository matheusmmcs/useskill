package br.ufpi.datamining.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity  
@Table(name="datamining_task")
public class TaskDataMining implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String title;
	private Integer threshold;
	private List<ActionSingleDataMining> actionsInitial;
	private List<ActionSingleDataMining> actionsEnd;
	
	
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
	
	@OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	public List<ActionSingleDataMining> getActionsInitial() {
		return actionsInitial;
	}
	
	public void setActionsInitial(List<ActionSingleDataMining> actionsInitial) {
		this.actionsInitial = actionsInitial;
	}
	
	@OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	public List<ActionSingleDataMining> getActionsEnd() {
		return actionsEnd;
	}
	
	public void setActionsEnd(List<ActionSingleDataMining> actionsEnd) {
		this.actionsEnd = actionsEnd;
	}
		
}
