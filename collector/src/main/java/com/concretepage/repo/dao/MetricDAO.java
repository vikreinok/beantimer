package com.concretepage.repo.dao;

import com.concretepage.entity.Metric;
import com.concretepage.model.ProcessedMetric;

import java.util.List;

public interface MetricDAO {

    List<Metric> getAllMetrics();

    Metric getMetricById(long id);

    void addMetric(Metric metric);

    void addMetrics(List<Metric> metrics);

    void deleteMetric(long id);

    boolean metricExists(String title, String location);

    void updateMetric(long id, Metric metric);

    List<ProcessedMetric> getMetricsProcessed();
}
 