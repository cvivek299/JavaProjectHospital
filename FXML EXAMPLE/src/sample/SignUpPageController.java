package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.SQLException;

public class SignUpPageController {
    @FXML
    private TextField employeeName;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private ComboBox department;
    @FXML
    private Label promptText;

    //in ui,these pop down,when we click department
    ObservableList<String> departmentList=FXCollections.observableArrayList("reception","house keeping","culinary","recreation");
    //in ui,these pop down,when we click department

    @FXML
    private void initialize() {
        department.setItems(departmentList);
    }

    //returns the selected roomType
    private void reset()
    {
        department.valueProperty().set(null);
        username.clear();
        password.clear();
        employeeName.clear();
    }

    private boolean checkValid(String username) throws SQLException {
        String sql="select count(*) from employee where username='"+username+"';";
        return (!SqlOperations.checkIfAnyPresentAlready(sql));
    }

    private void insertIntoEmployee(String username,String password,String employeeName,String department) throws SQLException {
        String sql="insert into employee(username,password,name,department) values('"+username+"','" +
                ""+password+"','"+employeeName+"','"+department+"');";
        SqlOperations.insert(sql);
    }



    @FXML
    public void onSignUpClicked() throws SQLException {

        //all checks starts
        if(username.getText().isEmpty())
        {
            promptText.setText("Username field cannot be empty");
            return;
        }
        if(password.getText().isEmpty())
        {
            promptText.setText("Password field cannot be empty");
            return;
        }
        if(employeeName.getText().isEmpty())
        {
            promptText.setText("Name field cannot be empty");
            return;
        }
        if(department.getSelectionModel().isEmpty())
        {
            promptText.setText("Please select a department");
            return;
        }
        if(!checkValid(username.getText()))
        {
            promptText.setText("username already exists");
            return;
        }
        //all checks ends

        insertIntoEmployee(username.getText(),password.getText(),employeeName.getText(),department.getValue().toString());
        promptText.setText("successfully signed Up");
        reset();
    }

    @FXML
    public void goToLoginPage(ActionEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
        Parent roomResultPage = fxmlLoader.load();
        Stage rootStage=(Stage)(((Node)event.getSource()).getScene().getWindow());
        rootStage.setScene(new Scene(roomResultPage, 600, 495));
        rootStage.show();
    }

}
