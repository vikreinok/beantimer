package ee.aktors.beantimer;

import ee.aktors.beantimer.comm.RestClient;
import ee.aktors.beantimer.model.Measurement;
import ee.aktors.beantimer.util.TimingUtil;

import java.util.Stack;

public class PeriodicDataSender extends Thread {

    final private int sleepMs;
    RestClient restClient;


    public PeriodicDataSender(int sleepMs, RestClient restClient) {
        this.sleepMs = sleepMs;
        this.restClient = restClient;
    }

    @Override
    public void run() {
        while (true) {

            sleep(sleepMs);

            //TODO synchronize!!! producer consumer
            Stack<Measurement> measurements = TimingUtil.getMeasurements();

            System.err.printf("Sending %d measurements %n", measurements.size());
            restClient.sendMeasurements(measurements);

            measurements.clear();

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
