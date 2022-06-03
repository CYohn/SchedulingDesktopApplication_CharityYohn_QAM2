package implementationsDao;

import Objects.Appointment;
import controllers.AddAppointmentController;
import interfacesDao.AppointmentsInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utilities.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;

public class AppointmentsImplement implements AppointmentsInterface {
    /**
     * getAllAppointments is an observableList to store all appointments
     */
    public static ObservableList<Appointment> getAllAppointments = FXCollections.observableArrayList();

    /**
     * appointmentsByCustomerID is an observable list to store appointments which were filtered by customer ID
     */
    public static ObservableList<Appointment> appointmentsByCustomerID = FXCollections.observableArrayList();

    /**
     * Gets all appointments from the database and adds them to an ObservableList
     *
     * @throws SQLException An exception that provides information on a database access error or other errors
     */
    public static void getAllAppointments() throws SQLException {
        getAllAppointments.clear();
        String allAppointmentsQuery = "SELECT Appointment_ID, Title, Description, Location, Type, Start, End, customers.Customer_ID, users.User_ID, contacts.Contact_ID" +
                " FROM client_schedule.appointments, customers, users, contacts " +
                " WHERE appointments.Customer_ID = customers.Customer_ID " +
                " AND appointments.User_ID = users.User_ID" +
                " AND appointments.Contact_ID = contacts.Contact_ID";
        PreparedStatement getAppointmentsPreparedStatement = DatabaseConnection.getConnection().prepareStatement(allAppointmentsQuery);
        //System.out.println("getAppointmentsPreparedStatement was successful in AppointmentsImplement.getAllAppointments()");
        ResultSet allAppointmentsResults;

        try {

            if (getAppointmentsPreparedStatement != null) {
                allAppointmentsResults = getAppointmentsPreparedStatement.executeQuery();


                while (allAppointmentsResults.next()) {
                    int appointmentId = allAppointmentsResults.getInt("Appointment_ID");
                    //System.out.println("allAppointmentResults appointmentId: " + appointmentId);

                    String title = allAppointmentsResults.getString("Title");
                    //System.out.println("allAppointmentsResults title: " + title);

                    String description = allAppointmentsResults.getString("Description");
                    //System.out.println("allAppointmentsResults description: " + description);

                    String location = allAppointmentsResults.getString("Location");
                    //System.out.println("allAppointmentsResults location: " + location);

                    String type = allAppointmentsResults.getString("Type");
                    //System.out.println("allAppointmentsResults type: " + type);

                    LocalDateTime startDateTime = allAppointmentsResults.getTimestamp("Start").toLocalDateTime();
                    //System.out.println("allAppointmentsResults startDateTime: " + startDateTime);

                    LocalDateTime endDateTime = allAppointmentsResults.getTimestamp("End").toLocalDateTime();
                    //System.out.println("allAppointmentsResults endDateTime: " + endDateTime);

                    int customerId = allAppointmentsResults.getInt("Customer_ID");
                    //System.out.println("allAppointmentsResults customerId: " + customerId);

                    int userId = allAppointmentsResults.getInt("User_ID");
                    //System.out.println("allAppointmentResults userId: " + userId);

                    int contactId = allAppointmentsResults.getInt("Contact_ID");
                    //System.out.println("allAppointmentResults contactId: " + contactId);

                    Appointment appointment = new Appointment (appointmentId, title, description, location, type, startDateTime, endDateTime, customerId, userId, contactId);
                    getAllAppointments.add(appointment);
                    //System.out.println("Appointment object populated in getAllAppointments list:   " +
                    //        "  Contact ID:  " + appointment.getContactId() +
                    //        "  Appointment ID:  " + appointment.getAppointmentId());
                }
            } else{System.out.println("ResultSet was null");
            }

        } catch (SQLException throwables) {
            System.out.println("SQLException thrown in getAllCustomers method in the CustomerImplement file");
            throwables.getMessage();
            throwables.getCause();
            throwables.printStackTrace();
        }
    }

    /**
     * Adds appointments from the user interface to the database
     *
     * @param appointmentToAdd An appointment object constructed from the add appointment controller
     * @return Returns a response from the database. If successful, the response is 1, if not successful the response is 0
     */
    public static int addAppointment(Appointment appointmentToAdd) {
        int databaseResponseToUpdate = 0;
        try {

            String title = appointmentToAdd.getTitle();
            String description = appointmentToAdd.getDescription();
            String location = appointmentToAdd.getLocation();
            String type = appointmentToAdd.getType();

            Timestamp startDateTime = Timestamp.valueOf(appointmentToAdd.getStartDateTime());
            Timestamp endDateTime = Timestamp.valueOf(appointmentToAdd.getEndDateTime());

            int customerId = appointmentToAdd.getCustomerId();
            int userId = appointmentToAdd.getUserId();
            int contactId = appointmentToAdd.getContactId();

            //System.out.println("Appointment to send to the DB:  " + appointmentToAdd);


            String insertCustomerIntoDB = "INSERT INTO client_schedule.appointments (" +
                    "Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) " +
                    "VALUES(?,?,?,?,?,?,?,?,?);";


            PreparedStatement insertPreparedStatement = DatabaseConnection.getConnection().prepareStatement(insertCustomerIntoDB);


            insertPreparedStatement.setString(1, title);
            insertPreparedStatement.setString(2, description);
            insertPreparedStatement.setString(3, location);
            insertPreparedStatement.setString(4, type);

            insertPreparedStatement.setTimestamp(5, startDateTime);
            insertPreparedStatement.setTimestamp(6, endDateTime);

            insertPreparedStatement.setInt(7, customerId);
            insertPreparedStatement.setInt(8, userId);
            insertPreparedStatement.setInt(9, contactId);

            databaseResponseToUpdate = insertPreparedStatement.executeUpdate();

            // Updating Query
            if (databaseResponseToUpdate == 1) {
                System.out.println("Table Updated Successfully.......");
                System.out.println("databaseResponseToUpdate: " + databaseResponseToUpdate);
                return databaseResponseToUpdate;
            } else{System.out.println("Database did not add the appointment successfully. DB encountered an error.");
            }

        } catch (SQLException e) {
            System.out.println("addAppointment encountered an error in the AppointmentsImplement file:");
            e.getCause();
            e.getMessage();
            e.printStackTrace();
        }
        return databaseResponseToUpdate;
    }


