/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpi.models;

import java.io.Serializable;
import java.util.Date;
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
		@NamedQuery(name = "Fluxo.obterfluxos", query = "select fluxo from Teste as t "
				+ "left join t.tarefas as tarefas "
				+ "left join tarefas.fluxos as fluxo "
				+ "where t.id= :teste "
				+ "and tarefas.id= :tarefa "
				+ "and t.usuarioCriador.id= :usuarioCriador "
				+ "and fluxo.usuario.id= :usuario order by  fluxo.tempoRealizacao desc"),
		@NamedQuery(name = "Fluxo.obterfluxo", query = "select fluxo from Teste as t "
				+ "left join t.tarefas as tarefas "
				+ "left join tarefas.fluxos as fluxo "
				+ "where t.id= :teste "
				+ "and tarefas.id= :tarefa "
				+ "and t.usuarioCriador.id= :usuarioCriador "
				+ "and fluxo.usuario.id= :usuario " + "and fluxo.id= :fluxo"),
		@NamedQuery(name = "Fluxo.obterActions", query = "select acao from Teste as t "
				+ "left join t.tarefas as tarefas "
				+ "left join tarefas.fluxos as fluxo "
				+ "left join fluxo.acoes as acao "
				+ "where t.id= :teste "
				+ "and tarefas.id= :tarefa "
				+ "and t.usuarioCriador.id= :usuarioCriador "
				+ "and fluxo.usuario.id= :usuario " + "and fluxo.id= :fluxo"),
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
		@NamedQuery(name = "Fluxo.getFluxos.Tarefa", query = "select new br.ufpi.models.vo.FluxoVO(fluxo.usuario.nome,fluxo.usuario.id,fluxo.dataRealizacao,fluxo.tempoRealizacao,fluxo.tipoConvidado) from Teste as t "
				+ "left join t.tarefas as tarefas "
				+ "left join tarefas.fluxos as fluxo "
				+ "where t.id= :teste "
				+ "and tarefas.id= :tarefa "
				+ "and t.usuarioCriador.id= :usuarioCriador GROUP BY fluxo.usuario.id"),
		/**
		 * Obtem todos os Fluxos de uma tarefa passando apenas o id da tarefa
		 * 
		 * @author Cleiton Moura
		 * 
		 */
		@NamedQuery(name = "Fluxo.obter.fluxos.Tarefa", query = "select new br.ufpi.models.vo.FluxoVO(fluxo.usuario.nome,fluxo.usuario.id,fluxo.dataRealizacao,fluxo.tempoRealizacao,fluxo.tipoConvidado) from Tarefa as tarefas "
				+ "left join tarefas.fluxos as fluxo "
				+ "where "
				+ "tarefas.id= :tarefa " + "GROUP BY fluxo.usuario.id"),
		/*
		 * obtem o total de fluxo de uma tarefa
		 * 
		 * Lista de Condições * 1º usuarioCriador ser dono do teste
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
		@NamedQuery(name = "Fluxo.quantidade.Acoes", query = "select new br.ufpi.models.vo.FluxoCountVO(flu.tempoRealizacao,(Select count(*) from Fluxo as flu2 left join flu2.acoes where flu2=flu)) "
				+ "from Fluxo as flu left join flu.acoes where flu.tarefa.id= :tarefa and flu.tipoConvidado= :tipoConvidado and flu.isFinished= true Group by flu.id"),
		@NamedQuery(name = "Fluxo.quantidade.Acoes.por.tipo", query = "select  (Select count(*) from Fluxo as flu2 left join flu2.acoes as action where flu2=flu and action.sActionType= :actionType) from Fluxo as flu where flu.tarefa.id= :tarefa and flu.tipoConvidado= :tipoConvidado and flu.isFinished= true  Group by flu.id"),
		
		@NamedQuery(name = "Fluxo.Acoes.por.tipos", query = "select acoes from Fluxo as flu left join flu.acoes as acoes where flu.id= :fluxo and flu.isFinished= true and acoes.sActionType in (:actionType)"),
		@NamedQuery(name = "Fluxos.tipo.convite.Acoes.por.tipos", query = "select flu from Fluxo as flu where flu.tarefa.id= :tarefaId and flu.tipoConvidado= :tipoConvidado and flu.isFinished= true"),
		/**
		 * Obtem a quantidade de fluxos que foram concluidos ou nao
		 * @author "Cleiton Moura"
		 * 
		 */
		@NamedQuery(name = "Fluxo.realizados", query = "select  count(*) "
				+ "from Fluxo as flu where flu.tarefa.id= :tarefa and flu.tipoConvidado= :tipoConvidado and flu.isFinished= :isFinished"),
		@NamedQuery(name = "Fluxo.fluxosdoUsuario", query = "select new br.ufpi.models.vo.FluxoVO(flu.id,flu.dataRealizacao,flu.tempoRealizacao,flu.tipoConvidado,(Select count(*) from Fluxo as flu2 left join flu2.acoes where flu2=flu)) "
				+ "from Fluxo as flu left join flu.acoes where flu.tarefa.id= :tarefa and flu.usuario.id= :usuario Group by flu.id"),
		@NamedQuery(name = "Fluxo.getNomeUsuario", query = "Select fluxo.usuario.nome from Fluxo fluxo left join fluxo.usuario where fluxo.id= :fluxo") })
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
	private boolean isFinished = true;
	/**
	 * Comentario de por que o usuário pulou a Tarefa
	 */
	@Column(columnDefinition = "LONGTEXT")
	private String comentario;

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

	public boolean isFinished() {
		return isFinished;
	}

	public void setFinished(boolean isFinished) {
		this.isFinished = isFinished;
	}

	/**
	 * @return the comentario
	 */
	public String getComentario() {
		return comentario;
	}

	/**
	 * @param comentario
	 *            the comentario to set
	 */
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

}
