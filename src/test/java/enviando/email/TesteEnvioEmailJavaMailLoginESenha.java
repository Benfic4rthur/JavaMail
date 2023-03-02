package enviando.email;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class TesteEnvioEmailJavaMailLoginESenha {
	public static void main(String[] args) {

		int enviar = JOptionPane.showConfirmDialog(null, "Deseja enviar um email?");
		
		if(enviar == 0) {
			// Configuração do servidor SMTP do Hotmail
			String host = "smtp-mail.outlook.com";
			int port = 587;
			//String username = "seu-email-hotmail-aqui@hotmail.com"; // substitua com o seu email
			//String password = "sua senha aqui"; // substitua com a sua senha
			String username = JOptionPane.showInputDialog("Digite o seu endereço de e-mail:");
			String password = JOptionPane.showInputDialog("Digite a senha do seu e-mail:");

			// Configuração das propriedades
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", host);
			props.put("mail.smtp.port", port);
			props.put("mail.smtp.socketfactory.port", "465");
			props.put("mail.smtp.socketfactory.class", "javax.net.ssl.SSLSocketFactory");
			String enderecoEmail = JOptionPane.showInputDialog("Digite o e-mail para qual quer fazer o envio:");
			String assuntoEmail = JOptionPane.showInputDialog("Digite o Assunto do e-mail:");
			JTextArea mensagemEmail = new JTextArea(10, 50);
			JScrollPane scrollPane = new JScrollPane(mensagemEmail);
			JOptionPane.showMessageDialog(null, scrollPane, "Digite a mensagem do e-mail:", JOptionPane.INFORMATION_MESSAGE);

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
				message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(enderecoEmail));
				message.setSubject(assuntoEmail);
				message.setText(mensagemEmail.getText());

				// Envio da mensagem
				Transport.send(message);

				JOptionPane.showMessageDialog(null, "E-mail enviado com sucesso!");

			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Envio de e-mail cancelado!");
		}
	}
}