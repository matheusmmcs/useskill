package br.ufpi.datamining.models;

import br.ufpi.datamining.components.PageViewDataMining;

public class PageViewActionDataMining {

	private Long id;
	private String identifier;
	private String local;
	private String element;
	private String action;
	private Long time;

	
	public PageViewActionDataMining(ActionDataMining action) {
		super();
		this.id = action.getId();
		//this.local = PageViewDataMining.getUrlFormatted(action.getsUrl()) + "-" + action.getsJhm() + "-" + action.getsStepJhm();
		this.local = action.getsJhm() + "-" + action.getsStepJhm();
		this.element = action.getsXPath();
		this.action = action.getsActionType();
		this.time = action.getsTime();
	}
	
	public PageViewActionDataMining(Long id, String local, String element,
			String action, Long time) {
		super();
		this.id = id;
		this.local = local;
		this.element = element;
		this.action = action;
		this.time = time;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}
	public String getElement() {
		return element;
	}
	public void setElement(String element) {
		this.element = element;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public String getPageViewActionUnique() {
		return this.getLocal() + " | " + this.getElement() + " | " + this.getAction();
	}
}
