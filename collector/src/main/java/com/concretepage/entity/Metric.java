package com.concretepage.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "metric")
public class Metric extends TimedStampedIdEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    @Column(name = "beanName")
    private String beanName;
    @Column(name = "beanType")
    private String beanType;
    @Column(name = "duration")
    private Long duration;
    @Column(name = "initialisationStartTimeMillis")
    private Long initialisationStartTimeMillis;


    public Metric() {
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanType() {
        return beanType;
    }

    public void setBeanType(String beanType) {
        this.beanType = beanType;
    }

    public Long getDuration() {
        return duration;
    }

    public void setDuration(Long duration) {
        this.duration = duration;
    }

    public Long getInitialisationStartTimeMillis() {
        return initialisationStartTimeMillis;
    }

    public void setInitialisationStartTimeMillis(Long initialisationStartTimeMillis) {
        this.initialisationStartTimeMillis = initialisationStartTimeMillis;
    }
}