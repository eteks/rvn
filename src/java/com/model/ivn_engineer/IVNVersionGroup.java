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
public class IVNVersionGroup {
    private int id;
    private int ivnversion_id;
    private int vehicle_and_model_mapping_id;
    private String canmodel_group;
    private String linmodel_group;
    private String hardwaremodel_group;
    private String signal_group;
    private String ecu_group;
    private String button_type;
    private String operation_status;

//    public Vehicle_and_Model_Mapping(String string, String format, int i) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    public int getId() {
            return id;
    }
    public void setId(int id) {
            this.id = id;
    }
    public int getIVNversion_id() {
            return ivnversion_id;
    }
    public void setIVNversion_id(int ivnversion_id) {
            this.ivnversion_id = ivnversion_id;
    }
    public int getVehicle_and_model_mapping_id() {
            return vehicle_and_model_mapping_id;
    }
    public void setVehicle_and_model_mapping_id(int vehicle_and_model_mapping_id) {
            this.vehicle_and_model_mapping_id = vehicle_and_model_mapping_id;
    }
    public String getCanmodel_group() {
            return canmodel_group;
    }
    public void setCanmodel_group(String canmodel_group) {
            this.canmodel_group = canmodel_group;
    }
    public String getLinmodel_group() {
            return linmodel_group;
    }
    public void setLinmodel_group(String linmodel_group) {
            this.linmodel_group = linmodel_group;
    }
    public String getHardwaremodel_group() {
            return hardwaremodel_group;
    }
    public void setHardwaremodel_group(String hardwaremodel_group) {
            this.hardwaremodel_group = hardwaremodel_group;
    }
    public String getSignal_group() {
            return signal_group;
    }
    public void setSignal_group(String signal_group) {
            this.signal_group = signal_group;
    }
    public String getEcu_group() {
            return ecu_group;
    }
    public void setEcu_group(String ecu_group) {
            this.ecu_group = ecu_group;
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
    
    public IVNVersionGroup(){}
    public IVNVersionGroup(int ivnversion_id, String canmodel_group, String linmodel_group, String hardwaremodel_group, String signal_group, String ecu_group, String button_type, String operation_status)
    {
            this.ivnversion_id=ivnversion_id;
            this.canmodel_group=canmodel_group;
            this.linmodel_group=linmodel_group;
            this.hardwaremodel_group=hardwaremodel_group;
            this.signal_group=signal_group;
            this.ecu_group=ecu_group;
            this.button_type=button_type;
            this.operation_status = operation_status;
    }
}
