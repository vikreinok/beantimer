package com.beantimer.service;

import com.beantimer.entity.Metric;
import com.beantimer.model.ProcessedMetric;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MetricService {

    List<Metric> getAllMetrics();

    Metric getMetricById(long id);

    void addMetric(Metric metric);

    void updateMetric(long id, Metric metric);

    void addMetrics(List<Metric> metrics, String username);

    void deleteMetric(long id);

    void deleteAll();

    List<ProcessedMetric> getProcessedMetrics(String sort, String dir, String username);
}
