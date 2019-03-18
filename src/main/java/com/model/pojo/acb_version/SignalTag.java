package com.model.pojo.acb_version;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

import com.controller.common.CookieRead;

@Table("signaltags")
public class SignalTag extends Model {

	public int getSignalTagId() {
		return getInteger("id");
	}

	public String getTagName() {
		return getString("tagname");
	}

	public boolean getStatus() {
		return getBoolean("status");
	}

	public int getCreated_or_updated_by() {
		return getInteger("created_or_updated_by");
	}

	public SignalTag() {

	}

	public SignalTag(String tagName, String createdDate) {
		set("tagame", tagName);
		set("created_date", createdDate);
		set("created_or_updated_by", CookieRead.getUserIdFromSession());
		//set("status", status);
	}
}
