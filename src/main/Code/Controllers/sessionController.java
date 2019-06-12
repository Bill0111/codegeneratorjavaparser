package Code.Controllers;

import Code.codeGenerator.codeIterator;
import com.github.javaparser.ast.CompilationUnit;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.junit.runner.JUnitCore;

import java.io.*;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.*;
import javafx.scene.control.*;

public class sessionController extends Application {

    @FXML
    private Label ProblemSolved;

    @FXML
    private Label Correct;

    @FXML
    private Label inCorrect;

    @FXML
    private Label timeremaining;


    @FXML
    private ListView<String> codeBox;

    @FXML
    private Label lineNum;

    @FXML
    private TextField answer;

    @FXML
    private Label testlineNum;

    private int timeRemain;
    private long start;

    private boolean forLoop;
    private boolean whileloop;
    private int codeComplexity;
    private int logicComplexity;

    private boolean firstAttempt;
    private int problemSolved;
    private int correct;

    private boolean executable;


    @FXML
    private void initMenu(ActionEvent event) throws Exception
    {
        Context.getInstance().setNumCorrect(correct);
        Context.getInstance().setQuestionsAnswered(problemSolved);
        Stage stage = (Stage)((Node)event.getTarget()).getScene().getWindow();
        Parent menu = FXMLLoader.load(getClass().getResource("resources/menu.fxml"));
        stage.setScene(new Scene(menu));
        stage.setTitle("Menu");


    }
    @FXML
    public void initialize() throws IOException{

        timeRemain = Context.getInstance().getTime()*10;
        forLoop= Context.getInstance().getForLoop();
        whileloop= Context.getInstance().getWhileLoop();
        boolean ifStatement= Context.getInstance().getIfStatement();
        codeComplexity= Context.getInstance().getCodeComplexity();

        if(!ifStatement)
            logicComplexity=0;
        else
            logicComplexity= Context.getInstance().getLogicComplexity();

        problemSolved=0;
        correct=0;
        firstAttempt = true ;
        executable = false;
        generate();
        refreshWindow();


        start = System.currentTimeMillis();

        KeyFrame update = new KeyFrame(Duration.seconds(1), event -> setTimeRemain());
        Timeline tl = new Timeline(update);
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.play();


//        while(!executable) {
//            generate();
//            check("atest.java");
//            infinity();
//        }
//        infinity();
//        executable = false;


    }

    private void setTimeRemain(){

        long millisecondsSinceEpoch =   (long)Duration.minutes(timeRemain).toMillis() - System.currentTimeMillis() + start;
        Instant instant = Instant.ofEpochMilli ( millisecondsSinceEpoch );
        ZonedDateTime zdt = ZonedDateTime.ofInstant ( instant , ZoneOffset.UTC );

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern ( "HH:mm:ss" );
        String output = formatter.format ( zdt );

        timeremaining.setText(output);
    }

    private String generateCode(ArrayList<String> loopType, int codeComplexity,int logicComplexity)
    {

        CompilationUnit cu = new codeIterator(loopType,codeComplexity,logicComplexity).getCu();
        return cu.toString();
    }

//    int lineNumber = lstCode.getSelectionModel().getSelectedIndex() + 1;

    @FXML
    private void generate() throws IOException{

        ArrayList<String> looptypes = new ArrayList<>();
        if(forLoop)
            looptypes.add("forloop");
        if(whileloop)
            looptypes.add("whileloop");
        ArrayList<String> code = StringConverter(generateCode(looptypes,codeComplexity,logicComplexity));
        ArrayList<String> newList = new ArrayList<String>(code);
        PrintStream o = new PrintStream(new File("atest.java"));
        System.setOut(o);
        codeBox.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        codeBox.getItems().clear();
        while(!code.isEmpty()) {
            String temp = code.remove(0);
            System.out.println(temp);
            codeBox.getItems().add(temp);
        }
        o.close();
        PrintStream e = new PrintStream(new File("atestp.java"));
        System.setOut(e);
        newList.remove(0);
        System.out.println("public class atestp {");
        int line = 2;
        while(!newList.isEmpty()) {
            String temp = newList.remove(0);
            System.out.println(temp);
            if(temp.contains("result")) {
                System.out.println("System.out.print(" + "\">\" + " +  line + ");");
                System.out.println("System.out.println( \",\" +result + \";\");");
            }
            line++;
        }
        e.close();


        firstAttempt = true;
        answer.setText("");
    }

    @FXML
    private void displayLineNum(){
        int lineNumber = codeBox.getSelectionModel().getSelectedIndex() + 1;
        lineNum.setText(lineNumber+"");

    }



    private ArrayList<String> StringConverter(String a)
    {
        ArrayList<String> result = new ArrayList<>();
        int i =0;
        int j =0;
        while(j<a.length())
        {
            if(a.charAt(j)=='\n')
            {
                result.add(a.substring(i,j));
                i=j;
            }
            j++;
        }
        return result;
    }

