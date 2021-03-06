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
@RequestMapping("gantt")
public class GanttController {

    private final MetricService metricService;

    @Autowired
    public GanttController(MetricService metricService) {
        this.metricService = metricService;
    }

    @GetMapping()
    public ModelAndView showCities(@RequestParam(value="username",required = false) String username) {
        List<GanttMetric> ganttMetrics = metricService.getGanttMetrics("viktor_reinok");

        int limit = 2000;
        if (ganttMetrics.size() > limit) {
            ganttMetrics = ganttMetrics.subList(0, limit);
        }
        String json = JsonUtil.transformToJsonArray(ganttMetrics);

        Map<String, Object> params = new HashMap<>();
        params.put("metrics", json);

        return new ModelAndView("gantt", params);
    }


}
