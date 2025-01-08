package controllers;

import models.Visita;
import models.Categoria;
import models.Usuario;
import play.mvc.Controller;
import play.mvc.With;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@With(Seguranca.class) // Aplica a segurança do controlador Seguranca

public class Visitas extends Controller {

	// Método para renderizar o formulário de cadastro/edição de visita

	public static void form(Long id) {

		// Se um ID for fornecido, busca a visita correspondente; caso contrário, cria
		// uma nova

		Visita visita = (id != null) ? Visita.findById(id) : new Visita();
		if (id != null && visita == null) { // Verifica se a visita foi encontrada

			flash.error("Visita não encontrada."); // Mensagem de erro
			listar(); // Redireciona para a lista de visitas

		}

		List<Categoria> categorias = Categoria.findAll(); // Busca todas as categorias
		render(visita, categorias); // Renderiza a página com a visita e categorias

	}

	// Método para salvar uma visita (cadastrar ou editar)

	public static void salvar(Visita visita) {

		String mensagem = "Cadastrado com sucesso!"; // Mensagem padrão para cadastro
		if (visita.id != null) { // Verifica se a visita já existe (edição)
			mensagem = "Editado com sucesso!"; // Mensagem para edição

		}

		// Obtém a hora e a data da visita a partir dos parâmetros

		String horaString = params.get("visita.hora");
		visita.hora = LocalTime.parse(horaString); // Converte a string para LocalTime

		String dataString = params.get("visita.data");
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Define o formato da data
		visita.data = LocalDate.parse(dataString, formatter); // Converte a string para LocalDate

		// Obtém os IDs dos passeios selecionados

		String[] passeioIds = params.getAll("visita.passeioIds");

		int total = 0; // Inicializa o total

		if (passeioIds != null) { // Verifica se há passeios selecionados

			// Converte os IDs de String para Long

			List<Long> idsLong = Arrays.stream(passeioIds).map(Long::valueOf).collect(Collectors.toList());

			// Busca as categorias correspondentes aos IDs

			List<Categoria> passeiosSelecionados = Categoria.find("id in (:ids)").bind("ids", idsLong).fetch();
			visita.passeio = passeiosSelecionados; // Atribui os passeios à visita

			for (Categoria categoria : passeiosSelecionados) { // Calcula o total
				total += categoria.valor; // Soma o valor de cada categoria

			}

			total *= visita.numeroParticipantes; // Multiplica pelo número de participantes

		} else {

			visita.passeio = new ArrayList<>(); // Se não houver passeios, inicializa como lista vazia

		}

		visita.total = total; // Atribui o total à visita
		visita.save(); // Salva a visita no banco de dados

		flash.success(mensagem); // Exibe mensagem de sucesso
		listar(); // Redireciona para a lista de visitas

	}

	// Método para listar todas as visitas

	public static void listar() {

		List<Visita> visitas = Visita.findAll(); // Busca todas as visitas
		render(visitas); // Renderiza a lista de visitas

	}

	// Método para editar uma visita

	public static void editar(Long id) {

		if (id == null || id <= 0) { // Verifica se o ID é válido
			flash.error("ID inválido para edição."); // Mensagem de erro
			listar(); // Redireciona para a lista de visitas

		}

		Visita visita = Visita.findById(id); // Busca a visita pelo ID
		if (visita == null) { // Verifica se a visita foi encontrada
			flash.error("Visita não encontrada."); // Mensagem de erro se não encontrada
			listar(); // Redireciona para a lista de visitas

		}

		List<Categoria> categorias = Categoria.findAll(); // Busca todas as categorias
		renderTemplate("Visitas/form.html", visita, categorias); // Renderiza o template de edição

	}

	// Método para remover uma visita

	public static void remover(Long id) {

		Visita visita = Visita.findById(id); // Busca a visita pelo ID
		if (visita != null) { // Verifica se a visita foi encontrada
			visita.delete(); // Remove a visita do banco de dados
			flash.success("Visita removida com sucesso!"); // Mensagem de sucesso

		} else {

			flash.error("Visita não encontrada."); // Mensagem de erro se não encontrada

		}

		listar(); // Redireciona para a lista de visitas

	}

}