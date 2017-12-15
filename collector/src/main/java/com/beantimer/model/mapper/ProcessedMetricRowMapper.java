package com.beantimer.model.mapper;

import com.beantimer.model.ProcessedMetric;

import java.util.ArrayList;
import java.util.List;

public class ProcessedMetricRowMapper {

    public List<ProcessedMetric> map(List resultSet) {

        List<ProcessedMetric> processedMetrics = new ArrayList<>();

        for (Object result : resultSet) {

            Object[] rs = (Object[]) result;
            ProcessedMetric processedMetric = new ProcessedMetric(
                    (String) rs[0],
                    (String) rs[1],
                    (String) rs[2],
                    (Boolean)rs[3],
                    ((Number)rs[4]).doubleValue(),
                    ((Number)rs[5]).longValue(),
                    ((Number)rs[6]).longValue(),
                    ((Number)rs[7]).longValue()
            );

            processedMetrics.add(processedMetric);

        }
        return processedMetrics;
    }

}