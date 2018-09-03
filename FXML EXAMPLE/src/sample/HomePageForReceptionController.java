package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HomePageForReceptionController {

    //id's of all the buttons,labels seen in UI
    @FXML
    public Button roomBookButton;

    @FXML
    public Button printBillButton;
    //id's of all the buttons,labels seen in UI


    //employeeId who has logged on(whose department is reception)
    int employeeId;
    //employeeId who has logged on(whose department is reception)

    //this function used by LoginPageController,it passes the employee_id of the person who logs in to this
    public void set(int employeeId)
    {
        this.employeeId=employeeId;
    }
    //this function used by LoginPageController,it passes the employee_id of the person who logs in to this



    @FXML
    public void roomBookClicked(ActionEvent event) throws IOException {


        //when clicked go to RoomDetailsPage,also pass it employeeId
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("RoomDetailsPage.fxml"));
        Parent roomDetailsPage = fxmlLoader.load();
        RoomDetailsPageController controller = fxmlLoader.<RoomDetailsPageController>getController();

        controller.set(employeeId);
        Stage rootStage=(Stage)(((Node)event.getSource()).getScene().getWindow());
        rootStage.setScene(new Scene(roomDetailsPage, 600, 495));
        rootStage.show();
        //when clicked go to RoomDetailsPage,also pass it employeeId


    }





}
