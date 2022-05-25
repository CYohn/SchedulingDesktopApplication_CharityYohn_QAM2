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


//    @FXML
//    void OnAddCustTabReload(ActionEvent event) {
//        FXMLLoader loader = new FXMLLoader();
//        loader = new FXMLLoader();
//        try {
//            AnchorPane addCustomerAnchor = loader.load(getClass().getResource("/views/AddCustomerView.fxml"));
//            addCustomerTab.setContent(addCustomerAnchor);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @FXML
//    void OnSchedTabReload(ActionEvent event) {
//        FXMLLoader loader = new FXMLLoader();
//        loader = new FXMLLoader();
//        try {
//            AnchorPane schedulingAnchor = loader.load(getClass().getResource("/views/ScheduleView.fxml"));
//            schedulingTab.setContent(schedulingAnchor);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @FXML
//    void onAddAppTabReload(ActionEvent event) {
//        FXMLLoader loader = new FXMLLoader();
//        loader = new FXMLLoader();
//        try {
//            AnchorPane addAppointmentAnchor = loader.load(getClass().getResource("/views/AddAppointmentView.fxml"));
//            addAppointmentTab.setContent(addAppointmentAnchor);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @FXML
//    void onModifyApptReload(ActionEvent event) {
//        FXMLLoader loader = new FXMLLoader();
//        loader = new FXMLLoader();
//        try {
//            AnchorPane modifyAppointmentAnchor = loader.load(getClass().getResource("/views/ModifyAppointmentView.fxml"));
//            modifyAppointmentTab.setContent(modifyAppointmentAnchor);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @FXML
//    void onModifyCustTabReload(ActionEvent event) {
//        FXMLLoader loader = new FXMLLoader();
//        loader = new FXMLLoader();
//        try {
//            AnchorPane modifyCustomerAnchor = loader.load(getClass().getResource("/views/ModifyCustomerView.fxml"));
//            modifyCustomerTab.setContent(modifyCustomerAnchor);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Load FXML Tabs
        //Load AddCustomerTab
        AtomicReference<FXMLLoader> loader = new AtomicReference<>(new FXMLLoader());

        //Load SchedulingTab
        try {
            AnchorPane schedulingAnchor = loader.get().load(getClass().getResource("/views/ScheduleView.fxml"));
            schedulingTab.setContent(schedulingAnchor);

        } catch (IOException e) {
            System.out.println("I/O exception ScheduleView not found (Tabbed pane controller fxml loader)");
            e.printStackTrace();
        }

        loader.set(new FXMLLoader());
        try {
            AnchorPane addCustomerAnchor = loader.get().load(getClass().getResource("/views/AddCustomerView.fxml"));
            addCustomerTab.setContent(addCustomerAnchor);

        } catch (IOException e) {
            System.out.println("I/O exception AddCustomerView not found (Tabbed pane controller fxml loader)");
            e.printStackTrace();
        }

        //Load modifyCustomerTab
        loader.set(new FXMLLoader());
        try {
            AnchorPane modifyCustomerAnchor = loader.get().load(getClass().getResource("/views/ModifyCustomerView.fxml"));
            modifyCustomerTab.setContent(modifyCustomerAnchor);


        } catch (IOException e) {
            System.out.println("I/O exception ModifyCustomerView not found (Tabbed pane controller fxml loader)");
            e.printStackTrace();
        }

        //Load AddAppointmentTab
        loader.set(new FXMLLoader());
        try {
            AnchorPane addAppointmentAnchor = loader.get().load(getClass().getResource("/views/AddAppointmentView.fxml"));
            addAppointmentTab.setContent(addAppointmentAnchor);

        } catch (IOException e) {
            System.out.println("I/O exception AddAppointmentView not found (Tabbed pane controller fxml loader)");
            e.printStackTrace();
        }

        //Load modifyAppointmentTab
        loader.set(new FXMLLoader());
        try {
            AnchorPane modifyAppointmentAnchor = loader.get().load(getClass().getResource("/views/ModifyAppointmentView.fxml"));
            modifyAppointmentTab.setContent(modifyAppointmentAnchor);

        } catch (IOException e) {
            System.out.println("I/O exception ModifyAppointmentView not found (Tabbed pane controller fxml loader)");
            e.printStackTrace();
        }

        //Load reportsTab
        loader.set(new FXMLLoader());
        try {
            AnchorPane reportsAnchor = loader.get().load(getClass().getResource("/views/ReportsView.fxml"));
            reportsTab.setContent(reportsAnchor);

        } catch (IOException e) {
            System.out.println("I/O exception ReportsView not found (Tabbed pane controller fxml loader)");
            e.printStackTrace();
        }


        //Change listeners to reload the page on reselection
        schedulingTab.setOnSelectionChanged(t -> {
            if (schedulingTab.isSelected()) {
                System.out.println(schedulingTab.getText() + " is selected");
                //FXMLLoader loader = new FXMLLoader();
                loader.set(new FXMLLoader());
                try {
                    AnchorPane schedulingAnchor = loader.get().load(getClass().getResource("/views/ScheduleView.fxml"));
                    schedulingTab.setContent(schedulingAnchor);

                } catch (IOException e) {
                    System.out.println("I/O exception SchedulingView not found (Tabbed pane controller fxml loader)");
                    e.printStackTrace();
                }
            }
        });

        addCustomerTab.setOnSelectionChanged(t -> {
            if (addCustomerTab.isSelected()) {
                System.out.println(addCustomerTab.getText() + " is selected");
                //FXMLLoader loader = new FXMLLoader();
                loader.set(new FXMLLoader());
                try {
                    AnchorPane addCustomerAnchor = loader.get().load(getClass().getResource("/views/AddCustomerView.fxml"));
                    addCustomerTab.setContent(addCustomerAnchor);

                } catch (IOException e) {
                    System.out.println("I/O exception AddCustomerView not found (Tabbed pane controller fxml loader)");
                    e.printStackTrace();
                }
            }
        });

        modifyCustomerTab.setOnSelectionChanged(t -> {
            if (modifyCustomerTab.isSelected()) {
                System.out.println(modifyCustomerTab.getText() + " is selected");
                //FXMLLoader loader = new FXMLLoader();
                loader.set(new FXMLLoader());
                try {
                    AnchorPane modifyCustomerAnchor = loader.get().load(getClass().getResource("/views/ModifyCustomerView.fxml"));
                    modifyCustomerTab.setContent(modifyCustomerAnchor);

                } catch (IOException e) {
                    System.out.println("I/O exception ModifyCustomerView not found (Tabbed pane controller fxml loader)");
                    e.printStackTrace();
                }
            }
        });

        addAppointmentTab.setOnSelectionChanged(t -> {
            if (addAppointmentTab.isSelected()) {
                System.out.println(addAppointmentTab.getText() + " is selected");
                //FXMLLoader loader = new FXMLLoader();
                loader.set(new FXMLLoader());
                try {
                    AnchorPane addAppointmentAnchor = loader.get().load(getClass().getResource("/views/AddAppointmentView.fxml"));
                    addAppointmentTab.setContent(addAppointmentAnchor);

                } catch (IOException e) {
                    System.out.println("I/O exception AddAppointmentView not found (Tabbed pane controller fxml loader)");
                    e.printStackTrace();
                }
            }
        });

        modifyAppointmentTab.setOnSelectionChanged(t -> {
            if (modifyAppointmentTab.isSelected()) {
                System.out.println(modifyAppointmentTab.getText() + " is selected");
                //FXMLLoader loader = new FXMLLoader();
                loader.set(new FXMLLoader());
                try {
                    AnchorPane modifyAppointmentAnchor = loader.get().load(getClass().getResource("/views/ModifyAppointmentView.fxml"));
                    modifyAppointmentTab.setContent(modifyAppointmentAnchor);

                } catch (IOException e) {
                    System.out.println("I/O exception AddAppointmentView not found (Tabbed pane controller fxml loader)");
                    e.printStackTrace();
                }
            }
        });

        reportsTab.setOnSelectionChanged(t -> {
            if (reportsTab.isSelected()) {
                System.out.println(reportsTab.getText() + " is selected");
                //FXMLLoader loader = new FXMLLoader();
                loader.set(new FXMLLoader());
                try {
                    AnchorPane reportsAnchor = loader.get().load(getClass().getResource("/views/ReportsView.fxml"));
                    reportsTab.setContent(reportsAnchor);

                } catch (IOException e) {
                    System.out.println("I/O exception AddAppointmentView not found (Tabbed pane controller fxml loader)");
                    e.printStackTrace();
                }
            }
        });
    }
}
