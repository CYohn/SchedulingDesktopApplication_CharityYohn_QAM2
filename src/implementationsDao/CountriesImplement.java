package implementationsDao;


import Objects.Country;
import interfacesDao.CountriesInterface;
import javafx.collections.ObservableList;
import utilities.DatabaseConnection;

import java.sql.*;

public class CountriesImplement extends DatabaseConnection {


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

    public CountriesImplement() throws SQLException {
    }

    /**
     * Method creates an observable list of all the country objects in the database
     *
     * @return an observable list of all the countries in the database
     * @throws SQLException Provides information about database access errors and possibly other errors
     *                      for more information about SQLException see https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     */
    public static ObservableList<Country> getAllCountriesFromDB() throws SQLException {
        ObservableList<Country> allCountries = CountriesInterface.getAllCountries();
        try {
            DatabaseConnection.makeConnection();
            allCountriesPreparedStatement.executeQuery();
        } catch (SQLException e) {
            System.out.println("getAllCountriesFromDB method in class CountriesImplement encountered an error: ");
            e.printStackTrace();
        }
        ResultSet allCountriesResults = allCountriesPreparedStatement.getResultSet();
        allCountries.addAll((Country) allCountriesResults);
        DatabaseConnection.closeConnection();

        return allCountries;
    }


    /**
     * Method creates an observable list of the country names in the database
     *
     * @return an observable list of country names in the database
     * @throws SQLException Provides information about database access errors and possibly other errors
     *                      for more information about SQLException see https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     */
    public static ObservableList<Country> getCountryNamesFromDB() throws SQLException {
        ObservableList<Country> countryNames = CountriesInterface.getAllCountries();
        try {
            DatabaseConnection.makeConnection();
            countryNamesPreparedStatement.executeQuery();
        } catch (SQLException e) {
            System.out.println("getCountryNamesFromDB method in class CountriesImplement encountered an error");
            e.printStackTrace();
        }
        ResultSet allCountriesResults = countryNamesPreparedStatement.getResultSet();
        countryNames.addAll((Country) allCountriesResults);
        DatabaseConnection.closeConnection();
        return countryNames;
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