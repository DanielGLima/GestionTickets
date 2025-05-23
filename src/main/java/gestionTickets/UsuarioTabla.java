package gestionTickets;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class UsuarioTabla {
    private final IntegerProperty id;
    private final SimpleStringProperty nombre;
    private final SimpleStringProperty correo;
    private final SimpleStringProperty telefono;
    private final SimpleStringProperty departamento;
    private final SimpleStringProperty contrasena;

    public UsuarioTabla(int id, String nombre, String correo, String telefono, String departamento, String contrasena) {
        this.id = new SimpleIntegerProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.correo = new SimpleStringProperty(correo);
        this.telefono = new SimpleStringProperty(telefono);
        this.departamento = new SimpleStringProperty(departamento);
        this.contrasena = new SimpleStringProperty(contrasena);
    }


    public int getId() {return id.get();}
    public String getNombre() { return nombre.get(); }
    public String getCorreo() { return correo.get(); }
    public String getTelefono() { return telefono.get(); }
    public String getDepartamento() { return departamento.get(); }
    public String getContrasena() { return contrasena.get(); }

    public void setId(int id) {this.id.set(id);}
    public SimpleStringProperty nombreProperty() { return nombre; }
    public SimpleStringProperty correoProperty() { return correo; }
    public SimpleStringProperty telefonoProperty() { return telefono; }
    public SimpleStringProperty departamentoProperty() { return departamento; }
    public SimpleStringProperty contrasenaProperty() { return contrasena; }
}
