/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.pojo.ivn_version;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

/**
 *
 * @author ETS-4
 */
@Table("engine_control_unit")
public class EngineControlUnit extends Model {
	private String created_date;

	public int getECUId() {
		return getInteger("id");
	}

	public String getEcuName() {
		return getString("ecu_name");
	}

	public String getEcuDescription() {
		return getString("ecu_description");
	}

	public int getCreated_or_updated_by() {
		return getInteger("created_or_updated_by");
	}

	public boolean getStatus() {
		return getBoolean("status");
	}

	public String getCreated_date() {
		return created_date;
	}

	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}

	public EngineControlUnit(String ecu_name, int created_or_updated_by) {
		set("ecu_name", ecu_name);
		set("created_or_updated_by", created_or_updated_by);
	}
}
