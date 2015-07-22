package br.ufpi.datamining.models.aux;

import java.util.HashMap;
import java.util.List;

import br.ufpi.datamining.models.PageViewActionDataMining;
import br.ufpi.datamining.models.enums.SessionClassificationDataMiningEnum;

public class SessionResultDataMining {

	private String id; //"username-count"
	private String username;
	
	private SessionClassificationDataMiningEnum classification;
	private Long time;
	private List<PageViewActionDataMining> actions;
	private HashMap<String, Integer> actionsRequiredSession;
	
	private Double actionsNormalized;
	private Double timeNormalized;
	private Double fuzzyPriority;
	private Double userRateSuccess;
	private Double userRateRequired;
	
	private Boolean hasThreshold;
	
	public SessionResultDataMining(String id,
			String username,
			SessionClassificationDataMiningEnum classification, Long time,
			List<PageViewActionDataMining> actions,
			HashMap<String, Integer> actionsRequiredSession,
			Double userRateRequired,
			Boolean hasThreshold) {
		super();
		this.username = username;
		this.id = id;
		this.classification = classification;
		this.time = time;
		this.actions = actions;
		this.hasThreshold = hasThreshold;
		this.actionsRequiredSession = actionsRequiredSession;
		this.userRateRequired = userRateRequired;
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

	public Double getFuzzyPriority() {
		return fuzzyPriority;
	}

	public void setFuzzyPriority(Double fuzzyPriority) {
		this.fuzzyPriority = fuzzyPriority;
	}
	
	public Double getUserRateSuccess() {
		return userRateSuccess;
	}

	public void setUserRateSuccess(Double userRateSuccess) {
		this.userRateSuccess = userRateSuccess;
	}

	public Double getActionsNormalized() {
		return actionsNormalized;
	}

	public void setActionsNormalized(Double actionsNormalized) {
		this.actionsNormalized = actionsNormalized;
	}

	public Double getTimeNormalized() {
		return timeNormalized;
	}

	public void setTimeNormalized(Double timeNormalized) {
		this.timeNormalized = timeNormalized;
	}

	public HashMap<String, Integer> getActionsRequiredSession() {
		return actionsRequiredSession;
	}

	public void setActionsRequiredSession(HashMap<String, Integer> actionsRequiredSession) {
		this.actionsRequiredSession = actionsRequiredSession;
	}

	public Double getUserRateRequired() {
		return userRateRequired;
	}

	public void setUserRateRequired(Double userRateRequired) {
		this.userRateRequired = userRateRequired;
	}

	public boolean isClassificationSuccess(){
		return this.getClassification().equals(SessionClassificationDataMiningEnum.SUCCESS);
	}
		
}