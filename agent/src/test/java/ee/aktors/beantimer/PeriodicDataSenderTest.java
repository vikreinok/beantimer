package ee.aktors.beantimer;

import ee.aktors.beantimer.comm.RestClient;
import ee.aktors.beantimer.model.Measurement;
import ee.aktors.beantimer.util.TimingUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class PeriodicDataSenderTest {

    @Mock
    RestClient restClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        when(restClient.sendMeasurements(any())).then((Answer<Boolean>) invocationOnMock -> true);
        when(restClient.getEndpoint()).thenReturn("localhost:9999");
        when(restClient.getUser()).thenReturn("username");
    }

    @Test
    void run_launchACollectorIfNotRunning() throws Exception {

        addMeasurements(10);

        PeriodicDataSender periodicDataSender = new PeriodicDataSender(1, restClient);
        periodicDataSender.start();

        Thread.sleep(15);

        assertEquals(0, TimingUtil.getMeasurements().size());
        assertEquals(true, periodicDataSender.isAlive());

        PeriodicDataSender.setIsRunning(false);

        Thread.sleep(15);

        assertEquals(false, periodicDataSender.isAlive());

    }


    private void addMeasurements(int nrOfMeasurementsToAdd) {
        List<Measurement> measurements = new ArrayList<>(nrOfMeasurementsToAdd);
        for (int i = 0; i < nrOfMeasurementsToAdd; i++) {
            measurements.add(new Measurement("branName" + 1, "branType" + 1, "branScope" + 1, (long) i, 10L + i, false));
        }
        TimingUtil.getMeasurements().addAll(measurements);
    }


}