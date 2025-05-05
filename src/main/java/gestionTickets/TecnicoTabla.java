package gestionTickets;

import javafx.beans.property.*;

public class TecnicoTabla {
    private final IntegerProperty id;
    private final StringProperty nombre;
    private final StringProperty correo;
    private final StringProperty departamento;

    public TecnicoTabla(int id, String nombre, String correo, String departamento) {
        this.id = new SimpleIntegerProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.correo = new SimpleStringProperty(correo);
        this.departamento = new SimpleStringProperty(departamento);
    }

    public int getId() { return id.get(); }
    public String getNombre() { return nombre.get(); }
    public String getCorreo() { return correo.get(); }
    public String getDepartamento() { return departamento.get(); }

    public IntegerProperty idProperty() { return id; }
    public StringProperty nombreProperty() { return nombre; }
    public StringProperty correoProperty() { return correo; }
    public StringProperty departamentoProperty() { return departamento; }
}
