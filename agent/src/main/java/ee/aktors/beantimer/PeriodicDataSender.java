package ee.aktors.beantimer;

import ee.aktors.beantimer.comm.RestClient;
import ee.aktors.beantimer.model.Measurement;
import ee.aktors.beantimer.util.TimingUtil;

import java.io.IOException;
import java.util.Stack;

public class PeriodicDataSender extends Thread {

    final private int sleepMs;
    RestClient restClient;

    private static boolean collectorLaunched = false;


    public PeriodicDataSender(int sleepMs, RestClient restClient) {
        this.sleepMs = sleepMs;
        this.restClient = restClient;
    }

    @Override
    public void run() {
        while (true) {

            sleep(sleepMs);

            //TODO synchronize!!! producer consumer
            Stack<Measurement> measurements = new Stack<>();
            measurements.addAll(TimingUtil.getMeasurements());

            System.err.printf("Sending %d measurements %n", measurements.size());
            boolean successFulDelivery = restClient.sendMeasurements(measurements);

            if (successFulDelivery) {
                measurements.clear();
            } else {
                launchCollector();
            }

        }
    }

    private void launchCollector() {
        try {
            // Works only if agent path is build path of beantimer project
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

}
