/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpi.models;

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

import br.ufpi.models.roteiro.VariavelRoteiro;

/**
 * 
 * @author Cleiton
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "Tarefa.pertence.Teste.Usuario", query = "select t from Tarefa as t left join t.teste as test where t.teste.id= :teste and t.id= :tarefa and t.teste.usuarioCriador.id= :usuario "),
		@NamedQuery(name = "Tarefa.pertence.Teste.GetRoteiro", query = "select new java.lang.String(t.roteiro) from Tarefa as t left join t.teste as test where t.teste.id= :teste and t.id= :tarefa"),
		@NamedQuery(name = "Tarefa.pertence.Teste.GetVariaveis", query = "select t from Tarefa as t left join t.teste as test where t.teste.id= :teste and t.id= :tarefa"),
		@NamedQuery(name = "Tarefa.pertence.Teste.GetNome", query = "select new java.lang.String(t.nome) from Tarefa as t left join t.teste as test where t.teste.id= :teste and t.id= :tarefa"),
		@NamedQuery(name = "Tarefa.pertence.Teste.GetTarefaVO", query = "select new br.ufpi.models.vo.TarefaVO(t.roteiro,t.urlInicial,t.nome) from Tarefa as t left join t.teste as test where t.teste.id= :teste and t.id= :tarefa"),
		@NamedQuery(name = "Tarefa.pertence.Teste.Liberado.Usuario", query = "select t from Tarefa as t left join t.teste as test where t.teste.id= :teste and t.id= :tarefa and t.teste.usuarioCriador.id= :usuario and t.teste.liberado= true"),
		
		/**
		 * Usuario tem que ser dono do teste. o teste não pode ser liberado.
		 * Tarefa tem que pertencer ao teste.
		 * 
		 */
		@NamedQuery(name = "Tarefa.pertence.Teste.Nao.Liberado.Usuario", query = "select t from Tarefa as t left join t.teste as test where t.teste.id= :teste and t.id= :tarefa and t.teste.usuarioCriador.id= :usuario and t.teste.liberado=false") })
public class Tarefa implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank
	private String nome;
	@Column(columnDefinition = "LONGTEXT")
	@NotBlank
	private String roteiro;
	@OneToMany(mappedBy = "tarefa", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Impressao> impressoes;
	@OneToMany(mappedBy="tarefa",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Fluxo> fluxos;
	@OneToMany(mappedBy="tarefa",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<Comentario> comentarios;
	
	@OneToMany(mappedBy="tarefa",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	private List<VariavelRoteiro> variaveisRoteiro;
	
	@ManyToOne(cascade = CascadeType.REFRESH)
	private Teste teste;
	
	@Column(nullable = false)
	@NotBlank
	private String urlInicial;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Impressao> getImpressoes() {
		return impressoes;
	}

	public void setImpressoes(List<Impressao> impressoes) {
		this.impressoes = impressoes;
	}

	public Teste getTeste() {
		return teste;
	}

	public void setTeste(Teste teste) {
		this.teste = teste;
	}

	public String getRoteiro() {
		return roteiro;
	}

	public void setRoteiro(String roteiro) {
		this.roteiro = roteiro;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getUrlInicial() {
		return urlInicial;
	}

	public void setUrlInicial(String urlInicial) {
		this.urlInicial = urlInicial;
	}

	/**
	 * @return the fluxo
	 */
	public List<Fluxo> getFluxos() {
		return fluxos;
	}

	/**
	 * @param fluxo the fluxo to set
	 */
	public void setFluxos(List<Fluxo> fluxo) {
		this.fluxos = fluxo;
	}

	/**
	 * @return the comentarios
	 */
	public List<Comentario> getComentarios() {
		return comentarios;
	}

	/**
	 * @param comentarios the comentarios to set
	 */
	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	/**
	 * @return the variaveisRoteiro
	 */
	public List<VariavelRoteiro> getVariaveisRoteiro() {
		return variaveisRoteiro;
	}

	/**
	 * @param variaveisRoteiro the variaveisRoteiro to set
	 */
	public void setVariaveisRoteiro(List<VariavelRoteiro> variaveisRoteiro) {
		this.variaveisRoteiro = variaveisRoteiro;
	}
	
}
