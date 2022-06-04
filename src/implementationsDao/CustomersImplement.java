package implementationsDao;


import Objects.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utilities.DatabaseConnection;

import java.sql.*;

/**
 * Interacts with the database to perform create, read, update, and delete operations.
 */
public class CustomersImplement extends DatabaseConnection {

    /**
     * An observableList to hold all customers from the database.
     */
    public static ObservableList<Customer> getAllCustomers = FXCollections.observableArrayList();

    public CustomersImplement() throws SQLException {
    }

    /**
     * Saves a customer in the database. The method takes in a customer object generated from the add customer controller
     *
     * @param customerToSave A customer object created in the AddCustomerController. The save function in the add
     *                       customer controller takes user input and creates a customer object to save to the database.
     * @return Returns a database response with the number of rows affected. Since we are saving one customer at a time,
     * a successful response is 1. An unsuccessful update returns 0.
     * @throws SQLException An exception that provides information on a database access error or other errors.
     */
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

    /**
     * Gets all customers from the database. The method gets all customers and adds them to the allCustomers observableList.
     *
     * @throws SQLException An exception that provides information on a database access error or other errors.
     */
    public static void getAllCustomers() throws SQLException {
        getAllCustomers.clear();
        String allCustomersQuery = "SELECT Customer_ID, Customer_Name, Address, Postal_Code, Phone, customers.Division_ID, first_level_divisions.Division, Country" +
                " FROM client_schedule.customers, first_level_divisions, countries " +
                " WHERE customers.Division_ID = first_level_divisions.Division_ID " +
                " AND first_level_divisions.Country_ID = countries.Country_ID " +
                "ORDER BY Customer_Name asc;";
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
            else{System.out.println("ResultSet was null");
            }

        } catch (SQLException throwables) {
            System.out.println("SQLException thrown in getAllCustomers method in the CustomerImplement file");
            throwables.getMessage();
            throwables.getCause();
            throwables.printStackTrace();
        }
    }

    /**
     * Updates an existing customer in the database. The method updates only the customer with the matching customer ID.
     *
     * @param customerToUpdate A customer object created in the ModifyCustomerController. The user selects a customer to
     *                         update and the program creates a customer object from the information input by the user.
     *                         The customer object is then sent to this method as a parameter to update the corresponding
     *                         in the database.
     * @return Returns a database response with the number of rows affected. Since we are saving one customer at a time,
     * a successful response is 1. An unsuccessful update returns 0.
     * @throws SQLException An exception that provides information on a database access error or other errors.
     */
    public static int updateCustomer(Customer customerToUpdate) throws SQLException {
        //(int customerId, String customerName, String customerAddress, String customerPostalCode, String customerPhone, int customerDivisionId) throws SQLException {

        int customerId = customerToUpdate.getCustomerId();
        String customerName = customerToUpdate.getCustomerName();
        String customerAddress = customerToUpdate.getCustomerAddress();
        String customerPostalCode = customerToUpdate.getCustomerPostalCode();
        String customerPhone = customerToUpdate.getCustomerPhone();
        int customerDivisionId = customerToUpdate.getCustomerDivisionId();

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

    /**
     * Deletes a customer from the database. Deletes only the corresponding customer with a matching customer ID.
     *
     * @param customerId The ID of the customer selected in the ModifyCustomerController.
     * @return Returns a database response with the number of rows affected. Since we are saving one customer at a time,
     * a successful response is 1. An unsuccessful update returns 0.
     * @throws SQLException An exception that provides information on a database access error or other errors.
     */
    public static int deleteCustomer(int customerId) throws SQLException {
        String deleteSql = "DELETE FROM client_schedule.customers WHERE Customer_ID = " + customerId + ";";
        PreparedStatement deleteCustomerPreparedStatement = DatabaseConnection.getConnection().prepareStatement(deleteSql);
        int dbResponse = deleteCustomerPreparedStatement.executeUpdate(); //returns number of rows affected
        return dbResponse;
    }

}