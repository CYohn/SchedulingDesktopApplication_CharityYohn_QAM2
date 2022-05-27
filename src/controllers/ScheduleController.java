package controllers;

import Objects.Appointment;
import implementationsDao.AppointmentsImplement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoField;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.ResourceBundle;

import static utilities.TimezoneConversion.convertUTCToUserTime;

public class ScheduleController implements Initializable {

    public ObservableList<Appointment> allAppointments = AppointmentsImplement.getAllAppointments;

    @FXML
    private Tab allAppointmentsTab;

    @FXML
    private TableView<Appointment> appointmentTable;

    @FXML
    private TableView<Appointment> appointmentTable1;

    @FXML
    private TableView<Appointment> appointmentTable2;

    @FXML
    private TableColumn<?, ?> aptContactColumn;

    @FXML
    private TableColumn<?, ?> aptContactColumn1;

    @FXML
    private TableColumn<?, ?> aptContactColumn2;

    @FXML
    private TableColumn<?, ?> aptDescriptioncolumn;

    @FXML
    private TableColumn<?, ?> aptDescriptioncolumn1;

    @FXML
    private TableColumn<?, ?> aptDescriptioncolumn2;

    @FXML
    private TableColumn<?, ?> aptEndDateColumn;

    @FXML
    private TableColumn<?, ?> aptEndDateColumn1;

    @FXML
    private TableColumn<?, ?> aptEndDateColumn2;

    @FXML
    private TableColumn<?, ?> aptEndTimeColumn;

    @FXML
    private TableColumn<?, ?> aptEndTimeColumn1;

    @FXML
    private TableColumn<?, ?> aptEndTimeColumn2;

    @FXML
    private TableColumn<?, ?> aptIdColumn;

    @FXML
    private TableColumn<?, ?> aptIdColumn1;

    @FXML
    private TableColumn<?, ?> aptIdColumn2;

    @FXML
    private TableColumn<?, ?> aptLocationColumn;

    @FXML
    private TableColumn<?, ?> aptLocationColumn1;

    @FXML
    private TableColumn<?, ?> aptLocationColumn2;

    @FXML
    private TableColumn<?, ?> aptStartDateColumn;

    @FXML
    private TableColumn<?, ?> aptStartDateColumn1;

    @FXML
    private TableColumn<?, ?> aptStartDateColumn2;

    @FXML
    private TableColumn<?, ?> aptStartTimeColumn;

    @FXML
    private TableColumn<?, ?> aptStartTimeColumn1;

    @FXML
    private TableColumn<?, ?> aptStartTimeColumn2;

    @FXML
    private TableColumn<?, ?> aptTitleColum;

    @FXML
    private TableColumn<?, ?> aptTitleColum1;

    @FXML
    private TableColumn<?, ?> aptTitleColum2;

    @FXML
    private TableColumn<?, ?> aptTypeColumn;

    @FXML
    private TableColumn<?, ?> aptTypeColumn1;

    @FXML
    private TableColumn<?, ?> aptTypeColumn2;

    @FXML
    private Tab currentMonthTab;

    @FXML
    private Tab currentWeekTab;

    @FXML
    private TableColumn<?, ?> custIdColumn;

    @FXML
    private TableColumn<?, ?> custIdColumn1;

    @FXML
    private TableColumn<?, ?> custIdColumn2;

    @FXML
    private TableColumn<?, ?> userIdColumn;

    @FXML
    private TableColumn<?, ?> userIdColumn1;

    @FXML
    private TableColumn<?, ?> userIdColumn2;

public void loadCurrentWeek(){
    ObservableList<Appointment> appointmentsWithConvertedTimes = FXCollections.observableArrayList();
    try {
        AppointmentsImplement.getAllAppointments();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    for (Appointment appointment: allAppointments) {

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

    ObservableList<Appointment>aptsThisWeek = FXCollections.observableArrayList();

    LocalDate userToday = LocalDate.now();
    int weekOfYear = userToday.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
    appointmentsWithConvertedTimes.stream()
            .filter(apt -> apt.getStartDate().get(ChronoField.ALIGNED_WEEK_OF_YEAR) == weekOfYear)
            .forEach(aptsThisWeek::add);
    System.out.println("Appointments this month  "+ aptsThisWeek);


    appointmentTable1.setItems(aptsThisWeek);

    aptContactColumn1.setCellValueFactory(new PropertyValueFactory<>("contactId"));
    aptDescriptioncolumn1.setCellValueFactory(new PropertyValueFactory<>("description"));
    aptEndDateColumn1.setCellValueFactory(new PropertyValueFactory<>("endDate"));
    aptEndTimeColumn1.setCellValueFactory(new PropertyValueFactory<>("endTime"));
    aptIdColumn1.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
    aptLocationColumn1.setCellValueFactory(new PropertyValueFactory<>("location"));
    aptStartDateColumn1.setCellValueFactory(new PropertyValueFactory<>("startDate"));
    aptStartTimeColumn1.setCellValueFactory(new PropertyValueFactory<>("startTime"));
    aptTitleColum1.setCellValueFactory(new PropertyValueFactory<>("title"));
    aptTypeColumn1.setCellValueFactory(new PropertyValueFactory<>("type"));
    custIdColumn1.setCellValueFactory(new PropertyValueFactory<>("customerId"));
    userIdColumn1.setCellValueFactory(new PropertyValueFactory<>("userId"));
}






    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    loadCurrentWeek();

        allAppointmentsTab.setOnSelectionChanged(t -> {
            if (allAppointmentsTab.isSelected()) {

                ObservableList<Appointment> appointmentsWithConvertedTimes = FXCollections.observableArrayList();
                try {
                    AppointmentsImplement.getAllAppointments();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                for (Appointment appointment: allAppointments) {

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
        });

        currentMonthTab.setOnSelectionChanged(t -> {
            if (currentMonthTab.isSelected()) {

                ObservableList<Appointment> appointmentsWithConvertedTimes = FXCollections.observableArrayList();
                try {
                    AppointmentsImplement.getAllAppointments();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                for (Appointment appointment: allAppointments) {

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

                ObservableList<Appointment>aptsThisMonth = FXCollections.observableArrayList();

                int userMonthValue = LocalDate.now().getMonthValue();
                appointmentsWithConvertedTimes.stream()
                        .filter(apt -> apt.getStartDate().getMonthValue() == userMonthValue)
                        .forEach(aptsThisMonth::add);
                System.out.println("Appointments this month  "+ appointmentsWithConvertedTimes);

                ObservableList<Appointment>convertedAptThisMonth = aptsThisMonth;
                appointmentTable2.setItems(convertedAptThisMonth);

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

        currentWeekTab.setOnSelectionChanged(t -> {
            if (currentWeekTab.isSelected()) {
                loadCurrentWeek();
                }
            });
    }
}