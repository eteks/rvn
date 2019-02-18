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
@Table("network")
public class Network extends Model {
	private String created_date;

	public int getNetworkId() {
		return getInteger("id");
	}

	public String getNetworkName() {
		return getString("network_name");
	}

	public String getNetworkDescription() {
		return getString("network_description");
	}

	public String getNetworkType() {
		return getString("network_type");
	}

	public boolean getStatus() {
		return getBoolean("status");
	}

	public int getCreated_or_updated_by() {
		return getInteger("created_or_updated_by");
	}

	public String getCreated_date() {
		return created_date;
	}

	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}
	
}
