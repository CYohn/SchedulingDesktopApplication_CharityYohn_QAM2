package interfacesDao;


import Objects.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public interface CustomersInterface {

    ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    static ObservableList<Customer> getAllCustomers() {
        return allCustomers;
    }


    public void updateCustomer();

    public void deleteCustomer();

    public void addCustomer(Customer customer);

}
