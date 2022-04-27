package interfacesDao;


import Objects.Country;
import implementationsDao.CountriesImplement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;


public interface CountriesInterface {


    ObservableList<Country> countryNames = FXCollections.observableArrayList();
    ObservableList<Country> allCountries = getAllCountriesFromDB();

    static ObservableList<Country> getAllCountriesFromDB() {
        return allCountries;
    }


    static ObservableList<Country> getAllCountries() {
        return allCountries;
    }


    public void updateCountry();

    public void deleteCountry();

    public void addCountry();

}
