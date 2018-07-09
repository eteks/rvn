/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.model.VehicleversionDB;
import com.opensymphony.xwork2.ActionSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ets-2
 */
public class Domain_and_Features extends ActionSupport{
    private Map<String, String> maps = new HashMap<String, String>();
    public String vehmod_map_result_obj;
    private List<Map<String, Object>> vehicleversion_result = new ArrayList<Map<String, Object>>();
    
    public String PDBAssignPage(){
        System.out.println("Entered");
        System.out.println("PDBAssignPage");
        try{
            vehicleversion_result = VehicleversionDB.LoadVehicleVersion();
            System.out.println("oject"+vehicleversion_result);
        }
        catch (Exception ex) { 
            System.out.println(ex.getMessage()); 
            maps.put("status", "Some error occurred !!"); 
        }
        return "success";
    }
    
    public Map<String, String> getMaps() {
            return maps;
    }

    public void setMaps(Map<String, String> maps) {
            this.maps = maps;
    }
    public String getVehmod_map_result_obj() {
            return vehmod_map_result_obj;
    }

    public void setVehmod_map_result_obj(String vehmod_map_result) {
            this.vehmod_map_result_obj = vehmod_map_result_obj;
    }
    public List<Map<String, Object>> getVehicleversion_result() {
            return vehicleversion_result;
    }

    public void setVehicleversion_result(List<Map<String, Object>> vehicleversion_result) {
            this.vehicleversion_result = vehicleversion_result;
    }
    
}
