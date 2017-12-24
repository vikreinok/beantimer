package ee.aktors.beantimer;

import ee.aktors.beantimer.comm.RestClient;
import ee.aktors.beantimer.model.Measurement;
import ee.aktors.beantimer.util.TimingUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PeriodicDataSender extends Thread {

    final private int sleepMs;
    final private RestClient restClient;

    private static volatile boolean isRunning = true;
    private static boolean collectorLaunched = false;


    public PeriodicDataSender(int sleepMs, RestClient restClient) {
        this.sleepMs = sleepMs;
        this.restClient = restClient;
    }

    @Override
    public void run() {
        while (isRunning) {

            sleep(sleepMs);

            List<Measurement> measurements = new ArrayList<>();
            measurements.addAll(new ArrayList<>(TimingUtil.getMeasurements()));

            System.err.printf("Sending %d measurements to %s by user:'%s' %n ", measurements.size(), restClient.getEndpoint(), restClient.getUser());
            boolean successFulDelivery = restClient.sendMeasurements(measurements);

            if (successFulDelivery) {
                TimingUtil.getMeasurements().clear();
                measurements.clear();
            } else {
//                launchCollector();
            }

        }
    }

    /**
     *  Works only if agent path is build path of beantimer project
     */
    private void launchCollector() {
        try {
            System.out.println("Launching collector");
            if (!collectorLaunched) {
                Runtime.getRuntime().exec("cmd.exe /c cd .. & cd .. & cd collector & mvn spring-boot:run");
                collectorLaunched = true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sleep(int sleepMs) {
        try {
            Thread.sleep(sleepMs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void setIsRunning(boolean isRunning) {
        PeriodicDataSender.isRunning = isRunning;
    }
}
