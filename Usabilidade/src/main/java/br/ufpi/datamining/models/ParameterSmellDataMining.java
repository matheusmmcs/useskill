package br.ufpi.datamining.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity  
@Table(name="datamining_parameter_smell")
@NamedQueries({
	@NamedQuery(name = "Parameter.Smell", query = "SELECT p FROM ParameterSmellDataMining p WHERE p.idSmell = :idSmell")
})
public class ParameterSmellDataMining implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private Long idSmell;
	private String description;
	
	@Id  
	@GeneratedValue
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(nullable = false)
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	@Column(nullable = false)
	public Long getIdSmell() {
		return idSmell;
	}

	public void setIdSmell(Long idSmell) {
		this.idSmell = idSmell;
	}
	
}
