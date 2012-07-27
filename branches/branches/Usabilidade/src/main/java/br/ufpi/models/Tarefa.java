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
import javax.persistence.OneToOne;

import org.hibernate.validator.constraints.NotBlank;

/**
 * 
 * @author Cleiton
 */

@Entity
@NamedQueries({
		@NamedQuery(name = "Tarefa.pertence.Teste.Usuario", query = "select t from Tarefa as t left join t.teste where t.teste.id= :teste and t.id= :tarefa and t.teste.usuarioCriador.id= :usuario "),
		@NamedQuery(name = "Tarefa.pertence.Teste.Liberado.Usuario", query = "select t from Tarefa as t left join t.teste where t.teste.id= :teste and t.id= :tarefa and t.teste.usuarioCriador.id= :usuario and t.teste.liberado= false"),
		/**
		 * Usuario tem que ser dono do teste. o teste não pode ser liberado.
		 * Tarefa tem que pertencer ao teste.
		 * 
		 */
		@NamedQuery(name = "Tarefa.pertence.Teste.Nao.Liberado.Usuario", query = "select t from Tarefa as t left join t.teste where t.teste.id= :teste and t.id= :tarefa and t.teste.usuarioCriador.id= :usuario and t.teste.liberado=false") })
public class Tarefa implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank
	private String nome;
	@Column(columnDefinition = "TINYTEXT")
	@NotBlank
	private String roteiro;
	@OneToMany(mappedBy = "tarefa", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Impressao> impressoes;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<FluxoIdeal> fluxoIdeais;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<FluxoUsuario> fluxoUsuarios;
	@ManyToOne(cascade = CascadeType.REFRESH)
	private Teste teste;
	@Column(nullable = false)
	@NotBlank
	private String urlInicial;
	private boolean fluxoIdealPreenchido;
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = true)
	private Questionario questionario;

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

	public boolean isFluxoIdealPreenchido() {
		return fluxoIdealPreenchido;
	}

	public void setFluxoIdealPreenchido(boolean fluxoIdealPreenchido) {
		this.fluxoIdealPreenchido = fluxoIdealPreenchido;
	}

	public String getUrlInicial() {
		return urlInicial;
	}

	public void setUrlInicial(String urlInicial) {
		this.urlInicial = urlInicial;
	}


	/**
	 * Usado para obeter a lista de perguntas de uma determinada Tarefa
	 * 
	 * @return Questionario da tarefa contendo todas as perguntas referente a
	 *         tarefa
	 */
	public Questionario getQuestionario() {
		return questionario;
	}

	public void setQuestionario(Questionario questionario) {
		this.questionario = questionario;
	}

	public List<FluxoIdeal> getFluxoIdeais() {
		return fluxoIdeais;
	}

	public void setFluxoIdeais(List<FluxoIdeal> fluxoIdeais) {
		this.fluxoIdeais = fluxoIdeais;
	}

	public List<FluxoUsuario> getFluxoUsuarios() {
		return fluxoUsuarios;
	}

	public void setFluxoUsuarios(List<FluxoUsuario> fluxoUsuarios) {
		this.fluxoUsuarios = fluxoUsuarios;
	}

}
