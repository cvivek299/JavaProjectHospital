package sample;

import connectivity.ConnectionClass;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.*;

public class CustomerDetailsPageController {


    //id's of all the buttons,labels seen in UI
    @FXML
    public TextField firstName;

    @FXML
    public TextField lastName;

    @FXML
    public TextField emailAddress;

    @FXML
    public TextField phoneNumber;

    @FXML
    public Label alertMessage;
    //id's of all the buttons,labels seen in UI


    //room which this customer will take,and employeeId of receptionist
    public int roomNo,employeeId;
    //room which this customer will take,and employeeId of receptionist
    public String checkInDate,checkOutDate;

    //this function used by roomAvailabilityPageController,it passes the room_no to this
    public void set(int employeeId,int roomNo,String checkInDate,String checkOutDate)
    {
        this.employeeId=employeeId;
        this.roomNo=roomNo;
        this.checkInDate=checkInDate;
        this.checkOutDate=checkOutDate;
    }
    //this function used by roomAvailabilityPageController,it passes the room_no to this

    public void insertIntoBooking(int employeeId,int customerId,int roomNo,String checkInDate,String checkOutDate) throws SQLException {

        String sql=null;
        if(checkInDate==null)
        {
            sql="insert into booking(employee_id,customer_id,room_No,check_out) " +
                    "values ("+employeeId+","+customerId+"" +
                    ","+roomNo+",'"+checkOutDate+"');";

        }
        else
        {
            sql="insert into booking(employee_id,customer_id,room_No,check_in,check_out) " +
                    "values ("+employeeId+","+customerId+"" +
                    ","+roomNo+",'"+checkInDate+"','"+checkOutDate+"');";

        }

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

    void updateRoomTable(int roomNo) throws SQLException {

        String sql="update room set is_reserved=1 where room_no="+roomNo+";";
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

    //when submit/book button clicked
    @FXML
    void addCustomer() throws SQLException {
        //sql part->adding into database
        Connection connection = null;
        Statement statement=null;
        ResultSet resultSet=null;
        String sqlWithLastName,sqlWithoutLastName;
        String sqlFindCustomerIdOfThisCustomer;
        int customerId;
        try {

            ConnectionClass connectionClass = new ConnectionClass();
            connection = connectionClass.getConnection();





            sqlWithLastName = "insert into customer(first_name,last_name,email_address,phone_number) values(?,?,?,?);";
            sqlWithoutLastName = "insert into customer(first_name,email_address,phone_number) values(?,?,?);";



            if(firstName.getText().length()==0) {
                alertMessage.setText("Please enter the first name.");
            }
            else if(emailAddress.getText().length()==0) {
                alertMessage.setText("Please enter the email address.");
            }
            else if(phoneNumber.getText().length()==0) {
                phoneNumber.setText("Please enter the phone number.");
            }
            else
            {
                PreparedStatement ps;
                if(lastName.getText().length()==0) {
                    ps=connection.prepareStatement(sqlWithoutLastName);
                    ps.setString(1,firstName.getText());
                    ps.setString(2,emailAddress.getText());
                    ps.setString(3,phoneNumber.getText());
                }
                else {
                    ps=connection.prepareStatement(sqlWithLastName);
                    ps.setString(1,firstName.getText());
                    ps.setString(2,lastName.getText());
                    ps.setString(3,emailAddress.getText());
                    ps.setString(4,phoneNumber.getText());

                }
                ps.executeUpdate();
                if (connection != null)
                    connection.close();
                sqlFindCustomerIdOfThisCustomer = "select customer_id from customer where first_name='" + firstName.getText() + "'" +
                        " and email_address='" + emailAddress.getText() + "' and phone_number='" + phoneNumber.getText() + "';";
                connection = connectionClass.getConnection();
                statement = connection.createStatement();
                resultSet = statement.executeQuery(sqlFindCustomerIdOfThisCustomer);
                //Ensure we start with first row
                resultSet.beforeFirst();
                resultSet.next();
                customerId = resultSet.getInt(1);
                if (resultSet != null)
                    resultSet.close();
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();

                insertIntoBooking(employeeId,customerId,roomNo,checkInDate,checkOutDate);
                updateRoomTable(roomNo);
                System.out.println("BOOOKED");

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        //sql part->adding into database
    }
    //when submit/book button clicked
}
