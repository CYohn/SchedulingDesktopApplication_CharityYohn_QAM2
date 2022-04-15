package utilities;

import java.sql.*;

public class DatabaseConnection {


    String url
            = "jdbc://localhost:3306/client_schedule";   // JDBC URL
    String username = "sqlUser";  // Username preset in DB
    String password = "Passw0rd!";    // Password preset in DB

    Connection connection;  // Get connection

    {
        try {
            connection = DriverManager.getConnection(url, username, password);
            Statement stmt = connection.createStatement();       // Create Statement
            String query = "SELECT * FROM appointments";
            ResultSet rs = stmt.executeQuery(query);       // Execute Query
            while (rs.next()) {                            // Process Results
                System.out.println(rs.getInt("Appointment_ID") + "  ");   // Print Columns
                System.out.println(rs.getString("Title") + "  ");
                System.out.println(rs.getString("Description") + "  ");
                System.out.println(rs.getString("Location") + "  ");
                System.out.println(rs.getString("Type") + "  ");
                System.out.println(rs.getString("Start") + "  ");
                System.out.println(rs.getString("End") + "  ");
                System.out.println(rs.getString("Customer_ID") + "  ");
                System.out.println(rs.getString("User_ID") + "  ");
                System.out.println(rs.getString("Contact_ID") + "  ");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        // instance of a
        // Connection
        // object

    }
}
