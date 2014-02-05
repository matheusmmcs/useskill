/**
 * 
 */
package br.ufpi.models.roteiro;

import java.io.Serializable;

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
import javax.persistence.OneToOne;

import org.hibernate.validator.constraints.NotBlank;

import br.ufpi.models.Fluxo;

/**
 * @author Matheus
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "ValorRoteiro.findAll", query = "SELECT v FROM ValorRoteiro v"),
	@NamedQuery(name = "ValorRoteiro.findById", query = "SELECT v FROM ValorRoteiro v WHERE v.id = :id"),
	@NamedQuery(name = "ValorRoteiro.findByValor", query = "SELECT v FROM ValorRoteiro v WHERE v.valor = :valor"),
	@NamedQuery(name = "ValorRoteiro.findByFluxo", query = "SELECT v FROM ValorRoteiro v WHERE v.fluxo = :fluxo"),
	@NamedQuery(name = "ValorRoteiro.findByVariavelRoteiro", query = "SELECT v FROM ValorRoteiro v WHERE v.variavelRoteiro = :variavelRoteiro"),

	@NamedQuery(name = "ValorRoteiro.findByVariavelRoteiro.Count", query = "SELECT count(*) FROM ValorRoteiro v WHERE v.variavelRoteiro = :variavelRoteiro"),
	@NamedQuery(name = "ValorRoteiro.findByVariavelRoteiro.Livre.Count", query = "SELECT count(*) FROM ValorRoteiro v WHERE v.variavelRoteiro = :variavelRoteiro AND v.emUtilizacao = false AND v.fluxo IS NULL")
})
public class ValorRoteiro implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	@NotBlank
	private String valor;
	
	/**
	 * Boolean para garantir que não haja outro fluxo utilizando este valor
	 */
	@NotBlank
	private boolean emUtilizacao = false;
	
	/**
	 * Utilizado para determinar se já foi utilizado em algum fluxo ou não
	 */
	@OneToOne
	private Fluxo fluxo;
	
	@ManyToOne(optional = false, cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private VariavelRoteiro variavelRoteiro;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public boolean isEmUtilizacao() {
		return emUtilizacao;
	}

	public void setEmUtilizacao(boolean emUtilizacao) {
		this.emUtilizacao = emUtilizacao;
	}

	public Fluxo getFluxo() {
		return fluxo;
	}

	public void setFluxo(Fluxo fluxo) {
		this.fluxo = fluxo;
	}

	/**
	 * @return the variavelRoteiro
	 */
	public VariavelRoteiro getVariavelRoteiro() {
		return variavelRoteiro;
	}

	/**
	 * @param variavelRoteiro the variavelRoteiro to set
	 */
	public void setVariavelRoteiro(VariavelRoteiro variavelRoteiro) {
		this.variavelRoteiro = variavelRoteiro;
	}
	
}
