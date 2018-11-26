/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.acb_owner;

import com.model.ivn_engineer.*;

/**
 *
 * @author ets-2
 */
public class ACBInput_and_Ouput_Signal {
    private int id;
    private int signal_id;
    private String signal_type;
    private int network_id;
    private String network_type;
    private int pdbversion_group_id;
    private String button_type;
    private String operation_status;
            
    public int getId() {
            return id;
    }
    public void setId(int id) {
            this.id = id;
    }
    public int getSignal_id() {
            return signal_id;
    }
    public void setSignal_id(int signal_id) {
            this.signal_id = signal_id;
    }
    public String getSignal_type() {
            return signal_type;
    }
    public void setSignal_type(String signal_type) {
            this.signal_type = signal_type;
    }
    public int getNetwork_id() {
            return network_id;
    }
    public void setNetwork_id(int network_id) {
            this.network_id = network_id;
    }
    public int getPdbversion_group_id() {
            return pdbversion_group_id;
    }
    public void setPdbversion_group_id(int pdbversion_group_id) {
            this.pdbversion_group_id = pdbversion_group_id;
    }
    public String getNetwork_type() {
            return network_type;
    }
    public void setNetwork_type(String network_type) {
            this.network_type = network_type;
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

    public ACBInput_and_Ouput_Signal(){}
    public ACBInput_and_Ouput_Signal(String signal_type, int signal_id, int network_id, String network_type, int pdbversion_group_id, String button_type, String operation_status)
    {
            this.signal_type = signal_type;
            this.signal_id = signal_id;
            this.network_id = network_id;
            this.network_type = network_type;
            this.pdbversion_group_id=pdbversion_group_id;            
            this.button_type = button_type;
            this.operation_status = operation_status;
    }
}
