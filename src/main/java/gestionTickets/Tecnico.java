package gestionTickets;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Tecnico {

    private String idTecnico;
    private final StringProperty nombre;
    private final StringProperty correo;
    private final StringProperty telefono;

    // Constructor con ID
    public Tecnico(String idTecnico, String nombre, String correo, String telefono) {
        this.idTecnico = idTecnico;
        this.nombre = new SimpleStringProperty(nombre);
        this.correo = new SimpleStringProperty(correo);
        this.telefono = new SimpleStringProperty(telefono);
    }

    // Constructor sin ID (para pruebas o creación desde interfaz)
    public Tecnico(String nombre, String correo, String telefono) {
        this(null, nombre, correo, telefono);
    }

    // Getters y Setters
    public String getIdTecnico() {
        return idTecnico;
    }

    public void setIdTecnico(String idTecnico) {
        this.idTecnico = idTecnico;
    }

    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public String getCorreo() {
        return correo.get();
    }

    public void setCorreo(String correo) {
        this.correo.set(correo);
    }

    public String getTelefono() {
        return telefono.get();
    }

    public void setTelefono(String telefono) {
        this.telefono.set(telefono);
    }

    // Propiedades JavaFX para TableView
    public StringProperty nombreProperty() {
        return nombre;
    }

    public StringProperty correoProperty() {
        return correo;
    }

    public StringProperty telefonoProperty() {
        return telefono;
    }

    @Override
    public String toString() {
        return getNombre(); // Para mostrarse correctamente en ListView
    }
}
