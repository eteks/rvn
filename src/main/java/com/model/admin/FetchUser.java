/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.admin;

/**
 *
 * @author ETS-4
 */
public class FetchUser {

    private int id;
    private String employee_id;
    private String firstname;
    private String email;
    private double mobile_number;
    private String group_name;
    private boolean email_status;
    private boolean status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(double mobile_number) {
        this.mobile_number = mobile_number;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public boolean isEmail_status() {
        return email_status;
    }

    public void setEmail_status(boolean email_status) {
        this.email_status = email_status;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
