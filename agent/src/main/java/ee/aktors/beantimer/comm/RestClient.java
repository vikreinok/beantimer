package ee.aktors.beantimer.comm;

import ee.aktors.beantimer.model.Measurement;
import ee.aktors.beantimer.util.JsonUtil;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 *
 */
public class RestClient {

    final static Logger LOG = Logger.getLogger(RestClient.class);

    private String endpoint = "http://localhost:8080/metric/all";

    public RestClient(String endpoint) {
        this.endpoint = endpoint;
    }

    public RestClient() {
        System.err.println("Using default endpoint: " + endpoint);
    }

    public void sendMeasurements(List<Measurement> measurements) {
        String payload = JsonUtil.transformToJsonArray(measurements);

        DefaultHttpClient httpClient = null;
        BufferedReader br = null;
        try {

            httpClient = new DefaultHttpClient();
            HttpPut putRequest = new HttpPut(endpoint);
            putRequest.addHeader("accept", "application/json");
            putRequest.addHeader("Content-Type", "application/json");
            putRequest.setEntity(new StringEntity(payload));

            HttpResponse response = httpClient.execute(putRequest);

            if (response.getStatusLine().getStatusCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
            }

            br = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));

            String output;
            LOG.info("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                LOG.info(output);
            }

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpClient != null && httpClient.getConnectionManager() != null) {
                httpClient.getConnectionManager().shutdown();
            }
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    LOG.error(e);
                }
            }
        }

    }
}