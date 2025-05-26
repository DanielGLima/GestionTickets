package gestionTickets.CRUD;

import gestionTickets.ConexionDB;
import gestionTickets.Tecnico;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TecnicoCRUD {

    public List<Tecnico> obtenerTecnicos(){
        List<Tecnico> listaTecnicos = new ArrayList<Tecnico>();
        String sql = """
            SELECT usuario_id, nombre_completo, correo_electronico, telefono
            FROM Usuario
            WHERE rol_id = 2 AND estado = TRUE
        """;

        try (Connection conn = ConexionDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()){

            while (rs.next()) {
                Tecnico t = new Tecnico(
                        rs.getInt("usuario_id"),
                        rs.getString("nombre_completo"),
                        rs.getString("correo_electronico"),
                        rs.getString("telefono"),
                        null
                );
                listaTecnicos.add(t);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return listaTecnicos;

    }

}
