package gestionTickets;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Departamento {

    private final StringProperty idDepartamento;
    private final StringProperty nombre;
    private final StringProperty descripcion;

    // Constructor con ID (por si luego conectas a base de datos)
    public Departamento(String idDepartamento, String nombre, String descripcion) {
        this.idDepartamento = new SimpleStringProperty(idDepartamento);
        this.nombre = new SimpleStringProperty(nombre);
        this.descripcion = new SimpleStringProperty(descripcion);
    }

    // Constructor sin ID (por ejemplo, al crear desde la interfaz)
    public Departamento(String nombre, String descripcion) {
        this(null, nombre, descripcion);
    }

    // Getters y setters
    public String getIdDepartamento() {
        return idDepartamento.get();
    }

    public void setIdDepartamento(String id) {
        this.idDepartamento.set(id);
    }

    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public String getDescripcion() {
        return descripcion.get();
    }

    public void setDescripcion(String descripcion) {
        this.descripcion.set(descripcion);
    }

    // Propiedades para JavaFX TableView
    public StringProperty idDepartamentoProperty() {
        return idDepartamento;
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public StringProperty descripcionProperty() {
        return descripcion;
    }

    @Override
    public String toString() {
        return getNombre();
    }
}
