package controllers;

import Objects.Customer;
import implementationsDao.CustomersImplement;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ModifyCustomerController implements Initializable {



    @FXML
    private MenuButton searchSelectorMenu;

    @FXML
    private TextField searchTxtField;

    @FXML
    private TextField addressTxtField;

    @FXML
    private TableView<Customer> allCustomersTable;

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

    @FXML
    private Label customerRemovedLabel;

    public ModifyCustomerController() throws SQLException {
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerRemovedLabel.setVisible(false);

    }
}
