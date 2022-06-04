package implementationsDao;

import Objects.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utilities.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Interacts wit the database to retrieve the users table and add the users to an observableList.
 */
public class UsersImplement extends DatabaseConnection {

    /**
     * An observable list of usernames and IDs.
     */
    public static ObservableList<User> userNames = FXCollections.observableArrayList();

    /**
     * Gets all users from the database.
     *
     * @throws SQLException An exception that provides information on a database access error or other errors.
     */
    public static void getAllUserNames() throws SQLException {
        userNames.clear();
        String allCustomersQuery = "SELECT User_Name, User_ID" +
                " FROM client_schedule.users";

        PreparedStatement getUserNamesPreparedStatement = DatabaseConnection.getConnection().prepareStatement(allCustomersQuery);
        System.out.println("usersImplementPreparedStatement was successful in UsersImplement.getAllUserNames()");
        ResultSet allUserNamesResults;

        try {

            if (getUserNamesPreparedStatement != null) {
                allUserNamesResults = getUserNamesPreparedStatement.executeQuery();


                while (allUserNamesResults.next()) {

                    String userName = allUserNamesResults.getString("User_Name");
                    //System.out.println("allUserNamesResults userName: " + userName);

                    int userId = allUserNamesResults.getInt("User_ID");
                    //System.out.println("allUserNamesResults userName: " + userName);

                    User user = new User(userId, userName);

                    userNames.add(user);
                    //System.out.println("Customer object populated in getAllCustomers list: " + customer);
                }
            }
            else{System.out.println("ResultSet was null");}

        }
        catch(SQLException throwables){
            System.out.println("SQLException thrown in getAllCustomers method in the CustomerImplement file");
            throwables.getMessage();
            throwables.getCause();
            throwables.printStackTrace();
        }
    }
}

