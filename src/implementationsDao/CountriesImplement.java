package implementationsDao;


import Objects.Country;
import interfacesDao.CountriesInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utilities.DatabaseConnection;

import java.sql.*;

public class CountriesImplement extends DatabaseConnection implements CountriesInterface {
    public CountriesImplement() throws SQLException {
    }


    static Connection connection = DatabaseConnection.getConnection();
    static PreparedStatement allCountriesPreparedStatement;
    static PreparedStatement countryNamesPreparedStatement;
    static String sqlQuerry = "SELECT * FROM countries";

    static {
        try {
            allCountriesPreparedStatement = DatabaseConnection.makePreparedStatement(sqlQuerry, connection);
            System.out.println("allCountriesPreparedStatement was successful");
        } catch (SQLException e) {
            System.out.println("allCountriesPreparedStatement in the file CountriesImplement encountered an error");
            e.printStackTrace();
        }
    }

    static {
        try {
            countryNamesPreparedStatement = DatabaseConnection.makePreparedStatement(sqlQuerry, connection);
            System.out.println("countryNamesPreparedStatement was successful");
        } catch (SQLException e) {
            System.out.println("countryNamesPreparedStatement in the file CountriesImplement encountered an error:");
            e.printStackTrace();
        }
    }

    /**
     * Method creates an observable list of all the country objects in the database
     *
     * @throws SQLException Provides information about database access errors and possibly other errors
     *                      for more information about SQLException see https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     */
    public static ObservableList<Country> populateCountriesList() throws SQLException {
        ObservableList<Country> allCountries = null;
        ResultSet allCountryResults;
        try {
            allCountries = FXCollections.observableArrayList();
            String allCountriesSearchStatement = "SELECT Country_ID, Country FROM countries;";
            PreparedStatement getCountriesFromDB = DatabaseConnection.getConnection().prepareStatement(allCountriesSearchStatement);
            allCountryResults = getCountriesFromDB.executeQuery();

            while (allCountryResults.next()) {
                int countryId = allCountryResults.getInt("Country_ID");
                System.out.println(countryId);

                String countryName = allCountryResults.getString("Country");
                System.out.println(countryName);

                Country country = new Country(countryId, countryName);
                allCountries.add(country);
                System.out.println("Country object populated in all countries list");
            }

        } catch (SQLException throwables) {
            System.out.println("SQLException thrown in populateCountriesList() method in the CountriesImplement file");
            throwables.printStackTrace();
        }
        return allCountries;

        //allCountryResults.close();
        //closeConnection();
    }


    /**
     * Method creates an observable list of the country names in the database
     *
     * @return an observable list of country names in the database
     * @throws SQLException Provides information about database access errors and possibly other errors
     *                      for more information about SQLException see https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     */
    public static ObservableList<String> populateCountryNamesList() throws SQLException {
        ObservableList<String> countryNames = null;
        try {
            countryNames = FXCollections.observableArrayList();
            String countryNamesSearchStatement = "SELECT Country FROM countries";
            countryNamesPreparedStatement = DatabaseConnection.getConnection().prepareStatement(countryNamesSearchStatement);
            ResultSet countryNamesResults = countryNamesPreparedStatement.executeQuery(countryNamesSearchStatement);

            while (countryNamesResults.next()) {
                String countryName = countryNamesResults.getString("country");
                System.out.println(countryName);
                countryNames.add(countryName);
                System.out.println("Name was populated in Names list");
            }

            //countryNamesResults.close();
            //closeConnection();

        } catch (Exception e) {
            System.out.println("SQLException thrown in populateCountryNamesList() method in the CountriesImplement file");
            e.printStackTrace();
        }
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