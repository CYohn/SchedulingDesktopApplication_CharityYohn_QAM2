package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
        private TableColumn<?, ?> aptContactColumn;

        @FXML
        private TableColumn<?, ?> aptDescriptioncolumn;

        @FXML
        private TableColumn<?, ?> aptEndDateColumn;

        @FXML
        private TableColumn<?, ?> aptEndTimeColumn;

        @FXML
        private TableColumn<?, ?> aptIdColumn;

        @FXML
        private TableColumn<?, ?> aptLocationColumn;

        @FXML
        private TableColumn<?, ?> aptStartDateColumn;

        @FXML
        private TableColumn<?, ?> aptStartTimeColumn;

        @FXML
        private TableColumn<?, ?> aptTitleColum;

        @FXML
        private TableColumn<?, ?> aptTypeColumn;

        @FXML
        private Button clearButton;

        @FXML
        private ComboBox<?> contactComboBox;

        @FXML
        private TableColumn<?, ?> custIdColumn;

        @FXML
        private TextField locationTxtField;

        @FXML
        private AnchorPane modifyAppointmentAnchor;

        @FXML
        private Button saveButton;

        @FXML
        private MenuButton searchSelectorMenu;

        @FXML
        private TextField searchTxtField;

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
        private TableColumn<?, ?> userIdColumn;


        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {

        }
}
