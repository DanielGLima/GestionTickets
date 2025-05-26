package gestionTickets;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Ticket {
    private int idTicket;
    private String titulo;
    private String descripcion;
    private String prioridad;
    private LocalDateTime fechaCreacion;
    private EstadoTicket estadoActual;
    private Usuario usuarioCreador;
    private Tecnico tecnicoAsignado;
    private Departamento departamento;
    private List<Nota> notas;
    private List<Ticket> ticketsDelSistema;


    //Constructor


    public Ticket(int idTicket, String titulo, String descripcion,String prioridad, LocalDateTime fechaCreacion, EstadoTicket estadoActual, Usuario usuarioCreador, Tecnico tecnicoAsignado, Departamento departamento, List<Nota> notas, List<Ticket> ticketsDelSistema) {
        this.idTicket = idTicket;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.prioridad = prioridad;
        this.fechaCreacion = fechaCreacion;
        this.estadoActual = estadoActual;
        this.usuarioCreador = usuarioCreador;
        this.tecnicoAsignado = tecnicoAsignado;
        this.departamento = departamento;
        this.notas = notas;
        this.ticketsDelSistema = ticketsDelSistema;
    }

    //Getters y Setters

    public int getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(int idTicket) {
        this.idTicket = idTicket;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    public String getPrioridad() {
        return prioridad;
    }
    public void setPrioridad(String prioridad) {
        this.prioridad = prioridad;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public EstadoTicket getEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(EstadoTicket estadoActual) {
        this.estadoActual = estadoActual;
    }

    public Usuario getUsuarioCreador() {
        return usuarioCreador;
    }

    public void setUsuarioCreador(Usuario usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }

    public Tecnico getTecnicoAsignado() {
        return tecnicoAsignado;
    }

    public void setTecnicoAsignado(Tecnico tecnicoAsignado) {
        this.tecnicoAsignado = tecnicoAsignado;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    public List<Nota> getNotas() {
        return notas;
    }

    public void setNotas(List<Nota> notas) {
        this.notas = notas;
    }

    public List<Ticket> getTicketsDelSistema() {
        return ticketsDelSistema;
    }

    public void setTicketsDelSistema(List<Ticket> ticketsDelSistema) {
        this.ticketsDelSistema = ticketsDelSistema;
    }
//Metodos

    //El metodo debe permitir agregar una nueva nota a la lista, No debe sobreescribir a la lista ni reiniciarse.
    public void agregarNota(Nota nota){
        notas.add(nota);
    }
}
