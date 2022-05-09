package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ModifyCustomerController implements Initializable {

    @FXML
    private MenuButton searchSelectorMenu;

    @FXML
    private TextField searchTxtField;

    @FXML
    private TextField addressTxtField;

    @FXML
    private TableView<?> allCustomersTable;

    @FXML
    private GridPane applicationFormLeft;

    @FXML
    private Button clearButton;

    @FXML
    private ComboBox<?> countryComboBox;

    @FXML
    private TableColumn<?, ?> custAddressColumn;

    @FXML
    private TableColumn<?, ?> custCountryColumn;

    @FXML
    private TableColumn<?, ?> custIdColumn;

    @FXML
    private TableColumn<?, ?> custNameColumn;

    @FXML
    private TextField custNameTxtField;

    @FXML
    private TableColumn<?, ?> custPhoneColumn;

    @FXML
    private TextField custPhoneTxtField;

    @FXML
    private TableColumn<?, ?> custPostalCodeColumn;

    @FXML
    private TableColumn<?, ?> custStateColumn;

    @FXML
    private Button deleteCustButton;

    @FXML
    private TextField postalCodeTxtField;

    @FXML
    private Button saveButton;

    @FXML
    private ComboBox<?> stateComboBox;

    @FXML
    private Label custIdLabel;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
