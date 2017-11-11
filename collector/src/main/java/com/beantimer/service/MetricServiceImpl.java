package com.beantimer.service;

import com.beantimer.entity.Metric;
import com.beantimer.entity.User;
import com.beantimer.model.ProcessedMetric;
import com.beantimer.repo.MetricRepository;
import com.beantimer.repo.dao.MetricDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.beantimer.model.ProcessedMetric.TOTAL_BEAN_NAME;
import static com.beantimer.model.ProcessedMetric.TOTAL_BEAN_SCOPE;
import static com.beantimer.model.ProcessedMetric.TOTAL_BEAN_TYPE;
import static com.beantimer.model.ProcessedMetric.TOTAL_PRIMARY;

@Service
public class MetricServiceImpl implements MetricService {

    private final MetricDAO metricDAO;
    private final UserService userService;

    @Autowired
    private MetricRepository metricRepository;

    @Autowired
    public MetricServiceImpl(MetricDAO metricDAO, UserService userService) {
        this.metricDAO = metricDAO;
        this.userService = userService;
    }

    @Override
    public Metric getMetricById(long id) {
        return metricRepository.findOne(id);
    }

    @Override
    public List<Metric> getAllMetrics() {
        return metricRepository.findAll();
    }

    @Override
    public synchronized void addMetric(Metric metric) {
        metricRepository.save(metric);
    }

    @Override
    public void updateMetric(long id, Metric metric) throws IllegalArgumentException {
        Metric existingMetric = metricRepository.findOne(id);
        if (existingMetric == null) {
            throw new IllegalArgumentException("Can't find metric with ID " + id);
        }
        metricDAO.updateMetric(id, existingMetric, metric);
    }

    @Override
    @Transactional
    public void addMetrics(List<Metric> metrics, String username) {

        Optional<User> userOpt = userService.findByUsername(username);

        User user;
        if (userOpt.isPresent()) {
            user = userOpt.get();
        } else {
            user = userService.addUser(new User(username));
        }


        for (Metric metric : metrics) {
            metric.setUser(user);
        }

        metricRepository.save(metrics);
    }

    @Override
    public void deleteMetric(long id) {
        metricRepository.delete(id);
    }

    @Override
    public void deleteAll() {
        metricRepository.deleteAll();
    }

    @Override
    public List<ProcessedMetric> getProcessedMetrics(String sort, String dir) {
        List<ProcessedMetric> metricsProcessed = metricDAO.getMetricsProcessed(sort, dir);

        double durationAvgTotal = 0;
        long durationMinTotal = 0;
        long durationMaxTotal = 0;
        long countTotal = 0;
        for (ProcessedMetric processedMetric : metricsProcessed) {
            durationAvgTotal += processedMetric.getDurationAvg();
            durationMinTotal += processedMetric.getDurationMin();
            durationMaxTotal += processedMetric.getDurationMax();
            countTotal += processedMetric.getCount();
        }

        metricsProcessed.add(new ProcessedMetric(TOTAL_BEAN_NAME, TOTAL_BEAN_TYPE, TOTAL_BEAN_SCOPE, TOTAL_PRIMARY, durationAvgTotal, durationMinTotal, durationMaxTotal, countTotal));

        return metricsProcessed;
    }
}
