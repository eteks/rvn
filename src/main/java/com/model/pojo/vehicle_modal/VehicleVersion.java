/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.pojo.vehicle_modal;

import com.controller.common.CookieRead;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

/**
 *
 * @author ETS-4
 */
@Table("vehicleversion")
public class VehicleVersion extends Model {
    
    private String operation_status;
    
    public int getVVId() {
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
    
    public VehicleVersion() {
    }
    
    public VehicleVersion(int id) {
    	set("id", id);
    }

    //Function for record updation
    public VehicleVersion(int id, boolean status, boolean flag, String operation_status) {
        set("id", id);
        set("status", status);
        set("created_or_updated_by", CookieRead.getUserIdFromSession());
        this.operation_status = operation_status;
        set("flag", flag);
    }

    //Function for record creation
    public VehicleVersion(float versionname, boolean status, boolean flag, String operation_status) {
        set("versionname", versionname);
        set("status", status);
        set("created_or_updated_by", CookieRead.getUserIdFromSession());
        this.operation_status = operation_status;
        set("flag", flag);
    }
}
