package enviando.email;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

public class JavamailDoZero {

	@org.junit.Test
	public void testeEmail() {

		// Configuração do servidor SMTP do Hotmail
		String host = "smtp-mail.outlook.com";
		int port = 587;
		String username = "seu-email-aqui@hotmail.com"; // substitua com o seu email
		String password = "sua senha aqui"; // substitua com a sua senha
		try {
		// Configuração das propriedades
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true"); /*autorização*/
		props.put("mail.smtp.starttls.enable", "true"); /*autenticação*/
		props.put("mail.smtp.host", host); /*servidor*/
		props.put("mail.smtp.port", port); /*porta do servidor*/
		props.put("mail.smtp.socketfactory.port", "465");/*especifica a porta a ser conectada pelo socket*/
		props.put("mail.smtp.socketfactory.class", "javax.net.ssl.SSLSocketFactory");/*classe do java usada para a conexão*/
		
		Session session = Session.getInstance(props, new Authenticator() {
		@Override
		protected PasswordAuthentication getPasswordAuthentication() {
			return new PasswordAuthentication(username, password);
		}
		});
		
		Address[] toUser = InternetAddress.parse("arthur_benfica@hotmail.com , natacha-inacio@hotmail.com");
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(username)); /*Quem esta enviando*/
		message.setRecipients(Message.RecipientType.TO, toUser);/*email de destino*/
		message.setSubject("Chegou email enviado com java");
		message.setText("corpo do email");
		
		Transport.send(message);
		} catch (Exception e) {
			// TODO: handle exception
		}
	
		

	}

}
