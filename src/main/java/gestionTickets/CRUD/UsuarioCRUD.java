package gestionTickets.CRUD;

import gestionTickets.ConexionDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioCRUD {
// prueba update
    // 🔹 CREAR USUARIO
    public void crearUsuario(String nombreCompleto, String correoElectronico, String nombreUsuario, String contrasena, String telefono, int rolId) {
        String sql = "INSERT INTO Usuario (nombre_completo, correo_electronico, nombre_usuario, contrasena, telefono, rol_id, departamento_id, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?, NULL, TRUE)";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombreCompleto);
            stmt.setString(2, correoElectronico);
            stmt.setString(3, nombreUsuario);
            stmt.setString(4, contrasena);
            stmt.setString(5, telefono);
            stmt.setInt(6, rolId);

            int filas = stmt.executeUpdate();
            if (filas > 0) {
                System.out.println("✅ Usuario creado exitosamente.");
            }
        } catch (SQLException e) {
            System.out.println("⛔ Error al crear usuario:");
            e.printStackTrace();
        }
    }

    // 🔹 LEER USUARIO POR ID
    public void leerUsuario(int usuarioId) {
        String sql = "SELECT * FROM Usuario WHERE usuario_id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("🧾 Usuario encontrado:");
                System.out.println("Nombre: " + rs.getString("nombre_completo"));
                System.out.println("Correo: " + rs.getString("correo_electronico"));
                System.out.println("Teléfono: " + rs.getString("telefono"));
            } else {
                System.out.println("⚠️ Usuario no encontrado.");
            }

        } catch (SQLException e) {
            System.out.println("⛔ Error al leer usuario:");
            e.printStackTrace();
        }
    }

    // 🔹 ACTUALIZAR USUARIO
    public void actualizarUsuario(int usuarioId, String nuevoNombre, String nuevoCorreo, String nuevoTelefono) {
        String sql = "UPDATE Usuario SET nombre_completo = ?, correo_electronico = ?, telefono = ? WHERE usuario_id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nuevoNombre);
            stmt.setString(2, nuevoCorreo);
            stmt.setString(3, nuevoTelefono);
            stmt.setInt(4, usuarioId);

            int filas = stmt.executeUpdate();
            if (filas > 0) {
                System.out.println("✅ Usuario actualizado exitosamente.");
            } else {
                System.out.println("⚠️ Usuario no encontrado.");
            }

        } catch (SQLException e) {
            System.out.println("⛔ Error al actualizar usuario:");
            e.printStackTrace();
        }
    }

    // 🔹 ELIMINAR USUARIO
    public void eliminarUsuario(int usuarioId) {
        String sql = "DELETE FROM Usuario WHERE usuario_id = ?";
        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, usuarioId);

            int filas = stmt.executeUpdate();
            if (filas > 0) {
                System.out.println("🗑️ Usuario eliminado exitosamente.");
            } else {
                System.out.println("⚠️ Usuario no encontrado.");
            }

        } catch (SQLException e) {
            System.out.println("⛔ Error al eliminar usuario:");
            e.printStackTrace();
        }
    }
}
