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
    public void updateMetric(long id, Metric existingMetric, Metric metric) {
        existingMetric.setBeanName(metric.getBeanName());
        existingMetric.setBeanType(metric.getBeanType());
        existingMetric.setBeanScope(metric.getBeanScope());
        existingMetric.setPrimaryBean(metric.isPrimaryBean());
        existingMetric.setDuration(metric.getDuration());
        existingMetric.setInitialisationStartTimeMillis(metric.getInitialisationStartTimeMillis());

        metricRepository.save(existingMetric);
    }

    @Override
    public List<ProcessedMetric> getMetricsProcessed(String sort, String dir, String username) {

        String orderByClause = validateAndDecideOnUseOfSortParams(sort, dir);

        String queryStr = String.format("SELECT " +

                "m.beanName, " +
                "m.beanType, " +
                "m.beanScope, " +
                "m.primaryBean, " +
                "AVG(m.duration) AS durationAvg, " +
                "MIN(m.duration) AS durationMin, " +
                "MAX(m.duration) AS durationMax, " +
                "COUNT(m.beanName) AS count " +

                "FROM Metric m " +
                "LEFT JOIN m.user u " +
                "WHERE u.username = %s "+
                "GROUP BY m.beanName, m.beanType, m.beanScope, m.primaryBean " +
                "%s", username, orderByClause);

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

}
