package interfacesDao;


import Objects.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface CustomersInterface {

    ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    static ObservableList<Customer> getAllCustomers() {
        return allCustomers;
    }


    public static int updateCustomer(Customer customerToUpdate) {
        return 0;
    }

    public static int deleteCustomer(int customerId) {
        return 0;
    }

    public static void addCustomer(ObservableList<Customer> customersToSave) throws SQLException {
    }

    ;

}
