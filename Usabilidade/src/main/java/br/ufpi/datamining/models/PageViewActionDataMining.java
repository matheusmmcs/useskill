package br.ufpi.datamining.models;

import br.ufpi.datamining.components.PageViewDataMining;
import br.ufpi.datamining.models.enums.ActionTypeDataMiningEnum;

public class PageViewActionDataMining {

	private Long id;
	private String identifier;
	private String local;
	private String element;
	private String action;
	private Long time;
	
	//more info
	private String infoContent;
	private String infoTag;
	private String infoName;
	private Integer infoPosX;
	private Integer infoPosY;
	private Boolean required;
	
	public PageViewActionDataMining(ActionDataMining action) {
		super();
		this.id = action.getId();
		
		if ((action.getsJhm() == null || action.getsJhm().equals("")) ||
			(action.getsStepJhm() == null || action.getsStepJhm().equals(""))) {
			// ":" influencia na forma que o ActionSituationAux monta os FieldSearchTupleDataMining
			this.local = ":"+PageViewDataMining.getUrlFormatted(action.getsUrl()) + "-" + action.getsJhm() + "-" + action.getsStepJhm();
		} else {
			this.local = action.getsJhm() + "-" + action.getsStepJhm();
		}
		
		this.element = action.getsXPath();
		this.action = action.getsActionType();
		this.time = action.getsTime();
		
		this.infoContent = getContentAdjusted(action);
		this.infoTag = action.getsTag();
		this.infoName = action.getsName();
		this.infoPosX = action.getsPosX();
		this.infoPosY = action.getsPosY();
	}
	
	public static String getContentAdjusted (ActionDataMining action) {
		if (!action.getsActionType().equals(ActionTypeDataMiningEnum.focusout.getAction())){
			String result = action.getsContentText() != null ? action.getsContentText() : action.getsContent();
			return result != null ? result.trim() : null;
		}
		return "";
	}
	
	public PageViewActionDataMining(Long id, String local, String element,
			String action, Long time, String infoContent, String infoTag, 
			String infoName, Integer infoPosX, Integer infoPosY) {
		super();
		this.id = id;
		this.local = local;
		this.element = element;
		this.action = action;
		this.time = time;
		
		this.infoContent = infoContent;
		this.infoTag = infoTag;
		this.infoName = infoName;
		this.infoPosX = infoPosX;
		this.infoPosY = infoPosY;
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
	public String getInfoContent() {
		return infoContent;
	}
	public void setInfoContent(String infoContent) {
		this.infoContent = infoContent;
	}
	public String getInfoTag() {
		return infoTag;
	}
	public void setInfoTag(String infoTag) {
		this.infoTag = infoTag;
	}
	public String getInfoName() {
		return infoName;
	}
	public void setInfoName(String infoName) {
		this.infoName = infoName;
	}
	public Integer getInfoPosX() {
		return infoPosX;
	}
	public void setInfoPosX(Integer infoPosX) {
		this.infoPosX = infoPosX;
	}
	public Integer getInfoPosY() {
		return infoPosY;
	}
	public void setInfoPosY(Integer infoPosY) {
		this.infoPosY = infoPosY;
	}

	public Boolean isRequired() {
		return required;
	}

	public void setRequired(Boolean required) {
		this.required = required;
	}

}
