package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import main.Main;
import utilities.DatabaseConnection;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.time.ZoneOffset.UTC;

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
            String getUserSearchStatement = "SELECT User_Name, Password FROM client_schedule.users " +
                    "WHERE User_Name=" + "'" + userName + "';";
            PreparedStatement getUserFromDB = DatabaseConnection.getConnection().prepareStatement(getUserSearchStatement);
            getUserResults = getUserFromDB.executeQuery(getUserSearchStatement);

            while (getUserResults.next()) {
                userName = getUserResults.getString("User_Name");
                System.out.println("Results from getUserPassword: User_Name from DB: " + userName);

                dbPassword = getUserResults.getString("Password");
                System.out.println("Results from getUserPassword: Password from DB: " + dbPassword);
                return dbPassword;
            }

        } catch (SQLException throwables) {
            System.out.println("SQLException thrown in getUserPassword(String userName) method in the LoginPageController file");
            incorrectInfoLabel.setVisible(true);
            throwables.printStackTrace();
        }
        return dbPassword;
    }

    public boolean validateUser() throws SQLException {
        String password = passwordField.getText();
        System.out.println("User entered password in validateUser():" + password);
        String dbPassword = getUserPassword(userNameField.getText());
        System.out.println("dbPassword from validateUser()" + dbPassword);

        if (dbPassword == null) {
            System.out.println("validateUser() results returns null DB result");
            incorrectInfoLabel.setVisible(true);
            return false;}

        if (dbPassword.equals(password)) {
            System.out.println("validateUser() results returns true");
            return true;
        }

        else {
            incorrectInfoLabel.setVisible(true);
            System.out.println("validateUser() results returns false");
            return false;
        }
    }

    @FXML
    void loginButtonClick(MouseEvent event) throws IOException, SQLException {

        if (userNameField.getText().isEmpty() == true){userNameReqLabel.setVisible(true);}
        if (passwordField.getText().isEmpty() == true){passwordRequiredLabel.setVisible(true);}

        boolean validationStatus = validateUser();
        if (validationStatus == false){
            incorrectInfoLabel.setVisible(true);
            try {
                PrintWriter pw = new PrintWriter(new FileOutputStream(
                        new File("login_activity.txt"),//Change to login_activity.txt
                        true /* append = true */));
                //out.txt will appear in the project's root directory under NetBeans projects
                //Note that Notepad will not display the following lines on separate lines
                pw.append("Unsuccessful login attempt by user:  " + userNameField.getText() + " \n");//This is the information to be appended DateTime, UserName etc.
                pw.append("Timestamp: " + Timestamp.valueOf(LocalDateTime.now(UTC)) + " UTC Time\n");
                pw.append("\n");
                pw.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        if (validationStatus == true) {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/views/TabbedPaneView.fxml"))));
            stage.setScene(new Scene(scene));
            stage.show();

            try {
                PrintWriter pw = new PrintWriter(new FileOutputStream(
                        new File("login_activity.txt"),//Change to login_activity.txt
                        true /* append = true */));
                //out.txt will appear in the project's root directory under NetBeans projects
                //Note that Notepad will not display the following lines on separate lines
                pw.append("Successful login by user:  " + userNameField.getText() + " \n");//This is the information to be appended DateTime, UserName etc.
                pw.append("Timestamp: " + Timestamp.valueOf(LocalDateTime.now(UTC)) + " UTC Time\n");
                pw.append("\n");
                pw.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
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

        Locale location = Locale.getDefault(); //For using current location
        String displayLanguage = System.getProperty("user.language"); //For using the language setting from the user's machine

        System.out.println(location);
        System.out.println("Language from System.getProperty():  " + displayLanguage);
        String timeZone = TimeZone.getDefault().getID();
        userLocationLabel.setText(String.valueOf(timeZone));

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

        userNameField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                userNameReqLabel.setVisible(false);
                incorrectInfoLabel.setVisible(false);
            }
        });

        passwordField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                passwordRequiredLabel.setVisible(false);
                incorrectInfoLabel.setVisible(false);
            }
        });
    }
}


