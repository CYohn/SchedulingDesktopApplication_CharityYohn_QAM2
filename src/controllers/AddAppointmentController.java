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

public class AddAppointmentController extends TimezoneConversion implements Initializable {


    private static LocalDateTime startDate;
    private static LocalDateTime endDate;
    Customer selectedCustomer;
    private static int selectedCustomerID;

    private ObservableList<Contact> contactNames = ContactsImplement.contactNames;
    private ObservableList<User> userNames = UsersImplement.userNames;
    private ObservableList<String> appointmentTypes = FXCollections.observableArrayList
            ("Planning Session", "Progress Update", "De-Briefing");
    private ObservableList<Appointment> getAppointmentsByCustomerID = AppointmentsImplement.AppointmentsByCustomerID;


    @FXML
    private GridPane applicationFormLeft;


    //Table and columns
    @FXML
    private TableView<Customer> customerTable;

    @FXML
    private TableColumn<Customer, Integer> customerIdColumn;

    @FXML
    private TableColumn<Customer, String> customerNameColumn;

    @FXML
    private TableColumn<Customer, String> customerPhoneColumn;

    //Text Fields
    @FXML
    private TextField titleTxtField;
    @FXML
    private TextArea appointmentDescriptionTxtField;

    @FXML
    private TextField locationTxtField;

   //Date pickers
    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    //Form ComboBoxes
    @FXML
    private ComboBox<Contact> contactComboBox;

    @FXML
    private ComboBox<LocalTime> endTimeHrComboBox;

    @FXML
    private ComboBox<LocalTime> endTimeMinComboBox;

    @FXML
    private ComboBox<LocalTime> startTimeHrComboBox;

    @FXML
    private ComboBox<LocalTime> startTimeMinComboBox;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private ComboBox<User> userComboBox;

   //Form Buttons
   @FXML
   private Button saveButton;

    @FXML
    private Button clearButton;

    //Active Form Labels
    @FXML
    private Label dateAndTimeErrorLabel;

    //Setters and getters for the start and end DateTime selected in the comboBox

    public static LocalDateTime getStartDate() {
        return startDate;
    }

    public static void setStartDate(LocalDateTime startDate) {
        AddAppointmentController.startDate = startDate;
    }

    public static LocalDateTime getEndDate() {
        return endDate;
    }

    public static void setEndDate(LocalDateTime endDate) {
        AddAppointmentController.endDate = endDate;
    }

    public static int getSelectedCustomerID() {
        return selectedCustomerID;
    }


    public static void setSelectedCustomerID(int selectedCustomerID) {
        AddAppointmentController.selectedCustomerID = selectedCustomerID;
    }

    public void populateCustomerTable(ObservableList<Customer> getAllCustomers) {

        customerTable.setItems(getAllCustomers);

        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
    }

    @FXML
    Customer onTableClickGetSelectedCustomer(MouseEvent event) {
        if(!customerTable.getSelectionModel().isEmpty()){
            selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
            setSelectedCustomerID(selectedCustomer.getCustomerId());
        }
        return selectedCustomer;
    }

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

    public LocalDateTime setStartDateTimeSelection() {
        LocalDateTime startDateTimeSelection = LocalDateTime.of(startDatePicker.getValue(), startTimeHrComboBox.getValue());
        setStartDate(startDateTimeSelection);
        return startDateTimeSelection;
    }

    public LocalDateTime setEndDateTimeSelection() {
        LocalDateTime endDateTimeSelection = LocalDateTime.of(endDatePicker.getValue(), endTimeHrComboBox.getValue());
        setEndDate(endDateTimeSelection);
        return endDateTimeSelection;
    }

    public boolean validateStartBeforeEndTime() throws Exception {
        try {
            setStartDateTimeSelection();
            setEndDateTimeSelection();
            LocalDateTime startSelection = getStartDate();
            LocalDateTime endSelection = getEndDate();
            if (endSelection.isAfter(startSelection) && startSelection.isAfter(LocalDateTime.now())) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.getMessage();
            e.getCause();
            e.printStackTrace();
        }
        return false;
    }


