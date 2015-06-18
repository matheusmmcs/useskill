package br.ufpi.datamining.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import br.ufpi.datamining.models.enums.FieldTypeDataMiningEnum;

@Entity  
@Table(name="datamining_fieldsearchtuple")
public class FieldSearchTupleDataMining implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private ActionSingleDataMining action;
	private String field;
	private String value;
	private FieldTypeDataMiningEnum type;
	
	@Id  
	@GeneratedValue
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	@Enumerated(EnumType.STRING)
	public FieldTypeDataMiningEnum getType() {
		return type;
	}
	public void setType(FieldTypeDataMiningEnum type) {
		this.type = type;
	}
	
	@ManyToOne
    @JoinColumn(name="action_id")
	public ActionSingleDataMining getAction() {
		return action;
	}
	
	public void setAction(ActionSingleDataMining action){
		this.action = action;
	}
	
	public Object valueToObject(){
		if(type.equals(FieldTypeDataMiningEnum.BOOLEAN)){
			return Boolean.parseBoolean(value);
		}else if(type.equals(FieldTypeDataMiningEnum.NUMBER)){
			return Double.parseDouble(value);
		}else if(type.equals(FieldTypeDataMiningEnum.CHAR)){
			return value.charAt(0);
		}else{
			return value;
		}
	}
}
