package com.locus.assignment.entity;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public class BaseEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;
    @Column(name = "CreatedOn")
    private Date CreatedOn;
    @Column(name = "ModifiedOn")
    private Date modifiedOn;
    @Column(name = "CreatedBy")
    private Integer createdBy;
    @Column(name = "Active", columnDefinition = "boolean default true")
    private boolean active;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreatedOn() {
        return CreatedOn;
    }

    public void setCreatedOn(Date createdOn) {
        CreatedOn = createdOn;
    }

    public Date getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(Date modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setDefaultOnCreate(){
        this.active = true;
        this.setCreatedOn(new Date());
    }

    public void setDefaultOnUpdate(){
        this.setModifiedOn(new Date());
    }
}
