package utilities;

import java.sql.*;

public class DatabaseConnection {


    String url
            = "jdbc:derby://localhost:3306/client_schedule";   // JDBC URL
    String username = "sqlUser";  // Username preset in DB
    String password = "Passw0rd!";    // Password preset in DB

    Connection conn;  // Get connection

    {
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    // instance of a
    // Connection
    // object

}
