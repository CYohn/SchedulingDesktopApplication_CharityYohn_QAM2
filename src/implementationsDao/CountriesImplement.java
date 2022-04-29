package implementationsDao;


import Objects.Country;
import interfacesDao.CountriesInterface;
import javafx.collections.ObservableList;
import utilities.DatabaseConnection;

import java.sql.*;

public class CountriesImplement extends DatabaseConnection {
    public CountriesImplement() throws SQLException {
    }

    static String allCountriesSearchStatement = "SELECT * FROM countries;";
    static String countryNamesSearchStatement = "SELECT country FROM countries";
    static Connection makeConnection = DatabaseConnection.makeConnection();
    static PreparedStatement allCountriesPreparedStatement;
    static PreparedStatement countryNamesPreparedStatement;

    static {
        try {
            allCountriesPreparedStatement = DatabaseConnection.makePreparedStatement(allCountriesSearchStatement, makeConnection);
        } catch (SQLException e) {
            System.out.println("allCountriesPreparedStatement in the file CountriesImplement encountered an error");
            e.printStackTrace();
        }
    }

    static {
        try {
            countryNamesPreparedStatement = DatabaseConnection.makePreparedStatement(countryNamesSearchStatement, makeConnection);
        } catch (SQLException e) {
            System.out.println("countryNamesPreparedStatement in the file CountriesImplement encountered an error:");
            e.printStackTrace();
        }
    }

    Connection closeConnection = DatabaseConnection.closeConnection();



    /**
     * Method creates an observable list of all the country objects in the database
     *
     * @return an observable list of all the countries in the database
     * @throws SQLException Provides information about database access errors and possibly other errors
     *                      for more information about SQLException see https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     */
    public static ObservableList<Country> getAllCountriesFromDB() throws SQLException {
        ObservableList<Country> allCountries = CountriesInterface.getAllCountriesFromDB();
        try {
            DatabaseConnection.getConnection().prepareStatement(allCountriesSearchStatement);
            allCountriesPreparedStatement.executeQuery();

            ResultSet allCountriesResults = allCountriesPreparedStatement.getResultSet();

            if (allCountriesResults.next()) {
                Country country = new Country(
                        allCountriesResults.getInt("countryId"),
                        allCountriesResults.getString("country"));

                allCountries.addAll(country);
            }
            DatabaseConnection.closeConnection();

        } catch (SQLException e) {
            System.out.println("getAllCountriesFromDB method in class CountriesImplement encountered an error: ");
            e.printStackTrace();
        }
        return allCountries;
    }


    /**
     * Method creates an observable list of the country names in the database
     *
     * @return an observable list of country names in the database
     * @throws SQLException Provides information about database access errors and possibly other errors
     *                      for more information about SQLException see https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     */
    public static ObservableList<Country> getAllCountryNamesFromDB() {
        ObservableList<Country> allCountryNames = CountriesInterface.getAllCountriesFromDB();
        try {
            DatabaseConnection.getConnection().prepareStatement(countryNamesSearchStatement);
            countryNamesPreparedStatement.executeQuery();

            ResultSet allCountryNamesResults = countryNamesPreparedStatement.getResultSet();

            if (allCountryNamesResults.next()) {
                Country countryName = new Country(
                        allCountryNamesResults.getString("country"));
                allCountryNames.addAll(countryName);
            }
            DatabaseConnection.closeConnection();

        } catch (SQLException e) {
            System.out.println("getAllCountriesFromDB method in class CountriesImplement encountered an error: ");
            e.printStackTrace();
        }
        return allCountryNames;
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
}