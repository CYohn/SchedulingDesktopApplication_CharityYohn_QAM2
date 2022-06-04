package Objects;

/**
 * This class is the contructor class for contact objects.
 */
public class Contact {

    /**
     * Contact ID.
     */
    private int contactId;
    /**
     * Contact name.
     */
    private String contactName;

    /**
     * Constructor used to get the contact from the Database
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
