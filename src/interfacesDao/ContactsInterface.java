package interfacesDao;

import Objects.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public interface ContactsInterface {
    ObservableList<Contact> allContacts = FXCollections.observableArrayList();

    static ObservableList<Contact> getAllContacts() {
        return allContacts;
    }


    public void updateContact();

    public void deleteContact();

    public void addContact();

}
