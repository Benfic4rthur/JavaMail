package javaMailClassesSeparadas;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

public class EnvioJavaMailGUIComProfissao extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField deField;
	private JTextField telefoneField;
	private JTextField cargoField;
	private JTextField emailToField;
	private JTextField emailSubjectField;
	private JTextArea emailMessageArea;
	private JProgressBar progressBar;
	private Timer timer;
	private JButton btnAnexar;
	private JTextField anexoField;
	private File anexo;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EnvioJavaMailGUIComProfissao frame = new EnvioJavaMailGUIComProfissao();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public EnvioJavaMailGUIComProfissao() {
		setTitle("Envio de email");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 470, 380);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		/*Labels*/

		JLabel lblde = new JLabel("Nome:");
		lblde.setBounds(10, 10, 66, 14);
		contentPane.add(lblde);
		
		JLabel lblFone = new JLabel("Telefone:");
		lblFone.setBounds(10, 35, 96, 14);
		contentPane.add(lblFone);
		
		JLabel lblCargo = new JLabel("Profissão:");
		lblCargo.setBounds(10, 60, 96, 14);
		contentPane.add(lblCargo);

		JLabel lblPara = new JLabel("Para:");
		lblPara.setBounds(10, 85, 46, 14);
		contentPane.add(lblPara);

		JLabel lblAssunto = new JLabel("Assunto:");
		lblAssunto.setBounds(10, 110, 66, 14);
		contentPane.add(lblAssunto);

		JLabel lblMensagem = new JLabel("Mensagem:");
		lblMensagem.setBounds(10, 135, 66, 14);
		contentPane.add(lblMensagem);
		
				
		/*Text Fields*/
		
		deField = new JTextField();
		deField.setBounds(106, 7, 335, 20);
		contentPane.add(deField);
		deField.setColumns(10);
		telefoneField = new JTextField();
		telefoneField.setBounds(106, 32, 335, 20);
		contentPane.add(telefoneField);
		telefoneField.setColumns(10);
		
		cargoField = new JTextField();
		cargoField.setBounds(106, 57, 335, 20);
		contentPane.add(cargoField);
		cargoField.setColumns(10);
		
		emailToField = new JTextField();
		emailToField.setBounds(106, 82, 335, 20);
		contentPane.add(emailToField);
		emailToField.setColumns(10);
		
		emailSubjectField = new JTextField();
		emailSubjectField.setBounds(106, 107, 335, 20);
		contentPane.add(emailSubjectField);
		emailSubjectField.setColumns(10);
		
		/*Mensagem*/
		
		emailMessageArea = new JTextArea();
		emailMessageArea.setBounds(106, 132, 335, 113);
		contentPane.add(emailMessageArea);
		
		/*Botão para anexar arquivo*/
		
		btnAnexar = new JButton("Anexar");
		btnAnexar.setBounds(10, 265, 89, 23);
		contentPane.add(btnAnexar);
		
		anexoField = new JTextField();
		anexoField.setEditable(false);
		anexoField.setBounds(106, 266, 335, 20);
		contentPane.add(anexoField);
		anexoField.setColumns(10);
		/*Listener do botão de anexo*/
		
		btnAnexar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				int returnVal = chooser.showOpenDialog(EnvioJavaMailGUIComProfissao.this);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					anexo = chooser.getSelectedFile();
					anexoField.setText(anexo.getName());
				}
			}
		});

		JButton btnEnviar = new JButton("Enviar");
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					enviarEmail();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		btnEnviar.setBounds(10, 300, 89, 23);
		contentPane.add(btnEnviar);

		progressBar = new JProgressBar();
		progressBar.setStringPainted(true);
		progressBar.setBounds(109, 301, 335, 23);
		progressBar.setValue(0); // Definindo valor inicial como zero
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
	private void enviarEmail() throws IOException, Exception {
		 String telefone = telefoneField.getText();
		    telefone = telefone.replaceAll("[^0-9]", ""); // remove todos os caracteres não numéricos
		    if (telefone.length() >= 2) {
		        telefone = "(" + telefone.substring(0, 2) + ")" + telefone.substring(2);
		    }
		    if (telefone.length() >= 9) {
		        telefone = telefone.substring(0, 8) + "-" + telefone.substring(8);
		    }
		    telefoneField.setText(telefone);
		String cargo = cargoField.getText();
		String de = deField.getText();
		String para = emailToField.getText();
		String assunto = emailSubjectField.getText();
		String mensagem = emailMessageArea.getText();
		String username = "seu email hotmail aqui";
		String password = "sua senha aqui";
		StringBuilder stringBuildermensagemEmail = new StringBuilder();
		stringBuildermensagemEmail.append("<html><body style='background-color:#F5F5F5;'>");
		stringBuildermensagemEmail.append("<div style='background-color:#FFFFFF;padding:20px;'>");
		stringBuildermensagemEmail
		.append("<h1 style='color:#333;font-size:22px;font-weight:bold;margin-top:0;'>" + assunto + "</h1>");
		stringBuildermensagemEmail
				.append("<h1 style='color:#333;font-size:16px;font-weight:bold;margin-top:0;'>" + mensagem + "</h1>");
		stringBuildermensagemEmail.append("<p style='margin-bottom:20px;font-size:14px;'>");
		stringBuildermensagemEmail.append("<br>");
		stringBuildermensagemEmail.append("<span style='font-size:14px;font-weight:bold;'>"+de+"</span><br>");
		stringBuildermensagemEmail.append(cargo +"<br>");
		stringBuildermensagemEmail.append(telefone + "<br>");
		stringBuildermensagemEmail.append("“A grandeza não consiste em receber honras, mas em merecê-las.”</p>");
		stringBuildermensagemEmail.append(
				"<p style='color:#888;'>Obs.: As informações contidas neste e-mail e nos arquivos anexados podem ser informações confidenciais ou privilegiadas.<br>");
		stringBuildermensagemEmail.append(
				"Caso você não seja o destinatário correto, apague o conteúdo desta mensagem e notifique o remetente imediatamente.<br>");
		stringBuildermensagemEmail.append(
				"<span style='color:#008000;'>Antes de imprimir pense em sua responsabilidade e compromisso com o Meio Ambiente!</span></p>");
		stringBuildermensagemEmail.append("</div>");
		stringBuildermensagemEmail.append(
				"<a href='#' style='display:inline-block;background-color:#4CAF50;color:#fff;padding:10px 20px;text-decoration:none;border-radius:5px;'>Responder</a>");
		stringBuildermensagemEmail.append("</body></html>");
		if (de.isEmpty() || para.isEmpty() || assunto.isEmpty() || mensagem.isEmpty() || telefone.isEmpty() || cargo.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos.");
			return;
		}
		
		if (anexo == null || !anexo.exists()) {
			int anexoemail =  JOptionPane.showConfirmDialog(null, "Deseja prossegui sem anexo?");
		if(anexoemail == 0) {
				EnvioJavaMail envioSemAnexo = new EnvioJavaMail(username, password, de, para, assunto, stringBuildermensagemEmail.toString());
				boolean enviadoComSucessoSemAnexo = envioSemAnexo.enviarEmail(true);
				if (enviadoComSucessoSemAnexo) {
					timer.start();
				} else {
					JOptionPane.showMessageDialog(null, "Ocorreu um erro ao enviar o email.");
				}
				}else {
				JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos.");
				return;
			}
		}else if(anexo != null || anexo.exists()) {
		EnvioJavaMail envio = new EnvioJavaMail(username, password, de, para, assunto, stringBuildermensagemEmail.toString(), anexo);
		boolean enviadoComSucesso = envio.enviarEmailAnexo(true);
		if (enviadoComSucesso) {
			timer.start();
		} else {
			JOptionPane.showMessageDialog(null, "Ocorreu um erro ao enviar o email.");
		}
		}
	}
}