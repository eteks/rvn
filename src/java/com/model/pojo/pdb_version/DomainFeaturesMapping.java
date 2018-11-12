/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.pojo.pdb_version;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 *
 * @author ETS-4
 */
@Entity
@Table(name = "domain_and_features_mapping")
public class DomainFeaturesMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "dfm_generator")
    @SequenceGenerator(name = "dfm_generator", sequenceName = "dfm_seq")
    @Column(updatable = false, nullable = false)
    private int id;
    @ManyToOne
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
    private Domain domain;
    @ManyToOne
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
    private Features features;
    private String created_date;
    @Transient
    private int domain_id;
    @Transient
    private int feature_id;

    public DomainFeaturesMapping() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public Features getFeatures() {
        return features;
    }

    public void setFeatures(Features features) {
        this.features = features;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public int getDomain_id() {
        return domain_id;
    }

    public void setDomain_id(int domain_id) {
        this.domain_id = domain_id;
    }

    public int getFeature_id() {
        return feature_id;
    }

    public void setFeature_id(int feature_id) {
        this.feature_id = feature_id;
    }

    public DomainFeaturesMapping(int domain_id, int feature_id, String created_date) {
        this.domain_id = domain_id;
        this.feature_id = feature_id;
        this.created_date = created_date;
    }

}
