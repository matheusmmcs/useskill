package br.ufpi.datamining.models;

public class PageViewActionDataMining {

	private Long id;
	private String local;
	private String element;
	private String action;
	private Long time;
	
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
	
}
