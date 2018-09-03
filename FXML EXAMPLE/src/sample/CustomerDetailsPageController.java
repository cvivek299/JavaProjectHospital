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


    //room which this customer will take
    public int room_no;
    //room which this customer will take


    //this function used by roomAvailabilityPageController,it passes the room_no to this
    void set(int room_no)
    {
        this.room_no=room_no;
    }
    //this function used by roomAvailabilityPageController,it passes the room_no to this



    //when submit/book button clicked
    @FXML
    void addCustomer() throws SQLException {
        //sql part->adding into database
        Connection connection = null;
        String sqlWithLastName,sqlWithoutLastName;

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


            }






        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null)
                connection.close();
        }
        //sql part->adding into database
    }
    //when submit/book button clicked
}
