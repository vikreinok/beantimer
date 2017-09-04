package ee.aktors.beantimer;

import ee.aktors.beantimer.comm.RestClient;
import org.apache.log4j.Logger;

import java.lang.instrument.Instrumentation;

/**
 * Add following to VM options to target Apring application
 * -DpackageToMeasure=com.corp.project -javaagent: abolute path \beantimer\target\beantimer.jar
 */
public class Agent {

    final static Logger LOG = Logger.getLogger(Agent.class);

    public static void premain(String agentArgs, Instrumentation inst) {

        String packageToMeasure = System.getProperty("packageToMeasure");

        if (packageToMeasure == null || packageToMeasure.isEmpty()) {
            LOG.error("Please specify package to instrument as a agent argument. For example  -DpackageToMeasure=com.corp.project  .../beantimer.jar");
            return;
        }

        LOG.info("Measuring bean initialization in [ms] in package: " + packageToMeasure);

        LOG.info("Agent initializing");
        ClassFilter classFilter = new ClassFilter(packageToMeasure);
        inst.addTransformer(new TimerBeanTransformer(classFilter));
        LOG.info("Agent initialized");

        int period = 5000;
        String endpoint = "http://localhost:8080/metric/all";
        RestClient restClient = new RestClient(endpoint);
        PeriodicDataSender periodicDataSender = new PeriodicDataSender(period, restClient);
        periodicDataSender.start();
        LOG.info(String.format("PeriodicDataSender started with period %d ms against following endpoint %s", period, endpoint));

    }

}