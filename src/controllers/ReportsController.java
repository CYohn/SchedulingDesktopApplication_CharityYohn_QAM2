package controllers;

import Objects.Appointment;
import Objects.Contact;
import Objects.Customer;
import implementationsDao.AppointmentsImplement;
import implementationsDao.ContactsImplement;
import implementationsDao.CustomersImplement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;

import static utilities.TimezoneConversion.convertUTCToUserTime;

/**
 * This class is a controller which controls the reports page.
 */
public class ReportsController implements Initializable {

    /**
     * Gets all appointments from the database
     */
    public ObservableList<Appointment> allAppointments = AppointmentsImplement.getAllAppointments;
    /**
     * An observable list of months
     */
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
    /**
     * Gets all customers from the database
     */
    ObservableList<Customer> allCustomers = CustomersImplement.getAllCustomers;
    /**
     * The observable list of appointment types
     */
    private ObservableList<String> appointmentTypes = FXCollections.observableArrayList
            ("Planning Session", "Progress Update", "De-Briefing");
    /**
     * Gets contacts from the database
     */
    private ObservableList<Contact> contactNames = ContactsImplement.contactNames;
    /**
     * The contact column of the top appointments table.
     */
    @FXML
    private TableColumn<Appointment, Integer> aptContactColumn2;
    /**
     * The contact colun of the second appointment table.
     */
    @FXML
    private TableColumn<Appointment, Integer> aptContactColumn21;
    /**
     * The description column of the first appointments table.
     */
    @FXML
    private TableColumn<Appointment, String> aptDescriptioncolumn2;
    /**
     * The description column of the second appointments table.
     */
    @FXML
    private TableColumn<Appointment, String> aptDescriptioncolumn21;
    /**
     * The end date column of the first appointment table.
     */
    @FXML
    private TableColumn<Appointment, LocalDate> aptEndDateColumn2;
    /**
     * the end date column of the second appointment table.
     */
    @FXML
    private TableColumn<Appointment, LocalDate> aptEndDateColumn21;
    /**
     * the end time column of the first table.
     */
    @FXML
    private TableColumn<Appointment, LocalTime> aptEndTimeColumn2;
    /**
     * the end time column of the second table.
     */
    @FXML
    private TableColumn<Appointment, LocalTime> aptEndTimeColumn21;
    /**
     * The id column of the first table.
     */
    @FXML
    private TableColumn<Appointment, Integer> aptIdColumn2;
    /**
     * the id column of the second table.
     */
    @FXML
    private TableColumn<Appointment, Integer> aptIdColumn21;
    /**
     * the location column of the first table.
     */
    @FXML
    private TableColumn<Appointment, String> aptLocationColumn2;
    /**
     * The location column of the second table.
     */
    @FXML
    private TableColumn<Appointment, String> aptLocationColumn21;
    /**
     * The start date column of the first table.
     */
    @FXML
    private TableColumn<Appointment, LocalDate> aptStartDateColumn2;
    /**
     * The start date column of the second table.
     */
    @FXML
    private TableColumn<Appointment, LocalDate> aptStartDateColumn21;
    /**
     * The start time column of the first table.
     */
    @FXML
    private TableColumn<Appointment, LocalTime> aptStartTimeColumn2;
    /**
     * The start time column of the second table.
     */
    @FXML
    private TableColumn<Appointment, LocalTime> aptStartTimeColumn21;
    /**
     * the title column of the first table.
     */
    @FXML
    private TableColumn<Appointment, String> aptTitleColum2;
    /**
     * The title column of the second table.
     */
    @FXML
    private TableColumn<Appointment, String> aptTitleColum21;
    /**
     * The type column of the first table.
     */
    @FXML
    private TableColumn<Appointment, String> aptTypeColumn2;
    /**
     * The type column of the second table.
     */
    @FXML
    private TableColumn<Appointment, String> aptTypeColumn21;

    /**
     * Adds all the months to an observable list
     */
    public void addAllMonths() {
        months.addAll(january, february, march, april, may, june, july, august, september, october, november, december);
    }

