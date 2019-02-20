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
@Table("ivnversion_group")
public class IVNVersionGroup extends Model {

	private int vehicle_and_model_mapping_id;
	private String button_type;
	private String operation_status;

	public int getIVNVgId() {
		return getInteger("id");
	}

	public int getIvnversion_id() {
		return getInteger("ivnversion_id");
	}

	public String getCanmodel_group() {
		return getString("canmodel_group");
	}

	public String getLinmodel_group() {
		return getString("linmodel_group");
	}

	public String getHardwaremodel_group() {
		return getString("hardwaremodel_group");
	}

	public String getSignal_group() {
		return getString("signal_group");
	}

	public String getEcu_group() {
		return getString("ecu_group");
	}

	public int getVehicle_and_model_mapping_id() {
		return vehicle_and_model_mapping_id;
	}

	public String getButton_type() {
		return button_type;
	}

	public String getOperation_status() {
		return operation_status;
	}

	public void setVehicle_and_model_mapping_id(int vehicle_and_model_mapping_id) {
		this.vehicle_and_model_mapping_id = vehicle_and_model_mapping_id;
	}

	public void setButton_type(String button_type) {
		this.button_type = button_type;
	}

	public void setOperation_status(String operation_status) {
		this.operation_status = operation_status;
	}

	public IVNVersionGroup() {
	}
	
	public IVNVersionGroup(int ivnversion_id, String canmodel_group, String linmodel_group, String hardwaremodel_group, String signal_group, String ecu_group, String button_type, String operation_status)
    {
            set("ivnversion_id", ivnversion_id);
            set("canmodel_group", canmodel_group);
            set("linmodel_group", linmodel_group);
            set("hardwaremodel_group", hardwaremodel_group);
            set("signal_group", signal_group);
            set("ecu_group", ecu_group);
            this.button_type=button_type;
            this.operation_status = operation_status;
    }
}
