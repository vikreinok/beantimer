package ee.aktors.beantimer.comm;

import ee.aktors.beantimer.model.Metric;
import org.junit.Before;
import org.junit.Test;

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
        Metric metric = new Metric("name1", "type", 10);
        restClient.sendData(metric);
    }

}