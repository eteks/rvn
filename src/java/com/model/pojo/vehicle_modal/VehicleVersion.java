/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.pojo.vehicle_modal;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 *
 * @author ETS-4
 */
@Entity
public class VehicleVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "vv_generator")
    @SequenceGenerator(name = "vv_generator", sequenceName = "vv_seq")
    @Column(updatable = false, nullable = false)
    private int id;
    private float versionname;
    private boolean status;
    private boolean flag;
    private int created_or_updated_by;
    private String created_date;
    @Column
    @UpdateTimestamp
    private Timestamp modified_date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getVersionname() {
        return versionname;
    }

    public void setVersionname(float versionname) {
        this.versionname = versionname;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getCreated_or_updated_by() {
        return created_or_updated_by;
    }

    public void setCreated_or_updated_by(int created_or_updated_by) {
        this.created_or_updated_by = created_or_updated_by;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public Timestamp getModified_date() {
        return modified_date;
    }

    public void setModified_date(Timestamp modified_date) {
        this.modified_date = modified_date;
    }

}
