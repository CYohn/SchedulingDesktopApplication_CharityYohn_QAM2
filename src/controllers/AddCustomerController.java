package controllers;

import Objects.Country;
import Objects.Customer;
import Objects.FirstLevelDivision;
import implementationsDao.CountriesImplement;
import implementationsDao.FirstLevelDivisionImplement;
import interfacesDao.FirstLevelDivisionsInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.ResourceBundle;

//Idea for future revisions: Add a customer search feature to allow the user to see if a customer already exists

public class AddCustomerController implements Initializable {
    public AddCustomerController() throws SQLException {
    }

    ObservableList<Country> allCountries = CountriesImplement.populateCountriesList();
    ObservableList<String> countryNames = CountriesImplement.populateCountryNamesList();
    ObservableList<FirstLevelDivision> allDivisions = FirstLevelDivisionImplement.populateDivisionsList();

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
    private ComboBox<FirstLevelDivision> divisionComboBox;

    @FXML
    void populateDivisionComboBox(ActionEvent event) throws SQLException {

    ObservableList<Country> allCountries = CountriesImplement.populateCountriesList();
    ObservableList<FirstLevelDivision> allDivisions = FirstLevelDivisionImplement.populateDivisionsList();
    String countryName = countryComboBox.getValue();

    }

    @FXML
    void clearForm(MouseEvent event) {
        custNameTxtField.clear();
        addressTxtField.clear();
        postalCodeTxtField.clear();
        custPhoneTxtField.clear();
        countryComboBox.setPromptText("Country");
        divisionComboBox.setPromptText("State/Province");
    }




    //int getDivisionID(){return divisionID;}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Printing the observable list of names from the AddCustomer controller: " + countryNames);
        countryComboBox.setItems(countryNames);


        String customerName = custNameTxtField.getText();
        String customerDivision = divisionComboBox.getItems().toString();
        String customerStreetAddress = addressTxtField.getText();
        String customerPostalCode = postalCodeTxtField.getText();
        String customerPhone = custPhoneTxtField.getText();
        String customerAddress = customerStreetAddress + customerPostalCode + customerDivision;



    }
}
