package br.ufpi.datamining.models.aux;

import java.util.List;

import br.ufpi.datamining.models.PageViewActionDataMining;
import br.ufpi.datamining.models.enums.SessionClassificationDataMiningEnum;

public class SessionResultDataMining {

	private String id; //"username-count"
	private String username;
	private SessionClassificationDataMiningEnum classification;
	private Long time;
	private List<PageViewActionDataMining> actions;
	private Boolean hasThreshold;
	
	public SessionResultDataMining(String id,
			String username,
			SessionClassificationDataMiningEnum classification, Long time,
			List<PageViewActionDataMining> actions,
			Boolean hasThreshold) {
		super();
		this.username = username;
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
	public List<PageViewActionDataMining> getActions() {
		return actions;
	}
	public void setActions(List<PageViewActionDataMining> actions) {
		this.actions = actions;
	}

	public Boolean getHasThreshold() {
		return hasThreshold;
	}

	public void setHasThreshold(Boolean hasThreshold) {
		this.hasThreshold = hasThreshold;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
		
}