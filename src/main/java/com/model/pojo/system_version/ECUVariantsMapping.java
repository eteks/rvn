/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.pojo.system_version;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

/**
 *
 * @author ETS-4
 */
@Table("ecu_and_variants_mapping")
public class ECUVariantsMapping extends Model {
	public int getEVMId() {
		return getInteger("id");
	}

	public int getEcuId() {
		return getInteger("ecu_id");
	}

	public int getVariantId() {
		return getInteger("variant_id");
	}

	public String getCreated_date() {
		return getString("created_date");
	}

	public ECUVariantsMapping() {
	}

	public ECUVariantsMapping(int ecu_id, int variant_id, String created_date) {
		set("ecu_id", ecu_id);
		set("variant_id", variant_id);
		set("created_date", created_date);
	}
}
