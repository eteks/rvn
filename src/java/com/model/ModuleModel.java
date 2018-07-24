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
public class ModuleModel {
    private int id;
    private String moduleName;
    private String iconCode;
    private String routePage;
    private int Status;

    @Override
    public String toString() {
        return "ModuleModel{" + "id=" + id + ", moduleName=" + moduleName + ", iconCode=" + iconCode + ", routePage=" + routePage + ", Status=" + Status + '}';
    }

    public ModuleModel() {
    }

    public ModuleModel(String moduleName, String iconCode, String routePage) {
        this.id = id;
        this.moduleName = moduleName;
        this.iconCode = iconCode;
        this.routePage = routePage;
        this.Status = Status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getIconCode() {
        return iconCode;
    }

    public void setIconCode(String iconCode) {
        this.iconCode = iconCode;
    }

    public String getRoutePage() {
        return routePage;
    }

    public void setRoutePage(String routePage) {
        this.routePage = routePage;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int Status) {
        this.Status = Status;
    }
    
    
}
