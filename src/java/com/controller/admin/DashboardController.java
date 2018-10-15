/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.admin;

import com.model.admin.UserDB;
import com.model.pdb_owner.PDBVersionDB;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author ETS-4
 */
public class DashboardController {

    private JSONArray feature_chart = new JSONArray();
    private JSONArray users_chart = new JSONArray();

    public JSONArray getFeature_chart() {
        return feature_chart;
    }

    public void setFeature_chart(JSONArray feature_chart) {
        this.feature_chart = feature_chart;
    }

    public JSONArray getUsers_chart() {
        return users_chart;
    }

    public void setUsers_chart(JSONArray users_chart) {
        this.users_chart = users_chart;
    }

    public String featureChart() {
        Object[] feature_result = PDBVersionDB.getFeaturesChartCount();
        int touched = (int) feature_result[1];
        int untouched = (int) feature_result[0] - (int) feature_result[1];

        JSONObject untouch = new JSONObject();
        untouch.put("name","Untouched");
        untouch.put("value",untouched);
        feature_chart.add(untouch);
        JSONObject touch = new JSONObject();
        touch.put("name","Touched");
        touch.put("value",touched);
        feature_chart.add(touch);
        
        return "success";
    }
    
    public String usersChart() {
        users_chart = UserDB.getUserCountbyGroup();
        return "success";
    }
}
