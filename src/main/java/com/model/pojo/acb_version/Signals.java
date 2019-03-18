/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.pojo.acb_version;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;
import org.json.simple.JSONArray;

import com.controller.common.CookieRead;

/**
 *
 * @author ETS-4
 */
@Table("signals")
public class Signals extends Model {
	private JSONArray signal_tags;

	public int getSignalId() {
		return getInteger("id");
	}

	public String getSignal_name() {
		return getString("signal_name");
	}

	public String getSignal_alias() {
		return getString("signal_alias");
	}

	public String getSignal_description() {
		return getString("signal_description");
	}

	public int getSignal_length() {
		return getInteger("signal_length");
	}

	public String getSignal_byteorder() {
		return getString("signal_byteorder");
	}

	public String getSignal_unit() {
		return getString("signal_unit");
	}

	public String getSignal_valuetype() {
		return getString("signal_valuetype");
	}

	public int getSignal_initvalue() {
		return getInteger("signal_initvalue");
	}

	public double getSignal_factor() {
		return getDouble("signal_factor");
	}

	public int getSignal_offset() {
		return getInteger("signal_offset");
	}

	public int getSignal_minimum() {
		return getInteger("signal_minimum");
	}

	public int getSignal_maximum() {
		return getInteger("signal_maximum");
	}

	public String getSignal_valuetable() {
		return getString("signal_valuetable");
	}

	public String getSignal_can_id() {
		return getString("can_id_group");
	}

	public String getSignal_lin_id() {
		return getString("lin_id_group");
	}

	public String getSignal_hw_id() {
		return getString("hw_id_group");
	}

	public boolean getStatus() {
		return getBoolean("status");
	}

	public int getCreated_or_updated_by() {
		return getInteger("created_or_updated_by");
	}

	public JSONArray getSignal_tags() {
		return signal_tags;
	}

	public void setSignal_tags(JSONArray signal_tags) {
		this.signal_tags = signal_tags;
	}

	public Signals() {
	}

	public Signals(String signal_name, String signal_alias, String signal_description, int signal_length,
			String signal_byteorder, String signal_unit, String signal_valuetype, int signal_initvalue,
			double signal_factor, int signal_offset, int signal_minimum, int signal_maximum, String signal_valuetable,
			String can_id_group, String lin_id_group, String hw_id_group, JSONArray signal_tags, String created_date) {
		set("signal_name", signal_name);
		set("signal_alias", signal_alias);
		set("signal_description", signal_description);
		set("signal_length", signal_length);
		set("signal_byteorder", signal_byteorder);
		set("signal_unit", signal_unit);
		set("signal_valuetype", signal_valuetype);
		set("signal_initvalue", signal_initvalue);
		set("signal_factor", signal_factor);
		set("signal_offset", signal_offset);
		set("signal_minimum", signal_minimum);
		set("signal_maximum", signal_maximum);
		set("signal_valuetable", signal_valuetable);
		set("can_id_group", can_id_group);
		set("lin_id_group", lin_id_group);
		set("hw_id_group", hw_id_group);
		this.signal_tags = signal_tags;
		set("created_date", created_date);
		set("created_or_updated_by", CookieRead.getUserIdFromSession());
	}

	public Signals(String signal_name, String signal_alias, String signal_description, String signal_can_id,
			String signal_lin_id, String signal_hw_id, String created_date, boolean status) {
		set("signal_name", signal_name);
		set("signal_alias", signal_alias);
		set("signal_description", signal_description);
		set("can_id_group", signal_can_id);
		set("lin_id_group", signal_lin_id);
		set("hw_id_group", signal_hw_id);
		set("created_date", created_date);
		set("created_or_updated_by", CookieRead.getUserIdFromSession());
		set("status", status);
	}
}
