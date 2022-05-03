package implementationsDao;


import Objects.Customer;
import interfacesDao.CustomersInterface;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utilities.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class CustomersImplement extends DatabaseConnection implements CustomersInterface {



    ObservableList<Customer> getAllCustomers = CustomersInterface.getAllCustomers();
    public static ObservableList<Customer> customersToSave = FXCollections.observableArrayList();


    static Connection connection = DatabaseConnection.getConnection();
    static PreparedStatement allDivisionsPreparedStatement;
    String Customer_Name;
    String Address;
    String Postal_Code;
    String Phone;
    int Division_ID;




    @Override
    public void updateCustomer() {

    }

    @Override
    public void deleteCustomer() {

    }

    @Override
    public void addCustomer (Customer customer) {

        String insertCustomerInDB = "INSERT INTO customers (" +
                "Customer_Name, Address, Postal_Code, Phone,Division_ID) VALUES (" +
                        "Name," + "123 Address Way," + "12345," + "123-456-7896," + "2)";
    }
}
