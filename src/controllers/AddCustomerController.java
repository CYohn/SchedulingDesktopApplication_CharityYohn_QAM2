package controllers;

import Objects.Customer;
import implementationsDao.CountriesImplement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import utilities.DatabaseConnection;


import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import static implementationsDao.CustomersImplement.addCustomer;


//Idea for future revisions: Add a customer search feature to allow the user to see if a customer already exists

/**
 * This class is a controller which controls the add customer page.
 */
public class AddCustomerController implements Initializable {

    /**
     * An observable list of countries. This list is populated by a method in the CountriesImplement file.
     */
    ObservableList<String> divisionIDResult = CountriesImplement.populateCountryNamesList();
    /**
     * An error label informing the user that the entry was not saved. An entry may not save is any field is empty.
     * Other causes of save errors, although less likely could be due to connectivity issues with the database, changes to the database tables, or changes to
     * user account access.
     */
    @FXML
    private Label saveErrorLabel;
    /**
     * A label to inform the user that the entry was saved successfully.
     */
    @FXML
    private Label saveSuccessfulLabel;
    /**
     * An input field for the customer's address
     */
    @FXML
    private TextField addressTxtField;
    /**
     * A button to clear the form.
     */
    @FXML
    private Button clearButton;
    /**
     * A combo box to allow the user to select the customer's country. A selection in this combo box enables the
     * divisions combo box which is filtered based on the country selection.
     */
    @FXML
    private ComboBox<String> countryComboBox;
    /**
     * A test field to input the customer's name.
     */
    @FXML
    private TextField custNameTxtField;
    /**
     * A text field to input the customer's phone
     */
    @FXML
    private TextField custPhoneTxtField;
    /**
     * A text field to input the customer's postal code.
     */
    @FXML
    private TextField postalCodeTxtField;
    /**
     * The save button.
     */
    @FXML
    private Button saveButton;
    /**
     * A combo box for the selection of the customer's division. Initially, the division combo box is disabled. After
     * the user selects a country, the division combo box populates with the appropriate divisions based on the country selection.
     */
    @FXML
    private ComboBox<String> divisionComboBox;
    /**
     * A label to inform the user that the input was too long to conform to the database requirements for field length.
     */
    @FXML
    private Label addressLengthAlert;
    /**
     * A label to inform the user that the input was too long to conform to the database requirements for field length.
     */
    @FXML
    private Label nameLengthAlert;
    /**
     * A label to inform the user that the input was too long to conform to the database requirements for field length.
     */
    @FXML
    private Label phoneLengthAlert;
    /**
     * A label to inform the user that the input was too long to conform to the database requirements for field length.
     */
    @FXML
    private Label postalLengthAlert;
    /**
     * An error label which appears when the user tries to save the form missing a field.
     */
    @FXML
    private Label allFieldsRequiredLabel;

    /**
     * A consrtructor in place to catch exceptions.
     *
     * @throws SQLException An exception that provides information on a database access error or other errors.
     */
    public AddCustomerController() throws SQLException {

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

    /**
     * Gets the division ID from the database
     *
     * @return returns the division ID from the database
     */
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
        divisionComboBox.getSelectionModel().clearSelection();
        countryComboBox.getSelectionModel().clearSelection();
        saveSuccessfulLabel.setVisible(false);
        saveErrorLabel.setVisible(false);
    }

    /**
     * This is the pop-up that appears as an alert when an empty field is found.
     * The validation occurs in emptyFieldAlert()
     */
    public void alert() {
        Alert infoRequiredAlert = new Alert(Alert.AlertType.WARNING);
        infoRequiredAlert.setTitle("Information Required");
        infoRequiredAlert.setHeaderText("Please enter all information.  Thank you! ");
        infoRequiredAlert.setContentText("Please enter missing information");
        infoRequiredAlert.showAndWait();
    }

    /**
     * A pop-up for the length alert
     */
    public void lengthAlert() {
        Alert lengthAlert = new Alert(Alert.AlertType.WARNING);
        lengthAlert.setTitle("Too Many Characters in the Field");
        lengthAlert.setHeaderText("Please adjust the length of your entry");
        lengthAlert.setContentText("Thank you");
        lengthAlert.showAndWait();
    }

