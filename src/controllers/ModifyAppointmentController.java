package controllers;

import Objects.Appointment;
import Objects.Contact;
import Objects.Customer;
import Objects.User;
import implementationsDao.ContactsImplement;
import implementationsDao.CustomersImplement;
import implementationsDao.UsersImplement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import utilities.TimezoneConversion;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;


import static implementationsDao.AppointmentsImplement.getAllAppointments;
import static java.lang.String.valueOf;
import static utilities.TimezoneConversion.convertUTCToUserTime;

public class ModifyAppointmentController implements Initializable {

        private ObservableList<Contact> contactNames = ContactsImplement.contactNames;
        private ObservableList<User> userNames = UsersImplement.userNames;
        private ObservableList<String> appointmentTypes = FXCollections.observableArrayList
                ("Planning Session", "Progress Update", "De-Briefing");

        ObservableList<Appointment>allAppointments = getAllAppointments;
        ObservableList<Appointment>appointmentsWithConvertedTimes = FXCollections.observableArrayList();
        ObservableList<Customer> allCustomers = CustomersImplement.getAllCustomers;

        @FXML private TableView<Appointment> appointmentTable;
        @FXML private TableColumn<Appointment, Integer> aptContactColumn;
        @FXML private TableColumn<Appointment, String> aptDescriptioncolumn;
        @FXML private TableColumn<Appointment, LocalDate> aptEndDateColumn;
        @FXML private TableColumn<Appointment, LocalTime> aptEndTimeColumn;
        @FXML private TableColumn<Appointment, Integer> aptIdColumn;
        @FXML private TableColumn<Appointment, String> aptLocationColumn;
        @FXML private TableColumn<Appointment, LocalDate> aptStartDateColumn;
        @FXML private TableColumn<Appointment, LocalTime> aptStartTimeColumn;
        @FXML private TableColumn<Appointment, String> aptTitleColum;
        @FXML private TableColumn<Appointment, String> aptTypeColumn;
        @FXML private TableColumn<Appointment, Integer> custIdColumn;
        @FXML private TableColumn<Appointment, Integer> userIdColumn;

        @FXML private TextField locationTxtField;
        @FXML private TextField titleTxtField;
        @FXML private TextArea appointmentDescriptionTxtField;

        @FXML private DatePicker startDatePicker;
        @FXML private DatePicker endDatePicker;

        @FXML private ComboBox<LocalTime> startTimeHrComboBox;
        @FXML private ComboBox<LocalTime> endTimeHrComboBox;
        @FXML private ComboBox<Contact> contactComboBox;
        @FXML private ComboBox<String> typeComboBox;
        @FXML private ComboBox<User> userComboBox;
        @FXML private ComboBox<Customer> customerComboBox;


        @FXML private Button deleteButton;
        @FXML private Button clearButton;
        @FXML private Button saveButton;

        @FXML private Label dateAndTimeErrorLabel;
        @FXML private Label titleLengthAlert;
        @FXML private Label saveSuccessfulLabel;
        @FXML private Label saveErrorLabel;
        @FXML private Label locationLengthAlert;
        @FXML private Label allFieldsRequiredLabel;
        @FXML private Label descriptionLengthAlert;
        @FXML private Label aptNumberLabel;

        public ModifyAppointmentController() throws SQLException {
        }


