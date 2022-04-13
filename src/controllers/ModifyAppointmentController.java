package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ModifyAppointmentController implements Initializable {


        @FXML
        private GridPane applicationFormLeft;

        @FXML
        private TextArea appointmentDescription;

        @FXML
        private TableView<?> appointmentTable;

        @FXML
        private Button clearButton;

        @FXML
        private ComboBox<?> contactComboBox;

        @FXML
        private TextField locationTxtField;

        @FXML
        private AnchorPane modifyAppointmentAnchor;

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


        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

        }
}
