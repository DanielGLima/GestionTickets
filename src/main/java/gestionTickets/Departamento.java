package gestionTickets;

public class Departamento {

    private String idDepartamento;
    private String nombre;
    private String descripcion;

    //Constructor


    public Departamento(String idDepartamento, String nombre, String descripcion) {
        this.idDepartamento = idDepartamento;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    //Getters y Setters

    public String getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(String idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
