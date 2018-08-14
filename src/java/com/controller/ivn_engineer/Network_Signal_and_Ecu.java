/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.ivn_engineer;

import com.controller.common.JSONConfigure;
import com.google.gson.Gson;
import com.model.ivn_engineer.Network_Ecu;
import com.model.ivn_engineer.IVNEngineerDB;
import com.model.ivn_engineer.Signal;
import com.model.ivn_supervisor.Vehicleversion;
import com.model.ivn_supervisor.VehicleversionDB;
import com.model.pdbowner.PDBVersionDB;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author ets-2
 */
public class Network_Signal_and_Ecu {

    private Map<String, String> maps = new HashMap<String, String>();
    private List<Map<String, Object>> vehicleversion_result = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> ivnversion_result = new ArrayList<Map<String, Object>>();
//    private List<Map<String, Object>> domainfeatures_result = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> eculist_result = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> signallist_result = new ArrayList<Map<String, Object>>();
    private Map<String, Object> network_list = new HashMap<String, Object>();
//    private List<Map<String, Object>> result_data = new ArrayList<Map<String, Object>>();
    public String eculist_result_obj;
    public String signallist_result_obj;
    public String network_list_obj;
    public String IVNVersionCreationPage() { 
        try{
            vehicleversion_result = VehicleversionDB.LoadVehicleVersion("active");
            ivnversion_result = IVNEngineerDB.LoadIVNVersion();
            network_list = IVNEngineerDB.LoadNetwork();
            network_list_obj = new Gson().toJson(network_list);
            
            eculist_result = IVNEngineerDB.LoadECU();
            eculist_result_obj = new Gson().toJson(eculist_result);
            
            signallist_result = IVNEngineerDB.LoadSignals();
            signallist_result_obj = new Gson().toJson(signallist_result);
            
            System.out.println("pdbversion_result"+ivnversion_result);
            System.out.println("vehicleversion_result"+vehicleversion_result);
            
            System.out.println("network_list_obj"+network_list_obj);
            System.out.println("eculist_result_obj"+eculist_result_obj);
            System.out.println("signallist_result_obj"+signallist_result_obj);
        }
        catch (Exception ex) { 
            System.out.println(ex.getMessage()); 
            maps.put("status", "Some error occurred !!"); 
        }
        return "success";
    }
    
    public String CreateIVNVersion_Attributes(){
        maps.put("status", "Function called"); 
        JSONParser parser = new JSONParser();
        String jsondata = JSONConfigure.getAngularJSONFile();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now(); 
        try {
            Object obj = parser.parse(jsondata);
            JSONObject json = (JSONObject) obj;  
            System.out.println("json"+json);
            String nw_type = (String) json.get("network");
            if(!nw_type.equals("signals") && !nw_type.equals("ecu")){
                String nw_name = (String) json.get("name");
                String nw_description = (String) json.get("description");
                Network_Ecu n = new Network_Ecu(nw_name,nw_description,dtf.format(now),1,nw_type);
                int result = IVNEngineerDB.insertNetworkData(n);
            }
            else if(nw_type.equals("ecu")){
                String ecu_name = (String) json.get("name");
                String ecu_description = (String) json.get("description");
                Network_Ecu n = new Network_Ecu(ecu_name,ecu_description,nw_type,dtf.format(now),1);
                int result = IVNEngineerDB.insertNetworkData(n);
            }
            else{
                System.out.println("Signals");
                String signal_name = (String) json.get("name");
                String signal_alias = (String) json.get("alias");
                String signal_description = (String) json.get("description");               
                String signal_byteorder = (String) json.get("byteorder");
                String signal_unit = (String) json.get("unit");
                String signal_valuetype = (String) json.get("valuetype");
                String signal_valuetable = (String) json.get("valuetable");
                String signal_can_id = (String) json.get("can");
                String signal_lin_id = (String) json.get("lin");
                String signal_hw_id = (String) json.get("hardware");
                System.out.println("int value started");
                int signal_length = Integer.parseInt((String) json.get("length"));
                System.out.println("signal_length"+signal_length);
                int signal_initvalue = Integer.parseInt((String) json.get("initvalue"));
                System.out.println("signal_initvalue"+signal_initvalue);
                double signal_factor = Double.parseDouble((String) json.get("factor"));
                System.out.println("signal_factor"+signal_factor);
                int signal_offset = Integer.parseInt((String) json.get("offset"));
                System.out.println("signal_offset"+signal_offset);
                int signal_minimum = Integer.parseInt((String) json.get("minimum"));
                System.out.println("signal_minimum"+signal_minimum);
                int signal_maximum = Integer.parseInt((String) json.get("maximum"));
                System.out.println("signal_maximum"+signal_maximum);
                
                Signal s = new Signal(signal_name,signal_alias,signal_description,signal_length,
                                      signal_byteorder,signal_unit,signal_valuetype,signal_initvalue,
                                      signal_factor,signal_offset,signal_minimum,signal_maximum,
                                      signal_valuetable,signal_can_id,signal_lin_id,signal_hw_id,
                                      dtf.format(now),1);
                int result = IVNEngineerDB.insertSignalData(s);
//                String ecu_name = (String) json.get("name");
//                String ecu_description = (String) json.get("description");
//                Network_Ecu n = new Network_Ecu(ecu_name,ecu_description,nw_type,dtf.format(now),1);
//                int result = IVNEngineerDB.insertNetworkData(n);
            }
            maps.put("status", "Record Inserted Successfully"); 
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
    
    public List<Map<String, Object>> getIvnversion_result() {
            return ivnversion_result;
    }

    public void setIvnversion_result(List<Map<String, Object>> ivnversion_result) {
            this.ivnversion_result = ivnversion_result;
    }
    
    public String getEculist_result_obj() {
            return eculist_result_obj;
    }
    public void setEculist_result_obj(String eculist_result_obj) {
            this.eculist_result_obj = eculist_result_obj;
    }
    
    public String getSignallist_result_obj() {
            return signallist_result_obj;
    }
    public void setSignallist_result_obj(String signallist_result_obj) {
            this.signallist_result_obj = signallist_result_obj;
    }
//    public List<Map<String, Object>> getDomainFeatures_result() {
//            return domainfeatures_result;
//    }
//
//    public void setDomainFeatures_result(List<Map<String, Object>> domainfeatures_result) {
//            this.domainfeatures_result = domainfeatures_result;
//    }
    
    public Map<String, Object> getNetwork_list() {
            return network_list;
    }

    public void setNetwork_list(Map<String, Object> network_list) {
            this.network_list = network_list;
    }
//    
//    public List<Map<String, Object>> getResult_data() {
//            return result_data;
//    }
//
//    public void setResult_data(List<Map<String, Object>> result_data) {
//            this.result_data = result_data;
//    }
    public String getNetwork_list_obj() {
            return network_list_obj;
    }

    public void setNetwork_list_obj(String network_list_obj) {
            this.network_list_obj = network_list_obj;
    }
}
