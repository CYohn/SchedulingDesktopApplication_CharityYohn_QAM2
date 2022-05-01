package controllers.SQLHelpers;

import controllers.AddCustomerController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utilities.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DivisionSortsAndSearches extends AddCustomerController {
    static Connection connection = DatabaseConnection.getConnection();
    static PreparedStatement sqlFindCountryIDPreparedStatement;
    static PreparedStatement getDivisionsPreparedStatement;
    public String selectedCountry = getSelectedCountry();

    public DivisionSortsAndSearches() throws SQLException {

    }

    public ObservableList<String> populateDivisionNamesList() throws SQLException {
        ObservableList<String> divisionNames = null;
        try {
            divisionNames = FXCollections.observableArrayList();
            String FindCountryIDSearchStatement = ("SELECT CountryID from countries WHERE country = " + selectedCountry);
            sqlFindCountryIDPreparedStatement = DatabaseConnection.getConnection().prepareStatement(FindCountryIDSearchStatement);
            ResultSet countryID = sqlFindCountryIDPreparedStatement.executeQuery(FindCountryIDSearchStatement);

            String sqlDivisionsQuery = ("SELECT Division FROM first_level_divisions WHERE Country_ID=" + countryID);
            getDivisionsPreparedStatement = DatabaseConnection.getConnection().prepareStatement(sqlDivisionsQuery);
            ResultSet divisionsResults = getDivisionsPreparedStatement.executeQuery(sqlDivisionsQuery);

            while (divisionsResults.next()) {
                String division = divisionsResults.getString("Division");
                System.out.println(division);
                divisionNames.add(division);
                System.out.println("Division was populated in divisionNames list");
            }

            //countryNamesResults.close();
            //closeConnection();

        } catch (Exception e) {
            System.out.println("SQLException thrown in populateDivisionNamesList() method in the DivisionSortsAndSearches file");
            e.printStackTrace();
        }
        return divisionNames;
    }


}
