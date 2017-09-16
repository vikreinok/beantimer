package com.beantimer.repo.dao;

import com.beantimer.SpringContextTest;
import com.beantimer.entity.Metric;
import com.beantimer.model.ProcessedMetric;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;

/**
 *
 */
public class MetricDAOTest extends SpringContextTest {

    @Autowired
    private MetricDAO metricDAO;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void testGetProcessedMetric() throws Exception {

        String beanType = "A";
        String beanName = "a";
        Long durationMin = 10L;
        Long durationMax = 20L;
        Long durationAvg = (durationMax + durationMin) / 2;


        Metric m1 = new Metric();
        m1.setBeanType(beanType);
        m1.setBeanName(beanName);
        m1.setDuration(durationMin);

        Metric m2 = new Metric();
        m2.setBeanType(beanType);
        m2.setBeanName(beanName);
        m2.setDuration(durationMax);

        metricDAO.addMetric(m1);
        metricDAO.addMetric(m2);


        List<ProcessedMetric> metricProcessedList = metricDAO.getMetricsProcessed();


        assertNotNull(metricProcessedList);
        assertEquals(1, metricProcessedList.size());
        ProcessedMetric metricProcessed = metricProcessedList.get(0);
        assertEquals(beanType, metricProcessed.getBeanType());
        assertEquals(beanName, metricProcessed.getBeanName());
        assertEquals(durationAvg, metricProcessed.getDurationAvg(), 0.1);
        assertEquals(durationMin, metricProcessed.getDurationMin());
        assertEquals(durationMax, metricProcessed.getDurationMax());


    }
}