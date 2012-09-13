/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpi.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author Cleiton
 */
@NamedQueries({

		/*
		 * obtem uma lista de fluxo em que o usuario participou
		 * 
		 * Lista de Condições
		 * 
		 * 1º usuarioCriador ser dono do teste
		 * 
		 * 2º tarefa pertencer ao teste
		 * 
		 * 3º usuario ter realizado o fluxo
		 */
		@NamedQuery(name = "Fluxo.obterfluxo", query = "select fluxo from Teste as t "
				+ "left join t.tarefas as tarefas "
				+ "left join tarefas.fluxos as fluxo "
				+ "where t.id= :teste "
				+ "and tarefas.id= :tarefa "
				+ "and t.usuarioCriador.id= :usuarioCriador "
				+ "and fluxo.usuario.id= :usuario"),
		/*
		 * obtem uma lista de fluxo de uma tarefa
		 * 
		 * Lista de Condições
		 * 
		 * 1º usuarioCriador ser dono do teste
		 * 
		 * 2º tarefa pertencer ao teste
		 * 
		 * 3º usuario ter realizado o fluxo
		 */
		@NamedQuery(name = "Fluxo.getFluxos.Tarefa", query = "select new br.ufpi.models.vo.FluxoVO(fluxo.usuario.nome,fluxo.usuario.id,fluxo.dataRealizacao,fluxo.tempoRealizacao) from Teste as t "
				+ "left join t.tarefas as tarefas "
				+ "left join tarefas.fluxos as fluxo "
				+ "where t.id= :teste "
				+ "and tarefas.id= :tarefa "
				+ "and t.usuarioCriador.id= :usuarioCriador "),
		/*
		 * obtem o total de fluxo de uma tarefa
		 * 
		 * Lista de Condições
		 * 
		 * 1º usuarioCriador ser dono do teste
		 * 
		 * 2º tarefa pertencer ao teste
		 * 
		 * 3º usuario ter realizado o fluxo
		 */

		@NamedQuery(name = "Fluxo.getFluxos.Tarefa.Count", query = "select count(*) from Teste as t "
				+ "left join t.tarefas as tarefas "
				+ "left join tarefas.fluxos as fluxo "
				+ "where t.id= :teste "
				+ "and tarefas.id= :tarefa "
				+ "and t.usuarioCriador.id= :usuarioCriador "),
		@NamedQuery(name = "Fluxo.getFluxos.Tarefa.Lista.Tempo", query = "select fluxo.tempoRealizacao from Teste as t "
				+ "left join t.tarefas as tarefas "
				+ "left join tarefas.fluxos as fluxo "
				+ "where t.id= :teste "
				+ "and tarefas.id= :tarefa "
				+ "and t.usuarioCriador.id= :usuarioCriador "), })
@Entity
public class Fluxo implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataRealizacao;
	private Long tempoRealizacao;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Action> acoes;
	@ManyToOne(optional = false, cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
	private Usuario usuario;
	@ManyToOne(fetch = FetchType.LAZY)
	private Tarefa tarefa;
	private TipoConvidado tipoConvidado;

	/**
	 * @return the tarefa
	 */
	public Tarefa getTarefa() {
		return tarefa;
	}

	/**
	 * @param tarefa
	 *            the tarefa to set
	 */
	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Action> getAcoes() {
		return acoes;
	}

	public void setAcoes(List<Action> acoes) {
		this.acoes = acoes;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the dataRealizacao
	 */
	public Date getDataRealizacao() {
		return dataRealizacao;
	}

	/**
	 * @param dataRealizacao
	 *            the dataRealizacao to set
	 */
	public void setDataRealizacao(Date dataRealizacao) {
		this.dataRealizacao = dataRealizacao;
	}

	/**
	 * @return the tempoRealicao
	 */
	public Long getTempoRealizacao() {
		return tempoRealizacao;
	}

	/**
	 * @param tempoRealicao
	 *            the tempoRealicao to set
	 */
	public void setTempoRealizacao(Long tempoRealicao) {
		this.tempoRealizacao = tempoRealicao;
	}

	public TipoConvidado getTipoConvidado() {
		return tipoConvidado;
	}

	public void setTipoConvidado(TipoConvidado tipoConvidado) {
		this.tipoConvidado = tipoConvidado;
	}

}
