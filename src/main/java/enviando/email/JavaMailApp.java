package enviando.email;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class JavaMailApp extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField emailSubjectField;
	private JTextArea emailMessageArea;
	private JTextField emailToField;
	private JTextField deField;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JavaMailApp frame = new JavaMailApp();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public JavaMailApp() {
		setTitle("Envio de email");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(550, 500);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("De:");
		lblNewLabel_1.setBounds(10, 10, 130, 14);
		contentPane.add(lblNewLabel_1);

		deField = new JTextField();
		deField.setBounds(90, 7, 424, 20);
		contentPane.add(deField);
		deField.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Para ( ,):");
		lblNewLabel_2.setBounds(10, 40, 130, 14);
		contentPane.add(lblNewLabel_2);

		emailToField = new JTextField();
		emailToField.setBounds(90, 37, 424, 20);
		contentPane.add(emailToField);
		emailToField.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Assunto:");
		lblNewLabel_3.setBounds(10, 70, 130, 14);
		contentPane.add(lblNewLabel_3);

		emailSubjectField = new JTextField();
		emailSubjectField.setBounds(90, 67, 424, 20);
		contentPane.add(emailSubjectField);
		emailSubjectField.setColumns(10);

		JLabel lblNewLabel_4 = new JLabel("Mensagem:");
		lblNewLabel_4.setBounds(10, 108, 130, 14);
		contentPane.add(lblNewLabel_4);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(90, 108, 424, 294);
		contentPane.add(scrollPane);

		emailMessageArea = new JTextArea();
		emailMessageArea.setLineWrap(true); // adiciona a quebra de linha automática
		scrollPane.setViewportView(emailMessageArea);

		JButton btnNewButton = new JButton("Enviar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			    String username = "seu email@hotmail.com"; // substitua com o seu email
			    String password = "sua senha"; // substitua com a sua senha
			    String de = deField.getText().trim();
			    String emailTo = emailToField.getText().trim();
			    String[] emailToSplit = emailTo.split(",");
			    String emailSubject = emailSubjectField.getText();
			    String emailMessage = emailMessageArea.getText();

			    if (username.isEmpty() || password.isEmpty() || emailTo.isEmpty() || emailSubject.isEmpty()
			            || emailMessage.isEmpty()) {
			        JOptionPane.showMessageDialog(JavaMailApp.this, "Preencha todos os campos.");
			        return;
			    }
			    // Configuração das propriedades
			    String host = "smtp-mail.outlook.com";
			    int port = 587;
			    Properties props = new Properties();
			    props.put("mail.smtp.ssl.trust", "*"); /*autentica com segurança ssl*/
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
			        message.setFrom(new InternetAddress(username, de));
			        for (String address : emailToSplit) {
			            message.addRecipient(Message.RecipientType.TO, new InternetAddress(address.trim()));
			        }
			        message.setSubject(emailSubject);
			        message.setText(emailMessage);

			        // Envia a mensagem
			        Transport.send(message);
			        JScrollPane scrollPane = new JScrollPane();
					scrollPane.setBounds(90, 108, 424, 294);
					contentPane.add(scrollPane);
			        
			     // Criar JProgressBar para mostrar o loading
			        JProgressBar progressBar = new JProgressBar(0, 100);
			        progressBar.setIndeterminate(true);
			        progressBar.setString("Enviando E-mails...");
			        progressBar.setStringPainted(true);

			        // Criar JOptionPane com a barra de loading
			        JOptionPane optionPane = new JOptionPane(progressBar, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);
			        JDialog dialog = optionPane.createDialog(scrollPane, "Aguarde...");


			        // Iniciar Timer para fechar o JOptionPane após 3 segundos
			        Timer timer = new Timer(3000, (event) -> {
			            dialog.dispose();

			            JOptionPane.showMessageDialog(JavaMailApp.this, "Email enviado com sucesso!");
			        });
			        timer.setRepeats(false);
			        timer.start();

			        // Mostrar o JOptionPane com a barra de loading
			        dialog.setVisible(true);
			    
			    } catch (MessagingException ex) {
			        JOptionPane.showMessageDialog(JavaMailApp.this, "Erro ao enviar email!");
			    } catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
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