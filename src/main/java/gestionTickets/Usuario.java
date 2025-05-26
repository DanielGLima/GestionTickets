package gestionTickets;

import java.util.ArrayList;
import java.util.List;

public class Usuario extends Persona {

    public Usuario(int idPersona, String nombre, String correo, String telefono, Rol rol) {
        super(idPersona, nombre, correo, telefono, rol);
    }

    public void consultarTickets(List<Ticket> ticketsDelSistema) {
        List<Ticket> misTickets = new ArrayList<>();

        for (Ticket ticketActual : ticketsDelSistema) {
            if (ticketActual.getUsuarioCreador().getIdPersona() == this.getIdPersona()) {
                misTickets.add(ticketActual);
            }
        }

        if (misTickets.isEmpty()) {
            System.out.println("Usuario sin tickets");
        } else {
            for (Ticket t : misTickets) {
                System.out.println("Ticket encontrado: " + t.getTitulo());
            }
        }
    }
}
