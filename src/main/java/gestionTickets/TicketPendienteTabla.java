package gestionTickets;

import javafx.beans.property.*;

public class TicketPendienteTabla {

    private final IntegerProperty id;
    private final StringProperty titulo;
    private final StringProperty departamento;
    private final StringProperty prioridad;
    private final StringProperty fechaCreacion;

    public TicketPendienteTabla(int id, String titulo, String departamento, String prioridad, String fechaCreacion) {
        this.id = new SimpleIntegerProperty(id);
        this.titulo = new SimpleStringProperty(titulo);
        this.departamento = new SimpleStringProperty(departamento);
        this.prioridad = new SimpleStringProperty(prioridad);
        this.fechaCreacion = new SimpleStringProperty(fechaCreacion);
    }

    // Getters
    public int getId() {
        return id.get();
    }

    public String getTitulo() {
        return titulo.get();
    }

    public String getDepartamento() {
        return departamento.get();
    }

    public String getPrioridad() {
        return prioridad.get();
    }

    public String getFechaCreacion() {
        return fechaCreacion.get();
    }

    // Setters
    public void setId(int id) {
        this.id.set(id);
    }

    public void setTitulo(String titulo) {
        this.titulo.set(titulo);
    }

    public void setDepartamento(String departamento) {
        this.departamento.set(departamento);
    }

    public void setPrioridad(String prioridad) {
        this.prioridad.set(prioridad);
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion.set(fechaCreacion);
    }

    // Propiedades (para TableView)
    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty tituloProperty() {
        return titulo;
    }

    public StringProperty departamentoProperty() {
        return departamento;
    }

    public StringProperty prioridadProperty() {
        return prioridad;
    }

    public StringProperty fechaCreacionProperty() {
        return fechaCreacion;
    }
}
