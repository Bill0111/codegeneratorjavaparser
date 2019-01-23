package Code.Controllers;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

class confirmBox {

    private static boolean answer;

    static boolean display(String title, String message,String text1,String text2)
    {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setWidth(300);
        window.setHeight(250);

        Label label = new Label(message);

        Button yes = new Button(text1);
        Button no = new Button(text2);

        yes.setOnAction(event -> {
            answer = true;
            window.close();

        });

        no.setOnAction(event -> {
            answer = false;
            window.close();

        });


        VBox layout = new VBox(10);
        layout.getChildren().addAll(label,yes,no);
        layout.setAlignment(Pos.CENTER);

        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.showAndWait();

        return answer;




    }
}




