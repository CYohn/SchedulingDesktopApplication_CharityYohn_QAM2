package implementationsDao;


import Objects.Customer;
import Objects.FirstLevelDivision;
import interfacesDao.CustomersInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utilities.DatabaseConnection;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.Locale;
import java.util.Objects;

public class CustomersImplement extends DatabaseConnection implements CustomersInterface {

    private static String name;
    private static String address;
    private static String postalCode;
    private static String phone;
    private static int division;

    private static String Customer_Name;
    private static String Address;
    private static String Postal_Code;
    private static String Phone;
    private static int Division_ID;

    public static ObservableList<Customer> getAllCustomers = CustomersInterface.getAllCustomers();

    public static ObservableList<Customer> customersToSave = FXCollections.observableArrayList();

    static Connection connection = DatabaseConnection.getConnection();
    static PreparedStatement customersImplementPreparedStatement;


    static {
        try {
            String allCustomersQuery = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, customers.Division_ID, Country " +
                    "FROM client_schedule.customers, first_level_divisions, countries " +
                    "WHERE customers.Division_ID = first_level_divisions.Division_ID " +
                    "AND first_level_divisions.Country_ID = countries.Country_ID";
            customersImplementPreparedStatement = DatabaseConnection.makePreparedStatement(allCustomersQuery, connection);
            System.out.println("customersImplementPreparedStatement was successful");
        } catch (SQLException e) {
            System.out.println("customerImplementPreparedStatement in the file CustomersImplement encountered an error");
            e.getMessage();
            e.getCause();
            e.printStackTrace();
        }
    }

    public CustomersImplement() throws SQLException {
    }


    public static int addCustomer(ObservableList<Customer> customersToSave) throws SQLException {
        int databaseResponseToUpdate = 0;
        try {
            for (Customer customer : customersToSave) {
                String name = customer.getCustomerName();
                String address = customer.getCustomerAddress();
                String postalCode = customer.getCustomerPostalCode();
                String phone = customer.getCustomerPhone();
                String country = customer.getCountry();
                int division = customer.getCustomerDivisionId();

                System.out.println("Customer to send to the DB: " + customer);


                String insertCustomerIntoDB = "INSERT INTO client_schedule.customers (" +
                        "Customer_Name, Address, Postal_Code, Phone, Division_ID) " +
                        "VALUES(?,?,?,?,?);";


                PreparedStatement insertPreparedStatement = DatabaseConnection.getConnection().prepareStatement(insertCustomerIntoDB);

                //countryPreparesStatement.setString(1, country);
                insertPreparedStatement.setString(1, name);
                insertPreparedStatement.setString(2, address);
                insertPreparedStatement.setString(3, postalCode);
                insertPreparedStatement.setString(4, phone);
                insertPreparedStatement.setInt(5, division);

                databaseResponseToUpdate = insertPreparedStatement.executeUpdate();

                // Updating Query
                if (databaseResponseToUpdate == 1) {
                    System.out.println("Table Updated Successfully.......");
                    System.out.println("databaseResponseToUpdate: " + databaseResponseToUpdate);
                    return databaseResponseToUpdate;
                }

            }
        } catch (SQLException e) {
            System.out.println("addCustomer encountered an error in the CustomersImplement file:");
            e.getCause();
            e.getMessage();
            e.printStackTrace();
        }
        return databaseResponseToUpdate;
    }


