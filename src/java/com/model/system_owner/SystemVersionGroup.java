/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.system_owner;

import com.model.ivn_supervisor.*;
import com.model.pdb_owner.*;

/**
 *
 * @author ets-2
 */
public class SystemVersionGroup {
    private int id;
    private int systemversion_id;
    private int vehicleversion_id;
    private int vehicle_id;
    private int acbversion_id;
    private int domain_and_features_mapping_id;
    private int ecu_id;
    private int variant_id;
    private String available_status;
    private String button_type;
    private String operation_status;

    public int getId() {
            return id;
    }
    public void setId(int id) {
            this.id = id;
    }
    public int getSystemversion_id() {
            return systemversion_id;
    }
    public void setSystemversion_id(int systemversion_id) {
            this.systemversion_id = systemversion_id;
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
    public int getDomain_and_features_mapping_id() {
            return domain_and_features_mapping_id;
    }
    public void setDomain_and_features_mapping_id(int domain_and_features_mapping_id) {
            this.domain_and_features_mapping_id = domain_and_features_mapping_id;
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
    public String getOperation_status() {
            return operation_status;
    }
    public void setOperation_status(String operation_status) {
            this.operation_status = operation_status;
    }

    public SystemVersionGroup(){}
    public SystemVersionGroup(int systemversion_id,int vehicleversion_id,int vehicle_id,int acbversion_id, int domain_and_features_mapping_id, int ecu_id, int variant_id, String available_status, String button_type, String operation_status)
    {
            this.operation_status = operation_status;
            this.systemversion_id=systemversion_id;
            this.vehicleversion_id=vehicleversion_id;
            this.vehicle_id=vehicle_id;
            this.acbversion_id=acbversion_id;
            this.domain_and_features_mapping_id=domain_and_features_mapping_id;
            this.ecu_id=ecu_id;
            this.variant_id=variant_id;
            this.available_status=available_status;
            this.button_type=button_type;
    }
}
