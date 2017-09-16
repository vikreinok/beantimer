package com.beantimer.controller;

import com.beantimer.entity.Metric;
import com.beantimer.model.ProcessedMetric;
import com.beantimer.service.MetricService;
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
        return new ResponseEntity<>(metric, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<Metric>> getAllMetrics() {
        List<Metric> list = metricService.getAllMetrics();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/processed")
    public ResponseEntity<List<ProcessedMetric>> getProcessedMetrics() {
        List<ProcessedMetric> processedMetrics = metricService.getProcessedMetrics();
        return new ResponseEntity<>(processedMetrics, HttpStatus.OK);
    }

    @PutMapping()
    public ResponseEntity<Metric> addOrUpdateMetric(@RequestBody Metric metric) {
        metricService.addMetric(metric);
        return new ResponseEntity<>(metric, HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Metric> updateMetric(@PathVariable("id") Long id, @RequestBody Metric metric) {
        metricService.updateMetric(id, metric);
        return new ResponseEntity<>(metric, HttpStatus.OK);
    }

    @PutMapping("/all")
    public ResponseEntity<List<Metric>> updateMetrics(@RequestBody List<Metric> metrics) {
        metricService.addMetrics(metrics);
        return new ResponseEntity<>(metrics, HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteMetric(@PathVariable("id") Integer id) {
        metricService.deleteMetric(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @GetMapping("/deleteMetrics")
    public ResponseEntity<Void> deleteAllMetric() {
        metricService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}