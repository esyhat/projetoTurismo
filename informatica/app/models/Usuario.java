package models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Usuario extends Model { // Classe que representa um usuário

	public String nome; // Nome do usuário
	public String email; // Email do usuário
	public String senha; // Senha do usuário

	@Enumerated(EnumType.STRING) // Indica que o status é um enumerado armazenado como string
	public Status status; // Status do usuário
	public String hash; // Hash para recuperação de senha

	public Usuario() { // Construtor
		this.status = Status.ATIVO; // Define o status padrão como ATIVO

	}

	@Override

	public String toString() { // Método para representar o usuário como string
		return nome; // Retorna o nome do usuário

	}

	// Método para autenticar um usuário

	public static String autenticar(String login, String senha) {
		Usuario u = Usuario.find("email = ?1 and senha = ?2", login, senha).first(); // Busca o usuário pelo email e
																						// senha

		if (u == null) {
			return null; // Retorna null se o usuário não for encontrado
		} else {
			return u.email; // Retorna o email do usuário autenticado

		}

	}

}