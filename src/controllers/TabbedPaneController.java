package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TabbedPaneController implements Initializable {

    //TODO: ADD THE ALERT FOR APPOINTMENTS WITHIN 15 MINUTES TO INITIALIZE

    @FXML
    private AnchorPane addAppointmentAnchor;

    @FXML
    private Tab addAppointmentTab;

    @FXML
    private AnchorPane addCustomerAnchor;

    @FXML
    private Tab addCustomerTab;

    @FXML
    private AnchorPane modifyAppointmentAnchor;

    @FXML
    private Tab modifyAppointmentTab;

    @FXML
    private AnchorPane modifyCustomerAnchor;

    @FXML
    private Tab modifyCustomerTab;

    @FXML
    private AnchorPane reportsAnchor;

    @FXML
    private Tab reportsTab;

    @FXML
    private AnchorPane schedulingAnchor;

    @FXML
    private Tab schedulingTab;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Load FXML Tabs
        //Load AddCustomerTab
        FXMLLoader loader = new FXMLLoader();

        //Load SchedulingTab
        try {
            AnchorPane schedulingAnchor = loader.load(getClass().getResource("/views/ScheduleView.fxml"));
            schedulingTab.setContent(schedulingAnchor);

        } catch (IOException e) {
            System.out.println("I/O exception ScheduleView not found (Tabbed pane controller fxml loader)");
            e.printStackTrace();
        }

        loader = new FXMLLoader();
        try {
            AnchorPane addCustomerAnchor = loader.load(getClass().getResource("/views/AddCustomerView.fxml"));
            addCustomerTab.setContent(addCustomerAnchor);

        } catch (IOException e) {
            System.out.println("I/O exception AddCustomerView not found (Tabbed pane controller fxml loader)");
            e.printStackTrace();
        }

        //Load modifyCustomerTab
        loader = new FXMLLoader();
        try {
            AnchorPane modifyCustomerAnchor = loader.load(getClass().getResource("/views/ModifyCustomerView.fxml"));
            modifyCustomerTab.setContent(modifyCustomerAnchor);

        } catch (IOException e) {
            System.out.println("I/O exception ModifyCustomerView not found (Tabbed pane controller fxml loader)");
            e.printStackTrace();
        }

        //Load AddAppointmentTab
        loader = new FXMLLoader();
        try {
            AnchorPane addAppointmentAnchor = loader.load(getClass().getResource("/views/AddAppointmentView.fxml"));
            addAppointmentTab.setContent(addAppointmentAnchor);

        } catch (IOException e) {
            System.out.println("I/O exception AddAppointmentView not found (Tabbed pane controller fxml loader)");
            e.printStackTrace();
        }

        //Load modifyAppointmentTab
        loader = new FXMLLoader();
        try {
            AnchorPane modifyAppointmentAnchor = loader.load(getClass().getResource("/views/ModifyAppointmentView.fxml"));
            modifyAppointmentTab.setContent(modifyAppointmentAnchor);

        } catch (IOException e) {
            System.out.println("I/O exception ModifyAppointmentView not found (Tabbed pane controller fxml loader)");
            e.printStackTrace();
        }

        //Load reportsTab
        loader = new FXMLLoader();
        try {
            AnchorPane reportsAnchor = loader.load(getClass().getResource("/views/ReportsView.fxml"));
            reportsTab.setContent(reportsAnchor);

        } catch (IOException e) {
            System.out.println("I/O exception ReportsView not found (Tabbed pane controller fxml loader)");
            e.printStackTrace();
        }
    }
}
