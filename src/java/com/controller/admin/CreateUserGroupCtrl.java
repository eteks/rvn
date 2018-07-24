/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.admin;

import com.controller.common.JSONConfigure;

import com.model.AdminDb;
import com.model.CreateUserGroup;
import static com.mysql.jdbc.StringUtils.isNullOrEmpty;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.struts2.convention.annotation.Result;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

@Result(type = "json")
public class CreateUserGroupCtrl extends ActionSupport {
    private Map<String, String> maps = new HashMap<>();
    private List<CreateUserGroup> userGroupList;
    public List<CreateUserGroup> getUserGroupList() {
        return userGroupList;
    }
    public void setUserGroupList(List<CreateUserGroup> userGroupList) {
        this.userGroupList = userGroupList;
    }
    public Map<String, String> getMaps() {
        return maps;
    }
    public void setMaps(Map<String, String> maps) {
        this.maps = maps;
    }
    String ugroup = null;
    public String createUserGroup() {
        try {
            JSONParser parser = new JSONParser();
            String jsondata = JSONConfigure.getAngularJSONFile();
            Object obj = parser.parse(jsondata);
            JSONObject json = (JSONObject) obj;
            ugroup = (String) json.get("usergroup");
            int status = 0;
            CreateUserGroup userGroup = new CreateUserGroup(ugroup, status);
            if(ugroup!=null && !ugroup.isEmpty())
            {
            boolean result = AdminDb.CheckGroup_name(userGroup);
            if (result) {
                maps.put("status", "exist");
            } else {
                boolean result1=AdminDb.addUserGroup(userGroup);
                if (result1) {
                    maps.put("status", "success");
                } else {
                    maps.put("status", "error");
                }
            }
            }
            else
            {
                maps.put("status", "empty");
            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
        return Action.SUCCESS;
    }
    public String getAllUserGroup() {
        userGroupList= AdminDb.getAllUserGroup();
        return Action.SUCCESS;
    }
}
