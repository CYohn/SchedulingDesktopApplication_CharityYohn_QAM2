package controllers;

import Objects.Appointment;
import Objects.Contact;
import implementationsDao.AppointmentsImplement;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ResourceBundle;


import static implementationsDao.AppointmentsImplement.getAllAppointments;
import static utilities.TimezoneConversion.convertUTCToUserTime;

public class ModifyAppointmentController implements Initializable {

        ObservableList<Appointment>allAppointments = getAllAppointments;

        ObservableList<Appointment>appointmentsWithConvertedTimes = FXCollections.observableArrayList();

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

        @FXML private ComboBox<LocalTime> startTimeHr;
        @FXML private ComboBox<LocalTime> endTimeHrComboBox;
        @FXML private ComboBox<Contact> contactComboBox;
        @FXML private ComboBox<String> typeComboBox;

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
                        appointmentsWithConvertedTimes.addAll(convertedTimesAppointment);
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

        @FXML void onActionClearForm(ActionEvent event) {

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


                try {
                        getAllAppointments();
                } catch (SQLException e) {
                        e.printStackTrace();
                }
                populateAptTable(allAppointments);
        }
}
