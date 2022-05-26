package controllers;

import Objects.Contact;
import Objects.Customer;
import implementationsDao.ContactsImplement;
import implementationsDao.CustomersImplement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ReportsController implements Initializable {


    public class Month {
        String monthName;
        int monthNumber;
        public Month(int monthNumber, String monthName) {
            this.monthNumber = monthNumber;
            this.monthName = monthName;
        }
        @Override
        public String toString(){
            return (monthNumber + ":   " + monthName);
        }
    }

    ObservableList<Month> months = FXCollections.observableArrayList();
    Month january = new Month(1, "January");
    Month february = new Month(2, "February");
    Month march = new Month(3, "March");
    Month april = new Month(4, "April");
    Month may = new Month(5, "May");
    Month june = new Month(6, "June");
    Month july = new Month(7, "July");
    Month august = new Month(8, "August");
    Month september = new Month(9, "September");
    Month october = new Month(10, "October");
    Month november = new Month(11, "November");
    Month december = new Month(12, "December");

    private ObservableList<String> appointmentTypes = FXCollections.observableArrayList
            ("Planning Session", "Progress Update", "De-Briefing");
    private ObservableList<Contact> contactNames = ContactsImplement.contactNames;

    ObservableList<Customer> allCustomers = CustomersImplement.getAllCustomers;
    public void addAllMonths(){
        months.addAll(january, february, march, april, may, june, july, august, september, october, november, december);
    }

    @FXML
    private TableColumn<?, ?> aptContactColumn2;

    @FXML
    private TableColumn<?, ?> aptContactColumn21;

    @FXML
    private TableColumn<?, ?> aptDescriptioncolumn2;

    @FXML
    private TableColumn<?, ?> aptDescriptioncolumn21;

    @FXML
    private TableColumn<?, ?> aptEndDateColumn2;

    @FXML
    private TableColumn<?, ?> aptEndDateColumn21;

    @FXML
    private TableColumn<?, ?> aptEndTimeColumn2;

    @FXML
    private TableColumn<?, ?> aptEndTimeColumn21;

    @FXML
    private TableColumn<?, ?> aptIdColumn2;

    @FXML
    private TableColumn<?, ?> aptIdColumn21;

    @FXML
    private TableColumn<?, ?> aptLocationColumn2;

    @FXML
    private TableColumn<?, ?> aptLocationColumn21;

    @FXML
    private TableColumn<?, ?> aptStartDateColumn2;

    @FXML
    private TableColumn<?, ?> aptStartDateColumn21;

    @FXML
    private TableColumn<?, ?> aptStartTimeColumn2;

    @FXML
    private TableColumn<?, ?> aptStartTimeColumn21;

    @FXML
    private TableColumn<?, ?> aptTitleColum2;

    @FXML
    private TableColumn<?, ?> aptTitleColum21;

    @FXML
    private TableColumn<?, ?> aptTypeColumn2;

    @FXML
    private TableColumn<?, ?> aptTypeColumn21;

    @FXML
    private TableView<?> contactAppointmentTable;

    @FXML
    private ComboBox<Contact> contactComboBox;

    @FXML
    private TableColumn<?, ?> custIdColumn2;

    @FXML
    private TableColumn<?, ?> custIdColumn21;

    @FXML
    private TableView<?> customerAppointmentTable;

    @FXML
    private ComboBox<Customer> customerComboBox;

    @FXML
    private ComboBox<Month> monthComboBox;

    @FXML
    private TextField numberOfAppointments;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private TableColumn<?, ?> userIdColumn2;

    @FXML
    private TableColumn<?, ?> userIdColumn21;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addAllMonths();
        monthComboBox.setItems(months);
        typeComboBox.setItems(appointmentTypes);
        contactComboBox.setItems(contactNames);
        customerComboBox.setItems(allCustomers);




    }
}
