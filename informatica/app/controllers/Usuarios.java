package controllers;

import models.Categoria;
import models.Status;
import models.Usuario;
import play.db.jpa.GenericModel;
import play.mvc.Controller;
import play.mvc.With;

import java.util.Arrays;
import java.util.List;

public class Usuarios extends Controller {

	public static void form(Long id) {

		// Se um ID for fornecido, busca o usuário correspondente; caso contrário, cria
		// um novo

		Usuario usuario = (id != null) ? Usuario.findById(id) : new Usuario();
		render(usuario); // Renderiza a página com o usuário

	}

	// Método para salvar um usuário (cadastrar ou editar)

	public static void salvar(Usuario usuario) {

		String mensagem = "Cadastrado com sucesso!"; // Mensagem padrão para cadastro
		if (usuario.id != null) { // Verifica se o usuário já existe (edição)
			mensagem = "Editado com sucesso!"; // Mensagem para edição

		}

		// Converte o nome para maiúsculas e o email para minúsculas

		usuario.nome = usuario.nome.toUpperCase();
		usuario.email = usuario.email.toLowerCase();
		
		usuario.save(); // Salva o usuário no banco de dados
		flash.success(mensagem); // Exibe mensagem de sucesso
		listar(null); // Redireciona para a lista de usuários

	}

	// Método para remover um usuário (marcando como inativo)

	public static void remover(Long id) {

		Usuario u = Usuario.findById(id); // Busca o usuário pelo ID
		u.status = Status.INATIVO; // Define o status como inativo
		
		u.save(); // Salva as alterações no banco de dados
		flash.success("Removido com sucesso!"); // Exibe mensagem de sucesso
		listar(null); // Redireciona para a lista de usuários

	}

	// Método para listar usuários, com opção de filtro por termo

	public static void listar(String termo) {

		List<Usuario> usuarios = null; // Inicializa a lista de usuários
		if (termo == null) { // Se não houver termo de busca

			// Busca todos os usuários ativos

			usuarios = Usuario.find("status = ?1", Status.ATIVO).fetch();

		} else {

			// Busca usuários que correspondem ao termo (nome ou email) e que estão ativos

			usuarios = Usuario.find("(lower(nome) like ?1 or email like ?1) AND status = ?2",

					"%" + termo.toLowerCase() + "%", Status.ATIVO).fetch();

		}

		render(usuarios, termo); // Renderiza a lista de usuários

	}

	// Método para editar um usuário

	public static void editar(Long id) {

		Usuario usuario = Usuario.findById(id); // Busca o usuário pelo ID
		if (usuario == null) { // Verifica se o usuário foi encontrado
			notFound("Usuario não encontrado"); // Exibe mensagem de erro se não encontrado

		}

		renderTemplate("Usuarios/form.html", usuario); // Renderiza o template de edição

	}

}