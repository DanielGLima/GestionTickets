package gestionTickets;

import java.time.LocalDate;

public class Nota {
    private Persona autor;
    private String contenido;
    private LocalDate fecha;

    //Constructor
    public Nota(Persona autor, String contenido, LocalDate fecha) {
        this.autor = autor;
        this.contenido = contenido;
        this.fecha = fecha;
    }

    //Getters y Setters

    public Persona getAutor() {
        return autor;
    }

    public void setAutor(Persona autor) {
        this.autor = autor;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
