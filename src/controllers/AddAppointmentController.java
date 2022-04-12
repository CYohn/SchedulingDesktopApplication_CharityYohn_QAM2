package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

// Idea for future revisions: If the User ID is the same as the person who loggs in, assign the user ID based on the login

public class AddAppointmentController implements Initializable {

    @FXML
    private GridPane applicationFormLeft;

    @FXML
    private TextArea appointmentDescription;

    @FXML
    private Button clearButton;

    @FXML
    private ComboBox<?> contactComboBox;

    @FXML
    private TableColumn<?, ?> customerId;

    @FXML
    private TableColumn<?, ?> customerName;

    @FXML
    private TextField customerSearch;

    @FXML
    private TableView<?> customerTable;

    @FXML
    private TextField locationTxtField;

    @FXML
    private Button saveButton;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private ComboBox<?> startTimeHr;

    @FXML
    private ComboBox<?> startTimeMin;

    @FXML
    private TextField titleTxtField;

    @FXML
    private ComboBox<?> typeComboBox;

    @FXML
    private TableColumn<?, ?> userId;

    @FXML
    private TableColumn<?, ?> userName;

    @FXML
    private TextField userSearch;

    @FXML
    private TableView<?> userTable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
