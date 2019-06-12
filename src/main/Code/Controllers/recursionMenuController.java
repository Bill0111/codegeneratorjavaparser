package Code.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class recursionMenuController {

    @FXML
    private ChoiceBox<String> recursionMethod;

    @FXML
    public void initialize() {
        recursionMethod.getItems().removeAll(recursionMethod.getItems());
        recursionMethod.getItems().addAll("Mergesort","Other Recursive Methods");
        recursionMethod.getSelectionModel().select(0);
    }

    @FXML
    private void initMenu(ActionEvent event) throws Exception
    {

        Stage stage = (Stage)((Node)event.getTarget()).getScene().getWindow();
        Parent menu = FXMLLoader.load(getClass().getResource("resources/menu.fxml"));
        stage.setScene(new Scene(menu));
        stage.setTitle("Menu");
    }

    @FXML
    private void sessionStart(ActionEvent event) throws Exception
    {

        Context.getInstance().setRecursionMethod(recursionMethod.getValue());
        Stage stage = (Stage)((Node)event.getTarget()).getScene().getWindow();
        Parent session = FXMLLoader.load(getClass().getResource("resources/recursionSession.fxml"));
        stage.setScene(new Scene(session));
        stage.setTitle("Recursion Method Practice Tool");

    }

}
