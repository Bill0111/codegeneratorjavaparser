package Code.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class loginController {


    @FXML
    private TextField email;
    @FXML
    private TextField password;
    @FXML
    private Label warning;
    @FXML
    private void clickLoginButton(ActionEvent event) throws Exception
    {
        if (isEmory(email)){

            Context.getInstance().setUser(email.getText());
            Stage stage = (Stage)((Node)event.getTarget()).getScene().getWindow();
            Parent menu = FXMLLoader.load(getClass().getResource("resources/menu.fxml"));
            stage.setScene(new Scene(menu));
            stage.setTitle("Menu");
        }

    }
    private boolean isEmory(TextField in) {
        String message = in.getText();
        if (message.length() < 11) {
            warning.setText("Invalid Email!");
            return false;
        }
        String substring = message.substring(Math.max(message.length() - 10, 0));
        if (substring.equals("@emory.edu"))
            return true;
        warning.setText("please enter an emory email!");
        return false;
    }


//    private void closeProgram(ActionEvent event) {
//        boolean answer = ConfirmBox.display("confirm", "u sure?");
//        if (answer) {
//            Stage stage = (Stage)((Node)event.getTarget()).getScene().getWindow();
//            stage.close();
//        }
//    }
}

