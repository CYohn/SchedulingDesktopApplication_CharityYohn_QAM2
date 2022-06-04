package controllers;

import Objects.Appointment;
import Objects.Contact;
import Objects.Customer;
import Objects.User;
import implementationsDao.AppointmentsImplement;
import implementationsDao.ContactsImplement;
import implementationsDao.UsersImplement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import utilities.TimezoneConversion;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

import static implementationsDao.AppointmentsImplement.getAppointmentsByCustomerID;
import static implementationsDao.ContactsImplement.getAllContactNames;
import static implementationsDao.CustomersImplement.getAllCustomers;
import static implementationsDao.UsersImplement.getAllUserNames;

// Idea for future revisions: If the User ID is the same as the person who logs in, assign the user ID based on the login

/**
 * This class is a controller class for the Add appointments screen.
 */
public class AddAppointmentController extends TimezoneConversion implements Initializable {

    /**
     * The user's selected start date and time. This variable is used in the validation methods.
     */
    private static LocalDateTime startDate;
    /**
     * The user's selected end date and time. This variable is used in the validation methods.
     */
    private static LocalDateTime endDate;
    /**
     * The iD of the customer selected in the customer table.
     */
    private static int selectedCustomerID;
    /**
     * The customer selected by the user in the customer table.
     */
    Customer selectedCustomer;
    /**
     * The overlapping appointment found in the overlapping appointments validation. This is used in the pop-up to inform the user
     * of which appointment the selected start time and date overlaps with. Intended to increase the applications's usability.
     */
    Appointment overlappingAppointment;

    /**
     * Observable list of contact names. Used to set the contacts combo box.
     */
    private ObservableList<Contact> contactNames = ContactsImplement.contactNames;

    /**
     * Observable list of user names. Used to set the contents of the users combo box.
     */
    private ObservableList<User> userNames = UsersImplement.userNames;

    /**
     * Observable list of appointment types. Used to set the contents of the types combo box.
     */
    private ObservableList<String> appointmentTypes = FXCollections.observableArrayList
            ("Planning Session", "Progress Update", "De-Briefing");

    /**
     * Observable list of appointments filtered and obtained by the database. The list is filtered by customer ID.
     */
    private ObservableList<Appointment> getAppointmentsByCustomerID = AppointmentsImplement.appointmentsByCustomerID;


    //Table and columns
    /**
     * Table used to select the customer to associate with the appointment
     */
    @FXML
    private TableView<Customer> customerTable;

    /**
     * The customer ID column of the customer table
     */
    @FXML
    private TableColumn<Customer, Integer> customerIdColumn;

    /**
     * The name column of the customer table.
     */
    @FXML
    private TableColumn<Customer, String> customerNameColumn;

    /**
     * the phone column of the customer table.
     */
    @FXML
    private TableColumn<Customer, String> customerPhoneColumn;

    //Text Fields
    /**
     * The text field to enter the title of the appointment
     */
    @FXML
    private TextField titleTxtField;

    /**
     * The text area to input the description field of the appointment.
     */
    @FXML
    private TextArea appointmentDescriptionTxtField;

    /**
     * The text field to input the location of the appointment.
     */
    @FXML
    private TextField locationTxtField;

    //Date pickers
    /**
     * Element for input of the start date selection
     */
    @FXML
    private DatePicker startDatePicker;

    /**
     * Element for input of the end date selection
     */
    @FXML
    private DatePicker endDatePicker;

    //Form ComboBoxes
    /**
     * Combo box which holds all of the contacts in the database.
     */
    @FXML
    private ComboBox<Contact> contactComboBox;

    /**
     * Combo box for choosing the end time of the appointment. This combo box is configure to only allow time selections during business hours.
     * The bos also shows times converted from Easter time (United States NY) to the user's local times.
     */
    @FXML
    private ComboBox<LocalTime> endTimeHrComboBox;

    /**
     * Combo box for choosing the start time of the appointment. This combo box is configure to only allow time selections during business hours.
     * The bos also shows times converted from Easter time (United States NY) to the user's local times.
     */
    @FXML
    private ComboBox<LocalTime> startTimeHrComboBox;

    /**
     * Combo Box for choosing the appointment type.
     */
    @FXML
    private ComboBox<String> typeComboBox;

