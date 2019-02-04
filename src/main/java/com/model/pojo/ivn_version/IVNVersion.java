/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.pojo.ivn_version;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

import com.controller.common.CookieRead;

/**
 *
 * @author ETS-4
 */
@Table("ivnversion")
public class IVNVersion extends Model {
	private String operation_status;

	public int getIVNId() {
		return getInteger("id");
	}

	public float getVersionname() {
		return getFloat("ivn_versionname");
	}

	public boolean getStatus() {
		return getBoolean("status");
	}

	public int getCreated_or_updated_by() {
		return getInteger("created_or_updated_by");
	}

	public String getOperation_status() {
		return operation_status;
	}

	public boolean getFlag() {
		return getBoolean("flag");
	}

	public IVNVersion() {
	}

	public IVNVersion(int id) {
		set("id", id);
	}

	// Function for record updation
	public IVNVersion(int id, boolean status, boolean flag, String created_date, String operation_status) {
		set("id", id);
		set("status", status);
		//this.created_date = created_date;
		set("created_or_updated_by", CookieRead.getUserIdFromSession());
		this.operation_status = operation_status;
		set("flag", flag);
	}

	// Function for record creation
	public IVNVersion(float versionname, boolean status, boolean flag, String created_date, String operation_status) {
		set("versionname", versionname);
		set("status", status);
		//this.created_date = created_date;
		set("created_or_updated_by", CookieRead.getUserIdFromSession());
		this.operation_status = operation_status;
		set("flag", flag);
	}
}
