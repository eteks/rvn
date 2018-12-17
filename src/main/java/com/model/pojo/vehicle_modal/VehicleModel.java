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
@Table("vehiclemodel")
public class VehicleModel extends Model {

    public int getVMId() {
        return getInteger("id");
    }

    public String getModelname() {
        return getString("modelname");
    }

    public int getCreated_or_updated_by() {
        return getInteger("created_or_updated_by");
    }

    public VehicleModel() {
    }

    public VehicleModel(String modelname) {
        set("modelname", modelname);
        set("created_or_updated_by", CookieRead.getUserIdFromSession());
    }
}
