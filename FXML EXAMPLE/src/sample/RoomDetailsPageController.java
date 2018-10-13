package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;

public class RoomDetailsPageController extends CommonActions {

    //simply a map which maps "5000"->5000,...
    private HashMap<String,Integer> budget;

    //id's of all the buttons,labels seen in UI
    @FXML
    private ComboBox minBudget;
    @FXML
    private ComboBox maxBudget;
    @FXML
    private ToggleButton bhk1;
    @FXML
    private ToggleButton bhk2;
    @FXML
    private ToggleButton bhk3;
    @FXML
    private DatePicker checkIn;
    @FXML
    private DatePicker checkOut;
    @FXML
    private Label promptLabel;
    @FXML
    private Button back;
    //id's of all the buttons,labels seen in UI


    //employeeId of the person logged in,and who is booking
    private int employeeId;
    //employeeId of the person logged in,and who is booking

    //in ui,these pop down,when we click min Budget,max Budget
    ObservableList<String> budgetList=FXCollections.observableArrayList("5000","10000","15000","20000");
    //in ui,these pop down,when we click min Budget,max Budget


    //this function used by HomePageForReceptionController,it passes the employee_id of the person who logs in to this
    public void set(int employeeId)
    {
        this.employeeId=employeeId;
    }
    //this function used by HomePageForReceptionController,it passes the employee_id of the person who logs in to this

    //first method to be called
    @FXML
    private void initialize()
    {
        minBudget.setItems(budgetList);
        maxBudget.setItems(budgetList);

        budget = new HashMap<>();
        budget.put("5000",5000);
        budget.put("10000",10000);
        budget.put("15000",15000);
        budget.put("20000",20000);
    }
    //first method to be called


    //returns the selected roomSize
    private String getRoomSize()
    {
        String roomSize=null;

        if(bhk1.isSelected())
            roomSize="1BHK";
        if(bhk2.isSelected())
            roomSize="2BHK";
        if(bhk3.isSelected())
            roomSize="3BHK";
        return roomSize;

    }

    //returns the number of room of type specified by sql query,like 1BHK deluxe,which are not reserved
    private int countRooms(String sql) throws SQLException {
        return SqlOperations.countInTable(sql);
    }
    //returns the number of room of type specified by sql query,like 1BHK deluxe,which are not reserved

    //returns the price of a given type of room
    private float roomPrice(String sql) throws SQLException {
        return SqlOperations.floatSelect(sql);
    }
    //returns the price of a given type of room


    //when searchButton clicked,we find the number of such available rooms,and their price and pass it
    //to RoomAvailabilityPageController
    @FXML
    public void searchResult(ActionEvent event) throws IOException, SQLException {


        if(checkOut.getValue()==null)
        {
            promptLabel.setText("Please enter the checkout date");
            return;
        }
        String checkOutDate=checkOut.getValue().toString();
        String checkInDate=null;
        if(!(checkIn.getValue()==null))
            checkInDate=checkIn.getValue().toString();


        int deluxeCount,superiorCount,standardCount;
        float deluxePrice,superiorPrice,standardPrice;

        String sqlDeluxeCount=null,sqlSuperiorCount=null,sqlStandardCount=null;
        String sqlDeluxePrice=null,sqlSuperiorPrice=null,sqlStandardPrice=null;

        String roomSize=getRoomSize();

        sqlDeluxeCount="select count(*) from room natural join room_price where " +
                "price>="+budget.get(minBudget.getValue())+" and " +
                "price<="+budget.get(maxBudget.getValue())+" and room_size='"+roomSize+"' and " +
                "room_type='deluxe' and is_reserved = 0;";
        sqlSuperiorCount="select count(*) from room natural join room_price where " +
                "price>="+budget.get(minBudget.getValue())+" and " +
                "price<="+budget.get(maxBudget.getValue())+" and room_size='"+roomSize+"' and " +
                "room_type='superior' and is_reserved = 0;";
        sqlStandardCount="select count(*) from room natural join room_price where " +
                "price>="+budget.get(minBudget.getValue())+" and " +
                "price<="+budget.get(maxBudget.getValue())+" and room_size='"+roomSize+"' and " +
                "room_type='standard' and is_reserved = 0;";

        sqlDeluxePrice="select price from room_price where room_size='"+roomSize+"' and " +
                "room_type='deluxe'";
        sqlSuperiorPrice="select price from room_price where room_size='"+roomSize+"' and " +
                "room_type='superior'";
        sqlStandardPrice="select price from room_price where room_size='"+roomSize+"' and " +
                "room_type='standard'";


        deluxeCount=countRooms(sqlDeluxeCount);
        superiorCount=countRooms(sqlSuperiorCount);
        standardCount=countRooms(sqlStandardCount);

        deluxePrice=roomPrice(sqlDeluxePrice);
        superiorPrice=roomPrice(sqlSuperiorPrice);
        standardPrice=roomPrice(sqlStandardPrice);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("roomAvailabilityPage.fxml"));
        Parent roomResultPage = fxmlLoader.load();
        RoomAvailabilityPageController controller = fxmlLoader.<RoomAvailabilityPageController>getController();
       // System.out.println(controller);

        controller.set(employeeId,deluxeCount,superiorCount,standardCount,deluxePrice,superiorPrice,standardPrice,roomSize,checkInDate,checkOutDate);
        Stage rootStage=(Stage)(((Node)event.getSource()).getScene().getWindow());
        rootStage.setScene(new Scene(roomResultPage, 1200, 650));
        rootStage.show();

    }


    @FXML
    public void onBackClicked(ActionEvent event) throws IOException {
        //when clicked go to RoomDetailsPage,also pass it employeeId
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HomePageForReception.fxml"));
        Parent homePageForReception = fxmlLoader.load();
        HomePageForReceptionController controller = fxmlLoader.<HomePageForReceptionController>getController();

        controller.set(employeeId);
        Stage rootStage=(Stage)(((Node)event.getSource()).getScene().getWindow());
        rootStage.setScene(new Scene(homePageForReception, 1200, 650));
        rootStage.show();
        //when clicked go to RoomDetailsPage,also pass it employeeId

    }


}
