package ee.aktors.beantimer;

import org.apache.log4j.Logger;

import java.lang.instrument.Instrumentation;

/**
 * Add following
 * -javaagent: abolute path \beantimer\target\beantimer.jar
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
    }

}