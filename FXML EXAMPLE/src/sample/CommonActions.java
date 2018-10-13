package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CommonActions {

    public int employeeId;
    @FXML
    public void onLogoutClicked(ActionEvent event) throws IOException {
        //when clicked go to RoomDetailsPage,also pass it employeeId
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("LoginPage.fxml"));
        Parent loginPage = fxmlLoader.load();
        Stage rootStage=(Stage)(((Node)event.getSource()).getScene().getWindow());
        rootStage.setScene(new Scene(loginPage, 1200, 650));
        rootStage.show();
        //when clicked go to RoomDetailsPage,also pass it employeeId
    }


    @FXML
    public void onHomePageClicked(ActionEvent event) throws IOException {
        //when clicked go to RoomDetailsPage,also pass it employeeId
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HomePageForReception.fxml"));
        Parent loginPage = fxmlLoader.load();
        HomePageForReceptionController controller = fxmlLoader.<HomePageForReceptionController>getController();

        controller.set(employeeId);
        Stage rootStage=(Stage)(((Node)event.getSource()).getScene().getWindow());
        rootStage.setScene(new Scene(loginPage, 1200, 650));
        rootStage.show();
        //when clicked go to RoomDetailsPage,also pass it employeeId
    }


}
