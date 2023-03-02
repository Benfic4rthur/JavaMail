package enviando.email;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class EnvioJavaMail extends JFrame {
	private JPanel contentPane;
	private JTextField emailSubjectField;
	private JTextArea emailMessageArea;
	private JTextField emailToField;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EnvioJavaMail frame = new EnvioJavaMail();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public EnvioJavaMail() {
		setTitle("Envio de email");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("Para:");
		lblNewLabel_1.setBounds(10, 10, 130, 14);
		contentPane.add(lblNewLabel_1);

		emailToField = new JTextField();
		emailToField.setBounds(90, 7, 424, 20);
		contentPane.add(emailToField);
		emailToField.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Assunto:");
		lblNewLabel_2.setBounds(10, 50, 130, 14);
		contentPane.add(lblNewLabel_2);

		emailSubjectField = new JTextField();
		emailSubjectField.setBounds(90, 47, 424, 20);
		contentPane.add(emailSubjectField);
		emailSubjectField.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("Mensagem:");
		lblNewLabel_3.setBounds(10, 88, 130, 14);
		contentPane.add(lblNewLabel_3);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(90, 88, 424, 294);
		contentPane.add(scrollPane);

		emailMessageArea = new JTextArea();
		scrollPane.setViewportView(emailMessageArea);

		JButton btnNewButton = new JButton("Enviar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = "seu-email-hotmail-aqui@hotmail.com"; // substitua com o seu email
				String password = "sua senha aqui"; // substitua com a sua senha
				String emailTo = emailToField.getText();
				String emailSubject = emailSubjectField.getText();
				String emailMessage = emailMessageArea.getText();

				if (username.isEmpty() || password.isEmpty() || emailTo.isEmpty() || emailSubject.isEmpty()
						|| emailMessage.isEmpty()) {
					JOptionPane.showMessageDialog(EnvioJavaMail.this, "Preencha todos os campos.");
					return;
				}

				// Configuração das propriedades
				String host = "smtp-mail.outlook.com";
				int port = 587;
				Properties props = new Properties();
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.starttls.enable", "true");
				props.put("mail.smtp.host", host);
				props.put("mail.smtp.port", port);
				props.put("mail.smtp.socketfactory.port", "465");
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
					message.setFrom(new InternetAddress(username));
					message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
					message.setSubject(emailSubject);
					message.setText(emailMessage);

					// Envia a mensagem
					Transport.send(message);

					JOptionPane.showMessageDialog(EnvioJavaMail.this, "Email enviado com sucesso!");

				} catch (MessagingException ex) {
					JOptionPane.showMessageDialog(EnvioJavaMail.this, "Erro ao enviar email: " + ex.getMessage());
				}

			}
		});
		btnNewButton.setBounds(170, 403, 89, 23);
		contentPane.add(btnNewButton);

		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				emailSubjectField.setText("");
				emailMessageArea.setText("");
			}
		});
		btnLimpar.setBounds(270, 403, 89, 23);
		contentPane.add(btnLimpar);
	}
}