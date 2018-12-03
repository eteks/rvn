/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.pojo.user;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.Table;

/**
 *
 * @author ETS-4
 */
@Table("users")
@BelongsTo(parent = Groups.class, foreignKeyName = "group_id")
public class Users extends Model {

    public int getUserId(){
        return getInteger("id");
    }
    
    public String getUserName() {
        return getString("username");
    }
    
    public String getEmployeeId(){
        return getString("employee_id");
    }
    
    public String getFirstName(){
        return getString("firstname");
    }
    
    public String getLastName(){
        return getString("lastname");
    }
    
    public String getPassword(){
        return getString("password");
    }
    
    public String getEmail(){
        return getString("email");
    }
    
    public String getSupervisorEmail(){
        return getString("supervisor_email");
    }
    
    public double getMobileNumber(){
        return getDouble("mobile_number");
    }
    
    public int getGroupId(){
        return getInteger("group_id");
    }
    
    public boolean getStatus(){
        return getBoolean("status");
    }
    
    public boolean getEmailVerifyStatus(){
        return getBoolean("email_status");
    }

    public Users() {
    }

    public Users(String username, String employee_id, String firstname, String lastname, String password, String email, String supervisor_email, double mobile_number, int group_id, boolean status, String created_date) {
        set("username", username);
        set("employee_id", employee_id);
        set("firstname", firstname);
        set("lastname", lastname);
        set("password", password);
        set("email", email);
        set("supervisor_email", supervisor_email);
        set("mobile_number", mobile_number);
        set("group_id", group_id);
        set("status", status);
        //set("created_date", created_date);
    }

    public Users(String username, String employee_id, String firstname, String lastname, String password, String email, String supervisor_email, double mobile_number, int group_id, boolean status, boolean emailVerify, String created_date) {
        set("username", username);
        set("employee_id", employee_id);
        set("firstname", firstname);
        set("lastname", lastname);
        set("password", password);
        set("email", email);
        set("supervisor_email", supervisor_email);
        set("mobile_number", mobile_number);
        set("group_id", group_id);
        set("status", status);
        set("email_status", emailVerify);
        //set("created_date", created_date);
    }
}
