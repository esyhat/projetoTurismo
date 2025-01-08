package jobs;

import java.util.ArrayList;

import models.Categoria;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

@OnApplicationStart
public class Inicializador extends Job {

	@Override
	public void doJob() throws Exception { // Método que contém a lógica do job

        // Verifica se não há categorias cadastradas

        if (Categoria.count() == 0) {

            // Cria e salva a primeira categoria

            Categoria c1 = new Categoria();

            c1.passeio = "Passeios de Buggy"; // Define o nome do passeio
            c1.valor = 250; // Define o valor do passeio
            c1.save(); // Salva a categoria no banco de dados


            // Cria e salva a segunda categoria

            Categoria c2 = new Categoria();

            c2.passeio = "Praias"; // Define o nome do passeio
            c2.valor = 50; // Define o valor do passeio
            c2.save(); // Salva a categoria no banco de dados


            // Cria e salva a terceira categoria

            Categoria c3 = new Categoria();

            c3.passeio = "Passeios para o Parracho"; // Define o nome do passeio
            c3.valor = 100; // Define o valor do passeio
            c3.save(); // Salva a categoria no banco de dados

        }

    }

}