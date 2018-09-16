/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.acb_owner;

import com.model.ivn_engineer.*;

/**
 *
 * @author ets-2
 */
public class ACBVersionGroup {
    private int id;
    private int acbversion_id;
    private int ivnversion_id;
    private int pdbversion_id;
    private int vehicleversion_id;
    private int vehicle_id;
    private int ecu_id;
    private String inputsignal_group;
    private String outputsignal_group;
    private Boolean touchedstatus;
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
    public int getACBversion_id() {
            return acbversion_id;
    }
    public void setACBversion_id(int acbversion_id) {
            this.acbversion_id = acbversion_id;
    }
    public int getIVNversion_id() {
            return ivnversion_id;
    }
    public void setIVNversion_id(int ivnversion_id) {
            this.ivnversion_id = ivnversion_id;
    }
    public int getPDBversion_id() {
            return pdbversion_id;
    }
    public void setPDBversion_id(int pdbversion_id) {
            this.pdbversion_id = pdbversion_id;
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
    public int getEcu_id() {
            return ecu_id;
    }
    public void setEcu_id(int ecu_id) {
            this.ecu_id = ecu_id;
    }
    public String getInputsignal_group() {
            return inputsignal_group;
    }
    public void setInputsignal_group(String inputsignal_group) {
            this.inputsignal_group = inputsignal_group;
    }
    public String getOutputsignal_group() {
            return outputsignal_group;
    }
    public void setOutputsignal_group(String outputsignal_group) {
            this.outputsignal_group = outputsignal_group;
    }
    public Boolean getTouchedstatus() {
            return touchedstatus;
    }
    public void setTouchedstatus(Boolean touchedstatus) {
            this.touchedstatus = touchedstatus;
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
    
    public ACBVersionGroup(){}
    public ACBVersionGroup(int acbversion_id,int ivnversion_id,int pdbversion_id,int vehicleversion_id,int vehicle_id,int ecu_id, String inputsignal_group, String outputsignal_group,Boolean touchedstatus, String button_type, String operation_status)
    {
            this.acbversion_id=acbversion_id;
            this.ivnversion_id=ivnversion_id;
            this.pdbversion_id=pdbversion_id;
            this.vehicleversion_id=vehicleversion_id;
            this.vehicle_id=vehicle_id;
            this.ecu_id=ecu_id;
            this.inputsignal_group=inputsignal_group;
            this.outputsignal_group=outputsignal_group;
            this.touchedstatus = touchedstatus;
            this.button_type=button_type;
            this.operation_status = operation_status;
    }
}
