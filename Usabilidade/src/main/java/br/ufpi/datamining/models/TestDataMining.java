package br.ufpi.datamining.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.ufpi.models.Teste;
import br.ufpi.models.Usuario;

@Entity  
@Table(name="datamining_test")
@NamedQueries({
	@NamedQuery(name = "Testes.Usuario", query = "SELECT t FROM TestDataMining t WHERE :user MEMBER OF t.users"),
	@NamedQuery(name = "Teste.Usuario", query = "SELECT t FROM TestDataMining t WHERE t.id= :test and :user MEMBER OF t.users")
})
public class TestDataMining implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String title;
	private String clientAbbreviation;
	private String urlSystem;
	private List<TaskDataMining> tasks;
	private List<Usuario> users;
	private Usuario userCreated;
	private List<EvaluationTestDataMining> evaluations;
	
	private Teste testControl;
	private Boolean isControl = false;
	

	@Id  
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(nullable = false)
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@Column(nullable = false)
	public String getClientAbbreviation() {
		return clientAbbreviation;
	}
	
	public void setClientAbbreviation(String clientAbbreviation) {
		this.clientAbbreviation = clientAbbreviation;
	}
	
	@Column(nullable = false)
	public String getUrlSystem() {
		return urlSystem;
	}
	public void setUrlSystem(String urlSystem) {
		this.urlSystem = urlSystem;
	}

	@OneToMany(mappedBy="testDataMining", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	public List<TaskDataMining> getTasks() {
		return tasks;
	}

	public void setTasks(List<TaskDataMining> tasks) {
		this.tasks = tasks;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
	public List<Usuario> getUsers() {
		return users;
	}
	public void setUsers(List<Usuario> users) {
		this.users = users;
	}

	@ManyToOne(optional = false, cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	public Usuario getUserCreated() {
		return userCreated;
	}

	public void setUserCreated(Usuario userCreated) {
		this.userCreated = userCreated;
	}
	
	@OneToMany(mappedBy="test", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	public List<EvaluationTestDataMining> getEvaluations() {
		return evaluations;
	}

	public void setEvaluations(List<EvaluationTestDataMining> evaluations) {
		this.evaluations = evaluations;
	}

	@Column(nullable = false)
	public Boolean getIsControl() {
		return isControl;
	}

	public void setIsControl(Boolean isControl) {
		this.isControl = isControl;
	}

	@JoinColumn(name="testControl_id", nullable = true)
	@OneToOne(fetch = FetchType.LAZY)
	public Teste getTestControl() {
		return testControl;
	}

	public void setTestControl(Teste testControl) {
		this.testControl = testControl;
	}
		
}
