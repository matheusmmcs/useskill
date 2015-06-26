package br.ufpi.datamining.models.aux;

import java.util.List;

public class UserResultDataMining {
	
	private String username;
	private Double actionsAverage;
	private Double timesAverage;
	private Integer countSessionsSuccess;
	private Integer countSessionsError;
	private Integer countSessionsRepeat;
	private Integer countSessionsThreshold;
	private Double rateSuccess;
	private Integer countSessions;
	private List<String> sessionsId;
	
	public UserResultDataMining(String username, Double actionsAverage,
			Double timesAverage, Integer countSessionsSuccess,
			Integer countSessionsError, Integer countSessionsRepeat,
			Integer countSessionsThreshold, List<String> sessionsId) {
		super();
		this.username = username;
		this.actionsAverage = actionsAverage;
		this.timesAverage = timesAverage;
		this.countSessionsSuccess = countSessionsSuccess;
		this.countSessionsError = countSessionsError;
		this.countSessionsRepeat = countSessionsRepeat;
		this.countSessionsThreshold = countSessionsThreshold;
		this.sessionsId = sessionsId;
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
	
}