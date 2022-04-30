package controllers;

import Objects.Country;
import Objects.Customer;
import Objects.FirstLevelDivision;
import implementationsDao.CountriesImplement;
import interfacesDao.FirstLevelDivisionsInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

//Idea for future revisions: Add a customer search feature to allow the user to see if a customer already exists

public class AddCustomerController implements Initializable {

    ObservableList<Country> allCountries = CountriesImplement.populateCountriesList();
    ObservableList<String> countryNames = CountriesImplement.populateCountryNamesList();



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
    private ComboBox<FirstLevelDivision> stateComboBox;

    public AddCustomerController() throws SQLException {
    }

    @FXML
    void clearForm(MouseEvent event) {
        custNameTxtField.clear();
        addressTxtField.clear();
        postalCodeTxtField.clear();
        custPhoneTxtField.clear();
        countryComboBox.setPromptText("Country");
        stateComboBox.setPromptText("State/Province");
    }




    //int getDivisionID(){return divisionID;}

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Printing the observable list of names from the AddCustomer controller: " + countryNames);
        countryComboBox.setItems(countryNames);

        String customerName = custNameTxtField.getText();
        String customerDivision = stateComboBox.getItems().toString();
        String customerStreetAddress = addressTxtField.getText();
        String customerPostalCode = postalCodeTxtField.getText();
        String customerPhone = custPhoneTxtField.getText();
        String customerAddress = customerStreetAddress + customerPostalCode + customerDivision;



    }
}
