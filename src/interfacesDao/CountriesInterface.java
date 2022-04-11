package interfacesDao;


import Objects.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public interface CountriesInterface {
    ObservableList<Country> allCountries = FXCollections.observableArrayList();

    static ObservableList<Country> getAllCountries() {
        return allCountries;
    }


    public void updateCountry();

    public void deleteCountry();

    public void addCountry();

}
