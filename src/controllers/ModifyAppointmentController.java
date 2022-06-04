package controllers;

import Objects.Appointment;
import Objects.Contact;
import Objects.Customer;
import Objects.User;
import implementationsDao.AppointmentsImplement;
import implementationsDao.ContactsImplement;
import implementationsDao.CustomersImplement;
import implementationsDao.UsersImplement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import utilities.TimezoneConversion;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;


import static implementationsDao.AppointmentsImplement.getAllAppointments;
import static implementationsDao.AppointmentsImplement.getAppointmentsByCustomerID;
import static implementationsDao.CustomersImplement.getAllCustomers;
import static java.lang.String.valueOf;
import static utilities.TimezoneConversion.convertUTCToUserTime;


/**
 * A class which is a controller for the Modify Appointments screen.
 */
public class ModifyAppointmentController implements Initializable {

    /**
     * the selected start date and time.
     */
    private static LocalDateTime startDate;
    /**
     * The selected end date and time.
     */
    private static LocalDateTime endDate;
    /**
     * An observableList which holds all appointments.
     */
    ObservableList<Appointment> allAppointments = getAllAppointments;
    /**
     * An observableList which holds all appointments. The appointment start and end dates and times have been separated
     * from LocalDateTime to LocalDate and LocalTime, respectively, to improve the diplay's readability.
     * Additionally, the appointment times are converted from the UTC time stored in the database to the user's local time.
     * This list is then used to populate the appointments table.
     */
    ObservableList<Appointment> appointmentsWithConvertedTimes = FXCollections.observableArrayList();
    /**
     * An observableList of all customers. Used to poulate the customers table.
     */
    ObservableList<Customer> allCustomers = CustomersImplement.getAllCustomers;
    /**
     * A variable to hold an object representing an overlapping appointment.
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
     * An observableList of appointments filtered by customer ID. This list is used for validation purposes.
     */
    private ObservableList<Appointment> getAppointmentsByCustomerID = AppointmentsImplement.appointmentsByCustomerID;
    /**
     * The selected contact.
     */
    private String selectedContactName;
    /**
     * The selected customer.
     */
    private String selectedCustomerName;
    /**
     * The selected user.
     */
    private String selectedUserName;
    /**
     * The appointments table.
     */
    @FXML
    private TableView<Appointment> appointmentTable;

    /**
     * Sets the start date and time
     *
     * @param startDate the start date and time
     */
    public static void setStartDate(LocalDateTime startDate) {
        ModifyAppointmentController.startDate = startDate;
    }

    /**
     * Gets the end date and time
     *
     * @return
     */
    public static LocalDateTime getEndDate() {
        return endDate;
    }

    /**
     * Sets the end date and time
     *
     * @param endDate sets the end date and time
     */
    public static void setEndDate(LocalDateTime endDate) {
        ModifyAppointmentController.endDate = endDate;
    }

