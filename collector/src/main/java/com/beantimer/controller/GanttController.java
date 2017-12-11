package com.beantimer.controller;


import com.beantimer.model.ProcessedMetric;
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
    public ModelAndView showCities(@RequestParam(value="sort", required = false) String sort, @RequestParam(value="dir",required = false) String dir, @RequestParam(value="username",required = false) String username) {
        List<ProcessedMetric> processedMetrics = metricService.getProcessedMetrics(sort, dir, username);

        ProcessedMetric processedMetric = new ProcessedMetric("name", "type", "scope", true, 10d, 123L, 32L, 31L);
        processedMetrics.add(processedMetric);

        String json = JsonUtil.transformToJsonArray(processedMetrics);

        Map<String, Object> params = new HashMap<>();
        params.put("metrics", json);

        return new ModelAndView("gantt", params);
    }


}
