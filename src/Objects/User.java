package Objects;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class User {

    private int userId;
    private String userName;
    private String password;
    private LocalDateTime createDate;
    private String createdBy;

    /**
     * Constructor used to retrieve a full user object
     *
     * @param userId     The user's ID
     * @param userName   The user's name
     * @param password   The user's password
     * @param createDate The user's create date
     * @param createdBy  Who created the user entry
     */
    public User(int userId, String userName, String password, LocalDateTime createDate, String createdBy) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.createDate = createDate;
        this.createdBy = createdBy;
    }

    /**
     * Constructor used to populate combo boxes
     *
     * @param userId   User ID
     * @param userName User name
     */
    public User(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    /**
     * Returns a human friendly string representation of the user object
     *
     * @return String containing the user ID and username
     */
    @Override
    public String toString() {
        return ("ID: " + userId + ",   Name: " + userName);
    }

    /**
     * Gets the user ID
     *
     * @return User ID
     */
    public int getUserId() {
        return userId;
    }

    /**
     * Gets the username
     *
     * @return Username
     */
    public String getUserName() {
        return userName;
    }

}
