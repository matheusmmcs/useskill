/**
 * 
 */
package br.ufpi.models.roteiro;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

import org.hibernate.validator.constraints.NotBlank;

import br.ufpi.models.Tarefa;

/**
 * @author Matheus
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "VariavelRoteiro.findAll", query = "SELECT v FROM VariavelRoteiro as v WHERE v.deleted = false"),
	@NamedQuery(name = "VariavelRoteiro.findById", query = "SELECT v FROM VariavelRoteiro as v WHERE v.id = :id AND v.deleted = false"),
	@NamedQuery(name = "VariavelRoteiro.findByVariavel", query = "SELECT v FROM VariavelRoteiro as v WHERE v.variavel = :variavel AND v.deleted = false"),
	@NamedQuery(name = "VariavelRoteiro.pertence.Tarefa", query = "SELECT v FROM VariavelRoteiro as v LEFT JOIN v.tarefa WHERE v.tarefa.id= :tarefa AND v.id= :variavel AND v.deleted = false"),
	@NamedQuery(name = "VariavelRoteiro.pertence.Tarefa.com.NomeIgual", query = "SELECT v FROM VariavelRoteiro as v LEFT JOIN v.tarefa WHERE v.tarefa.id= :tarefa AND v.variavel = :nomeVariavel AND v.deleted = false")
})
public class VariavelRoteiro implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	@NotBlank
	private String variavel;
	
	@OneToMany(mappedBy = "variavelRoteiro", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ValorRoteiro> valores;
	
	@ManyToOne(optional = false, cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private Tarefa tarefa;
	
	@Column(nullable = false)
	private boolean deleted = false;
	
	/**
	 * @return the valores
	 */
	public List<ValorRoteiro> getValores() {
		return valores;
	}
	/**
	 * @param valores the valores to set
	 */
	public void setValores(List<ValorRoteiro> valores) {
		this.valores = valores;
	}
	/**
	 * @return the variavel
	 */
	public String getVariavel() {
		return variavel;
	}
	/**
	 * @param variavel the variavel to set
	 */
	public void setVariavel(String variavel) {
		this.variavel = variavel;
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	/**
	 * @return the tarefa
	 */
	public Tarefa getTarefa() {
		return tarefa;
	}
	/**
	 * @param tarefa the tarefa to set
	 */
	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}
	/**
	 * @return the deleted
	 */
	public boolean isDeleted() {
		return deleted;
	}
	/**
	 * @param deleted the deleted to set
	 */
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	
}
