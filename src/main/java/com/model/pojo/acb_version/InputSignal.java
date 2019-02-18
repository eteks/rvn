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
@Table("acb_inputsignal")
public class InputSignal extends Model {

	public int getaISid() {
		return getInteger("id");
	}

	public int getInput_signal_id() {
		return getInteger("input_signal_id");
	}

	public int getInput_network_id() {
		return getInteger("input_network_id");
	}

	public String getNetwork_type() {
		return getString("network_type");
	}

	public int getPdbversion_group_id() {
		return getInteger("pdbversion_group_id");
	}

	public InputSignal(int input_signal_id, int input_network_id, String network_type, int pdbversion_group_id) {
		set("input_signal_id", input_signal_id);
		set("input_network_id", input_network_id);
		set("network_type", network_type);
		set("pdbversion_group_id", pdbversion_group_id);
	}

}
