/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

/**
 *
 * @author ets-2
 */
public class Vehicleversion {
    private int id;
    private String versionname;
    private boolean status;
    private String created_date;
    private int created_or_updated_by;
    public int getId() {
            return id;
    }
    public void setId(int id) {
            this.id = id;
    }
    public String getVersionname() {
            return versionname;
    }
    public void setversionname(String versionname) {
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
    
    public Vehicleversion(){}
    public Vehicleversion(String versionname, boolean status, String created_date, int created_or_updated_by)
    {
            this.versionname=versionname;
            this.status=status;
            this.created_date=created_date;
            this.created_or_updated_by=created_or_updated_by;
    }  
}
