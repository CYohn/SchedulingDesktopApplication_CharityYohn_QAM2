package controllers;

import Objects.Customer;
import com.sun.javafx.binding.DoubleConstant;
import implementationsDao.ContactsImplement;
import implementationsDao.UsersImplement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import utilities.TimezoneConversion;

import java.net.URL;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.util.Date;
import java.util.ResourceBundle;

import static implementationsDao.ContactsImplement.getAllContactNames;
import static implementationsDao.CustomersImplement.getAllCustomers;
import static implementationsDao.UsersImplement.getAllUserNames;

// Idea for future revisions: If the User ID is the same as the person who logs in, assign the user ID based on the login

public class AddAppointmentController extends TimezoneConversion implements Initializable {


    private static LocalDateTime startDate;
    private static LocalDateTime endDate;

    private ObservableList<String> contactNames = ContactsImplement.contactNames;
    private ObservableList<String> userNames = UsersImplement.userNames;
    private ObservableList<String> appointmentTypes = FXCollections.observableArrayList
            ("Planning Session", "Progress Update", "De-Briefing");

    Customer selectedCustomer;

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
    private ComboBox<String> contactComboBox;

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
    private ComboBox<String> userComboBox;

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
            startTimeHrComboBox.getItems().add(startHour);
            startHour = startHour.plusMinutes(15);
        }
    }

    public LocalDateTime getStartDateTimeSelection() {
        LocalDateTime startDateTimeSelection = LocalDateTime.of(startDatePicker.getValue(), startTimeHrComboBox.getValue());
        setStartDate(startDateTimeSelection);
        return startDateTimeSelection;
    }

    public LocalDateTime getEndDateTimeSelection() {
        LocalDateTime endDateTimeSelection = LocalDateTime.of(endDatePicker.getValue(), endTimeHrComboBox.getValue());
        setEndDate(endDateTimeSelection);
        return endDateTimeSelection;
    }

    public boolean validateStartBeforeEndTime() throws DateTimeException {
        try {
            LocalDateTime startSelection = getStartDateTimeSelection();
            LocalDateTime endSelection = getEndDateTimeSelection();
            if (endSelection.isAfter(startSelection) && startSelection.isAfter(LocalDateTime.now())) {
                return true;
            } else {
                return false;
            }
        }catch (DateTimeException e){
            e.getMessage();
            e.getCause();
            e.printStackTrace();
        }
        return false;
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            getAllContactNames();
        } catch (SQLException e) {
            e.getMessage();
            e.getCause();
            e.printStackTrace();
        }
        try {
            getAllUserNames();
        } catch (SQLException e) {
            e.getMessage();
            e.getCause();
            e.printStackTrace();
        }

        try {
            getAllCustomers();
        } catch (SQLException e) {
            e.getMessage();
            e.getCause();
            e.printStackTrace();
        }

        dateAndTimeErrorLabel.setVisible(false);
        contactComboBox.setItems(contactNames.sorted());
        typeComboBox.setItems(appointmentTypes);
        userComboBox.setItems(userNames);
        populateStartTimeComboBox();
        populateEndTimeComboBox();
        populateCustomerTable(getAllCustomers);

        endTimeHrComboBox.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus is lost
                Boolean validationResult = validateStartBeforeEndTime();
                if(validationResult) {dateAndTimeErrorLabel.setVisible(false);}
                else{dateAndTimeErrorLabel.setVisible(true);}
            }
        });

        endDatePicker.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus is lost
                endTimeHrComboBox.getValue();
                if(!endTimeHrComboBox.getValue().equals(null)) { //if the end time is not null
                    Boolean validationResult = validateStartBeforeEndTime();
                    if (validationResult) {
                        dateAndTimeErrorLabel.setVisible(false);
                    } else {
                        dateAndTimeErrorLabel.setVisible(true);
                    }
                }

            }
        });




    }
}
