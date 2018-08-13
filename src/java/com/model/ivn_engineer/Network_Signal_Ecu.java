/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.ivn_engineer;

/**
 *
 * @author ets-2
 */
public class Network_Signal_Ecu {
    private int id;
    
    private String networkname;
    private String network_description;
    
    private String ecu_name;
    private String ecu_description;
    
    private String created_date;
    private int created_or_updated_by;
    private boolean status;
    private String network_type;
    public int getId() {
            return id;
    }
    public void setId(int id) {
            this.id = id;
    }
    public String getNetworkname() {
            return networkname;
    }
    public void setNetworkname(String networkname) {
            this.networkname = networkname;
    }
    public String getNetworkdescription() {
            return network_description;
    }
    public void setNetworkdescription(String network_description) {
            this.network_description = network_description;
    }
    public String getCreated_date() {
            return created_date;
    }
    public void setCreated_date(String created_date) {
            this.created_date = created_date;
    }
    public int getCreated_or_updated_by() {
            return created_or_updated_by;
    }
    public void setCreated_or_updated_by(int created_or_updated_by) {
            this.created_or_updated_by = created_or_updated_by;
    }
    public boolean getStatus() {
            return status;
    }
    public void setStatus(boolean status) {
            this.status = status;
    }
    public String getNetwork_type() {
            return network_type;
    }
    public void setNetwork_type(String network_type) {
            this.network_type = network_type;
    }
    public String getEcuname() {
            return ecu_name;
    }
    public void setEcuname(String ecu_name) {
            this.ecu_name = ecu_name;
    }
    public String getEcudescription() {
            return ecu_description;
    }
    public void setEcudescription(String ecu_description) {
            this.ecu_description = ecu_description;
    }


    public Network_Signal_Ecu(){}
    //Function for newtork such as CAN,LIN,Hardware
    public Network_Signal_Ecu(String networkname, String network_description, String created_date, int created_or_updated_by, String network_type)
    {
        this.networkname=networkname;
        this.network_description = network_description;
        this.created_date=created_date;
        this.created_or_updated_by=created_or_updated_by;
        this.status = status;
        this.network_type = network_type;
    }
    //Function for ECU
    public Network_Signal_Ecu(String ecu_name, String ecu_description, String network_type, String created_date, int created_or_updated_by)
    {
        this.ecu_name=ecu_name;
        this.ecu_description = ecu_description;
        this.created_date=created_date;
        this.created_or_updated_by=created_or_updated_by;
        this.status = status;
        this.network_type = network_type;
    }
}
