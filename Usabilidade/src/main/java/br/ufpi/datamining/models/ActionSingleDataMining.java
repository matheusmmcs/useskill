package br.ufpi.datamining.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import br.ufpi.datamining.models.enums.ActionTypeDataMiningEnum;
import br.ufpi.datamining.models.enums.MomentTypeActionDataMiningEnum;

@Entity  
@Table(name="datamining_actionsingle")  
public class ActionSingleDataMining implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;

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

	@OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	public List<FieldSearchTupleDataMining> getElementFiedlSearch() {
		return elementFiedlSearch;
	}

	public void setElementFiedlSearch(
			List<FieldSearchTupleDataMining> elementFiedlSearch) {
		this.elementFiedlSearch = elementFiedlSearch;
	}

	@OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	public List<FieldSearchTupleDataMining> getUrlFieldSearch() {
		return urlFieldSearch;
	}

	public void setUrlFieldSearch(List<FieldSearchTupleDataMining> urlFieldSearch) {
		this.urlFieldSearch = urlFieldSearch;
	}
	
}
