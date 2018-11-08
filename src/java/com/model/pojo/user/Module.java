/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.pojo.user;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 *
 * @author ETS-4
 */
@Entity
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "module_generator")
    @SequenceGenerator(name = "module_generator", sequenceName = "module_seq")
    @Column(updatable = false, nullable = false)
    private int id;
    private String modulename;
    private String icon_code;
    private String route_pages;
    private boolean status;
    private LocalDateTime modified_date;
    private LocalDateTime created_date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModulename() {
        return modulename;
    }

    public void setModulename(String modulename) {
        this.modulename = modulename;
    }

    public String getIcon_code() {
        return icon_code;
    }

    public void setIcon_code(String icon_code) {
        this.icon_code = icon_code;
    }

    public String getRoute_pages() {
        return route_pages;
    }

    public void setRoute_pages(String route_pages) {
        this.route_pages = route_pages;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public LocalDateTime getModified_date() {
        return modified_date;
    }

    public void setModified_date(LocalDateTime modified_date) {
        this.modified_date = modified_date;
    }

    public LocalDateTime getCreated_date() {
        return created_date;
    }

    public void setCreated_date(LocalDateTime created_date) {
        this.created_date = created_date;
    }
    
    @PrePersist
    public void prePersist(){
        created_date = LocalDateTime.now();
    }
    
    @PreUpdate
    public void preUpdate(){
        modified_date = LocalDateTime.now();
    }
}
