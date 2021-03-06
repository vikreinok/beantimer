package com.beantimer.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(
        indexes = {
                @Index(name = "INDEX_RuleRecord_beanName", columnList = "beanName"),
                @Index(name = "INDEX_RuleRecord_beanType", columnList = "beanType"),
                @Index(name = "INDEX_RuleRecord_beanScope", columnList = "beanScope"),
                @Index(name = "INDEX_RuleRecord_primaryBean", columnList = "primaryBean"),
        },
        name = "metric")
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
    @Column(name = "beanScope")
    private String beanScope;
    @Column(name = "primaryBean")
    private Boolean primaryBean;
    @ManyToOne
    private User user;


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

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Long getInitialisationStartTimeMillis() {
        return initialisationStartTimeMillis;
    }

    public void setInitialisationStartTimeMillis(Long initialisationStartTimeMillis) {
        this.initialisationStartTimeMillis = initialisationStartTimeMillis;
    }

    public String getBeanScope() {
        return beanScope;
    }

    public void setBeanScope(String beanScope) {
        this.beanScope = beanScope;
    }

    public Boolean isPrimaryBean() {
        return primaryBean;
    }

    public void setPrimaryBean(Boolean primaryBean) {
        this.primaryBean = primaryBean;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}