    /**
     * The contact column of the appointments table
     */
    @FXML
    private TableColumn<Appointment, Integer> aptContactColumn;
    /**
     * The description column of the appointments table.
     */
    @FXML
    private TableColumn<Appointment, String> aptDescriptioncolumn;
    /**
     * The end date column of the appointments table.
     */
    @FXML
    private TableColumn<Appointment, LocalDate> aptEndDateColumn;
    /**
     * The end time column of the appointments table.
     */
    @FXML
    private TableColumn<Appointment, LocalTime> aptEndTimeColumn;
    /**
     * The ID column of the appointments table.
     */
    @FXML
    private TableColumn<Appointment, Integer> aptIdColumn;
    /**
     * the location column of the appointments table.
     */
    @FXML
    private TableColumn<Appointment, String> aptLocationColumn;
    /**
     * The start date column of the appointments table.
     */
    @FXML
    private TableColumn<Appointment, LocalDate> aptStartDateColumn;
    /**
     * The start time column of the appointments table.
     */
    @FXML
    private TableColumn<Appointment, LocalTime> aptStartTimeColumn;
    /**
     * The title column of the appointments table.
     */
    @FXML
    private TableColumn<Appointment, String> aptTitleColum;
    /**
     * The type column of the appoiontments table.
     */
    @FXML
    private TableColumn<Appointment, String> aptTypeColumn;
    /**
     * The customer ID column of the appointments table.
     */
    @FXML
    private TableColumn<Appointment, Integer> custIdColumn;
    /**
     * The user ID column of the appointments table.
     */
    @FXML
    private TableColumn<Appointment, Integer> userIdColumn;
    /**
     * A text field to enter the appointment location.
     */
    @FXML
    private TextField locationTxtField;
    /**
     * A text field to enter the title of the appointment.
     */
    @FXML
    private TextField titleTxtField;
    /**
     * A text area to enter the appointment description.
     */
    @FXML
    private TextArea appointmentDescriptionTxtField;
    /**
     * The date picker for the appointment start date.
     */
    @FXML
    private DatePicker startDatePicker;
    /**
     * The date picker for the appointment end date.
     */
    @FXML
    private DatePicker endDatePicker;
    /**
     * A combo box to select the start time.
     */
    @FXML
    private ComboBox<LocalTime> startTimeHrComboBox;
    /**
     * A combo box to select the end time.
     */
    @FXML
    private ComboBox<LocalTime> endTimeHrComboBox;
    /**
     * A combo box to select the contact.
     */
    @FXML
    private ComboBox<Contact> contactComboBox;
    /**
     * A combo box to select the type.
     */
    @FXML
    private ComboBox<String> typeComboBox;
    /**
     * A combo box to select the associated user.
     */
    @FXML
    private ComboBox<User> userComboBox;
    /**
     * A combo box to select the associated customer.
     */
    @FXML
    private ComboBox<Customer> customerComboBox;
    /**
     * The delete button.
     */
    @FXML
    private Button deleteButton;
    /**
     * The clear button.
     */
    @FXML
    private Button clearButton;
    /**
     * The save button
     */
    @FXML
    private Button saveButton;
    /**
     * Label which appears when an error has occurred concerning the date and times. The date and times go through two
     * real time validations. The first validation checks that the start date and time is before the end date and time. The
     * second validation checks that the appointment is in the future. If either of these validations fail the form will
     * show this error message.
     */
    @FXML
    private Label dateAndTimeErrorLabel;
    /**
     * An error label which shows if the user's description input exceeds the length allowed by the database.
     */
    @FXML
    private Label titleLengthAlert;
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
    private Label saveSuccessfulLabel;
    /**
     * An error label which shows if the user's description input exceeds the length allowed by the database.
     */
    @FXML
    private Label saveErrorLabel;
    /**
     * An error label which shows if the user's description input exceeds the length allowed by the database.
     */
    @FXML
    private Label locationLengthAlert;
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
     * A field to show the appointment ID. This field is not modifieable by the user and is for information purposes only.
     */
    @FXML
    private Label aptNumberLabel;
    /**
     * A label to inform the user that the appointment was successfully deleted.
     */
    @FXML
    private Label deleteSuccessfulLabel;
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
    private Label dateAndTimeErrorLabel2;

    /**
     * Gets the start date and time
     *
     * @return start date and time
     */
    public static LocalDateTime getStartDate() {
        return startDate;
    }

    /**
     * Sets the selected customer ID
     *
     * @param selectedCustomerID
     */
    public static void setSelectedCustomerID(int selectedCustomerID) {
    }

    /**
     * Sets the selected contact name
     *
     * @param selectedContactName the name of the selected contact
     */
    public void setSelectedContactName(String selectedContactName) {
        this.selectedContactName = selectedContactName;
    }

    /**
     * Sets the selected customer name
     *
     * @param selectedCustomerName
     */
    public void setSelectedCustomerName(String selectedCustomerName) {
        this.selectedCustomerName = selectedCustomerName;
    }

    /**
     * Sets the selected username
     *
     * @param selectedUserName
     */
    public void setSelectedUserName(String selectedUserName) {
        this.selectedUserName = selectedUserName;
    }

    /**
     * Sets and object with the overlapping appointment
     *
     * @param overlappingAppointment The overlapping appointment object
     */
    public void setOverlappingAppointment(Appointment overlappingAppointment) {
        this.overlappingAppointment = overlappingAppointment;
    }

