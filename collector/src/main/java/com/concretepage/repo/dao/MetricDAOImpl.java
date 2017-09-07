package com.concretepage.repo.dao;

import com.concretepage.entity.Metric;
import com.concretepage.repo.MetricRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Transactional
@Repository
public class MetricDAOImpl implements MetricDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private MetricRepository metricRepository;

    @Override
    public Metric getMetricById(int id) {
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
    public void deleteMetric(int id) {
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
}
