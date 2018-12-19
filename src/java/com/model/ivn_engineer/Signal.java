/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.ivn_engineer;

import org.json.simple.JSONArray;

/**
 *
 * @author ets-2
 */
public class Signal {
    private int id;   
    private String signal_name;
    private String signal_alias; 
    private String signal_description;
    private int signal_length;
    private String signal_byteorder;
    private String signal_unit;
    private String signal_valuetype;
    private int signal_initvalue;
    private double signal_factor; 
    private int signal_offset;
    private int signal_minimum;
    private int signal_maximum;
    private String signal_valuetable;
    private String signal_can_id;
    private String signal_lin_id;  
    private String signal_hw_id; 
    private JSONArray signal_tags;
    
    private String created_date;
    private int created_or_updated_by;
    private boolean status;

    public int getId() {
            return id;
    }
    public void setId(int id) {
            this.id = id;
    }
    public String getSignal_name() {
            return signal_name;
    }
    public void setSignal_name(String signal_name) {
            this.signal_name = signal_name;
    }
    public String getSignal_alias() {
            return signal_alias;
    }
    public void setSignal_alias(String signal_alias) {
            this.signal_alias = signal_alias;
    }
    public String getSignal_description() {
            return signal_description;
    }
    public void setSignal_description(String signal_description) {
            this.signal_description = signal_description;
    }
    public int getSignal_length() {
            return signal_length;
    }
    public void setSignal_length(int signal_length) {
            this.signal_length = signal_length;
    }
    public String getSignal_byteorder() {
            return signal_byteorder;
    }
    public void setSignal_byteorder(String signal_byteorder) {
            this.signal_byteorder = signal_byteorder;
    }
    public String getSignal_unit() {
            return signal_unit;
    }
    public void setSignal_unit(String signal_unit) {
            this.signal_unit = signal_unit;
    }
    public String getSignal_valuetype() {
            return signal_valuetype;
    }
    public void setSignal_valuetype(String signal_valuetype) {
            this.signal_valuetype = signal_valuetype;
    }
    public int getSignal_initvalue() {
            return signal_initvalue;
    }
    public void setSignal_initvalue(int signal_initvalue) {
            this.signal_initvalue = signal_initvalue;
    }
    public double getSignal_factor() {
            return signal_factor;
    }
    public void setSignal_factor(double signal_factor) {
            this.signal_factor = signal_factor;
    }
    public int getSignal_offset() {
            return signal_offset;
    }
    public void setSignal_offset(int signal_offset) {
            this.signal_offset = signal_offset;
    }
    public int getSignal_minimum() {
            return signal_minimum;
    }
    public void setSignal_minimum(int signal_minimum) {
            this.signal_minimum = signal_minimum;
    }
    public int getSignal_maximum() {
            return signal_maximum;
    }
    public void setSignal_maximum(int signal_maximum) {
            this.signal_maximum = signal_maximum;
    }
    public String getSignal_valuetable() {
            return signal_valuetable;
    }
    public void setSignal_valuetable(String signal_valuetable) {
            this.signal_valuetable = signal_valuetable;
    }
    public String getSignal_can_id() {
            return signal_can_id;
    }
    public void setSignal_can_id(String signal_can_id) {
            this.signal_can_id = signal_can_id;
    }
    public String getSignal_lin_id() {
            return signal_lin_id;
    }
    public void setSignal_lin_id(String signal_lin_id) {
            this.signal_lin_id = signal_lin_id;
    }
    public String getSignal_hw_id() {
            return signal_hw_id;
    }
    public void setSignal_hw_id(String signal_hw_id) {
            this.signal_hw_id = signal_hw_id;
    }
    public JSONArray getSignal_tags() {
            return signal_tags;
    }
    public void setSignal_tags(JSONArray signal_tags) {
            this.signal_tags = signal_tags;
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

    public Signal(){}
    //Function for Signal
    public Signal(String signal_name, String signal_alias, String signal_description,
                  int signal_length, String signal_byteorder, String signal_unit,
                  String signal_valuetype, int signal_initvalue, double signal_factor,
                  int signal_offset, int signal_minimum, int signal_maximum,
                  String signal_valuetable, String signal_can_id, String signal_lin_id, String signal_hw_id,                   
                  JSONArray signal_tags, String created_date, int created_or_updated_by)
    {
        this.signal_name=signal_name;
        this.signal_alias = signal_alias;
        this.signal_description = signal_description;
        this.signal_length = signal_length;
        this.signal_byteorder = signal_byteorder;
        this.signal_unit = signal_unit;
        this.signal_valuetype = signal_valuetype;
        this.signal_initvalue = signal_initvalue;
        this.signal_factor = signal_factor;
        this.signal_offset = signal_offset;
        this.signal_minimum = signal_minimum;
        this.signal_maximum = signal_maximum;
        this.signal_valuetable = signal_valuetable;
        this.signal_can_id = signal_can_id;
        this.signal_lin_id = signal_lin_id;
        this.signal_hw_id = signal_hw_id;
        this.signal_tags = signal_tags;
        
        this.created_date=created_date;
        this.created_or_updated_by=created_or_updated_by;
    }

    public Signal(String signal_name, String signal_alias,String signal_description , String signal_can_id, String signal_lin_id, String signal_hw_id, String created_date, int created_or_updated_by, boolean status) {
        this.signal_name = signal_name;
        this.signal_alias = signal_alias;
        this.signal_description = signal_description;
        this.signal_can_id = signal_can_id;
        this.signal_lin_id = signal_lin_id;
        this.signal_hw_id = signal_hw_id;
        this.created_date = created_date;
        this.created_or_updated_by = created_or_updated_by;
        this.status = status;
    }
    
    
}
