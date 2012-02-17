/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpi.models;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author Cleiton
 */

@Entity
@DiscriminatorValue("2")
public class FluxoUsuario extends Fluxo{
    private static final long serialVersionUID = 1L;
   }