    /**
     * Combo box for the selection of the user associated with the appointment. The users are pulled from the database.
     */
    @FXML
    private ComboBox<User> userComboBox;

    //Form Buttons

    /**
     * Button to save the appointment.
     */
    @FXML
    private Button saveButton;

    /**
     * Button to clear the form.
     */
    @FXML
    private Button clearButton;

    //Active Form Labels
    /**
     * Label which appears when an error has occurred concerning the date and times. The date and times go through two
     * real time validations. The first validation checks that the start date and time is before the end date and time. The
     * second validation checks that the appointment is in the future. If either of these validations fail the form will
     * show this error message.
     */
    @FXML
    private Label dateAndTimeErrorLabel;

    /**
     * An error label which appears when the user tries to save the form missing a field.
     */
    @FXML
    private Label allFieldsRequiredLabel;

    /**
     * An error label which shows if the user's description input exceeds the length allowed by the database.
     */
    @FXML
    private Label descriptionLengthAlert;

    /**
     * An error label which shows if the user's location input exceeds the length allowed by the database.
     */
    @FXML
    private Label locationLengthAlert;

    /**
     * An error label which shows if the user's title input exceeds the length allowed by the database.
     */
    @FXML
    private Label titleLengthAlert;

    /**
     * A label informing the user that the save was successful.
     */
    @FXML
    private Label saveSuccessfulLabel;

    /**
     * An error label informing the user that the entry was not saved. An entry may not save if the date and time
     * validations fail. For example, if the start date is after the end date or if the appointment is in the past.
     * Additionally, all fields must have input or selections and must comply with the length restrictions set forth
     * in the database. Finally, appointments for a specific customer must not overlap.
     * If failure any of these validations occur, the appointment will not save to the database.
     * <p>
     * Other causes of save errors, although less likely could be due to connectivity issues with the database, changes to the database tables, or changes to
     * user account access.
     */
    @FXML
    private Label saveErrorLabel;

    //Setters and getters for the start and end DateTime selected in the comboBox

    /**
     * Gets the appointment start date
     *
     * @return Appointment start date
     */
    public static LocalDateTime getStartDate() {
        return startDate;
    }

    /**
     * Sets the appointment start date
     *
     * @param startDate Start date of the appointment
     */
    public static void setStartDate(LocalDateTime startDate) {
        AddAppointmentController.startDate = startDate;
    }

    /**
     * Gets the end date and time of the apoointment
     *
     * @return End date and time
     */
    public static LocalDateTime getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date of the appointment
     *
     * @param endDate end date and time
     */
    public static void setEndDate(LocalDateTime endDate) {
        AddAppointmentController.endDate = endDate;
    }

    /**
     * Gets the selected customer ID from the table
     *
     * @return
     */
    public static int getSelectedCustomerID() {
        return selectedCustomerID;
    }

//    public Appointment getOverlappingAppointment() {
//        return overlappingAppointment;
//    }

    /**
     * Sets the ID of the customer selected in the table
     *
     * @param selectedCustomerID The selected customer's ID
     */
    public static void setSelectedCustomerID(int selectedCustomerID) {
        AddAppointmentController.selectedCustomerID = selectedCustomerID;
    }

    /**
     * Sets an overlapping appointment object variable
     *
     * @param overlappingAppointment an appointment object which represents the overlapping appointment
     */
    public void setOverlappingAppointment(Appointment overlappingAppointment) {
        this.overlappingAppointment = overlappingAppointment;
    }

    /**
     * Populates the table in which the user selects a customer to associate with the appointment
     *
     * @param getAllCustomers A fresh list of customers retrieved from the database
     */
    public void populateCustomerTable(ObservableList<Customer> getAllCustomers) {

        customerTable.setItems(getAllCustomers);

        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
    }

    /**
     * Gets the user selection from the customer table
     *
     * @param event When the user clicks on the table
     * @return The selected customer's ID
     */
    @FXML
    int onTableClickGetSelectedCustomer(MouseEvent event) {
        if (!customerTable.getSelectionModel().isEmpty()) {
            selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
            selectedCustomerID = selectedCustomer.getCustomerId();
            System.out.println("SelectedCustomerID from AddAppointmentController: " + selectedCustomerID);
            setSelectedCustomerID(selectedCustomerID);
        }
        return selectedCustomerID;
    }

