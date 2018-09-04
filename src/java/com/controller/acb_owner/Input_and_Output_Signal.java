package com.controller.acb_owner;

import com.controller.common.JSONConfigure;
import com.google.gson.Gson;
import com.model.acb_owner.ACBOwnerDB;
import com.model.ivn_engineer.IVNEngineerDB;
import com.model.ivn_engineer.IVNversion;
import com.model.ivn_supervisor.VehicleversionDB;
import com.model.pdb_owner.PDBVersionDB;
import com.model.pdb_owner.PDBversion;
import com.opensymphony.xwork2.ActionContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ets-2
 */
public class Input_and_Output_Signal {
    private Map<String, String> maps = new HashMap<String, String>();
    private List<Map<String, Object>> pdbversion_result = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> ivnversion_result = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> acbversion_result = new ArrayList<Map<String, Object>>();
    private Map<String, Object> pdb_map_result = new HashMap<String, Object>();
    private Map<String, Object> ivn_map_result = new HashMap<String, Object>();
    
    public String ACBVersionCreationPage(){
        System.out.println("Entered");
        System.out.println("ACBVersionCreationPage");
        //This will execute if url contains parameter(id and action-edit, view)
        try{
            HttpServletRequest request = (HttpServletRequest) ActionContext.getContext()
                              .get(ServletActionContext.HTTP_REQUEST);
            System.out.println("request"+request);
            System.out.println("id_value"+request.getParameter("id"));
            System.out.println("action_value"+request.getParameter("action"));
            
            pdbversion_result = PDBVersionDB.LoadPDBVersion("active");
            ivnversion_result = IVNEngineerDB.LoadIVNVersion("active");
            acbversion_result = ACBOwnerDB.LoadACBVersion("all");
            System.out.println("pdbversion_result"+pdbversion_result);
        }
        catch (Exception ex){
             System.out.println(ex.getMessage()); 
        }
        try{
            pdbversion_result = PDBVersionDB.LoadPDBVersion("active");
            ivnversion_result = IVNEngineerDB.LoadIVNVersion("active");
            acbversion_result = ACBOwnerDB.LoadACBVersion("all");
            System.out.println("pdbversion_result"+pdbversion_result);
        }
        catch (Exception ex) { 
            System.out.println(ex.getMessage()); 
            maps.put("status", "Some error occurred !!"); 
        }
        return "success";
    }
    public String LoadPDBDataForACBVersion() throws ParseException{
        System.out.println("LoadPDBDataForACBVersion controller");
        JSONParser parser = new JSONParser();
        String jsondata = JSONConfigure.getAngularJSONFile();

        Object obj = parser.parse(jsondata);
        JSONObject json = (JSONObject) obj; 
        int pdbver_id = Integer.parseInt((String) json.get("pdbversion_id")); 
        PDBversion pdbver = new PDBversion(pdbver_id);

        try{
            pdb_map_result = ACBOwnerDB.LoadPDBDataForACBVersion(pdbver);
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
    
    public String LoadIVNDataForACBVersion() throws ParseException {
        System.out.println("LoadIVNDataForACBVersion controller");
        JSONParser parser = new JSONParser();
        String jsondata = JSONConfigure.getAngularJSONFile();

        Object obj = parser.parse(jsondata);
        JSONObject json = (JSONObject) obj; 
        int ivnver_id = Integer.parseInt((String) json.get("ivnversion_id")); 
        IVNversion ivnver = new IVNversion(ivnver_id);

        try{
            ivn_map_result = ACBOwnerDB.LoadIVNDataForACBVersion(ivnver);
//            pdb_map_result_obj = new Gson().toJson(pdb_map_result);
//                vehmod_map_result_obj =  Gson().toJSON(vehmod_map_result);
            System.out.println("ivn_map_result"+ivn_map_result);
        }
        catch (Exception ex) { 
            System.out.println(ex.getMessage()); 
            maps.put("status", "Some error occurred !!"); 
        }
//            return vehmod_map_result;
//            System.out.println("Result"+vehmod_map_result);
        return "success";
    }
       
    public List<Map<String, Object>> getPdbversion_result() {
            return pdbversion_result;
    }
    public void setPdbversion_result(List<Map<String, Object>> pdbversion_result) {
            this.pdbversion_result = pdbversion_result;
    }   
    public List<Map<String, Object>> getIvnversion_result() {
            return ivnversion_result;
    }
    public void setIvnversion_result(List<Map<String, Object>> ivnversion_result) {
            this.ivnversion_result = ivnversion_result;
    }
    public List<Map<String, Object>> getAcbversion_result() {
            return acbversion_result;
    }
    public void setAcbversion_result(List<Map<String, Object>> acbversion_result) {
            this.acbversion_result = acbversion_result;
    }
    public Map<String, Object> getPdb_map_result() {
            return pdb_map_result;
    }
    public void setPdb_map_result(Map<String, Object> pdb_map_result) {
            this.pdb_map_result = pdb_map_result;
    }
    public Map<String, Object> getIvn_map_result() {
            return ivn_map_result;
    }
    public void setIvn_map_result(Map<String, Object> ivn_map_result) {
            this.ivn_map_result = ivn_map_result;
    }
}
