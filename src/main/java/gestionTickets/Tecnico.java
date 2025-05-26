package gestionTickets;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Tecnico extends Usuario {

    // Propiedades JavaFX (opcionales para TableView)
    private final StringProperty nombreProperty;
    private final StringProperty correoProperty;
    private final StringProperty telefonoProperty;

    public Tecnico(int idPersona, String nombre, String correo, String telefono, Rol rol) {
        super(idPersona, nombre, correo, telefono, rol);
        this.nombreProperty = new SimpleStringProperty(nombre);
        this.correoProperty = new SimpleStringProperty(correo);
        this.telefonoProperty = new SimpleStringProperty(telefono);
    }

    // Constructores adicionales si se requieren
    public Tecnico(String nombre, String correo, String telefono) {
        this(0, nombre, correo, telefono, null); // puedes asignar un Rol por defecto si necesitas
    }

    // Propiedades JavaFX para TableView
    public StringProperty nombreProperty() {
        return nombreProperty;
    }

    public StringProperty correoProperty() {
        return correoProperty;
    }

    public StringProperty telefonoProperty() {
        return telefonoProperty;
    }

    @Override
    public String toString() {
        return getNombre(); // heredado de Persona
    }
}
