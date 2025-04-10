package gestionTickets;

public class EstadoTicket {

    private String idEstadoTicket;
    private String descripcionEstadoTicket;

    public EstadoTicket(String idEstadoTicket, String descripcionEstadoTicket) {
        this.idEstadoTicket = idEstadoTicket;
        this.descripcionEstadoTicket = descripcionEstadoTicket;
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
