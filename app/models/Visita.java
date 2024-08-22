package models;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;

import play.db.jpa.Model;
@Entity
public class Visita extends Model {
	
	public String nome;
    public String email;
    public String senha;
    public int data;
    public int hora;
}
