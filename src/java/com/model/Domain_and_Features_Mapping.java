/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

/**
 *
 * @author ets-2
 */
public class Domain_and_Features_Mapping {
    private int id;
    private int domain_id;
    private int feature_id;
    private String created_date;
    public int getId() {
            return id;
    }
    public void setId(int id) {
            this.id = id;
    }
    public int getDomainId() {
            return domain_id;
    }
    public void setDomainId(int domain_id) {
            this.id = domain_id;
    }
    public int getFeatureId() {
            return feature_id;
    }
    public void setFeatureId(int feature_id) {
            this.feature_id = feature_id;
    }
    public String getCreated_date() {
            return created_date;
    }
    public void setCreated_date(String created_date) {
            this.created_date = created_date;
    }

    public Domain_and_Features_Mapping(){}
    public Domain_and_Features_Mapping(int domain_id, int feature_id, String created_date)
    {
            this.domain_id=domain_id;
            this.feature_id = feature_id;
            this.created_date=created_date;
    }
}
