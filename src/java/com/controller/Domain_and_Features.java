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
import com.model.PDBVersionGroup;
import com.model.PDBversion;
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
import org.json.simple.parser.ParseException;

/**
 *
 * @author ets-2
 */
public class Domain_and_Features extends ActionSupport{
    private Map<String, String> maps = new HashMap<String, String>();
    private List<Map<String, Object>> vehicleversion_result = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> pdbversion_result = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> domainfeatures_result = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> featureslist_result = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> pdb_map_result = new ArrayList<Map<String, Object>>();
    public String featureslist_result_obj;
    
    public String PDBAssignPage(){
        System.out.println("Entered");
        System.out.println("PDBAssignPage");
        try{
            vehicleversion_result = VehicleversionDB.LoadVehicleVersion();
            pdbversion_result = PDBVersionDB.LoadPDBVersion();
            featureslist_result = PDBVersionDB.LoadFeaturesList();
            featureslist_result_obj = new Gson().toJson(featureslist_result);
            System.out.println("pdbversion_result"+pdbversion_result);
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
    public String CreatePDBVersion() { 
        System.out.println("CreatePDBVersion");
//        String button_type = (String) json.get("button_type");
        String button_type = "submit";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  
        boolean status = (boolean) false;
        int pdbversion_id = 0;
        String previousversion_status = null;
        try {     
            if(previousversion_status == "false" && button_type.equals("save") && pdbversion_id != 0){
                System.out.println("id passed");
            }
            else{
                System.out.println("no id");
                PDBversion pv = new PDBversion((float) 1.0, status,dtf.format(now),1,"create");
                int pdb_id = PDBVersionDB.insertPDBVersion(pv);
                int vmm_id = 1;
                int dfm_id = 2;
                String av_status = "y";
                PDBVersionGroup pvg = new PDBVersionGroup(pdb_id,vmm_id,dfm_id,av_status,button_type,"create");
                int pvg_id = PDBVersionDB.insertPDBVersionGroup(pvg);
            }
        }
        catch (Exception ex) { 
            System.out.println("entered into catch");
            System.out.println(ex.getMessage()); 
            maps.put("status", "Some error occurred !!"); 
        }
        return "success";
    }   
    public String LoadPDBPreviousVehicleversionData() throws ParseException {
        System.out.println("LoadPDBPreviousVehicleversionData controller");
        JSONParser parser = new JSONParser();
        String jsondata = JSONConfigure.getAngularJSONFile();

        Object obj = parser.parse(jsondata);
        JSONObject json = (JSONObject) obj; 
        int pdbver_id = Integer.parseInt((String) json.get("pdbversion_id")); 
        PDBversion pdbver = new PDBversion(pdbver_id);

        try{
            pdb_map_result = (List<Map<String, Object>>) PDBVersionDB.LoadPDBPreviousVehicleversionData(pdbver);
//            pdb_map_result_obj = new Gson().toJson(pdb_map_result);
//                vehmod_map_result_obj =  Gson().toJSON(vehmod_map_result);
            System.out.println("pdb_map_result"+pdb_map_result);
        }
        catch (Exception ex) { 
            System.out.println(ex.getMessage()); 
            maps.put("status", "Some error occurred !!"); 
        }
//            return vehmod_map_result;
//            System.out.println("Result"+vehmod_map_result);
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
    public List<Map<String, Object>> getPdbversion_result() {
            return pdbversion_result;
    }

    public void setPdbversion_result(List<Map<String, Object>> pdbversion_result) {
            this.pdbversion_result = pdbversion_result;
    }
    
    
}
