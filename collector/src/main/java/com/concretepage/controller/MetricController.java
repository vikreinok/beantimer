package com.concretepage.controller;

import com.concretepage.entity.Metric;
import com.concretepage.service.MetricService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("metric")
public class MetricController {

    @Autowired
    private MetricService metricService;

    @GetMapping("/{id}")
    public ResponseEntity<Metric> getMetricById(@PathVariable("id") Integer id) {
        Metric metric = metricService.getMetricById(id);
        return new ResponseEntity<Metric>(metric, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Metric>> getAllMetrics() {
        List<Metric> list = metricService.getAllMetrics();
        return new ResponseEntity<List<Metric>>(list, HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<Metric> updateMetric(@RequestBody Metric metric) {
        metricService.addMetric(metric);
        return new ResponseEntity<Metric>(metric, HttpStatus.OK);
    }

    @PutMapping("/all")
    public ResponseEntity<List<Metric>> updateMetrics(@RequestBody List<Metric> metrics) {
        metricService.addMetrics(metrics);
        return new ResponseEntity<List<Metric>>(metrics, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteMetric(@PathVariable("id") Integer id) {
        metricService.deleteMetric(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/deleteMetrics")
    public ResponseEntity<Void> deleteAllMetric() {
        metricService.deleteAll();
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}