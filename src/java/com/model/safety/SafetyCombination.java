/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model.safety;

import com.model.safety.*;
import com.controller.common.CookieRead;

/**
 *
 * @author ets-2
 */
public class SafetyCombination {
    private int id;
    private String querybuilder_name;
    private String querybuilder_type;
    private String querybuilder_condition;
    private boolean status;
    private String created_date;
    private int created_or_updated_by;
    private String operation_status;
    public int getId() {
            return id;
    }
    public void setId(int id) {
            this.id = id;
    }
    public String getQuerybuilder_name() {
            return querybuilder_name;
    }
    public void setQuerybuilder_name(String querybuilder_name) {
            this.querybuilder_name = querybuilder_name;
    }
    public String getQuerybuilder_type() {
            return querybuilder_type;
    }
    public void setQuerybuilder_type(String querybuilder_type) {
            this.querybuilder_type = querybuilder_type;
    }
    public String getQuerybuilder_condition() {
            return querybuilder_condition;
    }
    public void getQuerybuilder_condition(String querybuilder_condition) {
            this.querybuilder_condition = querybuilder_condition;
    }
    public boolean getStatus() {
            return status;
    }
    public void setStatus(boolean status) {
            this.status = status;
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
    public String getOperation_status() {
            return operation_status;
    }
    public void setOperation_status(String operation_status) {
            this.operation_status = operation_status;
    }
    
    public SafetyCombination(){}
    public SafetyCombination(int id)
    {
            this.id=id;
    }
    //Function for record updation
    public SafetyCombination(int id, String querybuilder_name,String querybuilder_type,String querybuilder_condition, boolean status, String created_date, String operation_status)
    {
            this.id=id;
            this.querybuilder_name=querybuilder_name;
            this.querybuilder_type=querybuilder_type;
            this.querybuilder_condition=querybuilder_condition;
            this.status=status;
            this.created_date=created_date;
            this.created_or_updated_by=CookieRead.getUserIdFromSession();
            this.operation_status = operation_status;
    }  
    //Function for record creation
    public SafetyCombination(String querybuilder_name,String querybuilder_type,String querybuilder_condition, boolean status, String created_date, String operation_status)
    {
            this.querybuilder_name=querybuilder_name;
            this.querybuilder_type=querybuilder_type;
            this.querybuilder_condition=querybuilder_condition;
            this.status=status;
            this.created_date=created_date;
            this.created_or_updated_by=CookieRead.getUserIdFromSession();
            this.operation_status = operation_status;
    }  
}
