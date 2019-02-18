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
@Table("acb_outputsignal")
public class OutputSignal extends Model {
	public int getaOSid() {
		return getInteger("id");
	}

	public int getOutput_signal_id() {
		return getInteger("output_signal_id");
	}

	public int getOutput_network_id() {
		return getInteger("output_network_id");
	}

	public String getNetwork_type() {
		return getString("network_type");
	}

	public int getPdbversion_group_id() {
		return getInteger("pdbversion_group_id");
	}

	public OutputSignal(int output_signal_id, int output_network_id, String network_type, int pdbversion_group_id) {
		set("output_signal_id", output_signal_id);
		set("output_network_id", output_network_id);
		set("network_type", network_type);
		set("pdbversion_group_id", pdbversion_group_id);
	}
}
