package com.concretepage.service;

import com.concretepage.dao.MetricDAO;
import com.concretepage.entity.Metric;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MetricServiceImpl implements MetricService {


    @Autowired
    private MetricDAO metricDAO;

    @Override
    public Metric getMetricById(int id) {
        Metric obj = metricDAO.getMetricById(id);
        return obj;
    }

    @Override
    public List<Metric> getAllMetrics() {
        return metricDAO.getAllMetrics();
    }

    @Override
    public synchronized boolean addMetric(Metric metric) {
        if (metricDAO.metricExists(metric.getBeanName(), metric.getBeanType())) {
            return false;
        } else {
            metricDAO.addMetric(metric);
            return true;
        }
    }

    @Override
    public void deleteMetric(int id) {
        metricDAO.deleteMetric(id);
    }
}
