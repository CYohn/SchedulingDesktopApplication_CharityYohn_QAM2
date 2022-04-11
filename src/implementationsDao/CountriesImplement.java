package implementationsDao;


import Objects.Country;
import interfacesDao.ContactsInterface;
import interfacesDao.CountriesInterface;
import javafx.collections.ObservableList;

public class CountriesImplement implements ContactsInterface {

    ObservableList<Country> getAllCountries = CountriesInterface.getAllCountries();

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
