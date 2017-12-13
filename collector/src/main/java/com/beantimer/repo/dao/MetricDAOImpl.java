package com.beantimer.repo.dao;

import com.beantimer.controller.validator.OrderByValidator;
import com.beantimer.entity.Metric;
import com.beantimer.model.GanttMetric;
import com.beantimer.model.ProcessedMetric;
import com.beantimer.model.mapper.GanttMetricRowMapper;
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
                "WHERE u.username = '%s' OR u.username = 'null' OR u.username = '' OR u IS NULL "+
                "GROUP BY m.beanName, m.beanType, m.beanScope, m.primaryBean " +
                "%s", username, orderByClause);

        Query query = entityManager.createQuery(queryStr);

        return new ProcessedMetricRowMapper().map(query.getResultList());
    }

    @Override
    public List<GanttMetric> getMetricsGantt(String username) {

        String queryStr = String.format("SELECT " +
                "m.beanName, " +
                "m.beanType, " +
                "m.beanScope, " +
                "m.primaryBean, " +
                "(SELECT AVG(mx.duration) FROM Metric AS mx WHERE mx.beanName = m.beanName AND mx.beanType = m.beanType AND m.beanScope = mx.beanScope AND m.primaryBean = mx.primaryBean GROUP BY mx.beanName, mx.beanType, mx.beanScope, mx.primaryBean LIMIT 1) AS durationAvg, " +
                "m.duration, " +
                "m.initialisationStartTimeMillis " +
                "FROM Metric AS m " +
                "LEFT JOIN Metric AS m2 " +
                "ON (m.beanName = m2.beanName AND m.beanType = m2.beanType AND m.beanScope = m2.beanScope AND m.primaryBean = m2.primaryBean AND m.initialisationStartTimeMillis < m2.initialisationStartTimeMillis) " +
//                "LEFT JOIN User " +
                "WHERE m.id IS NULL " +
//                "  AND u.username = '%s' OR u.username = 'null' OR u.username = '' OR u IS NULL " +
                "ORDER BY m.id, m.initialisationStartTimeMillis ASC " +
                " ", username);

        Query query = entityManager.createNativeQuery(queryStr);

        return new GanttMetricRowMapper().map(query.getResultList());
    }

    private String validateAndDecideOnUseOfSortParams(String sort, String dir) {
        String orderByClause = "";

        if (OrderByValidator.checkIfClazzContainsColumn(ProcessedMetric.class, sort) && OrderByValidator.validateDirection(dir)) {
            orderByClause = String.format("ORDER BY m.%s %s", sort, dir);
        }

        return orderByClause;
    }

}
