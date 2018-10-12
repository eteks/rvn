/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.ivn_supervisor;

/**
 *
 * @author ets-2
 */
public class Modelversion {
    private int id;
    private float versionname;
    private boolean status;
    private boolean flag;
    private String created_date;
    private int created_or_updated_by;
    private String operation_status;
    public int getId() {
            return id;
    }
    public void setId(int id) {
            this.id = id;
    }
    public float getVersionname() {
            return versionname;
    }
    public void setversionname(float versionname) {
            this.versionname = versionname;
    }
    public boolean getStatus() {
            return status;
    }
    public void setStatus(boolean status) {
            this.status = status;
    }
    public String getCreated_date() {
            return created_date;
    }
    public void setCreated_date(String created_date) {
            this.created_date = created_date;
    }
    public int getCreated_or_updated_by() {
            return created_or_updated_by;
    }
    public void setCreated_or_updated_by(int created_or_updated_by) {
            this.created_or_updated_by = created_or_updated_by;
    }
    public String getOperation_status() {
            return operation_status;
    }
    public void setOperation_status(String operation_status) {
            this.operation_status = operation_status;
    }
    public boolean getFlag() {
            return flag;
    }
    public void setFlag(boolean flag) {
            this.flag = flag;
    }
    
    public Modelversion(){}
    public Modelversion(int id)
    {
            this.id=id;
    }
    //Function for record updation
    public Modelversion(int id, boolean status, boolean flag, String created_date, int created_or_updated_by, String operation_status)
    {
            this.id=id;
            this.status=status;
            this.created_date=created_date;
            this.created_or_updated_by=created_or_updated_by;
            this.operation_status = operation_status;
            this.flag = flag;
    }  
    //Function for record creation
    public Modelversion(float versionname, boolean status, boolean flag, String created_date, int created_or_updated_by, String operation_status)
    {
            this.versionname=versionname;
            this.status=status;
            this.created_date=created_date;
            this.created_or_updated_by=created_or_updated_by;
            this.operation_status = operation_status;
            this.flag = flag;
    }  
}
