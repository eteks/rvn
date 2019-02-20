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
@Table("ivn_hardwaremodels")
public class HardwareModels extends Model {
	public int getCanModelId() {
		return getInteger("id");
	}

	public int getIVNVersionId() {
		return getInteger("ivnversion_id");
	}

	public int getNetworkLinId() {
		return getInteger("network_hardware_id");
	}

	public int getVehicleModelMappingId() {
		return getInteger("vehicle_and_model_mapping_id");
	}

	public boolean getAvailableStatus() {
		return getBoolean("available_status");
	}

	public HardwareModels(int ivnversionId, int networkHardwareId, int vmmId, boolean status) {
		set("ivnversion_id", ivnversionId);
		set("network_hardware_id", networkHardwareId);
		set("vehicle_and_model_mapping_id", vmmId);
		set("available_status", status);
	}
}
