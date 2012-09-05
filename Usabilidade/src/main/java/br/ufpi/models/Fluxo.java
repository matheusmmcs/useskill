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
	 @NamedQuery(name = "Fluxo.Tarefa.pertence.Teste.E.Usuario.Realizou.Fluxo", query = "select fluxo from Teste as t "
             + "left join t.tarefas as tarefas "
             + "left join tarefas.fluxos as fluxo "
             + "where t.id= :teste "
             + "and tarefas.id= :tarefa "
             + "and t.usuarioCriador.id= :usuarioCriador "
             + "and fluxo.usuario.id= :usuario"),})
@Entity
public class Fluxo implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataInicio;
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataFim;
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

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public String toString() {
		return "FluxoComponente [id=" + id + ", dataInicio=" + dataInicio
				+ ", dataFim=" + dataFim + "]";
	}

	public TipoConvidado getTipoConvidado() {
		return tipoConvidado;
	}

	public void setTipoConvidado(TipoConvidado tipoConvidado) {
		this.tipoConvidado = tipoConvidado;
	}

}
