/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.ivn_engineer;

import com.google.gson.Gson;
import com.model.ivn_engineer.IVNEngineerDB;
import com.model.ivn_supervisor.VehicleversionDB;
import com.model.pdbowner.PDBVersionDB;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