    /**
     * Populates the start time combo box
     * Converts the business start time from Eastern Time to the user's local time.
     * Then sets the combo bx to only allow selectable times within business hours, but shows the
     * times in the user's local times
     */
    public void populateStartTimeComboBox() {
        LocalDateTime businessStartConverted = TimezoneConversion.getBusinessStartConverted();
        LocalTime businessStartConvertedTime = businessStartConverted.toLocalTime();
        int businessStartHour = businessStartConvertedTime.getHour();
        int businessStartMinute = businessStartConvertedTime.getMinute();
        LocalTime startHour = LocalTime.of(businessStartHour, businessStartMinute);

        LocalDateTime businessEndConverted = TimezoneConversion.getBusinessEndConverted();
        LocalTime businessEndConvertedTime = businessEndConverted.toLocalTime();
        int businessEndHour = businessEndConvertedTime.getHour();
        int businessEndMinute = businessEndConvertedTime.getMinute();
        LocalTime endHour = LocalTime.of(businessEndHour, businessEndMinute);

        while (startHour.isBefore(endHour)) {
            startTimeHrComboBox.getItems().add(startHour);
            startHour = startHour.plusMinutes(15);
        }
    }

    /**
     * Populates the end time combo box
     * Converts the business start time from Eastern Time to the user's local time.
     * Then sets the combo bx to only allow selectable times within business hours, but shows the
     * times in the user's local times
     */
    public void populateEndTimeComboBox() {
        LocalDateTime businessStartConverted = TimezoneConversion.getBusinessStartConverted();
        LocalTime businessStartConvertedTime = businessStartConverted.toLocalTime();
        int businessStartHour = businessStartConvertedTime.getHour();
        int businessStartMinute = businessStartConvertedTime.getMinute();
        LocalTime startHour = LocalTime.of(businessStartHour, businessStartMinute);

        LocalDateTime businessEndConverted = TimezoneConversion.getBusinessEndConverted();
        LocalTime businessEndConvertedTime = businessEndConverted.toLocalTime();
        int businessEndHour = businessEndConvertedTime.getHour();
        int businessEndMinute = businessEndConvertedTime.getMinute();
        LocalTime endHour = LocalTime.of(businessEndHour, businessEndMinute);

        while (startHour.isBefore(endHour)) {
            endTimeHrComboBox.getItems().add(startHour);
            startHour = startHour.plusMinutes(15);
        }
    }

    /**
     * Gets the start date and time selected by the user and turns it into a LocalDatetime for sending to the database
     *
     * @return LocalDatetime start time and date
     */
    public LocalDateTime setStartDateTimeSelection() {
        LocalDateTime startDateTimeSelection = LocalDateTime.of(9999, 12, 31, 23, 59);
        if ((startDatePicker.getValue() != null) && (startTimeHrComboBox.getValue() != null) &&
                (endTimeHrComboBox.getValue() != null) && (endTimeHrComboBox.getValue() != null)) {
            startDateTimeSelection = LocalDateTime.of(startDatePicker.getValue(), startTimeHrComboBox.getValue());
            setStartDate(startDateTimeSelection);
            return startDateTimeSelection;
        } else {
            System.out.println("Start Date is Empty");
        }//do nothing
        return startDateTimeSelection;
    }

    /**
     * Gets the end date and time selected by the user and turns it into a LocalDatetime for sending to the database
     *
     * @return LocalDatetime end time and date
     */
    public LocalDateTime setEndDateTimeSelection() {
        LocalDateTime endDateTimeSelection = LocalDateTime.of(2022, 01, 01, 00, 01);
        if ((endDatePicker.getValue() != null) && (endTimeHrComboBox.getValue() != null)) {
            endDateTimeSelection = LocalDateTime.of(endDatePicker.getValue(), endTimeHrComboBox.getValue());
            setEndDate(endDateTimeSelection);
            return endDateTimeSelection;
        }
        return endDateTimeSelection;
    }

    /**
     * Validates that the selected start time and date are before the end time and date and that the appointment is in th future
     *
     * @return true is the validation passes and false if the validation does not pass
     * @throws Exception
     */


