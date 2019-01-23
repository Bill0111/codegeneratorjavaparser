package Code.Controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.Time;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class menuController {
    @FXML
    private CheckBox computer;
    @FXML
    private CheckBox forLoop;
    @FXML
    private CheckBox whileLoop;
    @FXML
    private CheckBox ifStatement;
    @FXML
    private CheckBox fCall;
    @FXML
    private CheckBox recursion;
    @FXML
    private CheckBox inheritance;
    @FXML
    private ChoiceBox<Integer> codeComplexity;
    @FXML
    private ChoiceBox<Integer> logicComplexity;
    @FXML
    private ChoiceBox<Time> sessionTime;
    @FXML
    private ChoiceBox<String> mode;
    @FXML
    private Label user;
    @FXML
    private Label time;

    private long start;

    @FXML
    public void initialize() {
        forLoop.setSelected(true);
        ifStatement.setSelected(true);

        codeComplexity.getItems().removeAll(codeComplexity.getItems());
        codeComplexity.getItems().addAll(1, 2, 3);
        codeComplexity.getSelectionModel().select(0);

        logicComplexity.getItems().removeAll(logicComplexity.getItems());
        logicComplexity.getItems().addAll(0,1, 2, 3);
        logicComplexity.getSelectionModel().select(0);

        sessionTime.getItems().removeAll(sessionTime.getItems());
        sessionTime.getItems().addAll(new Time(-67800000), new Time(-67200000), new Time(-66600000));
        sessionTime.getSelectionModel().select(0);

        mode.getItems().removeAll(mode.getItems());
        mode.getItems().addAll("Practice Mode", "Test Mode");
        mode.getSelectionModel().select(0);

        computer.setSelected(true);

        user.setText(Context.getInstance().getUser().substring(0, Context.getInstance().getUser().length() - 10));


        start = System.currentTimeMillis();

        KeyFrame update = new KeyFrame(Duration.seconds(1), event -> setTime());
        Timeline tl = new Timeline(update);
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();



    }


    private void setTime(){
        long millisecondsSinceEpoch = System.currentTimeMillis()-start;
        Instant instant = Instant.ofEpochMilli ( millisecondsSinceEpoch );
        ZonedDateTime zdt = ZonedDateTime.ofInstant ( instant , ZoneOffset.UTC );

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern ( "HH:mm:ss" );
        String output = formatter.format ( zdt );

        time.setText(output);
    }

    @FXML
    private void computerMode(){
        if(!computer.isSelected()){
           forLoop.setDisable(false);
           whileLoop.setDisable(false);
           ifStatement.setDisable(false);
           fCall.setDisable(false);
           recursion.setDisable(false);
           inheritance.setDisable(false);
           codeComplexity.setDisable(false);
           logicComplexity.setDisable(false);
        }
        else{
            forLoop.setDisable(true);
            whileLoop.setDisable(true);
            ifStatement.setDisable(true);
            fCall.setDisable(true);
            recursion.setDisable(true);
            inheritance.setDisable(true);
            codeComplexity.setDisable(true);
            logicComplexity.setDisable(true);
        }
    }

    @FXML
    private void sessionStart(ActionEvent event) throws Exception
    {

        Context.getInstance().setTime(sessionTime.getSelectionModel().getSelectedIndex()+1);
        Context.getInstance().setForLoop(forLoop.isSelected());
        Context.getInstance().setWhileLoop(whileLoop.isSelected());
        Context.getInstance().setIfStatement(ifStatement.isSelected());
        Context.getInstance().setCodeComplexity(codeComplexity.getValue());
        Context.getInstance().setLogicComplexity(logicComplexity.getValue());


        Stage stage = (Stage)((Node)event.getTarget()).getScene().getWindow();
        Parent session = FXMLLoader.load(getClass().getResource("../../Scenes/session.fxml"));
        stage.setScene(new Scene(session));
        stage.setTitle("Practice Tool");




    }
    @FXML
    private void logout(ActionEvent event) throws Exception
    {
        Stage stage = (Stage)((Node)event.getTarget()).getScene().getWindow();
        Parent login = FXMLLoader.load(getClass().getResource("../../Scenes/login.fxml"));
        stage.setTitle("Code-Generator Tracing Practice Tool");
        stage.setScene(new Scene(login));
    }

    @FXML
    private void loadSummary(ActionEvent event) throws Exception
    {
        Stage stage = (Stage)((Node)event.getTarget()).getScene().getWindow();
        Parent summary = FXMLLoader.load(getClass().getResource("../../Scenes/summary.fxml"));
        stage.setScene(new Scene(summary));
        stage.setTitle("Summary");
    }
}
