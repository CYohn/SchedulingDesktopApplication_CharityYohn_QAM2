package interfacesDao;


import Objects.Country;
import implementationsDao.CountriesImplement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;


public interface CountriesInterface {


    ObservableList<Country> countryNames = FXCollections.observableArrayList();
    ObservableList<Country> allCountries = FXCollections.observableArrayList();

    static ObservableList<Country> getAllCountriesFromDB() {
        return allCountries;
    }


    static ObservableList<Country> getAllCountryNamesFromDB() {
        return countryNames;
    }


    public void updateCountry();

    public void deleteCountry();

    public void addCountry();

}
