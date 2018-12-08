/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.pojo.pdb_version;

import com.controller.common.CookieRead;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

/**
 *
 * @author ETS-4
 */
@Table("pdbversion")
public class PDBVersion extends Model {
    
    public int getPDBId() {
        return getInteger("id");
    }
    
    public float getPDBVersionname() {
        return getFloat("pdb_versionname");
    }
    
    public boolean getStatus() {
        return getBoolean("status");
    }
    
    public int getCreated_or_updated_by() {
        return getInteger("created_or_updated_by");
    }
    
    public String getOperation_status() {
        return getString("operation_status");
    }
    
    public boolean getFlag() {
        return getBoolean("flag");
    }
    
    public PDBVersion() {
    }
    
    public PDBVersion(int id) {
        set("id", id);
    }

    //Function for record updation
    public PDBVersion(int id, boolean status, boolean flag, String operation_status) {
        set("id", id);
        set("status", status);
        set("created_or_updated_by", CookieRead.getUserIdFromSession());
        set("operation_status", operation_status);
        set("flag", flag);
    }

    //Function for record creation
    public PDBVersion(float pdb_versionname, boolean status, boolean flag, String operation_status) {
        set("pdb_versionname", pdb_versionname);
        set("status", status);
        set("created_or_updated_by", CookieRead.getUserIdFromSession());
        set("operation_status", operation_status);
        set("flag", flag);
    }
}
