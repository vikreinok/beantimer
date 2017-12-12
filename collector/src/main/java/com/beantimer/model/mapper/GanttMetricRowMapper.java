package com.beantimer.model.mapper;

import com.beantimer.model.GanttMetric;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class GanttMetricRowMapper {

    public List<GanttMetric> map(List resultSet) {

        List<GanttMetric> processedMetrics = new ArrayList<>();

        for (Object result : resultSet) {

            Object[] rs = (Object[]) result;
            GanttMetric ganttMetric = new GanttMetric(
                    (String) rs[0],
                    (String) rs[1],
                    (String) rs[2],
                    (Boolean)rs[3],
                    ((BigInteger) rs[4]).doubleValue(),
                    Long.valueOf(rs[5] != null ? rs[5].toString() : "0"),
                    Long.valueOf(rs[6] != null ? rs[6].toString() : "0")
            );

            processedMetrics.add(ganttMetric);

        }
        return processedMetrics;
    }

}