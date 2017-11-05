package com.beantimer.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User extends TimedStampedIdEntity implements Serializable {
    private static final long serialVersionUID = 1L;


    @Column(name = "username")
    private String username;

    @OneToMany(mappedBy = "user")
    private List<Metric> metrics = new ArrayList<>();

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public List<Metric> getMetrics() {
        return metrics;
    }

}