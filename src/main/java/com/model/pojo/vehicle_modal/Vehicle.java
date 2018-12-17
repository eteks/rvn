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
@Table("vehicle")
public class Vehicle extends Model {
    
    public int getVId() {
        return getInteger("id");
    }
    
    public String getVehiclename() {
        return getString("vehiclename");
    }
    
    public int getCreated_or_updated_by() {
        return getInteger("created_or_updated_by");
    }
    
    public Vehicle() {
    }
    
    public Vehicle(String vehiclename) {
        set("vehiclename", vehiclename);
        set("created_or_updated_by", CookieRead.getUserIdFromSession());
    }
}
