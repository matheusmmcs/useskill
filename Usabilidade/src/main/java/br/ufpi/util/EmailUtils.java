/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpi.util;

import br.ufpi.models.Teste;
import br.ufpi.models.Usuario;
import java.net.URL;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

/**
 * Classe configurada para enviar email-s, podendo adicionar anexos;
 * 
 * @author Cleiton
 */
public class EmailUtils {

	private static final String HOSTNAME = "smtp.gmail.com";
	private static final String USERNAME = "usabilitool@gmail.com";
	private static final String PASSWORD = "cl123456";
	private static final String EMAILORIGEM = "usabilitool@gmail.com";
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
	 * 
	 * @param mensagem
	 *            Passar mensagem todo formatado em estilo HTML
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
	 * 
	 * @param path
	 *            caminho em que o arquivo se encontra
	 * @param descricao
	 *            do arquivo
	 * @param nome
	 *            Nome do arquivo com sua extensão
	 * @throws EmailException
	 */
	public void addAnexo(String path, String descricao, String nome)
			throws EmailException {
		EmailAttachment attachment = new EmailAttachment();
		attachment.setPath(path); // caminho da imagem
		attachment.setDisposition(EmailAttachment.ATTACHMENT);
		attachment.setDescription(descricao);
		attachment.setName(nome);
		email.attach(attachment);
	}

	/**
	 * Adiciona anexos ao email a ser enviado, anexos que estao na web.
	 * 
	 * @param path
	 *            url de arquivos que esta na WEb
	 * @param descricao
	 * @param nome
	 *            Adicionar nome do arquivo com sua extensão
	 * @throws EmailException
	 */
	public void addAnexo(URL path, String descricao, String nome)
			throws EmailException {
		EmailAttachment attachment = new EmailAttachment();
		attachment.setURL(path); // caminho da imagem
		attachment.setDisposition(EmailAttachment.ATTACHMENT);
		attachment.setDescription(descricao);
		attachment.setName(nome);
		email.attach(attachment);
	}

	public void enviarEmailConfirmacao(Usuario pessoa) {
		System.out.println("Enviar Email de Confirmação");
		Mensagem mensagem = new Mensagem();
		mensagem.setDestino(pessoa.getEmail());
		mensagem.setTitulo("Teste");
		mensagem.setMensagem(BaseUrl.BASEURL + "/confirmar/"
				+ pessoa.getConfirmacaoEmail());

		try {
			this.enviaEmail(mensagem);
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}

	public void enviarNovaSenha(Usuario pessoa, String senha) {
		// String header = request.getHeader("Host");
		// header=header+request.getContextPath();
		// System.out.println("header"+header);
		Mensagem mensagem = new Mensagem();
		mensagem.setDestino(pessoa.getEmail());
		mensagem.setTitulo("Teste");
		mensagem.setMensagem("Sua Nova senha:" + senha);

		try {
			this.enviaEmail(mensagem);
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}

	public void enviarConviteTeste(Usuario pessoa, Teste teste) {
		Mensagem mensagem = new Mensagem();
		mensagem.setDestino(pessoa.getEmail());
		mensagem.setTitulo("Você foi confidado para participar do Teste");
		mensagem.setMensagem("");

		try {
			this.enviaEmail(mensagem);
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}
}
