package ee.aktors.beantimer;

import ee.aktors.beantimer.comm.RestClient;
import org.junit.Test;

/**
 *
 */
public class PeriodicDataSenderTest {
    @Test
    public void run_launchACollectorIfNotRunning() throws Exception {
        new PeriodicDataSender(0, new RestClient()).run();
    }

}