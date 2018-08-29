package sample;

import connectivity.ConnectionClass;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ToggleButton;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class SearchRoomController {


    public HashMap<String,Integer> budget;
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





    ObservableList<String> budgetList=FXCollections.observableArrayList("5000","10000","15000","20000");

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


    @FXML
    public void bedroom()
    {
    }

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

    @FXML
    public void searchResult() throws SQLException {
        Connection connection=null;
        Statement statement=null;
        ResultSet resultSet=null;
        String sql=null;

        try
        {
            ConnectionClass connectionClass = new ConnectionClass();
            connection = connectionClass.getConnection();
            statement = connection.createStatement();

            String roomSize=getRoomSize();
            String roomType=getRoomType();

            sql="select * from room natural join room_type where price>="+budget.get(minBudget.getValue())+" and " +
                    "price<="+budget.get(maxBudget.getValue())+" and room_size='"+roomSize+"' and room_type='"
                    +roomType+"' and is_reserved <> 0;";


            resultSet = statement.executeQuery(sql);
            //Ensure we start with first row
            resultSet.beforeFirst();

            //so cursor is pointing before first row,at clling next it points to first row,if empty false
            if (resultSet.next()) {
                do {
                    System.out.println(""+resultSet.getInt("room_no")
                            +resultSet.getString("room_type")
                            +resultSet.getInt("price"));


                }while(resultSet.next());
            } else {

                System.out.println("No rooms found as per your requirements!!");
            }

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






    }


}
