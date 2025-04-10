package gestionTickets;

import java.util.List;

public class SistemaTickets {

    private List<Persona> usuarios;
    private List<Departamento> departamentos;
    private List<EstadoTicket> estados;
    private List<Ticket> tickets;

    //Contructor
    public SistemaTickets(List<Persona> usuarios, List<Departamento> departamentos, List<EstadoTicket> estados, List<Ticket> tickets) {
        this.usuarios = usuarios;
        this.departamentos = departamentos;
        this.estados = estados;
        this.tickets = tickets;
    }

    //Getters y Setters

    public List<Persona> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Persona> usuarios) {
        this.usuarios = usuarios;
    }

    public List<Departamento> getDepartamentos() {
        return departamentos;
    }

    public void setDepartamentos(List<Departamento> departamentos) {
        this.departamentos = departamentos;
    }

    public List<EstadoTicket> getEstados() {
        return estados;
    }

    public void setEstados(List<EstadoTicket> estados) {
        this.estados = estados;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}
