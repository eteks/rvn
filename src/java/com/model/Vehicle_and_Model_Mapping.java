/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

/**
 *
 * @author ets-2
 */
public class Vehicle_and_Model_Mapping {
    private int id;
    private int vehicleversion_id;
    private int vehicle_id;
    private int model_id;
    private boolean flag;
    private String button_type;

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
    public boolean getFlag() {
            return flag;
    }
    public void setFlag(boolean flag) {
            this.flag = flag;
    }

    public Vehicle_and_Model_Mapping(){}
    public Vehicle_and_Model_Mapping(int vehicleversion_id, int vehicle_id, int model_id, String button_type)
    {
            this.vehicleversion_id=vehicleversion_id;
            this.vehicle_id=vehicle_id;
            this.model_id=model_id;
//            this.flag=flag;
            this.button_type=button_type;
    }
}
