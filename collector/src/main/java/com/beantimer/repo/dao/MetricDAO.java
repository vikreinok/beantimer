package com.beantimer.repo.dao;

import com.beantimer.entity.Metric;
import com.beantimer.model.ProcessedMetric;

import java.util.List;

public interface MetricDAO {

    List<Metric> getAllMetrics();

    Metric getMetricById(long id);

    void addMetric(Metric metric);

    void addMetrics(List<Metric> metrics);

    void deleteMetric(long id);

    void updateMetric(long id, Metric existingMetric, Metric metric);

    List<ProcessedMetric> getMetricsProcessed(String sort, String dir);

    void deleteAll();
}
 