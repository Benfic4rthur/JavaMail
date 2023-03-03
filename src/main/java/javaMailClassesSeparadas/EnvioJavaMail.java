package javaMailClassesSeparadas;

import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.lang.model.element.NestingKind;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class EnvioJavaMail {
	private String username = "";
	private String password = "";
	private String de = "";
    private String[] para;
    private String assunto = "";
    private String mensagem = "";


    public EnvioJavaMail(String username, String password, String de, String para, String assunto, String mensagem) {
    	this.username = username;
    	this.password = password;
        this.de = de;
        this.para = para.split(",");
        this.assunto = assunto;
        this.mensagem = mensagem;
    }

    public boolean enviarEmail(boolean envioHtml) {
        boolean enviadoComSucesso = false;
     // Configuração das propriedades
        
	    String host = "smtp-mail.outlook.com";
	    int port = 587;
	    Properties props = new Properties();
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.smtp.host", host);
	    props.put("mail.smtp.port", port);
	    props.put("mail.smtp.socketfactory.class", "javax.net.ssl.SSLSocketFactory");

	    // Autenticação do usuário
	    Session session = Session.getInstance(props, new javax.mail.Authenticator() {
	        protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(username, password);
	        }
        });

	    try {
	        // Cria a mensagem de email
	        Message message = new MimeMessage(session);
	        message.setFrom(new InternetAddress(username, de));
	        for (String address : para) {
	            message.addRecipient(Message.RecipientType.TO, new InternetAddress(address.trim()));
	        }
	        message.setSubject(assunto);
	        if (envioHtml) {
	        	message.setContent(mensagem, "text/html; charset=utf8");
	        }else {
				message.setText(mensagem);
			}
	          // Envia a mensagem
	        Transport.send(message);
            enviadoComSucesso = true;
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return enviadoComSucesso;
    }
}