/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.pojo.vehicle_modal;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.BelongsToParents;
import org.javalite.activejdbc.annotations.Table;

/**
 *
 * @author ETS-4
 */
@Table("vehicle_and_model_mapping")
@BelongsToParents({
    @BelongsTo(foreignKeyName = "vehicleversion_id", parent = VehicleVersion.class),
    @BelongsTo(foreignKeyName = "vehicle_id", parent = Vehicle.class),
    @BelongsTo(foreignKeyName = "model_id", parent = VehicleModel.class)
})
public class VehicleModelMapping extends Model {

    private String button_type;
    private String operation_status;

    public int getVMMId() {
        return getInteger("id");
    }

    public int getVehicleversion_id() {
        return getInteger("vehicleversion_id");
    }

    public int getVehicle_id() {
        return getInteger("vehicle_id");
    }

    public int getModel_id() {
        return getInteger("model_id");
    }

    public String getButton_type() {
        return button_type;
    }

    public void setButton_type(String button_type) {
        this.button_type = button_type;
    }

    public String getOperation_status() {
        return operation_status;
    }

    public void setOperation_status(String operation_status) {
        this.operation_status = operation_status;
    }

    public VehicleModelMapping() {
    }

    public VehicleModelMapping(int vehicleversion_id, int vehicle_id, int model_id, String button_type, String operation_status) {
        this.operation_status = operation_status;
        set("vehicleversion_id", vehicleversion_id);
        set("vehicle_id", vehicle_id);
        set("model_id", model_id);
        this.button_type = button_type;
    }

    public VehicleModelMapping(int vehicleversion_id, int vehicle_id) {
        set("vehicleversion_id", vehicleversion_id);
        set("vehicle_id", vehicle_id);
    }
}