    /**
     * The first of three validation methods for validating overlapping appointments
     *
     * @param startDate The user's selected start date and time of the appointment
     *                  The validation passes if the method returns false
     * @return returns true if an overlapping appointment is found, false if no overlapping appointment is found
     * @throws SQLException getAppointmentsByCustomerID return a list of appointments from the database
     */
    public boolean overlapValidationA(LocalDateTime startDate) throws SQLException {

        for (Appointment appointment : getAppointmentsByCustomerID) {
            LocalDateTime startTimeExistingAppointment = appointment.getStartDateTime();
            LocalDateTime endTimeExistingAppointment = appointment.getEndDateTime();

            if (((startDate.isAfter(startTimeExistingAppointment)) || (startDate.isEqual(startTimeExistingAppointment)))
                    && (startDate).isBefore(endTimeExistingAppointment)) {
                setOverlappingAppointment(appointment);
                return true;
            }
        }
        return false;
    }

    /**
     * The second of three validation methods for validating overlapping appointments
     * The validation passes if the method returns false
     *
     * @param endDate the user selected end date
     * @return true if an overlapping appointment is found, false if no overlapping appointment is found
     * @throws SQLException getAppointmentsByCustomerID return a list of appointments from the database
     *                      which may throw an SQL Exception
     */
    public boolean overlapValidationB(LocalDateTime endDate) throws SQLException {

        for (Appointment appointment : getAppointmentsByCustomerID) {
            LocalDateTime startTimeExistingAppointment = appointment.getStartDateTime();
            LocalDateTime endTimeExistingAppointment = appointment.getEndDateTime();

            if (((endDate.isBefore(endTimeExistingAppointment)) || (endDate.isEqual(endTimeExistingAppointment)))
                    && (endDate).isAfter(startTimeExistingAppointment)) {
                setOverlappingAppointment(appointment);
                return true;
            }
        }
        return false;
    }

    /**
     * The third of three validation methods for validating overlapping appointments
     * The validation passes if the method returns false
     *
     * @param startDate the user's selected start date and time
     * @param endDate   the user's selected end date and time
     * @return true if an overlapping appointment is found, false if no overlapping appointment is found
     * @throws SQLException getAppointmentsByCustomerID return a list of appointments from the database
     *                      which may throw an SQL Exception
     */
    public boolean overlapValidationC(LocalDateTime startDate, LocalDateTime endDate) throws SQLException {

        for (Appointment appointment : getAppointmentsByCustomerID) {
            LocalDateTime startTimeExistingAppointment = appointment.getStartDateTime();
            LocalDateTime endTimeExistingAppointment = appointment.getEndDateTime();

            if (((startDate.isBefore(startTimeExistingAppointment)) || (startDate.isEqual(startTimeExistingAppointment)))
                    && ((endDate.isAfter(endTimeExistingAppointment)) || (endDate.isEqual(endTimeExistingAppointment)))) {
                setOverlappingAppointment(appointment);
                return true;
            }
        }
        return false;
    }

