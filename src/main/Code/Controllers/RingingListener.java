package Code.Controllers;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

public class RingingListener extends RunListener {
    private boolean answer = false;
    public Boolean getAnswer(){
        return answer;
    }
    public void testFailure(Failure failure) {
//        System.out.println("a");
        answer=true;
    }
}