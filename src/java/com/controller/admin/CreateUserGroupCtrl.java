/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.admin;

import com.controller.common.JSONConfigure;
import com.google.gson.Gson;

import com.model.AdminDb;
import com.model.CreateUserGroup;
import com.model.pdb_owner.Features;
import com.model.pdb_owner.PDBVersionDB;
import static com.mysql.jdbc.StringUtils.isNullOrEmpty;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//@Result(type = "json")
public class CreateUserGroupCtrl extends ActionSupport {
    private Map<String, String> maps = new HashMap<>();
    private List<CreateUserGroup> userGroupList;
    private List<Map<String, Object>> result_data = new ArrayList<Map<String, Object>>();
//    public String featureslist_result_obj;
    public String result_data_obj;
    
    
    public String getAllUserGroup() 
    {
//        userGroupList= AdminDb.getAllUserGroup();
//        return Action.SUCCESS;
        System.out.println("getAllUserGroup controller");
//        Features fea = new Features();
        try{
            result_data = (List<Map<String, Object>>) AdminDb.getAllUserGroup();
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
