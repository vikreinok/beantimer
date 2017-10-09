package com.beantimer.model;

/**
 *
 */
public class ProcessedMetric {


    public final static String TOTAL_BEAN_NAME = "Total";
    public final static String TOTAL_BEAN_TYPE = "";
    public final static String TOTAL_BEAN_SCOPE = "singleton";

    final private String beanName;
    final private String beanType;
    final private String beanScope;
    final private Double durationAvg;
    final private Long durationMin;
    final private Long durationMax;
    final private Long count;

    public ProcessedMetric(String beanName, String beanType, String beanScope, Double durationAvg, Long durationMin, Long durationMax, Long count) {
        this.beanName = beanName;
        this.beanType = beanType;
        this.beanScope = beanScope;
        this.durationAvg = durationAvg;
        this.durationMin = durationMin;
        this.durationMax = durationMax;
        this.count = count;
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

    public Double getDurationAvg() {
        return durationAvg;
    }

    public Long getDurationMin() {
        return durationMin;
    }

    public Long getDurationMax() {
        return durationMax;
    }

    public Long getCount() {
        return count;
    }

    @Override
    public String toString() {
        String sb = "ProcessedMetric{" + "beanName='" + beanName + '\'' +
                ", beanType='" + beanType + '\'' +
                ", beanScope='" + beanScope + '\'' +
                ", durationAvg=" + durationAvg +
                ", durationMin=" + durationMin +
                ", durationMax=" + durationMax +
                ", count=" + count +
                '}';
        return sb;
    }
}
