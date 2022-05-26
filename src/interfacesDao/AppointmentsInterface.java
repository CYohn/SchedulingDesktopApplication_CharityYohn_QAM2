package interfacesDao;

import Objects.Appointment;
import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public interface AppointmentsInterface {
    ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();



    public static int addAppointment(Appointment appointmentToAdd){return 0;};

    public static int updateAppointment(Appointment appointment) {
        return 0;
    }

    public void deleteAppointment();



}