    public boolean overlapValidationA() throws SQLException {

        LocalDateTime startTimeOfNewAppointment = getStartDate();
        getAppointmentsByCustomerID();

        for (Appointment appointment : getAppointmentsByCustomerID) {
            LocalDateTime startTimeExistingAppointment = appointment.getStartDateTime();
            LocalDateTime endTimeExistingAppointment = appointment.getEndDateTime();
            if (((startTimeOfNewAppointment.isAfter(startTimeExistingAppointment)) || (startTimeExistingAppointment.isEqual(startTimeExistingAppointment)))
                    && (startTimeOfNewAppointment).isBefore(endTimeExistingAppointment)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public boolean overlapValidationB() throws SQLException {

        LocalDateTime startTimeOfNewAppointment = getStartDate();
        LocalDateTime endTimeOfNewAppointment = getEndDate();
        getAppointmentsByCustomerID();

        for (Appointment appointment : getAppointmentsByCustomerID) {
            LocalDateTime startTimeExistingAppointment = appointment.getStartDateTime();
            LocalDateTime endTimeExistingAppointment = appointment.getEndDateTime();
            if (((endTimeOfNewAppointment.isBefore(endTimeExistingAppointment)) || (endTimeOfNewAppointment.isEqual(endTimeExistingAppointment)))
                    && (startTimeOfNewAppointment).isAfter(startTimeExistingAppointment)) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    public boolean overlapValidationC() throws SQLException {

        LocalDateTime startTimeOfNewAppointment = getStartDate();
        LocalDateTime endTimeOfNewAppointment = getEndDate();
        getAppointmentsByCustomerID();

        for (Appointment appointment : getAppointmentsByCustomerID) {
            LocalDateTime startTimeExistingAppointment = appointment.getStartDateTime();
            LocalDateTime endTimeExistingAppointment = appointment.getEndDateTime();
            if (((startTimeOfNewAppointment.isBefore(startTimeExistingAppointment)) || (startTimeOfNewAppointment.isEqual(startTimeExistingAppointment)))
                    && ((endTimeOfNewAppointment.isAfter(endTimeExistingAppointment)) || (endTimeOfNewAppointment.isEqual(endTimeExistingAppointment)))) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @FXML
    void onSaveButtonAction(ActionEvent event) throws Exception {
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


        String description = appointmentDescriptionTxtField.getText();
        int customerId = getSelectedCustomerID();
        int userId = userComboBox.getValue().getUserId();

        setStartDateTimeSelection();
        setEndDateTimeSelection();

        boolean validationResultStartBeforeEnd= validateStartBeforeEndTime(); //returns true if validation passes
        boolean validationResultA = overlapValidationA(); // returns false if validation passes
        boolean validationResultB = overlapValidationB(); // returns false if validation passes
        boolean validationResultC = overlapValidationC(); // returns false if validation passes
        System.out.println("validationResultA: " + validationResultA);
        System.out.println("validationResultB: " + validationResultB);
        System.out.println("ValidationResultC: " + validationResultC);

        if (validateStartBeforeEndTime() == true && validationResultA == false && validationResultB == false && validationResultC == false) {
            Appointment appointmentToSave = new Appointment(title, description, location, type, startDateTime, endDateTime, customerId, userId, contactId);
            AppointmentsImplement.addAppointment(appointmentToSave);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getAllContactNames();
            getAllUserNames();
            getAllCustomers();
            getAppointmentsByCustomerID();
        } catch (SQLException e) {
            e.getMessage();
            e.getCause();
            e.printStackTrace();
        }

        dateAndTimeErrorLabel.setVisible(false);
        contactComboBox.setItems(contactNames.sorted());
        typeComboBox.setItems(appointmentTypes);
        userComboBox.setItems(userNames);
        TimezoneConversion.convertBusinessStartFromEstToLocalTime();
        TimezoneConversion.convertBusinessEndFromEstToLocalTime();
        populateStartTimeComboBox();
        populateEndTimeComboBox();
        populateCustomerTable(getAllCustomers);

        endTimeHrComboBox.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus is lost
                Boolean validationResult = null;
                try {
                    validationResult = validateStartBeforeEndTime();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(validationResult) {dateAndTimeErrorLabel.setVisible(false);}
                else{dateAndTimeErrorLabel.setVisible(true);}
            }
        });

        endDatePicker.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus is lost
                endTimeHrComboBox.getValue();
                if(!endTimeHrComboBox.getValue().equals(null)) { //if the end time is not null
                    Boolean validationResult = null;
                    try {
                        validationResult = validateStartBeforeEndTime();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (validationResult) { dateAndTimeErrorLabel.setVisible(false);
                    } else { dateAndTimeErrorLabel.setVisible(true); }
                }
            }
        });




    }
}