    /**
     * Validates that the user's selected start time and date are before the end time and date. Also validates that the
     * selected appointment times are in the furture.
     *
     * @return Returns tru if the validation passes. False if the validation fails.
     * @throws Exception The class Exception and its subclasses are a form of Throwable that indicates conditions that a reasonable application might want to catch.
     */
    public boolean validateStartBeforeEndTime() throws Exception {
        try {
            if ((endDatePicker.getValue() != null) && (startDatePicker.getValue() != null) &&
                    (endTimeHrComboBox.getValue() != null) && (startTimeHrComboBox.getValue() != null)) {
                setStartDateTimeSelection();
                setEndDateTimeSelection();
                LocalDateTime startSelection = getStartDate();
                LocalDateTime endSelection = getEndDate();
                if (endSelection.isAfter(startSelection) && startSelection.isAfter(LocalDateTime.now())) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            e.getMessage();
            e.getCause();
            e.printStackTrace();
        }
        return false;
    }

    /**
     * The save procedure
     * The method first checks for empty fields and that the fields meet the length limits set by the database.
     * Then the method validates that the appointment is in the future and that the start
     * time and date is before the end time and date. If those validations pass, the method checks for overlapping appointments.
     * If all validations pass the method converts the times to UTC time using the converter in the utilities package.
     * Finally, it constructs an appointment object and sends the object to AppointmentsImplement.addAppointment to add to the database.
     * If any of the validations fail, the method calls the appropriate error message to display to the user.
     *
     * @param event The user clicks save
     * @throws Exception May throw an SQL Exception or runtime exception
     */
    @FXML
    void onSaveButtonAction(ActionEvent event) throws Exception {
        emptyFieldAlert();
        if ((titleTxtField.getText() != null) && (titleTxtField.getLength() < 50) &&
                (locationTxtField.getText() != null) && (locationTxtField.getLength() < 50) &&
                (appointmentDescriptionTxtField.getText() != null) && (appointmentDescriptionTxtField.getLength() < 50) &&
                (contactComboBox.getValue() != null) && (typeComboBox.getValue() != null) &&
                (startDatePicker.getValue() != null) && (startTimeHrComboBox != null) &&
                (endDatePicker.getValue() != null) && (endTimeHrComboBox != null) &&
                (customerTable.getSelectionModel().getSelectedItem() != null) && (userComboBox.getValue() != null)) {
            validateStartBeforeEndTime();
            getAppointmentsByCustomerID();
            String title = titleTxtField.getText();
            String location = locationTxtField.getText();
            int contactId = contactComboBox.getValue().getContactId();

            String type = typeComboBox.getValue();

            LocalDate startDate = startDatePicker.getValue();
            LocalTime startTime = startTimeHrComboBox.getValue();
            LocalDateTime userStartDateTime = LocalDateTime.of(startDate, startTime);
            LocalDate endDate = endDatePicker.getValue();
            LocalTime endTime = endTimeHrComboBox.getValue();
            LocalDateTime userEndDateTime = LocalDateTime.of(endDate, endTime);

            // Get times converted to UTC
            LocalDateTime startDateTime = TimezoneConversion.convertUserStartTimeToUTC(userStartDateTime);
            LocalDateTime endDateTime = TimezoneConversion.convertUserEndTimeToUTC(userEndDateTime);
            System.out.println("Start and end times converted: " + startDateTime + " " + endDateTime);


            String description = appointmentDescriptionTxtField.getText();
            int customerId = customerTable.getSelectionModel().getSelectedItem().getCustomerId();
            int userId = userComboBox.getValue().getUserId();


            boolean validationResultStartBeforeEnd = validateStartBeforeEndTime(); //returns true if validation passes
            boolean validationResultA = overlapValidationA(startDateTime); // returns false if validation passes
            boolean validationResultB = overlapValidationB(endDateTime); // returns false if validation passes
            boolean validationResultC = overlapValidationC(startDateTime, endDateTime); // returns false if validation passes
            System.out.println("Validation Result Start before end: " + validationResultStartBeforeEnd);
            System.out.println("validationResultA: " + validationResultA);
            System.out.println("validationResultB: " + validationResultB);
            System.out.println("ValidationResultC: " + validationResultC);


            if (validateStartBeforeEndTime() == true) {

                if (validationResultA == false && validationResultB == false && validationResultC == false) {
                    Appointment appointmentToSave = new Appointment(title, description,
                            location, type, startDateTime, endDateTime, customerId, userId, contactId);
                    AppointmentsImplement.addAppointment(appointmentToSave);
                    saveButton.setDisable(true);
                    saveSuccessfulLabel.setVisible(true);
                } else {
                    System.out.println("Conflicting appointment found");
                    overlapAlert();
                    saveErrorLabel.setVisible(true);
                }
            } else {
                dateAndTimeErrorLabel.setVisible(true);
            }
        }
    }


    /**
     * An alert to alert the user that an overlapping appointment was found and directs to user to which appointment with
     * the appointment ID, start date, start time, end date and end time of the overlapping appointment for reference.
     * The additional information is in an effort to make the application easier for the user to use.
     */
    public void overlapAlert() {
        int overlapId = overlappingAppointment.getAppointmentId();
        LocalDateTime overlapStartConversion = convertUTCToUserTime(overlappingAppointment.getStartDateTime());
        LocalDateTime overlapEndConversion = convertUTCToUserTime(overlappingAppointment.getEndDateTime());
        LocalDate overlapStartDate = overlapStartConversion.toLocalDate();
        LocalTime overlapStartTime = overlapStartConversion.toLocalTime();
        LocalDate overlapEndDate = overlapEndConversion.toLocalDate();
        LocalTime overlapEndTime = overlapEndConversion.toLocalTime();
        Alert infoRequiredAlert = new Alert(Alert.AlertType.WARNING);
        infoRequiredAlert.setTitle("An Overlapping Appointment Was Found");
        infoRequiredAlert.setHeaderText("This appointment overlaps with appointment ID: " + overlapId);
        infoRequiredAlert.setContentText("Beginning on: " + overlapStartDate + " at " + overlapStartTime +
                " and ending on: " + overlapEndDate + " at " + overlapEndTime);
        infoRequiredAlert.showAndWait();
    }

    /**
     * An alert that pops up when an empty field is found before saving an appointment.
     * This is the pop-up message itself, the validation is done in emptyFieldAlert().
     */
    public void alert() {
        Alert infoRequiredAlert = new Alert(Alert.AlertType.WARNING);
        infoRequiredAlert.setTitle("Information Required");
        infoRequiredAlert.setHeaderText("Please enter all information.  Thank you! ");
        infoRequiredAlert.setContentText("Please enter missing information");
        infoRequiredAlert.showAndWait();
    }

    /**
     * Checks for empty fields. If any empty field is found the method calls alert() to trigger a pop-up message and
     * outlines the empty field with a red border. the red border is an effort to improve usability of the app from the user perspective.
     */
    public void emptyFieldAlert() {
        String title = titleTxtField.getText();
        String location = locationTxtField.getText();
        String description = appointmentDescriptionTxtField.getText();
        Contact contact = contactComboBox.getValue();
        String type = typeComboBox.getValue();
        LocalTime startTime = startTimeHrComboBox.getValue();
        LocalDate startDate = startDatePicker.getValue();
        LocalTime endtime = endTimeHrComboBox.getValue();
        LocalDate endDate = endDatePicker.getValue();
        Customer selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
        User user = userComboBox.getValue();

        if(title.trim().isEmpty()||location.trim().isEmpty()||description.trim().isEmpty()
                ||contact == null||type == null || startTime == null || startDate == null
                || endtime == null || endDate == null || selectedCustomer == null || user == null){
            alert();
            if (title.trim().isEmpty()) {
                allFieldsRequiredLabel.setVisible(true);
                titleTxtField.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            }

            if (location.trim().isEmpty()) {
                allFieldsRequiredLabel.setVisible(true);
                locationTxtField.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            }
            if (description.trim().isEmpty()) {
                allFieldsRequiredLabel.setVisible(true);
                appointmentDescriptionTxtField.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            }
            if (type == null) {
                allFieldsRequiredLabel.setVisible(true);
                typeComboBox.setStyle("-fx-border-color: #B22222; -fx-focus-color: #B22222;");
            }
            if (startDate == null) {
                allFieldsRequiredLabel.setVisible(true);
                startDatePicker.setStyle("-fx-border-color: #B22222; -fx-focus-color: #B22222;");
            }
            if (startTime == null) {
                allFieldsRequiredLabel.setVisible(true);
                startTimeHrComboBox.setStyle("-fx-border-color: #B22222; -fx-focus-color: #B22222;");
            }
            if (endDate == null) {
                allFieldsRequiredLabel.setVisible(true);
                endDatePicker.setStyle("-fx-border-color: #B22222; -fx-focus-color: #B22222;");
            }
            if (endtime == null) {
                allFieldsRequiredLabel.setVisible(true);
                endTimeHrComboBox.setStyle("-fx-border-color: #B22222; -fx-focus-color: #B22222;");
            }
            if (selectedCustomer == null) {
                allFieldsRequiredLabel.setVisible(true);
                customerTable.setStyle("-fx-border-color: #B22222; -fx-focus-color: #B22222;");
            }
            if (user == null) {
                allFieldsRequiredLabel.setVisible(true);
                userComboBox.setStyle("-fx-border-color: #B22222; -fx-focus-color: #B22222;");
            }
            if (contact == null) {
                allFieldsRequiredLabel.setVisible(true);
                contactComboBox.setStyle("-fx-border-color: #B22222; -fx-focus-color: #B22222;");
            }
        }
    }

    /**
     * Clears the user entries and alerts formatting from the form
     *
     * @param event the user clicks the clear button
     */
    @FXML
    void onActionClearForm(ActionEvent event) {
        titleTxtField.clear();
        titleLengthAlert.setVisible(false);
        locationTxtField.clear();
        locationLengthAlert.setVisible(false);
        contactComboBox.getSelectionModel().clearSelection();
        typeComboBox.getSelectionModel().clearSelection();
        startDatePicker.setValue(null);
        startTimeHrComboBox.getSelectionModel().clearSelection();
        endDatePicker.setValue(null);
        endTimeHrComboBox.getSelectionModel().clearSelection();
        appointmentDescriptionTxtField.clear();
        descriptionLengthAlert.setVisible(false);
        saveErrorLabel.setVisible(false);
        saveSuccessfulLabel.setVisible(false);
        allFieldsRequiredLabel.setVisible(false);
        dateAndTimeErrorLabel.setVisible(false);
        customerTable.getSelectionModel().clearSelection();
        userComboBox.getSelectionModel().clearSelection();
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
            getAllCustomers.clear();
            getAllContactNames();
            getAllUserNames();
            getAllCustomers();
            getAppointmentsByCustomerID();

        } catch (SQLException e) {
            e.getMessage();
            e.getCause();
            e.printStackTrace();
        }

        /**
         * Hide all alert and error / success labels
         */
        allFieldsRequiredLabel.setVisible(false);
        descriptionLengthAlert.setVisible(false);
        locationLengthAlert.setVisible(false);
        titleLengthAlert.setVisible(false);
        saveSuccessfulLabel.setVisible(false);
        saveErrorLabel.setVisible(false);
        dateAndTimeErrorLabel.setVisible(false);

        contactComboBox.setItems(contactNames);
        typeComboBox.setItems(appointmentTypes);
        userComboBox.setItems(userNames);
        TimezoneConversion.convertBusinessStartFromEstToLocalTime();
        TimezoneConversion.convertBusinessEndFromEstToLocalTime();
        populateStartTimeComboBox();
        populateEndTimeComboBox();
        populateCustomerTable(getAllCustomers);

/**
 * Change listener for the end time combo box.
 * Triggers the start time before end time validation in real time after the user has selected an end time.
 */
        endTimeHrComboBox.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus is lost
                if ((endTimeHrComboBox.getValue() != null) && (endDatePicker.getValue() != null)) {
                    Boolean validationResult = null;
                    saveButton.setDisable(false);
                    saveErrorLabel.setVisible(false);
                    //endTimeHrComboBox.setStyle("-fx-border-color: light-grey; -fx-focus-color: white;");
                    try {
                        validationResult = validateStartBeforeEndTime();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (validationResult) {
                        dateAndTimeErrorLabel.setVisible(false);
                    } else {
                        dateAndTimeErrorLabel.setVisible(true);
                    }
                }
            }
        });

        /**
         * Change listener for the end date picker.
         * Triggers the start time before end time validation in real time after the user has selected an end time.
         */
        endDatePicker.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus is lost
                if (endTimeHrComboBox.getValue() != null) { //if the end time is not null
                    endTimeHrComboBox.getValue();
                    saveButton.setDisable(false);
                    saveErrorLabel.setVisible(false);
                    //endDatePicker.setStyle("-fx-border-color: light-grey; -fx-focus-color: white;");

                    Boolean validationResult = null;
                    try {
                        validationResult = validateStartBeforeEndTime();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (validationResult) {
                        dateAndTimeErrorLabel.setVisible(false);
                    } else {
                        dateAndTimeErrorLabel.setVisible(true);
                    }
                }
            }
        });

