package sample;

import connectivity.ConnectionClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class RoomAvailabilityPageController {

    //id's of all the buttons,labels seen in UI
    @FXML
    public Label deluxeRoomsCount;

    @FXML
    public Label superiorRoomsCount;

    @FXML
    public Label standardRoomsCount;

    @FXML
    public Label deluxePriceLabel;

    @FXML
    public Label superiorPriceLabel;

    @FXML
    public Label standardPriceLabel;

    @FXML
    public Label errorLabel;

    @FXML
    public Button bookButton;

    @FXML
    public ToggleButton deluxeButton;

    @FXML
    public ToggleButton superiorButton;

    @FXML
    public ToggleButton standardButton;
    //id's of all the buttons,labels seen in UI





    private int deluxeCount,superiorCount,standardCount;
    private float deluxePrice,superiorPrice,standardPrice;
    private int employeeId;
    private String roomSize;
    private String checkInDate,checkOutDate;

    //this function used by RoomDetailsPageController,it passes the count of rooms,and their price,and employeeId
    public void set(int employeeId,int deluxeCount,int superiorCount,int standardCount,float deluxePrice,float superiorPrice,float standardPrice,String roomSize,
                    String checkInDate,String checkOutDate)
    {
        System.out.println(standardCount);
        this.employeeId=employeeId;
        this.deluxeCount=deluxeCount;
        this.superiorCount=superiorCount;
        this.standardCount=standardCount;
        this.deluxePrice=deluxePrice;
        this.superiorPrice=superiorPrice;
        this.standardPrice=standardPrice;
        this.roomSize=roomSize;
        this.checkInDate=checkInDate;
        this.checkOutDate=checkOutDate;

        //setting values on the labels on ui
        deluxeRoomsCount.setText(deluxeCount+" rooms left.");
        superiorRoomsCount.setText(superiorCount+" rooms left.");
        standardRoomsCount.setText(standardCount+" rooms left.");
        deluxePriceLabel.setText("Rs."+deluxePrice);
        superiorPriceLabel.setText("Rs."+superiorPrice);
        standardPriceLabel.setText("Rs."+standardPrice);
        //setting values on the labels on ui
    }
    //this function used by RoomDetailsPageController,it passes the count of rooms,and their price,and employeeId


    //returns the selected roomType
    public String getRoomType()
    {

        String roomType=null;

        if(standardButton.isSelected())
            roomType="standard";
        if(superiorButton.isSelected())
            roomType="superior";
        if(deluxeButton.isSelected())
            roomType="deluxe";

        return roomType;

    }
    //returns the selected roomType


    //lets say he chooses deluxe,and rooms are available,then get the roomNo of one such deluxe room
    public int getFirstRoom(String roomType) throws SQLException {
        String sql="select room_no from room where "+
                "room_size='"+roomSize+"' and " +
                "room_type='"+roomType+"' and is_reserved = 0;";
        int roomNo=0;
        Connection connection=null;
        Statement statement=null;
        ResultSet resultSet=null;
        try {
            ConnectionClass connectionClass = new ConnectionClass();
            connection = connectionClass.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            //Ensure we start with first row
            resultSet.beforeFirst();
            resultSet.next();
            roomNo = resultSet.getInt(1);
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

        return roomNo;

    }



    @FXML
    public void bookButtonClicked(ActionEvent event) throws IOException, SQLException {


        String roomType=getRoomType();
        //if he doesnt selects any of the three
        if(roomType==null)
        {
            errorLabel.setText("Please select one!!");
            return;
        }
        //if he doesnt selects any of the three

        //if the roomType he selects has no rooms available
        if( ((roomType.equals("deluxe")) && (deluxeCount==0)) || ((roomType.equals("superior")) && (superiorCount==0))
                || ((roomType.equals("standard")) && (standardCount==0)))
        {
            errorLabel.setText("Sorry that type of room is not available");
            return;
        }
        //if the roomType he selects has no rooms available

        int roomNo=getFirstRoom(roomType);


        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CustomerDetailsPage.fxml"));
        Parent roomResultPage = fxmlLoader.load();
        CustomerDetailsPageController controller = fxmlLoader.<CustomerDetailsPageController>getController();
        // System.out.println(controller);

        controller.set(employeeId,roomNo,checkInDate,checkOutDate);
        Stage rootStage=(Stage)(((Node)event.getSource()).getScene().getWindow());
        rootStage.setScene(new Scene(roomResultPage, 600, 495));
        rootStage.show();

    }


}
