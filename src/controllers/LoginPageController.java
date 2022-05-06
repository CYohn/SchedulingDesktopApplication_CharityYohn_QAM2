package controllers;

import implementationsDao.UsersImplement;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {

    public static String password = getPasswordField().getText();
    public static String userName = getUserNameField().getText();
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

    @FXML
    private Label locationLabel;


    public static PasswordField getPasswordField() {
        return passwordField;
    }

    public static TextField getUserNameField() {
        return userNameField;
    }

    @FXML
    void clearButtonClick(MouseEvent event) {
        userNameField.clear();
        passwordField.clear();
    }

    @FXML
    void loginButtonClick(MouseEvent event) throws IOException {
        boolean validationStatus = UsersImplement.validateUser(passwordField.getText());
        if (validationStatus == true){
            stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load((getClass().getResource("/views/TabbedPaneView.fxml")));
            stage.setScene(new Scene(scene));
            stage.show();
        }
        else{incorrectInfoLabel.setVisible(true);}
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Login Form Initialized!");
        passwordRequiredLabel.setVisible(false);
        userNameReqLabel.setVisible(false);
        incorrectInfoLabel.setVisible(false);
    }
}

