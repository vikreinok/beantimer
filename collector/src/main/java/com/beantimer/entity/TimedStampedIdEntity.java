package com.beantimer.entity;

import javax.persistence.Basic;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import java.util.Date;

/**
 *
 */
@MappedSuperclass
public class TimedStampedIdEntity extends IdEntity {

    @Basic(optional = false)
    private Date created;

    @PrePersist
    protected void persist() {
        if (created == null) {
            created = new Date();
        }
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

}
