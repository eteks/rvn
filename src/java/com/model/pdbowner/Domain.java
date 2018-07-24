/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.pdbowner;

/**
 *
 * @author ets-2
 */
public class Domain {
    private int id;
    private String domain_name;
    private String created_date;
    private int created_or_updated_by;
    public int getId() {
            return id;
    }
    public void setId(int id) {
            this.id = id;
    }
    public String getDomainname() {
            return domain_name;
    }
    public void setDomainname(String domain_name) {
            this.domain_name = domain_name;
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

    public Domain(){}
    public Domain(String domain_name, String created_date, int created_or_updated_by)
    {
            this.domain_name=domain_name;
            this.created_date=created_date;
            this.created_or_updated_by=created_or_updated_by;
    }
}
