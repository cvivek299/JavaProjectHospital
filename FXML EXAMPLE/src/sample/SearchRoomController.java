package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;

public class SearchRoomController {
    @FXML
    public ComboBox minBudget;
    @FXML
    public ComboBox maxBudget;
    @FXML
    public ToggleButton bhk1;
    @FXML
    public ToggleButton bhk2;
    @FXML
    public ToggleButton bhk3;
    @FXML
    public ToggleButton bhkAny;



    ObservableList<String> budgetList=FXCollections.observableArrayList("<5000","5000","10000","15000",">15000");

    @FXML
    private void initialize()
    {
        minBudget.setItems(budgetList);
        maxBudget.setItems(budgetList);
    }

    @FXML
    public void bedroom()
    {
    }


}
