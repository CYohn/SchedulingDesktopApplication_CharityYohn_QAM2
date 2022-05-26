package controllers;

import Objects.Customer;
import implementationsDao.AppointmentsImplement;
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

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static implementationsDao.CustomersImplement.addCustomer;
import static implementationsDao.CustomersImplement.getAllCustomers;
import static java.lang.String.valueOf;

public class ModifyCustomerController implements Initializable {

    //TODO SET THE CLEAR BUTTON

    //TODO deleteCustomer() - Going to work on appointments first

    ObservableList<String> divisionIDResult = CountriesImplement.populateCountryNamesList();

    @FXML
    private TableView<Customer> allCustomersTable;

    Customer customerToUpdateHolder = null; // To pass customer object from onSave() to getDBResponseResponse()

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
    private Label deleteSuccessfulLabel;
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

    //Form Alert Labels
    @FXML
    private Label addressLengthAlert;

    @FXML
    private Label nameLengthAlert;

    @FXML
    private Label phoneLengthAlert;

    @FXML
    private Label postalLengthAlert;

    @FXML
    private Label allFieldsRequiredLabel;



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
        //Get table values
         String selectedAddress = selectedCustomer.getCustomerAddress();
         Integer selectedCustId = selectedCustomer.getCustomerId();
         String selectedName = selectedCustomer.getCustomerName();
         String selectedPhone = selectedCustomer.getCustomerPhone();
         String selectedPostal = selectedCustomer.getCustomerPostalCode();
         String selectedState = selectedCustomer.getDivision();
         String selectedCountry = selectedCustomer.getCountry();

         //Set the form values to the selectedCustomer values
        custNameTxtField.setText(selectedName);
        custIdLabel.setText(valueOf(selectedCustId));
        addressTxtField.setText(selectedAddress);
        custPhoneTxtField.setText(selectedPhone);
        postalCodeTxtField.setText(selectedPostal);
        countryComboBox.setValue(selectedCountry);
        stateComboBox.setValue(selectedState);
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
                    divisionNames.clear();
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
    @FXML
    void onSave(MouseEvent event) throws IOException, SQLException {
        fieldLengthAlert();
        emptyFieldAlert();

        if((!custNameTxtField.getText().isEmpty()) && (custNameTxtField.getLength() < 50) &&
                (!addressTxtField.getText().isEmpty()) && (addressTxtField.getLength() < 100) &&
                (!postalCodeTxtField.getText().isEmpty()) && (postalCodeTxtField.getLength() < 50) &&
                (!custPhoneTxtField.getText().isEmpty()) && (custPhoneTxtField.getLength() < 50) &&
                (countryComboBox.getValue() != null) &&
                (stateComboBox.getValue() != null)) {

            String customerName = custNameTxtField.getText();
            System.out.println(customerName);
            String customerAddress = addressTxtField.getText();
            System.out.println(customerAddress);
            String customerPostalCode = postalCodeTxtField.getText();
            System.out.println(customerPostalCode);
            String customerPhone = custPhoneTxtField.getText();
            System.out.println(customerPhone);
            String customerCountry = countryComboBox.getValue();
            System.out.println(customerCountry);
            int customerDivisionId = getDivisionID();
            System.out.println(customerDivisionId);
            int customerId = (Integer)(allCustomersTable.getSelectionModel().getSelectedItem().getCustomerId());
            System.out.println(customerId);


            Customer customerToUpdate = new Customer(customerId, customerName, customerAddress, customerPostalCode, customerPhone, customerDivisionId);
            customerToUpdateHolder = customerToUpdate;
            //updateCustomer(int customerId, String name, String address, String postalCode, String phone, int division)
            CustomersImplement.updateCustomer(customerToUpdate);

            getDBResponseResponse();

            saveButton.setDisable(true);
            CustomersImplement.getAllCustomers();
            populateCustomerTable(getAllCustomers);
        }
    }

    public void getDBResponseResponse() throws SQLException {
        int databaseResponseToUpdateCustomer = CustomersImplement.updateCustomer(customerToUpdateHolder);
        System.out.println("databaseResponseToUpdateCustomer: " + databaseResponseToUpdateCustomer);
        if (databaseResponseToUpdateCustomer == 1){
            saveSuccessfulLabel.setVisible(true);
            System.out.println("Database response to adding the customer: " + databaseResponseToUpdateCustomer);

        }
        else {
            saveErrorLabel.setVisible(true);
            System.out.println("Customer not added");}

    }

    public int getDivisionID() {
        PreparedStatement findDivisionIDPreparedStatement;
        String selectedDivision = stateComboBox.getValue(); //Get the User's division selection
        int customerDivisionID = 0;

        try {
            //Find the associated division ID based on the selected division name
            String FindDivisionIDSearchStatement = ("SELECT Division_ID from first_level_divisions WHERE Division = '" + selectedDivision + "'");
            findDivisionIDPreparedStatement = DatabaseConnection.getConnection().prepareStatement(FindDivisionIDSearchStatement);
            ResultSet divisionIDResult = findDivisionIDPreparedStatement.executeQuery(FindDivisionIDSearchStatement);


            while (divisionIDResult.next()) {
                customerDivisionID = divisionIDResult.getInt("Division_ID");
                System.out.println("Division ID was found in the getDivisionID method: " + customerDivisionID);
                return customerDivisionID;
            }
        } catch (SQLException throwables) {
            System.out.println("getDivisionID method in the AddCustomerController encountered an error: ");
            throwables.getCause();
            throwables.getMessage();
            throwables.printStackTrace();
        }
        return customerDivisionID;
    }

