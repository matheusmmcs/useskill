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
public class Pergunta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String titulo;
    @Column(columnDefinition = "TEXT")
    private String texto;
    private boolean tipoRespostaAlternativa;
    @OneToMany(mappedBy = "pergunta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Alternativa> alternativas;
    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.MERGE)
    private Questionario questionario;
    @OneToMany(mappedBy = "pergunta", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RespostaAlternativa> respostaAlternativas;
    @OneToMany(mappedBy = "pergunta",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
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

    public void setRespostaAlternativas(List<RespostaAlternativa> respostaAlternativas) {
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

   
}
