package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {

    @FXML
    private TableColumn<?, ?> aptContactColumn2;

    @FXML
    private TableColumn<?, ?> aptContactColumn21;

    @FXML
    private TableColumn<?, ?> aptDescriptioncolumn2;

    @FXML
    private TableColumn<?, ?> aptDescriptioncolumn21;

    @FXML
    private TableColumn<?, ?> aptEndDateColumn2;

    @FXML
    private TableColumn<?, ?> aptEndDateColumn21;

    @FXML
    private TableColumn<?, ?> aptEndTimeColumn2;

    @FXML
    private TableColumn<?, ?> aptEndTimeColumn21;

    @FXML
    private TableColumn<?, ?> aptIdColumn2;

    @FXML
    private TableColumn<?, ?> aptIdColumn21;

    @FXML
    private TableColumn<?, ?> aptLocationColumn2;

    @FXML
    private TableColumn<?, ?> aptLocationColumn21;

    @FXML
    private TableColumn<?, ?> aptStartDateColumn2;

    @FXML
    private TableColumn<?, ?> aptStartDateColumn21;

    @FXML
    private TableColumn<?, ?> aptStartTimeColumn2;

    @FXML
    private TableColumn<?, ?> aptStartTimeColumn21;

    @FXML
    private TableColumn<?, ?> aptTitleColum2;

    @FXML
    private TableColumn<?, ?> aptTitleColum21;

    @FXML
    private TableColumn<?, ?> aptTypeColumn2;

    @FXML
    private TableColumn<?, ?> aptTypeColumn21;

    @FXML
    private TableView<?> contactAppointmentTable;

    @FXML
    private ComboBox<?> contactComboBox;

    @FXML
    private TableColumn<?, ?> custIdColumn2;

    @FXML
    private TableColumn<?, ?> custIdColumn21;

    @FXML
    private TableView<?> customerAppointmentTable;

    @FXML
    private ComboBox<?> customerComboBox;

    @FXML
    private ComboBox<?> monthComboBox;

    @FXML
    private TextField numberOfAppointments;

    @FXML
    private ComboBox<?> typeComboBox;

    @FXML
    private TableColumn<?, ?> userIdColumn2;

    @FXML
    private TableColumn<?, ?> userIdColumn21;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
