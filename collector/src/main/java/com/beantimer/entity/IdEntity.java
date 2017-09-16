package com.beantimer.entity;

import javax.persistence.*;

/**
 *
 */
@MappedSuperclass
public abstract class IdEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "ID")
    private Long id;

    public Long getId() {
        return id;
    }

    protected void setId(Long ID)
    {
        this.id = ID;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ":" + id;
    }
}
