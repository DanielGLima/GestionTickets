package gestionTickets;

public class TicketTabla {
    private int id;
    private String titulo;
    private String estado;
    private String prioridad;
    private String tecnico;
    private String departamento;

    public TicketTabla(int id, String titulo, String estado, String prioridad, String tecnico, String departamento) {
        this.id = id;
        this.titulo = titulo;
        this.estado = estado;
        this.prioridad = prioridad;
        this.tecnico = tecnico;
        this.departamento = departamento;
    }

    public int getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getEstado() { return estado; }
    public String getPrioridad() { return prioridad; }
    public String getTecnico() { return tecnico; }
    public String getDepartamento() { return departamento; }
}
