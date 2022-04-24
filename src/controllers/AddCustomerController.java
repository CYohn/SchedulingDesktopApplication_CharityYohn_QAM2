package controllers;

import Objects.Country;
import Objects.FirstLevelDivision;
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

import java.net.URL;
import java.util.ResourceBundle;

//Idea for future revisions: Add a customer search feature to allow the user to see if a customer already exists

public class AddCustomerController implements Initializable {


    private ObservableList country = FXCollections.observableArrayList();

    @FXML
    private TextField addressTxtField;

    @FXML
    private GridPane applicationFormLeft;

    @FXML
    private Button clearButton;

    @FXML
    private ComboBox<Country> countryComboBox;

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

    @FXML
    void clearForm(MouseEvent event) {
        custNameTxtField.clear();
        addressTxtField.clear();
        postalCodeTxtField.clear();
        custPhoneTxtField.clear();
    }

    @FXML
    void getCountries(ActionEvent event) {
        // This action occurs just before the popup of the Countries combobox is shown
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        String customerName = custNameTxtField.getText();
        String customerCountry;
        String customerDivision;
        String customerStreetAddress = addressTxtField.getText();
        String customerPostalCode = postalCodeTxtField.getText();
        String customerPhone = custPhoneTxtField.getText();

    }
}
