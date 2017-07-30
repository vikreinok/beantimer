package com.concretepage.controller;

import com.concretepage.entity.Metric;
import com.concretepage.service.MetricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @Autowired
    private MetricService metricService;

    @GetMapping()
    public ModelAndView showCities() {

        List<Metric> metrics = metricService.getAllMetrics();

        Map<String, Object> params = new HashMap<>();
        params.put("metrics", metrics);

        return new ModelAndView("showMetrics", params);
    }


}
