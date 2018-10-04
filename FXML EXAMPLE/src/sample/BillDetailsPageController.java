package sample;

import connectivity.ConnectionClass;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//get ur bill ,and update bill table using your booking id
//no checks for two bills with same booking id,program will go down,databse will be consistent
public class BillDetailsPageController {

    @FXML
    private TextField bookingId;

    //to display error input,and for now bill amount
    @FXML
    private Label alertMessage;


    //the employee who is printing the bill
    private int employeeId;

    //passing employee id from prev page
    public void set(int employeeId)
    {
        this.employeeId=employeeId;
    }

    //see if there is any such booking id
    public boolean checkValid(int bookingId) throws SQLException {

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String sql;

        try {

            ConnectionClass connectionClass = new ConnectionClass();
            connection = connectionClass.getConnection();
            statement = connection.createStatement();

            sql = "select count(*) from booking where booking_id="+bookingId+";" ;

            resultSet = statement.executeQuery(sql);
            //Ensure we start with first row
            resultSet.beforeFirst();

            if (resultSet.next()) {
                int countRoom=resultSet.getInt(1);
                if(countRoom==0)
                    return false;
                else
                    return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null)
                resultSet.close();
            if (statement != null)
                statement.close();
            if (connection != null)
                connection.close();

        }
        return false;
    }



    private void insertInBillTable(int employeeId,int bookingId,float cost) throws SQLException {

        String sql=null;
        sql="insert into bill(booking_id,employee_id,total_cost) " +
                "values ("+bookingId+","+employeeId+","+cost+");";

        Connection connection=null;
        Statement statement=null;
        try {
            ConnectionClass connectionClass = new ConnectionClass();
            connection = connectionClass.getConnection();
            statement = connection.createStatement();
            statement.executeUpdate(sql);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            if(statement!=null)
                statement.close();

            if(connection!=null)
                connection.close();
        }

    }

    //for now only room price
    private float findTotalCost(int bookingId) throws SQLException {
        float cost=0;
        String sql="select price from room natural join room_price where room_no=" +
                "(select room_no from booking where booking_id="+bookingId+");";

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {

            ConnectionClass connectionClass = new ConnectionClass();
            connection = connectionClass.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            //Ensure we start with first row
            resultSet.beforeFirst();

            if (resultSet.next()) {
                cost=resultSet.getFloat(1);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (resultSet != null)
                resultSet.close();
            if (statement != null)
                statement.close();
            if (connection != null)
                connection.close();

        }

        return cost;
    }


    //when button clicked,insertis into db,and prints amount
    @FXML
    public void createBill() throws SQLException {
        if(bookingId.getText().length()==0) {
            alertMessage.setText("Please enter the Booking Id.");
            return;
        }

        int bookingIdInInt=Integer.parseInt(bookingId.getText());

        boolean isValid=checkValid(bookingIdInInt);
        if(!isValid)
        {
            alertMessage.setText("Please enter a valid room number");
            return;

        }

        float cost=findTotalCost(bookingIdInInt);
        insertInBillTable(employeeId,bookingIdInInt,cost);
        alertMessage.setText("This is ur bill amout to be paid "+cost);


    }


}
