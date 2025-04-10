package gestionTickets;

public class Tecnico extends Persona {

    private Departamento departamento;

    //Getters y Setters


    public Departamento getDepartamento() {
        return departamento;
    }

    public void setDepartamento(Departamento departamento) {
        this.departamento = departamento;
    }

    //constructor que hereda de la clase Persona y el atributo Departamento
    public Tecnico(String idPersona, String nombre, String correo, String telefono, Rol rol, Departamento departamento) {
        super(idPersona, nombre, correo, telefono, rol);
        this.departamento = departamento;

        //this.departamento = departamento; este atributo no se agrega al super porque no lo hereda la clase Persona

    }

    //Metodos

    //el metodo se implementa cuando un tecnico agarra un ticket pendiente y se encarga de resolverlo
    //verifica si el tecnico pertenece al departamento del ticket
    //Asigna el ticket al Tecnico
    //Cambia el estado del ticket

    public void atenderTicket(Ticket ticket, EstadoTicket estadoTicket) {
        if(this.departamento.equals(ticket.getDepartamento())){
            ticket.setTecnicoAsignado(this);
            ticket.setEstadoActual(estadoTicket);
        }
        else{
            System.out.println("Verifique que el ticket este en ese departamento");
        }

    }
}
