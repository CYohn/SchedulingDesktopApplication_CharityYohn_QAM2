package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utilities.DatabaseConnection;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {

    Stage stage;
    Parent scene;


    private ResourceBundle languageResource;

    @FXML
    private Label welcomeBackLabel;

    @FXML
    private Label userNameReqLabel;

    @FXML
    private Label userNameLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Label loginInstructionLabel;

    @FXML
    private Button clearButton;

    @FXML
    private Button loginButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField userNameField;


    @FXML
    private Label incorrectInfoLabel;

    @FXML
    private Label passwordRequiredLabel;

    @FXML
    private Label locationLabel;

    private static String dbPassword;

    @FXML
    private Label userLocationLabel;


    @FXML
    void clearButtonClick(MouseEvent event) {
        userNameField.clear();
        passwordField.clear();
    }

    static Connection connection = DatabaseConnection.getConnection();
    static PreparedStatement allUsersPreparedStatement;

    public String getUserPassword(String userName) throws SQLException {

        ResultSet getUserResults;
        try {
            String getUserSearchStatement = "SELECT User_Name, Password FROM client_schedule.users WHERE User_Name=" + "'" + userName + "';";
            PreparedStatement getDivisionsFromDB = DatabaseConnection.getConnection().prepareStatement(getUserSearchStatement);
            getUserResults = getDivisionsFromDB.executeQuery(getUserSearchStatement);


            while (getUserResults.next()) {
                userName = getUserResults.getString("User_Name");
                System.out.println("Results from getUserPassword: User_Name from DB: " + userName);

                dbPassword = getUserResults.getString("Password");
                System.out.println("Results from getUserPassword: Password from DB: " + dbPassword);
                return dbPassword;
            }
            //getDivisionsFromDB.close();
            //connection.close();

        } catch (SQLException throwables) {
            System.out.println("SQLException thrown in getUserPassword(String userName) method in the LoginPageController file");
            throwables.printStackTrace();
        }
        return dbPassword;
    }

    public boolean validateUser() throws SQLException {
        String password = passwordField.getText();
        System.out.println("User entered password in validateUser():" + password);
        String dbPassword = getUserPassword(userNameField.getText());
        System.out.println("dbPassword from validateUser()" + dbPassword);
        if (dbPassword.equals(password)) {
            System.out.println("validateUser() results returns true");
            return true;
        } else {
            System.out.println("validateUser() results returns false");
            return false;
        }
    }

    @FXML
    void loginButtonClick(MouseEvent event) throws IOException, SQLException {

        boolean validationStatus = validateUser();

        if (validationStatus == true) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/views/TabbedPaneView.fxml"))));
            stage.setScene(new Scene(scene));
            stage.show();
        } else {
            incorrectInfoLabel.setVisible(true);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        System.out.println("Login Form Initialized!");
        passwordRequiredLabel.setVisible(false);
        userNameReqLabel.setVisible(false);
        incorrectInfoLabel.setVisible(false);

//        Locale location = new Locale("fr","FR"); //For testing French
//        String displayLanguage = location.getLanguage(); //For testing French
        Locale location = Locale.getDefault();
        System.out.println(location);
        String displayLanguage = location.getDisplayLanguage();
        System.out.println(displayLanguage);
        userLocationLabel.setText(String.valueOf(location));

        this.languageResource = ResourceBundle.getBundle("utilities/Language", location);

        if (displayLanguage.equals("fr") || displayLanguage.equals("en")) {

            welcomeBackLabel.setText(languageResource.getString("welcomeBackLabel"));
            loginInstructionLabel.setText(languageResource.getString("loginInstructionLabel"));
            userNameLabel.setText(languageResource.getString("userNameLabel"));
            passwordLabel.setText(languageResource.getString("passwordLabel"));
            userNameReqLabel.setText(languageResource.getString("userNameReqLabel"));
            incorrectInfoLabel.setText(languageResource.getString("incorrectInfoLabel"));
            passwordRequiredLabel.setText(languageResource.getString("passwordRequiredLabel"));
            locationLabel.setText(languageResource.getString("locationLabel"));
            userNameField.setPromptText(languageResource.getString("userNameFieldPrompt"));
            passwordField.setPromptText(languageResource.getString("passwordFieldPrompt"));
            loginButton.setText(languageResource.getString("loginButtonText"));
            clearButton.setText(languageResource.getString("clearButtonText"));
        }
    }
}


