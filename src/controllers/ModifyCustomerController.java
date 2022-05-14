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

import static implementationsDao.CustomersImplement.getAllCustomers;

public class ModifyCustomerController implements Initializable {




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


    public void populateCustomerTable() throws SQLException {

        try {
            CustomersImplement.getAllCustomers();
            allCustomersTable.setItems(CustomersImplement.allCustomers);

            custIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            custNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            custAddressColumn.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
            custCountryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
            custStateColumn.setCellValueFactory(new PropertyValueFactory<>("division"));
            custPostalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
            custPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
        } catch (SQLException populateCustomerTableException){
            populateCustomerTableException.getMessage();
            populateCustomerTableException.getCause();
            populateCustomerTableException.printStackTrace();
        }

    }

    public ModifyCustomerController() throws SQLException {
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("ModifyCustomerController initialized");
        customerRemovedLabel.setVisible(false);
        try {
            populateCustomerTable();
        } catch (SQLException e) {
            e.getMessage();
            e.getCause();
            e.printStackTrace();
        }


    }
}
