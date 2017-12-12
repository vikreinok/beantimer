package com.beantimer.model;

/**
 *
 */
public class GanttMetric {

    final private String beanName;
    final private String beanType;
    final private String beanScope;
    final private boolean primaryBean;
    final private Double durationAvg;
    final private Long duration;
    final private Long initialisationStartTimeMillis;


    public GanttMetric(String beanName, String beanType, String beanScope, boolean primaryBean, Double durationAvg, Long duration, Long initialisationStartTimeMillis) {
        this.beanName = beanName;
        this.beanType = beanType;
        this.beanScope = beanScope;
        this.primaryBean = primaryBean;
        this.durationAvg = durationAvg;
        this.duration = duration;
        this.initialisationStartTimeMillis = initialisationStartTimeMillis;
    }

    public String getBeanName() {
        return beanName;
    }

    public String getBeanType() {
        return beanType;
    }

    public String getBeanScope() {
        return beanScope;
    }

    public boolean isPrimaryBean() {
        return primaryBean;
    }

    public Double getDurationAvg() {
        return durationAvg;
    }

    public Long getDuration() {
        return duration;
    }

    public Long getInitialisationStartTimeMillis() {
        return initialisationStartTimeMillis;
    }

    @Override
    public String toString() {
        return String.format("{\"beanName\":\"%s\",\"beanType\":\"%s\",\"beanScope\":\"%s\",\"durationAvg\":\"%s\",\"duration\":\"%s\",\"initialisationStartTimeMillis\":\"%s\",\"primaryBean\":%s }", getBeanName(), getBeanType(), getBeanScope(),  getDurationAvg(), getDuration(), getInitialisationStartTimeMillis(), isPrimaryBean());
    }
}
