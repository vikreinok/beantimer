package com.beantimer.model;

/**
 *
 */
public class ProcessedMetric {


    public final static String TOTAL_BEAN_NAME = "Total";
    public final static String TOTAL_BEAN_TYPE = "";

    private String beanName;
    private String beanType;
    private Double durationAvg;
    private Long durationMin;
    private Long durationMax;
    private Long count;

    public ProcessedMetric(String beanName, String beanType, Double durationAvg, Long durationMin, Long durationMax, Long count) {
        this.beanName = beanName;
        this.beanType = beanType;
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
        final StringBuffer sb = new StringBuffer("ProcessedMetric{");
        sb.append("beanName='").append(beanName).append('\'');
        sb.append(", beanType='").append(beanType).append('\'');
        sb.append(", durationAvg=").append(durationAvg);
        sb.append(", durationMin=").append(durationMin);
        sb.append(", durationMax=").append(durationMax);
        sb.append(", count=").append(count);
        sb.append('}');
        return sb.toString();
    }
}
