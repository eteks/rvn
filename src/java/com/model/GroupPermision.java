/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

public class GroupPermision {
    private int id;
    private int group_id;
    private int module_id;
    private int modulepermisionid;

    @Override
    public String toString() {
        return "GroupPermision{" + "id=" + id + ", group_id=" + group_id + ", module_id=" + module_id + ", modulepermisionid=" + modulepermisionid + '}';
    }

    public GroupPermision(int group_id, int module_id, int modulepermisionid) {
       
        this.group_id = group_id;
        this.module_id = module_id;
        this.modulepermisionid = modulepermisionid;
    }

    public GroupPermision() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public int getModule_id() {
        return module_id;
    }

    public void setModule_id(int module_id) {
        this.module_id = module_id;
    }

    public int getModulepermisionid() {
        return modulepermisionid;
    }

    public void setModulepermisionid(int modulepermisionid) {
        this.modulepermisionid = modulepermisionid;
    }
}
