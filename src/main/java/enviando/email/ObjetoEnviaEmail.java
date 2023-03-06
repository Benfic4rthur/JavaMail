package enviando.email;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;


public class ObjetoEnviaEmail {

	// Configuração do servidor SMTP do Hotmail
	private String host = "smtp-mail.outlook.com";
	private int port = 587;
	private String username = ""; // substitua com o seu email
	private String password = ""; // substitua com a sua senha
	private String listaDestinatarios = "";
	private String nomeRemetente = "";
	private String assuntoEmail = "";
	private String mensagemEmail = "";
	
	public ObjetoEnviaEmail(String listaDestinatario, String nomeRemetente, String assuntoEmail, String mensagemEmail) {
		this.listaDestinatarios = listaDestinatario;
		this.nomeRemetente = nomeRemetente;
		this.assuntoEmail = assuntoEmail;
		this.mensagemEmail = mensagemEmail;
	}
	
	public void enviarEmail() throws MessagingException, UnsupportedEncodingException {
		// Configuração das propriedades
		Properties props = new Properties();
		props.put("mail.smtp.ssl.trust", "*"); /* autentica com segurança ssl */
		props.put("mail.smtp.auth", "true"); /* autorização */
		props.put("mail.smtp.starttls.enable", "true"); /* autenticação */
		props.put("mail.smtp.host", host); /* servidor */
		props.put("mail.smtp.port", port); /* porta do servidor */
		props.put("mail.smtp.socketfactory.port", "465");/* especifica a porta a ser conectada pelo socket */
		props.put("mail.smtp.socketfactory.class",
				"javax.net.ssl.SSLSocketFactory");/* classe do java usada para a conexão */

		Session session = Session.getInstance(props, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		Address[] toUser = InternetAddress.parse(listaDestinatarios);
		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(username , nomeRemetente)); /* Quem esta enviando */
		message.setRecipients(Message.RecipientType.TO, toUser);/* email de destino */
		message.setSubject(assuntoEmail); /*assunto do email*/
		message.setText(mensagemEmail);/*texto do email*/

		Transport.send(message);
	}
		/*Esse metodo simula o pdf ou um anexo*/
		private FileInputStream simuladordePdf() throws Exception{
			Document docmDocument = new Document();
			File file = new File("anexo.pdf");
			file.createNewFile();
			PdfWriter.getInstance(docmDocument, new FileOutputStream(file));
			docmDocument.open();
			docmDocument.add(new Paragraph(mensagemEmail));
			docmDocument.close();
			return new FileInputStream(file);
		}
	}
