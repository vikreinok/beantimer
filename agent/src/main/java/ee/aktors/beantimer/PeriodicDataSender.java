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
            //TODO synchronize!!! producer consumer
            Stack<Measurement> measurements = TimingUtil.getMeasurements();

            //TODO sent in bulk
            int count = 0;
            for (int i = 0; i < measurements.size() && count < 10; i++) {
                Measurement measurement = measurements.get(i);
                restClient.sendData(measurement);
                measurements.remove(measurement);
                count++;
            }

            try {
                Thread.sleep(sleepMs);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
