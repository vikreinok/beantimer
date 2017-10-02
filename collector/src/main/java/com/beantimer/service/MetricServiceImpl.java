package com.beantimer.service;

import com.beantimer.entity.Metric;
import com.beantimer.model.ProcessedMetric;
import com.beantimer.repo.dao.MetricDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.beantimer.model.ProcessedMetric.TOTAL_BEAN_NAME;
import static com.beantimer.model.ProcessedMetric.TOTAL_BEAN_TYPE;

@Service
public class MetricServiceImpl implements MetricService {

    private final MetricDAO metricDAO;

    @Autowired
    public MetricServiceImpl(MetricDAO metricDAO) {
        this.metricDAO = metricDAO;
    }

    @Override
    public Metric getMetricById(long id) {
        return metricDAO.getMetricById(id);
    }

    @Override
    public List<Metric> getAllMetrics() {
        return metricDAO.getAllMetrics();
    }

    @Override
    public synchronized void addMetric(Metric metric) {
        metricDAO.addMetric(metric);
    }

    @Override
    public void updateMetric(long id, Metric metric) throws IllegalArgumentException {
        Metric existingMetric = metricDAO.getMetricById(id);
        if (existingMetric == null) {
            throw new IllegalArgumentException("Can't find metric with ID " + id);
        }
        metricDAO.updateMetric(id, existingMetric, metric);
    }

    @Override
    public void addMetrics(List<Metric> metrics) {
        metricDAO.addMetrics(metrics);
    }

    @Override
    public void deleteMetric(long id) {
        metricDAO.deleteMetric(id);
    }

    @Override
    public void deleteAll() {
        metricDAO.deleteAll();
    }

    @Override
    public List<ProcessedMetric> getProcessedMetrics(String sort, String dir) {
        List<ProcessedMetric> metricsProcessed = metricDAO.getMetricsProcessed(sort, dir);

        double durationAvgTotal = 0;
        long durationMinTotal = 0;
        long durationMaxTotal = 0;
        long countTotal = 0;
        for (ProcessedMetric processedMetric : metricsProcessed) {
            durationAvgTotal += processedMetric.getDurationAvg();
            durationMinTotal += processedMetric.getDurationMin();
            durationMaxTotal += processedMetric.getDurationMax();
            countTotal += processedMetric.getCount();
        }

        metricsProcessed.add(new ProcessedMetric(TOTAL_BEAN_NAME, TOTAL_BEAN_TYPE, durationAvgTotal, durationMinTotal, durationMaxTotal, countTotal));

        return metricsProcessed;
    }
}
