package implementationsDao;

import Objects.Appointment;
import interfacesDao.AppointmentsInterface;
import javafx.collections.ObservableList;
import utilities.DatabaseConnection;

import java.sql.*;
import java.time.LocalDateTime;

public class AppointmentsImplement implements AppointmentsInterface {

    ObservableList<Appointment> getAllAppointments = AppointmentsInterface.getAllAppointments();


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

            System.out.println("Appointment to send to the DB:  " + appointmentToAdd);


                String insertCustomerIntoDB = "INSERT INTO client_schedule.appointments (" +
                        "Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) " +
                        "VALUES(?,?,?,?,?,?,?,?,?);";


                PreparedStatement insertPreparedStatement = DatabaseConnection.getConnection().prepareStatement(insertCustomerIntoDB);

                //countryPreparesStatement.setString(1, country);
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
                }
                else{System.out.println("Database did not add the appointment successfully. DB encountered an error.");}

        } catch (SQLException e) {
            System.out.println("addAppointment encountered an error in the AppointmentsImplement file:");
            e.getCause();
            e.getMessage();
            e.printStackTrace();
        }
        return databaseResponseToUpdate;
    }




    @Override
    public void updateAppointment() {

    }

    @Override
    public void deleteAppointment() {

    }




}

