package controllers;

import implementationsDao.ContactsImplement;
import implementationsDao.UsersImplement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static implementationsDao.ContactsImplement.getAllContactNames;
import static implementationsDao.UsersImplement.getAllUserNames;
import static implementationsDao.UsersImplement.userNames;

// Idea for future revisions: If the User ID is the same as the person who logs in, assign the user ID based on the login

public class AddAppointmentController implements Initializable {

    private ObservableList<String> contactNames = ContactsImplement.contactNames;
    private ObservableList<String> UserNames = UsersImplement.userNames;
    private ObservableList<String> appointmentTypes = FXCollections.observableArrayList
            ("Planning Session", "Progress Update", "De-Briefing");

    @FXML
    private GridPane applicationFormLeft;

    @FXML
    private TextArea appointmentDescriptionTxtField;

    @FXML
    private Button clearButton;

    @FXML
    private ComboBox<String> contactComboBox;

    @FXML
    private TableColumn<?, ?> customerId;

    @FXML
    private TableColumn<?, ?> customerId1;

    @FXML
    private TableColumn<?, ?> customerName;

    @FXML
    private TableView<?> customerTable;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private ComboBox<?> endTimeHrComboBox;

    @FXML
    private ComboBox<?> endTimeMinComboBox;

    @FXML
    private TextField locationTxtField;

    @FXML
    private Button saveButton;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private ComboBox<?> startTimeHrComboBox;

    @FXML
    private ComboBox<?> startTimeMinComboBox;

    @FXML
    private TextField titleTxtField;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private ComboBox<String> userComboBox;



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
        contactComboBox.setItems(contactNames.sorted());
        typeComboBox.setItems(appointmentTypes);
        userComboBox.setItems(userNames);

    }
}
