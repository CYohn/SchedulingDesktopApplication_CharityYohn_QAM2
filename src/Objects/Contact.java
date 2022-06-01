package Objects;

public class Contact {

    /**
     * Contact variables
     */
    private int contactId;
    private String contactName;
    private String email;

    /**
     * Constructor used to get the contact from the Database
     *
     * @param contactId   Contact ID
     * @param contactName Contact Name
     * @param email       Contact email
     */
    public Contact(int contactId, String contactName, String email) {
        this.contactId = contactId;
        this.contactName = contactName;
        this.email = email;
    }

    /**
     * Constructor used to populate combo boxes
     *
     * @param contactId   Contact ID
     * @param contactName Contact Name
     */
    public Contact(int contactId, String contactName) {
        this.contactId = contactId;
        this.contactName = contactName;
    }

    /**
     * Method overrides how the object is presented in string form for a more human friendly representation in combo boxes.
     *
     * @return Returns a string with the ID and name of the contact
     */
    @Override
    public String toString() {
        return ("ID: " + contactId + ",   Name: " + contactName);
    }

    /**
     * Gets the contact ID
     *
     * @return Contact ID
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Gets the contact Name
     *
     * @return contact name
     */
    public String getContactName() {
        return contactName;
    }


}
