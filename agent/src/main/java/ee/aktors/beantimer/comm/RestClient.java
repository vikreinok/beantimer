package ee.aktors.beantimer.comm;

import ee.aktors.beantimer.model.Measurement;
import ee.aktors.beantimer.util.JsonUtil;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.List;

import static ee.aktors.beantimer.constant.CommonConstant.HEADER_NAME_X_USER;

/**
 *
 */
public class RestClient {

    private final static Logger LOG = Logger.getLogger(RestClient.class);
    private static final String HEADER_NAME_ACCEPT = "accept";
    private static final String HEADER_NAME_CONTENT_TYPE = "Content-Type";
    private static final String HEADER_VALUE_APPLICATION_JSON = "application/json";

    private String endpoint = "http://localhost:9999/metric/all";
    private final String user;

    public RestClient(String endpoint, String user) {
        this.endpoint = endpoint;
        this.user = user;
    }

    public RestClient(String user) {
        this.user = user;
        System.err.println("Using default endpoint: " + endpoint);
    }

    public boolean sendMeasurements(List<Measurement> measurements) {
        String payload = JsonUtil.transformToJsonArray(measurements);



        boolean success = true;

        CloseableHttpClient httpClient = null;
        try {

            httpClient = HttpClients.createDefault();
            HttpPut putRequest = new HttpPut(endpoint);
            putRequest.addHeader(HEADER_NAME_X_USER, user);
            putRequest.addHeader(HEADER_NAME_ACCEPT, HEADER_VALUE_APPLICATION_JSON);
            putRequest.addHeader(HEADER_NAME_CONTENT_TYPE, HEADER_VALUE_APPLICATION_JSON);
            putRequest.setEntity(new StringEntity(payload));


            // Create a custom response handler
            ResponseHandler<String> responseHandler = response -> {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            };

            String response = httpClient.execute(putRequest, responseHandler);
            LOG.info(response);
        } catch (Exception e) {
            e.printStackTrace();
            success = false;
        } finally {
            if (httpClient != null) {
                try {
                    httpClient.close();
                } catch (IOException e) {
                    LOG.warn(e);
                }
            }
        }
        return success;
    }
}