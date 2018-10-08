/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.system_owner;

import com.controller.common.JSONConfigure;
import com.google.gson.Gson;
import com.model.pdb_owner.Domain;
import com.model.pdb_owner.Domain_and_Features_Mapping;
import com.model.pdb_owner.Features;
import com.model.pdb_owner.PDBVersionDB;
import com.model.system_owner.ECU_and_Variants_Mapping;
import com.model.system_owner.SystemOwnerDB;
import com.model.system_owner.Variants;
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
public class ECU_Variants_and_Features {
    private Map<String, String> maps = new HashMap<String, String>();
    private List<Map<String, Object>> result_data = new ArrayList<Map<String, Object>>();
    public String result_data_obj;
    public String GetECU_Listing() 
    {
        System.out.println("GetECU_Listing controller");
//        Features fea = new Features();
        try{
            result_data = (List<Map<String, Object>>) SystemOwnerDB.GetECU_Listing();
            result_data_obj = new Gson().toJson(result_data);
//            vehmod_map_result_obj = new Gson().toJson(vehmod_map_result);
//                vehmod_map_result_obj =  Gson().toJSON(vehmod_map_result);
            System.out.println("oject"+result_data_obj);
        }
        catch (Exception ex) { 
            System.out.println(ex.getMessage()); 
            maps.put("status", "Some error occurred !!"); 
        }
//            return vehmod_map_result;
//            System.out.println("Result"+vehmod_map_result);
        return "success";
    }
    public String CreateVariants() throws ParseException { 
        System.out.println("CreateVariants");
        JSONParser parser = new JSONParser();
        String jsondata = JSONConfigure.getAngularJSONFile();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
        LocalDateTime now = LocalDateTime.now();  
        try {                
            Object obj = parser.parse(jsondata);
            JSONObject json = (JSONObject) obj;     
            System.out.println(json);
            int ecu_id = Integer.parseInt((String) json.get("ecu_id"));
            JSONArray variants_list = (JSONArray) json.get("variants"); 
            String operation_type = (String) json.get("operation_type");
            System.out.println("variants_list"+variants_list);
            System.out.println("ecu_id"+ecu_id);  
            int i=0;
            for (Object o : variants_list) {    
                JSONObject variant_data = (JSONObject) o;
                System.out.println("for_loop");
                System.out.println("variant_name"+variant_data.get("variants"));
                String variant_name = (String) variant_data.get("variants");
                if(variant_name != null){
                    //Insert data in Variants table
                    Variants v = new Variants(variant_name,dtf.format(now),1);
                    int variants_result =  SystemOwnerDB.insertVariants(v);

                    //Insert data in ECU and Variants mapping table
                    ECU_and_Variants_Mapping evm = new ECU_and_Variants_Mapping(ecu_id,variants_result,dtf.format(now));
                    int evm_result =  SystemOwnerDB.insertEcuVariantsMapping(evm);

                    //Delete data from Ecu and variants mapping table if variants is not passed for ecu
                    if(i++ == variants_list.size() - 1){
                        SystemOwnerDB.deleteEcuVariantsMapping(ecu_id);
                        if(operation_type.equals("create"))
                            maps.put("status", "ECU Variants Created Successfully");
                        else
                            maps.put("status", "ECU Variants Updated Successfully");
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
    public Map<String, String> getMaps() {
            return maps;
    }
    public void setMaps(Map<String, String> maps) {
            this.maps = maps;
    }
    public List<Map<String, Object>> getResult_data() {
            return result_data;
    }

    public void setResult_data(List<Map<String, Object>> result_data) {
            this.result_data = result_data;
    }
    public String getResult_data_obj() {
            return result_data_obj;
    }

    public void setResult_data_obj(String result_data_obj) {
            this.result_data_obj = result_data_obj;
    }
}