    public void fieldLengthAlert(){
        if(custNameTxtField.getLength() > 50){nameLengthAlert.setVisible(true);}
        if(addressTxtField.getLength() > 100){addressLengthAlert.setVisible(true);}
        if(postalCodeTxtField.getLength() > 50){postalLengthAlert.setVisible(true);}
        if(custPhoneTxtField.getLength() > 50){phoneLengthAlert.setVisible(true);}
    }

    public void hideLengthAlerts(){
        addressLengthAlert.setVisible(false);
        nameLengthAlert.setVisible(false);
        phoneLengthAlert.setVisible(false);
        postalLengthAlert.setVisible(false);
        allFieldsRequiredLabel.setVisible(false);
    }

    public void emptyFieldAlert() {
        String name = custNameTxtField.getText();
        String address = addressTxtField.getText();
        String postalCode = postalCodeTxtField.getText();
        String phone = custPhoneTxtField.getText();
        String country = countryComboBox.getValue();
        String division = stateComboBox.getValue();

        if(name.trim().isEmpty()||address.trim().isEmpty()||postalCode.trim().isEmpty()
                ||phone.trim().isEmpty()||country == null||division == null){
            alert();
            if (name.trim().isEmpty()) {
                allFieldsRequiredLabel.setVisible(true);
                custNameTxtField.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            }

            if (address.trim().isEmpty()) {
                allFieldsRequiredLabel.setVisible(true);
                addressTxtField.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            }
            if (postalCode.trim().isEmpty()) {
                allFieldsRequiredLabel.setVisible(true);
                postalCodeTxtField.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            }
            if (phone.trim().isEmpty()) {
                allFieldsRequiredLabel.setVisible(true);
                custPhoneTxtField.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
            }
            if (country == null) {
                allFieldsRequiredLabel.setVisible(true);
                countryComboBox.setStyle("-fx-border-color: #B22222; -fx-focus-color: #B22222;");

            }
            if (division == null) {
                allFieldsRequiredLabel.setVisible(true);
                stateComboBox.setStyle("-fx-border-color: #B22222; -fx-focus-color: #B22222;");
            }
        }
    }
    public void alert(){
        Alert infoRequiredAlert = new Alert(Alert.AlertType.WARNING);
        infoRequiredAlert.setTitle("Information Required");
        infoRequiredAlert.setHeaderText("Please enter all information.  Thank you! ");
        infoRequiredAlert.setContentText("Please enter missing information");
        infoRequiredAlert.showAndWait();
    }

    public void deleteAlert() throws SQLException {
        int customerId = allCustomersTable.getSelectionModel().getSelectedItem().getCustomerId();
        String customerName = allCustomersTable.getSelectionModel().getSelectedItem().getCustomerName();
        Alert deleteAlert = new Alert(Alert.AlertType.WARNING);
        deleteAlert.setTitle("This will permanently delete the customer and ALL customer appointments ");
        deleteAlert.setHeaderText("Are you sure you want to delete customer: " + customerName + "  ID: " + customerId +" ?");
        deleteAlert.setContentText("All customer appointments will also be permanently deleted." );
        //deleteAlert.showAndWait();

        ButtonType yesButton = new ButtonType("Delete Customer");
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

        deleteAlert.getButtonTypes().setAll(yesButton, cancelButton);

        Optional<ButtonType> result = deleteAlert.showAndWait();
        if(result.get() == yesButton)
        {
            AppointmentsImplement.deleteAppointmentByCustomerId(customerId);
            CustomersImplement.deleteCustomer(customerId);
            deleteSuccessfulLabel.setVisible(true);
            deleteCustButton.setDisable(true);
            deleteAlert.close();

        }
        else if(result.get() == cancelButton)
        {
            deleteAlert.close();
        }
    }

    @FXML
    void onActionDeleteCustomer(ActionEvent event) throws SQLException {
        deleteAlert();
    }

    void clearForm(MouseEvent event) {
        custNameTxtField.clear();
        addressTxtField.clear();
        postalCodeTxtField.clear();
        custPhoneTxtField.clear();
        stateComboBox.getSelectionModel().clearSelection();
        countryComboBox.getSelectionModel().clearSelection();
        allCustomersTable.getSelectionModel().clearSelection();
        saveSuccessfulLabel.setVisible(false);
        saveErrorLabel.setVisible(false);
        deleteSuccessfulLabel.setVisible(false);
        allFieldsRequiredLabel.setVisible(false);
        hideLengthAlerts();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("ModifyCustomerController initialized");
        deleteSuccessfulLabel.setVisible(false);
        hideLengthAlerts();

        try {
            getAllCustomers.clear();
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


        allCustomersTable.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (newValue) { //when focused
                saveSuccessfulLabel.setVisible(false);
                saveErrorLabel.setVisible(false);
                saveButton.setDisable(false);
                allFieldsRequiredLabel.setVisible(false);
                deleteSuccessfulLabel.setVisible(false);
                deleteCustButton.setDisable(false);
                saveButton.setDisable(false);
                allCustomersTable.setStyle("-fx-border-color: default; -fx-focus-color: default;");
            }
        });

    }


}
