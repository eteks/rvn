/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.pdb_owner;

/**
 *
 * @author ets-2
 */
public class PDBversion {
    private int id;
    private float pdb_versionname;
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
    public float getPDBVersionname() {
            return pdb_versionname;
    }
    public void setPDBVersionname(float pdb_versionname) {
            this.pdb_versionname = pdb_versionname;
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
    
    public PDBversion(){}
    public PDBversion(int id)
    {
            this.id=id;
    }
    //Function for record updation
    public PDBversion(int id, boolean status, boolean flag, String created_date, int created_or_updated_by, String operation_status)
    {
            this.id=id;
            this.status=status;
            this.created_date=created_date;
            this.created_or_updated_by=created_or_updated_by;
            this.operation_status = operation_status;
            this.flag = flag;
    }  
    //Function for record creation
    public PDBversion(float pdb_versionname, boolean status, boolean flag, String created_date, int created_or_updated_by, String operation_status)
    {
            this.pdb_versionname=pdb_versionname;
            this.status=status;
            this.created_date=created_date;
            this.created_or_updated_by=created_or_updated_by;
            this.operation_status = operation_status;
            this.flag = flag;
    }  
}
