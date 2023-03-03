package enviando.email;

public class AppTeste {

	@org.junit.Test
	public void testeEmail() throws Exception {
		
		ObjetoEnviaEmail enviaEmail = 
				new ObjetoEnviaEmail(
						"", 
						"", 
						"testando envio de emails", 
						"testando envio de emails");
		enviaEmail.enviarEmail();
		Thread.sleep(5000);
}
}