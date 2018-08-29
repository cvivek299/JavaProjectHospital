package sample;

import connectivity.ConnectionClass;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Controller {

    public Button signUp;
    public Label success;
    public TextField username;
    public PasswordField password;
    public void signUpClicked()
    {
        signUp.setText("Yes");
    }

    public void loginClicked() throws SQLException
    {

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        String sql;

        try {

            ConnectionClass connectionClass = new ConnectionClass();
            connection = connectionClass.getConnection();
            statement = connection.createStatement();

            sql = "select username,password from admin where username='" + username.getText() + "';";

            resultSet = statement.executeQuery(sql);
            //Ensure we start with first row
            resultSet.beforeFirst();

            if (resultSet.next()) {
                String userPassword = resultSet.getString("password");
                if (userPassword.equals(password.getText()))
                    success.setText(resultSet.getString("username"));
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
}
