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
    private TableColumn<Appointment, Integer> aptContactColumn;

    @FXML
    private TableColumn<Appointment, Integer> aptContactColumn1;

    @FXML
    private TableColumn<Appointment, Integer> aptContactColumn2;

    @FXML
    private TableColumn<Appointment, String> aptDescriptioncolumn;

    @FXML
    private TableColumn<Appointment, String> aptDescriptioncolumn1;

    @FXML
    private TableColumn<Appointment, String> aptDescriptioncolumn2;

    @FXML
    private TableColumn<Appointment, LocalDate> aptEndDateColumn;

    @FXML
    private TableColumn<Appointment, LocalDate> aptEndDateColumn1;

    @FXML
    private TableColumn<Appointment, LocalDate> aptEndDateColumn2;

    @FXML
    private TableColumn<Appointment, LocalTime> aptEndTimeColumn;

    @FXML
    private TableColumn<Appointment, LocalTime> aptEndTimeColumn1;

    @FXML
    private TableColumn<Appointment, LocalTime> aptEndTimeColumn2;

    @FXML
    private TableColumn<Appointment, Integer> aptIdColumn;

    @FXML
    private TableColumn<Appointment, Integer> aptIdColumn1;

    @FXML
    private TableColumn<Appointment, Integer> aptIdColumn2;

    @FXML
    private TableColumn<Appointment, String> aptLocationColumn;

    @FXML
    private TableColumn<Appointment, String> aptLocationColumn1;

    @FXML
    private TableColumn<Appointment, String> aptLocationColumn2;

    @FXML
    private TableColumn<Appointment, LocalDate> aptStartDateColumn;

    @FXML
    private TableColumn<Appointment, LocalDate> aptStartDateColumn1;

    @FXML
    private TableColumn<Appointment, LocalDate> aptStartDateColumn2;

    @FXML
    private TableColumn<Appointment, LocalTime> aptStartTimeColumn;

    @FXML
    private TableColumn<Appointment, LocalTime> aptStartTimeColumn1;

    @FXML
    private TableColumn<Appointment, LocalTime> aptStartTimeColumn2;

    @FXML
    private TableColumn<Appointment, String> aptTitleColum;

    @FXML
    private TableColumn<Appointment, String> aptTitleColum1;

    @FXML
    private TableColumn<Appointment, String> aptTitleColum2;

    @FXML
    private TableColumn<Appointment, String> aptTypeColumn;

    @FXML
    private TableColumn<Appointment, String> aptTypeColumn1;

    @FXML
    private TableColumn<Appointment, String> aptTypeColumn2;

    @FXML
    private Tab currentMonthTab;

    @FXML
    private Tab currentWeekTab;

    @FXML
    private TableColumn<Appointment, Integer> custIdColumn;

    @FXML
    private TableColumn<Appointment, Integer> custIdColumn1;

    @FXML
    private TableColumn<Appointment, Integer> custIdColumn2;

    @FXML
    private TableColumn<Appointment, Integer> userIdColumn;

    @FXML
    private TableColumn<Appointment, Integer> userIdColumn1;

    @FXML
    private TableColumn<Appointment, Integer> userIdColumn2;

    /**
     * The method gets all the appointments from the database and translates the time from UTC to the user local time.
     * The method then builds an appointment object which
     *
     * @Lambda is filtered by the current week of the year
     * and then populates the table based on the filtered list.
     */
    public void loadCurrentWeek() {
        ObservableList<Appointment> appointmentsWithConvertedTimes = FXCollections.observableArrayList();
        try {
            AppointmentsImplement.getAllAppointments();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Appointment appointment : allAppointments) {

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
        System.out.println("Appointments this month  " + aptsThisWeek);


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


    /**
     * Initializes the page
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCurrentWeek();

        /**
         * @Lambda The lambda listens for a selection change on the tabs. If all appointments is selects it loads all
         * the appointments into the table.
         */
        allAppointmentsTab.setOnSelectionChanged(t -> {
            if (allAppointmentsTab.isSelected()) {

                ObservableList<Appointment> appointmentsWithConvertedTimes = FXCollections.observableArrayList();
                try {
                    AppointmentsImplement.getAllAppointments();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                for (Appointment appointment : allAppointments) {

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

        /**
         * @Lambda listens for a selection change
         * If the current month tab is selected the appointments are filtered based on the current month and loaded into the table.
         */
        currentMonthTab.setOnSelectionChanged(t -> {
            if (currentMonthTab.isSelected()) {

                ObservableList<Appointment> appointmentsWithConvertedTimes = FXCollections.observableArrayList();
                try {
                    AppointmentsImplement.getAllAppointments();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                for (Appointment appointment : allAppointments) {

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

        /**
         * Listens for a selection of the current week tab and calls the method loadCurrentWeek() if selected
         */
        currentWeekTab.setOnSelectionChanged(t -> {
            if (currentWeekTab.isSelected()) {
                loadCurrentWeek();
            }
        });
    }
}