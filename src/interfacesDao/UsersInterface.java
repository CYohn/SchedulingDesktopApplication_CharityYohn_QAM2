package interfacesDao;

import Objects.Customer;
import Objects.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public interface UsersInterface {

    ObservableList<User> allUsers = FXCollections.observableArrayList();

    static ObservableList<User> getAllUsers() {
        return allUsers;
    }


    public void updateUser();

    public void deleteUser();

    public void addUser();
}