    /**
     * The label to show the number of appointments based on the search criteria of the Month / Type / Month and Type combination.
     */
    @FXML
    private Label numberOfAppointments;

    /**
     * The table to sort appointments by contact.
     */
    @FXML
    private TableView<Appointment> contactAppointmentTable;
    /**
     * The combo box to select a customer by which to sort the appointments.
     */
    @FXML
    private ComboBox<Customer> customerComboBox;
    /**
     * The combo box to select the contact by which to sort the appointments.
     */
    @FXML
    private ComboBox<Contact> contactComboBox;
    /**
     * The ID column of the first table.
     */
    @FXML
    private TableColumn<?, ?> custIdColumn2;
    /**
     * The id column of the second table.
     */
    @FXML
    private TableColumn<?, ?> custIdColumn21;
    /**
     * The appointments table which shows the result for the customer filter.
     */
    @FXML
    private TableView<Appointment> customerAppointmentTable;
    /**
     * The combo box to check the month.
     */
    @FXML
    private ComboBox<Month> monthComboBox;

    /**
     * Creates an inner class to list and work with the months of the year
     */
    public class Month {
        /**
         * The name of the month
         */
        private String monthName;
        /**
         * The number of the month in the year.
         */
        private int monthNumber;

        /**
         * The constructor for the month object.
         *
         * @param monthNumber The number of the month in the year.
         * @param monthName   The name of the month.
         */
        public Month(int monthNumber, String monthName) {
            this.monthNumber = monthNumber;
            this.monthName = monthName;
        }

        /**
         * Creates a human friendly string representation of the month object
         *
         * @return A string representing the month
         */
        @Override
        public String toString() {
            return (monthNumber + ":   " + monthName);
        }

        /**
         * Gets the assigned number of the month (ex: January == 1, Februrary == 2 etc.)
         *
         * @return the month number
         */
        public int getMonthNumber() {
            return monthNumber;
        }

    }

    /**
     * The combo box to select the type.
     */
    @FXML
    private ComboBox<String> typeComboBox;
    /**
     * the user ID column of the first table.
     */
    @FXML
    private TableColumn<?, ?> userIdColumn2;
    /**
     * The user ID column of the second table.
     */
    @FXML
    private TableColumn<?, ?> userIdColumn21;


    /**
     * Checks which combo box is selected and modifies the search based on the selection.
     * for example if the user on selects the number of appointments in a month the result is for only the month. If the
     * user selects the number of appointments by type only, the result is appointments by type, and if the user makes a
     * selection in both boxes the results are bother parameters.
     *
     * @return Returns the number of appointments based on the user selection
     * @Lambda The lambdas filter the all appointments, checking against the selected month number to type, or both
     */
    public Integer appointmentsCount() {

        String selectedType;

        if ((!monthComboBox.getSelectionModel().isEmpty()) && (typeComboBox.getSelectionModel().isEmpty())) {

            int selectedMonthNumber = monthComboBox.getSelectionModel().getSelectedItem().getMonthNumber();

            Integer numberOfAptInMonth = (int) allAppointments.stream()
                    .filter(apt -> apt.getStartDateTime().getMonthValue() == selectedMonthNumber).count();
            System.out.println("Number of Apt in Month  " + numberOfAptInMonth);
            return numberOfAptInMonth;
        }
        if ((monthComboBox.getSelectionModel().isEmpty()) && (!typeComboBox.getSelectionModel().isEmpty())) {

            selectedType = typeComboBox.getSelectionModel().getSelectedItem();

            Integer numberOfAptOfType = (int) allAppointments.stream()
                    .filter(apt -> apt.getType().equalsIgnoreCase(selectedType)).count();
            System.out.println("Number of Apt Type  "+ numberOfAptOfType);
            return  numberOfAptOfType;
        }
        if((!monthComboBox.getSelectionModel().isEmpty()) && (!typeComboBox.getSelectionModel().isEmpty())){

            selectedType = typeComboBox.getSelectionModel().getSelectedItem();
            int selectedMonthNumber = monthComboBox.getSelectionModel().getSelectedItem().getMonthNumber();
            Integer numberOfAptWithBothConstraints = (int) allAppointments.stream()
                    .filter(apt -> apt.getStartDateTime().getMonthValue() == selectedMonthNumber)
                    .filter(apt -> apt.getType().equalsIgnoreCase(selectedType)).count();
            System.out.println("Number of Apt With both constraints  " + numberOfAptWithBothConstraints);
            return numberOfAptWithBothConstraints;
        }
        return 0;
    }


