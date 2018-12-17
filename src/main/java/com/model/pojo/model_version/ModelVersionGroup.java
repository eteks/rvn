/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.pojo.model_version;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

/**
 *
 * @author ETS-4
 */
@Table("modelversion_group")
public class ModelVersionGroup extends Model {

    private String button_type;
    private String operation_status;

    public int getMVGId() {
        return getInteger("id");
    }

    public int getModelversion_id() {
        return getInteger("modelversion_id");
    }

    public int getVehicleversion_id() {
        return getInteger("vehicleversion_id");
    }

    public int getVehicle_id() {
        return getInteger("vehicle_id");
    }

    public int getACBversion_id() {
        return getInteger("acbversion_id");
    }

    public int getVehicle_and_model_mapping_id() {
        return getInteger("vehicle_and_model_mapping_id");
    }

    public int getEcu_id() {
        return getInteger("ecu_id");
    }

    public int getVariant_id() {
        return getInteger("variant_id");
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

    public ModelVersionGroup() {
    }

    public ModelVersionGroup(int modelversion_id, int vehicleversion_id, int vehicle_id, int acbversion_id, int vehicle_and_model_mapping_id, int ecu_id, int variant_id, String button_type, String operation_status) {
        this.operation_status = operation_status;
        set("modelversion_id", modelversion_id);
        set("vehicleversion_id", vehicleversion_id);
        set("vehicle_id", vehicle_id);
        set("acbversion_id", acbversion_id);
        set("vehicle_and_model_mapping_id", vehicle_and_model_mapping_id);
        set("ecu_id", ecu_id);
        set("variant_id", variant_id);
        this.button_type = button_type;
    }
}
