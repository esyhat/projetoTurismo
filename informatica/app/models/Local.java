package models;

import javax.persistence.Entity;
import play.db.jpa.Model;

@Entity // Indica que esta classe é uma entidade JPA

public class Local extends Model { // Classe que representa um local

    public String passeio; // Nome do passeio
    public int valor; // Valor do passeio

    @Override

    public String toString() { // Método para representar o local como string
        return passeio + " (Valor: " + valor + ")"; // Retorna o nome e valor do passeio

    }

}