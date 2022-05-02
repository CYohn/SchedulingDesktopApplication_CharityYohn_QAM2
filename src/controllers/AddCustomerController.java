package controllers;

import Objects.Country;
import implementationsDao.CountriesImplement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import utilities.DatabaseConnection;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Collections;
import java.util.ResourceBundle;

//Idea for future revisions: Add a customer search feature to allow the user to see if a customer already exists

public class AddCustomerController implements Initializable {




    public AddCustomerController() throws SQLException {

    }

    ObservableList<Country> allCountries = CountriesImplement.populateCountriesList();
    ObservableList<String> countryNames = CountriesImplement.populateCountryNamesList();
  //  ObservableList<String> selectedDivisions = DivisionSortsAndSearches.populateDivisionNamesList();


    @FXML
    private TextField addressTxtField;

    @FXML
    private GridPane applicationFormLeft;

    @FXML
    private Button clearButton;

    @FXML
    private ComboBox<String> countryComboBox;
    //public static String getCountrySelection(){return countryComboBox.getValue();}


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

    @FXML
    void clearForm(MouseEvent event) {
        custNameTxtField.clear();
        addressTxtField.clear();
        postalCodeTxtField.clear();
        custPhoneTxtField.clear();
        countryComboBox.setValue(null);
        divisionComboBox.setValue(null);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Printing the observable list of names from the AddCustomer controller: " + countryNames);
        countryComboBox.setItems(countryNames);
        divisionComboBox.setDisable(true);



        String customerName = custNameTxtField.getText();
        String customerDivision = divisionComboBox.getValue();
        String customerStreetAddress = addressTxtField.getText();
        String customerPostalCode = postalCodeTxtField.getText();
        String customerPhone = custPhoneTxtField.getText();
        String customerAddress = customerStreetAddress + customerPostalCode + customerDivision;



    }
}