//Change listeners to validate text fields and warn the user in real time if input is over the allowed length
        /**
         * Change listener to validate the length of text entries in real time.
         * Triggers an error label if the text entry is longer than the length allowed in the database
         */
        titleTxtField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                saveSuccessfulLabel.setVisible(false);
                saveErrorLabel.setVisible(false);
                saveButton.setDisable(false);
                allFieldsRequiredLabel.setVisible(false);
                //titleTxtField.setStyle("-fx-border-color: light-grey; -fx-focus-color: white;");
                if (titleTxtField.getLength() > 50) {
                    titleLengthAlert.setVisible(true);
                } else if (titleTxtField.getLength() < 50) {
                    titleLengthAlert.setVisible(false);
                }
            }
        });

        /**
         * Change listener to validate the length of text entries in real time.
         * Triggers an error label if the text entry is longer than the length allowed in the database
         */
        locationTxtField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                saveSuccessfulLabel.setVisible(false);
                saveErrorLabel.setVisible(false);
                saveButton.setDisable(false);
                allFieldsRequiredLabel.setVisible(false);
                //locationTxtField.setStyle("-fx-border-color: light-grey; -fx-focus-color: white;");
                if (locationTxtField.getLength() > 50) {
                    locationLengthAlert.setVisible(true);
                } else if (locationTxtField.getLength() < 50) {
                    locationLengthAlert.setVisible(false);
                }
            }
        });

        /**
         * Change listener to validate the length of text entries in real time.
         * Triggers an error label if the text entry is longer than the length allowed in the database
         */
        appointmentDescriptionTxtField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                saveSuccessfulLabel.setVisible(false);
                saveErrorLabel.setVisible(false);
                saveButton.setDisable(false);
                allFieldsRequiredLabel.setVisible(false);
                //appointmentDescriptionTxtField.setStyle("-fx-border-color: light-grey; -fx-focus-color: white;");
                if (appointmentDescriptionTxtField.getLength() > 50) {
                    descriptionLengthAlert.setVisible(true);
                } else if (appointmentDescriptionTxtField.getLength() < 50) {
                    descriptionLengthAlert.setVisible(false);
                }
            }
        });

        /**
         * Listener for the type combo box.
         * Resets the error labels if the user enters a new value
         */
        typeComboBox.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                saveSuccessfulLabel.setVisible(false);
                saveErrorLabel.setVisible(false);
                saveButton.setDisable(false);
                allFieldsRequiredLabel.setVisible(false);
                //typeComboBox.setStyle("-fx-border-color: light-grey; -fx-focus-color: white;");
            }
        });

        /**
         * Listener for the user combo box.
         * Resets the error labels if the user enters a new value
         */
        userComboBox.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                saveSuccessfulLabel.setVisible(false);
                saveErrorLabel.setVisible(false);
                saveButton.setDisable(false);
                allFieldsRequiredLabel.setVisible(false);
                //userComboBox.setStyle("-fx-border-color: light-grey; -fx-focus-color: white;");
            }
        });

        /**
         * Listener for the contact combo box.
         * Resets the error labels if the user enters a new value
         */
        contactComboBox.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                saveSuccessfulLabel.setVisible(false);
                saveErrorLabel.setVisible(false);
                saveButton.setDisable(false);
                allFieldsRequiredLabel.setVisible(false);
                //contactComboBox.setStyle("-fx-border-color: light-grey; -fx-focus-color: white;");
            }
        });

        /**
         * Listener for the customer table.
         * Resets the error labels if the user enters a new value
         */
        customerTable.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                saveSuccessfulLabel.setVisible(false);
                saveErrorLabel.setVisible(false);
                saveButton.setDisable(false);
                allFieldsRequiredLabel.setVisible(false);
                //customerTable.setStyle("-fx-border-color: light-grey; -fx-focus-color: white;");
            }
        });

        /**
         * Listener for the start date picker.
         * Resets the error labels if the user enters a new value
         */
        startDatePicker.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                saveSuccessfulLabel.setVisible(false);
                saveErrorLabel.setVisible(false);
                saveButton.setDisable(false);
                allFieldsRequiredLabel.setVisible(false);
                //startDatePicker.setStyle("-fx-border-color: light-grey; -fx-focus-color: white;");
            }
        });

        /**
         * Listener for the start time combo box.
         * Resets the error labels if the user enters a new value
         */
        startTimeHrComboBox.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                saveSuccessfulLabel.setVisible(false);
                saveErrorLabel.setVisible(false);
                saveButton.setDisable(false);
                allFieldsRequiredLabel.setVisible(false);
                //startTimeHrComboBox.setStyle("-fx-border-color: light-grey; -fx-focus-color: white;");
            }
        });
    }
}
