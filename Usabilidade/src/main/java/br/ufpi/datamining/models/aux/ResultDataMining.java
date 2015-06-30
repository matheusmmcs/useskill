package br.ufpi.datamining.models.aux;

import java.util.ArrayList;
import java.util.List;

public class ResultDataMining {

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
	
	private List<UserResultDataMining> users;
	private List<SessionResultDataMining> sessions;
	
	public ResultDataMining() {
		this.users = new ArrayList<UserResultDataMining>();
		this.sessions = new ArrayList<SessionResultDataMining>();
	}
	
	public ResultDataMining(List<UserResultDataMining> users, List<SessionResultDataMining> sessions, 
			Double actionsAverage, Double timesAverage, Double actionsAverageOk, Double timesAverageOk,
			Integer countSessionsSuccess, Integer countSessionsError, Integer countSessionsRepeat,
			Integer countSessionsThreshold) {
		this();
		this.users = users;
		this.sessions = sessions;
		this.actionsAverage = actionsAverage;
		this.timesAverage = timesAverage;
		this.actionsAverageOk = actionsAverageOk;
		this.timesAverageOk = timesAverageOk;
		
		this.countSessionsSuccess = countSessionsSuccess;
		this.countSessionsError = countSessionsError;
		this.countSessionsRepeat = countSessionsRepeat;
		this.countSessionsThreshold = countSessionsThreshold;
		
		this.setCountSessions(countSessionsSuccess + countSessionsError + countSessionsRepeat + countSessionsThreshold);
		this.setRateSuccess(((double)countSessionsSuccess/this.getCountSessions())*100);
	}
	
	public List<UserResultDataMining> getUsers() {
		return users;
	}
	public void setUsers(List<UserResultDataMining> users) {
		this.users = users;
	}
	public List<SessionResultDataMining> getSessions() {
		return sessions;
	}
	public void setSessions(List<SessionResultDataMining> sessions) {
		this.sessions = sessions;
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
		
}
