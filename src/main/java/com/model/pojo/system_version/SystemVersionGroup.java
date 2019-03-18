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
@Table("systemversion_group")
public class SystemVersionGroup extends Model {
	private String button_type;
	private String operation_status;

	public int getSysVerGrpId() {
		return getInteger("id");
	}

	public int getSystemVersionId() {
		return getInteger("systemversion_id");
	}

	public int getVehicleVersionId() {
		return getInteger("vehicleversion_id");
	}

	public int getVehicleId() {
		return getInteger("vehicle_id");
	}

	public int getACBversionId() {
		return getInteger("acbversion_id");
	}

	public int getDomain_and_features_mapping_id() {
		return getInteger("domain_and_features_mapping_id");
	}

	public int getEcuId() {
		return getInteger("ecu_id");
	}

	public int getVariantId() {
		return getInteger("variant_id");
	}

	public String getAvailableStatus() {
		return getString("available_status");
	}

	public String getButton_type() {
		return button_type;
	}

	public String getOperation_status() {
		return operation_status;
	}

	public void setButton_type(String button_type) {
		this.button_type = button_type;
	}

	public void setOperation_status(String operation_status) {
		this.operation_status = operation_status;
	}

	public SystemVersionGroup() {
	}

	public SystemVersionGroup(int systemversion_id, int vehicleversion_id, int vehicle_id, int acbversion_id,
			int domain_and_features_mapping_id, int ecu_id, int variant_id, String available_status, String button_type,
			String operation_status) {
		this.operation_status = operation_status;
		set("systemversion_id", systemversion_id);
		set("vehicleversion_id", vehicleversion_id);
		set("vehicle_id", vehicle_id);
		set("acbversion_id", acbversion_id);
		set("domain_and_features_mapping_id", domain_and_features_mapping_id);
		set("ecu_id", ecu_id);
		set("variant_id", variant_id);
		set("available_status", available_status);
		this.button_type = button_type;
	}
}
