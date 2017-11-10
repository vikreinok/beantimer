package com.beantimer.controller;


import com.beantimer.SpringContextTest;
import com.beantimer.entity.Metric;
import com.beantimer.entity.User;
import com.beantimer.repo.MetricRepository;
import com.beantimer.repo.UserRepository;
import com.beantimer.repo.dao.MetricDAO;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import static com.beantimer.model.ProcessedMetric.TOTAL_BEAN_NAME;
import static com.beantimer.model.ProcessedMetric.TOTAL_BEAN_TYPE;
import static com.beantimer.model.ProcessedMetric.TOTAL_PRIMARY;
import static ee.aktors.beantimer.constant.CommonConstant.HEADER_NAME_X_USER;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


/**
 *
 */
public class MetricControllerTest extends SpringContextTest {

    public static final String BEAN_NAME = "beanName";
    public static final String BEAN_TYPE = "BeanType";
    public static final String BEAN_SCOPE = "singleton";
    public static final Boolean BEAN_PRIMARY = true;
    public static final Long DURATION = 100000000000L;
    public static final Long INITIALISATION_START_TIME_MILLIS = 10000000000L;

    public static final String USER_NAME = "username";

    private MockMvc mockMvc;

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private Metric metric;
    private User user;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MetricDAO metricDAO;

    @Autowired
    private MetricRepository metricRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.stream(converters)
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
        metric.setBeanScope(BEAN_SCOPE);
        metric.setPrimaryBean(BEAN_PRIMARY);
        metric.setDuration(DURATION);
        metric.setInitialisationStartTimeMillis(INITIALISATION_START_TIME_MILLIS);
        metric.setCreated(new Date());
        metric = metricRepository.save(metric);

        user = new User(USER_NAME);
        user = userRepository.save(user);
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
    public void getProcessedMetrics() throws Exception {

        metricRepository.delete(metric.getId());

        String beanType = "A";
        String beanName = "a";
        boolean primary = false;
        int durationMin = 10;
        int durationMax = 20;
        Double durationAvg = (double)(durationMax + durationMin) / 2;


        Metric m1 = new Metric();
        m1.setBeanType(beanType);
        m1.setBeanName(beanName);
        m1.setPrimaryBean(primary);
        m1.setDuration(durationMin);

        Metric m2 = new Metric();
        m2.setBeanType(beanType);
        m2.setBeanName(beanName);
        m2.setPrimaryBean(primary);
        m2.setDuration(durationMax);

        metricRepository.save(m1);
        metricRepository.save(m2);

        mockMvc.perform(get("/metric/processed")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].beanName", is(beanName)))
                .andExpect(jsonPath("$[0].beanType", is(beanType)))
                .andExpect(jsonPath("$[0].primaryBean", is(primary)))
                .andExpect(jsonPath("$[0].durationAvg", is(durationAvg)))
                .andExpect(jsonPath("$[0].durationMin", is(durationMin)))
                .andExpect(jsonPath("$[0].durationMax", is(durationMax)))
                .andExpect(jsonPath("$[0].count", is(2)))
                .andExpect(jsonPath("$[1].beanName", is(TOTAL_BEAN_NAME)))
                .andExpect(jsonPath("$[1].beanType", is(TOTAL_BEAN_TYPE)))
                .andExpect(jsonPath("$[1].primaryBean", is(TOTAL_PRIMARY)))
                .andExpect(jsonPath("$[1].durationAvg", is(durationAvg)))
                .andExpect(jsonPath("$[1].durationMin", is(durationMin)))
                .andExpect(jsonPath("$[1].durationMax", is(durationMax)))
                .andExpect(jsonPath("$[1].count", is(2)));

        metricRepository.delete(m1.getId());
        metricRepository.delete(m2.getId());
    }

    @Test
    public void updateMetric() throws Exception {

        Long updatedDuration = DURATION + 1;

        Metric metric = new Metric();
        metric.setBeanName(this.metric.getBeanName());
        metric.setBeanType(this.metric.getBeanType());
        metric.setBeanScope(this.metric.getBeanScope());
        metric.setPrimaryBean(this.metric.isPrimaryBean());
        metric.setDuration(updatedDuration);
        metric.setInitialisationStartTimeMillis(this.metric.getInitialisationStartTimeMillis());

        mockMvc.perform(post("/metric/{id}", this.metric.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json(metric)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.beanName", is(BEAN_NAME)))
                .andExpect(jsonPath("$.beanType", is(BEAN_TYPE)))
                .andExpect(jsonPath("$.beanScope", is(BEAN_SCOPE)))
                .andExpect(jsonPath("$.initialisationStartTimeMillis", is(INITIALISATION_START_TIME_MILLIS)))
                .andExpect(jsonPath("$.duration", is(updatedDuration)));

        assertEquals(updatedDuration, metricRepository.findOne(this.metric.getId()).getDuration());

    }

    @Test
    public void addMetric() throws Exception {

        long countMetricBefore = metricRepository.count();
        long countUserBefore = userRepository.count();

        Metric metric = new Metric();
        metric.setBeanName(this.metric.getBeanName());
        metric.setBeanType(this.metric.getBeanType());
        metric.setBeanScope(this.metric.getBeanScope());
        metric.setPrimaryBean(this.metric.isPrimaryBean());
        metric.setDuration(this.metric.getDuration());
        metric.setInitialisationStartTimeMillis(this.metric.getInitialisationStartTimeMillis());

        mockMvc.perform(put("/metric/all")
                .header(HEADER_NAME_X_USER, user.getUsername())
                .contentType(MediaType.APPLICATION_JSON)
                .content(json(Arrays.asList(metric))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].beanName", is(BEAN_NAME)))
                .andExpect(jsonPath("$[0].beanType", is(BEAN_TYPE)))
                .andExpect(jsonPath("$[0].beanScope", is(BEAN_SCOPE)))
                .andExpect(jsonPath("$[0].initialisationStartTimeMillis", is(INITIALISATION_START_TIME_MILLIS)))
                .andExpect(jsonPath("$[0].duration", is(DURATION)))
                .andExpect(jsonPath("$[0].primaryBean", is(BEAN_PRIMARY)));

        assertEquals(countMetricBefore + 1, metricRepository.count());
        assertEquals(countUserBefore + 1, userRepository.count());

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