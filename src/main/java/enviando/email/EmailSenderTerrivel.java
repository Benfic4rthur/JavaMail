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
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class EmailSenderTerrivel extends JFrame {
	private JPanel contentPane;
	private JTextField emailToField;
	private JTextField emailSubjectField;
	private JTextArea emailMessageArea;
	private JPasswordField emailPasswordField;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EmailSenderTerrivel frame = new EmailSenderTerrivel();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public EmailSenderTerrivel() {
		setTitle("Enviar email");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Endereço de email:");
		lblNewLabel.setBounds(10, 11, 130, 14);
		contentPane.add(lblNewLabel);

		emailToField = new JTextField();
		emailToField.setBounds(150, 8, 424, 20);
		contentPane.add(emailToField);
		emailToField.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Senha:");
		lblNewLabel_1.setBounds(10, 39, 130, 14);
		contentPane.add(lblNewLabel_1);

		emailPasswordField = new JPasswordField();
		emailPasswordField.setBounds(150, 36, 424, 20);
		contentPane.add(emailPasswordField);

		JLabel lblNewLabel_2 = new JLabel("Assunto:");
		lblNewLabel_2.setBounds(10, 70, 130, 14);
		contentPane.add(lblNewLabel_2);

		emailSubjectField = new JTextField();
		emailSubjectField.setBounds(150, 67, 424, 20);
		contentPane.add(emailSubjectField);
		emailSubjectField.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("Mensagem:");
		lblNewLabel_3.setBounds(10, 101, 130, 14);
		contentPane.add(lblNewLabel_3);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(150, 101, 424, 311);
		contentPane.add(scrollPane);

		emailMessageArea = new JTextArea();
		scrollPane.setViewportView(emailMessageArea);

		JButton btnNewButton = new JButton("Enviar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String emailTo = emailToField.getText();
				String emailPassword = new String(emailPasswordField.getPassword());
				String emailSubject = emailSubjectField.getText();
				String emailMessage = emailMessageArea.getText();

				if (emailTo.isEmpty() || emailPassword.isEmpty() || emailSubject.isEmpty() || emailMessage.isEmpty()) {
					JOptionPane.showMessageDialog(EmailSenderTerrivel.this, "Preencha todos os campos.");
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
						return new PasswordAuthentication(emailTo, emailPassword);
					}
				});

				try {
					// Cria a mensagem de email
					Message message = new MimeMessage(session);
					message.setFrom(new InternetAddress(emailTo));
					message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
					message.setSubject(emailSubject);
					message.setText(emailMessage);

					// Envia a mensagem
					Transport.send(message);

					JOptionPane.showMessageDialog(EmailSenderTerrivel.this, "Email enviado com sucesso!");

				} catch (MessagingException ex) {
					JOptionPane.showMessageDialog(EmailSenderTerrivel.this, "Erro ao enviar email: " + ex.getMessage());
				}

			}
		});
		btnNewButton.setBounds(10, 423, 89, 23);
		contentPane.add(btnNewButton);

		JButton btnLimpar = new JButton("Limpar");
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				emailToField.setText("");
				emailPasswordField.setText("");
				emailSubjectField.setText("");
				emailMessageArea.setText("");
			}
		});
		btnLimpar.setBounds(109, 423, 89, 23);
		contentPane.add(btnLimpar);
	}
}
