/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.pdbowner;

/**
 *
 * @author ets-2
 */
public class PDBVersionGroup {
    private int id;
    private int pdbversion_id;
    private int vehicle_and_model_mapping_id;
    private int domain_and_features_mapping_id;
    private String available_status;
    private boolean flag;
    private String button_type;
    private String operation_status;

    public int getId() {
            return id;
    }
    public void setId(int id) {
            this.id = id;
    }
    public int getPDBversion_id() {
            return pdbversion_id;
    }
    public void setPDBversion_id(int pdbversion_id) {
            this.pdbversion_id = pdbversion_id;
    }
    public int getVehicle_and_model_mapping_id() {
            return vehicle_and_model_mapping_id;
    }
    public void setVehicle_and_model_mapping_id(int vehicle_and_model_mapping_id) {
            this.vehicle_and_model_mapping_id = vehicle_and_model_mapping_id;
    }
    public int getDomain_and_features_mapping_id() {
            return domain_and_features_mapping_id;
    }
    public void setDomain_and_features_mapping_id(int domain_and_features_mapping_id) {
            this.domain_and_features_mapping_id = domain_and_features_mapping_id;
    }
    public String getAvailable_status() {
            return available_status;
    }
    public void setAvailable_status(String available_status) {
            this.available_status = available_status;
    }
    public String getButton_type() {
            return button_type;
    }
    public void setButton_type(String button_type) {
            this.button_type = button_type;
    }
    public boolean getFlag() {
            return flag;
    }
    public void setFlag(boolean flag) {
            this.flag = flag;
    }
    public String getOperation_status() {
            return operation_status;
    }
    public void setOperation_status(String operation_status) {
            this.operation_status = operation_status;
    }

    public PDBVersionGroup(){}
    public PDBVersionGroup(int pdbversion_id, int vehicle_and_model_mapping_id, int domain_and_features_mapping_id, String available_status, String button_type, String operation_status)
    {
            this.operation_status = operation_status;
            this.pdbversion_id=pdbversion_id;
            this.vehicle_and_model_mapping_id=vehicle_and_model_mapping_id;
            this.domain_and_features_mapping_id=domain_and_features_mapping_id;
            this.available_status=available_status;
//            this.flag=flag;
            this.button_type=button_type;
    }
}
