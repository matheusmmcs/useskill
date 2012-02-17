/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpi.models;

import javax.persistence.*;

/**
 *
 * @author Cleiton
 */
@Entity
@DiscriminatorValue("1")
public class FluxoIdeal extends Fluxo {
   private static final long serialVersionUID = 1L;
}
