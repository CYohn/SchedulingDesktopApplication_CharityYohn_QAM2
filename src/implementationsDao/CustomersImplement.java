package implementationsDao;


import Objects.Customer;
import interfacesDao.CustomersInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utilities.DatabaseConnection;

import java.sql.*;

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

    //public static ObservableList<Customer> customersToSave = FXCollections.observableArrayList();

    static Connection connection = DatabaseConnection.getConnection();
    static PreparedStatement updateCustomersPreparedStatement;


    static {
        try {
            String allCustomersQuery = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, customers.Division_ID, Country " +
                    "FROM client_schedule.customers, first_level_divisions, countries " +
                    "WHERE customers.Division_ID = first_level_divisions.Division_ID " +
                    "AND first_level_divisions.Country_ID = countries.Country_ID";
            updateCustomersPreparedStatement = DatabaseConnection.makePreparedStatement(allCustomersQuery, connection);
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


    public static int addCustomer(Customer customerToSave) throws SQLException {
        int databaseResponseToUpdate = 0;
        try {

                String name = customerToSave.getCustomerName();
                String address = customerToSave.getCustomerAddress();
                String postalCode = customerToSave.getCustomerPostalCode();
                String phone = customerToSave.getCustomerPhone();
                String country = customerToSave.getCountry();
                int division = customerToSave.getCustomerDivisionId();

                System.out.println("Customer to send to the DB: " + customerToSave);


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

                    String customerName = allCustomersResults.getString("Customer_Name");
                    //System.out.println("allCustomersResults customerName: " + customerName);

                    String customerAddress = allCustomersResults.getString("Address");
                    //System.out.println("allCustomersResults customerAddress: " + customerAddress);

                    String customerPostalCode = allCustomersResults.getString("Postal_Code");
                    //System.out.println("allCustomersResults customerPostalCode: " + customerPostalCode);

                    String customerPhone = allCustomersResults.getString("Phone");
                    //System.out.println("allCustomersResults customerPhone: " + customerPhone);

                    int customerDivisionId = allCustomersResults.getInt("Division_ID");
                    //System.out.println("allCustomersResults customerDivisionId: " + customerDivisionId);

                    String division = allCustomersResults.getString("Division");

                    String country = allCustomersResults.getString("Country");
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


    public static int updateCustomer(Customer customerToUpdate) throws SQLException{
            //(int customerId, String customerName, String customerAddress, String customerPostalCode, String customerPhone, int customerDivisionId) throws SQLException {

        int customerId = customerToUpdate.getCustomerId();
        String customerName = customerToUpdate.getCustomerName();
        String customerAddress = customerToUpdate.getCustomerAddress();
        String customerPostalCode = customerToUpdate.getCustomerPostalCode();
        String customerPhone = customerToUpdate.getCustomerPhone();
        int customerDivisionId= customerToUpdate.getCustomerDivisionId();

        String updateSql = "UPDATE  client_schedule.customers SET " +
                "Customer_Name = ?," +
                "Address = ?, " +
                "Postal_Code = ?, " +
                "Phone = ?, " +
                "Division_ID = ? " +
                "WHERE Customer_ID = " + customerId;
        PreparedStatement updateCustomersPreparedStatement = DatabaseConnection.getConnection().prepareStatement(updateSql);
        
        updateCustomersPreparedStatement.setString(1, customerName);
        updateCustomersPreparedStatement.setString(2, customerAddress);
        updateCustomersPreparedStatement.setString(3, customerPostalCode);
        updateCustomersPreparedStatement.setString(4, customerPhone);
        updateCustomersPreparedStatement.setInt(5, customerDivisionId);
        int dbResponse = updateCustomersPreparedStatement.executeUpdate(); //returns number of rows affected
        return dbResponse;
    }


    public static int deleteCustomer(int customerId) throws SQLException {
        String deleteSql = "DELETE FROM client_schedule.customers WHERE Customer_ID = " + customerId +";";
        PreparedStatement deleteCustomerPreparedStatement = DatabaseConnection.getConnection().prepareStatement(deleteSql);
        int dbResponse = deleteCustomerPreparedStatement.executeUpdate(); //returns number of rows affected
        return dbResponse;
    }






}