    /**
     * This method checks for empty fields in the form.
     * If any empty fields are found it triggers a pop-up alert and outlines the empty field in a red border
     */
    public void emptyFieldAlert() {
        String name = custNameTxtField.getText();
        String address = addressTxtField.getText();
        String postalCode = postalCodeTxtField.getText();
        String phone = custPhoneTxtField.getText();
        String country = countryComboBox.getValue();
        String division = divisionComboBox.getValue();

        if (name.trim().isEmpty() || address.trim().isEmpty() || postalCode.trim().isEmpty()
                || phone.trim().isEmpty() || country == null || division == null){
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

    /**
     * Checks that the length of the text entries is under the limits imposed by the database
     */
    public void fieldLengthAlert() {
        if (custNameTxtField.getLength() > 50) {
            nameLengthAlert.setVisible(true);
        }
        if (addressTxtField.getLength() > 100) {
            addressLengthAlert.setVisible(true);
        }
        if (postalCodeTxtField.getLength() > 50) {
            postalLengthAlert.setVisible(true);
        }
        if (custPhoneTxtField.getLength() > 50) {
            phoneLengthAlert.setVisible(true);
        }
    }

    /**
     * Hised all length alerts
     */
    public void hideLengthAlerts() {
        addressLengthAlert.setVisible(false);
        nameLengthAlert.setVisible(false);
        phoneLengthAlert.setVisible(false);
        postalLengthAlert.setVisible(false);
        allFieldsRequiredLabel.setVisible(false);
    }

    /**
     * This is the save procedure.
     * The method first checks for empty fields. If validation passes, it creates a customer object and send the customer to
     * CustomerImplement.addCustomer(customerToSave). Then the method checks for the database response. If the response is
     * affirmative, a success label is shown to the user.
     *
     * @param event When the user clicks the save button
     * @throws IOException  May throw an IOException because the method take form input
     * @throws SQLException May throw an SQL exception because the method calls another method which saves the customer to the database
     */
    @FXML
    void onSave(MouseEvent event) throws IOException, SQLException {
        fieldLengthAlert();
        emptyFieldAlert();

        if ((!custNameTxtField.getText().isEmpty()) && (custNameTxtField.getLength() < 50) &&
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

            System.out.println("Printing out customers to save list (from the customers controller onSave method):");

            saveButton.setDisable(true);

            int databaseResponseToAddCustomer = addCustomer(customerToSave);
            System.out.println("databaseResponseToAddCustomer: " + databaseResponseToAddCustomer);
            if (databaseResponseToAddCustomer == 1){
                saveSuccessfulLabel.setVisible(true);
                System.out.println("Database response to adding the customer: " + databaseResponseToAddCustomer);

            } else {
                saveErrorLabel.setVisible(true);
                System.out.println("Customer not added");
            }
        }
    }


    /**
     * Initializes the page
     *
     * @param url
     * @param resourceBundle
     */
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
        /**
         * Change listener to validate the length of text entries in real time.
         * Triggers an error label if the text entry is longer than the length allowed in the database
         */
        custNameTxtField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                saveSuccessfulLabel.setVisible(false);
                saveErrorLabel.setVisible(false);
                saveButton.setDisable(false);
                allFieldsRequiredLabel.setVisible(false);
                //custNameTxtField.setStyle("-fx-border-color: default; -fx-focus-color: default;");
                if (custNameTxtField.getLength() > 50) {
                    nameLengthAlert.setVisible(true);
                } else if (custNameTxtField.getLength() < 50) {
                    nameLengthAlert.setVisible(false);
                }
            }
        });
        /**
         * Change listener to validate the length of text entries in real time.
         * Triggers an error label if the text entry is longer than the length allowed in the database
         */
        addressTxtField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                saveSuccessfulLabel.setVisible(false);
                saveErrorLabel.setVisible(false);
                saveButton.setDisable(false);
                allFieldsRequiredLabel.setVisible(false);
                //addressTxtField.setStyle("-fx-border-color: default; -fx-focus-color: default;");
                if (addressTxtField.getLength() > 100) {
                    addressLengthAlert.setVisible(true);
                } else if (addressTxtField.getLength() < 100) {
                    addressLengthAlert.setVisible(false);
                }
            }
        });

        /**
         * Change listener to validate the length of text entries in real time.
         * Triggers an error label if the text entry is longer than the length allowed in the database
         */
        postalCodeTxtField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                saveSuccessfulLabel.setVisible(false);
                saveErrorLabel.setVisible(false);
                saveButton.setDisable(false);
                allFieldsRequiredLabel.setVisible(false);
                //postalCodeTxtField.setStyle("-fx-border-color: default; -fx-focus-color: default;");
                if (postalCodeTxtField.getLength() > 50) {
                    postalLengthAlert.setVisible(true);
                } else if (postalCodeTxtField.getLength() < 50) {
                    postalLengthAlert.setVisible(false);
                }
            }
        });

        /**
         * Change listener to validate the length of text entries in real time.
         * Triggers an error label if the text entry is longer than the length allowed in the database
         */
        custPhoneTxtField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                saveSuccessfulLabel.setVisible(false);
                saveErrorLabel.setVisible(false);
                saveButton.setDisable(false);
                allFieldsRequiredLabel.setVisible(false);
                //custPhoneTxtField.setStyle("-fx-border-color: default; -fx-focus-color: default;");
                if (custPhoneTxtField.getLength() > 50) {
                    phoneLengthAlert.setVisible(true);
                } else if (custPhoneTxtField.getLength() < 50) {
                    phoneLengthAlert.setVisible(false);
                }
            }
        });
        /**
         * Listener for the country combo box.
         * Resets the error labels if the user enters a new value
         */
        countryComboBox.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                saveSuccessfulLabel.setVisible(false);
                saveErrorLabel.setVisible(false);
                saveButton.setDisable(false);
                allFieldsRequiredLabel.setVisible(false);
                //countryComboBox.setStyle("-fx-border-color: default; -fx-focus-color: default;");
            }
        });
        /**
         * Listener for the division combo box.
         * Resets the error labels if the user enters a new value
         */
        divisionComboBox.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                saveSuccessfulLabel.setVisible(false);
                saveErrorLabel.setVisible(false);
                saveButton.setDisable(false);
                allFieldsRequiredLabel.setVisible(false);
                //divisionComboBox.setStyle("-fx-border-color: default; -fx-focus-color: default;");
            }
        });

    }
}
