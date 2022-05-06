package implementationsDao;

import Objects.Customer;
import Objects.User;
import controllers.LoginPageController;
import interfacesDao.CustomersInterface;
import interfacesDao.UsersInterface;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;
import utilities.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsersImplement implements UsersInterface {

    private final String userName = LoginPageController.getUserNameField();
    private final String password = LoginPageController.getPasswordField();

    ObservableList<User> getAllUsers = UsersInterface.getAllUsers();

    static Connection connection = DatabaseConnection.getConnection();
    static PreparedStatement allUsersPreparedStatement;
    static String sqlQuerry = "SELECT * FROM users";


    static {
        try {
            allUsersPreparedStatement = DatabaseConnection.makePreparedStatement(sqlQuerry, connection);
            System.out.println("allUsersPreparedStatement was successful");
        } catch (SQLException e) {
            System.out.println("allUsersPreparedStatement in the file UsersImplement encountered an error");
            e.getMessage();
            e.getCause();
            e.printStackTrace();
        }
    }


    public void getUser(String userName){}

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
