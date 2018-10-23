/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.admin;

import com.controller.common.JSONConfigure;
import com.controller.common.MailUtil;
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
import java.util.UUID;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author ETS-4
 */
public class AddUserController extends ActionSupport {

    private Map<String, String> returnStatus = new HashMap<>();
    private Map<String, String> updateStatus = new HashMap<>();
    private List<FetchUser> userList = new ArrayList<>();
    private List<Map<String, Object>> userDetails = new ArrayList<>();
    private String employee_id;

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public Map<String, String> getReturnStatus() {
        return returnStatus;
    }

    public void setReturnStatus(Map<String, String> returnStatus) {
        this.returnStatus = returnStatus;
    }

    public Map<String, String> getUpdateStatus() {
        return updateStatus;
    }

    public void setUpdateStatus(Map<String, String> updateStatus) {
        this.updateStatus = updateStatus;
    }

    public List<FetchUser> getUserList() {
        return userList;
    }

    public void setUserList(List<FetchUser> userList) {
        this.userList = userList;
    }

    public List<Map<String, Object>> getUserDetails() {
        return userDetails;
    }

    public void setUserDetails(List<Map<String, Object>> userDetails) {
        this.userDetails = userDetails;
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
            //System.out.println("json" + json);

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
                double mobile_number = Double.parseDouble(userDetails.get("mobile_number") + "");
                long group_id = (long) userDetails.get("group_id");

                Object[] statusObj = UserDB.checkEmployeeIdMailExists(employee_id, email);

                if ((boolean) statusObj[0]) {
                    returnStatus.put("empStatus", "Employee ID already exists");
                }
                if ((boolean) statusObj[1]) {
                    returnStatus.put("emailStatus", "Email Id already exists");
                }
                if (!(boolean) statusObj[0] && !(boolean) statusObj[1]) {
                    if (MailUtil.isValidEmail(email)) {
                        int insertedId = UserDB.createUser(new Users(userName, employee_id, first_name, last_name, password, email, supervisor_email, mobile_number, Math.toIntExact(group_id), status, dtf.format(now)));
                        if (insertedId != 0) {
                            String verificationId = UUID.randomUUID().toString().replace("-", "");
                            if (UserDB.insertVerificationId(insertedId, verificationId)) {
                                MailUtil.sendVerificationMail(email, "Verification of Email", insertedId, verificationId);
                                returnStatus.put("insertStatus", "Confirmation Email Sent");
                            }
                        }
                    } else {
                        returnStatus.put("mailStatus", "Invalid Email");
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }

    public String fetchUserList() {
        userList = UserDB.getUserList();
        return "success";
    }

    public String fetchUserDetails() {
        userDetails = UserDB.getUserDetails(getEmployee_id());
        return "success";
    }

    public String updateDetails() {
        JSONParser parser = new JSONParser();
        String jsondata = JSONConfigure.getAngularJSONFile();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        boolean status = (boolean) false;

        try {
            Object obj = parser.parse(jsondata);
            JSONObject json = (JSONObject) obj;
            //System.out.println("json" + json);

            if (json.containsKey("status")) {
                status = (boolean) json.get("status");
            }

            if (json.containsKey("user_details")) {
                JSONObject userDetails = (JSONObject) json.get("user_details");
                Integer id = (int) (long) userDetails.get("id");
                String userName = (String) userDetails.get("user_name"),
                        employee_id = (String) userDetails.get("emp_id"),
                        first_name = (String) userDetails.get("first_name"),
                        last_name = (String) userDetails.get("last_name"),
                        password = (String) userDetails.get("password"),
                        email = (String) userDetails.get("email"),
                        supervisor_email = (String) userDetails.get("supervisor_email");
                double mobile_number = Double.parseDouble(userDetails.get("mobile_number") + "");
                long group_id = (long) userDetails.get("group_id");

                Object[] statusObj = UserDB.checkEmployeeIdMailExists(employee_id, email);
                Object[] checkId = UserDB.getEmployeeIdMail(id);

                if (checkId[0].toString().equals(employee_id) && checkId[1].toString().equals(email)) {
                    boolean updateStat = UserDB.updateDetails(new Users(userName, employee_id, first_name, last_name, password, email, supervisor_email, mobile_number, Math.toIntExact(group_id), status, dtf.format(now)), id);

                    if (updateStat) {
                        updateStatus.put("updateStatus", "Successfully Updated User");
                    }
                } else {
                    if ((boolean) statusObj[0] && !checkId[0].toString().equals(employee_id)) {
                        updateStatus.put("empStatus", "Employee ID already exists");
                    }
                    if ((boolean) statusObj[1] && !checkId[1].toString().equals(email)) {
                        updateStatus.put("emailStatus", "Email Id already exists");
                    }
                    if (!(boolean) statusObj[0] || !(boolean) statusObj[1]) {
                        boolean updateStat = UserDB.updateDetails(new Users(userName, employee_id, first_name, last_name, password, email, supervisor_email, mobile_number, Math.toIntExact(group_id), status, dtf.format(now)), id);

                        if (updateStat) {
                            updateStatus.put("updateStatus", "Successfully Updated User");
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "success";
    }
}
