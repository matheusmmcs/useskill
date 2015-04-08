/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpi.models;

import java.io.Serializable;

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

/**
 * 
 * @author Cleiton
 */
@NamedQueries({
		@NamedQuery(name = "RespostaEscrita.findPergunta", query = "select new br.ufpi.models.vo.RespostaEscritaVO(escrita.resposta,escrita.usuario.nome) "
				+ "from Pergunta as per "
				+ "left join per.respostaEscritas as escrita "
				+ "where per.id= :pergunta"),
		@NamedQuery(name = "RespostaEscrita.findPergunta.Count", query = "select count(per.id) "
				+ "from Pergunta as per "
				+ "left join per.respostaEscritas as escrita "
				+ "where per.id= :pergunta"),

})
@Entity
public class RespostaEscrita implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@ManyToOne(fetch = FetchType.LAZY)
	private Pergunta pergunta;
	@Column(columnDefinition = "LONGTEXT")
	private String resposta;
	@OneToOne
	private Usuario usuario;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Pergunta getPergunta() {
		return pergunta;
	}

	public void setPergunta(Pergunta pergunta) {
		this.pergunta = pergunta;
	}

	public String getResposta() {
		return resposta;
	}

	public void setResposta(String resposta) {
		this.resposta = resposta;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
