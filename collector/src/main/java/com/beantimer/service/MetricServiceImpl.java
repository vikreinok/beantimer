package com.beantimer.service;

import com.beantimer.entity.Metric;
import com.beantimer.model.ProcessedMetric;
import com.beantimer.repo.dao.MetricDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetricServiceImpl implements MetricService {

    @Autowired
    private MetricDAO metricDAO;

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
    public List<ProcessedMetric> getProcessedMetrics() {
        return metricDAO.getMetricsProcessed();
    }
}
