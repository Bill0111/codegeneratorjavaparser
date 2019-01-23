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
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class sessionController {

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
    private TextField answer;

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
        Stage stage = (Stage)((Node)event.getTarget()).getScene().getWindow();
        Parent menu = FXMLLoader.load(getClass().getResource("../../Scenes/menu.fxml"));
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



    @FXML
    private void generate() throws IOException{

        ArrayList<String> looptypes = new ArrayList<>();
        if(forLoop)
            looptypes.add("forloop");
        if(whileloop)
            looptypes.add("whileloop");
        ArrayList<String> code = StringConverter(generateCode(looptypes,(int)(Math.random()*codeComplexity)+1,(int)(Math.random()*(logicComplexity+1))));
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

        firstAttempt = true;
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
        Boolean ifTrue = ans.equals(getAnswer("atest.java"));


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


    private void refreshWindow(){
        ProblemSolved.setText(""+problemSolved);
        Correct.setText(""+correct);
        inCorrect.setText(problemSolved-correct+"");
    }

    private String getAnswer(String classname) throws IOException, InterruptedException{
        Process pro1 = Runtime.getRuntime().exec("javac " + classname);
        pro1.waitFor();
        Process pro2 = Runtime.getRuntime().exec("java "+ classname.substring(0,classname.length()-5));

        Scanner in = new Scanner(new InputStreamReader(pro2.getInputStream()));
        StringBuilder line = new StringBuilder();
        while(in.hasNextLine())
            line.append(in.nextLine());
        in.close();

        if(Math.abs(Double.parseDouble(line.toString()))>1000)
            return "Infinity";

        return line.toString();

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
