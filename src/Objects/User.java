package Objects;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * This class is the constructor class for user objects.
 */
public class User {
    /**
     * The user's ID.
     */
    private int userId;
    /**
     * The user's username.
     */
    private String userName;


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
