package gestionTickets;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    private static final String URL = "jdbc:postgresql://ep-lingering-bar-a4u8oid3-pooler.us-east-1.aws.neon.tech/bdTickets?user=bdTickets_owner&password=npg_X1ZoHmCNYl2K&sslmode=require";



    public static Connection conectar() throws SQLException {
    return DriverManager.getConnection(URL);}
}


