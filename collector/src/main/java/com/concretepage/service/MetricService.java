package com.concretepage.service;

import com.concretepage.entity.Metric;

import java.util.List;

public interface MetricService {

    List<Metric> getAllMetrics();

    Metric getMetricById(int id);

    boolean addMetric(Metric metric);

    void deleteMetric(int id);
}
