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
public class Users {

    private String username;
    private String employee_id;
    private String firstname;
    private String lastname;
    private String password;
    private String email;
    private String supervisor_email;
    private double mobile_number;
    private int group_id;
    private boolean status;
    private boolean emailVerify;
    private String created_date;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSupervisor_email() {
        return supervisor_email;
    }

    public void setSupervisor_email(String supervisor_email) {
        this.supervisor_email = supervisor_email;
    }

    public double getMobile_number() {
        return mobile_number;
    }

    public void setMobile_number(double mobile_number) {
        this.mobile_number = mobile_number;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isEmailVerify() {
        return emailVerify;
    }

    public void setEmailVerify(boolean emailVerify) {
        this.emailVerify = emailVerify;
    }

    public String getCreated_date() {
        return created_date;
    }

    public void setCreated_date(String created_date) {
        this.created_date = created_date;
    }

    public Users(String username, String employee_id, String firstname, String lastname, String password, String email, String supervisor_email, double mobile_number, int group_id, boolean status, String created_date) {
        this.username = username;
        this.employee_id = employee_id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.email = email;
        this.supervisor_email = supervisor_email;
        this.mobile_number = mobile_number;
        this.group_id = group_id;
        this.status = status;
        this.created_date = created_date;
    }

    public Users(String username, String employee_id, String firstname, String lastname, String password, String email, String supervisor_email, double mobile_number, int group_id, boolean status, boolean emailVerify, String created_date) {
        this.username = username;
        this.employee_id = employee_id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.email = email;
        this.supervisor_email = supervisor_email;
        this.mobile_number = mobile_number;
        this.group_id = group_id;
        this.status = status;
        this.emailVerify = emailVerify;
        this.created_date = created_date;
    }
}
