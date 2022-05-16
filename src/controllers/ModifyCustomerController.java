package controllers;

import Objects.Customer;
import implementationsDao.CountriesImplement;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import utilities.DatabaseConnection;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static implementationsDao.CustomersImplement.getAllCustomers;
import static java.lang.String.valueOf;

public class ModifyCustomerController implements Initializable {

    ObservableList<String> divisionIDResult = CountriesImplement.populateCountryNamesList();

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


    //Form labels
    @FXML
    private Label customerRemovedLabel;
    @FXML
    private Label updateSuccessfulLabel;
    @FXML
    private Label custIdLabel;
    @FXML
    private Label saveErrorLabel;
    @FXML
    private Label saveSuccessfulLabel;

    //Form buttons
    @FXML
    private Button saveButton;
    @FXML
    private Button clearButton;
    @FXML
    private Button deleteCustButton;

    public ModifyCustomerController() throws SQLException {
    }


    public void populateCustomerTable(ObservableList<Customer>getAllCustomers) {

            allCustomersTable.setItems(getAllCustomers);

            custIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            custNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));
            custAddressColumn.setCellValueFactory(new PropertyValueFactory<>("customerAddress"));
            custCountryColumn.setCellValueFactory(new PropertyValueFactory<>("country"));
            custStateColumn.setCellValueFactory(new PropertyValueFactory<>("division"));
            custPostalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("customerPostalCode"));
            custPhoneColumn.setCellValueFactory(new PropertyValueFactory<>("customerPhone"));
    }

    @FXML
    void onTableClickGetSelectedCustomer(MouseEvent event) {
        if(!allCustomersTable.getSelectionModel().isEmpty()){
            Customer selectedCustomer = allCustomersTable.getSelectionModel().getSelectedItem();
            populateSelectedCustomer(selectedCustomer);
        }
    }

    public void populateSelectedCustomer(Customer selectedCustomer){


         String selectedAddress = selectedCustomer.getCustomerAddress();
         Integer selectedCustId = selectedCustomer.getCustomerId();
         String selectedName = selectedCustomer.getCustomerName();
         String selectedPhone = selectedCustomer.getCustomerPhone();
         String selectedPostal = selectedCustomer.getCustomerPostalCode();
         String selectedState = selectedCustomer.getDivision();
        String selectedCountry = selectedCustomer.getCountry();

        custNameTxtField.setText(selectedName);
        custIdLabel.setText(valueOf(selectedCustId));
        addressTxtField.setText(selectedAddress);
        custPhoneTxtField.setText(selectedPhone);
        postalCodeTxtField.setText(selectedPostal);

        //TO DO: Set the state and country to match the customer's state and country
    }
    /**
     * Method enables and populates the Divisions ComboBox. First the method uses the user's input from the country combobox
     * to find the associated country ID. The method then uses the countryID to cross reference the associated divisions stored
     * in the database. Finally, the method stores the divisions in an Observable list and populates the combo box with the
     * appropriate divisions.
     * @param event User selects a country
     * @throws SQLException Provides information about database access errors and possibly other errors
     * for more information about SQLException see https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     */
    @FXML
    void populateDivisionComboBox(ActionEvent event) throws SQLException {
        System.out.println("Beginning Division ComboBox population");
        stateComboBox.setDisable(false); //Enable the divisions ComboBox
        PreparedStatement sqlFindCountryIDPreparedStatement;
        PreparedStatement getDivisionsPreparedStatement;
        String selectedCountry = countryComboBox.getValue(); //Get the User's country selection

        ObservableList<String> divisionNames = FXCollections.observableArrayList(); //To store the divisions

        try {
            //Find the associated country ID based on the selected country
            String FindCountryIDSearchStatement = ("SELECT Country_ID from countries WHERE Country = '" + selectedCountry + "'");
            sqlFindCountryIDPreparedStatement = DatabaseConnection.getConnection().prepareStatement(FindCountryIDSearchStatement);
            ResultSet countryIDResult = sqlFindCountryIDPreparedStatement.executeQuery(FindCountryIDSearchStatement);

            while (countryIDResult.next()) {
                int countryID = countryIDResult.getInt("Country_ID");
                System.out.println("Country ID was found: " + countryID);

                //Find the appropriate divisions based on the country ID found above
                String sqlDivisionsQuery = ("SELECT Division FROM first_level_divisions WHERE Country_ID = " + countryID + " ORDER BY Division ASC");
                getDivisionsPreparedStatement = DatabaseConnection.getConnection().prepareStatement(sqlDivisionsQuery);
                ResultSet divisionsResults = getDivisionsPreparedStatement.executeQuery(sqlDivisionsQuery);

                while (divisionsResults.next()) {
                    String division = divisionsResults.getString("Division");
                    divisionNames.add(division); //Add divisions to the ObservableList
                    stateComboBox.setItems(divisionNames); //Populate the ComboBox
                }
                System.out.println("Divisions successfully populated");
            }

        } catch (Exception e) {
            System.out.println("SQLException thrown in populateDivisionNamesList() method in the AddCustomerController file");
            e.getCause();
            e.getMessage();
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("ModifyCustomerController initialized");
        customerRemovedLabel.setVisible(false);
        try {
            getAllCustomers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        populateCustomerTable(getAllCustomers);
        System.out.println("All customers:  " + getAllCustomers);
        System.out.println("getAllCustomers list on initialize in ModifyCustomersController:  " + getAllCustomers);

        System.out.println("Printing the observable list of names from the AddCustomer controller: " + divisionIDResult);
        countryComboBox.setItems(divisionIDResult);
        stateComboBox.setDisable(true);
        saveErrorLabel.setVisible(false);
        saveSuccessfulLabel.setVisible(false);
        saveButton.setDisable(false);



    }


}
