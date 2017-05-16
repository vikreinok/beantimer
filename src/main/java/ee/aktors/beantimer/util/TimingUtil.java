package ee.aktors.beantimer.util;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 */
public class TimingUtil {


    private static Map<String, Integer> measurements;

    public static Map<String, Integer> getMeasurements() {
        if (measurements == null) {
            measurements = new LinkedHashMap<String, Integer>();
        }
        return measurements;
    }

    public static void addMeasurement(String beanName, int measurement) {
        getMeasurements().put(beanName, measurement);
        System.out.printf("bean %s measurement %d%n", beanName, measurement);
    }



}
