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
@Table("features")
public class Features extends Model {

    public int getFeatureId() {
        return getInteger("id");
    }

    public String getFeaturename() {
        return getString("feature_name");
    }

    public String getFeatureDescription() {
        return getString("feature_description");
    }

    public Features() {
    }

    public Features(String feature_name, String feature_description) {
        set("feature_name", feature_name);
        set("feature_description", feature_description);
        set("created_or_updated_by", CookieRead.getUserIdFromSession());
    }
}
