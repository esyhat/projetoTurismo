package controllers;

import play.mvc.Before;
import play.mvc.Controller;

public class Seguranca extends Controller {

	// Método que será executado antes de qualquer ação no controlador
	@Before
	static void verificarAutenticacao() {
		// Verifica se a sessão contém a chave "usuarioLogado"
		if (!session.contains("usuarioLogado")) {
			flash.error("Por favor realize login"); // Mensagem de erro se o usuário não estiver autenticado
			Logins.form(); // Redireciona para o formulário de login
		}
	}

}