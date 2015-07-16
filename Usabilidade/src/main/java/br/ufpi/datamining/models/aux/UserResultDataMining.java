package br.ufpi.datamining.models.aux;

import java.util.List;

public class UserResultDataMining {
	
	private String username;
	private Double actionsAverage;
	private Double timesAverage;
	private Double actionsAverageOk;
	private Double timesAverageOk;
	
	private Integer countSessionsSuccess;
	private Integer countSessionsError;
	private Integer countSessionsRepeat;
	private Integer countSessionsThreshold;
	
	private Double rateSuccess;
	private Integer countSessions;
	private Double fuzzyPriority;
	
	private Double maxActionsAverage;
	private Double maxTimeAverage;
	private Double maxActionsAverageOk;
	private Double maxTimeAverageOk;
	
	private List<String> sessionsId;
	
	public UserResultDataMining(String username, 
			Double actionsAverage, Double timesAverage, Double actionsAverageOk, Double timesAverageOk, 
			Integer countSessionsSuccess, Integer countSessionsError, Integer countSessionsRepeat,
			Integer countSessionsThreshold, Double fuzzyPriority, List<String> sessionsId) {
		super();
		this.username = username;
		this.actionsAverage = actionsAverage;
		this.timesAverage = timesAverage;
		this.actionsAverageOk = actionsAverageOk;
		this.timesAverageOk = timesAverageOk;
		
		this.countSessionsSuccess = countSessionsSuccess;
		this.countSessionsError = countSessionsError;
		this.countSessionsRepeat = countSessionsRepeat;
		this.countSessionsThreshold = countSessionsThreshold;
		
		this.sessionsId = sessionsId;
		this.fuzzyPriority = fuzzyPriority;
		this.setCountSessions(countSessionsSuccess + countSessionsError + countSessionsRepeat + countSessionsThreshold);
		this.setRateSuccess(((double)countSessionsSuccess/this.getCountSessions())*100);
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public Double getActionsAverage() {
		return actionsAverage;
	}
	public void setActionsAverage(Double actionsAverage) {
		this.actionsAverage = actionsAverage;
	}
	public Double getTimesAverage() {
		return timesAverage;
	}
	public void setTimesAverage(Double timesAverage) {
		this.timesAverage = timesAverage;
	}
	public Integer getCountSessionsSuccess() {
		return countSessionsSuccess;
	}
	public void setCountSessionsSuccess(Integer countSessionsSuccess) {
		this.countSessionsSuccess = countSessionsSuccess;
	}
	public Integer getCountSessionsError() {
		return countSessionsError;
	}
	public void setCountSessionsError(Integer countSessionsError) {
		this.countSessionsError = countSessionsError;
	}
	public Integer getCountSessionsRepeat() {
		return countSessionsRepeat;
	}
	public void setCountSessionsRepeat(Integer countSessionsRepeat) {
		this.countSessionsRepeat = countSessionsRepeat;
	}
	public List<String> getSessionsId() {
		return sessionsId;
	}
	public void setSessionsId(List<String> sessionsId) {
		this.sessionsId = sessionsId;
	}

	public Integer getCountSessionsThreshold() {
		return countSessionsThreshold;
	}

	public void setCountSessionsThreshold(Integer countSessionsThreshold) {
		this.countSessionsThreshold = countSessionsThreshold;
	}

	public Double getRateSuccess() {
		return rateSuccess;
	}

	public void setRateSuccess(Double rateSuccess) {
		this.rateSuccess = rateSuccess;
	}

	public Integer getCountSessions() {
		return countSessions;
	}

	public void setCountSessions(Integer countSessions) {
		this.countSessions = countSessions;
	}

	public Double getFuzzyPriority() {
		return fuzzyPriority;
	}

	public void setFuzzyPriority(Double fuzzyPriority) {
		this.fuzzyPriority = fuzzyPriority;
	}

	public Double getActionsAverageOk() {
		return actionsAverageOk;
	}

	public void setActionsAverageOk(Double actionsAverageOk) {
		this.actionsAverageOk = actionsAverageOk;
	}

	public Double getTimesAverageOk() {
		return timesAverageOk;
	}

	public void setTimesAverageOk(Double timesAverageOk) {
		this.timesAverageOk = timesAverageOk;
	}

	public Double getMaxActionsAverage() {
		return maxActionsAverage;
	}

	public void setMaxActionsAverage(Double maxActionsAverage) {
		this.maxActionsAverage = maxActionsAverage;
	}

	public Double getMaxTimeAverage() {
		return maxTimeAverage;
	}

	public void setMaxTimeAverage(Double maxTimeAverage) {
		this.maxTimeAverage = maxTimeAverage;
	}

	public Double getMaxActionsAverageOk() {
		return maxActionsAverageOk;
	}

	public void setMaxActionsAverageOk(Double maxActionsAverageOk) {
		this.maxActionsAverageOk = maxActionsAverageOk;
	}

	public Double getMaxTimeAverageOk() {
		return maxTimeAverageOk;
	}

	public void setMaxTimeAverageOk(Double maxTimeAverageOk) {
		this.maxTimeAverageOk = maxTimeAverageOk;
	}
	
	public Double getUncompleteNormalized(){
		return 1 - (getRateSuccess() / 100);
	}
	
	public Double getActionsAvarageOkNormalized(){
		return (this.getActionsAverageOk() / this.getMaxActionsAverageOk());
	}
	
	public Double getTimesAvarageOkNormalized(){
		return (this.getTimesAverageOk() / this.getMaxTimeAverageOk());
	}
	
	public Double getActionsAvarageNormalized(){
		return (this.getActionsAverage() / this.getMaxActionsAverage());
	}
	
	public Double getTimesAvarageNormalized(){
		return (this.getTimesAverage() / this.getMaxTimeAverage());
	}
	
}