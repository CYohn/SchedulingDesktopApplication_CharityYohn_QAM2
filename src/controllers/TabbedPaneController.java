package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TabbedPaneController implements Initializable {

    @FXML
    private Tab addAppointmentTab;

    @FXML
    private Tab addCustomerTab;

    @FXML
    private Tab modifyAppointmentTab;

    @FXML
    private Tab modifyCustomerTab;

    @FXML
    private Tab reportsTab;

    @FXML
    private Tab schedulingTab;

    @FXML
    private Pane addCustomerPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Load FXML Tabs
        //Load AddCustomerTab
        FXMLLoader loader = new FXMLLoader();
        try {
            Pane addCustomerPane = loader.load(getClass().getResource("/views/AddCustomerView.fxml"));
            addCustomerTab.setContent(addCustomerPane);

        } catch (IOException e) {
            System.out.println("I/O exception Add Customer View not found (Tabbed pane controller fxml loader)");
            e.printStackTrace();
        }

        //Load modifyCustomerTab
        loader = new FXMLLoader();
        try {
            Pane addCustomerPane = loader.load(getClass().getResource("/views/AddCustomerView.fxml"));
            addCustomerTab.setContent(addCustomerPane);

        } catch (IOException e) {
            System.out.println("I/O exception Add Customer View not found (Tabbed pane controller fxml loader)");
            e.printStackTrace();
        }

        //Load AddAppointmentTab
        loader = new FXMLLoader();
        try {
            Pane addCustomerPane = loader.load(getClass().getResource("/views/AddCustomerView.fxml"));
            addCustomerTab.setContent(addCustomerPane);

        } catch (IOException e) {
            System.out.println("I/O exception Add Customer View not found (Tabbed pane controller fxml loader)");
            e.printStackTrace();
        }

        //Load modifyAppointmentTab
        loader = new FXMLLoader();
        try {
            Pane addCustomerPane = loader.load(getClass().getResource("/views/AddCustomerView.fxml"));
            addCustomerTab.setContent(addCustomerPane);

        } catch (IOException e) {
            System.out.println("I/O exception Add Customer View not found (Tabbed pane controller fxml loader)");
            e.printStackTrace();
        }

        //Load SchedulingTab
        loader = new FXMLLoader();
        try {
            Pane addCustomerPane = loader.load(getClass().getResource("/views/AddCustomerView.fxml"));
            addCustomerTab.setContent(addCustomerPane);

        } catch (IOException e) {
            System.out.println("I/O exception Add Customer View not found (Tabbed pane controller fxml loader)");
            e.printStackTrace();
        }

        //Load reportsTab
        loader = new FXMLLoader();
        try {
            Pane addCustomerPane = loader.load(getClass().getResource("/views/AddCustomerView.fxml"));
            addCustomerTab.setContent(addCustomerPane);

        } catch (IOException e) {
            System.out.println("I/O exception Add Customer View not found (Tabbed pane controller fxml loader)");
            e.printStackTrace();
        }
    }
}
