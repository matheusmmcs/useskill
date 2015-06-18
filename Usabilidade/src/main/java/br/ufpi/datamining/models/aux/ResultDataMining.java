package br.ufpi.datamining.models.aux;

import java.util.ArrayList;
import java.util.List;

public class ResultDataMining {

	private List<UserResultDataMining> users;
	private List<SessionResultDataMining> sessions;
	
	public ResultDataMining() {
		this.users = new ArrayList<UserResultDataMining>();
		this.sessions = new ArrayList<SessionResultDataMining>();
	}
	
	public ResultDataMining(List<UserResultDataMining> users,
			List<SessionResultDataMining> sessions) {
		this();
		this.users = users;
		this.sessions = sessions;
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
		
}
