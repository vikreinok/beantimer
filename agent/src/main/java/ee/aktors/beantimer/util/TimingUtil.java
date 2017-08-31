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

    public static void addMeasurement(String beanName, String beanType, int measurement) {
        Measurement metric = new Measurement(beanName, beanType, measurement);
        getMeasurements().add(metric);
        System.out.println(metric);
    }

}
