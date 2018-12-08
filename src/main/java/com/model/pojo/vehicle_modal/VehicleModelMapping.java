/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.pojo.vehicle_modal;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.BelongsToParents;
import org.javalite.activejdbc.annotations.Table;

/**
 *
 * @author ETS-4
 */
@Table("vehicle_and_model_mapping")
@BelongsToParents({
    @BelongsTo(foreignKeyName = "vehicleversion_id",parent = VehicleVersion.class),
    @BelongsTo(foreignKeyName = "vehicle_id",parent = Vehicle.class),
    @BelongsTo(foreignKeyName = "model_id",parent = VehicleModel.class)
})
public class VehicleModelMapping extends Model{

}
