package com.beantimer.repo.dao;

import com.beantimer.entity.Metric;
import com.beantimer.model.GanttMetric;
import com.beantimer.model.ProcessedMetric;

import java.util.List;

public interface MetricDAO {


    void updateMetric(long id, Metric existingMetric, Metric metric);

    List<ProcessedMetric> getMetricsProcessed(String sort, String dir, String username);

    List<GanttMetric> getMetricsGantt(String username);
}
 