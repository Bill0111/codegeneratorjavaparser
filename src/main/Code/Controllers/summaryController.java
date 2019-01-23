package Code.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class summaryController {

    @FXML
    private void initMenu(ActionEvent event) throws Exception
    {
        Stage stage = (Stage)((Node)event.getTarget()).getScene().getWindow();
        Parent menu = FXMLLoader.load(getClass().getResource("../../Scenes/menu.fxml"));
        stage.setScene(new Scene(menu));
        stage.setTitle("Menu");
    }


    @FXML
    private void logout(ActionEvent event) throws Exception
    {
        Stage stage = (Stage)((Node)event.getTarget()).getScene().getWindow();
        Parent login = FXMLLoader.load(getClass().getResource("../../Scenes/login.fxml"));
        stage.setTitle("Code-Generator Tracing Practice Tool");
        stage.setScene(new Scene(login));

    }


}
