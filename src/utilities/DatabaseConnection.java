package utilities;

import java.sql.*;


public class DatabaseConnection {


    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL

    private static final String driver = ".idea/libraries/mysql-connector-java-8.0.25/mysql-connector-java-8.0.25.jar"; // Driver reference
    private static final String userName = "sqlUser"; // Username
    private static final String password = "Passw0rd!"; // Password
    private static Connection connection = null;  // Connection Interface
    private static PreparedStatement preparedStatement;


    /**
     * Connects to the database
     *
     * @return null
     */
    public static Connection makeConnection() {
        try {
            Class.forName(driver); // Locate Driver
            // password = Details.getPassword(); //Assign password
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // reference Connection object
            System.out.println("Successfully connected to the database");
        } catch (ClassNotFoundException e) {
            System.out.println("Error: ClassNotFoundException in connection method in the DatabaseConnection file:" + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Error: SQLException in connection method in the DatabaseConnection file:" + e.getMessage());
        }
        return null;
    }

    /**
     * Returns database connection
     *
     * @return connection
     */
    public static Connection getConnection() {
        return connection;
    }

    /**
     * Closes the database connection
     *
     * @return null
     */
    public static Connection closeConnection() {
        try {
            connection.close();
            System.out.println("Successfully closed the database connection");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static PreparedStatement makePreparedStatement(String sqlStatement, Connection connection) throws SQLException {
        if (connection != null) preparedStatement = connection.prepareStatement(sqlStatement);
        else
            System.out.println("Prepared Statement Creation Failed in the file DatabaseConnection");
        return null;
    }

    public static PreparedStatement getPreparedStatement() throws SQLException {
        if (preparedStatement != null)
            return preparedStatement;
        else System.out.println("Null reference to Prepared Statement in the file DatabaseConnection");
        return null;
    }

}
