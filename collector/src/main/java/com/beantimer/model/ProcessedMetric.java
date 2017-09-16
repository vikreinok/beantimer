package com.beantimer.model;

/**
 *
 */
public class ProcessedMetric {

    private String beanName;
    private String beanType;
    private Double durationAvg;
    private Long durationMin;
    private Long durationMax;


    public ProcessedMetric(String beanName, String beanType, Double durationAvg, Long durationMin, Long durationMax) {
        this.beanName = beanName;
        this.beanType = beanType;
        this.durationAvg = durationAvg;
        this.durationMin = durationMin;
        this.durationMax = durationMax;
    }

    public String getBeanName() {
        return beanName;
    }

    public String getBeanType() {
        return beanType;
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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("ProcessedMetric{");
        sb.append("beanName='").append(beanName).append('\'');
        sb.append(", beanType='").append(beanType).append('\'');
        sb.append(", durationAvg=").append(durationAvg);
        sb.append(", durationMin=").append(durationMin);
        sb.append(", durationMax=").append(durationMax);
        sb.append('}');
        return sb.toString();
    }
}
