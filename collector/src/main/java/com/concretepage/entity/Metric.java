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
    private Integer duration;


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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}