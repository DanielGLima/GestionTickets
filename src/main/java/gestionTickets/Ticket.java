package gestionTickets;

import java.time.LocalDate;

public class Ticket {
    private String idTicket;
    private String titulo;
    private String descripcion;
    private LocalDate fechaCreacion;
    private EstadoTicket estadoActual;
    private Usuario usuarioCreador;
    private Tecnico tecnicoAsignado;
    private Departamento departamento;
    private List<Nota> notas;


    //Constructor


    public Ticket(String idTicket, String titulo, String descripcion, LocalDate fechaCreacion, EstadoTicket estadoActual, Usuario usuarioCreador, Tecnico tecnicoAsignado, Departamento departamento, List<Nota> notas) {
        this.idTicket = idTicket;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.fechaCreacion = fechaCreacion;
        this.estadoActual = estadoActual;
        this.usuarioCreador = usuarioCreador;
        this.tecnicoAsignado = tecnicoAsignado;
        this.departamento = departamento;
        this.notas = notas;
    }

    //Getters y Setters

    public String getIdTicket() {
        return idTicket;
    }

    public void setIdTicket(String idTicket) {
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

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
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
}
