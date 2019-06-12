package Code.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.SelectionMode;

import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import java.awt.*;
import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;

public class recursionSessionController {

    @FXML
    private TextField list1;

    @FXML
    private TextField list2;

    @FXML
    private ListView<String> codeList;

    @FXML
    private void generate() throws Exception{
        ArrayList<String> code = new ArrayList<String>();
        PrintStream o = new PrintStream(new File("atest.java"));
        System.setOut(o);
        codeList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        codeList.getItems().clear();
        while(!code.isEmpty()) {
            String temp = code.remove(0);
            System.out.println(temp);
            codeList.getItems().add(temp);
        }
        o.close();

    }


}
