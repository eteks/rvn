/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.pojo.pdb_version;

import com.controller.common.CookieRead;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.Table;

/**
 *
 * @author ETS-4
 */
@Table("domain")
public class Domain extends Model {

    public int getDomainId() {
        return getInteger("id");
    }

    public String getDomainname() {
        return getString("domain_name");
    }

    public Domain() {
    }

    public Domain(String domain_name) {
        set("domain_name", domain_name);
        set("created_or_updated_by", CookieRead.getUserIdFromSession());
    }
}
