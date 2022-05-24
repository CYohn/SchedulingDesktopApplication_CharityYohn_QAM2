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

import static implementationsDao.AppointmentsImplement.AppointmentsByCustomerID;
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
    Appointment overlappingAppointment;

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

    @FXML
    private Label allFieldsRequiredLabel;

    @FXML
    private Label descriptionLengthAlert;

    @FXML
    private Label locationLengthAlert;

    @FXML
    private Label titleLengthAlert;

    @FXML
    private Label saveSuccessfulLabel;

    @FXML
    private Label saveErrorLabel;

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

    public Appointment getOverlappingAppointment() {
        return overlappingAppointment;
    }

    public void setOverlappingAppointment(Appointment overlappingAppointment) {
        this.overlappingAppointment = overlappingAppointment;
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
    int onTableClickGetSelectedCustomer(MouseEvent event) {
        if(!customerTable.getSelectionModel().isEmpty()){
            selectedCustomer = customerTable.getSelectionModel().getSelectedItem();
            selectedCustomerID = selectedCustomer.getCustomerId();
            System.out.println("SelectedCustomerID from AddAppointmentController: " + selectedCustomerID);
            setSelectedCustomerID(selectedCustomerID);
        }
        return selectedCustomerID;
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

    public boolean overlapValidationB(LocalDateTime startDate, LocalDateTime endDate) throws SQLException {


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



    @FXML
    void onSaveButtonAction(ActionEvent event) throws Exception {
        getAppointmentsByCustomerID();
        emptyFieldAlert();
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
        int customerId = customerTable.getSelectionModel().getSelectedItem().getCustomerId();
        int userId = userComboBox.getValue().getUserId();


        boolean validationResultStartBeforeEnd = validateStartBeforeEndTime(); //returns true if validation passes
        boolean validationResultA = overlapValidationA(startDateTime); // returns false if validation passes
        boolean validationResultB = overlapValidationB(startDateTime, endDateTime); // returns false if validation passes
        boolean validationResultC = overlapValidationC(startDateTime, endDateTime); // returns false if validation passes
        System.out.println("Validation Result Start before end: " + validationResultStartBeforeEnd);
        System.out.println("validationResultA: " + validationResultA);
        System.out.println("validationResultB: " + validationResultB);
        System.out.println("ValidationResultC: " + validationResultC);

        if((!titleTxtField.getText().isEmpty()) && (titleTxtField.getLength() < 50) &&
                (!locationTxtField.getText().isEmpty()) && (locationTxtField.getLength() < 50) &&
                (!appointmentDescriptionTxtField.getText().isEmpty()) && (appointmentDescriptionTxtField.getLength() < 50) &&
                (contactComboBox.getValue() != null) && (typeComboBox.getValue() != null) &&
                (startDatePicker.getValue() != null) && (startTimeHrComboBox != null) &&
                (endDatePicker.getValue() != null) && (endTimeHrComboBox != null) &&
                (customerTable.getSelectionModel().getSelectedItem() != null) && (userComboBox.getValue() != null)) {

            if (validateStartBeforeEndTime() == true && validationResultA == false && validationResultB == false && validationResultC == false) {
                Appointment appointmentToSave = new Appointment(title, description, location, type, startDateTime, endDateTime, customerId, userId, contactId);
                AppointmentsImplement.addAppointment(appointmentToSave);
                saveButton.setDisable(true);
                saveSuccessfulLabel.setVisible(true);
            } else {
                System.out.println("Conflicting appointment found");
                overlapAlert();
                saveErrorLabel.setVisible(true);
            }
        }
    }


public  void overlapAlert(){
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
            "and ending on: " + overlapEndDate + " at " + overlapEndTime);
    infoRequiredAlert.showAndWait();
}

    public void alert(){
        Alert infoRequiredAlert = new Alert(Alert.AlertType.WARNING);
        infoRequiredAlert.setTitle("Information Required");
        infoRequiredAlert.setHeaderText("Please enter all information.  Thank you! ");
        infoRequiredAlert.setContentText("Please enter missing information");
        infoRequiredAlert.showAndWait();
    }

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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getAllContactNames();
            getAllUserNames();
            getAppointmentsByCustomerID();

        } catch (SQLException e) {
            e.getMessage();
            e.getCause();
            e.printStackTrace();
        }

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


        endTimeHrComboBox.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus is lost
                Boolean validationResult = null;
                saveButton.setDisable(false);
                endTimeHrComboBox.setStyle("-fx-border-color: default; -fx-focus-color: default;");
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
                saveButton.setDisable(false);
                endDatePicker.setStyle("-fx-border-color: default; -fx-focus-color: default;");
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

//Change listeners to validate text fields and warn the user in real time if input is over the allowed length
        titleTxtField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                saveSuccessfulLabel.setVisible(false);
                saveErrorLabel.setVisible(false);
                saveButton.setDisable(false);
                allFieldsRequiredLabel.setVisible(false);
                titleTxtField.setStyle("-fx-border-color: default; -fx-focus-color: default;");
                if(titleTxtField.getLength() > 50) {
                    titleLengthAlert.setVisible(true);}
                else if(titleTxtField.getLength() < 50){titleLengthAlert.setVisible(false);}
            }
        });

        locationTxtField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                saveSuccessfulLabel.setVisible(false);
                saveErrorLabel.setVisible(false);
                saveButton.setDisable(false);
                allFieldsRequiredLabel.setVisible(false);
                locationTxtField.setStyle("-fx-border-color: default; -fx-focus-color: default;");
                if(locationTxtField.getLength() > 50) {
                    locationLengthAlert.setVisible(true);}
                else if(locationTxtField.getLength() < 50){locationLengthAlert.setVisible(false);}
            }
        });

        appointmentDescriptionTxtField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                saveSuccessfulLabel.setVisible(false);
                saveErrorLabel.setVisible(false);
                saveButton.setDisable(false);
                allFieldsRequiredLabel.setVisible(false);
                appointmentDescriptionTxtField.setStyle("-fx-border-color: default; -fx-focus-color: default;");
                if(appointmentDescriptionTxtField.getLength() > 50) {
                    descriptionLengthAlert.setVisible(true);}
                else if(appointmentDescriptionTxtField.getLength() < 50){descriptionLengthAlert.setVisible(false);}
            }
        });

        typeComboBox.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                saveSuccessfulLabel.setVisible(false);
                saveErrorLabel.setVisible(false);
                saveButton.setDisable(false);
                allFieldsRequiredLabel.setVisible(false);
                typeComboBox.setStyle("-fx-border-color: default; -fx-focus-color: default;");
            }
        });

        userComboBox.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                saveSuccessfulLabel.setVisible(false);
                saveErrorLabel.setVisible(false);
                saveButton.setDisable(false);
                allFieldsRequiredLabel.setVisible(false);
                userComboBox.setStyle("-fx-border-color: default; -fx-focus-color: default;");
            }
        });

        contactComboBox.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                saveSuccessfulLabel.setVisible(false);
                saveErrorLabel.setVisible(false);
                saveButton.setDisable(false);
                allFieldsRequiredLabel.setVisible(false);
                contactComboBox.setStyle("-fx-border-color: default; -fx-focus-color: default;");
            }
        });

        customerTable.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                saveSuccessfulLabel.setVisible(false);
                saveErrorLabel.setVisible(false);
                saveButton.setDisable(false);
                allFieldsRequiredLabel.setVisible(false);
                customerTable.setStyle("-fx-border-color: default; -fx-focus-color: default;");
            }
        });

        startDatePicker.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                saveSuccessfulLabel.setVisible(false);
                saveErrorLabel.setVisible(false);
                saveButton.setDisable(false);
                allFieldsRequiredLabel.setVisible(false);
                startDatePicker.setStyle("-fx-border-color: default; -fx-focus-color: default;");
            }
        });

        startTimeHrComboBox.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                saveSuccessfulLabel.setVisible(false);
                saveErrorLabel.setVisible(false);
                saveButton.setDisable(false);
                allFieldsRequiredLabel.setVisible(false);
                startTimeHrComboBox.setStyle("-fx-border-color: default; -fx-focus-color: default;");
            }
        });


    }
}
