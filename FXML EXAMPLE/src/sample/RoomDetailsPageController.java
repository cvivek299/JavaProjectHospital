package sample;

import connectivity.ConnectionClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.HashMap;

public class RoomDetailsPageController {

    //simply a map which maps "5000"->5000,...
    public HashMap<String,Integer> budget;

    //id's of all the buttons,labels seen in UI
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
    public ToggleButton standard;
    @FXML
    public ToggleButton superior;
    @FXML
    public ToggleButton deluxe;
    //id's of all the buttons,labels seen in UI


    //employeeId of the person logged in,and who is booking
    public int employeeId;
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


    @FXML
    public void bedroom()
    {
    }

    //returns the selected roomSize
    public String getRoomSize()
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
    //returns the selected roomSize

    //returns the selected roomType
    public String getRoomType()
    {
        String roomType=null;

        if(standard.isSelected())
            roomType="standard";
        if(superior.isSelected())
            roomType="superior";
        if(deluxe.isSelected())
            roomType="deluxe";

        return roomType;

    }
    //returns the selected roomType

    //returns the number of room of type specified by sql query,like 1BHK deluxe,which are not reserved
    public int countRooms(String sql) throws SQLException {
        Connection connection=null;
        Statement statement=null;
        ResultSet resultSet=null;
        int roomCount=0;
        try {
            ConnectionClass connectionClass = new ConnectionClass();
            connection = connectionClass.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            //Ensure we start with first row
            resultSet.beforeFirst();
            resultSet.next();
            roomCount = resultSet.getInt(1);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(resultSet!=null)
                resultSet.close();

            if(statement!=null)
                statement.close();

            if(connection!=null)
                connection.close();
        }

        return roomCount;

    }
    //returns the number of room of type specified by sql query,like 1BHK deluxe,which are not reserved

    //returns the price of a given type of room
    public float roomPrice(String sql) throws SQLException {
        Connection connection=null;
        Statement statement=null;
        ResultSet resultSet=null;
        float price=0;
        try {
            ConnectionClass connectionClass = new ConnectionClass();
            connection = connectionClass.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            //Ensure we start with first row
            resultSet.beforeFirst();
            resultSet.next();
            price = resultSet.getFloat(1);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(resultSet!=null)
                resultSet.close();

            if(statement!=null)
                statement.close();

            if(connection!=null)
                connection.close();
        }

        return price;

    }
    //returns the price of a given type of room


    //when searchButton clicked,we find the number of such available rooms,and their price and pass it
    //to RoomAvailabilityPageController
    @FXML
    public void searchResult(ActionEvent event) throws IOException, SQLException {


        int deluxeCount,superiorCount,standardCount;
        float deluxePrice,superiorPrice,standardPrice;

        String sqlDeluxeCount=null,sqlSuperiorCount=null,sqlStandardCount=null;
        String sqlDeluxePrice=null,sqlSuperiorPrice=null,sqlStandardPrice=null;

        String roomSize=getRoomSize();
        String roomType=getRoomType();

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

        controller.set(employeeId,deluxeCount,superiorCount,standardCount,deluxePrice,superiorPrice,standardPrice,roomSize);
        Stage rootStage=(Stage)(((Node)event.getSource()).getScene().getWindow());
        rootStage.setScene(new Scene(roomResultPage, 600, 495));
        rootStage.show();




    }


}
