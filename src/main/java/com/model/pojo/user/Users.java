/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.pojo.user;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

/**
 *
 * @author ETS-4
 */
@Table("users")
public class Users extends Model {

    public Users(String username, String employee_id, String firstname, String lastname, String password, String email, String supervisor_email, double mobile_number, int group_id, boolean status, String created_date) {
        set("username", username);
        set("employee_id", employee_id);
        set("firstname", firstname);
        set("lastname", lastname);
        set("password", password);
        set("email", email);
        set("supervisor_email", supervisor_email);
        set("mobile_number", mobile_number);
        set("status", status);
        set("group_id", group_id);
        set("created_date", created_date);
    }

}
