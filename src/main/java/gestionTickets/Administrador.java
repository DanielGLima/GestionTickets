package gestionTickets;

import java.util.List;

public class Administrador extends Persona {

    public Administrador(String idPersona, String nombre, String correo, String telefono, Rol rol) {
        super(idPersona, nombre, correo, telefono, rol);
    }


    public void crearDepartamento(String nombre, String descripcion, List<Departamento> departamentos) {
        if (nombre.isEmpty() || descripcion.isEmpty()) {
            System.out.println("El nombre o la descripcion no puede estar vacio");

        } else {
            for (Departamento departamento : departamentos) {
                if (departamento.getNombre().equalsIgnoreCase(nombre)) {
                    System.out.println("El departamento ya existe");
                    return;
                }
            }
        }
        Departamento departamento = new Departamento(null, nombre, descripcion);
        departamentos.add(departamento);
        System.out.println("Departamento agregado");

    }
}