    /**
     * Filters and retrieved customers from the database by customer ID
     *
     * @throws SQLException An exception that provides information on a database access error or other errors
     */
    public static void getAppointmentsByCustomerID() throws SQLException {
        String selectedCustomerId = String.valueOf(AddAppointmentController.getSelectedCustomerID());
        String allAppointmentsQuery = "SELECT Appointment_ID, Start, End" +
                " FROM client_schedule.appointments " +
                " WHERE appointments.Customer_ID = " + selectedCustomerId;
        PreparedStatement getAppointmentsPreparedStatement = DatabaseConnection.getConnection().prepareStatement(allAppointmentsQuery);
        //System.out.println("getAppointmentsPreparedStatement was successful in AppointmentsImplement.getAppointmentsByCustomerID()");
        ResultSet allAppointmentsResults;

        try {

            if (getAppointmentsPreparedStatement != null) {
                allAppointmentsResults = getAppointmentsPreparedStatement.executeQuery();


                while (allAppointmentsResults.next()) {
                    int appointmentId = allAppointmentsResults.getInt("Appointment_ID");
                    //System.out.println("getAppointmentsByCustomerID() appointmentId: " + appointmentId);

                    LocalDateTime startDateTime = allAppointmentsResults.getTimestamp("Start").toLocalDateTime();
                    //System.out.println("getAppointmentsByCustomerID() startDateTime: " + startDateTime);

                    LocalDateTime endDateTime = allAppointmentsResults.getTimestamp("End").toLocalDateTime();
                    //System.out.println("getAppointmentsByCustomerID() endDateTime: " + endDateTime);

                    Appointment appointmentByCustomerId = new Appointment(appointmentId, startDateTime, endDateTime);

                    appointmentsByCustomerID.add(appointmentByCustomerId);
                    //System.out.println("Appointment object populated in getAllAppointments list: " + appointmentByCustomerId);
                }
            }
            else{System.out.println("ResultSet was null");
            }

        } catch (SQLException throwables) {
            System.out.println("SQLException thrown in getAllCustomers method in the CustomerImplement file");
            throwables.getMessage();
            throwables.getCause();
            throwables.printStackTrace();
        }
    }


    /**
     * Updates the appointment in the database.
     *
     * @param appointment
     * @return
     * @throws SQLException
     */
    public static int updateAppointment(Appointment appointment) throws SQLException {

        int appointmentId = appointment.getAppointmentId();
        String title = appointment.getTitle();
        String description = appointment.getDescription();
        String location = appointment.getLocation();
        String type = appointment.getType();
        Timestamp startDateTime = Timestamp.valueOf(appointment.getStartDateTime());
        Timestamp endDateTime = Timestamp.valueOf(appointment.getEndDateTime());
        int userId = appointment.getUserId();
        int contactId = appointment.getContactId();
        int customerId = appointment.getCustomerId();

        String updateSql = "UPDATE  client_schedule.appointments SET " +
                "Appointment_ID = ?, " +
                "Title = ?, " +
                "Description = ?, " +
                "Location = ?, " +
                "Type = ?, " +
                "Start = ?, " +
                "End = ?, " +
                "Customer_ID = ?, " +
                "User_ID = ?, " +
                "Contact_ID = ? " +
        "WHERE Appointment_ID = " + appointmentId;
        PreparedStatement updateAppointmentPreparedStatement = DatabaseConnection.getConnection().prepareStatement(updateSql);

        updateAppointmentPreparedStatement.setInt(1, appointmentId);
        updateAppointmentPreparedStatement.setString(2, title);
        updateAppointmentPreparedStatement.setString(3, description);
        updateAppointmentPreparedStatement.setString(4, location);
        updateAppointmentPreparedStatement.setString(5, type);
        updateAppointmentPreparedStatement.setTimestamp(6, startDateTime);
        updateAppointmentPreparedStatement.setTimestamp(7, endDateTime);
        updateAppointmentPreparedStatement.setInt(8, customerId);
        updateAppointmentPreparedStatement.setInt(9, userId);
        updateAppointmentPreparedStatement.setInt(10, contactId);

        int dbResponse = updateAppointmentPreparedStatement.executeUpdate(); //returns number of rows affected
        return dbResponse;
    }


    public static int deleteAppointment(int appointmentId) throws SQLException {
        String deleteSql = "DELETE FROM client_schedule.appointments WHERE Appointment_ID = " + appointmentId +";";
        PreparedStatement updateAppointmentPreparedStatement = DatabaseConnection.getConnection().prepareStatement(deleteSql);
        int dbResponse = updateAppointmentPreparedStatement.executeUpdate(); //returns number of rows affected
        return dbResponse;
    }

    public static int deleteAppointmentByCustomerId(int customerId) throws SQLException {
        String deleteSql = "DELETE FROM client_schedule.appointments WHERE Appointment_ID = " + customerId +";";
        PreparedStatement updateAppointmentPreparedStatement = DatabaseConnection.getConnection().prepareStatement(deleteSql);
        int dbResponse = updateAppointmentPreparedStatement.executeUpdate(); //returns number of rows affected
        return dbResponse;
    }


}

