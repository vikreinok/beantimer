package ee.aktors.beantimer;

import ee.aktors.beantimer.comm.RestClient;
import ee.aktors.beantimer.model.Measurement;
import ee.aktors.beantimer.util.TimingUtil;
import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class PeriodicDataSenderTest {

    @Test
    @Ignore("Do not need that with remote collector")
    public void run_launchACollectorIfNotRunning() throws Exception {
        new PeriodicDataSender(0, new RestClient("")).start();
    }

    @Test
    public void run_concurrentResourceUse() throws Exception {

        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++) {
            Runnable producer = new Runnable() {
                @Override
                public void run() {
                    addMeasurements(10);
                }
            };
            executor.execute(producer);

            Runnable consumer = new PeriodicDataSender(0, new RestClient(""));
            executor.execute(consumer);
        }

        executor.shutdown();

        while (!executor.isTerminated()) {
        }

        assertEquals(0, TimingUtil.getMeasurements().size());

    }

    private void addMeasurements(int nrOfMeasurementsToAdd) {
        for (int i = 0; i < nrOfMeasurementsToAdd; i++) {
            TimingUtil.getMeasurements().add(new Measurement("branName" + 1, "branType" + 1, "branScope" + 1, (long) i, 10L + i, false));
        }
    }


}