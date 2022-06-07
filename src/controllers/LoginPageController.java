package controllers;

import implementationsDao.AppointmentsImplement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import Objects.Appointment;
import Objects.User;

import java.io.*;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.time.ZoneOffset.UTC;
import static utilities.TimezoneConversion.convertUTCToUserTime;

/**
 * A controller which controls the login page.
 */
public class LoginPageController implements Initializable {
    /**
     * A variable which holds the user's password.
     */
    private static String dbPassword;
    /**
     * Sets the stage
     */
    Stage stage;

    /**
     * The logged in user ID
     */
    int loggedUserId;
    /**
     * Sets the scene
     */
    Parent scene;

    /**
     * gets the logged in user ID
     *
     * @return Logged in user ID
     */
    public int getLoggedUserId() {
        return loggedUserId;
    }

    /**
     * Sets the logged in username
     *
     * @param userName username
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * The user name entry on the form
     */
    String userName;
    /**
     * An ObservableList to hold the upcoming appointments. An observable list was chosen in the event that more than one
     * upcoming appointment is found.
     */
    ObservableList<Appointment> upcomingAppointments = FXCollections.observableArrayList();
    /**
     * A resource bundle for page translation.
     */
    private ResourceBundle languageResource;
    /**
     * A simple form label welcoming the user back. This terminology was chosen because in a business sense, the user is
     * likely a daily user of the application. For example, the target user for an application such as this might be office staff.
     */
    @FXML
    private Label welcomeBackLabel;
    /**
     * An error label which appears if the user does not input a username and attempts to log in.
     */
    @FXML
    private Label userNameReqLabel;
    /**
     * A text field label indicating where to enter the username.
     */
    @FXML
    private Label userNameLabel;
    /**
     * A text field label indicating where to enter the user's password.
     */
    @FXML
    private Label passwordLabel;
    /**
     * A form label asking the user to log in.
     */
    @FXML
    private Label loginInstructionLabel;
    /**
     * The clear button clears the form.
     */
    @FXML
    private Button clearButton;
    /**
     * The login button is where the user presses to attempt to log in.
     */
    @FXML
    private Button loginButton;
    /**
     * A text field to enter a password. This password text field hides the user's password by default.
     */
    @FXML
    private PasswordField passwordField;
    /**
     * A text field to enter the user's username.
     */
    @FXML
    private TextField userNameField;
    /**
     * A warning label which appears if the user's username or password do not match the entries in the database.
     */
    @FXML
    private Label incorrectInfoLabel;
    /**
     * A warning label which appears if the user attempts to login without entering a password.
     */
    @FXML
    private Label passwordRequiredLabel;
    /**
     * A label which shows the user's location obtained from their device.
     */
    @FXML
    private Label locationLabel;
    /**
     * A label which indicates what the location is.
     */
    @FXML
    private Label userLocationLabel;


    /**
     * Clears the form
     *
     * @param event When the user clicks the clear button
     */
    @FXML
    void clearButtonClick(MouseEvent event) {
        userNameField.clear();
        passwordField.clear();
    }

