package javaMailClassesSeparadas;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
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



public class EnvioJavaMailGUI extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField emailSubjectField;
	private JTextArea emailMessageArea;
	private JTextField emailToField;
	private JTextField deField;
	private JProgressBar progressBar;
	private Timer timer;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EnvioJavaMailGUI frame = new EnvioJavaMailGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public EnvioJavaMailGUI() {
		setTitle("Envio de email");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblDe = new JLabel("De:");
		lblDe.setBounds(10, 10, 46, 14);
		contentPane.add(lblDe);

		JLabel lblPara = new JLabel("Para:");
		lblPara.setBounds(10, 35, 46, 14);
		contentPane.add(lblPara);

		JLabel lblAssunto = new JLabel("Assunto:");
		lblAssunto.setBounds(10, 60, 66, 14);
		contentPane.add(lblAssunto);

		JLabel lblMensagem = new JLabel("Mensagem:");
		lblMensagem.setBounds(10, 85, 66, 14);
		contentPane.add(lblMensagem);

		emailSubjectField = new JTextField();
		emailSubjectField.setBounds(86, 57, 335, 20);
		contentPane.add(emailSubjectField);
		emailSubjectField.setColumns(10);

		emailToField = new JTextField();
		emailToField.setBounds(86, 32, 335, 20);
		contentPane.add(emailToField);
		emailToField.setColumns(10);

		deField = new JTextField();
		deField.setBounds(86, 7, 335, 20);
		contentPane.add(deField);
		deField.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(86, 82, 335, 202);
		contentPane.add(scrollPane);

		emailMessageArea = new JTextArea();
		scrollPane.setViewportView(emailMessageArea);

		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent arg0) {
		        enviarEmail();
		    }
		});
		btnEnviar.setBounds(10, 331, 89, 23);
		contentPane.add(btnEnviar);

		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setBounds(109, 331, 315, 23);
		contentPane.add(progressBar);

		timer = new Timer(500, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				int value = progressBar.getValue() + 10;
				if (value < 100) {
					progressBar.setValue(value);
				} else {
					timer.stop();
					JOptionPane.showMessageDialog(null, "Email enviado com sucesso!");
					progressBar.setValue(0);
				}
			}
		});

	}

	private void enviarEmail() {
	    String de = deField.getText();
	    String para = emailToField.getText().trim();
	    String assunto = emailSubjectField.getText();
	    String mensagem = emailMessageArea.getText();
	    String username = "seu email aqui@hotmail.com";
	    String password = "sua senha aqui";
	    if (de.isEmpty() || para.isEmpty() || assunto.isEmpty() || mensagem.isEmpty()) {
	        JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos.");
	        return;
	    }

	    EnvioJavaMail envio = new EnvioJavaMail(username, password, de, para, assunto, mensagem);
	    boolean enviadoComSucesso = envio.enviarEmail();
	    if (enviadoComSucesso) {
	        timer.start();
	    } else {
	        JOptionPane.showMessageDialog(null, "Ocorreu um erro ao enviar o email.");
	    }
	}

}