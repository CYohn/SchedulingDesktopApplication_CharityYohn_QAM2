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

public class ReportsController implements Initializable {

    /**
     * Gets all appointments from the database
     */
    public ObservableList<Appointment> allAppointments = AppointmentsImplement.getAllAppointments;
    /**
     * Creates the month objects
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
    @FXML
    private TableColumn<Appointment, Integer> aptContactColumn2;
    @FXML
    private TableColumn<Appointment, Integer> aptContactColumn21;
    @FXML
    private TableColumn<Appointment, String> aptDescriptioncolumn2;
    @FXML
    private TableColumn<Appointment, String> aptDescriptioncolumn21;
    @FXML
    private TableColumn<Appointment, LocalDate> aptEndDateColumn2;
    @FXML
    private TableColumn<Appointment, LocalDate> aptEndDateColumn21;
    @FXML
    private TableColumn<Appointment, LocalTime> aptEndTimeColumn2;
    @FXML
    private TableColumn<Appointment, LocalTime> aptEndTimeColumn21;
    @FXML
    private TableColumn<Appointment, Integer> aptIdColumn2;
    @FXML
    private TableColumn<Appointment, Integer> aptIdColumn21;
    @FXML
    private TableColumn<Appointment, String> aptLocationColumn2;
    @FXML
    private TableColumn<Appointment, String> aptLocationColumn21;
    @FXML
    private TableColumn<Appointment, LocalDate> aptStartDateColumn2;
    @FXML
    private TableColumn<Appointment, LocalDate> aptStartDateColumn21;
    @FXML
    private TableColumn<Appointment, LocalTime> aptStartTimeColumn2;
    @FXML
    private TableColumn<Appointment, LocalTime> aptStartTimeColumn21;
    @FXML
    private TableColumn<Appointment, String> aptTitleColum2;
    @FXML
    private TableColumn<Appointment, String> aptTitleColum21;
    @FXML
    private TableColumn<Appointment, String> aptTypeColumn2;
    @FXML
    private TableColumn<Appointment, String> aptTypeColumn21;

    /**
     * Adds all the months to an observable list
     */
    public void addAllMonths() {
        months.addAll(january, february, march, april, may, june, july, august, september, october, november, december);
    }

    /**
     * Creates an inner class to list and wrok with the months of the year
     */
    public class Month {
        private String monthName;
        private int monthNumber;

        public Month(int monthNumber, String monthName) {
            this.monthNumber = monthNumber;
            this.monthName = monthName;
        }

        /**
         * Creates a human friendly string representation of the month object
         *
         * @return
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

    @FXML
    private TableView<Appointment> contactAppointmentTable;

    @FXML
    private ComboBox<Customer> customerComboBox;

    @FXML
    private ComboBox<Contact> contactComboBox;

    @FXML
    private TableColumn<?, ?> custIdColumn2;

    @FXML
    private TableColumn<?, ?> custIdColumn21;

    @FXML
    private TableView<Appointment> customerAppointmentTable;

    @FXML
    private ComboBox<Month> monthComboBox;

    @FXML
    private Label numberOfAppointments;

    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private TableColumn<?, ?> userIdColumn2;

    @FXML
    private TableColumn<?, ?> userIdColumn21;



    public ObservableList<Appointment> getAllAppointments() {
        return allAppointments;
    }


    public Integer appointmentsCount(){

        String selectedType;

        if ((!monthComboBox.getSelectionModel().isEmpty()) && (typeComboBox.getSelectionModel().isEmpty())){

            int selectedMonthNumber = monthComboBox.getSelectionModel().getSelectedItem().getMonthNumber();

            Integer numberOfAptInMonth = (int) allAppointments.stream()
                    .filter(apt -> apt.getStartDateTime().getMonthValue() == selectedMonthNumber).count();
            System.out.println("Number of Apt in Month  "+ numberOfAptInMonth);
            return numberOfAptInMonth;
        }
        if((monthComboBox.getSelectionModel().isEmpty()) && (!typeComboBox.getSelectionModel().isEmpty())){

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
            System.out.println("Number of Apt With both constraints  "+ numberOfAptWithBothConstraints);
            return numberOfAptWithBothConstraints;
        }
        return 0;
    }






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

                        for (Appointment appointment: filteredByContact) {

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

                System.out.println("filteredByCustomer list:  " + filteredByCustomerID);

                for (Appointment appointment: filteredByCustomerID) {

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

        monthComboBox.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { //when focus lost
                appointmentsCount();
                numberOfAppointments.setText(appointmentsCount().toString()) ;
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
