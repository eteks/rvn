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
public class VehicleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "vm_generator")
    @SequenceGenerator(name = "vm_generator", sequenceName = "vm_seq")
    @Column(updatable = false, nullable = false)
    private int id;
    private String modelname;
    private int created_or_updated_by;
    private boolean status;
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

    public String getModelname() {
        return modelname;
    }

    public void setModelname(String modelname) {
        this.modelname = modelname;
    }

    public int getCreated_or_updated_by() {
        return created_or_updated_by;
    }

    public void setCreated_or_updated_by(int created_or_updated_by) {
        this.created_or_updated_by = created_or_updated_by;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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
