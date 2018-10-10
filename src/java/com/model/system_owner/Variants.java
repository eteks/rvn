/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.system_owner;

import com.model.pdb_owner.*;

/**
 *
 * @author ets-2
 */
public class Variants {
    private int id;
    private String variant_name;
    private String created_date;
    private int created_or_updated_by;
    public int getId() {
            return id;
    }
    public void setId(int id) {
            this.id = id;
    }
    public String getVariantname() {
            return variant_name;
    }
    public void setVariantname(String variant_name) {
            this.variant_name = variant_name;
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

    public Variants(){}
    public Variants(String variant_name, String created_date, int created_or_updated_by)
    {
            this.variant_name=variant_name;
            this.created_date=created_date;
            this.created_or_updated_by=created_or_updated_by;
    }
}
