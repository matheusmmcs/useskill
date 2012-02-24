/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpi.util;

import java.net.URL;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 *Classe configurada para enviar email-s, podendo adicionar anexos;
 * @author Cleiton
 */
public class EmailUtils {

    private static final String HOSTNAME = "smtp.gmail.com";
    private static final String USERNAME = "cleitonmoura18@gmail.com";
    private static final String PASSWORD = "";
    private static final String EMAILORIGEM = "cleitonmoura18@gmail.com";
    private HtmlEmail email;

    public EmailUtils() {
        email = new HtmlEmail();
    }
    /*
     * configura o Smtp e a senha para enviar email;
     */

    private void conectaEmail() throws EmailException {
        email.setHostName(HOSTNAME);
        email.setSmtpPort(587);
        email.setAuthenticator(new DefaultAuthenticator(USERNAME, PASSWORD));
        email.setTLS(true);
        email.setFrom(EMAILORIGEM);
    }

    /**
     * Envia mensagem.
     * @param mensagem Passar mensagem todo formatado em estilo HTML
     * @throws EmailException 
     */
    public void enviaEmail(Mensagem mensagem) throws EmailException {
        conectaEmail();
        email.setSubject(mensagem.getTitulo());
        email.setMsg(mensagem.getMensagem());
        email.addTo(mensagem.getDestino());
        email.send();


    }

    /**
     * Adiciona anexos ao email a ser enviado.
     * @param path caminho em que o arquivo se encontra
     * @param descricao do arquivo 
     * @param nome Nome do arquivo com sua extensão
     * @throws EmailException 
     */
    public void addAnexo(String path, String descricao, String nome) throws EmailException {
        EmailAttachment attachment = new EmailAttachment();
        attachment.setPath(path); //caminho da imagem
        attachment.setDisposition(EmailAttachment.ATTACHMENT);
        attachment.setDescription(descricao);
        attachment.setName(nome);
        email.attach(attachment);
    }

    /**
     *  Adiciona anexos ao email a ser enviado, anexos que estao na web.
     * @param path url de arquivos que esta na WEb
     * @param descricao
     * @param nome Adicionar nome do arquivo com sua extensão
     * @throws EmailException 
     */
    public void addAnexo(URL path, String descricao, String nome) throws EmailException {
        EmailAttachment attachment = new EmailAttachment();
        attachment.setURL(path); //caminho da imagem
        attachment.setDisposition(EmailAttachment.ATTACHMENT);
        attachment.setDescription(descricao);
        attachment.setName(nome);
        email.attach(attachment);
    }
}
