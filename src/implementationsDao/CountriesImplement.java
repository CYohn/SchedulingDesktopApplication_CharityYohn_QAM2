package implementationsDao;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utilities.DatabaseConnection;

import java.sql.*;

/**
 * This class interacts with the database. The class gets all countries from the database and adds them to an observable list.
 */
public class CountriesImplement extends DatabaseConnection {
    public CountriesImplement() throws SQLException {
    }

    /**
     * A prepared statement to get the connection and send the SQL query to the database.
     */
    static PreparedStatement countryNamesPreparedStatement;

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
                //System.out.println("Name was populated in Names list");
            }


        } catch (Exception e) {
            System.out.println("SQLException thrown in populateCountryNamesList() method in the CountriesImplement file");
            e.printStackTrace();
        }
        return countryNames;
    }
}