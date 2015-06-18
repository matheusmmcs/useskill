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
	private String jhm;
	private String step;
	
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
	public String getStep() {
		return step;
	}
	public void setStep(String section) {
		this.step = section;
	}
	
	public String getJhm() {
		return jhm;
	}
	public void setJhm(String jhm) {
		this.jhm = jhm;
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
		fieldURL.setValue(this.getUrl().replaceAll(";jsessionid\\=[A-Z|0-9]*", "").replaceAll(":8080", "").replaceAll("www.", ""));
		urlsFields.add(fieldURL);
		
		FieldSearchTupleDataMining fieldJhm = new FieldSearchTupleDataMining();
		fieldJhm.setAction(action);
		fieldJhm.setField("sJhm");
		fieldJhm.setType(FieldTypeDataMiningEnum.STRING);
		fieldJhm.setValue(this.getJhm());
		urlsFields.add(fieldJhm);
		
		FieldSearchTupleDataMining fieldSection = new FieldSearchTupleDataMining();
		fieldSection.setAction(action);
		fieldSection.setField("sStepJhm");
		fieldSection.setType(FieldTypeDataMiningEnum.STRING);
		fieldSection.setValue(this.getStep());
		urlsFields.add(fieldSection);
		
		action.setUrlFieldSearch(urlsFields);
		//
		
		return action;
	}
	
}
