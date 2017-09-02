package com.concretepage.service;

import com.concretepage.entity.Metric;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MetricService {

    List<Metric> getAllMetrics();

    Metric getMetricById(long id);

    void addMetric(Metric metric);

    void addMetrics(List<Metric> metrics);

    void deleteMetric(long id);

    void deleteAll();
}
