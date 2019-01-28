/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.safety;

import com.controller.common.JSONConfigure;
import com.controller.common.VersionType;
import com.controller.notification.NotificationController;
import com.google.gson.Gson;
import com.model.ivn_supervisor.Vehicle;
import com.model.ivn_supervisor.VehicleModel;
import com.model.ivn_supervisor.Vehicle_and_Model_Mapping;
import com.model.ivn_supervisor.Vehicleversion;
import com.model.ivn_supervisor.VehicleversionDB;
import com.model.safety.SafetyCombination;
import com.model.safety.SafetyDB;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author ets-2
 */
public class Safety_Combination {
    private Map<String, String> maps = new HashMap<String, String>();
    private List<Map<String, Object>> safetycomb_result = new ArrayList<Map<String, Object>>();
    private List<Map<String, Object>> result_data = new ArrayList<Map<String, Object>>();
    public String result_data_obj;
    static HttpServletRequest request;
    public String CreateSafetyComb(){
        int comb_id = 0;
        String previousversion_status = null;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        System.out.println("CreateSafetyComb");
        request  =  ServletActionContext.getRequest();
        System.out.println(request.getParameter("sql"));
        try {
            System.out.println("entered try");
            if (request != null && request.getParameter("cid") != null) {
                comb_id = Integer.parseInt((String) request.getParameter("cid"));
            }
            System.out.println("comb_id"+comb_id);
            if (comb_id != 0) {
                //Get the data of previous vehicle version by id
                int c_id = comb_id;
                SafetyCombination lc = new SafetyCombination(c_id);
                safetycomb_result = (List<Map<String, Object>>) SafetyDB.LoadPreviousSafetyCombinationData(lc);
                System.out.println("safetycomb_result" + safetycomb_result);
                previousversion_status = String.valueOf(safetycomb_result.get(0).get("querybuilder_status"));
                System.out.println("previousversion_status" + previousversion_status);
            }       
            if (previousversion_status == "false" && comb_id != 0) {
                System.out.println("Ready to update");
                boolean r_status = Boolean.valueOf(request.getParameter("qb_status"));
                SafetyCombination lc = new SafetyCombination(comb_id, request.getParameter("qb_name"), "safety", 
                        request.getParameter("sql"),r_status, dtf.format(now),"update");
                //int result = VehicleversionDB.insertVehicleVersion(v);
                Object[] lc_res = SafetyDB.insertSafetyCombination(lc);
                int result = (int) lc_res[0];
                maps.put("status", "Safety Combination Updated Successfully");
            }
            else {
                System.out.println("Ready to insert");
                boolean r_status = Boolean.valueOf(request.getParameter("qb_status"));
                SafetyCombination lc = new SafetyCombination(request.getParameter("qb_name"), "safety", 
                        request.getParameter("sql"),r_status, dtf.format(now),"create");
                //int result = VehicleversionDB.insertVehicleVersion(v);
                Object[] lc_res = SafetyDB.insertSafetyCombination(lc);
                int result = (int) lc_res[0];
                maps.put("status", "Safety Combination Created Successfully");
            }
            System.out.println("maps"+maps);
        } catch (Exception ex) {
            System.out.println("entered into catch");
            System.out.println(ex.getMessage());
            maps.put("status", "Some error occurred !!");
        }
        return "success";
    }
    public String GetSafetyCombinationListing() {
        System.out.println("GetSafetyCombinationListing controller");
        SafetyCombination lc = new SafetyCombination();
        try {
            result_data = (List<Map<String, Object>>) SafetyDB.GetSafetyCombinationListing(lc);
            result_data_obj = new Gson().toJson(result_data);
//            vehmod_map_result_obj = new Gson().toJson(vehmod_map_result);
//                vehmod_map_result_obj =  Gson().toJSON(vehmod_map_result);
            System.out.println("oject" + result_data_obj);
        } catch (Exception ex) {
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
