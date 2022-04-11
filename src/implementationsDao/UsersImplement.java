package implementationsDao;

import Objects.Customer;
import Objects.User;
import interfacesDao.CustomersInterface;
import interfacesDao.UsersInterface;
import javafx.collections.ObservableList;

public class UsersImplement implements UsersInterface {


    ObservableList<User> getAllUsers = UsersInterface.getAllUsers();

    @Override
    public void updateUser() {

    }

    @Override
    public void deleteUser() {

    }

    @Override
    public void addUser() {

    }
}
