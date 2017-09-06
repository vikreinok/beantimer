package com.concretepage.controller;


import com.concretepage.MyApplication;
import com.concretepage.entity.Metric;
import com.concretepage.repo.MetricRepository;
import com.concretepage.repo.dao.MetricDAO;
import org.junit.Before;
import org.junit.Ignore;
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
import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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
    public static final Long DURATION = 100000000000L;
    public static final Long INITIALISATION_START_TIME_MILLIS = 10000000000L;

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
        metric.setInitialisationStartTimeMillis(INITIALISATION_START_TIME_MILLIS);
        metric.setCreated(new Date());
        metric = metricRepository.save(metric);
    }


    @Test
    public void getMetricById() throws Exception {

        mockMvc.perform(get("/metric/{id}", metric.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.beanName", is(BEAN_NAME)))
                .andExpect(jsonPath("$.beanType", is(BEAN_TYPE)))
                .andExpect(jsonPath("$.initialisationStartTimeMillis", is(INITIALISATION_START_TIME_MILLIS)))
                .andExpect(jsonPath("$.duration", is(DURATION)));

    }

    @Test
    public void getAllMetrics() throws Exception {
        mockMvc.perform(get("/metric")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].beanName", is(BEAN_NAME)))
                .andExpect(jsonPath("$[0].beanType", is(BEAN_TYPE)))
                .andExpect(jsonPath("$[0].initialisationStartTimeMillis", is(INITIALISATION_START_TIME_MILLIS)))
                .andExpect(jsonPath("$[0].duration", is(DURATION)));
    }

    @Test
    @Ignore("Fix this test. Add post method for updating")
    public void updateMetric() throws Exception {

        Long updatedDuration = DURATION + 1;

        Metric metric = new Metric();
        metric.setBeanName(this.metric.getBeanName());
        metric.setBeanType(this.metric.getBeanType());
        metric.setDuration(updatedDuration);
        metric.setInitialisationStartTimeMillis(this.metric.getInitialisationStartTimeMillis());

        mockMvc.perform(put("/metric")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json(metric)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.beanName", is(BEAN_NAME)))
                .andExpect(jsonPath("$.beanType", is(BEAN_TYPE)))
                .andExpect(jsonPath("$.initialisationStartTimeMillis", is(INITIALISATION_START_TIME_MILLIS)))
                .andExpect(jsonPath("$.duration", is(updatedDuration)));

        assertEquals(updatedDuration, metricRepository.findOne(this.metric.getId()).getDuration());

    }

    @Test
    public void deleteMetric() throws Exception {

        mockMvc.perform(delete("/metric/{id}", metric.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        assertNull(metricRepository.findOne(metric.getId()));

    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }

}