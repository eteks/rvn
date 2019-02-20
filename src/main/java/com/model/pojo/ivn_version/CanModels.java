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
@Table("ivn_canmodels")
public class CanModels extends Model {

	public int getCanModelId() {
		return getInteger("id");
	}

	public int getIVNVersionId() {
		return getInteger("ivnversion_id");
	}

	public int getNetworkCanId() {
		return getInteger("network_can_id");
	}

	public int getVehicleModelMappingId() {
		return getInteger("vehicle_and_model_mapping_id");
	}

	public boolean getAvailableStatus() {
		return getBoolean("available_status");
	}
	
	public CanModels(int ivnversionId, int networkCanId, int vmmId, boolean status) {
		set("ivnversion_id", ivnversionId);
		set("network_can_id", networkCanId);
		set("vehicle_and_model_mapping_id", vmmId);
		set("available_status", status);
	}
}
