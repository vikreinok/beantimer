package com.beantimer.controller;


import com.beantimer.model.ProcessedMetric;
import com.beantimer.service.MetricService;
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
@RequestMapping("showMetrics")
public class ShowMetricController {

    private final MetricService metricService;

    @Autowired
    public ShowMetricController(MetricService metricService) {
        this.metricService = metricService;
    }

    @GetMapping()
    public ModelAndView showCities(@RequestParam(value="sort", required = false) String sort, @RequestParam(value="dir",required = false) String dir, @RequestParam(value="username",required = false) String username) {
        List<ProcessedMetric> processedMetrics = metricService.getProcessedMetrics(sort, dir, username);

        Map<String, Object> params = new HashMap<>();
        params.put("metrics", processedMetrics);

        return new ModelAndView("showMetrics", params);
    }


}
