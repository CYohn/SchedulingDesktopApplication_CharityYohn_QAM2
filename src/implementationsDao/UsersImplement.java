package implementationsDao;

import Objects.Customer;
import Objects.User;
import interfacesDao.UsersInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utilities.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersImplement extends DatabaseConnection implements UsersInterface  {



    ObservableList<User> getAllUsers = UsersInterface.getAllUsers();
    public static ObservableList<User> userNames = FXCollections.observableArrayList();

    static Connection connection = DatabaseConnection.getConnection();
    static PreparedStatement getUserNamesPreparedStatement;
    static String sqlQuery = "SELECT * FROM users";


    static {
        try {
            getUserNamesPreparedStatement = DatabaseConnection.makePreparedStatement(sqlQuery, connection);
            System.out.println("allUsersPreparedStatement was successful");
        } catch (SQLException e) {
            System.out.println("allUsersPreparedStatement in the file UsersImplement encountered an error");
            e.getMessage();
            e.getCause();
            e.printStackTrace();
        }
    }

    public static void getAllUserNames() throws SQLException {
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

    @Override
    public void updateUser() {

    }

    @Override
    public void deleteUser() {

    }

    @Override
    public void addUser() {

    }
}
