/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.google.gson.Gson;
import com.model.GlobalDBActivities;
import com.model.VehicleversionDB;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author ets-2
 */
public class GlobalAction {
        public String count;
        public String GetDashboardData() throws SQLException { 
            System.out.println("GetDashboardData");
            Map<String, Integer> count_result = GlobalDBActivities.GetModuleCount();
            count = new Gson().toJson(count_result);
            return "success";
        }
        public String getCount() {
            return count;
        }
        public void setCount(String count) {
            this.count = count;
        }
     
}
