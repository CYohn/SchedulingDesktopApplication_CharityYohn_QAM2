package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utilities.DatabaseConnection;

import java.util.Objects;


public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load((Objects.requireNonNull(getClass().getResource("/views/LoginPageView.fxml"))));
        primaryStage.setTitle("");
        primaryStage.setScene(new Scene(root, 1175, 785));
        primaryStage.show();
    }

    public static void main(String[] args){
        DatabaseConnection.makeConnection();
        launch(args);
    }

}
