package Objects;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class User {

    private int userId;
    private String userName;
    private String password;
    private LocalDateTime createDate;
    private String createdBy;


    public User(int userId, String userName, String password, LocalDateTime createDate, String createdBy) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.createDate = createDate;
        this.createdBy = createdBy;
    }

    public User(int userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    @Override
    public String toString(){
        return (userName);
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}
