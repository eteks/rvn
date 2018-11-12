/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.pojo.pdb_version;

import com.model.pojo.vehicle_modal.VehicleModelMapping;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 *
 * @author ETS-4
 */
@Entity
public class PDBVersionGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "pdb_grp_generator")
    @SequenceGenerator(name = "pdb_grp_generator", sequenceName = "pdb_grp_seq")
    @Column(updatable = false, nullable = false)
    private int id;
    @OneToOne
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
    @JoinColumn(name = "pdbversion_id")
    private PDBVersion pdbversion;
    @OneToOne
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
    @JoinColumn(name = "vehicle_and_model_mapping_id")
    private VehicleModelMapping vmm;
    @OneToOne
    @Cascade({CascadeType.SAVE_UPDATE, CascadeType.DELETE})
    @JoinColumn(name = "domain_and_features_mapping_id")
    private DomainFeaturesMapping dfm;
    private String available_status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PDBVersion getPdbversion() {
        return pdbversion;
    }

    public void setPdbversion(PDBVersion pdbversion) {
        this.pdbversion = pdbversion;
    }

    public VehicleModelMapping getVmm() {
        return vmm;
    }

    public void setVmm(VehicleModelMapping vmm) {
        this.vmm = vmm;
    }

    public DomainFeaturesMapping getDfm() {
        return dfm;
    }

    public void setDfm(DomainFeaturesMapping dfm) {
        this.dfm = dfm;
    }

    public String getAvailable_status() {
        return available_status;
    }

    public void setAvailable_status(String available_status) {
        this.available_status = available_status;
    }

}
