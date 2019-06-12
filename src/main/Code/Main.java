package Code;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.*;

public class Main extends Application {

    private Stage primaryStage;


    @Override
    public void start(Stage primaryStage) throws Exception{
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Code-Generator Tracing Practice Tool");
        loadLogin();
        primaryStage.show();
    }

    private void loadLogin() throws Exception
    {
        Parent login = FXMLLoader.load(getClass().getResource("Controllers/resources/login.fxml"));
        primaryStage.setScene(new Scene(login));
    }


    public static void main(String[] args) {
        launch(args);
    }
    
}


