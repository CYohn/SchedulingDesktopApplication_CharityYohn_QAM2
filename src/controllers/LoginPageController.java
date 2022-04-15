package controllers;

import interfacesDao.UsersInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {

    @FXML
    private Button clearButton;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;
    String password = passwordField.getText();


    @FXML
    private TextArea userNameField;
    String userName = userNameField.getText();


    @FXML
    void clearButton(MouseEvent event) {
        userNameField.clear();
        passwordField.clear();
    }

    @FXML
    void loginButton(MouseEvent event) {
        UsersInterface.getAllUsers();

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized!");
    }
}
