package com.concretepage.repo.dao;

import com.concretepage.entity.Metric;
import com.concretepage.model.ProcessedMetric;
import com.concretepage.repo.MetricRepository;
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
        return entityManager.find(Metric.class, (long) id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Metric> getAllMetrics() {
        String hql = "FROM Metric as m ORDER BY m.id";
        return (List<Metric>) entityManager.createQuery(hql).getResultList();
    }

    @Override
    public void addMetric(Metric metric) {
        entityManager.persist(metric);
    }

    @Override
    public void addMetrics(List<Metric> metrics) {
        metricRepository.save(metrics);
    }

    @Override
    public void deleteMetric(long id) {
        entityManager.remove(getMetricById(id));
    }

    @Override
    public boolean metricExists(String beanName, String beanType) {
        String hql = "FROM Metric as m WHERE m.beanName = ? and m.beanType = ?";
        int count = entityManager.createQuery(hql).setParameter(1, beanName)
                .setParameter(2, beanType).getResultList().size();
        return count > 0 ? true : false;
    }

    @Override
    public void updateMetric(long id, Metric metric) {
        Metric existingMetric = metricRepository.findOne(id);
        if (existingMetric == null) {
            throw new IllegalArgumentException("Can't find metric with ID " + id);
        }
        existingMetric.setBeanName(metric.getBeanName());
        existingMetric.setBeanType(metric.getBeanType());
        existingMetric.setDuration(metric.getDuration());
        existingMetric.setInitialisationStartTimeMillis(metric.getInitialisationStartTimeMillis());

        metricRepository.save(existingMetric);
    }

    @Override
    @Transactional
    public List<ProcessedMetric> getMetricsProcessed() {

        String queryStr = "SELECT NEW com.concretepage.model.ProcessedMetric(beanName, beanType, AVG(duration), MIN(duration), MAX(duration)) FROM Metric  GROUP BY beanName, beanType ORDER BY initialisationStartTimeMillis DESC";

        TypedQuery<ProcessedMetric> query = entityManager.createQuery(queryStr, ProcessedMetric.class);

        return query.getResultList();
    }
}
