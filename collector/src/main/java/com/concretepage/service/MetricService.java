package com.concretepage.service;

import com.concretepage.entity.Metric;
import com.concretepage.model.ProcessedMetric;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MetricService {

    List<Metric> getAllMetrics();

    Metric getMetricById(long id);

    void addMetric(Metric metric);

    void updateMetric(long id, Metric metric);

    void addMetrics(List<Metric> metrics);

    void deleteMetric(long id);

    void deleteAll();

    List<ProcessedMetric> getProcessedMetrics();
}
