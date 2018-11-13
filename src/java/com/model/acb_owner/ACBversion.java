/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.acb_owner;

import com.controller.common.CookieRead;
import com.model.ivn_engineer.*;

/**
 *
 * @author ets-2
 */
public class ACBversion {
    private int id;
    private float versionname;
    private boolean status;
    private boolean flag;
    private String created_date;
    private int created_or_updated_by;
    private String operation_status;
    private String subversion_status;
    private int subversion_of;
    private boolean is_acbsubversion;
    private boolean fully_touchedstatus;
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
    public String getSubversion_status() {
            return subversion_status;
    }
    public void setSubversion_status(String subversion_status) {
            this.subversion_status = subversion_status;
    }
    public boolean getIs_acbsubversion() {
            return is_acbsubversion;
    }
    public void setIs_acbsubversion(boolean is_acbsubversion) {
            this.is_acbsubversion = is_acbsubversion;
    }
    public boolean getFully_touchedstatus() {
            return fully_touchedstatus;
    }
    public void setFully_touchedstatus(boolean fully_touchedstatus) {
            this.fully_touchedstatus = fully_touchedstatus;
    }
    
    public ACBversion(){}
    public ACBversion(int id)
    {
            this.id=id;
    }
    //Function for record updation
    public ACBversion(int id, boolean status, boolean flag, String created_date, String operation_status, String subversion_status, boolean is_acbsubversion, boolean fully_touchedstatus)
    {
            this.id=id;
            this.status=status;
            this.created_date=created_date;
            this.created_or_updated_by=CookieRead.getUserIdFromSession();
            this.operation_status = operation_status;
            this.flag = flag;
            this.subversion_status = subversion_status;
            this.is_acbsubversion = is_acbsubversion;
            this.fully_touchedstatus = fully_touchedstatus;
    }  
    //Function for record creation
    public ACBversion(float versionname, boolean status, boolean flag, String created_date, String operation_status)
    {
            this.versionname=versionname;
            this.status=status;
            this.created_date=created_date;
            this.created_or_updated_by=CookieRead.getUserIdFromSession();
            this.operation_status = operation_status;
            this.flag = flag;
    } 
}