    /**
     * Checks the username for a password match
     * This will probably be revised on a future version
     *
     * @param userName The username entered by the user
     * @return The user password
     * @throws SQLException May throw an SQL exception when working with user information in the database
     */
    public String getUserPassword(String userName) throws SQLException {

        ResultSet getUserResults;
        try {
            String getUserSearchStatement = "SELECT User_Name, Password, User_ID FROM client_schedule.users " +
                    "WHERE User_Name=" + "'" + userName + "';";
            PreparedStatement getUserFromDB = DatabaseConnection.getConnection().prepareStatement(getUserSearchStatement);
            getUserResults = getUserFromDB.executeQuery(getUserSearchStatement);

            while (getUserResults.next()) {
                int loggedUserId = getUserResults.getInt("User_ID");
                System.out.println("Results from getUserPassword(): User_Id from DB : " + loggedUserId);
                setLoggedUserId(loggedUserId);

                userName = getUserResults.getString("User_Name");
                System.out.println("Results from getUserPassword: User_Name from DB: " + userName);
                setUserName(userName);

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

    /**
     * Validates the user information
     *
     * @return Returns true if the user information correctly matches information in the database.
     * @throws SQLException An exception that provides information on a database access error or other errors
     */
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
        } else {
            incorrectInfoLabel.setVisible(true);
            System.out.println("validateUser() results returns false");
            return false;
        }
    }

    /**
     * Login procedure
     * First the method checks for empty fields and shows an error if any are found. If no fields are empty, the method
     * calls the validation procedure. If validation is true then the method logs the login to login_activity.txt
     * Additionally, if validation is true the method checks for upcoming user appointments and shows an alert with the information
     * about whether an upcoming appointment is found or not.
     * If validation is false the procedure shows an error and logs the invalid attempt to login_activity.txt
     *
     * @param event The user clicks the login button
     * @throws IOException  Signals that an I/O exception of some sort has occurred
     * @throws SQLException An exception that provides information on a database access error or other errors
     */
    @FXML
    void loginButtonClick(MouseEvent event) throws IOException, SQLException {

        if (userNameField.getText().isEmpty() == true) {
            userNameReqLabel.setVisible(true);
        }
        if (passwordField.getText().isEmpty() == true) {
            passwordRequiredLabel.setVisible(true);
        }

        boolean validationStatus = validateUser();
        if (validationStatus == false) {
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

//            /**
//             * This hard-coded appointment is in place to test the alert triggered by an appointment scheduled
//             * within 15 minutes of the user login.
//             */
//            LocalDateTime userTime = LocalDateTime.now().plusMinutes(14);
//            loggedUserId = getLoggedUserId();
//            Appointment appointmentOnLaunch = new Appointment(
//                    "On Launch",
//                    "Appointment created to test alert",
//                    "123 Software Lane, Springfield",
//                    "De-Briefing", userTime,
//                    userTime.plusMinutes(44),
//                    1,
//                    loggedUserId,
//                    1
//            );

//            AppointmentsImplement.addAppointment(appointmentOnLaunch);

            searchForUpcomingAppointments();


            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            scene = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/views/TabbedPaneView.fxml"))));
            stage.setScene(new Scene(scene));
            stage.show();
            alertOfUpcomingAppointment();

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

    /**
     * Sets the logged in user ID
     *
     * @param loggedUserId The logged in user ID
     */
    public void setLoggedUserId(int loggedUserId) {
        this.loggedUserId = loggedUserId;
    }

    /**
     * Searches for upcoming appointments. This method is called if the login is successful.
     */
    public void searchForUpcomingAppointments() {

        ObservableList<Appointment> allAppointments;
        ObservableList<Appointment> appointmentsWithConvertedTimes = FXCollections.observableArrayList();
        ObservableList<Appointment> filteredList = FXCollections.observableArrayList();
        allAppointments = AppointmentsImplement.getAllAppointments;
        loggedUserId = getLoggedUserId();
        System.out.println("In the method SearchForUpcomingAppointments() The userID used to search: " + loggedUserId);

        try {
            AppointmentsImplement.getAllAppointments();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Appointment appointment : allAppointments) {

            //System.out.println("Appointment from the populate apt Table: " + appointment);
            LocalDateTime startUTC = appointment.getStartDateTime();
            LocalDateTime endUTC = appointment.getEndDateTime();
            LocalDate startDate = convertUTCToUserTime(startUTC).toLocalDate();
            LocalTime startTime = convertUTCToUserTime(startUTC).toLocalTime();
            LocalDate endDate = convertUTCToUserTime(endUTC).toLocalDate();
            LocalTime endTime = convertUTCToUserTime(endUTC).toLocalTime();

            int appointmentId = appointment.getAppointmentId();
            String title = appointment.getTitle();
            String description = appointment.getDescription();
            String location = appointment.getLocation();
            String type = appointment.getType();
            int customerId = appointment.getCustomerId();
            int userId = appointment.getUserId();
            int contactId = appointment.getContactId();

            Appointment convertedTimesAppointment = new Appointment
                    (appointmentId, title, description, location, type, startDate, startTime,
                            endDate, endTime, customerId, userId, contactId);
            //appointmentsWithConvertedTimes.clear();
            System.out.println("Start Date and Time for convertedTimesAppointment " +
                    convertedTimesAppointment.getStartDate() + "  " + convertedTimesAppointment.getStartTime() + "  Apt ID: " + convertedTimesAppointment.getAppointmentId() +
                    "  User ID: " + convertedTimesAppointment.getUserId());
            appointmentsWithConvertedTimes.add(convertedTimesAppointment);
            System.out.println("Converted Times Appointment ID From Login Page controller" + convertedTimesAppointment.getAppointmentId());
        }
        LocalDate userToday = LocalDate.now();
        LocalTime timePlus15Min = LocalTime.now().plusMinutes(15);
        LocalTime timeNow = LocalTime.now();

        appointmentsWithConvertedTimes.stream()
                .filter(apt -> apt.getUserId() == loggedUserId)
                .filter(apt -> apt.getStartDate().equals(userToday))
                .filter(apt -> apt.getStartTime().isAfter(timeNow))
                .filter(apt -> apt.getStartTime().isBefore(timePlus15Min))
                .forEach(upcomingAppointments::add);
    }


    /**
     * Pop-up alert for upcoming appointments.
     */
    public void alertOfUpcomingAppointment() {
        int numberOfUpcomingAppointments = (int) upcomingAppointments.stream().count();

        if (numberOfUpcomingAppointments >= 1) {
            System.out.println("Upcoming appointments list in the alertOfUpcomingAppointments method: \n" +
                    upcomingAppointments);
            for (Appointment apt : upcomingAppointments) {
                int aptId = apt.getAppointmentId();
                LocalDate aptStartDate = apt.getStartDate();
                LocalTime aptStartTime = apt.getStartTime();

                Alert infoRequiredAlert = new Alert(Alert.AlertType.WARNING);
                infoRequiredAlert.setTitle("You Have An Appointment Starting in 15 Minutes or Less");
                infoRequiredAlert.setHeaderText("Please See the Appointment Information Below: ");
                infoRequiredAlert.setContentText("Appointment ID: " + aptId + " Starting at: " + aptStartTime + " On Date: " + aptStartDate);
                infoRequiredAlert.showAndWait();
            }
        }
        if (numberOfUpcomingAppointments == 0) {
            Alert infoRequiredAlert = new Alert(Alert.AlertType.INFORMATION);
            infoRequiredAlert.setTitle("You Have No Upcoming Appointments");
            //infoRequiredAlert.setHeaderText("Grab a coffee, you have 15 minutes with no appointments");
            infoRequiredAlert.setContentText("Grab a coffee or tea, you have at least 15 minutes with no appointments");
            infoRequiredAlert.showAndWait();
        }
    }

    /**
     * Initializes the page
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            AppointmentsImplement.getAllAppointments();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //System.out.println("Login Form Initialized!");
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

        /**
         * Checks the display language and directs the page to the correct translation
         */
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

        /**
         * Change listener - hides the empty field error labels when the user moves focus away from the field
         */
        userNameField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                userNameReqLabel.setVisible(false);
                incorrectInfoLabel.setVisible(false);
            }
        });

        /**
         * Change listener - hides the empty field error labels when the user moves focus away from the field
         */
        passwordField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                passwordRequiredLabel.setVisible(false);
                incorrectInfoLabel.setVisible(false);
            }
        });
    }
}


