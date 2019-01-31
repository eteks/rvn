/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.pojo.acb_version;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

/**
 *
 * @author ETS-4
 */
@Table("acbversion_group")
public class ACBVersionGroup extends Model {
	private String button_type;
	private String operation_status;

	public int getACBVGId() {
		return getInteger("id");
	}

	public int getACBversion_id() {
		return getInteger("acbversion_id");
	}

	public int getIVNversion_id() {
		return getInteger("ivnversion_id");
	}

	public int getPDBversion_id() {
		return getInteger("pdbversion_id");
	}

	public int getVehicleversion_id() {
		return getInteger("vehicleversion_id");
	}

	public int getVehicle_id() {
		return getInteger("vehicle_id");
	}

	public int getDomain_and_features_mapping_id() {
		return getInteger("domain_and_features_mapping_id");
	}

	public int getEcu_id() {
		return getInteger("ecu_id");
	}

	public String getInputsignal_group() {
		return getString("inputsignal_group");
	}

	public String getOutputsignal_group() {
		return getString("outputsignal_group");
	}

	public Boolean getTouchedstatus() {
		return getBoolean("touchedstatus");
	}

	public String getButton_type() {
		return button_type;
	}

	public void setButton_type(String button_type) {
		this.button_type = button_type;
	}

	public String getOperation_status() {
		return operation_status;
	}

	public void setOperation_status(String operation_status) {
		this.operation_status = operation_status;
	}

	public ACBVersionGroup() {
	}

	public ACBVersionGroup(int acbversion_id, int ivnversion_id, int pdbversion_id, int vehicleversion_id,
			int vehicle_id, int domain_and_features_mapping_id, int ecu_id, String inputsignal_group,
			String outputsignal_group, Boolean touchedstatus, String button_type, String operation_status) {
		set("acbversion_id",acbversion_id);
		set("ivnversion_id", ivnversion_id);
		set("pdbversion_id",pdbversion_id);
		set("vehicleversion_id",vehicleversion_id);
		set("vehicle_id",vehicle_id);
		set("domain_and_features_mapping_id",domain_and_features_mapping_id);
		set("ecu_id",ecu_id);
		set("inputsignal_group",inputsignal_group);
		set("outputsignal_group",outputsignal_group);
		set("touchedstatus",touchedstatus);
		this.button_type = button_type;
		this.operation_status = operation_status;
	}
}
