/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpi.models;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

/**
 * 
 * @author Cleiton
 */
@Entity
public class Teste implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@OneToMany(mappedBy = "teste",cascade=CascadeType.ALL)
	private List<Tarefa> tarefas;
	@OneToOne(cascade = CascadeType.ALL)
	private Questionario caracterizacao;
	@OneToOne(cascade = CascadeType.ALL)
	private Questionario satisfacao;
	@ManyToMany
	List<Usuario> usuariosParticipantes;
	@ManyToOne
	private Usuario usuarioCriador;
	/**
	 * Titulo mostrado apenas para o Criador.
	 */
	@Column(length=200)
	private String titulo;
	/**
	 * Titulo maior usado para informa os usuarios participantes
	 */
	@Column(length=200)
	private String tituloPublico;
	@Column(columnDefinition="TiNYTEXT")
	private String textoIndroducao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public List<Tarefa> getTarefas() {
		return tarefas;
	}

	public void setTarefas(List<Tarefa> tarefas) {
		this.tarefas = tarefas;
	}

	public Usuario getUsuarioCriador() {
		return usuarioCriador;
	}

	public void setUsuarioCriador(Usuario usuarioCriador) {
		this.usuarioCriador = usuarioCriador;
	}

	public List<Usuario> getUsuariosParticipantes() {
		return usuariosParticipantes;
	}

	public void setUsuariosParticipantes(List<Usuario> usuariosParticipantes) {
		this.usuariosParticipantes = usuariosParticipantes;
	}

	public Questionario getCaracterizacao() {
		return caracterizacao;
	}

	public void setCaracterizacao(Questionario caracterizacao) {
		this.caracterizacao = caracterizacao;
	}

	public Questionario getSatisfacao() {
		return satisfacao;
	}

	public void setSatisfacao(Questionario satisfacao) {
		this.satisfacao = satisfacao;
	}

	public String getTextoIndroducao() {
		return textoIndroducao;
	}

	public void setTextoIndroducao(String textoIndroducao) {
		this.textoIndroducao = textoIndroducao;
	}

	public String getTituloPublico() {
		return tituloPublico;
	}

	public void setTituloPublico(String tituloPublico) {
		this.tituloPublico = tituloPublico;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	@Override
	public String toString() {
		return "Teste [id=" + id + ", tarefas=" + tarefas + ", caracterizacao="
				+ caracterizacao + ", satisfacao=" + satisfacao
				+ ", usuarioCriador=" + usuarioCriador + ", titulo=" + titulo
				+ ", tituloPublico=" + tituloPublico + ", textoIndroducao="
				+ textoIndroducao + "]";
	}

}
