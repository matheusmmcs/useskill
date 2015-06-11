package br.ufpi.datamining.models.vo;

import java.util.ArrayList;
import java.util.List;

import br.ufpi.datamining.models.ActionSingleDataMining;
import br.ufpi.datamining.models.FieldSearchTupleDataMining;
import br.ufpi.datamining.models.TaskDataMining;
import br.ufpi.datamining.models.enums.ActionTypeDataMiningEnum;
import br.ufpi.datamining.models.enums.FieldTypeDataMiningEnum;
import br.ufpi.datamining.models.enums.MomentTypeActionDataMiningEnum;

public class ActionSingleJHeatDataMiningVO {
	
	private Long id;
	private TaskDataMining task;
	private ActionTypeDataMiningEnum actionType;
	private MomentTypeActionDataMiningEnum momentType;
	private String element;
	private String url;
	private String section;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public TaskDataMining getTask() {
		return task;
	}
	public void setTask(TaskDataMining task) {
		this.task = task;
	}
	public ActionTypeDataMiningEnum getActionType() {
		return actionType;
	}
	public void setActionType(ActionTypeDataMiningEnum actionType) {
		this.actionType = actionType;
	}
	public MomentTypeActionDataMiningEnum getMomentType() {
		return momentType;
	}
	public void setMomentType(MomentTypeActionDataMiningEnum momentType) {
		this.momentType = momentType;
	}
	public String getElement() {
		return element;
	}
	public void setElement(String element) {
		this.element = element;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = section;
	}
	
	public ActionSingleDataMining toActionSingleDataMining(){
		ActionSingleDataMining action = new ActionSingleDataMining();
		action.setId(this.getId());
		action.setActionType(this.getActionType());
		action.setMomentType(this.getMomentType());
		action.setTask(this.getTask());
		
		//add XPath
		List<FieldSearchTupleDataMining> elements = new ArrayList<FieldSearchTupleDataMining>();
		
		FieldSearchTupleDataMining fieldXPath = new FieldSearchTupleDataMining();
		fieldXPath.setAction(action);
		fieldXPath.setField("sXPath");
		fieldXPath.setType(FieldTypeDataMiningEnum.STRING);
		fieldXPath.setValue(this.getElement());
		elements.add(fieldXPath);
		
		action.setElementFiedlSearch(elements);
		//
		
		//add URL
		List<FieldSearchTupleDataMining> urlsFields = new ArrayList<FieldSearchTupleDataMining>();
		
		FieldSearchTupleDataMining fieldURL = new FieldSearchTupleDataMining();
		fieldURL.setAction(action);
		fieldURL.setField("sUrl");
		fieldURL.setType(FieldTypeDataMiningEnum.STRING);
		fieldURL.setValue(this.getUrl());
		urlsFields.add(fieldURL);
		
		FieldSearchTupleDataMining fieldSection = new FieldSearchTupleDataMining();
		fieldSection.setAction(action);
		fieldSection.setField("sSectionJhm");
		fieldSection.setType(FieldTypeDataMiningEnum.STRING);
		fieldSection.setValue(this.getSection());
		urlsFields.add(fieldSection);
		
		action.setUrlFieldSearch(urlsFields);
		//
		
		return action;
	}
	
}
