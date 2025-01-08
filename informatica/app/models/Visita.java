package models;

import javax.persistence.*;
import play.db.jpa.Model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
public class Visita extends Model { // Classe que representa uma visita


    public LocalDate data; // Data da visita
    public LocalTime hora; // Hora da visita
    public int numeroParticipantes; // Número de participantes na visita
    public double total; // Total a ser pago pela visita


    @ManyToMany // Relacionamento muitos-para-muitos com Categoria
    public List<Categoria> passeio; // Lista de categorias de passeio


    @ManyToMany // Relacionamento muitos-para-muitos com Usuario
    public List<Usuario> usuario; // Lista de usuários que participaram da visita

}