        public void populateAptTable(ObservableList<Appointment>allAppointments){

                for(Appointment appointment : allAppointments){
                        System.out.println("Appointment from the populate apt Table: " + appointment);
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

                appointmentTable.setItems(appointmentsWithConvertedTimes);

                aptContactColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));
                aptDescriptioncolumn.setCellValueFactory(new PropertyValueFactory<>("description"));
                aptEndDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
                aptEndTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
                aptIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
                aptLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
                aptStartDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
                aptStartTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
                aptTitleColum.setCellValueFactory(new PropertyValueFactory<>("title"));
                aptTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
                custIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
                userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        }

        // Populate the Combo Boxes

        public void populateContactcomboBox(){}

        public void populateTypecomboBox(){}

        public void populateUserComboBox(){}

        public void populateStartTimeComboBox(){
                LocalDateTime businessStartConverted = TimezoneConversion.getBusinessStartConverted();
                LocalTime businessStartConvertedTime = businessStartConverted.toLocalTime();
                int businessStartHour = businessStartConvertedTime.getHour();
                int businessStartMinute = businessStartConvertedTime.getMinute();
                LocalTime startHour = LocalTime.of(businessStartHour, businessStartMinute);

                LocalDateTime businessEndConverted = TimezoneConversion.getBusinessEndConverted();
                LocalTime businessEndConvertedTime = businessEndConverted.toLocalTime();
                int businessEndHour = businessEndConvertedTime.getHour();
                int businessEndMinute = businessEndConvertedTime.getMinute();
                LocalTime endHour = LocalTime.of(businessEndHour, businessEndMinute);

                while (startHour.isBefore(endHour)) {
                        startTimeHrComboBox.getItems().add(startHour);
                        startHour = startHour.plusMinutes(15);
                }
        }

        public void populateEndTimeComboBox(){LocalDateTime businessStartConverted = TimezoneConversion.getBusinessStartConverted();
                LocalTime businessStartConvertedTime = businessStartConverted.toLocalTime();
                int businessStartHour = businessStartConvertedTime.getHour();
                int businessStartMinute = businessStartConvertedTime.getMinute();
                LocalTime startHour = LocalTime.of(businessStartHour, businessStartMinute);

                LocalDateTime businessEndConverted = TimezoneConversion.getBusinessEndConverted();
                LocalTime businessEndConvertedTime = businessEndConverted.toLocalTime();
                int businessEndHour = businessEndConvertedTime.getHour();
                int businessEndMinute = businessEndConvertedTime.getMinute();
                LocalTime endHour = LocalTime.of(businessEndHour, businessEndMinute);

                while (startHour.isBefore(endHour)) {
                        endTimeHrComboBox.getItems().add(startHour);
                        startHour = startHour.plusMinutes(15);
                }}

        public void populateCustomerComboBox(){}




        @FXML
        void onClickPopulateApptElements(MouseEvent event) {
                if(!appointmentTable.getSelectionModel().isEmpty()){
                        Appointment selectedAppointment = appointmentTable.getSelectionModel().getSelectedItem();

                        //Get table values
                        Integer aptId = selectedAppointment.getAppointmentId();
                        String title = selectedAppointment.getTitle();
                        String description = selectedAppointment.getDescription();
                        String location = selectedAppointment.getLocation();
                        String type = selectedAppointment.getType();
                        LocalDate startDate = selectedAppointment.getStartDate();
                        LocalTime startTime = selectedAppointment.getStartTime();
                        LocalDate endDate = selectedAppointment.getEndDate();
                        LocalTime endTime = selectedAppointment.getEndTime();
                        Integer selectedCustId = selectedAppointment.getCustomerId();
                        Integer userId = selectedAppointment.getUserId();

                        Integer contactId = selectedAppointment.getContactId();
                        //Set the form values to the selectedCustomer values
                        aptNumberLabel.setText(aptId.toString());
                        titleTxtField.setText(title);
                        appointmentDescriptionTxtField.setText(valueOf(description));
                        locationTxtField.setText(location);
                        //contactComboBox.setValue(contact);
                        typeComboBox.setValue(type);
                        //userComboBox.setValue();
                        startDatePicker.setValue(startDate);
                        startTimeHrComboBox.setValue(startTime);
                        endDatePicker.setValue(endDate);
                        endTimeHrComboBox.setValue(endTime);
                        //customerComboBox.setValue();
                        }
                }


        @FXML void onActionClearForm(ActionEvent event) {

                titleTxtField.clear();
                titleLengthAlert.setVisible(false);
                locationTxtField.clear();
                locationLengthAlert.setVisible(false);
                contactComboBox.getSelectionModel().clearSelection();
                typeComboBox.getSelectionModel().clearSelection();
                startDatePicker.setValue(null);
                startTimeHrComboBox.getSelectionModel().clearSelection();
                endDatePicker.setValue(null);
                endTimeHrComboBox.getSelectionModel().clearSelection();
                appointmentDescriptionTxtField.clear();
                descriptionLengthAlert.setVisible(false);
                saveErrorLabel.setVisible(false);
                saveSuccessfulLabel.setVisible(false);
                allFieldsRequiredLabel.setVisible(false);
                dateAndTimeErrorLabel.setVisible(false);
                //customerTable.getSelectionModel().clearSelection();
                userComboBox.getSelectionModel().clearSelection();
        }



        @FXML void onSaveButtonAction(ActionEvent event) {

        }



        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
                dateAndTimeErrorLabel.setVisible(false);
                titleLengthAlert.setVisible(false);
                saveSuccessfulLabel.setVisible(false);
                saveErrorLabel.setVisible(false);
                locationLengthAlert.setVisible(false);
                allFieldsRequiredLabel.setVisible(false);
                descriptionLengthAlert.setVisible(false);

                //Populate the comboBoxes
                contactComboBox.setItems(contactNames);
                typeComboBox.setItems(appointmentTypes);
                userComboBox.setItems(userNames);
                customerComboBox.setItems(allCustomers);
                populateStartTimeComboBox();
                populateEndTimeComboBox();

                try {
                        allAppointments.clear();
                        getAllAppointments();
                } catch (SQLException e) {
                        e.printStackTrace();
                }
                populateAptTable(allAppointments);
        }
}
