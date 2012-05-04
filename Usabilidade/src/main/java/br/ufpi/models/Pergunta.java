/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpi.models;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

import org.hibernate.annotations.Cascade;
import org.hibernate.type.CalendarType;

/**
 * 
 * @author Cleiton
 */
@NamedQueries({
		@NamedQuery(name = "Pergunta.pertence.teste.usuario", query = "select p from Pergunta as p left join p.questionario left join p.questionario.teste left join p.questionario.teste.usuarioCriador where p.questionario.teste.id= :teste and  p.questionario.teste.usuarioCriador.id= :usuario and p.id= :pergunta and p.questionario.teste.liberado=false"),
		@NamedQuery(name = "Pergunta.delete.Alternativas", query = "delete Alternativa  a where a.pergunta.id= :pergunta"),
		@NamedQuery(name = "Pergunta.getQuestionario", query = "select p.questionario from Pergunta as p left join p.questionario as Questionario where p.id= :pergunta") })
@Entity
public class Pergunta implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String titulo;
	@Column(columnDefinition = "TEXT")
	private String texto;
	/**
	 * True para pergunta objetiva
	 */
	private Boolean tipoRespostaAlternativa;
	@OneToMany(mappedBy = "pergunta", cascade =CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Alternativa> alternativas;
	@ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.MERGE)
	private Questionario questionario;
	@OneToMany(mappedBy = "pergunta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<RespostaAlternativa> respostaAlternativas;
	@OneToMany(mappedBy = "pergunta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<RespostaEscrita> respostaEscritas;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<Alternativa> getAlternativas() {
		return alternativas;
	}

	public void setAlternativas(List<Alternativa> alternativas) {
		this.alternativas = alternativas;
	}

	public Questionario getQuestionario() {
		return questionario;
	}

	public void setQuestionario(Questionario questionario) {
		this.questionario = questionario;
	}

	public List<RespostaAlternativa> getRespostaAlternativas() {
		return respostaAlternativas;
	}

	public void setRespostaAlternativas(
			List<RespostaAlternativa> respostaAlternativas) {
		this.respostaAlternativas = respostaAlternativas;
	}

	public List<RespostaEscrita> getRespostaEscritas() {
		return respostaEscritas;
	}

	public void setRespostaEscritas(List<RespostaEscrita> respostaEscritas) {
		this.respostaEscritas = respostaEscritas;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

	public Boolean getTipoRespostaAlternativa() {
		return tipoRespostaAlternativa;
	}

	public void setTipoRespostaAlternativa(Boolean tipoRespostaAlternativa) {
		this.tipoRespostaAlternativa = tipoRespostaAlternativa;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public Pergunta clone() {
		Pergunta pergunta = new Pergunta();
		pergunta.setQuestionario(this.getQuestionario());
		pergunta.setAlternativas(this.getAlternativas());
		pergunta.setRespostaAlternativas(this.getRespostaAlternativas());
		
		return pergunta;

	}

	@Override
	public String toString() {
		return "Pergunta [id=" + id + ", titulo=" + titulo + ", texto=" + texto
				+ ", tipoRespostaAlternativa=" + tipoRespostaAlternativa
				+ ", alternativas=" + alternativas + ", questionario="
				+ questionario + ", respostaAlternativas="
				+ respostaAlternativas + ", respostaEscritas="
				+ respostaEscritas + "]";
	}
	
}
