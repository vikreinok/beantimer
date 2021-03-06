package com.beantimer.repo.dao;

import com.beantimer.SpringContextTest;
import com.beantimer.entity.Metric;
import com.beantimer.entity.User;
import com.beantimer.model.GanttMetric;
import com.beantimer.model.ProcessedMetric;
import com.beantimer.repo.MetricRepository;
import com.beantimer.repo.UserRepository;
import org.junit.After;
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

    @Autowired
    private MetricRepository metricRepository;
    @Autowired
    private UserRepository userRepository;

    @After
    public void tearDown() throws Exception {
        metricRepository.deleteAll();
    }


    @Test
    public void testGetProcessedMetric() throws Exception {

        String beanType = "A";
        String beanName = "a";
        String beanScope = "singleton";
        boolean primaryBean = false;
        Long durationMin = 10L;
        Long durationMax = 20L;
        Long durationAvg = (durationMax + durationMin) / 2;


        Metric m1 = new Metric();
        m1.setBeanType(beanType);
        m1.setBeanName(beanName);
        m1.setBeanScope(beanScope);
        m1.setPrimaryBean(primaryBean);
        m1.setDuration(durationMin);

        Metric m2 = new Metric();
        m2.setBeanType(beanType);
        m2.setBeanName(beanName);
        m2.setBeanScope(beanScope);
        m2.setPrimaryBean(primaryBean);
        m2.setDuration(durationMax);

        metricRepository.save(m1);
        metricRepository.save(m2);


        List<ProcessedMetric> processedMetrics = metricDAO.getMetricsProcessed(null, null, null);


        assertNotNull(processedMetrics);
        assertEquals(1, processedMetrics.size());
        ProcessedMetric processedMetric = processedMetrics.get(0);
        assertEquals(beanType, processedMetric.getBeanType());
        assertEquals(beanName, processedMetric.getBeanName());
        assertEquals(beanScope, processedMetric.getBeanScope());
        assertEquals(primaryBean, processedMetric.isPrimaryBean());
        assertEquals(durationAvg, processedMetric.getDurationAvg(), 0.1);
        assertEquals(durationMin, processedMetric.getDurationMin());
        assertEquals(durationMax, processedMetric.getDurationMax());

    }

    @Test
    public void testGetMetricsGantt() throws Exception {

        String beanType = "A";
        String beanName = "a";
        String beanScope = "singleton";

        String beanType2 = "B";
        String beanName2 = "b";
        String beanScope2 = "primary";
        boolean primaryBean = false;
        boolean primaryBean2 = true;
        Long durationMin = 10L;
        Long durationMax = 20L;
        Long durationAvg = (durationMax + durationMin) / 2;
        Long initialisationStartTimeMillis1 = 11L;
        Long initialisationStartTimeMillis2 = 22L;
        Long initialisationStartTimeMillis3 = 33L;


        Metric m1 = new Metric();
        m1.setBeanType(beanType);
        m1.setBeanName(beanName);
        m1.setBeanScope(beanScope);
        m1.setPrimaryBean(primaryBean);
        m1.setInitialisationStartTimeMillis(initialisationStartTimeMillis1);
        m1.setDuration(durationMin);

        Metric m2 = new Metric();
        m2.setBeanType(beanType2);
        m2.setBeanName(beanName2);
        m2.setBeanScope(beanScope2);
        m2.setPrimaryBean(primaryBean2);
        m2.setInitialisationStartTimeMillis(initialisationStartTimeMillis2);
        m2.setDuration(durationMax);

        Metric m3 = new Metric();
        m3.setBeanType(beanType);
        m3.setBeanName(beanName);
        m3.setBeanScope(beanScope);
        m3.setPrimaryBean(primaryBean);
        m3.setInitialisationStartTimeMillis(initialisationStartTimeMillis3);
        m3.setDuration(durationMax);

        metricRepository.save(m1);
        metricRepository.save(m2);
        metricRepository.save(m3);


        List<GanttMetric> processedMetrics = metricDAO.getMetricsGantt(null);

        processedMetrics.forEach(ganttMetric -> System.err.println(ganttMetric));

        assertNotNull(processedMetrics);
        assertEquals(2, processedMetrics.size());
        GanttMetric gm1 = processedMetrics.get(0);
        assertEquals(beanType2, gm1.getBeanType());
        assertEquals(beanName2, gm1.getBeanName());
        assertEquals(beanScope2, gm1.getBeanScope());
        assertEquals(primaryBean2, gm1.isPrimaryBean());
        assertEquals(durationMax, gm1.getDurationAvg(), 0.1);
        assertEquals(durationMax, gm1.getDuration());
        assertEquals(initialisationStartTimeMillis2, gm1.getInitialisationStartTimeMillis());

        GanttMetric gm2 = processedMetrics.get(1);
        assertEquals(beanType, gm2.getBeanType());
        assertEquals(beanName, gm2.getBeanName());
        assertEquals(beanScope, gm2.getBeanScope());
        assertEquals(primaryBean, gm2.isPrimaryBean());
        assertEquals(durationAvg, gm2.getDurationAvg(), 0.1);
        assertEquals(durationMax, gm2.getDuration());
        assertEquals(initialisationStartTimeMillis3, gm2.getInitialisationStartTimeMillis());
    }

    @Test
    public void testGetProcessedMetric_sorting() throws Exception {

        String beanType1 = "A1";
        String beanName1 = "a1";
        boolean primaryBean1 = true;
        String beanType2 = "A2";
        String beanName2 = "a2";
        boolean primaryBean2 = false;
        Long durationMin = 10L;
        Long durationMax = 20L;


        Metric m1 = new Metric();
        m1.setBeanType(beanType1);
        m1.setBeanName(beanName1);
        m1.setPrimaryBean(primaryBean1);
        m1.setDuration(durationMin);

        Metric m2 = new Metric();
        m2.setBeanType(beanType2);
        m2.setBeanName(beanName2);
        m2.setPrimaryBean(primaryBean2);
        m2.setDuration(durationMax);

        metricRepository.save(m1);
        metricRepository.save(m2);


        List<ProcessedMetric> processedMetrics = metricDAO.getMetricsProcessed("beanName", "ASC", null);


        assertNotNull(processedMetrics);
        assertEquals(2, processedMetrics.size());
        ProcessedMetric processedMetric1 = processedMetrics.get(0);
        ProcessedMetric processedMetric2 = processedMetrics.get(1);
        assertEquals(beanType1, processedMetric1.getBeanType());
        assertEquals(beanName1, processedMetric1.getBeanName());
        assertEquals(primaryBean1, processedMetric1.isPrimaryBean());

        assertEquals(beanType2, processedMetric2.getBeanType());
        assertEquals(beanName2, processedMetric2.getBeanName());
        assertEquals(primaryBean2, processedMetric2.isPrimaryBean());

    }

    @Test
    public void testGetProcessedMetric_user() throws Exception {

        String beanType1 = "A1";
        String beanName1 = "a1";
        boolean primaryBean1 = true;

        Long durationMin = 10L;

        Metric m1 = new Metric();
        m1.setBeanType(beanType1);
        m1.setBeanName(beanName1);
        m1.setPrimaryBean(primaryBean1);
        m1.setDuration(durationMin);

        User u1 = new User("username");

        userRepository.save(u1);
        m1.setUser(u1);
        metricRepository.save(m1);


        List<ProcessedMetric> processedMetrics = metricDAO.getMetricsProcessed("beanName", "ASC", u1.getUsername());

        assertNotNull(processedMetrics);
        assertEquals(1, processedMetrics.size());
        ProcessedMetric processedMetric1 = processedMetrics.get(0);
        assertEquals(beanType1, processedMetric1.getBeanType());
        assertEquals(beanName1, processedMetric1.getBeanName());
        assertEquals(primaryBean1, processedMetric1.isPrimaryBean());


        processedMetrics = metricDAO.getMetricsProcessed("beanName", "ASC", u1.getUsername() + "dummy");
        assertEquals(0, processedMetrics.size());


    }

}