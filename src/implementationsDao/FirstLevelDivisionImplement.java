package implementationsDao;

import Objects.Customer;
import Objects.FirstLevelDivision;
import interfacesDao.CustomersInterface;
import interfacesDao.FirstLevelDivisionsInterface;
import javafx.collections.ObservableList;

public class FirstLevelDivisionImplement implements FirstLevelDivisionsInterface {

    ObservableList<FirstLevelDivision> getAllDivisions = FirstLevelDivisionsInterface.getAllDivisions();


    @Override
    public void updateDivision() {

    }

    @Override
    public void deleteDivision() {

    }

    @Override
    public void addDivision() {

    }
}
