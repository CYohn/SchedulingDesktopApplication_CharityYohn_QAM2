package implementationsDao;


import Objects.Customer;
import Objects.FirstLevelDivision;
import interfacesDao.CustomersInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utilities.DatabaseConnection;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

    static ObservableList<Customer> getAllCustomers = CustomersInterface.getAllCustomers();

    public static ObservableList<Customer> customersToSave = FXCollections.observableArrayList();

    static Connection connection = DatabaseConnection.getConnection();
    static PreparedStatement customersImplementPreparedStatement;
    static String sqlQuery = "SELECT * customers";


    static {
        try {
            customersImplementPreparedStatement = DatabaseConnection.makePreparedStatement(sqlQuery, connection);
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
                int division = customer.getCustomerDivisionId();

                System.out.println("Customer to send to the DB: " + customer);

                String insertCustomerIntoDB = "INSERT INTO customers (" +
                        "Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (" +
                        "'" + name + "'," +
                        "'" + address + "'," +
                        "'" + postalCode + "'," +
                        "'" + phone + "'," +
                        "" + division + ");";

                PreparedStatement insertPreparedStatement = DatabaseConnection.getConnection().prepareStatement(insertCustomerIntoDB);
                databaseResponseToUpdate = insertPreparedStatement.executeUpdate(insertCustomerIntoDB);

                // Updating Query
                if (databaseResponseToUpdate == 1) {
                    System.out.println("Table Updated Successfully.......");
                    System.out.println("databaseResponseToUpdate: " + databaseResponseToUpdate);
                    return databaseResponseToUpdate;
                }

            }
        } catch (SQLException e) {
            System.out.println("printCustomerToSave encountered an error in th CustomersImplement file:");
            e.getCause();
            e.getMessage();
            e.printStackTrace();
        }
        return databaseResponseToUpdate;
    }


    public static void getAllCustomers() throws SQLException {
        customersImplementPreparedStatement = DatabaseConnection.makePreparedStatement(sqlQuery, connection);
        System.out.println("customersImplementPreparedStatement was successful");
        ResultSet allCustomersResults;

        try{

        allCustomersResults = customersImplementPreparedStatement.executeQuery();

        while (allCustomersResults.next()) {
            int customerId = allCustomersResults.getInt("Customer_ID");
            System.out.println("allCustomersResults customerId: " + customerId);

            String customerName = allCustomersResults.getString("Customer_Name");
            System.out.println("allCustomersResults customerName: " + customerName);

            String address = allCustomersResults.getString("Address");
            System.out.println("allCustomersResults address: " + address);

            String postalCode = allCustomersResults.getString("Postal_Code");
            System.out.println("allCustomersResults postalCode: " + postalCode);

            String phone = allCustomersResults.getString("Phone");
            System.out.println("allCustomersResults phone: " + phone);

            int divisionId = allCustomersResults.getInt("Division_ID");
            System.out.println("allCustomersResults divisionId: " + divisionId);

            Customer customer = new Customer(customerId, customerName, address, postalCode, phone, divisionId);
            getAllCustomers.add(customer);
            System.out.println("Customer object populated in getAllCustomers list: " + customer);
        }
    } catch (SQLException throwables) {
        System.out.println("SQLException thrown in getAllCustomers method in the CustomerImplement file");
        throwables.getMessage();
        throwables.getCause();
        throwables.printStackTrace();
    }
}



    @Override
    public void updateCustomer() {

    }

    @Override
    public void deleteCustomer() {

    }


}
