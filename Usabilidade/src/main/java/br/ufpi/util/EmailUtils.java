/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ufpi.util;

import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import br.ufpi.models.Teste;
import br.ufpi.models.Usuario;

/**
 * Classe configurada para enviar email-s, podendo adicionar anexos;
 * 
 * @author Cleiton
 */
public class EmailUtils {

	private static final String HOSTNAME = "smtp.gmail.com";
	private static final String USERNAME = "useskill.contato@gmail.com";
	private static final String PASSWORD = "cl123456";
	private static final String EMAILORIGEM = "useskill.contato@gmail.com";
	private static final String DEBUG = "false";

	public EmailUtils() {
	}
	
	@SuppressWarnings("unused")
	private Properties configTLS(){
		Properties props = System.getProperties();
	    props.put("mail.smtp.starttls.enable", true);
	    props.put("mail.smtp.host", HOSTNAME);
	    props.put("mail.smtp.port", "587");
	    props.put("mail.smtp.auth", true);
	    props.put("mail.debug", DEBUG);
	    
	    return props;
	}
	
	private Properties configSSL(){
		Properties props = System.getProperties();
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.host", HOSTNAME);
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.auth", "true");
		props.put("mail.debug", DEBUG);
	    return props;
	}
	
	/**
	 * Envia mensagem.
	 * 
	 * @param mensagem
	 *            Passar mensagem todo formatado em estilo HTML
	 * @throws EmailException
	 */
	public void enviaEmail(Mensagem mensagem) {
		/*
		Properties props = this.configSSL();
		
	    Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(USERNAME, PASSWORD);
			}
		});

	    MimeMessage message = new MimeMessage(session);
	    System.out.println("Port: "+session.getProperty("mail.smtp.port"));
	    
	    try {
	        InternetAddress from = new InternetAddress(EMAILORIGEM);
	        message.setSubject(mensagem.getTitulo());
	        message.setFrom(from);
	        message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(mensagem.getDestino()));

	        // Create a multi-part to combine the parts
	        Multipart multipart = new MimeMultipart();//"alternative"

	        // Create your text message part
	        BodyPart messageBodyPart = new MimeBodyPart();
	        messageBodyPart.setText(mensagem.getMensagem());

	        // Add the text part to the multipart
	        multipart.addBodyPart(messageBodyPart);

	        // Create the html part
	        //messageBodyPart = new MimeBodyPart();
	        //String htmlMessage = "Our html text";
	        //messageBodyPart.setContent(htmlMessage, "text/html");

	        // Add html part to multi part
	        //multipart.addBodyPart(messageBodyPart);

	        
	        // Associate multi-part with message
	        message.setContent(multipart);

	        // Send message
	        Transport transport = session.getTransport("smtp");
	        transport.connect("smtp.gmail.com", "username", "password");
	        System.out.println("Transport: "+transport.toString());
	        transport.sendMessage(message, message.getAllRecipients());


	    } catch (AddressException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    } catch (MessagingException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	    }
	    */
	}


	/**
	 * Envia email de Confirmação.
	 * 
	 * @param pessoa
	 */
	public void enviarEmailConfirmacao(Usuario pessoa) {
		Mensagem mensagem = new Mensagem();
		mensagem.setDestino(pessoa.getEmail());
		mensagem.setTitulo("Cadastro Useskill");
		mensagem.setMensagem("Para Confirmar seu cadastro na Useskill acesse: " + BaseUrl.BASEURL + "/confirmar/" + pessoa.getConfirmacaoEmail());

		this.enviaEmail(mensagem);
	}

	/**
	 * Envia email com a nova senha para o usuario
	 * 
	 * @param pessoa
	 * @param senha
	 */
	public void enviarNovaSenha(Usuario pessoa, String senha) {
		Mensagem mensagem = new Mensagem();
		mensagem.setDestino(pessoa.getEmail());
		mensagem.setTitulo("Nova Senha - Useskill");
		mensagem.setMensagem("Sua senha foi redefinida na Useskill. Sua Nova senha: \"" + senha + "\"");

		this.enviaEmail(mensagem);
	}

	/*
	 * Envia email para os Usuarios selecionados para a realização de um teste
	 */
	public void enviarConviteTeste(Usuario pessoa, Teste teste) {
		Mensagem mensagem = new Mensagem();
		mensagem.setDestino(pessoa.getEmail());
		mensagem.setTitulo("Você foi convidado a participar de um teste na UseSkill");
		mensagem.setMensagem("");

		this.enviaEmail(mensagem);
	}
}
