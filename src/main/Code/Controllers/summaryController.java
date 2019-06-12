package Code.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.control.Label;

public class summaryController {
    @FXML
    private Label questionAnswered;

    @FXML
    private Label Correct;

    @FXML
    private Label inCorrect;

    @FXML
    private Label Correctness;

    @FXML
    private Label timeConsumed;


    @FXML
    public void initialize() throws IOException {
        questionAnswered.setText(Context
                .getInstance().getQuestionsAnswered() + "");
        Correct.setText(Context
                .getInstance().getNumCorrect() + "");
        inCorrect.setText(Context.getInstance().getQuestionsAnswered() - Context
                .getInstance().getNumCorrect() + "");
        if (Context.getInstance().getQuestionsAnswered() == 0 )
            Correctness.setText("0");
        else
            Correctness.setText(100*Context.getInstance().getNumCorrect()/Context.getInstance().getQuestionsAnswered()+" %");
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
    private void logout(ActionEvent event) throws Exception
    {
        Stage stage = (Stage)((Node)event.getTarget()).getScene().getWindow();
        Parent login = FXMLLoader.load(getClass().getResource("resources/login.fxml"));
        stage.setTitle("Code-Generator Tracing Practice Tool");
        stage.setScene(new Scene(login));

    }


}
