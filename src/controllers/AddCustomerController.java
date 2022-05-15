package controllers;

import Objects.Country;
import Objects.Customer;
import implementationsDao.CountriesImplement;
import implementationsDao.CustomersImplement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import utilities.DatabaseConnection;


import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static implementationsDao.CustomersImplement.addCustomer;

//Idea for future revisions: Add a customer search feature to allow the user to see if a customer already exists

public class AddCustomerController implements Initializable {




    public AddCustomerController() throws SQLException {

    }

    ObservableList<Country> allCountries = CountriesImplement.populateCountriesList();
    ObservableList<String> divisionIDResult = CountriesImplement.populateCountryNamesList();

    @FXML
    private Label saveErrorLabel;

    @FXML
    private Label saveSuccessfulLabel;

    @FXML
    private TextField addressTxtField;

    @FXML
    private GridPane applicationFormLeft;

    @FXML
    private Button clearButton;

    @FXML
    private ComboBox<String> countryComboBox;

    @FXML
    private TextField custNameTxtField;

    @FXML
    private TextField custPhoneTxtField;

    @FXML
    private TextField postalCodeTxtField;

    @FXML
    private Button saveButton;

    @FXML
    private ComboBox<String> divisionComboBox;

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
        divisionComboBox.setDisable(false); //Enable the divisions ComboBox
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
                        divisionComboBox.setItems(divisionNames); //Populate the ComboBox
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


        public int getDivisionID() {
            PreparedStatement findDivisionIDPreparedStatement;
            String selectedDivision = divisionComboBox.getValue(); //Get the User's division selection
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

    /**
     * Clears the Add Customer form.
     * @param event User presses the clear button.
     */
    @FXML
    void clearForm(MouseEvent event) {
        custNameTxtField.clear();
        addressTxtField.clear();
        postalCodeTxtField.clear();
        custPhoneTxtField.clear();
        countryComboBox.setValue(null);
        divisionComboBox.setValue(null);
        saveSuccessfulLabel.setVisible(false);
        saveErrorLabel.setVisible(false);
    }

    public void alert(){
        Alert infoRequiredAlert = new Alert(Alert.AlertType.WARNING);
        infoRequiredAlert.setTitle("Information Required");
        infoRequiredAlert.setHeaderText("Please enter all information.  Thank you! ");
        infoRequiredAlert.setContentText("Please enter missing information");
        infoRequiredAlert.showAndWait();
    }

    public void lengthAlert(){
        Alert lengthAlert = new Alert(Alert.AlertType.WARNING);
        lengthAlert.setTitle("Too Many Characters in the Field");
        lengthAlert.setHeaderText("Please adjust the length of your entry");
        lengthAlert.setContentText("Thank you");
        lengthAlert.showAndWait();
    }

    public void emptyFieldAlert() {
        String name = custNameTxtField.getText();
        String address = addressTxtField.getText();
        String postalCode = postalCodeTxtField.getText();
        String phone = custPhoneTxtField.getText();
        String country = countryComboBox.getValue();
        String division = divisionComboBox.getValue();

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
            divisionComboBox.setStyle("-fx-border-color: #B22222; -fx-focus-color: #B22222;");
        }
        }
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

    @FXML
    void onSave(MouseEvent event) throws IOException, SQLException {
        fieldLengthAlert();
        emptyFieldAlert();

        if((!custNameTxtField.getText().isEmpty()) && (custNameTxtField.getLength() < 50) &&
        (!addressTxtField.getText().isEmpty()) && (addressTxtField.getLength() < 100) &&
        (!postalCodeTxtField.getText().isEmpty()) && (postalCodeTxtField.getLength() < 50) &&
        (!custPhoneTxtField.getText().isEmpty()) && (custPhoneTxtField.getLength() < 50) &&
        (countryComboBox.getValue() != null) &&
        (divisionComboBox.getValue() != null)) {

            String customerName = custNameTxtField.getText();
            String customerAddress = addressTxtField.getText();
            String customerPostalCode = postalCodeTxtField.getText();
            String customerPhone = custPhoneTxtField.getText();
            String customerCountry = countryComboBox.getValue();
            int customerDivisionId = getDivisionID();

            Customer customerToSave = new Customer(customerName,
                    customerAddress, customerPostalCode, customerPhone,customerCountry, customerDivisionId);
            CustomersImplement.customersToSave.add(customerToSave);

            System.out.println("Printing out customers to save list (from the customers controller onSave method):");
            addCustomer(CustomersImplement.customersToSave);
            getAddCustomerResponse();

            saveButton.setDisable(true);

        }
    }

