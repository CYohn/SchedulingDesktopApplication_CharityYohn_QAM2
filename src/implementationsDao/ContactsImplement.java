package implementationsDao;


import Objects.Contact;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utilities.DatabaseConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * This class interacts reads contacts from the database and adds them to a list of contact names and ID numbers.
 */
public class ContactsImplement extends DatabaseConnection {

    /**
     * An observableList of names and ID numbers from the database.
     */
    public static ObservableList<Contact> contactNames = FXCollections.observableArrayList();

    /**
     * Gets all contact names and ID numbers from the database and adds them to an observableList.
     *
     * @throws SQLException
     */
    public static void getAllContactNames() throws SQLException {
        contactNames.clear();
        String allContactNames = "SELECT Contact_Name, Contact_ID" +
                " FROM client_schedule.contacts";

        PreparedStatement getContactNamesPreparedStatement = DatabaseConnection.getConnection().prepareStatement(allContactNames);
        System.out.println("getContactNamesPreparedStatement was successful in ContactsImplement.getAllContactNames()");
        ResultSet allContactNamesResults;

        try {

            if (getContactNamesPreparedStatement != null) {
                allContactNamesResults = getContactNamesPreparedStatement.executeQuery();


                while (allContactNamesResults.next()) {

                    String contactName = allContactNamesResults.getString("Contact_Name");
                    //System.out.println("allUserNamesResults userName: " + userName);

                    int contactId = allContactNamesResults.getInt("Contact_ID");
                    //System.out.println("allUserNamesResults userName: " + userName);
                    Contact contact = new Contact(contactId, contactName);
                    contactNames.add(contact);
                    //System.out.println("Customer object populated in getAllCustomers list: " + customer);
                }
            } else{System.out.println("ResultSet was null");}

        } catch(SQLException throwables){
            System.out.println("SQLException thrown in getAllContactNames method in the ContactsImplement file");
            throwables.getMessage();
            throwables.getCause();
            throwables.printStackTrace();
        }
    }

}
