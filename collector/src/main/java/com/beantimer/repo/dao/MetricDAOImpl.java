package com.beantimer.repo.dao;

import com.beantimer.controller.validator.OrderByValidator;
import com.beantimer.entity.Metric;
import com.beantimer.model.ProcessedMetric;
import com.beantimer.model.mapper.ProcessedMetricRowMapper;
import com.beantimer.repo.MetricRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Transactional
@Repository
public class MetricDAOImpl implements MetricDAO {

    @PersistenceContext
    private EntityManager entityManager;

    private final MetricRepository metricRepository;

    @Autowired
    public MetricDAOImpl(MetricRepository metricRepository) {
        this.metricRepository = metricRepository;
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
    public void addMetric(Metric metric) {
        metricRepository.save(metric);
    }

    @Override
    public void addMetrics(List<Metric> metrics) {
        metricRepository.save(metrics);
    }

    @Override
    public void deleteMetric(long id) {
        metricRepository.delete(id);
    }

    @Override
    public void updateMetric(long id, Metric existingMetric, Metric metric) {
        existingMetric.setBeanName(metric.getBeanName());
        existingMetric.setBeanType(metric.getBeanType());
        existingMetric.setDuration(metric.getDuration());
        existingMetric.setInitialisationStartTimeMillis(metric.getInitialisationStartTimeMillis());

        metricRepository.save(existingMetric);
    }

    @Override
    public List<ProcessedMetric> getMetricsProcessed(String sort, String dir) {

        String orderByClause = validateAndDecideOnUseOfSortParams(sort, dir);

        String queryStr = String.format("SELECT " +
                "beanName, beanType, 'singleton' AS beanScope, AVG(duration) AS durationAvg, MIN(duration) AS durationMin, MAX(duration) AS durationMax, COUNT(beanName) AS count " +
                "FROM Metric " +
                "GROUP BY beanName, beanType, beanScope " +
                "%s", orderByClause);

        Query query = entityManager.createQuery(queryStr);

        return new ProcessedMetricRowMapper().map(query.getResultList());
    }

    private String validateAndDecideOnUseOfSortParams(String sort, String dir) {
        String orderByClause = "";

        if (OrderByValidator.checkIfClazzContainsColumn(ProcessedMetric.class, sort) && OrderByValidator.validateDirection(dir)) {
            orderByClause = String.format("ORDER BY %s %s", sort, dir);
        }

        return orderByClause;
    }

    @Override
    public void deleteAll() {
        metricRepository.deleteAll();
    }
}