    /**
     * Initializes the page
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addAllMonths();

        try {
            AppointmentsImplement.getAllAppointments();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("all appointments:  " + allAppointments);
        for(Appointment appointment: allAppointments){
            System.out.println("All Appointments at initialize:  contact ID: "+ appointment.getContactId() + "  Appointment ID  " + appointment.getAppointmentId());}


        monthComboBox.setItems(months);
        typeComboBox.setItems(appointmentTypes);
        contactComboBox.setItems(contactNames);
        customerComboBox.setItems(allCustomers);
/**
 * Listener for the contact combobox.
 * @ Lambda filters the all appointments based on the selected contact ID
 */
        contactComboBox.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus is lost
                try {
                    AppointmentsImplement.getAllAppointments();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                int selectedContactId = contactComboBox.getSelectionModel().getSelectedItem().getContactId();
                System.out.println(selectedContactId);

                ObservableList<Appointment> appointmentsWithConvertedTimes = FXCollections.observableArrayList();
                ObservableList<Appointment> filteredByContact = allAppointments.filtered(appointment ->
                {
                    if (appointment.getContactId() == selectedContactId) {
                        return true;
                    }
                    return false;
                });

                System.out.println("filteredByContact list:  " + filteredByContact);
/**
 * Populates the appointments table with appointments filtered by contact
 */
                for (Appointment appointment : filteredByContact) {

                    //System.out.println("Appointment from the populate apt Table: " + appointment);
                    LocalDateTime startUTC = appointment.getStartDateTime();
                    LocalDateTime endUTC = appointment.getEndDateTime();
                    LocalDate startDate = convertUTCToUserTime(startUTC).toLocalDate();
                    LocalTime startTime = convertUTCToUserTime(startUTC).toLocalTime();
                    LocalDate endDate = convertUTCToUserTime(endUTC).toLocalDate();
                    LocalTime endTime = convertUTCToUserTime(endUTC).toLocalTime();

                    int appointmentId = appointment.getAppointmentId();
                    String title = appointment.getTitle();
                    String description = appointment.getDescription();
                    String location = appointment.getLocation();
                    String type = appointment.getType();
                    int customerId = appointment.getCustomerId();
                    int userId = appointment.getUserId();
                    int contactId = appointment.getContactId();

                    Appointment convertedTimesAppointment = new Appointment
                            (appointmentId, title, description, location, type, startDate, startTime,
                                    endDate, endTime, customerId, userId, contactId);

                    appointmentsWithConvertedTimes.add(convertedTimesAppointment);
                }

                contactAppointmentTable.setItems(appointmentsWithConvertedTimes);

                aptContactColumn2.setCellValueFactory(new PropertyValueFactory<>("contactId"));
                aptDescriptioncolumn2.setCellValueFactory(new PropertyValueFactory<>("description"));
                aptEndDateColumn2.setCellValueFactory(new PropertyValueFactory<>("endDate"));
                aptEndTimeColumn2.setCellValueFactory(new PropertyValueFactory<>("endTime"));
                aptIdColumn2.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
                aptLocationColumn2.setCellValueFactory(new PropertyValueFactory<>("location"));
                aptStartDateColumn2.setCellValueFactory(new PropertyValueFactory<>("startDate"));
                aptStartTimeColumn2.setCellValueFactory(new PropertyValueFactory<>("startTime"));
                aptTitleColum2.setCellValueFactory(new PropertyValueFactory<>("title"));
                aptTypeColumn2.setCellValueFactory(new PropertyValueFactory<>("type"));
                custIdColumn2.setCellValueFactory(new PropertyValueFactory<>("customerId"));
                userIdColumn2.setCellValueFactory(new PropertyValueFactory<>("userId"));
            }
        });

