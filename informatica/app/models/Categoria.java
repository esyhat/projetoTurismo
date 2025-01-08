package models;

import javax.persistence.*;
import play.db.jpa.Model;
import java.util.List;


@Entity // Indica que esta classe é uma entidade JPA
public class Categoria extends Model { // Classe que representa uma categoria


    public String passeio; // Nome do passeio
    public int valor; // Valor do passeio
    public boolean selected; // Indica se a categoria está selecionada


    @Override

    public String toString() { // Método para representar a categoria como string
        return passeio + " (Valor: " + valor + ")"; // Retorna o nome e valor do passeio

    }

}