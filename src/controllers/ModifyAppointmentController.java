package controllers;

import Objects.Appointment;
import Objects.Contact;
import Objects.Customer;
import Objects.User;
import implementationsDao.AppointmentsImplement;
import implementationsDao.ContactsImplement;
import implementationsDao.CustomersImplement;
import implementationsDao.UsersImplement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import utilities.TimezoneConversion;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;


import static implementationsDao.AppointmentsImplement.getAllAppointments;
import static implementationsDao.AppointmentsImplement.getAppointmentsByCustomerID;
import static implementationsDao.CustomersImplement.getAllCustomers;
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
        private ObservableList<Appointment> getAppointmentsByCustomerID = AppointmentsImplement.AppointmentsByCustomerID;

        private String selectedContactName;
        private String selectedCustomerName;
        private String selectedUserName;
        private static LocalDateTime startDate;
        private static LocalDateTime endDate;
        Appointment overlappingAppointment;
        private static int selectedCustomerID;


        public static LocalDateTime getStartDate() {
                return startDate;
        }

        public static void setStartDate(LocalDateTime startDate) {
                ModifyAppointmentController.startDate = startDate;
        }

        public static LocalDateTime getEndDate() {
                return endDate;
        }

        public static void setEndDate(LocalDateTime endDate) {
                ModifyAppointmentController.endDate = endDate;
        }

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
        @FXML private Label deleteSuccessfulLabel;
        @FXML private Label dateAndTimeErrorLabel2;

        public String getSelectedContactName() {
                return selectedContactName;
        }

        public void setSelectedContactName(String selectedContactName) {
                this.selectedContactName = selectedContactName;
        }

        public String getSelectedCustomerName() {
                return selectedCustomerName;
        }

        public void setSelectedCustomerName(String selectedCustomerName) {
                this.selectedCustomerName = selectedCustomerName;
        }

        public String getSelectedUserName() {
                return selectedUserName;
        }

        public void setSelectedUserName(String selectedUserName) {
                this.selectedUserName = selectedUserName;
        }


        public void setOverlappingAppointment(Appointment overlappingAppointment) {
                this.overlappingAppointment = overlappingAppointment;
        }

        public static void setSelectedCustomerID(int selectedCustomerID) {
                ModifyAppointmentController.selectedCustomerID = selectedCustomerID;
        }

        public ModifyAppointmentController() throws SQLException {
        }

        public Contact returnSelectedContactFromID(int contactId){
                Contact selectedContact = null;
                for (Contact contact: contactNames){
                        if (contact.getContactId() == contactId){
                                String contactName = contact.getContactName();
                                setSelectedContactName(contactName);
                                selectedContact = contact;
                                return selectedContact;
                        }
                }
                return null;
        }

        public Customer returnSelectedCustomerFromID(int customerId){
                Customer selectedCustomer = null;
                for (Customer customer: allCustomers){
                        if (customer.getCustomerId() == customerId){
                                String customerName = customer.getCustomerName();
                                setSelectedCustomerName(customerName);
                                selectedCustomer = customer;
                                return selectedCustomer;
                        }
                }
                return null;
        }

        public User returnSelectedUserFromID(int userId){
                User selectedUser = null;
                for (User user: userNames){
                        if (user.getUserId() == userId){
                                String userName = user.getUserName();
                                setSelectedCustomerName(userName);
                                selectedUser = user;
                                return selectedUser;
                        }
                }
                return null;
        }

        public void populateAptTable(ObservableList<Appointment>allAppointments) throws SQLException {
                appointmentsWithConvertedTimes.clear();
                allAppointments.clear();
                getAllAppointments();
                for(Appointment appointment : allAppointments){
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





        @FXML
        void onClickPopulateApptElements(MouseEvent event) {
                if(!appointmentTable.getSelectionModel().isEmpty()){
                        deleteButton.setDisable(false);
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

                        //Convert IDs to String
                        Integer selectedCustId = selectedAppointment.getCustomerId();
                        Integer userId = selectedAppointment.getUserId();
                        Integer contactId = selectedAppointment.getContactId();
                        Contact selectedContact = returnSelectedContactFromID(contactId);
                        User selectedUser = returnSelectedUserFromID(userId);
                        Customer selectedCustomer = returnSelectedCustomerFromID(selectedCustId);

                        //Set the form values to the selectedCustomer values
                        aptNumberLabel.setText(aptId.toString());
                        titleTxtField.setText(title);
                        appointmentDescriptionTxtField.setText(valueOf(description));
                        locationTxtField.setText(location);
                        contactComboBox.setValue(selectedContact);
                        typeComboBox.setValue(type);
                        userComboBox.setValue(selectedUser);
                        startDatePicker.setValue(startDate);
                        startTimeHrComboBox.setValue(startTime);
                        endDatePicker.setValue(endDate);
                        endTimeHrComboBox.setValue(endTime);
                        customerComboBox.setValue(selectedCustomer);
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
                dateAndTimeErrorLabel2.setVisible(false);
                appointmentTable.getSelectionModel().clearSelection();
                userComboBox.getSelectionModel().clearSelection();
                customerComboBox.getSelectionModel().clearSelection();
                deleteSuccessfulLabel.setVisible(false);
                clearBoxFormatting();
        }

        public boolean validateStartBeforeEndTime() throws Exception {
                try {
                        setStartDateTimeSelection();
                        setEndDateTimeSelection();
                        LocalDateTime startSelection = getStartDate();
                        LocalDateTime endSelection = getEndDate();
                        if (endSelection.isAfter(startSelection) && startSelection.isAfter(LocalDateTime.now())) {
                                return true;
                        } else {
                                dateAndTimeErrorLabel.setVisible(true);
                                dateAndTimeErrorLabel2.setVisible(true);
                                return false;
                        }
                } catch (Exception e) {
                        e.getMessage();
                        e.getCause();
                        e.printStackTrace();
                }
                return false;
        }


        public boolean overlapValidationA(LocalDateTime startDate) throws SQLException {

                for (Appointment appointment : getAppointmentsByCustomerID) {
                        LocalDateTime startTimeExistingAppointment = appointment.getStartDateTime();
                        LocalDateTime endTimeExistingAppointment = appointment.getEndDateTime();

                        if (((startDate.isAfter(startTimeExistingAppointment)) || (startDate.isEqual(startTimeExistingAppointment)))
                                && (startDate).isBefore(endTimeExistingAppointment)) {
                                setOverlappingAppointment(appointment);
                                return true;
                        }
                }
                return false;
        }

        public boolean overlapValidationB(LocalDateTime startDate, LocalDateTime endDate) throws SQLException {


                for (Appointment appointment : getAppointmentsByCustomerID) {
                        LocalDateTime startTimeExistingAppointment = appointment.getStartDateTime();
                        LocalDateTime endTimeExistingAppointment = appointment.getEndDateTime();

                        if (((endDate.isBefore(endTimeExistingAppointment)) || (endDate.isEqual(endTimeExistingAppointment)))
                                && (endDate).isAfter(startTimeExistingAppointment)) {
                                setOverlappingAppointment(appointment);
                                return true;
                        }
                }
                return false;
        }

        public boolean overlapValidationC(LocalDateTime startDate, LocalDateTime endDate) throws SQLException {

                for (Appointment appointment : getAppointmentsByCustomerID) {
                        LocalDateTime startTimeExistingAppointment = appointment.getStartDateTime();
                        LocalDateTime endTimeExistingAppointment = appointment.getEndDateTime();

                        if (((startDate.isBefore(startTimeExistingAppointment)) || (startDate.isEqual(startTimeExistingAppointment)))
                                && ((endDate.isAfter(endTimeExistingAppointment)) || (endDate.isEqual(endTimeExistingAppointment)))) {
                                setOverlappingAppointment(appointment);
                                return true;
                        }
                }
                return false;
        }

        public LocalDateTime setStartDateTimeSelection() {
                LocalDateTime startDateTimeSelection = LocalDateTime.of(startDatePicker.getValue(), startTimeHrComboBox.getValue());
                setStartDate(startDateTimeSelection);
                return startDateTimeSelection;
        }

        public LocalDateTime setEndDateTimeSelection() {
                LocalDateTime endDateTimeSelection = LocalDateTime.of(endDatePicker.getValue(), endTimeHrComboBox.getValue());
                setEndDate(endDateTimeSelection);
                return endDateTimeSelection;
        }

        public void emptyFieldAlert() {
                String title = titleTxtField.getText();
                String location = locationTxtField.getText();
                String description = appointmentDescriptionTxtField.getText();
                Contact contact = contactComboBox.getValue();
                String type = typeComboBox.getValue();
                LocalTime startTime = startTimeHrComboBox.getValue();
                LocalDate startDate = startDatePicker.getValue();
                LocalTime endtime = endTimeHrComboBox.getValue();
                LocalDate endDate = endDatePicker.getValue();
                Customer selectedCustomer = customerComboBox.getValue();
                User user = userComboBox.getValue();

                if(title.trim().isEmpty()||location.trim().isEmpty()||description.trim().isEmpty()
                        ||contact == null||type == null || startTime == null || startDate == null
                        || endtime == null || endDate == null || selectedCustomer == null || user == null){
                        alert();
                        if (title.trim().isEmpty()) {
                                allFieldsRequiredLabel.setVisible(true);
                                titleTxtField.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
                        }

                        if (location.trim().isEmpty()) {
                                allFieldsRequiredLabel.setVisible(true);
                                locationTxtField.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
                        }
                        if (description.trim().isEmpty()) {
                                allFieldsRequiredLabel.setVisible(true);
                                appointmentDescriptionTxtField.setStyle("-fx-text-box-border: #B22222; -fx-focus-color: #B22222;");
                        }
                        if (type == null) {
                                allFieldsRequiredLabel.setVisible(true);
                                typeComboBox.setStyle("-fx-border-color: #B22222; -fx-focus-color: #B22222;");
                        }
                        if (startDate == null) {
                                allFieldsRequiredLabel.setVisible(true);
                                startDatePicker.setStyle("-fx-border-color: #B22222; -fx-focus-color: #B22222;");
                        }
                        if (startTime == null) {
                                allFieldsRequiredLabel.setVisible(true);
                                startTimeHrComboBox.setStyle("-fx-border-color: #B22222; -fx-focus-color: #B22222;");
                        }
                        if (endDate == null) {
                                allFieldsRequiredLabel.setVisible(true);
                                endDatePicker.setStyle("-fx-border-color: #B22222; -fx-focus-color: #B22222;");
                        }
                        if (endtime == null) {
                                allFieldsRequiredLabel.setVisible(true);
                                endTimeHrComboBox.setStyle("-fx-border-color: #B22222; -fx-focus-color: #B22222;");
                        }
                        if (selectedCustomer == null) {
                                allFieldsRequiredLabel.setVisible(true);
                                customerComboBox.setStyle("-fx-border-color: #B22222; -fx-focus-color: #B22222;");
                        }
                        if (user == null) {
                                allFieldsRequiredLabel.setVisible(true);
                                userComboBox.setStyle("-fx-border-color: #B22222; -fx-focus-color: #B22222;");
                        }
                        if (contact == null) {
                                allFieldsRequiredLabel.setVisible(true);
                                contactComboBox.setStyle("-fx-border-color: #B22222; -fx-focus-color: #B22222;");
                        }
                }
        }

        public void clearBoxFormatting(){

                        allFieldsRequiredLabel.setVisible(false);
                        titleTxtField.setStyle("-fx-text-box-border: default; -fx-focus-color: default;");

                        allFieldsRequiredLabel.setVisible(false);
                        locationTxtField.setStyle("-fx-text-box-border: default; -fx-focus-color: default;");


                        allFieldsRequiredLabel.setVisible(false);
                        appointmentDescriptionTxtField.setStyle("-fx-text-box-border: default; -fx-focus-color: default;");


                        allFieldsRequiredLabel.setVisible(false);
                        typeComboBox.setStyle("-fx-border-color: default; -fx-focus-color: default;");


                        allFieldsRequiredLabel.setVisible(false);
                        startDatePicker.setStyle("-fx-border-color: default; -fx-focus-color: default;");


                        allFieldsRequiredLabel.setVisible(false);
                        startTimeHrComboBox.setStyle("-fx-border-color: default; -fx-focus-color: default;");


                        allFieldsRequiredLabel.setVisible(false);
                        endDatePicker.setStyle("-fx-border-color: default; -fx-focus-color: default;");


                        allFieldsRequiredLabel.setVisible(false);
                        endTimeHrComboBox.setStyle("-fx-border-color: default; -fx-focus-color: default;");

                        allFieldsRequiredLabel.setVisible(false);
                        customerComboBox.setStyle("-fx-border-color: default; -fx-focus-color: default;");

                        allFieldsRequiredLabel.setVisible(false);
                        userComboBox.setStyle("-fx-border-color: default; -fx-focus-color: default;");

                        allFieldsRequiredLabel.setVisible(false);
                        contactComboBox.setStyle("-fx-border-color: default; -fx-focus-color: default;");
        }


        public void alert(){
                Alert infoRequiredAlert = new Alert(Alert.AlertType.WARNING);
                infoRequiredAlert.setTitle("Information Required");
                infoRequiredAlert.setHeaderText("Please enter all information.  Thank you! ");
                infoRequiredAlert.setContentText("Please enter missing information");
                infoRequiredAlert.showAndWait();
        }
        public  void overlapAlert(){
                int overlapId = overlappingAppointment.getAppointmentId();
                LocalDateTime overlapStartConversion = convertUTCToUserTime(overlappingAppointment.getStartDateTime());
                LocalDateTime overlapEndConversion = convertUTCToUserTime(overlappingAppointment.getEndDateTime());
                LocalDate overlapStartDate = overlapStartConversion.toLocalDate();
                LocalTime overlapStartTime = overlapStartConversion.toLocalTime();
                LocalDate overlapEndDate = overlapEndConversion.toLocalDate();
                LocalTime overlapEndTime = overlapEndConversion.toLocalTime();
                Alert infoRequiredAlert = new Alert(Alert.AlertType.WARNING);
                infoRequiredAlert.setTitle("An Overlapping Appointment Was Found");
                infoRequiredAlert.setHeaderText("This appointment overlaps with appointment ID: " + overlapId);
                infoRequiredAlert.setContentText("Beginning on: " + overlapStartDate + " at " + overlapStartTime +
                        " and ending on: " + overlapEndDate + " at " + overlapEndTime);
                infoRequiredAlert.showAndWait();
        }

        @FXML
        void onSaveButtonAction(ActionEvent event) throws Exception {

                emptyFieldAlert();
                validateStartBeforeEndTime();

                if((!titleTxtField.getText().isEmpty()) && (titleTxtField.getLength() < 50) &&
                        (!locationTxtField.getText().isEmpty()) && (locationTxtField.getLength() < 50) &&
                        (!appointmentDescriptionTxtField.getText().isEmpty()) && (appointmentDescriptionTxtField.getLength() < 50) &&
                        (contactComboBox.getValue() != null) && (typeComboBox.getValue() != null) &&
                        (startDatePicker.getValue() != null) && (startTimeHrComboBox != null) &&
                        (endDatePicker.getValue() != null) && (endTimeHrComboBox != null) &&
                        (customerComboBox.getSelectionModel().getSelectedItem() != null) && (userComboBox.getValue() != null)) {
                        getAppointmentsByCustomerID();

                        if(validateStartBeforeEndTime() == true) {


                        int appointmentId = appointmentTable.getSelectionModel().getSelectedItem().getAppointmentId();
                        String title = titleTxtField.getText();
                        String location = locationTxtField.getText();
                        int contactId = contactComboBox.getValue().getContactId();

                        String type = typeComboBox.getValue();

                        LocalDate startDate = startDatePicker.getValue();
                        LocalTime startTime = startTimeHrComboBox.getValue();
                        LocalDateTime userStartDateTime = LocalDateTime.of(startDate, startTime);
                        LocalDate endDate = endDatePicker.getValue();
                        LocalTime endTime = endTimeHrComboBox.getValue();
                        LocalDateTime userEndDateTime = LocalDateTime.of(endDate, endTime);

                        // Get times converted to UTC
                        LocalDateTime startDateTime = TimezoneConversion.convertUserStartTimeToUTC(userStartDateTime);
                        LocalDateTime endDateTime = TimezoneConversion.convertUserEndTimeToUTC(userEndDateTime);
                        System.out.println("Start and end times converted: " + startDateTime +" " + endDateTime);


                        String description = appointmentDescriptionTxtField.getText();
                        int customerId = customerComboBox.getSelectionModel().getSelectedItem().getCustomerId();
                        int userId = userComboBox.getValue().getUserId();


                        boolean validationResultStartBeforeEnd = validateStartBeforeEndTime(); //returns true if validation passes
                        boolean validationResultA = overlapValidationA(startDateTime); // returns false if validation passes
                        boolean validationResultB = overlapValidationB(startDateTime, endDateTime); // returns false if validation passes
                        boolean validationResultC = overlapValidationC(startDateTime, endDateTime); // returns false if validation passes
                        System.out.println("Validation Result Start before end: " + validationResultStartBeforeEnd);
                        System.out.println("validationResultA: " + validationResultA);
                        System.out.println("validationResultB: " + validationResultB);
                        System.out.println("ValidationResultC: " + validationResultC);



                        if (validationResultA == false && validationResultB == false && validationResultC == false) {
                                Appointment appointmentToSave = new Appointment(appointmentId, title, description,
                                        location, type, startDateTime, endDateTime, customerId, userId, contactId);
                                AppointmentsImplement.updateAppointment(appointmentToSave);
                                saveButton.setDisable(true);
                                saveSuccessfulLabel.setVisible(true);
                                appointmentTable.getItems().clear();
                                getAllAppointments();
                                populateAptTable(allAppointments);
                        } else {
                                System.out.println("Conflicting appointment found");
                                overlapAlert();
                                saveErrorLabel.setVisible(true);
                        }
                        }
                        else{dateAndTimeErrorLabel.setVisible(true);
                        dateAndTimeErrorLabel2.setVisible(true);
                        }
                }
        }

        public void deleteAlert() throws SQLException {
                int appointmentId = appointmentTable.getSelectionModel().getSelectedItem().getAppointmentId();
                LocalDate startDate = startDatePicker.getValue();
                LocalTime startTime = startTimeHrComboBox.getValue();
                String customerName = customerComboBox.getSelectionModel().getSelectedItem().getCustomerName();
                Alert deleteAlert = new Alert(Alert.AlertType.WARNING);
                deleteAlert.setTitle("This will permanently delete appointment " + appointmentId);
                deleteAlert.setHeaderText("Are you sur you want to delete appointment " + appointmentId + " ?");
                deleteAlert.setContentText("Appointment for Customer: " + customerName +
                        " Starting on " + startDate + " at " + startTime );
                //deleteAlert.showAndWait();

                ButtonType yesButton = new ButtonType("Delete Appointment");
                ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);

                deleteAlert.getButtonTypes().setAll(yesButton, cancelButton);

                Optional<ButtonType> result = deleteAlert.showAndWait();
                if(result.get() == yesButton)
                {
                        AppointmentsImplement.deleteAppointment(appointmentId);
                        deleteSuccessfulLabel.setVisible(true);
                        deleteButton.setDisable(true);
                        deleteAlert.close();

                }
                else if(result.get() == cancelButton)
                {
                        deleteAlert.close();
                }
        }

        @FXML
        void onActionDeleteAppointment(ActionEvent event) throws SQLException {
                deleteAlert();
                appointmentTable.getItems().clear();
                getAllAppointments();
                populateAptTable(allAppointments);
        }



        @Override
        public void initialize(URL url, ResourceBundle resourceBundle) {
                //Hide active labels
                dateAndTimeErrorLabel.setVisible(false);
                dateAndTimeErrorLabel2.setVisible(false);
                titleLengthAlert.setVisible(false);
                saveSuccessfulLabel.setVisible(false);
                saveErrorLabel.setVisible(false);
                locationLengthAlert.setVisible(false);
                allFieldsRequiredLabel.setVisible(false);
                descriptionLengthAlert.setVisible(false);
                deleteSuccessfulLabel.setVisible(false);

                //Populate the comboBoxes
                contactComboBox.setItems(contactNames);
                typeComboBox.setItems(appointmentTypes);
                userComboBox.setItems(userNames);
                customerComboBox.setItems(allCustomers);

                TimezoneConversion.convertBusinessStartFromEstToLocalTime();
                TimezoneConversion.convertBusinessEndFromEstToLocalTime();
                populateStartTimeComboBox();
                populateEndTimeComboBox();

                try {
                        getAllAppointments.clear();
                        allAppointments.clear();
                        getAllAppointments();
                } catch (SQLException e) {
                        e.printStackTrace();
                }
                try {
                        populateAptTable(allAppointments);
                } catch (SQLException e) {
                        e.printStackTrace();
                }


                endTimeHrComboBox.focusedProperty().addListener((arg0, oldValue, newValue) -> {
                        if (!newValue) { //when focus is lost
                                Boolean validationResult = null;
                                saveButton.setDisable(false);
                                saveErrorLabel.setVisible(false);
                                endTimeHrComboBox.setStyle("-fx-border-color: default; -fx-focus-color: default;");
                                try {
                                        validationResult = validateStartBeforeEndTime();
                                } catch (Exception e) {
                                        e.printStackTrace();
                                }
                                if(validationResult) {
                                        dateAndTimeErrorLabel.setVisible(false);
                                        dateAndTimeErrorLabel2.setVisible(false);
                                }
                                else{dateAndTimeErrorLabel.setVisible(true);
                                dateAndTimeErrorLabel2.setVisible(true);
                                }
                        }
                });

                endDatePicker.focusedProperty().addListener((arg0, oldValue, newValue) -> {
                        if (!newValue) { //when focus is lost
                                endTimeHrComboBox.getValue();
                                saveButton.setDisable(false);
                                saveErrorLabel.setVisible(false);
                                endDatePicker.setStyle("-fx-border-color: default; -fx-focus-color: default;");
                                if(!endTimeHrComboBox.getValue().equals(null)) { //if the end time is not null
                                        Boolean validationResult = null;
                                        try {
                                                validationResult = validateStartBeforeEndTime();
                                        } catch (Exception e) {
                                                e.printStackTrace();
                                        }
                                        if (validationResult) {
                                                dateAndTimeErrorLabel.setVisible(false);
                                                dateAndTimeErrorLabel2.setVisible(false);

                                        } else { dateAndTimeErrorLabel.setVisible(true);
                                        dateAndTimeErrorLabel2.setVisible(true);
                                        }
                                }
                        }
                });

//Change listeners to validate text fields and warn the user in real time if input is over the allowed length
                titleTxtField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
                        if (!newValue) { //when focus lost
                                saveSuccessfulLabel.setVisible(false);
                                saveErrorLabel.setVisible(false);
                                saveButton.setDisable(false);
                                allFieldsRequiredLabel.setVisible(false);
                                titleTxtField.setStyle("-fx-border-color: default; -fx-focus-color: default;");
                                if(titleTxtField.getLength() > 50) {
                                        titleLengthAlert.setVisible(true);}
                                else if(titleTxtField.getLength() < 50){titleLengthAlert.setVisible(false);}
                        }
                });

                locationTxtField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
                        if (!newValue) { //when focus lost
                                saveSuccessfulLabel.setVisible(false);
                                saveErrorLabel.setVisible(false);
                                saveButton.setDisable(false);
                                allFieldsRequiredLabel.setVisible(false);
                                locationTxtField.setStyle("-fx-border-color: default; -fx-focus-color: default;");
                                if(locationTxtField.getLength() > 50) {
                                        locationLengthAlert.setVisible(true);}
                                else if(locationTxtField.getLength() < 50){locationLengthAlert.setVisible(false);}
                        }
                });

                appointmentDescriptionTxtField.focusedProperty().addListener((arg0, oldValue, newValue) -> {
                        if (!newValue) { //when focus lost
                                saveSuccessfulLabel.setVisible(false);
                                saveErrorLabel.setVisible(false);
                                saveButton.setDisable(false);
                                allFieldsRequiredLabel.setVisible(false);
                                appointmentDescriptionTxtField.setStyle("-fx-border-color: default; -fx-focus-color: default;");
                                if(appointmentDescriptionTxtField.getLength() > 50) {
                                        descriptionLengthAlert.setVisible(true);}
                                else if(appointmentDescriptionTxtField.getLength() < 50){descriptionLengthAlert.setVisible(false);}
                        }
                });

                typeComboBox.focusedProperty().addListener((arg0, oldValue, newValue) -> {
                        if (!newValue) { //when focus lost
                                saveSuccessfulLabel.setVisible(false);
                                saveErrorLabel.setVisible(false);
                                saveButton.setDisable(false);
                                allFieldsRequiredLabel.setVisible(false);
                                typeComboBox.setStyle("-fx-border-color: default; -fx-focus-color: default;");
                        }
                });

                userComboBox.focusedProperty().addListener((arg0, oldValue, newValue) -> {
                        if (!newValue) { //when focus lost
                                saveSuccessfulLabel.setVisible(false);
                                saveErrorLabel.setVisible(false);
                                saveButton.setDisable(false);
                                allFieldsRequiredLabel.setVisible(false);
                                userComboBox.setStyle("-fx-border-color: default; -fx-focus-color: default;");
                        }
                });

                contactComboBox.focusedProperty().addListener((arg0, oldValue, newValue) -> {
                        if (!newValue) { //when focus lost
                                saveSuccessfulLabel.setVisible(false);
                                saveErrorLabel.setVisible(false);
                                saveButton.setDisable(false);
                                allFieldsRequiredLabel.setVisible(false);
                                contactComboBox.setStyle("-fx-border-color: default; -fx-focus-color: default;");
                        }
                });

                appointmentTable.focusedProperty().addListener((arg0, oldValue, newValue) -> {
                        if (!newValue) { //when focus lost
                                saveSuccessfulLabel.setVisible(false);
                                saveErrorLabel.setVisible(false);
                                saveButton.setDisable(false);
                                allFieldsRequiredLabel.setVisible(false);
                                appointmentTable.setStyle("-fx-border-color: default; -fx-focus-color: default;");
                                clearBoxFormatting();
                        }
                });

                startDatePicker.focusedProperty().addListener((arg0, oldValue, newValue) -> {
                        if (!newValue) { //when focus lost
                                saveSuccessfulLabel.setVisible(false);
                                saveErrorLabel.setVisible(false);
                                saveButton.setDisable(false);
                                allFieldsRequiredLabel.setVisible(false);
                                startDatePicker.setStyle("-fx-border-color: default; -fx-focus-color: default;");
                        }
                });

                startTimeHrComboBox.focusedProperty().addListener((arg0, oldValue, newValue) -> {
                        if (!newValue) { //when focus lost
                                saveSuccessfulLabel.setVisible(false);
                                saveErrorLabel.setVisible(false);
                                saveButton.setDisable(false);
                                allFieldsRequiredLabel.setVisible(false);
                                startTimeHrComboBox.setStyle("-fx-border-color: default; -fx-focus-color: default;");
                        }
                });




        }
}