    @FXML
    private void hitSubmit() throws IOException, InterruptedException{
        String ans = answer.getText();
        String realans = getAnswer("atest.java");
        int intans = (int)Double.parseDouble(realans);
        String intans2 = intans + "";

        Boolean ifTrue = ans.equals(realans) || ans.equals(intans2);


        if(firstAttempt&&ifTrue) {
            //correct window
            if(confirmBox.display("Congratulations!","Great, You got it correct!","View Again","Next Question")) {
                //stay;
                firstAttempt = false;
            }
            else {
                generate();
                problemSolved++;
            }


                correct++;
        }


        else if(ifTrue) {
            //correct window
            if(!confirmBox.display("Congratulations!","Great, You got it correct!","View Again","Next Question")) {
                generate();
                problemSolved++;
            }
        }
        else {
            //incorrect window
            Boolean a = confirmBox.display("Ooops", "Something went wrong", "Try Again", "Skip this one");
            if(a) {
                //stay;
                firstAttempt = false;
            }
            else
                    generate();

        }

        refreshWindow();
        executable=false;

    }

    @FXML
    private void viewAnswer() throws IOException, InterruptedException{
        if(!confirmBox.display("the Answer for this Question is ",getAnswer("atest.java"),"Go back","Next Question"))
            generate();
    }

    @FXML
    private void viewHint() throws Exception{
        Stage a = new Stage();
        start(a);
    }

    private String getTrace(String classname) throws IOException, InterruptedException{
        if(getAnswer("atest.java").equals("Infinity"))
            return ">AllLines , Infinity";

        Process pro3 = Runtime.getRuntime().exec("javac " + classname);
        pro3.waitFor();
        Process pro4 = Runtime.getRuntime().exec("java "+ classname.substring(0,classname.length()-5));

        Scanner in = new Scanner(new InputStreamReader(pro4.getInputStream()));
        StringBuilder line = new StringBuilder();
        while(in.hasNextLine())
            line.append(in.nextLine());
        in.close();
        return line.toString();
    }


    private void refreshWindow() {
        ProblemSolved.setText("" + problemSolved);
        Correct.setText("" + correct);
        inCorrect.setText(problemSolved - correct + "");
    }

    private boolean isInfinite(){
        JUnitCore core= new JUnitCore();
        RingingListener a = new RingingListener();
        core.addListener(a);
        core.run(TimeTest.class);
        return a.getAnswer();
    }


    private String getAnswer(String classname) throws IOException, InterruptedException{
        if(isInfinite()) {
            return "Infinity";
        }

        else{

            Process pro1 = Runtime.getRuntime().exec("javac " + classname);
            pro1.waitFor();
            Process pro2 = Runtime.getRuntime().exec("java "+ classname.substring(0,classname.length()-5));

            Scanner in = new Scanner(new InputStreamReader(pro2.getInputStream()));
            StringBuilder line = new StringBuilder();
            while(in.hasNextLine())
                line.append(in.nextLine());
            in.close();
            if(line.toString().length()>=5) {
                System.out.println(line.toString());
                return "Infinity";
            }

            return line.toString();
        }

    }



    private List<String> getNext(int lines , ArrayList<String> hint) throws IOException, InterruptedException{
        List<String> words = new ArrayList<>();
        if (lines ==-1){
            words.add("LineNumber");
            words.add("result");
        }
        else {
            String[] a = hint.get(lines).split(",");
            if(a[0].contains(">"))
                words.add(a[0].split(">")[1]);
            else
                words.add(a[0]);
            words.add(a[1]);
        }
        return words;
    }

    public void start(Stage stage) throws Exception {
        stage.setTitle("Tracing Table");

        TableView<ObservableList<String>> tableView = new TableView<>();
//        testlineNum.setText(getTrace("atestp.java").split(";")[0]);
        ArrayList<String> hint = new ArrayList<String>(Arrays.asList(getTrace("atestp.java").split(";")));

        // add columns

        List<String> columnNames = getNext(-1 , hint);
        for (int i = 0; i < columnNames.size(); i++) {
            final int finalIdx = i;
            TableColumn<ObservableList<String>, String> column = new TableColumn<>(
                    columnNames.get(i)
            );
            column.setCellValueFactory(param ->
                    new ReadOnlyObjectWrapper<>(param.getValue().get(finalIdx))
            );
            tableView.getColumns().add(column);
        }
        for (int i = 0; i < hint.size(); i++) {
            tableView.getItems().add(
                    FXCollections.observableArrayList(getNext(i , hint))
            );
        }

        tableView.setPrefHeight(400);


//        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
//            if (newSelection != null) {
//
//
//            }
//        });

        tableView.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() > 1) {
                highlight(Integer.parseInt(tableView.getSelectionModel().getSelectedItem().get(0))-1);
//                testlineNum.setText(tableView.getSelectionModel().getSelectedItem().get(0));
            }
        });

//        public void onEdit() {
//            // check the table's selected item and get selected item
//            if (table.getSelectionModel().getSelectedItem() != null) {
//                Person selectedPerson = table.getSelectionModel().getSelectedItem();
//                nameTextField.setText(selectedPerson.getName());
//                addressTextField.setText(selectedPerson.getAddress());
//            }
//        }

        Scene scene = new Scene(tableView);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void highlight(int index){
        codeBox.getSelectionModel().clearAndSelect(index);

    }




//    private void runCommand() throws IOException{
//        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
//        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
//        StandardJavaFileManager fileManager =compiler.getStandardFileManager(diagnostics,null,null);
//        Iterable<? extends JavaFileObject> compilationUnits = fileManager.getJavaFileObjectsFromStrings(Arrays.asList("atest.java"));
//        JavaCompiler.CompilationTask task = compiler.getTask(null,fileManager,diagnostics,null,null,compilationUnits);
//        boolean success = task.call();
//        fileManager.close();
//
//
//    }
}
