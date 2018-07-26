/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.ivn_supervisor;

import com.controller.common.JSONConfigure;
import com.google.gson.Gson;
import com.model.ivn_supervisor.Vehicle;
import com.model.ivn_supervisor.VehicleModel;
import com.model.ivn_supervisor.Vehicle_and_Model_Mapping;
import com.model.ivn_supervisor.Vehicleversion;
import com.model.ivn_supervisor.VehicleversionDB;
import com.opensymphony.xwork2.ActionContext;
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
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.json.simple.parser.ParseException;
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
    
    public String CreateVehicleVersion() { 
            System.out.println("createvehicleversion");
            JSONParser parser = new JSONParser();
            String jsondata = JSONConfigure.getAngularJSONFile();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
            LocalDateTime now = LocalDateTime.now();  
            boolean status = (boolean) false;
            int vehicleversion_id = 0;
            String previousversion_status = null;
            try {                
                Object obj = parser.parse(jsondata);
                JSONObject json = (JSONObject) obj;               
//                Insert Data in vehicle version table
                JSONObject vehicleversion_value = (JSONObject) json.get("vehicleversion");
                JSONArray vehicle_and_model_value = (JSONArray) json.get("vehicle_and_model");
                String button_type = (String) json.get("button_type");
                if( vehicleversion_value != null && vehicleversion_value.containsKey("vehicleversion")){
                    vehicleversion_id = Integer.parseInt((String) vehicleversion_value.get("vehicleversion"));
                } 
                                
                if( vehicleversion_value != null && vehicleversion_value.containsKey("status")){
                    status = (boolean) vehicleversion_value.get("status");
                }    
                
                if(vehicleversion_id !=0)
                {
                    //Get the data of previous vehicle version by id
                    int vehver_id = vehicleversion_id; 
                    Vehicleversion vver = new Vehicleversion(vehver_id);
                    vehmod_map_result = (List<Map<String, Object>>) VehicleversionDB.LoadPreviousVehicleversionData(vver);
                    previousversion_status = String.valueOf(vehmod_map_result.get(0).get("status"));
                }    
                
                //Update existing version
                if(previousversion_status == "false" && button_type.equals("save") && vehicleversion_id != 0){
                    System.out.println("Ready to update");
//                    maps.put("status", "Record Updated successfully in same version");
                    //Update vehicleversion,vehicle, model and mapping
//                    int vehmod_map_result = VehicleversionDB.UpdateVehicleModel_and_Mapping();
                      Vehicleversion v = new Vehicleversion(vehicleversion_id, status,dtf.format(now),1,"update");
                      System.out.println("vehicleversion_id"+vehicleversion_id);
                      int result = VehicleversionDB.insertVehicleVersion(v);
                      System.out.println("update_result_id"+result);
                      for (Object o : vehicle_and_model_value) {
                          JSONObject vehicleitem = (JSONObject) o;
                          String vehiclename = (String) vehicleitem.get("vehiclename");
                          JSONArray modelvalue = (JSONArray) vehicleitem.get("modelname");
                          //Insert Data in vehicle table
                          Vehicle veh = new Vehicle(vehiclename,dtf.format(now),1);
                          int veh_result = VehicleversionDB.insertVehicle(veh);
                          int i = 0;
                          //Insert Data in Model table
                          for (Object o1 : modelvalue) {
                             VehicleModel veh_mod = new VehicleModel((String) o1,dtf.format(now),1);
                             int vehmod_result = VehicleversionDB.insertVehicleModel(veh_mod);
                             //Insert Data in VehicleModel Mapping table
                             Vehicle_and_Model_Mapping veh_mod_map = new Vehicle_and_Model_Mapping(result,veh_result,vehmod_result,button_type,"update");
                             int vehmod_map_result = VehicleversionDB.insertVehicleModelMapping(veh_mod_map);
                             if(i++ == modelvalue.size() - 1){
                                    maps.put("status", "Record Updated successfully in same version");
//                                  if(vehmod_map_result == 0)
//                                      maps.put("status", "New Temporary Version Created Successfully"); 
//                                  else
//                                      maps.put("status", "New Permanent Version Created Successfully");
                             }
                          }
                      }
                }
                //Create new temporary or permanent version
                else{      
                    System.out.println("else");
                      Vehicleversion v = new Vehicleversion((float) 1.0, status,dtf.format(now),1,"create");
                      int result = VehicleversionDB.insertVehicleVersion(v);
                      for (Object o : vehicle_and_model_value) {
                          JSONObject vehicleitem = (JSONObject) o;
                          String vehiclename = (String) vehicleitem.get("vehiclename");
                          JSONArray modelvalue = (JSONArray) vehicleitem.get("modelname");
                          //Insert Data in vehicle table
                          Vehicle veh = new Vehicle(vehiclename,dtf.format(now),1);
                          int veh_result = VehicleversionDB.insertVehicle(veh);
                          int i = 0;
                          //Insert Data in Model table
                          for (Object o1 : modelvalue) {
                             VehicleModel veh_mod = new VehicleModel((String) o1,dtf.format(now),1);
                             int vehmod_result = VehicleversionDB.insertVehicleModel(veh_mod);
                             //Insert Data in VehicleModel Mapping table
                             Vehicle_and_Model_Mapping veh_mod_map = new Vehicle_and_Model_Mapping(result,veh_result,vehmod_result,button_type,"create");
                             int vehmod_map_result = VehicleversionDB.insertVehicleModelMapping(veh_mod_map);
                             if(i++ == modelvalue.size() - 1){
                                  if(vehmod_map_result == 0)
                                      maps.put("status", "New Temporary Version Created Successfully"); 
                                  else
                                      maps.put("status", "New Permanent Version Created Successfully");
                             }
                          }
                      }       
                }
            }
            catch (Exception ex) { 
                System.out.println("entered into catch");
                System.out.println(ex.getMessage()); 
                maps.put("status", "Some error occurred !!"); 
            }
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
            //This will execute if url contains parameter(id and action-edit, view)
            try{
                HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
                                  .get(ServletActionContext.HTTP_REQUEST);
                System.out.println("request"+request);
                System.out.println("id_value"+request.getParameter("id"));
                System.out.println("action_value"+request.getParameter("action"));
                Vehicleversion vver = new Vehicleversion(Integer.parseInt(request.getParameter("id")));
                vehmod_map_result = (List<Map<String, Object>>) VehicleversionDB.LoadPreviousVehicleversionData(vver);
                vehmod_map_result_obj = new Gson().toJson(vehmod_map_result);
            }
            catch (Exception ex){
                 System.out.println(ex.getMessage()); 
            }
            
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
        
        public String LoadPreviousVehicleversionData() throws ParseException {
            System.out.println("LoadPreviousVehicleversionData controller");
            JSONParser parser = new JSONParser();
            String jsondata = JSONConfigure.getAngularJSONFile();
            
            Object obj = parser.parse(jsondata);
            JSONObject json = (JSONObject) obj; 
            int vehver_id = Integer.parseInt((String) json.get("vehicleversion_id")); 
            Vehicleversion vver = new Vehicleversion(vehver_id);
                    
            try{
                vehmod_map_result = (List<Map<String, Object>>) VehicleversionDB.LoadPreviousVehicleversionData(vver);
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
