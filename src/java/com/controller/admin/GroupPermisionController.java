/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.admin;

import com.controller.JSONConfigure;
import com.model.AdminDb;
import com.model.CreateUserGroup;
import com.model.GroupPermision;

import com.model.ModuleModel;
import com.model.ModulePermision;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.struts2.convention.annotation.Result;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

@Result(type = "json")
public class GroupPermisionController extends ActionSupport {

    List<CreateUserGroup> groupList=new ArrayList<>();
    List<ModuleModel> moduleList=new ArrayList<>();
    private List<ModulePermision> modulePermision = new ArrayList<ModulePermision>();
    JSONParser parser;
    String jsondata;
    Object obj;
    JSONObject json;
    private Map<String,String> maps=new HashMap<>();

    public Map<String, String> getMaps() {
        return maps;
    }

    public void setMaps(Map<String, String> maps) {
        this.maps = maps;
    }
    public String getAllGroup_Model() {
        groupList = AdminDb.getAllGroupName();
        moduleList = AdminDb.getAllModuleName();
        return Action.SUCCESS;
    }

    public String getPermisionByModuleId() {
        String moduleId;
        System.out.println("on change method---");
        try {
            parser = new JSONParser();
            jsondata = JSONConfigure.getAngularJSONFile();
            obj = parser.parse(jsondata);
            json = (JSONObject) obj;
            moduleId = (String) json.get("module_id");
            modulePermision = AdminDb.getPermisionByModuleId(moduleId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Action.SUCCESS;
    }

    public String addGroupPermission() {
        System.out.println("group permission----");
        try {
            parser = new JSONParser();
            jsondata = JSONConfigure.getAngularJSONFile();
            obj = parser.parse(jsondata);
            json = (JSONObject) obj;
            System.out.println("json-:" + json);
            String moduleId = (String) json.get("mudulename");
            int md_id = Integer.parseInt(moduleId);
            String groupId = (String) json.get("groupName");
            int gp_id = Integer.parseInt(groupId);
            System.out.println("module id:-" + moduleId + " groupId:-" + groupId);
            JSONArray Module_data = (JSONArray) json.get("selectmultiple");
           
            for (int i = 0; i < Module_data.size(); i++) {
                String operation_id = Module_data.get(i).toString();
                int op_id = Integer.parseInt(operation_id);
                GroupPermision groupPermission = new GroupPermision(gp_id, md_id, op_id);
                int groupPermission_result = AdminDb.addGroupPermission(groupPermission);
                if (groupPermission_result == 1) {
                    System.out.println("insert success");
                }
                System.out.println("array is " + Module_data.get(i));
            }
           
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Action.SUCCESS;
    }
    public List<CreateUserGroup> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<CreateUserGroup> groupList) {
        this.groupList = groupList;
    }

    public List<ModuleModel> getModuleList() {
        return moduleList;
    }

    public void setModuleList(List<ModuleModel> moduleList) {
        this.moduleList = moduleList;
    }

    public List<ModulePermision> getModulePermision() {
        return modulePermision;
    }

    public void setModulePermision(List<ModulePermision> modulePermision) {
        this.modulePermision = modulePermision;
    }

}
