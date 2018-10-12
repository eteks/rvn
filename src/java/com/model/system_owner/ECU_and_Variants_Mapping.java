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
public class ECU_and_Variants_Mapping {
    private int id;
    private int ecu_id;
    private int variant_id;
    private String created_date;
    public int getId() {
            return id;
    }
    public void setId(int id) {
            this.id = id;
    }
    public int getEcuId() {
            return ecu_id;
    }
    public void setEcuId(int ecu_id) {
            this.ecu_id = ecu_id;
    }
    public int getVariantId() {
            return variant_id;
    }
    public void setVariantId(int variant_id) {
            this.variant_id = variant_id;
    }
    public String getCreated_date() {
            return created_date;
    }
    public void setCreated_date(String created_date) {
            this.created_date = created_date;
    }

    public ECU_and_Variants_Mapping(){}
    public ECU_and_Variants_Mapping(int ecu_id, int variant_id, String created_date)
    {
            this.ecu_id=ecu_id;
            this.variant_id = variant_id;
            this.created_date=created_date;
    }
}
