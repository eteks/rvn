/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.model.Vehicle;
import com.model.VehicleModel;
import com.model.Vehicle_and_Model_Mapping;
import com.model.Vehicleversion;
import com.model.VehicleversionDB;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author ets-2
 */
public class Vehicle_and_Model {
//    public String execute() throws IOException {
//        return "success";
//    }
    public String CreateVehicleVersion() 
    { 
            System.out.println("createvehicleversion");
            JSONParser parser = new JSONParser();
            String jsondata = JSONConfigure.getAngularJSONFile();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
            LocalDateTime now = LocalDateTime.now();  
            try { 
                
                Object obj = parser.parse(jsondata);
                JSONObject json = (JSONObject) obj; 
                System.out.println("output_json"+json);
                
//                Insert Data in vehicle version table
                JSONObject vehicleversion_value = (JSONObject) json.get("vehicleversion");
                String name = (String) vehicleversion_value.get("vehicleversion"); 
                boolean status = (boolean) vehicleversion_value.get("status");
                Vehicleversion v = new Vehicleversion((float) 1.0, status,dtf.format(now),1);
                int result = VehicleversionDB.insertVehicleVersion(v);
                System.out.println("result_value"+result);
//                if(result!=0)
//                {
//                        maps.put("status", "Vehicle version data succesfully !!");
//
//                }
//                else
//                {
//                        maps.put("status", "Some error occurred !!");
//
//                }
                JSONArray vehicle_and_model_value = (JSONArray) json.get("vehicle_and_model");
                for (Object o : vehicle_and_model_value) {
                    JSONObject vehicleitem = (JSONObject) o;
                    String vehiclename = (String) vehicleitem.get("vehiclename");
                    JSONArray modelvalue = (JSONArray) vehicleitem.get("modelname");
                    //Insert Data in vehicle table
                    Vehicle veh = new Vehicle(vehiclename,dtf.format(now),1);
                    int veh_result = VehicleversionDB.insertVehicle(veh);
                    
                    //Insert Data in Model table
                    for (Object o1 : modelvalue) {
                       VehicleModel veh_mod = new VehicleModel((String) o1,dtf.format(now),1);
                       int vehmod_result = VehicleversionDB.insertVehicleModel(veh_mod);
                       //Insert Data in VehicleModel Mapping table
                       Vehicle_and_Model_Mapping veh_mod_map = new Vehicle_and_Model_Mapping(result,veh_result,vehmod_result,status);
                       int vehmod_map_result = VehicleversionDB.insertVehicleModelMapping(veh_mod_map);
                       System.out.println("vehmod_result"+vehmod_result);
                    }
                }      
                maps.put("status", "New Vehicle version created Successfully"); 
            }
            catch (Exception ex) { 
                System.out.println(ex.getMessage()); 
                maps.put("status", "Some error occurred !!"); 
            }
//            System.out.println("before success");
            return "success";
        }
    
        private Map<String, String> maps = new HashMap<String, String>();

        public Map<String, String> getMaps() {
                return maps;
        }

        public void setMaps(Map<String, String> maps) {
                this.maps = maps;
        }
        
}
