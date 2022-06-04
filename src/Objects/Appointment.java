package Objects;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

/**
 * This class is the constructor class for the appointment object.
 */
public class Appointment {

    /**
     * The appointment ID.
     */
    private int appointmentId;
    /**
     * The appointment title.
     */
    private String title;
    /**
     * The appointment description.
     */
    private String description;
    /**
     * The appointment location.
     */
    private String location;
    /**
     * The appointment type.
     */
    private String type;
    /**
     * The appointment start date and time.
     */
    private LocalDateTime startDateTime;
    /**
     * The appointment end date and time.
     */
    private LocalDateTime endDateTime;
    /**
     * The user ID associated with the appointment.
     */
    private int userId;
    /**
     * The contact ID associated with the appointment.
     */
    private int contactId;
    /**
     * The customer ID associated with the appointment.
     */
    private int customerId;
    /**
     * The start date of the appointment.
     */
    private LocalDate startDate;
    /**
     * The start time of the appointment.
     */
    private LocalTime startTime;
    /**
     * The end date of the appointment.
     */
    private LocalDate endDate;
    /**
     * The end time of the appointment.
     */
    private LocalTime endTime;


    /**
     * @param appointmentId The appointment ID
     * @param title         The appointment title
     * @param description   The appointment description
     * @param location      The appointment location
     * @param type          The appointment type
     * @param startDateTime The appointment start date and time, stored in the DB as a TIMESTAMP
     * @param endDateTime   The appointment end date and time, stored in the DB as a TIMESTAMP
     * @param customerId    The ID of the associated customer
     * @param userId        The ID of the associated user
     * @param contactId     The ID of the associated contact
     */
    public Appointment(int appointmentId, String title, String description, String location, String type, LocalDateTime startDateTime, LocalDateTime endDateTime, int customerId, int userId, int contactId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    /**
     * This constructor is used to save new appointments and is missing the appointment ID because the ID is assigned via the database index
     *
     * @param title         Appointment title
     * @param description   Appointment description
     * @param location      Appointment location
     * @param type          Appointment type
     * @param startDateTime Appointment start date and time
     * @param endDateTime   Appointment end date and time
     * @param customerId    ID of the associated customer
     * @param userId        ID of the associated user
     * @param contactId     ID of the associated contact
     */
    public Appointment(String title, String description, String location, String type, LocalDateTime startDateTime, LocalDateTime endDateTime, int customerId, int userId, int contactId) {

        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    /**
     * This constructor is used to retrieve the dates and times of a specific appointment and is used for time validation
     *
     * @param appointmentId The ID of the appointment
     * @param startDateTime The start date and time of the appointment
     * @param endDateTime   The end date and time of the appointment
     */
    public Appointment(int appointmentId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.appointmentId = appointmentId;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    /**
     * An appointment constructor with the times and dates separated. This constructor is used for populating tables, as separated times and dates are more human friendly than timestamps
     *
     * @param appointmentId The ID of the appointment
     * @param title         The title of the appoitment
     * @param description   The description of the appointment
     * @param location      The location of the appointment
     * @param type          Appointment type
     * @param startDate     The start date of the appointment
     * @param startTime     The start time of the appointment
     * @param endDate       The appointmentend date
     * @param endTime       The appointment end time
     * @param customerId    The ID of the associated customer
     * @param userId        The ID of the associated user
     * @param contactId     The ID of the associated contact
     */
    public Appointment(int appointmentId, String title, String description, String location, String type, LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime, int customerId, int userId, int contactId) {
        this.appointmentId = appointmentId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDate = startDate;
        this.startTime = startTime;
        this.endDate = endDate;
        this.endTime = endTime;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    /**
     * Gets the appointment type
     *
     * @return the appointment type
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the appointment type
     *
     * @param type The appointment type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Gets the start date
     *
     * @return Appointment start date
     */
    public LocalDate getStartDate() {
        return startDate;
    }

    /**
     * Gets the appointment start time
     *
     * @return appointment start time
     */
    public LocalTime getStartTime() {
        return startTime;
    }


    /**
     * Gets the appointment end date
     *
     * @return Returns the appointment end date
     */
    public LocalDate getEndDate() {
        return endDate;
    }


    /**
     * Gets the appointment end time
     *
     * @return The appointment end time
     */
    public LocalTime getEndTime() {
        return endTime;
    }


    /**
     * Gets the appointment ID
     *
     * @return Appointment ID
     */
    public int getAppointmentId() {
        return appointmentId;
    }

    /**
     * Gets the appointment title
     *
     * @return the appointment title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the appointment title
     *
     * @param title the title of the appointment
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the appointment description
     *
     * @return Appointment description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the appointment description
     *
     * @param description The appointment description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets the appointment location
     *
     * @return The appointment location
     */
    public String getLocation() {
        return location;
    }

    /**
     * Sets the appointment location
     *
     * @param location the location of the appointment
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Gets the start date and time timestamp
     *
     * @return The start date and time
     */
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }


    /**
     * Get the end date and time timestamp of the appointment
     *
     * @return end date and time
     */
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    /**
     * Gets the ID of the associated user
     *
     * @return User ID
     */
    public int getUserId() {
        return userId;
    }


    public int getContactId() {
        return contactId;
    }


    /**
     * Gets the ID of the associated customer
     *
     * @return customer ID
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Sets the ID of the associated customer
     *
     * @param customerId The ID of the associated customer
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
