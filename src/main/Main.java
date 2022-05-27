package main;

import implementationsDao.AppointmentsImplement;
import implementationsDao.CustomersImplement;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utilities.DatabaseConnection;
import Objects.Appointment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/views/LoginPageView.fxml"))));
        primaryStage.setTitle("");
        primaryStage.setScene(new Scene(root, 1175, 785));
        primaryStage.show();
    }

    public static void main(String[] args) throws SQLException {
        DatabaseConnection.makeConnection();


        LocalDateTime userTime = LocalDateTime.now().plusMinutes(14);
        Appointment appointmentOnLaunch = new Appointment(
                "On Launch",
                "Appointment created to test alert",
                "123 Software Lane, Springfield",
                "De-Briefing", userTime,
                userTime.plusMinutes(44),
                2,
                2,
                2
        );

        AppointmentsImplement.addAppointment(appointmentOnLaunch);

        launch(args);

        DatabaseConnection.closeConnection();
    }

}
