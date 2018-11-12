/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.pojo.vehicle_modal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 *
 * @author ETS-4
 */
@Entity
@Table(name = "vehicle_and_model_mapping")
public class VehicleModelMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "vmm_generator")
    @SequenceGenerator(name = "vmm_generator", sequenceName = "vmm_seq")
    @Column(updatable = false, nullable = false)
    private int id;
    @OneToOne
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;
    @OneToOne
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
    @JoinColumn(name = "model_id")
    private VehicleModel vehicleModel;
    @OneToOne
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
    @JoinColumn(name = "vehicleversion_id")
    private VehicleVersion vehicleVersion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public VehicleModel getVehicleModel() {
        return vehicleModel;
    }

    public void setVehicleModel(VehicleModel vehicleModel) {
        this.vehicleModel = vehicleModel;
    }

    public VehicleVersion getVehicleVersion() {
        return vehicleVersion;
    }

    public void setVehicleVersion(VehicleVersion vehicleVersion) {
        this.vehicleVersion = vehicleVersion;
    }

}
