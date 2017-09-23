package com.beantimer.repo.dao;

import com.beantimer.controller.validator.OrderByValidator;
import com.beantimer.entity.Metric;
import com.beantimer.model.ProcessedMetric;
import com.beantimer.repo.MetricRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Transactional
@Repository
public class MetricDAOImpl implements MetricDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private MetricRepository metricRepository;

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

        String orderByClause = validateAndDescideonuseOfSortParams(sort, dir);

        String queryStr = String.format("SELECT " +
                "NEW com.beantimer.model.ProcessedMetric(beanName, beanType, AVG(duration), MIN(duration), MAX(duration), COUNT(beanName)) " +
                "FROM Metric " +
                "GROUP BY beanName, beanType " +
                "%s", orderByClause);

        TypedQuery<ProcessedMetric> query = entityManager.createQuery(queryStr, ProcessedMetric.class);

        return query.getResultList();
    }

    private String validateAndDescideonuseOfSortParams(String sort, String dir) {
        String orderByClause = "";
        try {
            OrderByValidator.validate(ProcessedMetric.class, sort, dir);
            orderByClause = String.format("ORDER BY %s %s", sort, dir);
        } catch (Exception e) {

        }
        return orderByClause;
    }

    @Override
    public void deleteAll() {
        metricRepository.deleteAll();
    }
}
