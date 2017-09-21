package com.beantimer.repo.dao;

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
    public List<ProcessedMetric> getMetricsProcessed() {

        String queryStr = "SELECT NEW com.beantimer.model.ProcessedMetric(beanName, beanType, AVG(duration), MIN(duration), MAX(duration)) FROM Metric  GROUP BY beanName, beanType";

        TypedQuery<ProcessedMetric> query = entityManager.createQuery(queryStr, ProcessedMetric.class);

        return query.getResultList();
    }

    @Override
    public void deleteAll() {
        metricRepository.deleteAll();
    }
}
