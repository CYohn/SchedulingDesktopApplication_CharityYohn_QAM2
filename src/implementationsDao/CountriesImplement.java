package implementationsDao;


import Objects.Country;
import interfacesDao.CountriesInterface;
import javafx.collections.ObservableList;

public class CountriesImplement implements CountriesInterface {

    ObservableList<Country> getAllCountries = CountriesInterface.getAllCountries();

    @Override
    public void updateCountry() {

    }

    @Override
    public void deleteCountry() {

    }

    @Override
    public void addCountry() {

    }
}
