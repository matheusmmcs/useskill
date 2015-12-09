package br.ufpi.datamining.models.aux;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ResultDataMining {

	private Double actionsAverageOk;
	private Double timesAverageOk;
	
	private Integer countSessionsSuccess;
	private Integer countSessionsError;
	private Integer countSessionsRepeat;
	private Integer countSessionsThreshold;
	
	private Integer countSessions;
	private Double rateSuccess;
	private Double rateRequired;
	private HashMap<String, Integer> actionsRequiredTask;
	
	
	private Double meanActionsOk;
	private Double meanTimesOk;
	private Double stdDevActionsOk;
	private Double stdDevTimesOk;
	private Double maxActionsOk;
	private Double maxTimesOk;
	private Double minActionsOk;
	private Double minTimesOk;
	
	private HashMap<String, String> pageViewActionIds;
	private HashMap<String, Integer> pageViewActionCount;
	
	private HashMap<String, String> usersSequences;
	
	private List<UserResultDataMining> users;
	private List<SessionResultDataMining> sessions;
	
	public ResultDataMining() {
		this.users = new ArrayList<UserResultDataMining>();
		this.sessions = new ArrayList<SessionResultDataMining>();
	}
	
	public ResultDataMining(List<UserResultDataMining> users, List<SessionResultDataMining> sessions, 
			Double actionsAverageOk, Double timesAverageOk,
			Integer countSessionsSuccess, Integer countSessionsError, Integer countSessionsRepeat,
			Integer countSessionsThreshold, HashMap<String, String> pageViewActionIds, HashMap<String, Integer> pageViewActionCount) {
		this();
		this.users = users;
		this.sessions = sessions;
		this.actionsAverageOk = actionsAverageOk;
		this.timesAverageOk = timesAverageOk;
		
		this.countSessionsSuccess = countSessionsSuccess;
		this.countSessionsError = countSessionsError;
		this.countSessionsRepeat = countSessionsRepeat;
		this.countSessionsThreshold = countSessionsThreshold;
		
		this.setPageViewActionIds(pageViewActionIds);
		this.setPageViewActionCount(pageViewActionCount);
		
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

	public Double getMeanActionsOk() {
		return meanActionsOk;
	}

	public void setMeanActionsOk(Double meanActionsOk) {
		this.meanActionsOk = meanActionsOk;
	}

	public Double getMeanTimesOk() {
		return meanTimesOk;
	}

	public void setMeanTimesOk(Double meanTimesOk) {
		this.meanTimesOk = meanTimesOk;
	}

	public Double getStdDevActionsOk() {
		return stdDevActionsOk;
	}

	public void setStdDevActionsOk(Double stdDevActionsOk) {
		this.stdDevActionsOk = stdDevActionsOk;
	}

	public Double getStdDevTimesOk() {
		return stdDevTimesOk;
	}

	public void setStdDevTimesOk(Double stdDevTimesOk) {
		this.stdDevTimesOk = stdDevTimesOk;
	}

	public Double getMaxActionsOk() {
		return maxActionsOk;
	}

	public void setMaxActionsOk(Double maxActionsOk) {
		this.maxActionsOk = maxActionsOk;
	}

	public Double getMaxTimesOk() {
		return maxTimesOk;
	}

	public void setMaxTimesOk(Double maxTimesOk) {
		this.maxTimesOk = maxTimesOk;
	}

	public Double getMinActionsOk() {
		return minActionsOk;
	}

	public void setMinActionsOk(Double minActionsOk) {
		this.minActionsOk = minActionsOk;
	}

	public Double getMinTimesOk() {
		return minTimesOk;
	}

	public void setMinTimesOk(Double minTimesOk) {
		this.minTimesOk = minTimesOk;
	}

	public HashMap<String, String> getPageViewActionIds() {
		return pageViewActionIds;
	}

	public void setPageViewActionIds(HashMap<String, String> pageViewActionIds) {
		this.pageViewActionIds = pageViewActionIds;
	}

	public HashMap<String, Integer> getPageViewActionCount() {
		return pageViewActionCount;
	}

	public void setPageViewActionCount(HashMap<String, Integer> pageViewActionCount) {
		this.pageViewActionCount = pageViewActionCount;
	}

	public Double getRateRequired() {
		return rateRequired;
	}

	public void setRateRequired(Double rateRequired) {
		this.rateRequired = rateRequired;
	}

	public HashMap<String, Integer> getActionsRequiredTask() {
		return actionsRequiredTask;
	}

	public void setActionsRequiredTask(HashMap<String, Integer> actionsRequiredTask) {
		this.actionsRequiredTask = actionsRequiredTask;
	}

	public HashMap<String, String> getUsersSequences() {
		return usersSequences;
	}

	public void setUsersSequences(HashMap<String, String> usersSequences) {
		this.usersSequences = usersSequences;
	}
		
}
