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
@Table("variants")
public class Variants extends Model {

	public int getVariantId() {
		return getInteger("id");
	}

	public String getVariantname() {
		return getString("variant_name");
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

	public Variants() {
	}

	public Variants(String variant_name, String created_date)
    {
            set("variant_name", variant_name);
            set("created_date", created_date);
            set("created_or_updated_by", CookieRead.getUserIdFromSession());
    }
}
