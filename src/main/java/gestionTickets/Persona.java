package gestionTickets;

public abstract class Persona {

    private String idPersona;
    private String nombre;
    private String correo;
    private String telefono;
    private Rol rol;


    //Getters y Setters

    public String getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    //constructor para que reciba todos los atributos como parametros
        public Persona(String idPersona, String nombre, String correo, String telefono, Rol rol) {
        this.idPersona = idPersona;
        this.nombre = nombre;
        this.correo = correo;
        this.telefono = telefono;
        this.rol = rol;

    }
}
