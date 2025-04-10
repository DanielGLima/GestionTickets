package gestionTickets;

import java.time.LocalDateTime;

public class Nota {
    private Persona autor;
    private String contenido;
    private LocalDateTime fecha;

    //Constructor
    public Nota(Persona autor, String contenido, LocalDateTime fecha) {
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

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }
}
