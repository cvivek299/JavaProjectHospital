package sample;

import connectivity.ConnectionClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginPageController {

    //id's of all the buttons,labels seen in UI
    public Button signUp;
    public Label success;
    public TextField username;
    public PasswordField password;
    //id's of all the buttons,labels seen in UI


    public void signUpClicked()
    {
        signUp.setText("Yes");
    }

    //when login clicked,simply sql,to check if valid username,password,and if receptionist,go to HomePageForReception passing employeeId
    public void loginClicked(ActionEvent event) throws SQLException
    {

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String sql;

        try {

            ConnectionClass connectionClass = new ConnectionClass();
            connection = connectionClass.getConnection();
            statement = connection.createStatement();

            sql = "select employee_id,username,password,department from employee where username='" + username.getText() + "';";

            resultSet = statement.executeQuery(sql);
            //Ensure we start with first row
            resultSet.beforeFirst();

            if (resultSet.next()) {
                int employeeId=resultSet.getInt("employee_id");
                String userPassword = resultSet.getString("password");

                if (userPassword.equals(password.getText()))
                {
                    success.setText(resultSet.getString("username"));
                    String department=resultSet.getString("department");
                    if(department.equals("reception"))
                    {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HomePageForReception.fxml"));
                        Parent HomePageForReception = fxmlLoader.load();
                        HomePageForReceptionController controller = fxmlLoader.<HomePageForReceptionController>getController();

                        controller.set(employeeId);
                        Stage rootStage = (Stage) (((Node) event.getSource()).getScene().getWindow());
                        rootStage.setScene(new Scene(HomePageForReception, 600, 495));
                        rootStage.show();
                    }
                }
                else
                    success.setText("Invalid password!!");
            } else {
                success.setText("Username doesn't exists!!");
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
    }
    //when login clicked,simply sql,to check if valid username,password,and if receptionist,go to HomePageForReception passing employeeId
}
