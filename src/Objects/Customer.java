

package Objects;

import implementationsDao.CustomersImplement;

import java.lang.reflect.Constructor;

public class Customer {


    private int customerId;
    private String customerName;
    private String customerAddress;
    private String customerPostalCode;
    private String customerPhone;
    private String country;
    private int customerDivisionId;
    private String division;


    /**
     * Constructor for CustomersImplement.getAllCustomers()
     * This constructor is used to retrieve all customers from the DB
     *
     * @param customerId         customer ID
     * @param customerName       Customer name
     * @param customerAddress    Customer address
     * @param customerPostalCode Customer postal code
     * @param customerPhone      Customer phone
     * @param customerDivisionId Customer Division ID
     * @param division           Customer Division
     * @param country            Customer Country
     */
    public Customer(int customerId, String customerName, String customerAddress, String customerPostalCode, String customerPhone, int customerDivisionId, String division, String country) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhone = customerPhone;
        this.customerDivisionId = customerDivisionId;
        this.division = division;
        this.country = country;
    }


    /**
     * Constructor used to send new customers to the database
     * This constructor has no customer ID because that is assigned via database indexing
     *
     * @param customerName       The customer's name
     * @param customerAddress    The customer's address
     * @param customerPostalCode The customer's postal code
     * @param customerPhone      The customer's phone
     * @param country            The customers' country
     * @param customerDivisionId The customer's division ID
     */
    public Customer(String customerName, String customerAddress, String customerPostalCode, String customerPhone, String country, int customerDivisionId) {

        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhone = customerPhone;
        this.country = country;
        this.customerDivisionId = customerDivisionId;
    }


    /**
     * Constructor to update Customer (with customer ID)
     *
     * @param customerId         Customer ID
     * @param customerName       Customer name
     * @param customerAddress    Customer address
     * @param customerPostalCode Customer postal code
     * @param customerPhone      Customer phone
     * @param customerDivisionId Customer division ID
     */
    public Customer(int customerId, String customerName, String customerAddress, String customerPostalCode, String customerPhone, int customerDivisionId) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhone = customerPhone;
        this.customerDivisionId = customerDivisionId;
    }

    /**
     * This is used to return a human friendly representation of the customer object, which is then used to populate combo boxes
     *
     * @return Human friendly object string representation including the customer name and the customer ID
     */
    @Override
    public String toString() {
        return (customerName + "   ID: " + customerId);
    }

    /**
     * Gets the customer ID
     *
     * @return Customer ID
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets the customer ID
     *
     * @param customerId The customer ID
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Gets the customer Name
     *
     * @return Customer Name
     */
    public String getCustomerName() {
        return customerName;
    }

    /**
     * Gets the customer address
     *
     * @return customer address
     */
    public String getCustomerAddress() {
        return customerAddress;
    }

    /**
     * Gets the customer postal code
     *
     * @return customer postal code
     */
    public String getCustomerPostalCode() {
        return customerPostalCode;
    }

    /**
     * Gets the customer phone
     *
     * @return Customer phone
     */
    public String getCustomerPhone() {
        return customerPhone;
    }

    /**
     * Gets the customer division ID
     *
     * @return Customer division ID
     */
    public int getCustomerDivisionId() {
        return customerDivisionId;
    }

    /**
     * Gets the customer's country
     *
     * @return Customer country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Gets the customer's division
     *
     * @return customer's division
     */
    public String getDivision() {
        return division;
    }

}
