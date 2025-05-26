package gestionTickets;

public class EstadoTicket {

    private String nombre;
    private String idEstadoTicket;
    private String descripcionEstadoTicket;

    public EstadoTicket(String nombre, String idEstadoTicket, String descripcionEstadoTicket) {
        this.nombre = nombre;
        this.idEstadoTicket = idEstadoTicket;
        this.descripcionEstadoTicket = descripcionEstadoTicket;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdEstadoTicket() {
        return idEstadoTicket;
    }

    public void setIdEstadoTicket(String idEstadoTicket) {
        this.idEstadoTicket = idEstadoTicket;
    }

    public String getDescripcionEstadoTicket() {
        return descripcionEstadoTicket;
    }

    public void setDescripcionEstadoTicket(String descripcionEstadoTicket) {
        this.descripcionEstadoTicket = descripcionEstadoTicket;
    }
}
