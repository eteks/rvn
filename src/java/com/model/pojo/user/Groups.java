/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.pojo.user;

import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author ETS-4
 */
@Entity
public class Groups {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "group_generator")
    @SequenceGenerator(name = "group_generator", sequenceName = "group_seq")
    @Column(updatable = false, nullable = false)
    private int id;
    private String group_name;
    private boolean status;
    private String route_pages;
    private boolean is_superadmin;
    private Date modified_date;
    private Date created_date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getRoute_pages() {
        return route_pages;
    }

    public void setRoute_pages(String route_pages) {
        this.route_pages = route_pages;
    }

    public boolean isIs_superadmin() {
        return is_superadmin;
    }

    public void setIs_superadmin(boolean is_superadmin) {
        this.is_superadmin = is_superadmin;
    }

    public Date getModified_date() {
        return modified_date;
    }

    public void setModified_date(Date modified_date) {
        this.modified_date = modified_date;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }
}
