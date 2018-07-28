/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.ivn_supervisor;

/**
 *
 * @author ets-2
 */
public class Vehicle_and_Model_Mapping {
    private int id;
    private int vehicleversion_id;
    private int vehicle_id;
    private int model_id;
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
    public int getModel_id() {
            return model_id;
    }
    public void setModel_id(int model_id) {
            this.model_id = model_id;
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

    public Vehicle_and_Model_Mapping(){}
    public Vehicle_and_Model_Mapping(int vehicleversion_id, int vehicle_id, int model_id, String button_type, String operation_status)
    {
            this.operation_status = operation_status;
            this.vehicleversion_id=vehicleversion_id;
            this.vehicle_id=vehicle_id;
            this.model_id=model_id;
            this.button_type=button_type;
    }
}
