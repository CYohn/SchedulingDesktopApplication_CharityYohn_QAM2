package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;


/**
 * This is the controller for the tabbed pane. It loads the views in a pane inside of each tab. This controller also
 * listens for changes in the tab selection and reloads each page when the tab is re-selected.
 */
public class TabbedPaneController implements Initializable {

    /**
     * The tab to load or reload the Add appointment page.
     */
    @FXML
    private Tab addAppointmentTab;
    /**
     * the tab to load or reload the add customer page.
     */
    @FXML
    private Tab addCustomerTab;
    /**
     * The tab to load or reload the modify appointment page.
     */
    @FXML
    private Tab modifyAppointmentTab;
    /**
     * the tab to load or re-load the modify customer page.
     */
    @FXML
    private Tab modifyCustomerTab;
    /**
     * The tab to load or reload the reports page.
     */
    @FXML
    private Tab reportsTab;
    /**
     * The tab to load or re-load the scheduling page.
     */
    @FXML
    private Tab schedulingTab;


    /**
     * Initializes the page
     *
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Load FXML Tabs
        //Load AddCustomerTab
        AtomicReference<FXMLLoader> loader = new AtomicReference<>(new FXMLLoader());
/**
 * Loads the scheduling tab
 */
        //Load SchedulingTab
        try {
            AnchorPane schedulingAnchor = loader.get().load(getClass().getResource("/views/ScheduleView.fxml"));
            schedulingTab.setContent(schedulingAnchor);

        } catch (IOException e) {
            System.out.println("I/O exception ScheduleView not found (Tabbed pane controller fxml loader)");
            e.printStackTrace();
        }
/**
 * Loads the AddCustomer tab
 */
        loader.set(new FXMLLoader());
        try {
            AnchorPane addCustomerAnchor = loader.get().load(getClass().getResource("/views/AddCustomerView.fxml"));
            addCustomerTab.setContent(addCustomerAnchor);

        } catch (IOException e) {
            System.out.println("I/O exception AddCustomerView not found (Tabbed pane controller fxml loader)");
            e.printStackTrace();
        }

        /**
         * Loads the modify customer tab
         */
        loader.set(new FXMLLoader());
        try {
            AnchorPane modifyCustomerAnchor = loader.get().load(getClass().getResource("/views/ModifyCustomerView.fxml"));
            modifyCustomerTab.setContent(modifyCustomerAnchor);


        } catch (IOException e) {
            System.out.println("I/O exception ModifyCustomerView not found (Tabbed pane controller fxml loader)");
            e.printStackTrace();
        }
/**
 * Loads the AddAppointment tab
 */
        loader.set(new FXMLLoader());
        try {
            AnchorPane addAppointmentAnchor = loader.get().load(getClass().getResource("/views/AddAppointmentView.fxml"));
            addAppointmentTab.setContent(addAppointmentAnchor);

        } catch (IOException e) {
            System.out.println("I/O exception AddAppointmentView not found (Tabbed pane controller fxml loader)");
            e.printStackTrace();
        }

        /**
         * Loads the Modify appointment tab
         */
        loader.set(new FXMLLoader());
        try {
            AnchorPane modifyAppointmentAnchor = loader.get().load(getClass().getResource("/views/ModifyAppointmentView.fxml"));
            modifyAppointmentTab.setContent(modifyAppointmentAnchor);

        } catch (IOException e) {
            System.out.println("I/O exception ModifyAppointmentView not found (Tabbed pane controller fxml loader)");
            e.printStackTrace();
        }

        /**
         * Loads the reports tab
         */
        loader.set(new FXMLLoader());
        try {
            AnchorPane reportsAnchor = loader.get().load(getClass().getResource("/views/ReportsView.fxml"));
            reportsTab.setContent(reportsAnchor);

        } catch (IOException e) {
            System.out.println("I/O exception ReportsView not found (Tabbed pane controller fxml loader)");
            e.printStackTrace();
        }


