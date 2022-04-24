package implementationsDao;


import Objects.Country;
import interfacesDao.CountriesInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utilities.DatabaseConnection;

import java.sql.*;

public class CountriesImplement extends DatabaseConnection {

    public ObservableList<Country> getCountriesFromDatabase() throws SQLException {
        ObservableList<Country> allCountries = CountriesInterface.getAllCountries();
        ObservableList<Country> countries = FXCollections.observableArrayList();

        String searchStatement = "SELECT * FROM countries;";


        PreparedStatement preparedStatement = DatabaseConnection.getPreparedStatement();

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            // Forward scroll resultSet
            while (resultSet.next()) {

                Country country = new Country(
                        resultSet.getInt("Country_ID"),
                        resultSet.getString("Country")
                );

                countries.add(country);
            }
            return countries;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }

    }


    //@Override
    public void updateCountry() {

    }

    //@Override
    public void deleteCountry() {

    }

    //@Override
    public void addCountry() {

    }
};