package utilities;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * This class handles the database connection.
 */
public class DatabaseConnection {
    /**
     * The database protocol.
     */
    private static final String protocol = "jdbc";
    /**
     * The database vendor.
     */
    private static final String vendor = ":mysql:";
    /**
     * The database location.
     */
    private static final String location = "//localhost/";
    /**
     * The database name.
     */
    private static final String databaseName = "client_schedule";
    /**
     * The database URL
     */
    private static final String jdbcUrl = protocol + vendor + location + databaseName; // LOCAL
    /**
     * The driver reference.
     */
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    /**
     * The database username
     */
    private static final String userName = "sqlUser"; // Username
    /**
     * The database connection
     */
    public static Connection connection = null;  // Connection Interface
    /**
     * The database password
     */
    private static String password = "Passw0rd!"; // Password
    /**
     * Database prepared statement.
     */
    private static PreparedStatement preparedStatement;

    /**
     * This method makes the database connection.
     */
    public static void makeConnection() {

        try {
            Class.forName(driver); // Locate Driver
            //password = Details.getPassword(); // Assign password
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // reference Connection object
            System.out.println("Connection successful!");
        } catch (ClassNotFoundException e) {
            System.out.println("Error:" + e.getMessage());
            e.getCause();
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Error:" + e.getMessage());
            e.getCause();
            e.printStackTrace();
        }
    }

    /**
     * Gets the database connection.
     *
     * @return The database connection.
     */
    public static Connection getConnection() {
        return connection;
    }

    /**
     * Closes the database connection.
     *
     * @return null
     */
    public static Connection closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.getCause();
            e.printStackTrace();
        }
        return null;
    }

    /**
     * The constructs a prepared statement.
     *
     * @param sqlStatement A string that will hold an SQL query.
     * @param conn         The database connection
     * @return Returns the database response to the prepared statement.
     * @throws SQLException An exception that provides information on a database access error or other errors.
     */
    public static PreparedStatement makePreparedStatement(String sqlStatement, Connection conn) throws SQLException {
        if (conn != null)
            preparedStatement = conn.prepareStatement(sqlStatement);
        else
            System.out.println("Prepared Statement Creation Failed!");
        return null;
    }

    public static PreparedStatement getPreparedStatement() throws SQLException {
        if (preparedStatement != null)
            return preparedStatement;
        else System.out.println("Null reference to Prepared Statement");
        return null;
    }

}