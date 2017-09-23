package com.beantimer.model.mapper;

import com.beantimer.model.ProcessedMetric;

import java.util.ArrayList;
import java.util.List;

/**
 * Fix issues with native queries
 * Use JPA2.1 native query entity
 */
public class ProcessedMetricRowMapper {

    public List<ProcessedMetric> map(List resultSet) {

        List<ProcessedMetric> processedMetrics = new ArrayList<>();

        for (Object result : resultSet) {

            Object[] rs = (Object[]) result;

            ProcessedMetric processedMetric = new ProcessedMetric(
                    (String) rs[0],
                    (String) rs[1],
                    (Double) rs[2],
                    (Long) rs[3],
                    (Long) rs[4],
                    (Long) rs[5]
            );

            processedMetrics.add(processedMetric);

        }
        return processedMetrics;
    }

}