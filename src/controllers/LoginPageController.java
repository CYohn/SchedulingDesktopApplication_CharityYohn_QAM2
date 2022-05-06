package controllers;

import interfacesDao.UsersInterface;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {

    Stage stage;
    Parent scene;

    @FXML
    private Button clearButton;

    @FXML
    private Button loginButton;

    @FXML
    private static PasswordField passwordField;

    @FXML
    private static TextField userNameField;

    @FXML
    private Label incorrectInfoLabel;

    @FXML
    private Label passwordRequiredLabel;

    @FXML
    private Label userNameReqLabel;

    public static String getPasswordField() {
        return passwordField.toString();
    }

    public void setPasswordField(PasswordField passwordField) {
        this.passwordField = passwordField;
    }

    public void setUserNameField(TextField userNameField) {
        this.userNameField = userNameField;
    }

    public static String getUserNameField() {
        return userNameField.toString();
    }

    @FXML
    void clearButtonClick(MouseEvent event) {
        userNameField.clear();
        passwordField.clear();
    }

    @FXML
    void loginButtonClick(MouseEvent event) throws IOException {


        stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((getClass().getResource("/views/TabbedPaneView.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
        }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized!");
    }
}

