package com.concretepage.controller;


import com.concretepage.MyApplication;
import com.concretepage.entity.Metric;
import com.concretepage.repo.MetricRepository;
import com.concretepage.repo.dao.MetricDAO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


/**
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyApplication.class)
@WebAppConfiguration
@ActiveProfiles("test")
public class MetricControllerTest {

    public static final String BEAN_NAME = "beanName";
    public static final String BEAN_TYPE = "BeanType";
    public static final long DURATION = 10L;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;


    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private Metric metric;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MetricDAO metricDAO;
    @Autowired
    private MetricRepository metricRepository;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null", this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        metricRepository.deleteAll();

        metric = new Metric();
        metric.setBeanName(BEAN_NAME);
        metric.setBeanType(BEAN_TYPE);
        metric.setDuration(DURATION);
        metric.setCreated(new Date());
        metricRepository.save(metric);
    }


    @Test
    public void getMetricById() throws Exception {

    }

    @Test
    public void getAllMetrics() throws Exception {
        mockMvc.perform(get("/metric")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].beanName", is(BEAN_NAME)))
                .andExpect(jsonPath("$[0].beanType", is(BEAN_TYPE)))
                .andExpect(jsonPath("$[0].duration", is(DURATION)));
    }

    @Test
    public void updateMetric() throws Exception {
    }

    @Test
    public void deleteMetric() throws Exception {
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

}