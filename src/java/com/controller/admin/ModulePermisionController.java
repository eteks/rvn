/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.admin;

import com.controller.common.JSONConfigure;
import com.model.AdminDb;
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

/**
 *
 * @author ETS-4
 */
@Result(type = "json")
public class ModulePermisionController extends ActionSupport {

    private List<ModuleModel> moduleNameList;
    private Map<String, String> maps = new HashMap<>();

    public List<ModuleModel> getModuleNameList() {
        return moduleNameList;
    }

    public void setModuleNameList(List<ModuleModel> moduleNameList) {
        this.moduleNameList = moduleNameList;
    }

    public Map<String, String> getMaps() {
        return maps;
    }

    public void setMaps(Map<String, String> maps) {
        this.maps = maps;
    }

    JSONParser parser;
    String jsondata;
    Object obj;
    JSONObject json;

    public String getAllModuleName() {
        moduleNameList = AdminDb.getAllModuleName();
        return Action.SUCCESS;
    }

    public String CreateModulePermision() {
        try {
            parser = new JSONParser();
            jsondata = JSONConfigure.getAngularJSONFile();
            obj = parser.parse(jsondata);
            json = (JSONObject) obj;
            String module_id = (String) json.get("mod_permission");
            int m_id = Integer.parseInt(module_id);
            boolean type = AdminDb.checkModuleId_On_ModulePermission(m_id);
            JSONArray Module_data = (JSONArray) json.get("moduleAct");
            System.out.println("module data size:-" + Module_data.size());
            if (!type) {
                if (Module_data.size() > 0) {
                    for (int i = 0; i < Module_data.size(); i++) {
                        String operation_id = Module_data.get(i).toString();
                        int op_id = Integer.parseInt(operation_id);
                        ModulePermision mp = new ModulePermision(m_id, op_id);
                        int result = AdminDb.addModulePermission(mp);
                        if (result > 0) {
                            System.out.println("insert success");
                            maps.put("status", "success");
                        }
                    }
                } else {
                    maps.put("status", "activity");
                }
            } else {
                maps.put("status", "exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Action.SUCCESS;
    }
}
