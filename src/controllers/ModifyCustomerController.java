package controllers;

import Objects.Customer;
import implementationsDao.CustomersImplement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static implementationsDao.CustomersImplement.getAllCustomers;
import static java.lang.String.valueOf;

public class ModifyCustomerController implements Initializable {

    ObservableList<String>searchOptions = FXCollections.observableArrayList(
            "ID Number", "Name", "Address", "Country", "State", "Postal Code", "Phone", "Show All Customers"
    );

    public static ObservableList<Customer>customersToPopulate = FXCollections.observableArrayList();

    @FXML
    private TableView<Customer> allCustomersTable;

    //Table Columns

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

    //Form labels
    @FXML
    private Label customerRemovedLabel;
    @FXML
    private Label updateSuccessfulLabel;
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







    public void populateCustomerTable(ObservableList<Customer>customersToPopulate) {

        try {
            CustomersImplement.getAllCustomers();
            allCustomersTable.setItems(customersToPopulate);

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


public void searchCustomers(){

    String userSearchValue = searchTxtField.getText();
    int userSearchType = searchSelectorMenu.getSelectionModel().getSelectedIndex();
    System.out.println("userSearchValue before switch statement: " + userSearchValue);
    System.out.println("Customers to populate before the switch statement: " + customersToPopulate);

    switch (userSearchType){
        case 0: customersToPopulate.clear();
            getAllCustomers.stream().filter(customer -> (valueOf(customer.getCustomerId()).equalsIgnoreCase(userSearchValue)))
                    .forEach(c -> customersToPopulate.addAll(c));
            System.out.println ("Case 0 Search Index: " + userSearchType + "userSearchValue: " + userSearchValue);
            System.out.println("Case 0 customersToPopulate List: " + customersToPopulate);
            populateCustomerTable(customersToPopulate);
            break;

        case 1: customersToPopulate.clear();
            getAllCustomers.stream().filter(customer -> customer.getCustomerName().equalsIgnoreCase(userSearchValue))
                    .forEach(c -> customersToPopulate.addAll(c));
            System.out.println ("Case 1 Search Index: " + userSearchType+ "userSearchValue: " + userSearchValue );
            System.out.println("Case 1 customersToPopulate List: " + customersToPopulate.toString());
            populateCustomerTable(customersToPopulate);
            break;

        case 2: customersToPopulate.clear();
            getAllCustomers.stream().filter(customer -> customer.getCustomerAddress().equalsIgnoreCase(userSearchValue))
                    .forEach(c -> customersToPopulate.addAll(c));
            System.out.println ("Case 2 Search Index: " + userSearchType+ "userSearchValue: " + userSearchValue);

            populateCustomerTable(customersToPopulate);
            System.out.println("Case 2 customersToPopulate List: " + customersToPopulate.toString());
            break;

        case 3: customersToPopulate.clear();
            getAllCustomers.stream().filter(customer -> customer.getCountry().equalsIgnoreCase(userSearchValue))
                    .forEach(c -> customersToPopulate.addAll(c));
            System.out.println ("Case 3 Search Index: " + userSearchType+ "userSearchValue: " + userSearchValue);

            populateCustomerTable(customersToPopulate);
            System.out.println("Case 3 customersToPopulate List: " + customersToPopulate.toString());
            break;

        case 4: customersToPopulate.clear();
            getAllCustomers.stream().filter(customer -> customer.getDivision().equalsIgnoreCase(userSearchValue))
                    .forEach(c -> customersToPopulate.addAll(c));
            System.out.println (" Case 4 Search Index: " + userSearchType+ "userSearchValue: " + userSearchValue);

            populateCustomerTable(customersToPopulate);
            System.out.println("Case 4 customersToPopulate List: " + customersToPopulate.toString());
            break;

        case 5: customersToPopulate.clear();
            getAllCustomers.stream().filter(customer -> customer.getCustomerPostalCode().equalsIgnoreCase(userSearchValue))
                    .forEach(c -> customersToPopulate.addAll(c));
            System.out.println ("Case 5 Search Index: " + userSearchType+ "userSearchValue: " + userSearchValue);

            populateCustomerTable(customersToPopulate);
            System.out.println("Case 5 customersToPopulate List: " + customersToPopulate.toString());
            break;

        case 6: customersToPopulate.clear();
            getAllCustomers.stream().filter(customer -> customer.getCustomerPhone().equalsIgnoreCase(userSearchValue))
                    .forEach(c -> customersToPopulate.addAll(c));
            System.out.println ("Case 6 Search Index: " + userSearchType+ "userSearchValue: " + userSearchValue);

            populateCustomerTable(customersToPopulate);
            System.out.println("Case 6 customersToPopulate List: " + customersToPopulate.toString());
            break;

        case 7: customersToPopulate.clear();
            customersToPopulate = getAllCustomers;
            System.out.println ("Case 7 Search Index: " + userSearchType+ "userSearchValue: " + userSearchValue);
            populateCustomerTable(customersToPopulate);
            System.out.println("Case 6 customersToPopulate List: " + customersToPopulate.toString());
            break;

        default: customersToPopulate = getAllCustomers;
            System.out.println ("Default Search Index: " + userSearchType+ "userSearchValue: " + userSearchValue);
            break;
    }

}

//    @FXML
//    void searchCustomersKeyPressed(KeyEvent event) { searchCustomers();}

//    @FXML
//    void searchCustomersOnAction(ActionEvent event) { searchCustomers();}

    @FXML
    void searchCustomerstxtChanged(InputMethodEvent event) { searchCustomers();}





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("ModifyCustomerController initialized");
        customerRemovedLabel.setVisible(false);
        //customersToPopulate = getAllCustomers;
        populateCustomerTable(customersToPopulate);


        System.out.println("Search Box observable list:" + searchOptions);
        searchSelectorMenu.setItems(searchOptions);


//        searchTxtField.focusedProperty().addListener((ov, oldV, newV) -> {
//            if(newV){
//                searchCustomers();
//            }
//        });
    }


}
