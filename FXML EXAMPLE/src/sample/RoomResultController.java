package sample;

import connectivity.ConnectionClass;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class RoomResultController {

    public ArrayList<String> roomType;

    @FXML
    public Label deluxeRoomsCount;

    @FXML
    public Label superiorRoomsCount;

    @FXML
    public Label standardRoomsCount;

    @FXML
    public Label deluxePrice;

    @FXML
    public Label superiorPrice;

    @FXML
    public Label standardPrice;



    @FXML
    private void initialize()
    {
        roomType=new ArrayList<>();

        roomType.add("deluxe");
        roomType.add("superior");
        roomType.add("standard");
        roomType.add("superior");
        roomType.add("standard");
        roomType.add("deluxe");
        roomType.add("superior");

        int count[]=new int[3];

        for(int i=0;i<roomType.size();i++)
        {
            if(roomType.get(i).equals("deluxe"))
                count[0]++;
            else if(roomType.get(i).equals("superior"))
                count[1]++;
            else
                count[2]++;
        }

        deluxeRoomsCount.setText(count[0]+" rooms left.");
        superiorRoomsCount.setText(count[1]+" rooms left.");
        standardRoomsCount.setText(count[2]+" rooms left.");



        deluxePrice.setText("Rs.7,000");
        superiorPrice.setText("Rs.5,000");
        standardPrice.setText("Rs.3,000");





    }



}
