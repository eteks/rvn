/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.ivn_supervisor;

import com.controller.common.CookieRead;

/**
 *
 * @author ets-2
 */
public class VehicleModel {
    private int id;
    private String modelname;
    private String created_date;
    private int created_or_updated_by;
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

    public VehicleModel(){}
    public VehicleModel(String modelname, String created_date)
    {
            this.modelname=modelname;
            this.created_date=created_date;
            this.created_or_updated_by=CookieRead.getUserIdFromSession();
    }
}
