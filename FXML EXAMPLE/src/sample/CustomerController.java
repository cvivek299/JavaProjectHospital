package sample;

import connectivity.ConnectionClass;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.*;

public class CustomerController {

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

    @FXML
    void addCustomer() throws SQLException {

        Connection connection = null;
        String sqlWithLastName,sqlWithoutLastName;

        try {

            ConnectionClass connectionClass = new ConnectionClass();
            connection = connectionClass.getConnection();

            sqlWithLastName = "insert into customer(first_name,last_name,email_address,phone_number) values(?,?,?,?);";

            sqlWithoutLastName = "insert into customer(first_name,email_address,phone_number) values(?,?,?);";



            if(firstName.getText().length()==0) {
                alertMessage.setText("Please enter your first name.");
            }
            else if(emailAddress.getText().length()==0) {
                alertMessage.setText("Please enter your email address.");
            }
            else if(phoneNumber.getText().length()==0) {
                phoneNumber.setText("Please enter your phone number.");
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
                    ps=connection.prepareStatement(sqlWithoutLastName);
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
    }
}
