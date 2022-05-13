

package Objects;

public class Customer {

    private int customerId;
    private String customerName;
    private String customerAddress;
    private String customerPostalCode;
    private String customerPhone;
    private String country;
    private int customerDivisionId;

    public Customer(int customerId, String customerName, String customerAddress, String customerPostalCode, String customerPhone, int customerDivisionId) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.customerPostalCode = customerPostalCode;
        this.customerPhone = customerPhone;
        this.customerDivisionId = customerDivisionId;
    }

    public Customer(String customerName, String customerAddress, String customerPostalCode, String customerPhone, int customerDivisionId) {
    }

    public Customer(int customerId, String customerName, String address, String postalCode, String phone, String country, int divisionId) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.customerAddress = address;
        this.customerPostalCode = postalCode;
        this.customerPhone = phone;
        this.country = country;
        this.customerDivisionId = divisionId;
    }

    public Customer(String customerName, String address, String postalCode, String phone, String country, int divisionId) {

        this.customerName = customerName;
        this.customerAddress = address;
        this.customerPostalCode = postalCode;
        this.customerPhone = phone;
        this.country = country;
        this.customerDivisionId = divisionId;
    }


    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerPostalCode() {
        return customerPostalCode;
    }

    public void setCustomerPostalCode(String customerPostalCode) {
        this.customerPostalCode = customerPostalCode;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public int getCustomerDivisionId() {
        return customerDivisionId;
    }

    public void setCustomerDivisionId(int customerDivisionId) {
        this.customerDivisionId = customerDivisionId;
    }


    public String getCountry() { return country; }

    public void setCountry(String country) { this.country = country; }
}
