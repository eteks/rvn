/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.pojo.pdb_version;

import com.model.pojo.vehicle_modal.VehicleModelMapping;
import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.BelongsToParents;
import org.javalite.activejdbc.annotations.Table;

/**
 *
 * @author ETS-4
 */
@Table("pdbversion_group")
@BelongsToParents({
    @BelongsTo(foreignKeyName = "pdbversion_id",parent = PDBVersion.class),
    @BelongsTo(foreignKeyName = "vehicle_and_model_mapping_id",parent = VehicleModelMapping.class),
    @BelongsTo(foreignKeyName = "domain_and_features_mapping_id",parent = DomainFeaturesMapping.class)
})
public class PDBVersionGroup extends Model {

    private String button_type;
    private String operation_status;

    public int getPDBVersionGroupId() {
        return getInteger("id");
    }

    public int getPDBversion_id() {
        return getInteger("pdbversion_id");
    }

    public int getVehicle_and_model_mapping_id() {
        return getInteger("vehicle_and_model_mapping_id");
    }

    public int getDomain_and_features_mapping_id() {
        return getInteger("domain_and_features_mapping_id");
    }

    public String getAvailable_status() {
        return getString("available_status");
    }

    public String getButton_type() {
        return button_type;
    }

    public void setButton_type(String button_type) {
        this.button_type = button_type;
    }

    public String getOperation_status() {
        return operation_status;
    }

    public void setOperation_status(String operation_status) {
        this.operation_status = operation_status;
    }

    public PDBVersionGroup() {
    }

    public PDBVersionGroup(int pdbversion_id, int vehicle_and_model_mapping_id, int domain_and_features_mapping_id, String available_status, String button_type, String operation_status) {
        this.operation_status = operation_status;
        set("pdbversion_id", pdbversion_id);
        set("vehicle_and_model_mapping_id", vehicle_and_model_mapping_id);
        set("domain_and_features_mapping_id", domain_and_features_mapping_id);
        set("available_status", available_status);
        this.button_type = button_type;
    }
}
