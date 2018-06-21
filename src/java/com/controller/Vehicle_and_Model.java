/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.google.gson.Gson;
import com.model.Vehicle;
import com.model.VehicleModel;
import com.model.Vehicle_and_Model_Mapping;
import com.model.Vehicleversion;
import com.model.VehicleversionDB;
import com.opensymphony.xwork2.ActionSupport;
import java.sql.SQLException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 *
 * @author ets-2
 */
public class Vehicle_and_Model extends ActionSupport{
//    public String execute() throws IOException {
//        return "success";
//    }
    private List<Map<String, Object>> vehmod_map_result = new ArrayList<Map<String, Object>>();
    private Map<String, String> maps = new HashMap<String, String>();
    public String vehmod_map_result_obj;
    private List<Map<String, Object>> vehicleversion_result = new ArrayList<Map<String, Object>>();
//    JSONObject vehmod_map_result_obj = new JSONObject();
    
    public String CreateVehicleVersion() { 
            System.out.println("createvehicleversion");
            JSONParser parser = new JSONParser();
            String jsondata = JSONConfigure.getAngularJSONFile();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
            LocalDateTime now = LocalDateTime.now();  
            boolean status = (boolean) false;
            try {                
                Object obj = parser.parse(jsondata);
                JSONObject json = (JSONObject) obj; 
                System.out.println("output_json"+json);
                System.out.println("output_json1");
                
//                Insert Data in vehicle version table
                JSONObject vehicleversion_value = (JSONObject) json.get("vehicleversion");
                System.out.println("output_json2"+vehicleversion_value);
//                String name = (String) vehicleversion_value.get("vehicleversion"); 
                System.out.println("output_json3");
                if( vehicleversion_value != null && vehicleversion_value.containsKey("status")){
                    System.out.println("output_json4");
                    status = (boolean) vehicleversion_value.get("status");
                }              
                System.out.println("output_json4");
                Vehicleversion v = new Vehicleversion((float) 1.0, status,dtf.format(now),1);
                System.out.println("before_result_value");
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
    
        public String GetVehicleVersion_Listing() {
            System.out.println("GetVehicleVersion_Listing controller");
            System.out.println("GetVehicleVersion_Listing after controller");
            Vehicleversion vver = new Vehicleversion();
            try{
                vehmod_map_result = (List<Map<String, Object>>) VehicleversionDB.GetVehicleVersion_Listing(vver);
                vehmod_map_result_obj = new Gson().toJson(vehmod_map_result);
//                vehmod_map_result_obj =  Gson().toJSON(vehmod_map_result);
                System.out.println("oject"+vehmod_map_result_obj);
            }
            catch (Exception ex) { 
                System.out.println(ex.getMessage()); 
                maps.put("status", "Some error occurred !!"); 
            }
//            return vehmod_map_result;
//            System.out.println("Result"+vehmod_map_result);
            return "success";
	}
        
        public String GetVehicle_Listing() {
            System.out.println("GetVehicle_Listing controller");
            Vehicle veh = new Vehicle();
            try{
                vehmod_map_result = (List<Map<String, Object>>) VehicleversionDB.GetVehicle_Listing(veh);
                vehmod_map_result_obj = new Gson().toJson(vehmod_map_result);
//                vehmod_map_result_obj =  Gson().toJSON(vehmod_map_result);
                System.out.println("oject"+vehmod_map_result_obj);
            }
            catch (Exception ex) { 
                System.out.println(ex.getMessage()); 
                maps.put("status", "Some error occurred !!"); 
            }
//            return vehmod_map_result;
//            System.out.println("Result"+vehmod_map_result);
            return "success";
	}
        
        public String GetVehicleModel_Listing() {
            System.out.println("GetVehicleModel_Listing controller");
            VehicleModel veh_mod = new VehicleModel();
            try{
                vehmod_map_result = (List<Map<String, Object>>) VehicleversionDB.GetVehicleModel_Listing(veh_mod);
                vehmod_map_result_obj = new Gson().toJson(vehmod_map_result);
//                vehmod_map_result_obj =  Gson().toJSON(vehmod_map_result);
                System.out.println("oject"+vehmod_map_result_obj);
            }
            catch (Exception ex) { 
                System.out.println(ex.getMessage()); 
                maps.put("status", "Some error occurred !!"); 
            }
//            return vehmod_map_result;
//            System.out.println("Result"+vehmod_map_result);
            return "success";
	}
        
        public String DisplayCreateVehicleversion() {
            System.out.println("DisplayCreateVehicleversion controller");
//            VehicleModel veh_mod = new VehicleModel();
            try{
                vehicleversion_result = VehicleversionDB.LoadVehicleVersion();
                System.out.println("oject"+vehicleversion_result);
            }
            catch (Exception ex) { 
                System.out.println(ex.getMessage()); 
                maps.put("status", "Some error occurred !!"); 
            }
//            return vehmod_map_result;
//            System.out.println("Result"+vehmod_map_result);
            return "success";
	}
        
        public String LoadPreviousVehicleversionData() {
            System.out.println("LoadPreviousVehicleversionData controller");
            try{
                vehmod_map_result = (List<Map<String, Object>>) VehicleversionDB.LoadPreviousVehicleversionData();
                vehmod_map_result_obj = new Gson().toJson(vehmod_map_result);
//                vehmod_map_result_obj =  Gson().toJSON(vehmod_map_result);
                System.out.println("oject"+vehmod_map_result_obj);
            }
            catch (Exception ex) { 
                System.out.println(ex.getMessage()); 
                maps.put("status", "Some error occurred !!"); 
            }
//            return vehmod_map_result;
//            System.out.println("Result"+vehmod_map_result);
            return "success";
	}
           

        public List<Map<String, Object>> getVehmod_map_result() {
                return vehmod_map_result;
        }

        public void setVehmod_map_result(List<Map<String, Object>> vehmod_map_result) {
                this.vehmod_map_result = vehmod_map_result;
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

//        private Object Gson() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
        
}