/**
 * @Lambda This listener has two lambda expressions. the first collects the argument, previous value, and newValue of the target.
 * It then listens for a new value and when focus is lost on the target, it commences with the rest of the operations.
 * the second lambda is noted below.
 */
        customerComboBox.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus is lost
                try {
                    AppointmentsImplement.getAllAppointments();
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                int selectedCustomerId = customerComboBox.getSelectionModel().getSelectedItem().getCustomerId();
                System.out.println();

                ObservableList<Appointment> appointmentsWithConvertedTimes2 = FXCollections.observableArrayList();
                /**
                 * @Lambda The lambda takes an appointment from the allAppointments list and compares the customer
                 * ID to the selected customer ID. It then returns true if a match is found and adds the appointment to
                 * the list filteredByCustomerID
                 */
                ObservableList<Appointment> filteredByCustomerID = allAppointments.filtered(appointment ->
                {
                    if (appointment.getCustomerId() == selectedCustomerId) {
                        return true;
                    }
                    return false;
                });

                /**
                 * Populates the table filtered by customer
                 */
                System.out.println("filteredByCustomer list:  " + filteredByCustomerID);

                for (Appointment appointment : filteredByCustomerID) {

                    //System.out.println("Appointment from the populate apt Table: " + appointment);
                    LocalDateTime startUTC = appointment.getStartDateTime();
                    LocalDateTime endUTC = appointment.getEndDateTime();
                    LocalDate startDate = convertUTCToUserTime(startUTC).toLocalDate();
                    LocalTime startTime = convertUTCToUserTime(startUTC).toLocalTime();
                    LocalDate endDate = convertUTCToUserTime(endUTC).toLocalDate();
                    LocalTime endTime = convertUTCToUserTime(endUTC).toLocalTime();

                    int appointmentId = appointment.getAppointmentId();
                    String title = appointment.getTitle();
                    String description = appointment.getDescription();
                    String location = appointment.getLocation();
                    String type = appointment.getType();
                    int customerId = appointment.getCustomerId();
                    int userId = appointment.getUserId();
                    int contactId = appointment.getContactId();

                    Appointment convertedTimesAppointment2 = new Appointment
                            (appointmentId, title, description, location, type, startDate, startTime,
                                    endDate, endTime, customerId, userId, contactId);

                    appointmentsWithConvertedTimes2.add(convertedTimesAppointment2);
                }

                customerAppointmentTable.setItems(appointmentsWithConvertedTimes2);

                aptContactColumn21.setCellValueFactory(new PropertyValueFactory<>("contactId"));
                aptDescriptioncolumn21.setCellValueFactory(new PropertyValueFactory<>("description"));
                aptEndDateColumn21.setCellValueFactory(new PropertyValueFactory<>("endDate"));
                aptEndTimeColumn21.setCellValueFactory(new PropertyValueFactory<>("endTime"));
                aptIdColumn21.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
                aptLocationColumn21.setCellValueFactory(new PropertyValueFactory<>("location"));
                aptStartDateColumn21.setCellValueFactory(new PropertyValueFactory<>("startDate"));
                aptStartTimeColumn21.setCellValueFactory(new PropertyValueFactory<>("startTime"));
                aptTitleColum21.setCellValueFactory(new PropertyValueFactory<>("title"));
                aptTypeColumn21.setCellValueFactory(new PropertyValueFactory<>("type"));
                custIdColumn21.setCellValueFactory(new PropertyValueFactory<>("customerId"));
                userIdColumn21.setCellValueFactory(new PropertyValueFactory<>("userId"));
            }
        });

        /**
         * @Lambda The listener listens for a new argument and sets the text based on the appointments count
         */
        monthComboBox.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                appointmentsCount();
                numberOfAppointments.setText(appointmentsCount().toString());
            }
        });

        typeComboBox.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                appointmentsCount();
                numberOfAppointments.setText(appointmentsCount().toString()) ;
            }
        });


    }
}
