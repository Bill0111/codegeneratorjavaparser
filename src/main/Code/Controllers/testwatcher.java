package Code.Controllers;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;

public class testwatcher extends TestWatcher {

    public testwatcher() {}

    @Override
    protected void failed(Throwable e, Description description) {
        System.out.println("Only executed when a test fails");
    }
}
