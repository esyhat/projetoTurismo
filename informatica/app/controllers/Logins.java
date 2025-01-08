package controllers;

import java.util.Random;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import models.Usuario;
import play.libs.Crypto;
import play.libs.Mail;
import play.mvc.Controller;

public class Logins extends Controller {

	// Método para salvar uma nova senha
	public static void salvarNovaSenha(String hash, String senha, String senhaConfirmacao) {
		// Busca o usuário pelo hash fornecido
		Usuario user = Usuario.find("hash = ?1", hash).first();

		if (user != null) {// Verifica se o usuário existe

			if (senha.equals(senhaConfirmacao)) {// Verifica se as senhas coincidem
				user.senha = senha; // Atualiza a senha do usuário
				user.hash = null; // Limpa o hash após a atualização
				user.save();  // Salva as alterações no banco de dados
				flash.success("Senha atualizada.");// Mensagem de sucesso
				form();  // Renderiza o formulário

			} else {
				flash.error("A senha de confirmação está diferente."); // Mensagem de erro
				digitarNovaSenha(hash); // Chama o método para digitar nova senha
			}

		} else {
			flash.error("Hash inválido."); // Mensagem de erro se o hash não for encontrado
			esqueciMinhaSenha(); // Chama o método para lidar com esquecimento de senha

		}

	}
	// Método para renderizar a página de digitação de nova senha
	public static void digitarNovaSenha(String hash) {
		render(hash); // Renderiza a página com o hash
	}
	// Método para solicitar uma nova senha
	public static void solicitarNovaSenha(String email) {
		// Busca o usuário pelo email fornecido
		Usuario user = Usuario.find("email = ?1", email).first();

		if (user != null) { // Verifica se o usuário existe
			Random rand = new Random();// Cria um objeto Random
			String hash = Crypto.passwordHash(rand.nextInt(1000) + ""); // Gera um hash aleatório

			// Remove caracteres indesejados do hash
			hash = hash.replace("+", "");
			hash = hash.replace("-", "");
			hash = hash.replace("&", "");

			user.hash = hash;// Atualiza o hash do usuário
			user.save();// Atualiza o hash do usuário

			HtmlEmail mail = new HtmlEmail();// Cria um objeto de email HTML
			try {
				mail.addTo(email);// Adiciona o destinatário
				mail.setFrom("juciaraju2357@gmail.com", "Recuperação de senha");// Define o remetente
				mail.setSubject("Você solicitou uma nova senha?"); // Define o assunto do email

				// Monta a mensagem HTML do email
				String msg = "<h4>Você solicitou uma nova senha?</h4>";
				msg += "Entre no link abaixo e digite sua nova senha:<br/>";
				msg += "<a href='http://localhost:9000/logins/digitarNovaSenha?hash=" + hash
						+ "'>http://localhost:9000/logins/digitarNovaSenha?hash=" + hash + "</a>";
				msg += "<br/>" + StringEscapeUtils
						.escapeHtml("Caso você não tenha pedido uma nova senha, desconsidere este e-mail.");

				
				mail.setHtmlMsg(msg);// Define a mensagem HTML
				Mail.send(mail);// Envia o email
				flash.success("O link da nova senha foi enviado para o seu e-mail.");
			} catch (EmailException e) {
				e.printStackTrace(); // Imprime a pilha de erros
				flash.error("Falha ao enviar o e-mail.");
			}

			render();
		} else {
			flash.error("E-mail não encontrado.");
			esqueciMinhaSenha(); // Chama o método para lidar com esquecimento de senha
		}
	}

	public static void esqueciMinhaSenha() {
		render();
	}

	public static void form() {
		render();
	}
	// Método para realizar o login do usuário
	public static void logar(String email, String senha) {

		long usuarioCount = Usuario.count(); // Conta o número de usuários cadastrados
		if (usuarioCount == 0) { // Verifica se não há usuários
			flash.error("Não há usuários cadastrados no sistema.");
			form();
			return;

		}

		if (email == null || email.isEmpty()) {
			flash.error("Por favor, forneça um email.");
			form();
			return;

		}

		if (senha == null || senha.isEmpty()) {
			flash.error("Por favor, forneça uma senha.");
			form();
			return;

		}

		String usuarioLogado = Usuario.autenticar(email, senha);
		if (usuarioLogado == null) {

			flash.error("Credenciais inválidas");
			form();
		} else {

			session.put("usuarioLogado", usuarioLogado);
			Visitas.form(null);
		}
	}

	public static void sair() {
		session.clear();
		flash.success("Você saiu do sistema");
		form();
	}

}