    public static void getAllCustomers() throws SQLException {
        String allCustomersQuery = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, customers.Division_ID, first_level_divisions.Division, Country" +
                " FROM client_schedule.customers, first_level_divisions, countries " +
                " WHERE customers.Division_ID = first_level_divisions.Division_ID " +
                " AND first_level_divisions.Country_ID = countries.Country_ID;";
        PreparedStatement getCustomersPreparedStatement = DatabaseConnection.getConnection().prepareStatement(allCustomersQuery);
        System.out.println("customersImplementPreparedStatement was successful in CustomersImplement.getAllCustomers()");
        ResultSet allCustomersResults;

        try {

            if (getCustomersPreparedStatement != null) {
                allCustomersResults = getCustomersPreparedStatement.executeQuery();


                while (allCustomersResults.next()) {
                    int customerId = allCustomersResults.getInt("Customer_ID");
                    //System.out.println("allCustomersResults customerId: " + customerId);

                    String customerName = allCustomersResults.getString("Customer_Name").toLowerCase(Locale.ROOT);
                    //System.out.println("allCustomersResults customerName: " + customerName);

                    String customerAddress = allCustomersResults.getString("Address").toLowerCase(Locale.ROOT);
                    //System.out.println("allCustomersResults customerAddress: " + customerAddress);

                    String customerPostalCode = allCustomersResults.getString("Postal_Code").toLowerCase(Locale.ROOT);
                    //System.out.println("allCustomersResults customerPostalCode: " + customerPostalCode);

                    String customerPhone = allCustomersResults.getString("Phone").toLowerCase(Locale.ROOT);
                    //System.out.println("allCustomersResults customerPhone: " + customerPhone);

                    int customerDivisionId = allCustomersResults.getInt("Division_ID");
                    //System.out.println("allCustomersResults customerDivisionId: " + customerDivisionId);

                    String division = allCustomersResults.getString("Division").toLowerCase(Locale.ROOT);

                    String country = allCustomersResults.getString("Country").toLowerCase(Locale.ROOT);
                    //System.out.println("allCustomersResults country: " + country);

                    Customer customer = new Customer(customerId, customerName, customerAddress, customerPostalCode, customerPhone, customerDivisionId, division, country);
                    getAllCustomers.add(customer);
                    //System.out.println("Customer object populated in getAllCustomers list: " + customer);
                }
            }
            else{System.out.println("ResultSet was null");}

        }
        catch(SQLException throwables){
            System.out.println("SQLException thrown in getAllCustomers method in the CustomerImplement file");
            throwables.getMessage();
            throwables.getCause();
            throwables.printStackTrace();
        }
    }


    public static int updateCustomer(int customerId, String name, String address, String postalCode, String phone, int division) throws SQLException {
        String updateSql = "UPDATE customer SET Customer_Name = ?," +
                "Address = ?, " +
                "Postal_Code = ?, " +
                "Phone = ?, " +
                "Division = ?, " +
                "WHERE Customer_ID = " + customerId;
        customersImplementPreparedStatement = DatabaseConnection.makePreparedStatement(updateSql, connection);
        customersImplementPreparedStatement.setString(1, name);
        customersImplementPreparedStatement.setString(2, address);
        customersImplementPreparedStatement.setString(3, postalCode);
        customersImplementPreparedStatement.setString(4, phone);
        customersImplementPreparedStatement.setInt(5, division);
        int dbResponse = customersImplementPreparedStatement.executeUpdate(); //returns number of rows affected
        return dbResponse;
    }


    public static int deleteCustomer(int customerId) throws SQLException {
        String deleteSql = "DELETE FROM customers WHERE Customer_ID = ?";
        customersImplementPreparedStatement = DatabaseConnection.makePreparedStatement(deleteSql, connection);
        customersImplementPreparedStatement.setInt(1, customerId);
        int dbResponse = customersImplementPreparedStatement.executeUpdate(); // returns the number of rows affected
        return dbResponse;
    }


//    public static String getDivisionName(ObservableList<Customer> getAllCustomers) throws SQLException {
//        String divisionName = null;
//        for (Customer customer : getAllCustomers) {
//
//            String searchForDivisionQuery = "SELECT from Division first_level_divisions.Division " +
//                    "WHERE customers.Division_ID = first_level_divisions.Division_ID";
//            PreparedStatement searchForDivision = DatabaseConnection.makePreparedStatement(searchForDivisionQuery, connection);
//            System.out.println("customersImplementPreparedStatement was successful in CustomersImplement.getAllCustomers()");
//            ResultSet divisionNameResults;
//
//            try {
//
//                divisionNameResults = searchForDivision.executeQuery();
//
//                while (divisionNameResults.next()) {
//                    divisionName = divisionNameResults.getString("Division");
//                    System.out.println("divisionResults in CustomerImplement.getDivisionName(): " + divisionName);
//                    return divisionName;
//                }
//            } catch (SQLException throwables) {
//                throwables.printStackTrace();
//            }
//
//        }
//        return divisionName;
//    }

}