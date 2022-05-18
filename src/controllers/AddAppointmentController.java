package controllers;

import Objects.Customer;
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

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.util.Date;
import java.util.ResourceBundle;

import static implementationsDao.ContactsImplement.getAllContactNames;
import static implementationsDao.CustomersImplement.getAllCustomers;
import static implementationsDao.UsersImplement.getAllUserNames;

// Idea for future revisions: If the User ID is the same as the person who logs in, assign the user ID based on the login

public class AddAppointmentController implements Initializable {

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


    public void populateCustomerTable(ObservableList<Customer>getAllCustomers) {

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

    public void populateStartTimeComboBox(){
        LocalTime startHour = LocalTime.of(8,0);
        LocalTime endHour = LocalTime.of(22,0);
        while (startHour.isBefore(endHour)){
            startTimeHrComboBox.getItems().add(startHour);

            startHour = startHour.plusMinutes(15);
        }
    }

    public void populateEndTimeComboBox(){
        LocalTime startHour = LocalTime.of(8,0);
        LocalTime endHour = LocalTime.of(22,0);
        while (startHour.isBefore(endHour)){
            endTimeHrComboBox.getItems().add(startHour);
            startHour = startHour.plusMinutes(15);
        }
    }

    public boolean validateStartBeforeEndTime(){
        LocalDateTime startSelection = LocalDateTime.of(startDatePicker.getValue(), startTimeHrComboBox.getValue());
        LocalDateTime endSelection = LocalDateTime.of(endDatePicker.getValue(), endTimeHrComboBox.getValue());
        if(endSelection.isAfter(startSelection) && startSelection.isAfter(LocalDateTime.now())){
            return true;
        }
        else{return false;}
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
    }
}