    public void getAddCustomerResponse() throws SQLException {
        int databaseResponseToAddCustomer = addCustomer(CustomersImplement.customersToSave);
        System.out.println("databaseResponseToAddCustomer: " + databaseResponseToAddCustomer);
        if (databaseResponseToAddCustomer == 1){
            saveSuccessfulLabel.setVisible(true);
            System.out.println("Database response to adding the customer: " + databaseResponseToAddCustomer);

        }
        else {
            saveErrorLabel.setVisible(true);
            System.out.println("Customer not added");}

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Printing the observable list of names from the AddCustomer controller: " + divisionIDResult);
        countryComboBox.setItems(divisionIDResult);
        divisionComboBox.setDisable(true);
        saveErrorLabel.setVisible(false);
        saveSuccessfulLabel.setVisible(false);
        saveButton.setDisable(false);

        hideLengthAlerts();



        //Change listeners to validate text fields and warn the user in real time if input is over the allowed length
        custNameTxtField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                saveSuccessfulLabel.setVisible(false);
                saveErrorLabel.setVisible(false);
                saveButton.setDisable(false);
                allFieldsRequiredLabel.setVisible(false);
                custNameTxtField.setStyle("-fx-border-color: default; -fx-focus-color: default;");
                if(custNameTxtField.getLength() > 50) {
                    nameLengthAlert.setVisible(true);}
                else if(custNameTxtField.getLength() < 50){nameLengthAlert.setVisible(false);}
                }
        });

        addressTxtField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                saveSuccessfulLabel.setVisible(false);
                saveErrorLabel.setVisible(false);
                saveButton.setDisable(false);
                allFieldsRequiredLabel.setVisible(false);
                addressTxtField.setStyle("-fx-border-color: default; -fx-focus-color: default;");
                if(addressTxtField.getLength() > 100) {
                    addressLengthAlert.setVisible(true);}
                else if(addressTxtField.getLength() < 100){addressLengthAlert.setVisible(false);}
            }
        });

        postalCodeTxtField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                saveSuccessfulLabel.setVisible(false);
                saveErrorLabel.setVisible(false);
                saveButton.setDisable(false);
                allFieldsRequiredLabel.setVisible(false);
                custNameTxtField.setStyle("-fx-border-color: default; -fx-focus-color: default;");
                if(postalCodeTxtField.getLength() > 50) {
                    postalLengthAlert.setVisible(true);}
                else if(postalCodeTxtField.getLength() < 50){postalLengthAlert.setVisible(false);}
            }
        });

        custPhoneTxtField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                saveSuccessfulLabel.setVisible(false);
                saveErrorLabel.setVisible(false);
                saveButton.setDisable(false);
                allFieldsRequiredLabel.setVisible(false);
                custNameTxtField.setStyle("-fx-border-color: default; -fx-focus-color: default;");
                if(custPhoneTxtField.getLength() > 50) {
                    phoneLengthAlert.setVisible(true);}
                else if(custPhoneTxtField.getLength() < 50){phoneLengthAlert.setVisible(false);}
            }
        });

        countryComboBox.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                saveSuccessfulLabel.setVisible(false);
                saveErrorLabel.setVisible(false);
                saveButton.setDisable(false);
                allFieldsRequiredLabel.setVisible(false);
                countryComboBox.setStyle("-fx-border-color: default; -fx-focus-color: default;");
            }
        });

        divisionComboBox.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                saveSuccessfulLabel.setVisible(false);
                saveErrorLabel.setVisible(false);
                saveButton.setDisable(false);
                allFieldsRequiredLabel.setVisible(false);
                divisionComboBox.setStyle("-fx-border-color: default; -fx-focus-color: default;");
            }
        });

    }
}
