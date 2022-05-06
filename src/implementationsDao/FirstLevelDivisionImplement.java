package implementationsDao;

import Objects.Country;
import Objects.Customer;
import Objects.FirstLevelDivision;
import controllers.AddCustomerController;
import interfacesDao.CustomersInterface;
import interfacesDao.FirstLevelDivisionsInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utilities.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FirstLevelDivisionImplement extends DatabaseConnection implements FirstLevelDivisionsInterface {

    ObservableList<FirstLevelDivision> allDivisions = FXCollections.observableArrayList();

    public FirstLevelDivisionImplement() throws SQLException {
    }


    static Connection connection = DatabaseConnection.getConnection();
    static PreparedStatement allDivisionsPreparedStatement;
    static String sqlQuerry = "SELECT * FROM first_level_divisions";


    static {
        try {
            allDivisionsPreparedStatement = DatabaseConnection.makePreparedStatement(sqlQuerry, connection);
            System.out.println("allDivisionsPreparedStatement was successful");
        } catch (SQLException e) {
            System.out.println("allDivisionsPreparedStatement in the file FirstLevelDivisionsImplement encountered an error");
            e.getMessage();
            e.getCause();
            e.printStackTrace();
        }
    }


    /**
     * Method creates an observable list of all the first level division objects obtained from the database
     *
     * @throws SQLException Provides information about database access errors and possibly other errors
     *                      for more information about SQLException see https://docs.oracle.com/javase/7/docs/api/java/sql/SQLException.html
     */
    public static ObservableList<FirstLevelDivision> populateDivisionsList() throws SQLException {
        ObservableList<FirstLevelDivision> allDivisions = null;
        ResultSet allDivisionsResults;
        try {
            allDivisions = FXCollections.observableArrayList();
            String allDivisionsSearchStatement = "SELECT Division_ID, Division, Country_ID FROM first_level_divisions ORDER BY Country_ID ASC;";
            PreparedStatement getDivisionsFromDB = DatabaseConnection.getConnection().prepareStatement(allDivisionsSearchStatement);
            allDivisionsResults = getDivisionsFromDB.executeQuery();

            while (allDivisionsResults.next()) {
                int divisionId = allDivisionsResults.getInt("Division_ID");
                //System.out.println("allDivisionsResults divisionId: " + divisionId);

                String divisionName = allDivisionsResults.getString("Division");
                //System.out.println("allDivisionsResults divisionName: " + divisionName);

                int countryId = allDivisionsResults.getInt("Country_ID");
                //System.out.println("allDivisionsResults countryId: " + countryId);

                FirstLevelDivision division = new FirstLevelDivision(divisionId, divisionName, countryId);
                allDivisions.add(division);
                //System.out.println("Division object populated in all divisions list: " + division);
            }
        } catch (SQLException throwables) {
            System.out.println("SQLException thrown in populateDivisionsList() method in the FirstLevelDivisionImplement file");
            throwables.printStackTrace();
        }
        return allDivisions;
    }

    @Override
    public void updateDivision() {

    }

    @Override
    public void deleteDivision() {

    }

    @Override
    public void addDivision() {

    }
}
