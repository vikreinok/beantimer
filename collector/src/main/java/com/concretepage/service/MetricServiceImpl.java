package com.concretepage.service;

import com.concretepage.repo.MetricRepository;
import com.concretepage.repo.dao.MetricDAO;
import com.concretepage.entity.Metric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetricServiceImpl implements MetricService {


    @Autowired
    private MetricDAO metricDAO;
    @Autowired
    private MetricRepository metricRepository;

    @Override
    public Metric getMetricById(long id) {
        return metricRepository.findOne(id);
    }

    @Override
    public List<Metric> getAllMetrics() {
        return metricRepository.findAll();
    }

    @Override
    public synchronized void addMetric(Metric metric) {
        metricDAO.addMetric(metric);
    }

    @Override
    public void updateMetric(long id, Metric metric) {
        metricDAO.updateMetric(id, metric);
    }

    @Override
    public void addMetrics(List<Metric> metrics) {
        metricDAO.addMetrics(metrics);
    }

    @Override
    public void deleteMetric(long id) {
        metricRepository.delete(id);
    }

    @Override
    public void deleteAll() {
        metricRepository.deleteAll();
    }
}
