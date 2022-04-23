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
            Statement statement = connection.createStatement();       // Create Statement
            String query = "SELECT * FROM appointments";
            ResultSet resultSet = statement.executeQuery(query);       // Execute Query
            while (resultSet.next()) {                            // Process Results
                System.out.println(resultSet.getInt("Appointment_ID") + "  ");   // Print Columns
                System.out.println(resultSet.getString("Title") + "  ");
                System.out.println(resultSet.getString("Description") + "  ");
                System.out.println(resultSet.getString("Location") + "  ");
                System.out.println(resultSet.getString("Type") + "  ");
                System.out.println(resultSet.getString("Start") + "  ");
                System.out.println(resultSet.getString("End") + "  ");
                System.out.println(resultSet.getString("Customer_ID") + "  ");
                System.out.println(resultSet.getString("User_ID") + "  ");
                System.out.println(resultSet.getString("Contact_ID") + "  ");
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        // instance of a
        // Connection
        // object

    }
}
