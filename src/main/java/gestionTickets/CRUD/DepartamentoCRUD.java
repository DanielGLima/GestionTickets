package gestionTickets.CRUD;

import gestionTickets.ConexionDB;
import gestionTickets.Departamento;
import gestionTickets.Tecnico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartamentoCRUD {

    public void insertarDepartamento(String nombre, String descripcion) {
        String sql = "INSERT INTO departamento (nombre, descripcion) VALUES (?, ?)";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombre);
            stmt.setString(2, descripcion);
            stmt.executeUpdate();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public List<Departamento> obtenerDepartamento () {
        List<Departamento> departamentos = new ArrayList<>();
        String sql = "select departamento_id, nombre, descripcion from Departamento;";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt .executeQuery()){

            while (rs.next()){
                Departamento d = new Departamento(
                String.valueOf(rs.getInt("departamento_id")),
                rs.getString("nombre"),
                rs.getString("descripcion"));
    departamentos.add(d);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return departamentos;
    }

    public void eliminarDepartamento(String id) {
        String sql = "DELETE FROM Departamento WHERE departamento_id = ?;";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, Integer.parseInt(id));
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void editarDepartamento(String id, String nuevoNombre, String nuevaDescripcion){
        String sql = "UPDATE Departamento SET  nombre = ?, descripcion = ? WHERE departamento_id = ?;";

        try(Connection conn = ConexionDB.conectar();
            PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, nuevoNombre);
            stmt.setString(2, nuevaDescripcion);
            stmt.setInt(3, Integer.parseInt(id));
            stmt.executeUpdate();

        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void asignarTecnicosAlDepartamento(String idDepartamento, List<Tecnico> tecnicos) {
        String sql = "INSERT INTO tecnico_departamento (tecnico_id, departamento_id) VALUES (?, ?) ON CONFLICT DO NOTHING";

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            for (Tecnico tecnico : tecnicos) {
                stmt.setInt(1, Integer.parseInt(tecnico.getIdTecnico()));
                stmt.setInt(2, Integer.parseInt(idDepartamento));
                stmt.addBatch();
            }

            stmt.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }





}
