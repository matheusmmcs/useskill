/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpi.util;

/**
 *Usada na Hora de enviar o email
 * @author Cleiton
 */
public class Mensagem {

    private String destino;
    private String titulo;
    private String mensagem;

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

	@Override
	public String toString() {
		return "Mensagem [destino=" + destino + ", titulo=" + titulo
				+ ", mensagem=" + mensagem + "]";
	}
    
}