    public ModifyAppointmentController() throws SQLException {
    }

    /**
     * Maches the selected contact with the contacts in the list to set the selected contact name and return the correct contact in the list.
     *
     * @param contactId The ID number of the selected contact
     * @return The contact to whom the ID belongs
     */
    public Contact returnSelectedContactFromID(int contactId) {
        Contact selectedContact = null;
        for (Contact contact : contactNames) {
            if (contact.getContactId() == contactId) {
                String contactName = contact.getContactName();
                setSelectedContactName(contactName);
                selectedContact = contact;
                return selectedContact;
            }
        }
        return null;
    }

    /**
     * Takes the ID number of the selected customer and returns the correct custome object.
     *
     * @param customerId The ID of the selected customer
     * @return customer object selected
     */
    public Customer returnSelectedCustomerFromID(int customerId) {
        Customer selectedCustomer = null;
        for (Customer customer : allCustomers) {
            if (customer.getCustomerId() == customerId) {
                String customerName = customer.getCustomerName();
                setSelectedCustomerName(customerName);
                selectedCustomer = customer;
                return selectedCustomer;
            }
        }
        return null;
    }

    /**
     * Returns the selected user object based on the user ID selected
     *
     * @param userId The ID of the user
     * @return User object
     */
    public User returnSelectedUserFromID(int userId) {
        User selectedUser = null;
        for (User user : userNames) {
            if (user.getUserId() == userId) {
                String userName = user.getUserName();
                setSelectedCustomerName(userName);
                selectedUser = user;
                return selectedUser;
            }
        }
        return null;
    }

    /**
     * Cell factory to populate the Appointment table.
     * The method gets a fresh list of appointments and converts the times to the user timezone and splits the times
     * into individual date / time variables. Then the method uses these new times to populate the appointments table.
     *
     * @param allAppointments
     * @throws SQLException
     */
    public void populateAptTable(ObservableList<Appointment> allAppointments) throws SQLException {
        appointmentsWithConvertedTimes.clear();
        allAppointments.clear();
        getAllAppointments();
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

            appointmentsWithConvertedTimes.add(convertedTimesAppointment);
        }

        appointmentTable.setItems(appointmentsWithConvertedTimes);

        aptContactColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        aptDescriptioncolumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        aptEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        aptEndTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        aptIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        aptLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        aptStartDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        aptStartTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        aptTitleColum.setCellValueFactory(new PropertyValueFactory<>("title"));
        aptTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        custIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
    }


    /**
     * Populates the start time combo box.
     * The method calls the time conversion in utilities to convert times from Eastern Time to the users local time.
     * Then the method populates a combo box, only allowing the user to select times within business hours while showing the times
     * in the correct timezone for the user.
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
     * Populates the end time combo box.
     * The method calls the time conversion in utilities to convert times from Eastern Time to the users local time.
     * Then the method populates a combo box, only allowing the user to select times within business hours while showing the times
     * in the correct timezone for the user.
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
     * Populates the form with information from the selection in the table
     *
     * @param event The user clicks on the table to select an appointment
     */
    @FXML
    void onClickPopulateApptElements(MouseEvent event) {
        if (!appointmentTable.getSelectionModel().isEmpty()) {
            deleteButton.setDisable(false);
            Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();

            //Get table values
            Integer aptId = selectedAppointment.getAppointmentId();
            String title = selectedAppointment.getTitle();
            String description = selectedAppointment.getDescription();
            String location = selectedAppointment.getLocation();
            String type = selectedAppointment.getType();
            LocalDate startDate = selectedAppointment.getStartDate();
            LocalTime startTime = selectedAppointment.getStartTime();
            LocalDate endDate = selectedAppointment.getEndDate();
            LocalTime endTime = selectedAppointment.getEndTime();

            //Convert IDs to String
            Integer selectedCustId = selectedAppointment.getCustomerId();
            Integer userId = selectedAppointment.getUserId();
            Integer contactId = selectedAppointment.getContactId();
            Contact selectedContact = returnSelectedContactFromID(contactId);
            User selectedUser = returnSelectedUserFromID(userId);
            Customer selectedCustomer = returnSelectedCustomerFromID(selectedCustId);

            //Set the form values to the selectedCustomer values
            aptNumberLabel.setText(aptId.toString());
            titleTxtField.setText(title);
            appointmentDescriptionTxtField.setText(valueOf(description));
            locationTxtField.setText(location);
            contactComboBox.setValue(selectedContact);
            typeComboBox.setValue(type);
            userComboBox.setValue(selectedUser);
            startDatePicker.setValue(startDate);
            startTimeHrComboBox.setValue(startTime);
            endDatePicker.setValue(endDate);
            endTimeHrComboBox.setValue(endTime);
            customerComboBox.setValue(selectedCustomer);
        }
    }

    /**
     * Clears the form values
     *
     * @param event When the user clicks the clear button
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
        dateAndTimeErrorLabel2.setVisible(false);
        appointmentTable.getSelectionModel().clearSelection();
        userComboBox.getSelectionModel().clearSelection();
        customerComboBox.getSelectionModel().clearSelection();
        deleteSuccessfulLabel.setVisible(false);
        clearBoxFormatting();
    }

    /**
     * Validates that the selected start time and date are before the end time and date and that the appointment is in th future
     *
     * @return true is the validation passes and false if the validation does not pass
     * @throws Exception The class Exception and its subclasses are a form of Throwable that indicates conditions
     * that a reasonable application might want to catch.
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
        LocalDateTime endDateTimeSelection = LocalDateTime.of(endDatePicker.getValue(), endTimeHrComboBox.getValue());
        setEndDate(endDateTimeSelection);
        return endDateTimeSelection;
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
        Customer selectedCustomer = customerComboBox.getValue();
        User user = userComboBox.getValue();

        if (title.trim().isEmpty()||location.trim().isEmpty()||description.trim().isEmpty()
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
                customerComboBox.setStyle("-fx-border-color: #B22222; -fx-focus-color: #B22222;");
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
     * Resets the formatting and error labels sho to the user on the form
     */
    public void clearBoxFormatting() {

        allFieldsRequiredLabel.setVisible(false);
        //titleTxtField.setStyle("-fx-text-box-border: light-grey; -fx-focus-color: white;");

        allFieldsRequiredLabel.setVisible(false);
        //locationTxtField.setStyle("-fx-text-box-border: light-grey; -fx-focus-color: white;");


        allFieldsRequiredLabel.setVisible(false);
        //appointmentDescriptionTxtField.setStyle("-fx-text-box-border: light-grey; -fx-focus-color: white;");


        allFieldsRequiredLabel.setVisible(false);
        //typeComboBox.setStyle("-fx-border-color: light-grey; -fx-focus-color: white;");


        allFieldsRequiredLabel.setVisible(false);
        //startDatePicker.setStyle("-fx-border-color: light-grey; -fx-focus-color: white;");


        allFieldsRequiredLabel.setVisible(false);
        //startTimeHrComboBox.setStyle("-fx-border-color: light-grey; -fx-focus-color: white;");


        allFieldsRequiredLabel.setVisible(false);
        //endDatePicker.setStyle("-fx-border-color: light-grey; -fx-focus-color: white;");


        allFieldsRequiredLabel.setVisible(false);
        //endTimeHrComboBox.setStyle("-fx-border-color: light-grey; -fx-focus-color: white;");

        allFieldsRequiredLabel.setVisible(false);
        //customerComboBox.setStyle("-fx-border-color: light-grey; -fx-focus-color: white;");

        allFieldsRequiredLabel.setVisible(false);
        //userComboBox.setStyle("-fx-border-color: light-grey; -fx-focus-color: white;");

        allFieldsRequiredLabel.setVisible(false);
        //contactComboBox.setStyle("-fx-border-color: light-grey; -fx-focus-color: white;");
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
     * The alert show to the user when an overlapping appointment is found.
     * The alert converts the times show to the user's local time and advises what appointment overlaps with the
     * time and date the user selected
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


        if ((!titleTxtField.getText().isEmpty()) && (titleTxtField.getLength() < 50) &&
                (!locationTxtField.getText().isEmpty()) && (locationTxtField.getLength() < 50) &&
                (!appointmentDescriptionTxtField.getText().isEmpty()) && (appointmentDescriptionTxtField.getLength() < 50) &&
                (contactComboBox.getValue() != null) && (typeComboBox.getValue() != null) &&
                (startDatePicker.getValue() != null) && (startTimeHrComboBox != null) &&
                (endDatePicker.getValue() != null) && (endTimeHrComboBox != null) &&
                (customerComboBox.getSelectionModel().getSelectedItem() != null) && (userComboBox.getValue() != null)) {
            getAppointmentsByCustomerID();
            validateStartBeforeEndTime();
            if(validateStartBeforeEndTime() == true) {


                int appointmentId = appointmentTable.getSelectionModel().getSelectedItem().getAppointmentId();
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
                System.out.println("Start and end times converted: " + startDateTime +" " + endDateTime);


                String description = appointmentDescriptionTxtField.getText();
                int customerId = customerComboBox.getSelectionModel().getSelectedItem().getCustomerId();
                int userId = userComboBox.getValue().getUserId();


                boolean validationResultStartBeforeEnd = validateStartBeforeEndTime(); //returns true if validation passes
                boolean validationResultA = overlapValidationA(startDateTime); // returns false if validation passes
                boolean validationResultB = overlapValidationB(endDateTime); // returns false if validation passes
                boolean validationResultC = overlapValidationC(startDateTime, endDateTime); // returns false if validation passes
                System.out.println("Validation Result Start before end: " + validationResultStartBeforeEnd);
                System.out.println("validationResultA: " + validationResultA);
                System.out.println("validationResultB: " + validationResultB);
                System.out.println("ValidationResultC: " + validationResultC);


                if (validationResultA == false && validationResultB == false && validationResultC == false) {
                    Appointment appointmentToSave = new Appointment(appointmentId, title, description,
                            location, type, startDateTime, endDateTime, customerId, userId, contactId);
                    AppointmentsImplement.updateAppointment(appointmentToSave);
                    saveButton.setDisable(true);
                    saveSuccessfulLabel.setVisible(true);
                    appointmentTable.getItems().clear();
                    getAllAppointments();
                    populateAptTable(allAppointments);
                } else {
                    System.out.println("Conflicting appointment found");
                    overlapAlert();
                    saveErrorLabel.setVisible(true);
                }
            } else{dateAndTimeErrorLabel.setVisible(true);
                dateAndTimeErrorLabel2.setVisible(true);
            }
        }
    }

    /**
     * An alert shown to the user to confirm the intention of deleting the selected appointment
     *
     * @throws SQLException
     */
    public void deleteAlert() throws SQLException {
        if ((appointmentTable.getSelectionModel().getSelectedItem() != null) && (titleTxtField.getText() != null) && (titleTxtField.getLength() < 50) &&
                (locationTxtField.getText() != null) && (locationTxtField.getLength() < 50) &&
                (appointmentDescriptionTxtField.getText() != null) && (appointmentDescriptionTxtField.getLength() < 50) &&
                (contactComboBox.getValue() != null) && (typeComboBox.getValue() != null) &&
                (startDatePicker.getValue() != null) && (startTimeHrComboBox != null) &&
                (endDatePicker.getValue() != null) && (endTimeHrComboBox != null) && (userComboBox.getValue() != null)) {

            int appointmentId = appointmentTable.getSelectionModel().getSelectedItem().getAppointmentId();
            LocalDate startDate = startDatePicker.getValue();
            LocalTime startTime = startTimeHrComboBox.getValue();
            String customerName = customerComboBox.getSelectionModel().getSelectedItem().getCustomerName();
            Alert deleteAlert = new Alert(Alert.AlertType.WARNING);
            deleteAlert.setTitle("This will permanently delete appointment " + appointmentId);
            deleteAlert.setHeaderText("Are you sur you want to delete appointment " + appointmentId + " ?");
            deleteAlert.setContentText("Appointment for Customer: " + customerName +
                    " Starting on " + startDate + " at " + startTime);
            //deleteAlert.showAndWait();

            ButtonType yesButton = new ButtonType("Delete Appointment");
            ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

            deleteAlert.getButtonTypes().setAll(yesButton, cancelButton);

            Optional<ButtonType> result = deleteAlert.showAndWait();
            if (result.get() == yesButton) {
                AppointmentsImplement.deleteAppointment(appointmentId);
                deleteSuccessfulLabel.setVisible(true);
                deleteButton.setDisable(true);
                deleteAlert.close();

            } else if (result.get() == cancelButton) {
                deleteAlert.close();
            }
        }
    }

    /**
     * The listener for the delete button
     *
     * @param event The user clicks the delete button
     * @throws SQLException An exception that provides information on a database access error or other errors
     */
    @FXML
    void onActionDeleteAppointment(ActionEvent event) throws SQLException {
        deleteAlert();
        appointmentTable.getItems().clear();
        getAllAppointments();
        populateAptTable(allAppointments);
    }


    /**
     * Initializes the page
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Hide active labels
        dateAndTimeErrorLabel.setVisible(false);
        dateAndTimeErrorLabel2.setVisible(false);
        titleLengthAlert.setVisible(false);
        saveSuccessfulLabel.setVisible(false);
        saveErrorLabel.setVisible(false);
        locationLengthAlert.setVisible(false);
        allFieldsRequiredLabel.setVisible(false);
        descriptionLengthAlert.setVisible(false);
        deleteSuccessfulLabel.setVisible(false);

        //Populate the comboBoxes
        contactComboBox.setItems(contactNames);
        typeComboBox.setItems(appointmentTypes);
        userComboBox.setItems(userNames);
        customerComboBox.setItems(allCustomers);

        TimezoneConversion.convertBusinessStartFromEstToLocalTime();
        TimezoneConversion.convertBusinessEndFromEstToLocalTime();
        populateStartTimeComboBox();
        populateEndTimeComboBox();

        try {
            getAllAppointments.clear();
            allAppointments.clear();
            getAllAppointments();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            populateAptTable(allAppointments);
        } catch (SQLException e) {
            e.printStackTrace();
        }

/**
 * Change listener for the end time combo box.
 * Triggers the start time before end time validation in real time after the user has selected an end time.
 */
        endTimeHrComboBox.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus is lost
                Boolean validationResult = null;
                saveButton.setDisable(false);
                saveErrorLabel.setVisible(false);
                //endTimeHrComboBox.setStyle("-fx-border-color: light-grey; -fx-focus-color: white;");
                try {
                    validationResult = validateStartBeforeEndTime();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(validationResult) {
                    dateAndTimeErrorLabel.setVisible(false);
                    dateAndTimeErrorLabel2.setVisible(false);
                } else {
                    dateAndTimeErrorLabel.setVisible(true);
                    dateAndTimeErrorLabel2.setVisible(true);
                }
            }
        });

        /**
         * Change listener for the end date picker.
         * Triggers the start time before end time validation in real time after the user has selected an end time.
         */
        endDatePicker.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus is lost
                endTimeHrComboBox.getValue();
                saveButton.setDisable(false);
                saveErrorLabel.setVisible(false);
                //endDatePicker.setStyle("-fx-border-color: light-grey; -fx-focus-color: white;");
                if (!endTimeHrComboBox.getValue().equals(null)) { //if the end time is not null
                    Boolean validationResult = null;
                    try {
                        validationResult = validateStartBeforeEndTime();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (validationResult) {
                        dateAndTimeErrorLabel.setVisible(false);
                        dateAndTimeErrorLabel2.setVisible(false);

                    } else {
                        dateAndTimeErrorLabel.setVisible(true);
                        dateAndTimeErrorLabel2.setVisible(true);
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
         * Change listener for the appointment table. Resets the error labels when the user selects a value.
         */
        appointmentTable.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                saveSuccessfulLabel.setVisible(false);
                saveErrorLabel.setVisible(false);
                saveButton.setDisable(false);
                allFieldsRequiredLabel.setVisible(false);
                //appointmentTable.setStyle("-fx-border-color: light-grey; -fx-focus-color: white;");
                clearBoxFormatting();
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
