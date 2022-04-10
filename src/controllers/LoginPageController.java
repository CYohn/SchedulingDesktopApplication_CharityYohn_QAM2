package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {

    @FXML
    private Button clearButton;

    @FXML
    private Button loginButton;

    @FXML
    private TextArea passwordField;

    @FXML
    private TextArea userNameField;



    @FXML
    void clearButton(MouseEvent event) {
        userNameField.clear();
        passwordField.clear();
    }

    @FXML
    void loginButton(MouseEvent event) {}



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized!");
    }
}
