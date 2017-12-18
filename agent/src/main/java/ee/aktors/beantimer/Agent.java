package ee.aktors.beantimer;

import ee.aktors.beantimer.comm.RestClient;
import org.apache.log4j.Logger;

import java.lang.instrument.Instrumentation;

/**
 * Add following to VM options to target Spring application
 * -DpackageToMeasure=com.corp.project -javaagent: absolute path \beantimer\target\beantimer.jar
 */
public class Agent {

    final static Logger LOG = Logger.getLogger(Agent.class);
    
    public static final int PERIOD_MS = 20_000;
//    public static final String ENDPOINT = "http://localhost:9999/metric/all";
    public static final String ENDPOINT = "http://84.52.54.143:9999/metric/all";

    public static void premain(String agentArgs, Instrumentation inst) {

        String packageToMeasure = System.getProperty("packageToMeasure");
        String beantimerUser = System.getProperty("beantimerUser");
        if (packageToMeasure == null || packageToMeasure.isEmpty()) {
            LOG.error("Please specify package to instrument as a agent argument. For example: -DpackageToMeasure=com.corp.project  -javaagent:.../beantimer.jar");
            return;
        }
        if (beantimerUser == null || beantimerUser.isEmpty()) {
            LOG.error("Please specify user to find your result in collector. For example: -DbeantimerUser=yourUsername  -javaagent:.../beantimer.jar");
        }

        LOG.info("Measuring bean initialization in [ms] in package: " + packageToMeasure);

        initializeAgent(inst, packageToMeasure);

        initAndStartPeriodicDataSender(PERIOD_MS, ENDPOINT, beantimerUser);

    }

    private static void initAndStartPeriodicDataSender(int periodMs, String endpoint, String beantimerUser) {
        RestClient restClient = new RestClient(endpoint, beantimerUser);
        PeriodicDataSender periodicDataSender = new PeriodicDataSender(periodMs, restClient);
        periodicDataSender.start();
        LOG.info(String.format("PeriodicDataSender started with periodMs %d ms against following endpoint %s", periodMs, endpoint));
    }

    private static void initializeAgent(Instrumentation inst, String packageToMeasure) {
        LOG.info("Agent initializing");
        ClassFilter classFilter = new ClassFilter(packageToMeasure);
        inst.addTransformer(new TimerBeanTransformer(classFilter));
        LOG.info("Agent initialized");
    }

}