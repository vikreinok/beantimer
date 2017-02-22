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
        LOG.info("Agent initializing");
        inst.addTransformer(new TimerBeanTransformer());
        LOG.info("Agent initializing");
    }

}