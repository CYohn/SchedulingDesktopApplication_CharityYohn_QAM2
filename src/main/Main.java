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

/**
 * Main class, extends the application
 */
public class Main extends Application {

    /**
     * @param primaryStage This is the primary stage
     * @throws Exception Thorws an IOException
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/views/LoginPageView.fxml"))));
        primaryStage.setTitle("");
        primaryStage.setScene(new Scene(root, 1175, 785));
        primaryStage.show();
    }

    /**
     * The main class and starting point of the application. The main class runs the program arguments. This class also
     * opens and closes the database connection.
     *
     * @param args program arguments
     * @throws SQLException An exception that provides information on a database access error or other errors.
     */
    public static void main(String[] args) throws SQLException {
        DatabaseConnection.makeConnection();

        launch(args);

        DatabaseConnection.closeConnection();
    }

}
