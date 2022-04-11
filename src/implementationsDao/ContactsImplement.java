package implementationsDao;


import Objects.Contact;
import interfacesDao.ContactsInterface;
import javafx.collections.ObservableList;


public class ContactsImplement implements ContactsInterface {

    ObservableList<Contact> getAllContacts = ContactsInterface.getAllContacts();

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
