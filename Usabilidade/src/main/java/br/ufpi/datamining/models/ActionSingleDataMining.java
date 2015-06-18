package br.ufpi.datamining.models;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.ufpi.datamining.models.enums.ActionTypeDataMiningEnum;
import br.ufpi.datamining.models.enums.MomentTypeActionDataMiningEnum;

@Entity  
@Table(name="datamining_actionsingle")  
public class ActionSingleDataMining implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private TaskDataMining task;

	private ActionTypeDataMiningEnum actionType;
	private MomentTypeActionDataMiningEnum momentType;
	
	private List<FieldSearchTupleDataMining> elementFiedlSearch;
	private List<FieldSearchTupleDataMining> urlFieldSearch;
	
	
	@Id  
	@GeneratedValue
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Enumerated(EnumType.STRING)
	public ActionTypeDataMiningEnum getActionType() {
		return actionType;
	}

	public void setActionType(ActionTypeDataMiningEnum actionType) {
		this.actionType = actionType;
	}

	@Enumerated(EnumType.STRING)
	public MomentTypeActionDataMiningEnum getMomentType() {
		return momentType;
	}

	public void setMomentType(MomentTypeActionDataMiningEnum momentType) {
		this.momentType = momentType;
	}

	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinTable(name="datamining_action_elementFieldSearchs")
	public List<FieldSearchTupleDataMining> getElementFiedlSearch() {
		return elementFiedlSearch;
	}

	public void setElementFiedlSearch(
			List<FieldSearchTupleDataMining> elementFiedlSearch) {
		this.elementFiedlSearch = elementFiedlSearch;
	}

	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinTable(name="datamining_action_urlFieldSearchs")
	public List<FieldSearchTupleDataMining> getUrlFieldSearch() {
		return urlFieldSearch;
	}

	public void setUrlFieldSearch(List<FieldSearchTupleDataMining> urlFieldSearch) {
		this.urlFieldSearch = urlFieldSearch;
	}
	
	@ManyToOne
    @JoinColumn(name="task_id")
	public TaskDataMining getTask() {
		return task;
	}
	
	public void setTask(TaskDataMining task){
		this.task = task;
	}
	
	public ActionDataMining toActionDataMining() throws SecurityException, NoSuchFieldException, IllegalArgumentException, IllegalAccessException{
		ActionDataMining action = new ActionDataMining();
		Class<?> c = action.getClass();
		action.setsActionType(this.getActionType().getAction());
		for(FieldSearchTupleDataMining f : this.getElementFiedlSearch()){
			Field declaredField = c.getDeclaredField(f.getField());
			declaredField.setAccessible(true);
			declaredField.set(action, f.valueToObject());
		}
		for(FieldSearchTupleDataMining f : this.getUrlFieldSearch()){
			Field declaredField = c.getDeclaredField(f.getField());
			declaredField.setAccessible(true);
			declaredField.set(action, f.valueToObject());
		}
		return action;
	}
}
