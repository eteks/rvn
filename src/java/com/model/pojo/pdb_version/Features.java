/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.pojo.pdb_version;

import com.controller.common.CookieRead;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 *
 * @author ETS-4
 */
@Entity
public class Features {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "f_generator")
    @SequenceGenerator(name = "f_generator", sequenceName = "f_seq")
    @Column(updatable = false, nullable = false)
    private int id;
    private String feature_name;
    private String feature_description;
    private int created_or_updated_by;
    private boolean status;
    private String created_date;
    @Column
    @UpdateTimestamp
    private Timestamp modified_date;
    @OneToMany(mappedBy = "features")
    private List<DomainFeaturesMapping> dfm = new ArrayList<>();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFeature_name() {
        return feature_name;
    }

    public void setFeature_name(String feature_name) {
        this.feature_name = feature_name;
    }

    public String getFeature_description() {
        return feature_description;
    }

    public void setFeature_description(String feature_description) {
        this.feature_description = feature_description;
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

    public List<DomainFeaturesMapping> getDfm() {
        return dfm;
    }

    public void setDfm(List<DomainFeaturesMapping> dfm) {
        this.dfm = dfm;
    }

    public Features(String feature_name, String feature_description, String created_date) {
        this.feature_name = feature_name;
        this.feature_description = feature_description;
        this.created_date = created_date;
        this.created_or_updated_by = CookieRead.getUserIdFromSession();
    }

}
