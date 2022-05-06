package implementationsDao;


import Objects.Customer;
import interfacesDao.CustomersInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utilities.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
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

    ObservableList<Customer> getAllCustomers = CustomersInterface.getAllCustomers();

    public static ObservableList<Customer> customersToSave = FXCollections.observableArrayList();

    static Connection connection = DatabaseConnection.getConnection();
    static PreparedStatement customersImplementPreparedStatement;
    static String sqlQuerry = "SELECT * customers";


    static {
        try {
            customersImplementPreparedStatement = DatabaseConnection.makePreparedStatement(sqlQuerry, connection);
            System.out.println("allDivisionsPreparedStatement was successful");
        } catch (SQLException e) {
            System.out.println("allDivisionsPreparedStatement in the file FirstLevelDivisionsImplement encountered an error");
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




    @Override
    public void updateCustomer() {

    }

    @Override
    public void deleteCustomer() {

    }


}
