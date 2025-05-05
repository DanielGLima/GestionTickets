package gestionTickets;

import java.util.List;

public class Rol {

    private String idRol;
    private String nombre;
    private List<String> permisos;


    //Constructor propio
    public Rol(String idRol, String nombre, List<String> permisos) {
        this.idRol = idRol;
        this.nombre = nombre;
        this.permisos = permisos;
    }

    //Getters y Setters
    public String getIdRol() {
        return idRol;
    }

    public void setIdRol(String idRol) {
        this.idRol = idRol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<String> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<String> permisos) {
        this.permisos = permisos;
    }

    @Override
    public String toString() {
        return nombre;
    }

}
