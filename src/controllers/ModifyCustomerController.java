package controllers;

import Objects.Country;
import Objects.Customer;
import Objects.FirstLevelDivision;
import implementationsDao.CustomersImplement;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
    private ComboBox<String> searchSelectorMenu;


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

    public void setMenuButtonItems() {
        String searchOptions[] =
                { "ID Number", "Name", "Street Address",
                        "Postal Code", "State / Province", "Country", "Phone" };
        ObservableList<String> comboBoxSearchOptions = FXCollections.observableArrayList(searchOptions);




//            customerId.setOnAction(b -> {                             //Action listener for the menu item selection
//                getCustomerWithCustomerId(searchTxtField.getText());
//            });
//
//
//            customerName.setOnAction(c -> {
//                getCustomerWithName(searchTxtField.getText());
//            });
//
//            MenuItem customerAddress = new MenuItem("Street Address");
//            customerAddress.setOnAction(d -> {
//                getCustomerWithAddress(searchTxtField.getText());
//            });
//
//            MenuItem customerPostalCode = new MenuItem("Postal Code");
//            customerPostalCode.setOnAction(e -> {
//                getCustomerWithPostalCode(searchTxtField.getText());
//            });
//
//            MenuItem customerDivision = new MenuItem("State / Province");
//            customerDivision.setOnAction(f -> {
//                getCustomerWithDivision(searchTxtField.getText());
//            });
//
//            MenuItem customerCountry = new MenuItem("Country");
//            customerCountry.setOnAction(g -> {
//                getCustomerWithCountry(searchTxtField.getText());
//            });
//
//            MenuItem customerPhone = new MenuItem("Phone");
//            customerPhone.setOnAction(h -> {
//                getCustomerWithPhone(searchTxtField.getText());
//            });


    }




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
        } catch (SQLException populateCustomerTableException) {
            populateCustomerTableException.getMessage();
            populateCustomerTableException.getCause();
            populateCustomerTableException.printStackTrace();
        }

    }

    //Searches for search options menu
    //Name search
    private Customer getCustomerWithName(String customerName) {
        ObservableList<Customer> allCustomers = getAllCustomers;
        for (Customer customer : allCustomers) {
            if (customer.getCustomerName() == customerName) {
                return customer;
            }
        }
        return null;
    }

    //Search by address
    private Customer getCustomerWithAddress(String customerAddress) {
        ObservableList<Customer> allCustomers = getAllCustomers;
        for (Customer customer : allCustomers) {
            if (customer.getCustomerAddress() == customerAddress) {
                return customer;
            }
        }
        return null;
    }

    //Search by phone
    private Customer getCustomerWithPhone(String customerPhone) {
        ObservableList<Customer> allCustomers = getAllCustomers;
        for (Customer customer : allCustomers) {
            if (customer.getCustomerPhone() == customerPhone) {
                return customer;
            }
        }
        return null;
    }

    //Search by postalCode
    private Customer getCustomerWithPostalCode(String customerPostalCode) {
        ObservableList<Customer> allCustomers = getAllCustomers;
        for (Customer customer : allCustomers) {
            if (customer.getCustomerPostalCode() == customerPostalCode) {
                return customer;
            }
        }
        return null;
    }

    //Search by customerId
    private Customer getCustomerWithCustomerId(String customerId) {
        ObservableList<Customer> allCustomers = getAllCustomers;
        for (Customer customer : allCustomers) {
            String idInList = String.valueOf(customer.getCustomerId());
            if (idInList == customerId) {
                return customer;
            }
        }
        return null;
    }

    //Search by division
    private Customer getCustomerWithDivision(String customerDivision) {
        ObservableList<Customer> allCustomers = getAllCustomers;
        for (Customer customer : allCustomers) {
            if (customer.getDivision() == customerDivision) {
                return customer;
            }
        }
        return null;
    }

    //Search by country
    private Customer getCustomerWithCountry(String customerCountry) {
        ObservableList<Customer> allCustomers = getAllCustomers;
        for (Customer customer : allCustomers) {
            if (customer.getCountry() == customerCountry) {
                return customer;
            }
        }
        return null;
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

        setMenuButtonItems();


    }
}
