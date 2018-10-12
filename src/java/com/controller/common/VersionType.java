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
public enum VersionType {
    VehicleVersion(1),
    pdbVersion(2),
    ivnVersion(3),
    acbVersion(4),
    SystemVersion(5);

    private Integer versionCode;

    VersionType(int versionCode) {
        this.versionCode = versionCode;
    }

    public static VersionType fromId(Integer versionCode) {
        for (VersionType at : VersionType.values()) {
            if (at.getVersionCode().equals(versionCode)) {
                return at;
            }
        }
        return null;
    }

    public Integer getVersionCode() {
        return this.versionCode;
    }
}
