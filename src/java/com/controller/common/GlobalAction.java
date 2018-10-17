/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller.common;

import com.google.gson.Gson;
import com.model.common.GlobalDBActivities;
import com.model.ivn_supervisor.VehicleversionDB;
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
        private List<Map<String, Object>> group_result = new ArrayList<Map<String, Object>>();
        private Map<String, Integer> count_result = new HashMap<String, Integer>();
        public String GetDashboardData() { 
            System.out.println("GetDashboardData");
            try {
                count_result = GlobalDBActivities.GetModuleCount();
                System.out.println("count_result"+count_result);
                //Get user groupss data
                group_result = GlobalDBActivities.GetUserGroups();
                System.out.println("group_result"+group_result);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            return "success";
        }  
        public List<Map<String, Object>> getGroup_result() {
            return group_result;
        }
        public void setGroup_result(List<Map<String, Object>> group_result) {
            this.group_result = group_result;
        }
        public Map<String, Integer> getCount_result() {
            return count_result;
        }
        public void setCount_result(Map<String, Integer> count_result) {
            this.count_result = count_result;
        }
}
