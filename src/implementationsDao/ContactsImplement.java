package implementationsDao;


import Objects.Contact;
import interfacesDao.ContactsInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class ContactsImplement implements ContactsInterface {

    public static ObservableList<Contact> getAllContacts = ContactsInterface.getAllContacts();

    @Override
    public void updateContact() {

    }

    @Override
    public void deleteContact() {

    }

    @Override
    public void addContact() {

    }
}
