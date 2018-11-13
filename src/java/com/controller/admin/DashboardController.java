/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.admin;

import com.model.admin.AdminDB;
import com.model.admin.UserDB;
import com.model.pdb_owner.PDBVersionDB;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author ETS-4
 */
public class DashboardController {

    private JSONArray feature_chart = new JSONArray();
    private JSONArray users_chart = new JSONArray();
    private Map<String, Object> dashboard_result = new HashMap<String, Object>();
    private Map<String, String> maps = new HashMap<String, String>();

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

    public Map<String, Object> getDashboard_result() {
        return dashboard_result;
    }

    public void setDashboard_result(Map<String, Object> dashboard_result) {
        this.dashboard_result = dashboard_result;
    }

    public Map<String, String> getMaps() {
        return maps;
    }

    public void setMaps(Map<String, String> maps) {
        this.maps = maps;
    }

    public String featureChart() {
        Object[] feature_result = PDBVersionDB.getFeaturesChartCount();
        int touched = (int) feature_result[1];
        int untouched = (int) feature_result[0] - (int) feature_result[1];

        JSONObject untouch = new JSONObject();
        untouch.put("name", "Untouched");
        untouch.put("value", untouched);
        feature_chart.add(untouch);
        JSONObject touch = new JSONObject();
        touch.put("name", "Touched");
        touch.put("value", touched);
        feature_chart.add(touch);

        return "success";
    }

    public String usersChart() {
        users_chart = UserDB.getUserCountbyGroup();
        return "success";
    }

    public String GetAdmin_Dashboarddata() {
        try {
            dashboard_result = AdminDB.GetAdmin_Dashboarddata();
            System.out.println("dashboard_result" + dashboard_result);
        } catch (Exception ex) {
            System.out.println("entered into catch");
            System.out.println(ex.getMessage());
            maps.put("status", "Some error occurred !!");
        }
        return "success";
    }
}
