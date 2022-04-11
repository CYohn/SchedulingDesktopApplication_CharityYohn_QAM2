package interfacesDao;

import Objects.FirstLevelDivision;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public interface FirstLevelDivisionsInterface {
    ObservableList<FirstLevelDivision> allDivisions = FXCollections.observableArrayList();

    static ObservableList<FirstLevelDivision> getAllDivisions() {
        return allDivisions;
    }


    public void updateDivision();

    public void deleteDivision();

    public void addDivision();
}
