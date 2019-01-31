/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.pojo.acb_version;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

import com.controller.common.CookieRead;

/**
 *
 * @author ETS-4
 */
@Table("acbversion")
public class ACBVersion extends Model {

	private String operation_status;
	private String subversion_status;
	private boolean is_acbsubversion;
	private boolean fully_touchedstatus;
	private String created_date;

	public int getACBId() {
		return getInteger("id");
	}

	public float getVersionname() {
		return getFloat("acb_versionname");
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

	public void setOperation_status(String operation_status) {
		this.operation_status = operation_status;
	}

	public boolean getFlag() {
		return getBoolean("flag");
	}

	public String getSubversion_status() {
		return subversion_status;
	}

	public void setSubversion_status(String subversion_status) {
		this.subversion_status = subversion_status;
	}

	public boolean getIs_acbsubversion() {
		return is_acbsubversion;
	}

	public void setIs_acbsubversion(boolean is_acbsubversion) {
		this.is_acbsubversion = is_acbsubversion;
	}

	public boolean getFully_touchedstatus() {
		return fully_touchedstatus;
	}

	public void setFully_touchedstatus(boolean fully_touchedstatus) {
		this.fully_touchedstatus = fully_touchedstatus;
	}

	public String getCreated_date() {
		return created_date;
	}

	public void setCreated_date(String created_date) {
		this.created_date = created_date;
	}

	public ACBVersion() {
	}

	public ACBVersion(int id) {
		set("id", id);
	}

	// Function for record updation
	public ACBVersion(int id, boolean status, boolean flag, String created_date, String operation_status,
			String subversion_status, boolean is_acbsubversion, boolean fully_touchedstatus) {
		set("id", id);
		set("status", status);
		set("created_or_updated_by", CookieRead.getUserIdFromSession());
		set("flag", flag);
		// this.created_date=created_date;
		this.operation_status = operation_status;
		this.subversion_status = subversion_status;
		this.is_acbsubversion = is_acbsubversion;
		this.fully_touchedstatus = fully_touchedstatus;
	}

	// Function for record creation
	public ACBVersion(float versionname, boolean status, boolean flag, String created_date, String operation_status) {
		set("acb_versionname", versionname);
		set("status", status);
		// this.created_date=created_date;
		set("created_or_updated_by", CookieRead.getUserIdFromSession());
		this.operation_status = operation_status;
		set("flag", flag);
	}
}