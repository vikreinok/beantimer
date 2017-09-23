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
        metricDAO.deleteAll();
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


        List<ProcessedMetric> processedMetrics = metricDAO.getMetricsProcessed(null, null);


        assertNotNull(processedMetrics);
        assertEquals(1, processedMetrics.size());
        ProcessedMetric processedMetric = processedMetrics.get(0);
        assertEquals(beanType, processedMetric.getBeanType());
        assertEquals(beanName, processedMetric.getBeanName());
        assertEquals(durationAvg, processedMetric.getDurationAvg(), 0.1);
        assertEquals(durationMin, processedMetric.getDurationMin());
        assertEquals(durationMax, processedMetric.getDurationMax());

    }

    @Test
    public void testGetProcessedMetric_sorting() throws Exception {

        String beanType1 = "A1";
        String beanName1 = "a1";
        String beanType2 = "A2";
        String beanName2 = "a2";
        Long durationMin = 10L;
        Long durationMax = 20L;


        Metric m1 = new Metric();
        m1.setBeanType(beanType1);
        m1.setBeanName(beanName1);
        m1.setDuration(durationMin);

        Metric m2 = new Metric();
        m2.setBeanType(beanType2);
        m2.setBeanName(beanName2);
        m2.setDuration(durationMax);

        metricDAO.addMetric(m1);
        metricDAO.addMetric(m2);


        List<ProcessedMetric> processedMetrics = metricDAO.getMetricsProcessed("beanName", "ASC");


        assertNotNull(processedMetrics);
        assertEquals(2, processedMetrics.size());
        ProcessedMetric processedMetric1 = processedMetrics.get(0);
        ProcessedMetric processedMetric2 = processedMetrics.get(1);
        assertEquals(beanType1, processedMetric1.getBeanType());
        assertEquals(beanName1, processedMetric1.getBeanName());

        assertEquals(beanType2, processedMetric2.getBeanType());
        assertEquals(beanName2, processedMetric2.getBeanName());

    }

}