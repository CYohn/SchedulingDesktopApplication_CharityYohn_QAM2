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

public class UsersImplement extends DatabaseConnection implements UsersInterface  {


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
