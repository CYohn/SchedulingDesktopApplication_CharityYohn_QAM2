package interfacesDao;


import Objects.Country;
import implementationsDao.CountriesImplement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;


public interface CountriesInterface {


    public void updateCountry();

    public void deleteCountry();

    public void addCountry();

}
