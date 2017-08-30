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
        List<Metric> all = metricRepository.findAll();
        Metric metric = new Metric();
        metric.setBeanName("na");
        metric.setBeanType("ta");
        metric.setDuration(3);
        all.add(metric);
        return all;
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
    public void deleteMetric(long id) {
        metricRepository.delete(id);
    }
}
