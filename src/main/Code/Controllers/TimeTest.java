package Code.Controllers;

import com.sun.org.glassfish.gmbal.ParameterNames;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestWatcher;
import org.junit.rules.Timeout;
import org.junit.runner.Description;
import org.junit.runner.JUnitCore;
import org.junit.runners.model.TestTimedOutException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class TimeTest{


    @Rule
    public Timeout globalTimeout = Timeout.seconds(4); // 10 seconds max per method tested


    @Test
    public void testSleepForTooLong() throws Exception {
//        TimeUnit.SECONDS.sleep(100); // sleep for 100 seconds
        Process pro1 = Runtime.getRuntime().exec("javac atest.java");
        pro1.waitFor();
        Process pro2 = Runtime.getRuntime().exec("java atest");
        Scanner in = new Scanner(new InputStreamReader(pro2.getInputStream()));
        StringBuilder line = new StringBuilder();
        while (in.hasNextLine())
            line.append(in.nextLine());
        in.close();


    }


}
