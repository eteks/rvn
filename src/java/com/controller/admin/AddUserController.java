/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.admin;

import com.controller.common.JSONConfigure;
import com.model.admin.FetchUser;
import com.model.admin.UserDB;
import com.model.admin.Users;
import com.opensymphony.xwork2.ActionSupport;
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
 * @author ETS-4
 */
public class AddUserController extends ActionSupport{

    private Map<String, String> returnStatus = new HashMap<>();
    private List<FetchUser> userList = new ArrayList<>();

    public Map<String, String> getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(Map<String, String> returnStatus) {
        this.returnStatus = returnStatus;
    }

    public List<FetchUser> getUserList() {
        return userList;
    }

    public void setUserList(List<FetchUser> userList) {
        this.userList = userList;
    }

    public String createUser() {
        JSONParser parser = new JSONParser();
        String jsondata = JSONConfigure.getAngularJSONFile();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        boolean status = (boolean) false;

        try {
            Object obj = parser.parse(jsondata);
            JSONObject json = (JSONObject) obj;
            System.out.println("json" + json);

            if (json.containsKey("status")) {
                status = (boolean) json.get("status");
            }

            if (json.containsKey("user_details")) {
                JSONObject userDetails = (JSONObject) json.get("user_details");
                String userName = (String) userDetails.get("user_name"),
                        employee_id = (String) userDetails.get("emp_id"),
                        first_name = (String) userDetails.get("first_name"),
                        last_name = (String) userDetails.get("last_name"),
                        password = (String) userDetails.get("password"),
                        email = (String) userDetails.get("email"),
                        supervisor_email = (String) userDetails.get("supervisor_email");
                double mobile_number = Double.parseDouble(userDetails.get("mobile_number")+"");
                long group_id = (long) userDetails.get("group_id");

                Object[] statusObj = UserDB.checkEmployeeIdMailExists(employee_id, email);

                if ((boolean) statusObj[0]) {
                    returnStatus.put("empStatus", "Employee ID already exists");
                }
                if ((boolean) statusObj[1]) {
                    returnStatus.put("emailStatus", "Email Id already exists");
                }
                if (!(boolean) statusObj[0] && !(boolean) statusObj[1]) {
                    boolean insertStatus = UserDB.createUser(new Users(userName, employee_id, first_name, last_name, password, email, supervisor_email, mobile_number, Math.toIntExact(group_id), status, dtf.format(now)));

                    if (insertStatus) {
                        returnStatus.put("insertStatus", "Successfully Added User");
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    public String fetchUserList(){
        userList =  UserDB.getUserList();
        return"success";
    }
}
