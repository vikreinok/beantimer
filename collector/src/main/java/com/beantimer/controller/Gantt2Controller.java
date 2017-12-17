package com.beantimer.controller;


import com.beantimer.model.GanttMetric;
import com.beantimer.service.MetricService;
import ee.aktors.beantimer.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Controller
@RequestMapping("gantt2")
public class Gantt2Controller {

    private final MetricService metricService;

    @Autowired
    public Gantt2Controller(MetricService metricService) {
        this.metricService = metricService;
    }

    @GetMapping()
    public ModelAndView showCities(@RequestParam(value="username",required = false) String username) {
//        List<GanttMetric> ganttMetrics = new ArrayList<>();
//        ganttMetrics.add(new GanttMetric("A", "aType", "scope", true, 130D, 100L, new Date().getTime(), null));
//        ganttMetrics.add(new GanttMetric("b", "bType", "scope", false, 140D, 170L, new Date().getTime() + 110, "A"));

        List<GanttMetric> ganttMetrics = metricService.getGanttMetrics("viktor_reinok");

        int limit = 10;
        if (ganttMetrics.size() > limit) {
            ganttMetrics = ganttMetrics.subList(0, limit);
        }
        String json = JsonUtil.transformToJsonArray(ganttMetrics);

        Map<String, Object> params = new HashMap<>();
        params.put("metrics", json);

        return new ModelAndView("gantt2", params);
    }


}
