/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

/**
 *
 * @author ETS-4
 */
public class ModulePermision {
   private int moduleid;
   private int operationid;

    @Override
    public String toString() {
        return "ModulePermision{" + "moduleid=" + moduleid + ", operationid=" + operationid + '}';
    }

    public ModulePermision(int moduleid, int operationid) {
        this.moduleid = moduleid;
        this.operationid = operationid;
    }

    public ModulePermision() {
    }

    public int getModuleid() {
        return moduleid;
    }

    public void setModuleid(int moduleid) {
        this.moduleid = moduleid;
    }

    public int getOperationid() {
        return operationid;
    }

    public void setOperationid(int operationid) {
        this.operationid = operationid;
    }
}
