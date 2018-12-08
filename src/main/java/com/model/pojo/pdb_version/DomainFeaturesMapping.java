/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.pojo.pdb_version;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.BelongsToParents;
import org.javalite.activejdbc.annotations.Table;

/**
 *
 * @author ETS-4
 */
@Table("domain_and_features_mapping")
@BelongsToParents({
    @BelongsTo(foreignKeyName = "domain_id", parent = Domain.class),
    @BelongsTo(foreignKeyName = "feature_id", parent = Features.class)
})
public class DomainFeaturesMapping extends Model {

    public int getDFMId() {
        return getInteger("id");
    }
    
    public int getDomainId() {
        return getInteger("domain_id");
    }

    public int getFeatureId() {
        return getInteger("feature_id");
    }

    public DomainFeaturesMapping() {
    }

    public DomainFeaturesMapping(int domain_id, int feature_id) {
        set("domain_id", domain_id);
        set("feature_id", feature_id);
    }
}
