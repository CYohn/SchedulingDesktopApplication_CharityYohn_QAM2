package implementationsDao;

import Objects.Country;
import Objects.Customer;
import interfacesDao.CountriesInterface;
import interfacesDao.CustomersInterface;
import javafx.collections.ObservableList;

public class CustomersImplement implements CustomersInterface {

    ObservableList<Customer> getAllCountries = CustomersInterface.getAllCustomers();


    @Override
    public void updateCustomer() {

    }

    @Override
    public void deleteCustomer() {

    }

    @Override
    public void addCustomer() {

    }
}
