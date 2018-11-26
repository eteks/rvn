/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.common;

/**
 *
 * @author ETS-4
 */
public enum VersionViewPage {
    vehicle_add(1),
    pdb_assign(2),
    ivn_version_create(3),
    acb_version_create(4),
    sys_version_create(5),
    model_version(6);
    
    private Integer versionViewPageCode;
    
    VersionViewPage(int versionViewPageCode) {
        this.versionViewPageCode = versionViewPageCode;
    }
    
    public static VersionViewPage fromId(Integer versionViewPageCode) {
        for (VersionViewPage at : VersionViewPage.values()) {
            if (at.getVersionViewPageCode().equals(versionViewPageCode)) {
                return at;
            }
        }
        return null;
    }

    public Integer getVersionViewPageCode() {
        return versionViewPageCode;
    }
    
}
