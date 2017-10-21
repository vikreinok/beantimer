package ee.aktors.beantimer.util;

import ee.aktors.beantimer.model.Measurement;

import java.util.Stack;

/**
 *
 */
public class TimingUtil {

    private static Stack<Measurement> measurements;

    public static Stack<Measurement> getMeasurements() {
        if (measurements == null) {
            measurements = new Stack<>();
        }
        return measurements;
    }

    public static void addMeasurement(String beanName, String beanType, String beanScope, long measurement, long initialisationStartTimeMillis, boolean primary) {
        Measurement metric = new Measurement(beanName, beanType, beanScope, measurement, initialisationStartTimeMillis, primary);
        getMeasurements().add(metric);
    }

    public static void addMeasurement(Measurement measurement) {
        getMeasurements().add(measurement);
    }

}