        /**
         * Change listener which reloads / refreshes the page on reselection
         */
        schedulingTab.setOnSelectionChanged(t -> {
            if (schedulingTab.isSelected()) {
                System.out.println(schedulingTab.getText() + " is selected");
                //FXMLLoader loader = new FXMLLoader();
                loader.set(new FXMLLoader());
                try {
                    System.out.println("********Beginning Reloading of Scheduling Tab********");
                    AnchorPane schedulingAnchor = loader.get().load(getClass().getResource("/views/ScheduleView.fxml"));
                    schedulingTab.setContent(schedulingAnchor);
                    System.out.println("********Finished Reloading Scheduling Tab********");

                } catch (IOException e) {
                    System.out.println("I/O exception SchedulingView not found (Tabbed pane controller fxml loader)");
                    e.printStackTrace();
                }
            }
        });
        /**
         * Change listener which reloads / refreshes the page on reselection
         */
        addCustomerTab.setOnSelectionChanged(t -> {
            if (addCustomerTab.isSelected()) {
                System.out.println(addCustomerTab.getText() + " is selected");
                //FXMLLoader loader = new FXMLLoader();
                loader.set(new FXMLLoader());
                try {
                    System.out.println("********Beginning Reloading of Add Customer Tab********");
                    AnchorPane addCustomerAnchor = loader.get().load(getClass().getResource("/views/AddCustomerView.fxml"));
                    addCustomerTab.setContent(addCustomerAnchor);
                    System.out.println("********Finished Reloading Add Customer Tab********");

                } catch (IOException e) {
                    System.out.println("I/O exception AddCustomerView not found (Tabbed pane controller fxml loader)");
                    e.printStackTrace();
                }
            }
        });
        /**
         * Change listener which reloads / refreshes the page on reselection
         */
        modifyCustomerTab.setOnSelectionChanged(t -> {
            if (modifyCustomerTab.isSelected()) {
                System.out.println(modifyCustomerTab.getText() + " is selected");
                //FXMLLoader loader = new FXMLLoader();
                loader.set(new FXMLLoader());
                try {
                    System.out.println("********Beginning Reloading of Modify Customer Tab********");
                    AnchorPane modifyCustomerAnchor = loader.get().load(getClass().getResource("/views/ModifyCustomerView.fxml"));
                    modifyCustomerTab.setContent(modifyCustomerAnchor);
                    System.out.println("********Finished Reloading Modify Customer Tab********");

                } catch (IOException e) {
                    System.out.println("I/O exception ModifyCustomerView not found (Tabbed pane controller fxml loader)");
                    e.printStackTrace();
                }
            }
        });
        /**
         * Change listener which reloads / refreshes the page on reselection
         */
        addAppointmentTab.setOnSelectionChanged(t -> {
            if (addAppointmentTab.isSelected()) {
                System.out.println(addAppointmentTab.getText() + " is selected");
                //FXMLLoader loader = new FXMLLoader();
                loader.set(new FXMLLoader());
                try {
                    System.out.println("********Beginning Reloading of Add Appointment Tab********");
                    AnchorPane addAppointmentAnchor = loader.get().load(getClass().getResource("/views/AddAppointmentView.fxml"));
                    addAppointmentTab.setContent(addAppointmentAnchor);
                    System.out.println("********Finished Reloading Add Appointment Tab********");

                } catch (IOException e) {
                    System.out.println("I/O exception AddAppointmentView not found (Tabbed pane controller fxml loader)");
                    e.printStackTrace();
                }
            }
        });
        /**
         * Change listener which reloads / refreshes the page on reselection
         */
        modifyAppointmentTab.setOnSelectionChanged(t -> {
            if (modifyAppointmentTab.isSelected()) {
                System.out.println(modifyAppointmentTab.getText() + " is selected");
                //FXMLLoader loader = new FXMLLoader();
                loader.set(new FXMLLoader());
                try {
                    System.out.println("********Beginning Reloading of Modify Appointment Tab********");
                    AnchorPane modifyAppointmentAnchor = loader.get().load(getClass().getResource("/views/ModifyAppointmentView.fxml"));
                    modifyAppointmentTab.setContent(modifyAppointmentAnchor);
                    System.out.println("********Finished Reloading Modify Appointment Tab********");

                } catch (IOException e) {
                    System.out.println("I/O exception AddAppointmentView not found (Tabbed pane controller fxml loader)");
                    e.printStackTrace();
                }
            }
        });
        /**
         * Change listener which reloads / refreshes the page on reselection
         */
        reportsTab.setOnSelectionChanged(t -> {
            if (reportsTab.isSelected()) {
                System.out.println(reportsTab.getText() + " is selected");
                //FXMLLoader loader = new FXMLLoader();
                loader.set(new FXMLLoader());
                try {
                    System.out.println("********Beginning Reloading of Reports Tab********");
                    AnchorPane reportsAnchor = loader.get().load(getClass().getResource("/views/ReportsView.fxml"));
                    reportsTab.setContent(reportsAnchor);
                    System.out.println("********Finished Reloading Reports Tab********");

                } catch (IOException e) {
                    System.out.println("I/O exception AddAppointmentView not found (Tabbed pane controller fxml loader)");
                    e.printStackTrace();
                }
            }
        });
    }
}
