package com.beantimer.model;

/**
 *
 */
public class ProcessedMetric {


    public final static String TOTAL_BEAN_NAME = "Total";
    public final static String TOTAL_BEAN_TYPE = "";
    public final static String TOTAL_BEAN_SCOPE = "";
    public final static boolean TOTAL_PRIMARY = false;

    final private String beanName;
    final private String beanType;
    final private String beanScope;
    final private boolean primaryBean;
    final private Double durationAvg;
    final private Long durationMin;
    final private Long durationMax;
    final private Long count;

    public ProcessedMetric(String beanName, String beanType, String beanScope, boolean primaryBean, Double durationAvg, Long durationMin, Long durationMax, Long count) {
        this.beanName = beanName;
        this.beanType = beanType;
        this.beanScope = beanScope;
        this.primaryBean = primaryBean;
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

    public boolean isPrimaryBean() {
        return primaryBean;
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
        sb.append(", beanScope='").append(beanScope).append('\'');
        sb.append(", primaryBean=").append(primaryBean);
        sb.append(", durationAvg=").append(durationAvg);
        sb.append(", durationMin=").append(durationMin);
        sb.append(", durationMax=").append(durationMax);
        sb.append(", count=").append(count);
        sb.append('}');
        return sb.toString();
    }
}
