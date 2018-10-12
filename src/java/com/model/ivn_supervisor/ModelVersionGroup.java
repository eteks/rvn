/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.ivn_supervisor;

import com.model.pdb_owner.*;

/**
 *
 * @author ets-2
 */
public class ModelVersionGroup {
    private int id;
    private int modelversion_id;
    private int vehicleversion_id;
    private int vehicle_id;
    private int acbversion_id;
    private int vehicle_and_model_mapping_id;
    private int ecu_id;
    private int variant_id;
    private String button_type;
    private String operation_status;

    public int getId() {
            return id;
    }
    public void setId(int id) {
            this.id = id;
    }
    public int getModelversion_id() {
            return modelversion_id;
    }
    public void setModelversion_id(int modelversion_id) {
            this.modelversion_id = modelversion_id;
    }
    public int getVehicleversion_id() {
            return vehicleversion_id;
    }
    public void setVehicleversion_id(int vehicleversion_id) {
            this.vehicleversion_id = vehicleversion_id;
    }
    public int getVehicle_id() {
            return vehicle_id;
    }
    public void setVehicle_id(int vehicle_id) {
            this.vehicle_id = vehicle_id;
    }
    public int getACBversion_id() {
            return acbversion_id;
    }
    public void setACBversion_id(int acbversion_id) {
            this.acbversion_id = acbversion_id;
    }
    public int getVehicle_and_model_mapping_id() {
            return vehicle_and_model_mapping_id;
    }
    public void setVehicle_and_model_mapping_id(int vehicle_and_model_mapping_id) {
            this.vehicle_and_model_mapping_id = vehicle_and_model_mapping_id;
    }
    public int getEcu_id() {
            return ecu_id;
    }
    public void setEcu_id(int ecu_id) {
            this.ecu_id = ecu_id;
    }
    public int getVariant_id() {
            return variant_id;
    }
    public void setVariant_id(int variant_id) {
            this.variant_id = variant_id;
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

    public ModelVersionGroup(){}
    public ModelVersionGroup(int modelversion_id,int vehicleversion_id,int vehicle_id,int acbversion_id, int vehicle_and_model_mapping_id, int ecu_id,int variant_id, String button_type, String operation_status)
    {
            this.operation_status = operation_status;
            this.modelversion_id=modelversion_id;
            this.vehicleversion_id=vehicleversion_id;
            this.vehicle_id=vehicle_id;
            this.acbversion_id=acbversion_id;
            this.vehicle_and_model_mapping_id=vehicle_and_model_mapping_id;
            this.ecu_id=ecu_id;
            this.variant_id=variant_id;
            this.button_type=button_type;
    }
}
