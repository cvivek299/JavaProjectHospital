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

    //this function used by RoomDetailsPageController,it passes the count of rooms,and their price,and employeeId
    public void set(int employeeId,int deluxeCount,int superiorCount,int standardCount,float deluxePrice,float superiorPrice,float standardPrice,String roomSize)
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


    //working on it
    public void getFirstRoom(String roomType)
    {



    }


    
    @FXML
    public void bookButtonClicked(ActionEvent event) throws IOException {


        String roomType=getRoomType();
        if(roomType==null)
        {
            errorLabel.setText("Please select one!!");
            return;
        }

        if( ((roomType.equals("deluxe")) && (deluxeCount==0)) || ((roomType.equals("superior")) && (superiorCount==0))
                || ((roomType.equals("standard")) && (standardCount==0)))
        {
            errorLabel.setText("Sorry that type of room is not available");
            return;
        }







/*
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CustomerDetailsPage.fxml"));
        Parent roomResultPage = fxmlLoader.load();
        CustomerDetailsPageController controller = fxmlLoader.<CustomerDetailsPageController>getController();
        // System.out.println(controller);

        controller.set(employeeId,deluxeCount,superiorCount,standardCount,deluxePrice,superiorPrice,standardPrice);
        Stage rootStage=(Stage)(((Node)event.getSource()).getScene().getWindow());
        rootStage.setScene(new Scene(roomResultPage, 600, 495));
        rootStage.show();
*/
    }


}
