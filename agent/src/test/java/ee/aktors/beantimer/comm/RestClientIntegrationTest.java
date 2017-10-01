package ee.aktors.beantimer.comm;

import ee.aktors.beantimer.model.Measurement;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

/**
 *
 */
public class RestClientIntegrationTest {

    private RestClient restClient;

    @Before
    public void setUp() throws Exception {
        // TODO launch collector
        restClient = new RestClient();
    }

    @Test
    public void sendData() throws Exception {
        Measurement measurement = new Measurement("name1", "type", 10L, 100L);
        restClient.sendMeasurements(Collections.singletonList(measurement));
    }

}