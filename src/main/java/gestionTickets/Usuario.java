package gestionTickets;

import javafx.scene.control.skin.CellSkinBase;

import java.util.ArrayList;
import java.util.List;

public class Usuario extends Persona {


    //constructor que hereda de la clase persona,. por lo cual se utiliza super(atributos)
    public Usuario(String idPersona, String nombre, String correo, String telefono, Rol rol) {
        super(idPersona, nombre, correo, telefono, rol);
    }

    //Metodos

    //Metodo para visualizar los tickets creados por el usuario

    public void consultarTickets(List<Ticket> ticketsDelSistema) {
        List<Ticket> misTickets = new ArrayList<>();

        for (int i = 0; i < ticketsDelSistema.size(); i++) {
            Ticket ticketActual = ticketsDelSistema.get(i);
            if (ticketActual.getUsuarioCreador().equals(this)) {
                misTickets.add(ticketActual);
            }
        }
        if (misTickets.isEmpty()) {
            System.out.println("Usuario sin tickets");
        } else {
            for (Ticket t : misTickets) {
                System.out.println("Ticket encontrado: " + t);
            }
        }
    }
}
