package implementationsDao;

import Objects.User;
import controllers.LoginPageController;
import interfacesDao.UsersInterface;
import javafx.collections.ObservableList;
import utilities.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsersImplement implements UsersInterface {

    private static String userName = LoginPageController.password;
    private static String password = LoginPageController.userName;
    private static String dbPassword;

    ObservableList<User> getAllUsers = UsersInterface.getAllUsers();

    static Connection connection = DatabaseConnection.getConnection();
    static PreparedStatement allUsersPreparedStatement;
    static String sqlQuery = "SELECT * FROM users";


    static {
        try {
            allUsersPreparedStatement = DatabaseConnection.makePreparedStatement(sqlQuery, connection);
            System.out.println("allUsersPreparedStatement was successful");
        } catch (SQLException e) {
            System.out.println("allUsersPreparedStatement in the file UsersImplement encountered an error");
            e.getMessage();
            e.getCause();
            e.printStackTrace();
        }
    }


    public static String getUserPassword(String userName) {
        ResultSet getUserResults;
        try {

            String getUserSearchStatement = "SELECT User_Name, Password FROM client_schedule.users WHERE User_Name=" + "'" + userName + "';";
            PreparedStatement getDivisionsFromDB = DatabaseConnection.getConnection().prepareStatement(getUserSearchStatement);
            getUserResults = getDivisionsFromDB.executeQuery(getUserSearchStatement);


            while (getUserResults.next()) {
                userName = getUserResults.getString("User_Name");
                System.out.println("User Name: " + userName);

                dbPassword = getUserResults.getString("Password");
                System.out.println("Password: " + dbPassword);
            }

        } catch (SQLException throwables) {
            System.out.println("SQLException thrown in populateDivisionsList() method in the FirstLevelDivisionImplement file");
            throwables.printStackTrace();
        }
        return dbPassword;
    }

    public static boolean validateUser(String password){
        String userPassword = password;
        String dbPassword = getUserPassword(userName);
        if(dbPassword == userPassword){
            return true;
        }
        else{
            return false;
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
