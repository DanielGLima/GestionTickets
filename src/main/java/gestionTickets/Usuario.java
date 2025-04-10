package gestionTickets;

public class Usuario extends Persona {

    //constructor que hereda de la clase persona,. por lo cual se utiliza super(atributos)
    public Usuario(String idPersona, String nombre, String correo, String telefono, Rol rol) {
        super(idPersona, nombre, correo, telefono, rol);
    }
}
