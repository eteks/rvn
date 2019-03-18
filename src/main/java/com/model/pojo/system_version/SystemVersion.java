/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.pojo.system_version;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

import com.controller.common.CookieRead;

/**
 *
 * @author ETS-4
 */
@Table("systemversion")
public class SystemVersion extends Model {
	private String operation_status;

	public int getSystemVersionId() {
		return getInteger("id");
	}

	public float getSystemVersionname() {
		return getFloat("system_versionname");
	}

	public String getCreated_date() {
		return getString("created_date");
	}

	public int getCreated_or_updated_by() {
		return getInteger("created_or_updated_by");
	}

	public boolean getStatus() {
		return getBoolean("status");
	}

	public boolean getFlag() {
		return getBoolean("flag");
	}

	public String getOperation_status() {
		return operation_status;
	}

	public void setOperation_status(String operation_status) {
		this.operation_status = operation_status;
	}

	public SystemVersion() {
	}

	public SystemVersion(int id) {
		set("id", id);
	}

	// Function for record updation
	public SystemVersion(int id, boolean status, boolean flag, String created_date, String operation_status) {
		set("id", id);
		set("status", status);
		set("created_date", created_date);
		set("created_or_updated_by", CookieRead.getUserIdFromSession());
		set("operation_status", operation_status);
		set("flag", flag);
	}

	// Function for record creation
	public SystemVersion(float versionname, boolean status, boolean flag, String created_date,
			String operation_status) {
		set("system_versionname", versionname);
		set("status", status);
		set("created_date", created_date);
		set("created_or_updated_by", CookieRead.getUserIdFromSession());
		set("operation_status", operation_status);
		set("flag", flag);
	}
}
