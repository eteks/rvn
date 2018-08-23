/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.ivn_engineer;

/**
 *
 * @author ets-2
 */
public class IVNNetwork_VehicleModel {
    private int id;
    private int ivnversion_id;
    private int network_id;
    private int vehicle_and_model_mapping_id;
    private boolean available_status;
    private String network_type;
    private String button_type;
    private String operation_status;
        
    
    public int getId() {
            return id;
    }
    public void setId(int id) {
            this.id = id;
    }
    public int getIvnversion_id() {
            return ivnversion_id;
    }
    public void setIvnversion_id(int ivnversion_id) {
            this.ivnversion_id = ivnversion_id;
    }
    public int getNetwork_id() {
            return network_id;
    }
    public void setNetwork_id(int network_id) {
            this.network_id = network_id;
    }
    public int getVehicle_model_mapping_id() {
            return vehicle_and_model_mapping_id;
    }
    public void setVehicle_model_mapping_id(int vehicle_and_model_mapping_id) {
            this.vehicle_and_model_mapping_id = vehicle_and_model_mapping_id;
    }
    public Boolean getAvailable_status() {
            return available_status;
    }
    public void setAvailable_status(Boolean available_status) {
            this.available_status = available_status;
    }
    public String getNetwork_type() {
            return network_type;
    }
    public void setNetwork_type(String network_type) {
            this.network_type = network_type;
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

    public IVNNetwork_VehicleModel(){}
    public IVNNetwork_VehicleModel(int ivnversion_id, int network_id, int vehicle_and_model_mapping_id, boolean available_status, String network_type, String button_type, String operation_status)
    {
            this.ivnversion_id = ivnversion_id;
            this.network_id = network_id;
            this.vehicle_and_model_mapping_id=vehicle_and_model_mapping_id;
            this.available_status=available_status;
            this.network_type = network_type;
            this.button_type = button_type;
            this.operation_status = operation_status;
    }
}
