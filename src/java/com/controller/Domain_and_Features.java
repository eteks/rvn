/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.google.gson.Gson;
import com.model.Domain;
import com.model.Domain_and_Features_Mapping;
import com.model.Features;
import com.model.PDBVersionDB;
import com.model.Vehicle;
import com.model.VehicleModel;
import com.model.Vehicle_and_Model_Mapping;
import com.model.Vehicleversion;
import com.model.VehicleversionDB;
import com.opensymphony.xwork2.ActionSupport;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author ets-2
 */
public class Domain_and_Features extends ActionSupport{
    private Map<String, String> maps = new HashMap<String, String>();
    private List<Map<String, Object>> vehicleversion_result = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> domainfeatures_result = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> featureslist_result = new ArrayList<Map<String, Object>>();
    public String featureslist_result_obj;
    
    public String PDBAssignPage(){
        System.out.println("Entered");
        System.out.println("PDBAssignPage");
        try{
            vehicleversion_result = VehicleversionDB.LoadVehicleVersion();
            featureslist_result = PDBVersionDB.LoadFeaturesList();
            featureslist_result_obj = new Gson().toJson(featureslist_result);
            System.out.println("vehicleversion_result"+vehicleversion_result);
            System.out.println("featureslist_result"+featureslist_result_obj);
        }
        catch (Exception ex) { 
            System.out.println(ex.getMessage()); 
            maps.put("status", "Some error occurred !!"); 
        }
        return "success";
    }
    
    public String CreateDomain_and_Features() { 
        System.out.println("CreateDomain_and_Features");
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
            System.out.println(json);
            String domain_name = (String) json.get("domain_name");
            JSONArray features_and_description = (JSONArray) json.get("features_and_description");
            System.out.println("vehiclename"+domain_name);
            System.out.println("vehicle_and_model_value"+features_and_description);
            
//            Insert Data in Domain table
            Domain dom = new Domain(domain_name,dtf.format(now),1);
            int dom_result = PDBVersionDB.insertDomain(dom);
            String dom_result1 = Integer.toString(dom_result);
            Map<String, Object> columns = new HashMap<String, Object>();
            columns.put("domain",domain_name);            
            //Insert Data in Features table
            for (Object o : features_and_description) {
                JSONObject fd_item = (JSONObject) o;
                String feature_name = (String) fd_item.get("feature");
                String feature_description = (String) fd_item.get("description");
                Features fd = new Features(feature_name,feature_description,dtf.format(now),1);
                int fd_result = PDBVersionDB.insertFeatures(fd);
                
                //Insert Data in Domain and Features Mapping Table
                Domain_and_Features_Mapping dfm = new Domain_and_Features_Mapping(dom_result,fd_result,dtf.format(now));
                int fdm_result = PDBVersionDB.insertDomainFeaturesMapping(dfm);
                columns.put("fid",fdm_result);
                columns.put("fea",feature_name);
                domainfeatures_result.add(columns);
            }        
//            domainfeatures_result_obj = new Gson().toJson(domainfeatures_result);
//            System.out.println("domain_result"+domainfeatures_result_obj);
        }
        catch (Exception ex) { 
            System.out.println("entered into catch");
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
    public List<Map<String, Object>> getVehicleversion_result() {
            return vehicleversion_result;
    }

    public void setVehicleversion_result(List<Map<String, Object>> vehicleversion_result) {
            this.vehicleversion_result = vehicleversion_result;
    }
    public String getFeatureslist_result_obj() {
                return featureslist_result_obj;
    }

    public void setFeatureslist_result_obj(String featureslist_result_obj) {
            this.featureslist_result_obj = featureslist_result_obj;
    }
    public List<Map<String, Object>> getDomainFeatures_result() {
            return domainfeatures_result;
    }

    public void setDomainFeatures_result(List<Map<String, Object>> domainfeatures_result) {
            this.domainfeatures_result = domainfeatures_result;
    }
    
}
