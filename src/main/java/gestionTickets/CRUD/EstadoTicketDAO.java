package gestionTickets.CRUD;

import gestionTickets.ConexionDB;
import gestionTickets.EstadoTicket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EstadoTicketDAO {

    public static EstadoTicket obtenerEstadoPorNombre(String nombre) {
        EstadoTicket estado = null;
        String sql = "SELECT * FROM estado_ticket WHERE nombre_estado = ?";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String id = rs.getString("estado_id");
                String descripcion = rs.getString("descripcion_estado");
                estado = new EstadoTicket(nombre, id, descripcion);
            }

        } catch (SQLException e) {
            System.err.println("Error al obtener estado: " + e.getMessage());
        }

        return estado;
    }

    public static List<EstadoTicket> obtenerTodosLosEstados() {
        List<EstadoTicket> estados = new ArrayList<>();
        String sql = "SELECT * FROM estado_ticket";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String id = rs.getString("estado_id");
                String nombre = rs.getString("nombre_estado");
                String descripcion = rs.getString("descripcion_estado");

                estados.add(new EstadoTicket(nombre, id, descripcion));
            }

        } catch (SQLException e) {
            System.err.println("Error al cargar los estados: " + e.getMessage());
        }

        return estados;
    }
}
