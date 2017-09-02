package com.concretepage.repo.dao;

import com.concretepage.entity.Metric;

import java.util.List;

public interface MetricDAO {
    List<Metric> getAllMetrics();

    Metric getMetricById(int id);

    void addMetric(Metric metric);

    void addMetrics(List<Metric> metrics);

    void deleteMetric(int id);

    boolean metricExists(String title, String location);
}
 