package gestionTickets;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConexionDB {

    private static final String URL = "jdbc:postgresql://<ep-lingering-bar-a4u8oid3-pooler.us-east-1.aws.neon.tech>:5432/<DB>?sslmode=require";
    private static final String USER = "<bdTickets_owner>";
    private static final String PASSWORD = "<npg_X1ZoHmCNYl2K>";

    public static Connection conectar() {
        try {
            System.out.println("Conectado con exito");
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Fallo al conectarse a la Base de Datos");
            return null;
        }
    }
}


