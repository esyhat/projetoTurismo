package controllers;

import java.util.List;

import models.Visita;
import play.mvc.Controller;


public class Visitas extends Controller {

	 public static void form() {
	        render();
	    }
	 
	 
	 public static void salvar(Visita v) {
		 String mensagem = "Cadastrado com sucesso!";
		 if (v.id != null) {
			 mensagem = "Editado com sucesso";
		 }	 

		v.save();
		flash.success(mensagem);
		listar(null);
	 }
	 
	 public static void remover(long id) {
		 Visita v = Visita.findById(id);
		 v.delete();
		 flash.success("Removido com sucesso!");
		 listar(null);
	 }
	 
	 public static void listar(String termo) {
		 List<Visita> visitas = Visita.findAll();
		 if (termo == null) {
			 visitas = Visita.findAll();
		 }else {
			 visitas = Visita.find("lower(nome) like ?1 or email like ?1",
			 	"%" + termo.toLowerCase() + "%").fetch();
		 }
		 render(visitas, termo);
		 
	 }

	 
	 public static void editar(long id) {
		 Visita v = Visita.findById(id);
		 renderTemplate ("Visitas/form.html", v);
		 
	 }
	 
	 
	 
	
	 
	
	 
	 
}
