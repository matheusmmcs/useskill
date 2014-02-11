/**
 * 
 */
package br.ufpi.models.roteiro;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
import br.ufpi.models.Usuario;
import br.ufpi.models.enums.SituacaoDeUsoEnum;

/**
 * @author Matheus
 *
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "ValorRoteiro.findAll", query = "SELECT v FROM ValorRoteiro v WHERE v.deleted = false"),
	@NamedQuery(name = "ValorRoteiro.findById", query = "SELECT v FROM ValorRoteiro v WHERE v.id = :id AND v.deleted = false"),
	@NamedQuery(name = "ValorRoteiro.findByValor", query = "SELECT v FROM ValorRoteiro v WHERE v.valor = :valor AND v.deleted = false"),
	@NamedQuery(name = "ValorRoteiro.findByVariavel.Situacao", query = "SELECT v FROM ValorRoteiro v WHERE v.variavelRoteiro.id = :variavel AND v.situacaoDeUso = :situacao AND v.deleted = false"),
	@NamedQuery(name = "ValorRoteiro.findByVariavel.Usuario.Situacao", query = "SELECT v FROM ValorRoteiro v WHERE v.variavelRoteiro.id = :variavel AND v.usuario.id = :usuario AND v.situacaoDeUso = :situacao AND v.deleted = false"),
	@NamedQuery(name = "ValorRoteiro.findByTarefa.Usuario.Situacao", query = "SELECT v FROM ValorRoteiro v WHERE v.variavelRoteiro.tarefa.id = :tarefa AND v.usuario.id = :usuario AND v.situacaoDeUso = :situacao AND v.deleted = false"),
	@NamedQuery(name = "ValorRoteiro.findByTeste.Usuario.Situacao", query = "SELECT v FROM ValorRoteiro v WHERE v.variavelRoteiro.tarefa.teste.id = :teste AND v.usuario.id = :usuario AND v.situacaoDeUso = :situacao AND v.deleted = false"),
	@NamedQuery(name = "ValorRoteiro.findByFluxo", query = "SELECT v FROM ValorRoteiro v WHERE v.fluxo = :fluxo AND v.deleted = false"),
	@NamedQuery(name = "ValorRoteiro.findByVariavelRoteiro", query = "SELECT v FROM ValorRoteiro v WHERE v.variavelRoteiro = :variavelRoteiro AND v.deleted = false"),

	@NamedQuery(name = "ValorRoteiro.findByVariavelRoteiro.Count", query = "SELECT count(*) FROM ValorRoteiro v WHERE v.variavelRoteiro = :variavelRoteiro AND v.deleted = false"),
	@NamedQuery(name = "ValorRoteiro.findByVariavelRoteiro.Livre.Count", query = "SELECT count(*) FROM ValorRoteiro v WHERE v.variavelRoteiro = :variavelRoteiro AND v.fluxo IS NULL AND v.situacaoDeUso = br.ufpi.models.enums.SituacaoDeUsoEnum.LIVRE AND v.deleted = false")
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
	 * Situacao para garantir que não haja outro fluxo utilizando este valor
	 */
	@Enumerated(EnumType.STRING)
	private SituacaoDeUsoEnum situacaoDeUso = SituacaoDeUsoEnum.LIVRE;
	
	/**
	 * Caso não esteja livre, buscar por algum em execução por esse usuario
	 */
	@OneToOne
	private Usuario usuario;
	
	/**
	 * Utilizado para determinar se já foi utilizado em algum fluxo ou não
	 */
	@OneToOne
	private Fluxo fluxo;
	
	@ManyToOne(optional = false, cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private VariavelRoteiro variavelRoteiro;
	
	@Column(nullable = false)
	private boolean deleted = false;

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

	/**
	 * @return the situacaoDeUso
	 */
	public SituacaoDeUsoEnum getSituacaoDeUso() {
		return situacaoDeUso;
	}

	/**
	 * @param situacaoDeUso the situacaoDeUso to set
	 */
	public void setSituacaoDeUso(SituacaoDeUsoEnum situacaoDeUso) {
		this.situacaoDeUso = situacaoDeUso;
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

	/**
	 * @return the usuario
	 */
	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
