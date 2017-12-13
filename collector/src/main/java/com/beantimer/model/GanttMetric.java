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
    final private String dependency;


    public GanttMetric(String beanName, String beanType, String beanScope, boolean primaryBean, Double durationAvg, Long duration, Long initialisationStartTimeMillis, String dependency) {
        this.beanName = beanName;
        this.beanType = beanType;
        this.beanScope = beanScope;
        this.primaryBean = primaryBean;
        this.durationAvg = durationAvg;
        this.duration = duration;
        this.initialisationStartTimeMillis = initialisationStartTimeMillis;
        this.dependency = dependency;
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

    public String getDependency() {
        return dependency;
    }

    @Override
    public String toString() {
        return String.format("{\"beanName\":\"%s\",\"beanType\":\"%s\",\"beanScope\":\"%s\",\"durationAvg\":\"%s\",\"duration\":\"%s\",\"initialisationStartTimeMillis\":\"%s\",\"primaryBean\":%s,\"dependency\":%s}", getBeanName(), getBeanType(), getBeanScope(),  getDurationAvg(), getDuration(), getInitialisationStartTimeMillis(), isPrimaryBean(), wrapNoNull(getDependency()));
    }

    private String wrapNoNull(Object o) {
        if (o == null) {
            return null;
        } else {
            return "\"" + o.toString() +  "\"";
        }
    }
}
