package controllers;

import Objects.Country;
import Objects.Customer;
import Objects.FirstLevelDivision;
import implementationsDao.CustomersImplement;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ModifyCustomerController implements Initializable {


    String division = CustomersImplement.getDivisionName(CustomersImplement.allCustomers);

    @FXML
    private TableView<Customer> allCustomersTable;

    //Table Columns
    //Search Type menu comboBox
    @FXML
    private MenuButton searchSelectorMenu;
    @FXML
    private TableColumn<Customer, String> custAddressColumn;
    @FXML
    private TableColumn<Customer, String> custCountryColumn;
    @FXML
    private TableColumn<Customer, Integer> custIdColumn;
    @FXML
    private TableColumn<Customer, String> custNameColumn;
    @FXML
    private TableColumn<Customer, String> custPhoneColumn;
    @FXML
    private TableColumn<Customer, String> custPostalCodeColumn;
    @FXML
    private TableColumn<Customer, String> custStateColumn;
    //Form text fields
    @FXML
    private TextField custPhoneTxtField;
    @FXML
    private TextField custNameTxtField;
    @FXML
    private TextField searchTxtField;

    @FXML
    private TextField postalCodeTxtField;
    @FXML
    private TextField addressTxtField;
    //Form ComboBoxes
    @FXML
    private ComboBox<String> stateComboBox;
    @FXML
    private ComboBox<String> countryComboBox;

    @FXML
    private Label customerRemovedLabel;
    //Form labels
    @FXML
    private Label custIdLabel;
    @FXML
    private GridPane applicationFormLeft; //Grid pane containing the application form
    //Form buttons
    @FXML
    private Button saveButton;
    @FXML
    private Button clearButton;

    //Variables
    @FXML
    private Button deleteCustButton;

    public void populateCustomerTable() {
        allCustomersTable.setItems(CustomersImplement.allCustomers);

        custAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        custCountryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
        custIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        custNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        custPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        custPostalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        custStateColumn.setCellValueFactory(new PropertyValueFactory<>("division"));
    }

    public ModifyCustomerController() throws SQLException {
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        customerRemovedLabel.setVisible(false);
        populateCustomerTable();

    }
}
