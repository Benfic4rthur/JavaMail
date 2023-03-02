package enviando.email;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;

public class AppTest {
	public static void main(String[] args) {

		// Configuração do servidor SMTP do Hotmail
		String host = "smtp-mail.outlook.com";
		int port = 587;
		String username = "arthur_benfica_appteste@hotmail.com";
		String password = "@Testesenha2525";

		// Configuração das propriedades
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		props.put("mail.smtp.socketfactory.port", "465");
		props.put("mail.smtp.socketfactory.class", "javax.net.ssl.SSLSocketFactory");

		// Criação da sessão
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			// Criação da mensagem
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("arthur_benfica_appteste@hotmail.com"));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse("arthur_benfica@hotmail.com"));
			message.setSubject("Testando envio de email pelo Hotmail com JavaMail");
			message.setText("Olá, isso é um teste!");

			// Envio da mensagem
			Transport.send(message);

			System.out.println("E-mail enviado com sucesso!");

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}