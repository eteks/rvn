/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.pojo.model_version;

import com.controller.common.CookieRead;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

/**
 *
 * @author ETS-4
 */
@Table("modelversion")
public class ModelVersion extends Model {

    private String operation_status;

    public int getMVId() {
        return getInteger("id");
    }

    public float getVersionname() {
        return getFloat("versionname");
    }

    public boolean getStatus() {
        return getBoolean("status");
    }

    public int getCreated_or_updated_by() {
        return getInteger("created_or_updated_by");
    }

    public boolean getFlag() {
        return getBoolean("flag");
    }

    public String getOperation_status() {
        return operation_status;
    }

    public void setOperation_status(String operation_status) {
        this.operation_status = operation_status;
    }

    public ModelVersion() {
    }

    public ModelVersion(int id) {
        set("id", id);
    }

    //Function for record updation
    public ModelVersion(int id, boolean status, boolean flag, String operation_status) {
        set("id", id);
        set("status", status);
        set("created_or_updated_by", CookieRead.getUserIdFromSession());
        this.operation_status = operation_status;
        set("flag", flag);
    }

    //Function for record creation
    public ModelVersion(float versionname, boolean status, boolean flag, String operation_status) {
        set("model_versionname", versionname);
        set("status", status);
        set("created_or_updated_by", CookieRead.getUserIdFromSession());
        this.operation_status = operation_status;
        set("flag", flag);
    }
}
