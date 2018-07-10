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
public class Features {
    private int id;
    private String feature_name;
    private String feature_description;
    private String created_date;
    private int created_or_updated_by;
    public int getId() {
            return id;
    }
    public void setId(int id) {
            this.id = id;
    }
    public String getFeaturename() {
            return feature_name;
    }
    public void setFeaturename(String domain_name) {
            this.feature_name = feature_name;
    }
    public String getFeatureDescription() {
            return feature_description;
    }
    public void setgetFeatureDescription(String domain_name) {
            this.feature_description = feature_description;
    }
    public String getCreated_date() {
            return created_date;
    }
    public void setCreated_date(String created_date) {
            this.created_date = created_date;
    }
    public int getCreated_or_updated_by() {
            return created_or_updated_by;
    }
    public void setCreated_or_updated_by(int created_or_updated_by) {
            this.created_or_updated_by = created_or_updated_by;
    }

    public Features(){}
    public Features(String feature_name, String feature_description, String created_date, int created_or_updated_by)
    {
            this.feature_name=feature_name;
            this.feature_description = feature_description;
            this.created_date=created_date;
            this.created_or_updated_by=created_or_updated_by;
    }
}
