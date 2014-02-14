/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpi.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author Cleiton
 */
@Entity
public class Alternativa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank
    @Column(columnDefinition="TINYTEXT")
    private String textoAlternativa;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getTextoAlternativa() {
        return textoAlternativa;
    }

    public void setTextoAlternativa(String textoAlternativa) {
        this.textoAlternativa = textoAlternativa;
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Alternativa [id=" + id + ", textoAlternativa="
				+ textoAlternativa + "]";
	}
    
}
