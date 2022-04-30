package implementationsDao;


import Objects.Country;
import interfacesDao.CountriesInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utilities.DatabaseConnection;

import java.sql.*;

public class CountriesImplement extends DatabaseConnection {
    public CountriesImplement() throws SQLException {
    }

    public static ObservableList<Country> allCountries = FXCollections.observableArrayList();
    public static ObservableList<String> countryNames = FXCollections.observableArrayList();

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
     * @throws SQLException Provides information about database access errors and possibly other errors
     *                      for more information about SQLException see https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     */
    private void populateCountriesList() throws SQLException {
        try {
            PreparedStatement getCountriesFromDB = DatabaseConnection.getConnection().prepareStatement(allCountriesSearchStatement);
            ResultSet allCountryResults = getCountriesFromDB.executeQuery();

            while (allCountryResults.next()) {
                String countryIDString = allCountryResults.getString("countryID");
                int countryIdNumber = Integer.parseInt(countryIDString);
                String countryName = allCountryResults.getString("country");

                Country country = new Country(countryIdNumber, countryName);
                allCountries.addAll(country);
            }

            allCountryResults.close();
            closeConnection();
        } catch (SQLException throwables) {
            System.out.println("SQLException thrown in populateCountriesList() method in the CountriesImplement file");
            throwables.printStackTrace();
        }
    }


    /**
     * Method creates an observable list of the country names in the database
     *
     * @return an observable list of country names in the database
     * @throws SQLException Provides information about database access errors and possibly other errors
     *                      for more information about SQLException see https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     */
    private void populateCountryNamesList() throws SQLException {
        try {
            countryNamesPreparedStatement = DatabaseConnection.getConnection().prepareStatement(countryNamesSearchStatement);
            ResultSet countryNamesResults = countryNamesPreparedStatement.executeQuery(countryNamesSearchStatement);

            while (countryNamesResults.next()) {
                String countryName = countryNamesResults.getString("country");
                countryNames.addAll(countryName);
            }

            countryNamesResults.close();
            closeConnection();

        } catch (Exception e) {
            System.out.println("SQLException thrown in populateCountryNamesList() method in the CountriesImplement file");
            e.printStackTrace();
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
}