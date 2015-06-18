package br.ufpi.datamining.models.aux;

import java.util.List;

import br.ufpi.datamining.models.ActionDataMining;
import br.ufpi.datamining.models.enums.SessionClassificationDataMiningEnum;

public class SessionResultDataMining {

	private String id; //"username-count"
	private SessionClassificationDataMiningEnum classification;
	private Long time;
	private List<ActionDataMining> actions;
	private Boolean hasThreshold;
	
	public SessionResultDataMining(String id,
			SessionClassificationDataMiningEnum classification, Long time,
			List<ActionDataMining> actions,
			Boolean hasThreshold) {
		super();
		this.id = id;
		this.classification = classification;
		this.time = time;
		this.actions = actions;
		this.hasThreshold = hasThreshold;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public SessionClassificationDataMiningEnum getClassification() {
		return classification;
	}
	public void setClassification(SessionClassificationDataMiningEnum classification) {
		this.classification = classification;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public List<ActionDataMining> getActions() {
		return actions;
	}
	public void setActions(List<ActionDataMining> actions) {
		this.actions = actions;
	}

	public Boolean getHasThreshold() {
		return hasThreshold;
	}

	public void setHasThreshold(Boolean hasThreshold) {
		this.hasThreshold = hasThreshold;
	}
		
}